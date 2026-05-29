package daw.ka.informejtycy.tag;

import daw.ka.informejtycy.InformejtycyRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class CustomTags {
	public static class Blocks {
		public static final TagKey<Block> NEEDS_INFORMEJTYCY_TOOL = createTag("needs_informejtycy_tool");
		public static final TagKey<Block> INCORRECT_FOR_INFORMEJTYCY_TOOL = createTag("incorrect_for_informejtycy_tool");
		public static final TagKey<Block> NEEDS_REINFORCED_INFORMEJTYCY_TOOL = createTag("needs_reinforced_informejtycy_tool");
		public static final TagKey<Block> INCORRECT_FOR_REINFORCED_INFORMEJTYCY_TOOL = createTag("incorrect_for_reinforced_informejtycy_tool");

		private static TagKey<Block> createTag(String name) {
			return TagKey.of(RegistryKeys.BLOCK, InformejtycyRegistry.id(name));
		}
	}

	public static class Items {
		public static final TagKey<Item> INFORMEJTYCY_REPAIR = createTag("informejtycy_repair");
		public static final TagKey<Item> REINFORCED_INFORMEJTYCY_REPAIR = createTag("reinforced_informejtycy_repair");
        public static final TagKey<Item> ZMYSIO_REPAIR = createTag("zmysio_repair");

		private static TagKey<Item> createTag(String name) {
			return TagKey.of(RegistryKeys.ITEM, InformejtycyRegistry.id(name));
		}
	}
}
