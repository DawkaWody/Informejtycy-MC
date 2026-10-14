package daw.ka.informejtycy.item.component;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.item.component.custom.VapeContents;
import net.minecraft.core.component.DataComponentType;

public class CustomDataComponents {
    public static DataComponentType<VapeContents> VAPE_CONTENTS;

    public static void registerAll() {
        VAPE_CONTENTS = InformejtycyRegistry.registerDataComponent("vape_contents", DataComponentType.<VapeContents>builder()
                .persistent(VapeContents.CODEC)
                .networkSynchronized(VapeContents.STREAM_CODEC)
                .cacheEncoding());
    }
}
