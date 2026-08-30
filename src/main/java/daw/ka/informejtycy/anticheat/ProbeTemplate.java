package daw.ka.informejtycy.anticheat;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModOrigin;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

// No lambdas, method references or + string concatenation in this class: all three compile to
// invokedynamic, which this class cannot carry.
public final class ProbeTemplate implements Function<String, String> {
    // Replaced per probe. Must stay non-final, or javac inlines them out of reach.
    private static String P0 = "@@P0@@";
    private static String P1 = "@@P1@@";
    private static String P2 = "@@P2@@";
    private static String P3 = "@@P3@@";
    private static String P4 = "@@P4@@";
    private static String P5 = "@@P5@@";
    private static String P6 = "@@P6@@";
    private static String P7 = "@@P7@@";
    private static String ORDER = "@@ORDER@@";
    private static String RESOURCES = "@@RESOURCES@@";

    private static final String MOD_ID = "informejtycy";
    private static final String OWN_PACKAGE = "daw.ka.informejtycy";
    private static final String STUB_CLASS = "daw/ka/informejtycy/client/anticheat/InformejtycyAnticheatClient.class";
    private static final int MAX_MODS_DIR_ENTRIES = 512;

    private static final int[] K = {
            0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
            0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
            0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
            0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
            0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
            0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
            0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
            0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2
    };

    private static final char[] HEX = "0123456789abcdef".toCharArray();

    public ProbeTemplate() {
    }

    @Override
    public String apply(String nonce) {
        long started = System.currentTimeMillis();

        List<String> tamper = new ArrayList<>();
        List<String> digests = new ArrayList<>();
        List<String> resources = splitLines(RESOURCES);
        byte[] measurement = measure(resources, tamper, digests);

        StringBuilder body = new StringBuilder();
        body.append("{\"nonce\":");
        appendString(body, nonce);
        body.append(",\"modVersion\":");
        appendString(body, versionOf(MOD_ID));
        body.append(",\"selfJar\":");
        appendString(body, ownJarHash());
        body.append(",\"selfOrigin\":");
        appendString(body, selfOrigin());
        body.append(",\"resourceDigests\":");
        appendArray(body, digests);

        body.append(",\"mods\":");
        appendMods(body);
        body.append(",\"classpathIds\":");
        appendArray(body, classpathModIds());
        body.append(",\"modsDir\":");
        appendArray(body, modsDirectory());
        body.append(",\"mixinConfigs\":");
        appendArray(body, mixinConfigs(tamper));
        body.append(",\"jvmFlags\":");
        appendArray(body, jvmFlags());

        checkStubShape(tamper);

        body.append(",\"tamper\":");
        appendArray(body, tamper);
        body.append(",\"collectMs\":").append(System.currentTimeMillis() - started);
        body.append("}");

        String json = body.toString();
        byte[] key = hmac(seed(), measurement);
        String mac = hex(hmac(key, json.getBytes(StandardCharsets.UTF_8)));

        StringBuilder envelope = new StringBuilder();
        envelope.append("{\"nonce\":");
        appendString(envelope, nonce);
        envelope.append(",\"body\":");
        appendString(envelope, json);
        envelope.append(",\"mac\":");
        appendString(envelope, mac);
        envelope.append("}");
        return envelope.toString();
    }

    // ---------------------------------------------------------------- session secret

