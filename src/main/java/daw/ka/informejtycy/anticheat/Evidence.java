package daw.ka.informejtycy.anticheat;

import java.util.List;

public class Evidence {
    public String nonce;
    public String modVersion;
    public String selfJar;
    public String selfOrigin;
    public List<String> resourceDigests;
    public long collectMs;

    public List<ModEntry> mods;
    public List<String> classpathIds;
    public List<String> modsDir;
    public List<String> mixinConfigs;
    public List<String> jvmFlags;
    public List<String> tamper;

    public static class ModEntry {
        public String id;
        public String version;
        public String hash;
        public String file;
    }
}
