package daw.ka.informejtycy.client.datagen;

import daw.ka.informejtycy.item.CustomItems;
import daw.ka.informejtycy.tag.CustomTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
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
		builder(CustomTags.Items.INFORMEJTYCY_REPAIR)
				.add(key(CustomItems.SILVER_WOLF));
		builder(CustomTags.Items.REINFORCED_INFORMEJTYCY_REPAIR)
				.add(key(CustomItems.GOLDEN_WOLF));
        builder(CustomTags.Items.ZMYSIO_REPAIR)
                .add(key(CustomItems.ZMYSIO_MILK_BUCKET));

		builder(ItemTags.SWORDS)
				.add(key(CustomItems.INFORMEJTYCY_SWORD))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_SWORD))
                .add(key(CustomItems.ZMYSIO_SWORD));
		builder(ItemTags.PICKAXES)
				.add(key(CustomItems.INFORMEJTYCY_PICKAXE))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_PICKAXE));
		builder(ItemTags.SHOVELS)
				.add(key(CustomItems.INFORMEJTYCY_SHOVEL))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_SHOVEL));
		builder(ItemTags.AXES)
				.add(key(CustomItems.INFORMEJTYCY_AXE))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_AXE));
		builder(ItemTags.HOES)
				.add(key(CustomItems.INFORMEJTYCY_HOE))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_HOE));

		builder(ItemTags.TRIMMABLE_ARMOR)
				.add(key(CustomItems.INFORMEJTYCY_HELMET))
				.add(key(CustomItems.INFORMEJTYCY_CHESTPLATE))
				.add(key(CustomItems.INFORMEJTYCY_LEGGINGS))
				.add(key(CustomItems.INFORMEJTYCY_BOOTS))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_HELMET))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_CHESTPLATE))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_LEGGINGS))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_BOOTS))
				.add(key(CustomItems.PRESIDENT_HELMET));

		builder(ItemTags.HEAD_ARMOR_ENCHANTABLE)
				.add(key(CustomItems.INFORMEJTYCY_HELMET))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_HELMET))
				.add(key(CustomItems.PRESIDENT_HELMET));
		builder(ItemTags.CHEST_ARMOR_ENCHANTABLE)
				.add(key(CustomItems.INFORMEJTYCY_CHESTPLATE))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_CHESTPLATE));
		builder(ItemTags.LEG_ARMOR_ENCHANTABLE)
				.add(key(CustomItems.INFORMEJTYCY_LEGGINGS))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_LEGGINGS));
		builder(ItemTags.FOOT_ARMOR_ENCHANTABLE)
				.add(key(CustomItems.INFORMEJTYCY_BOOTS))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_BOOTS));
		builder(ItemTags.DURABILITY_ENCHANTABLE)
				.add(key(CustomItems.INFORMEJTYCY_HELMET))
				.add(key(CustomItems.INFORMEJTYCY_CHESTPLATE))
				.add(key(CustomItems.INFORMEJTYCY_LEGGINGS))
				.add(key(CustomItems.INFORMEJTYCY_BOOTS))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_HELMET))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_CHESTPLATE))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_LEGGINGS))
				.add(key(CustomItems.REINFORCED_INFORMEJTYCY_BOOTS))
				.add(key(CustomItems.PRESIDENT_HELMET));
	}

	private static ResourceKey<Item> key(Item item) {
		return item.builtInRegistryHolder().key();
	}
}
