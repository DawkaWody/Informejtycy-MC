#!/usr/bin/env python3
"""List every block/item type in a Minecraft (Java) NBT file with its total count."""
import gzip, io, re, struct, sys, zlib
from collections import Counter

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
    f.read(1)                                     # root TAG_Compound
    f.read(struct.unpack(">H", f.read(2))[0])     # root name
    return read(f, 10)


def norm(v):
    m = ID_RE.fullmatch(v)
    return f"{m.group(1) or 'minecraft'}:{m.group(2)}" if m else None


def collect(node, found, key=None, skip_id=False):
    if isinstance(node, list):
        for v in node: collect(v, found, key)
        return
    if not isinstance(node, dict):
        return

    # vanilla structure file: count palette entries via the blocks[].state index
    pal = node.get("palette") or next(iter(node.get("palettes") or []), None)
    if isinstance(pal, list) and isinstance(node.get("blocks"), list):
        names = [norm(p.get("Name", "")) for p in pal]
        for b in node["blocks"]:
            i = b.get("state", -1)
            if 0 <= i < len(names) and names[i]: found[names[i]] += 1
            if "nbt" in b: collect(b["nbt"], found, skip_id=True)   # block entity: id == block
        for k, v in node.items():
            if k not in ("palette", "palettes", "blocks"): collect(v, found, k)
        return

    n = next((node[c] for c in ("Count", "count") if isinstance(node.get(c), int)), 1)
    for k, v in node.items():
        if isinstance(v, str) and k in ID_KEYS and not (skip_id and k == "id"):
            if i := norm(v): found[i] += n
        else:
            collect(v, found, k)


if __name__ == "__main__":
    if len(sys.argv) != 2:
        sys.exit("usage: list_nbt.py <file.nbt>")
    found = Counter()
    collect(load(sys.argv[1]), found)
    w = max((len(k) for k in found), default=0)
    for k, n in sorted(found.items(), key=lambda kv: (-kv[1], kv[0])):
        print(f"{k:<{w}}  {n}")