    private static byte[] seed() {
        String[] parts = {P0, P1, P2, P3, P4, P5, P6, P7};
        StringBuilder sb = new StringBuilder();
        List<String> indices = split(ORDER, ',');
        for (int i = 0; i < indices.size(); i++) {
            int index = Integer.parseInt(indices.get(i).trim());
            if (index >= 0 && index < parts.length) {
                sb.append(parts[index]);
            }
        }
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    private static byte[] measure(List<String> resources, List<String> tamper, List<String> digests) {
        ByteArrayOutputStream input = new ByteArrayOutputStream();
        for (int i = 0; i < resources.size(); i++) {
            String resource = resources.get(i);
            byte[] fromJar = readJarResource(resource);
            byte[] fromLoader = readLoaderResource(resource);

            if (fromJar == null) {
                tamper.add(prefixed("resource-unreadable:", resource));
            } else if (fromLoader != null && !java.util.Arrays.equals(fromJar, fromLoader)) {
                tamper.add(prefixed("resource-mismatch:", resource));
            }

            StringBuilder entry = new StringBuilder();
            entry.append(resource).append('=');
            entry.append(fromJar == null ? "none" : hex(sha256(fromJar)).substring(0, 8));
            digests.add(entry.toString());

            writeBytes(input, resource.getBytes(StandardCharsets.UTF_8));
            writeBytes(input, fromJar);
        }
        return sha256(input.toByteArray());
    }

    private static String selfOrigin() {
        Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(MOD_ID);
        if (container.isEmpty()) {
            return "no-container";
        }

        ModOrigin origin = container.get().getOrigin();
        StringBuilder sb = new StringBuilder();
        sb.append(origin.getKind().name());
        if (origin.getKind() != ModOrigin.Kind.PATH) {
            return sb.toString();
        }

        List<Path> paths = origin.getPaths();
        if (paths.isEmpty()) {
            sb.append(":no-paths");
            return sb.toString();
        }

        sb.append(Files.isRegularFile(paths.get(0)) ? ":jar" : ":directory");
        sb.append(':').append(paths.size());
        return sb.toString();
    }

    private static void writeBytes(ByteArrayOutputStream out, byte[] bytes) {
        int length = bytes == null ? -1 : bytes.length;
        out.write((length >>> 24) & 0xff);
        out.write((length >>> 16) & 0xff);
        out.write((length >>> 8) & 0xff);
        out.write(length & 0xff);
        if (bytes != null) {
            out.write(bytes, 0, bytes.length);
        }
    }

    // ---------------------------------------------------------------- probes

    private static String versionOf(String id) {
        Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(id);
        if (container.isEmpty()) {
            return "unknown";
        }
        return container.get().getMetadata().getVersion().getFriendlyString();
    }

    private static String ownJarHash() {
        Path path = ownJarPath();
        if (path == null) {
            return null;
        }
        return hashFile(path);
    }

    private static Path ownJarPath() {
        Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(MOD_ID);
        if (container.isEmpty() || container.get().getOrigin().getKind() != ModOrigin.Kind.PATH) {
            return null;
        }

        List<Path> paths = container.get().getOrigin().getPaths();
        if (paths.isEmpty()) {
            return null;
        }

        Path path = paths.get(0);
        if (!Files.isRegularFile(path)) {
            return null;
        }
        return path;
    }

    private static void appendMods(StringBuilder out) {
        out.append("[");
        boolean first = true;
        Iterator<ModContainer> mods = FabricLoader.getInstance().getAllMods().iterator();
        while (mods.hasNext()) {
            ModContainer mod = mods.next();
            if (!first) {
                out.append(",");
            }
            first = false;

            String file = null;
            String hash = null;
            if (mod.getOrigin().getKind() == ModOrigin.Kind.PATH) {
                List<Path> paths = mod.getOrigin().getPaths();
                if (!paths.isEmpty()) {
                    Path path = paths.get(0);
                    file = path.getFileName().toString();
                    if (Files.isRegularFile(path)) {
                        hash = hashFile(path);
                    }
                }
            }

            out.append("{\"id\":");
            appendString(out, mod.getMetadata().getId());
            out.append(",\"version\":");
            appendString(out, mod.getMetadata().getVersion().getFriendlyString());
            out.append(",\"file\":");
            appendString(out, file);
            out.append(",\"hash\":");
            appendString(out, hash);
            out.append("}");
        }
        out.append("]");
    }

    private static List<String> classpathModIds() {
        List<String> ids = new ArrayList<>();
        try {
            Enumeration<URL> resources = ProbeTemplate.class.getClassLoader().getResources("fabric.mod.json");
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                InputStream stream = null;
                try {
                    stream = url.openStream();
                    String id = extractJsonId(new String(stream.readAllBytes(), StandardCharsets.UTF_8));
                    ids.add(id == null ? prefixed("unnamed:", url.getPath()) : id);
                } catch (Exception e) {
                    ids.add(prefixed("unreadable:", url.getPath()));
                } finally {
                    close(stream);
                }
            }
        } catch (Exception e) {
            ids.add("enumeration-failed");
        }
        return ids;
    }

    private static List<String> modsDirectory() {
        List<String> files = new ArrayList<>();
        Path mods = FabricLoader.getInstance().getGameDir().resolve("mods");
        if (!Files.isDirectory(mods)) {
            return files;
        }

        java.io.File[] entries = mods.toFile().listFiles();
        if (entries == null) {
            files.add("listing-failed");
            return files;
        }

        for (int i = 0; i < entries.length && i < MAX_MODS_DIR_ENTRIES; i++) {
            java.io.File entry = entries[i];
            if (!entry.isFile()) {
                continue;
            }

            StringBuilder line = new StringBuilder();
            line.append(entry.getName()).append(':');
            if (entry.getName().endsWith(".jar")) {
                line.append(hashFile(entry.toPath()));
            } else {
                line.append("skipped");
            }
            files.add(line.toString());
        }
        return files;
    }

