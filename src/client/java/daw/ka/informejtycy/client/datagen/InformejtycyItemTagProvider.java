package daw.ka.informejtycy.client.datagen;

import daw.ka.informejtycy.block.CustomBlocks;
import daw.ka.informejtycy.item.CustomItems;
import daw.ka.informejtycy.tag.CustomTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class InformejtycyItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
	public InformejtycyItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
		tag(CustomTags.Items.INFORMEJTYCY_REPAIR)
				.add(key(CustomItems.SILVER_WOLF));
		tag(CustomTags.Items.REINFORCED_INFORMEJTYCY_REPAIR)
				.add(key(CustomItems.GOLDEN_WOLF));
        tag(CustomTags.Items.ZMYSIO_REPAIR)
                .add(key(CustomItems.ZMYSIO_MILK_BUCKET));

		tag(ItemTags.SWORDS)
				.add(key(CustomItems.INFORMEJTYCY_SWORD))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_SWORD))
                .add(key(CustomItems.ZMYSIO_SWORD));
		tag(ItemTags.PICKAXES)
				.add(key(CustomItems.INFORMEJTYCY_PICKAXE))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_PICKAXE));
		tag(ItemTags.SHOVELS)
				.add(key(CustomItems.INFORMEJTYCY_SHOVEL))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_SHOVEL));
		tag(ItemTags.AXES)
				.add(key(CustomItems.INFORMEJTYCY_AXE))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_AXE));
		tag(ItemTags.HOES)
				.add(key(CustomItems.INFORMEJTYCY_HOE))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_HOE));

		tag(ItemTags.TRIMMABLE_ARMOR)
				.add(key(CustomItems.INFORMEJTYCY_HELMET))
				.add(key(CustomItems.INFORMEJTYCY_CHESTPLATE))
				.add(key(CustomItems.INFORMEJTYCY_LEGGINGS))
				.add(key(CustomItems.INFORMEJTYCY_BOOTS))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_HELMET))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_CHESTPLATE))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_LEGGINGS))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_BOOTS))
				.add(key(CustomItems.PRESIDENT_HELMET));

		tag(ItemTags.HEAD_ARMOR_ENCHANTABLE)
				.add(key(CustomItems.INFORMEJTYCY_HELMET))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_HELMET))
				.add(key(CustomItems.PRESIDENT_HELMET));
		tag(ItemTags.CHEST_ARMOR_ENCHANTABLE)
				.add(key(CustomItems.INFORMEJTYCY_CHESTPLATE))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_CHESTPLATE));
		tag(ItemTags.LEG_ARMOR_ENCHANTABLE)
				.add(key(CustomItems.INFORMEJTYCY_LEGGINGS))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_LEGGINGS));
		tag(ItemTags.FOOT_ARMOR_ENCHANTABLE)
				.add(key(CustomItems.INFORMEJTYCY_BOOTS))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_BOOTS));
		tag(ItemTags.DURABILITY_ENCHANTABLE)
				.add(key(CustomItems.INFORMEJTYCY_HELMET))
				.add(key(CustomItems.INFORMEJTYCY_CHESTPLATE))
				.add(key(CustomItems.INFORMEJTYCY_LEGGINGS))
				.add(key(CustomItems.INFORMEJTYCY_BOOTS))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_HELMET))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_CHESTPLATE))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_LEGGINGS))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_BOOTS))
				.add(key(CustomItems.PRESIDENT_HELMET));

		tag(CustomTags.Items.ISTANBUL_LOGS)
				.add(key(CustomBlocks.ISTANBUL_LOG.asItem()))
				.add(key(CustomBlocks.STRIPPED_ISTANBUL_LOG.asItem()))
				.add(key(CustomBlocks.ISTANBUL_WOOD.asItem()))
				.add(key(CustomBlocks.STRIPPED_ISTANBUL_WOOD.asItem()));
		tag(CustomTags.Items.ZARZYK_LOGS)
				.add(key(CustomBlocks.ZARZYK_LOG.asItem()))
				.add(key(CustomBlocks.STRIPPED_ZARZYK_LOG.asItem()))
				.add(key(CustomBlocks.ZARZYK_WOOD.asItem()))
				.add(key(CustomBlocks.STRIPPED_ZARZYK_WOOD.asItem()));
		tag(ItemTags.LOGS_THAT_BURN)
				.addTag(CustomTags.Items.ISTANBUL_LOGS)
				.addTag(CustomTags.Items.ZARZYK_LOGS);
		tag(ItemTags.PLANKS)
				.add(key(CustomBlocks.ISTANBUL_PLANKS.asItem()))
				.add(key(CustomBlocks.ZARZYK_PLANKS.asItem()));
		tag(ItemTags.WOODEN_STAIRS)
				.add(key(CustomBlocks.ISTANBUL_STAIRS.asItem()))
				.add(key(CustomBlocks.ZARZYK_STAIRS.asItem()));
		tag(ItemTags.WOODEN_SLABS)
				.add(key(CustomBlocks.ISTANBUL_SLAB.asItem()))
				.add(key(CustomBlocks.ZARZYK_SLAB.asItem()));
		tag(ItemTags.WOODEN_FENCES)
				.add(key(CustomBlocks.ISTANBUL_FENCE.asItem()))
				.add(key(CustomBlocks.ZARZYK_FENCE.asItem()));
		tag(ItemTags.FENCE_GATES)
				.add(key(CustomBlocks.ISTANBUL_FENCE_GATE.asItem()))
				.add(key(CustomBlocks.ZARZYK_FENCE_GATE.asItem()));
		tag(ItemTags.WOODEN_PRESSURE_PLATES)
				.add(key(CustomBlocks.ISTANBUL_PRESSURE_PLATE.asItem()))
				.add(key(CustomBlocks.ZARZYK_PRESSURE_PLATE.asItem()));
		tag(ItemTags.WOODEN_BUTTONS)
				.add(key(CustomBlocks.ISTANBUL_BUTTON.asItem()))
				.add(key(CustomBlocks.ZARZYK_BUTTON.asItem()));
		tag(ItemTags.WOODEN_TRAPDOORS)
				.add(key(CustomBlocks.ISTANBUL_TRAPDOOR.asItem()))
				.add(key(CustomBlocks.ZARZYK_TRAPDOOR.asItem()));
		tag(ItemTags.WOODEN_DOORS)
				.add(key(CustomBlocks.ISTANBUL_DOOR.asItem()))
				.add(key(CustomBlocks.ZARZYK_DOOR.asItem()));
		tag(ItemTags.LEAVES)
				.add(key(CustomBlocks.ISTANBUL_LEAVES.asItem()))
				.add(key(CustomBlocks.ZARZYK_LEAVES.asItem()));
		tag(ItemTags.SAPLINGS)
				.add(key(CustomBlocks.ISTANBUL_SAPLING.asItem()))
				.add(key(CustomBlocks.ZARZYK_SAPLING.asItem()));
	}

	private static ResourceKey<Item> key(Item item) {
		return BuiltInRegistries.ITEM.getResourceKey(item).orElseThrow();
	}
}
