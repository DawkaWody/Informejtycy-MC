"""Print every block/item/entity type in a Minecraft (Java) NBT file as namespace:id."""
import gzip, io, re, struct, sys, zlib

ID_KEYS = {"Name", "id", "block", "item", "Block", "Item"}
ID_RE = re.compile(r"(?:([a-z0-9_.-]+):)?([a-z0-9_./-]+)")


def read(f, tid):
    if tid == 1: return struct.unpack(">b", f.read(1))[0]
    if tid == 2: return struct.unpack(">h", f.read(2))[0]
    if tid == 3: return struct.unpack(">i", f.read(4))[0]
    if tid == 4: return struct.unpack(">q", f.read(8))[0]
    if tid == 5: return struct.unpack(">f", f.read(4))[0]
    if tid == 6: return struct.unpack(">d", f.read(8))[0]
    if tid == 8: return f.read(struct.unpack(">H", f.read(2))[0]).decode("utf-8", "replace")
    if tid == 7: return f.read(struct.unpack(">i", f.read(4))[0])
    if tid == 9:
        sub, n = f.read(1)[0], struct.unpack(">i", f.read(4))[0]
        return [] if sub == 0 or n <= 0 else [read(f, sub) for _ in range(n)]
    if tid == 10:
        out = {}
        while (t := f.read(1)[0]) != 0:
            name = f.read(struct.unpack(">H", f.read(2))[0]).decode("utf-8", "replace")
            out[name] = read(f, t)
        return out
    if tid in (11, 12):
        n = struct.unpack(">i", f.read(4))[0]
        c, w = ("i", 4) if tid == 11 else ("q", 8)
        return list(struct.unpack(f">{n}{c}", f.read(n * w)))
    raise ValueError(f"unknown tag id {tid}")


def load(path):
    data = open(path, "rb").read()
    if data[:2] == b"\x1f\x8b": data = gzip.decompress(data)
    elif data[:1] != b"\x0a": data = zlib.decompress(data)
    f = io.BytesIO(data)
    f.read(1)                                        # root TAG_Compound
    f.read(struct.unpack(">H", f.read(2))[0])        # root name
    return read(f, 10)


def collect(node, found, key=None):
    if isinstance(node, dict):
        for k, v in node.items(): collect(v, found, k)
    elif isinstance(node, list):
        for v in node: collect(v, found, key)
    elif isinstance(node, str) and key in ID_KEYS:
        if m := ID_RE.fullmatch(node):
            found.add(f"{m.group(1) or 'minecraft'}:{m.group(2)}")


if __name__ == "__main__":
    if len(sys.argv) != 2:
        sys.exit("usage: list_nbt.py <file.nbt>")
    found = set()
    collect(load(sys.argv[1]), found)
    print("\n".join(sorted(found)))