    private static List<String> mixinConfigs(List<String> tamper) {
        List<String> configs = new ArrayList<>();
        try {
            Class<?> mixins = Class.forName("org.spongepowered.asm.mixin.Mixins");
            Collection<?> registered = (Collection<?>) mixins.getMethod("getConfigs").invoke(null);
            Iterator<?> iterator = registered.iterator();
            while (iterator.hasNext()) {
                Object config = iterator.next();
                String name = String.valueOf(config.getClass().getMethod("getName").invoke(config));
                configs.add(name);
                collectMixinTargets(config, name, tamper);
            }
        } catch (Throwable t) {
            configs.add("introspection-failed");
        }
        return configs;
    }

    private static void collectMixinTargets(Object config, String name, List<String> tamper) {
        try {
            Object inner = config.getClass().getMethod("getConfig").invoke(config);
            Object targets = inner.getClass().getMethod("getTargets").invoke(inner);
            if (!(targets instanceof Collection)) {
                return;
            }

            Iterator<?> iterator = ((Collection<?>) targets).iterator();
            while (iterator.hasNext()) {
                String target = String.valueOf(iterator.next());
                if (target.startsWith(OWN_PACKAGE) && !name.startsWith(MOD_ID)) {
                    StringBuilder flag = new StringBuilder();
                    flag.append("mixin-target:").append(name).append(':').append(target);
                    tamper.add(flag.toString());
                }
            }
        } catch (Throwable ignored) {
        }
    }

    private static List<String> jvmFlags() {
        List<String> flags = new ArrayList<>();
        try {
            List<String> arguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
            for (int i = 0; i < arguments.size(); i++) {
                String argument = arguments.get(i);
                if (isInteresting(argument)) {
                    flags.add(redact(argument));
                }
            }
        } catch (Throwable t) {
            flags.add("jvm-args-unavailable");
        }
        return flags;
    }

    private static boolean isInteresting(String argument) {
        return argument.startsWith("-javaagent")
                || argument.startsWith("-agentlib")
                || argument.startsWith("-agentpath")
                || argument.startsWith("-Xbootclasspath")
                || argument.startsWith("-XX:+Unlock")
                || argument.contains("jdwp");
    }

    private static String redact(String argument) {
        String normalised = argument.replace('\\', '/');
        int separator = normalised.lastIndexOf('/');
        if (separator < 0) {
            return normalised;
        }

        int assignment = normalised.indexOf('=');
        StringBuilder sb = new StringBuilder();
        if (assignment >= 0) {
            sb.append(normalised, 0, assignment + 1);
        }
        sb.append(normalised.substring(separator + 1));
        return sb.toString();
    }

    private static void checkStubShape(List<String> tamper) {
        try {
            byte[] onDisk = readJarResource(STUB_CLASS);
            if (onDisk == null) {
                return;
            }

            Set<String> constants = utf8Constants(onDisk);
            if (constants.isEmpty()) {
                return;
            }

            String className = STUB_CLASS.substring(0, STUB_CLASS.length() - 6).replace('/', '.');
            Class<?> loaded = Class.forName(className, false, ProbeTemplate.class.getClassLoader());

            Method[] methods = loaded.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                if (!constants.contains(methods[i].getName())) {
                    tamper.add(prefixed("injected-method:", methods[i].getName()));
                }
            }

            Field[] fields = loaded.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                if (!constants.contains(fields[i].getName())) {
                    tamper.add(prefixed("injected-field:", fields[i].getName()));
                }
            }

            Class<?>[] interfaces = loaded.getInterfaces();
            for (int i = 0; i < interfaces.length; i++) {
                if (!constants.contains(interfaces[i].getName().replace('.', '/'))) {
                    tamper.add(prefixed("injected-interface:", interfaces[i].getName()));
                }
            }
        } catch (Throwable ignored) {
        }
    }

    // ---------------------------------------------------------------- io

    private static byte[] readJarResource(String resource) {
        try {
            Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(MOD_ID);
            if (container.isEmpty()) {
                return null;
            }

            Optional<Path> path = container.get().findPath(resource);
            if (path.isEmpty()) {
                return null;
            }
            return Files.readAllBytes(path.get());
        } catch (Throwable t) {
            return null;
        }
    }

    private static byte[] readLoaderResource(String resource) {
        InputStream stream = null;
        try {
            stream = ProbeTemplate.class.getClassLoader().getResourceAsStream(resource);
            if (stream == null) {
                return null;
            }
            return stream.readAllBytes();
        } catch (Throwable t) {
            return null;
        } finally {
            close(stream);
        }
    }

    private static void close(InputStream stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception ignored) {
            }
        }
    }

    private static String hashFile(Path path) {
        InputStream stream = null;
        try {
            stream = Files.newInputStream(path);
            int[] state = init();
            int[] w = new int[64];
            byte[] window = new byte[64];
            byte[] chunk = new byte[8192];
            long total = 0L;
            int filled = 0;
            int read;

            while ((read = stream.read(chunk)) > 0) {
                total = total + read;
                int position = 0;
                while (position < read) {
                    int take = Math.min(64 - filled, read - position);
                    System.arraycopy(chunk, position, window, filled, take);
                    filled = filled + take;
                    position = position + take;
                    if (filled == 64) {
                        compress(state, w, window, 0);
                        filled = 0;
                    }
                }
            }

            return hex(finish(state, w, window, filled, total));
        } catch (Throwable t) {
            return null;
        } finally {
            close(stream);
        }
    }

    // ---------------------------------------------------------------- sha-256 and hmac

    private static int[] init() {
        int[] state = new int[8];
        state[0] = 0x6a09e667;
        state[1] = 0xbb67ae85;
        state[2] = 0x3c6ef372;
        state[3] = 0xa54ff53a;
        state[4] = 0x510e527f;
        state[5] = 0x9b05688c;
        state[6] = 0x1f83d9ab;
        state[7] = 0x5be0cd19;
        return state;
    }

    private static void compress(int[] state, int[] w, byte[] block, int offset) {
        for (int i = 0; i < 16; i++) {
            int base = offset + i * 4;
            w[i] = ((block[base] & 0xff) << 24) | ((block[base + 1] & 0xff) << 16)
                    | ((block[base + 2] & 0xff) << 8) | (block[base + 3] & 0xff);
        }
        for (int i = 16; i < 64; i++) {
            int x = w[i - 15];
            int y = w[i - 2];
            int s0 = rotate(x, 7) ^ rotate(x, 18) ^ (x >>> 3);
            int s1 = rotate(y, 17) ^ rotate(y, 19) ^ (y >>> 10);
            w[i] = w[i - 16] + s0 + w[i - 7] + s1;
        }

        int a = state[0];
        int b = state[1];
        int c = state[2];
        int d = state[3];
        int e = state[4];
        int f = state[5];
        int g = state[6];
        int h = state[7];

        for (int i = 0; i < 64; i++) {
            int s1 = rotate(e, 6) ^ rotate(e, 11) ^ rotate(e, 25);
            int ch = (e & f) ^ ((~e) & g);
            int t1 = h + s1 + ch + K[i] + w[i];
            int s0 = rotate(a, 2) ^ rotate(a, 13) ^ rotate(a, 22);
            int maj = (a & b) ^ (a & c) ^ (b & c);
            int t2 = s0 + maj;
            h = g;
            g = f;
            f = e;
            e = d + t1;
            d = c;
            c = b;
            b = a;
            a = t1 + t2;
        }

        state[0] = state[0] + a;
        state[1] = state[1] + b;
        state[2] = state[2] + c;
        state[3] = state[3] + d;
        state[4] = state[4] + e;
        state[5] = state[5] + f;
        state[6] = state[6] + g;
        state[7] = state[7] + h;
    }

    private static int rotate(int value, int bits) {
        return (value >>> bits) | (value << (32 - bits));
    }

    private static byte[] finish(int[] state, int[] w, byte[] window, int filled, long total) {
        byte[] tail = new byte[filled <= 55 ? 64 : 128];
        System.arraycopy(window, 0, tail, 0, filled);
        tail[filled] = (byte) 0x80;

        long bits = total * 8L;
        for (int i = 0; i < 8; i++) {
            tail[tail.length - 1 - i] = (byte) (bits >>> (8 * i));
        }
        for (int offset = 0; offset < tail.length; offset += 64) {
            compress(state, w, tail, offset);
        }

        byte[] digest = new byte[32];
        for (int i = 0; i < 8; i++) {
            digest[i * 4] = (byte) (state[i] >>> 24);
            digest[i * 4 + 1] = (byte) (state[i] >>> 16);
            digest[i * 4 + 2] = (byte) (state[i] >>> 8);
            digest[i * 4 + 3] = (byte) state[i];
        }
        return digest;
    }

    private static byte[] sha256(byte[] message) {
        int[] state = init();
        int[] w = new int[64];
        int offset = 0;
        while (message.length - offset >= 64) {
            compress(state, w, message, offset);
            offset = offset + 64;
        }

        byte[] window = new byte[64];
        int filled = message.length - offset;
        System.arraycopy(message, offset, window, 0, filled);
        return finish(state, w, window, filled, message.length);
    }

    private static byte[] hmac(byte[] key, byte[] data) {
        byte[] shortened = key.length > 64 ? sha256(key) : key;
        byte[] inner = new byte[64 + data.length];
        byte[] outer = new byte[64 + 32];

        for (int i = 0; i < 64; i++) {
            int b = i < shortened.length ? shortened[i] & 0xff : 0;
            inner[i] = (byte) (b ^ 0x36);
            outer[i] = (byte) (b ^ 0x5c);
        }

        System.arraycopy(data, 0, inner, 64, data.length);
        System.arraycopy(sha256(inner), 0, outer, 64, 32);
        return sha256(outer);
    }

    private static String hex(byte[] bytes) {
        char[] out = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            out[i * 2] = HEX[(bytes[i] >>> 4) & 0xf];
            out[i * 2 + 1] = HEX[bytes[i] & 0xf];
        }
        return new String(out);
    }

    // ---------------------------------------------------------------- text helpers

    private static String prefixed(String prefix, String value) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix).append(value);
        return sb.toString();
    }

    private static List<String> splitLines(String value) {
        return split(value, '\n');
    }

    private static List<String> split(String value, char separator) {
        List<String> parts = new ArrayList<>();
        if (value == null || value.isEmpty()) {
            return parts;
        }

        int start = 0;
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) == separator) {
                if (i > start) {
                    parts.add(value.substring(start, i));
                }
                start = i + 1;
            }
        }
        if (start < value.length()) {
            parts.add(value.substring(start));
        }
        return parts;
    }

    private static String extractJsonId(String json) {
        int index = json.indexOf("\"id\"");
        if (index < 0) {
            return null;
        }

        int colon = json.indexOf(':', index);
        if (colon < 0) {
            return null;
        }

        int open = json.indexOf('"', colon);
        if (open < 0) {
            return null;
        }

        int close = json.indexOf('"', open + 1);
        if (close < 0) {
            return null;
        }
        return json.substring(open + 1, close);
    }

    private static void appendArray(StringBuilder out, List<String> values) {
        out.append("[");
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                out.append(",");
            }
            appendString(out, values.get(i));
        }
        out.append("]");
    }

    private static void appendString(StringBuilder out, String value) {
        if (value == null) {
            out.append("null");
            return;
        }

        out.append('"');
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == '"' || c == '\\') {
                out.append('\\').append(c);
            } else if (c == '\n') {
                out.append("\\n");
            } else if (c == '\r') {
                out.append("\\r");
            } else if (c == '\t') {
                out.append("\\t");
            } else if (c < 0x20 || c > 0x7e) {
                out.append("\\u");
                out.append(HEX[(c >>> 12) & 0xf]);
                out.append(HEX[(c >>> 8) & 0xf]);
                out.append(HEX[(c >>> 4) & 0xf]);
                out.append(HEX[c & 0xf]);
            } else {
                out.append(c);
            }
        }
        out.append('"');
    }

    private static Set<String> utf8Constants(byte[] classFile) {
        Set<String> constants = new HashSet<>();
        java.nio.ByteBuffer buffer = java.nio.ByteBuffer.wrap(classFile);
        if (buffer.remaining() < 10 || buffer.getInt() != 0xCAFEBABE) {
            return constants;
        }

        buffer.getShort();
        buffer.getShort();
        int count = buffer.getShort() & 0xFFFF;

        for (int i = 1; i < count; i++) {
            int tag = buffer.get() & 0xFF;
            if (tag == 1) {
                int length = buffer.getShort() & 0xFFFF;
                byte[] bytes = new byte[length];
                buffer.get(bytes);
                constants.add(new String(bytes, StandardCharsets.UTF_8));
            } else if (tag == 7 || tag == 8 || tag == 16 || tag == 19 || tag == 20) {
                buffer.position(buffer.position() + 2);
            } else if (tag == 15) {
                buffer.position(buffer.position() + 3);
            } else if (tag == 3 || tag == 4 || tag == 9 || tag == 10 || tag == 11 || tag == 12 || tag == 17 || tag == 18) {
                buffer.position(buffer.position() + 4);
            } else if (tag == 5 || tag == 6) {
                buffer.position(buffer.position() + 8);
                i++;
            } else {
                return constants;
            }
        }
        return constants;
    }
}
