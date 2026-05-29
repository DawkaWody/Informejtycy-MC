package daw.ka.informejtycy.client.datagen;

import daw.ka.informejtycy.item.CustomItems;
import daw.ka.informejtycy.tag.CustomTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class InformejtycyItemTagProvider extends FabricTagProvider.ItemTagProvider {
	public InformejtycyItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.@NonNull WrapperLookup wrapperLookup) {
		valueLookupBuilder(CustomTags.Items.INFORMEJTYCY_REPAIR)
				.add(CustomItems.SILVER_WOLF);
		valueLookupBuilder(CustomTags.Items.REINFORCED_INFORMEJTYCY_REPAIR)
				.add(CustomItems.GOLDEN_WOLF);
        valueLookupBuilder(CustomTags.Items.ZMYSIO_REPAIR)
                .add(CustomItems.ZMYSIO_MILK_BUCKET);

		valueLookupBuilder(ItemTags.SWORDS)
				.add(CustomItems.INFORMEJTYCY_SWORD)
				.add(CustomItems.REINFORCED_INFORMEJTYCY_SWORD)
                .add(CustomItems.ZMYSIO_SWORD);
		valueLookupBuilder(ItemTags.PICKAXES)
				.add(CustomItems.INFORMEJTYCY_PICKAXE)
				.add(CustomItems.REINFORCED_INFORMEJTYCY_PICKAXE);
		valueLookupBuilder(ItemTags.SHOVELS)
				.add(CustomItems.INFORMEJTYCY_SHOVEL)
				.add(CustomItems.REINFORCED_INFORMEJTYCY_SHOVEL);
		valueLookupBuilder(ItemTags.AXES)
				.add(CustomItems.INFORMEJTYCY_AXE)
				.add(CustomItems.REINFORCED_INFORMEJTYCY_AXE);
		valueLookupBuilder(ItemTags.HOES)
				.add(CustomItems.INFORMEJTYCY_HOE)
				.add(CustomItems.REINFORCED_INFORMEJTYCY_HOE);

		valueLookupBuilder(ItemTags.TRIMMABLE_ARMOR)
				.add(CustomItems.INFORMEJTYCY_HELMET)
				.add(CustomItems.INFORMEJTYCY_CHESTPLATE)
				.add(CustomItems.INFORMEJTYCY_LEGGINGS)
				.add(CustomItems.INFORMEJTYCY_BOOTS)
				.add(CustomItems.REINFORCED_INFORMEJTYCY_HELMET)
				.add(CustomItems.REINFORCED_INFORMEJTYCY_CHESTPLATE)
				.add(CustomItems.REINFORCED_INFORMEJTYCY_LEGGINGS)
				.add(CustomItems.REINFORCED_INFORMEJTYCY_BOOTS)
				.add(CustomItems.PRESIDENT_HELMET);

		valueLookupBuilder(ItemTags.HEAD_ARMOR_ENCHANTABLE)
				.add(CustomItems.INFORMEJTYCY_HELMET)
				.add(CustomItems.REINFORCED_INFORMEJTYCY_HELMET)
				.add(CustomItems.PRESIDENT_HELMET);
		valueLookupBuilder(ItemTags.CHEST_ARMOR_ENCHANTABLE)
				.add(CustomItems.INFORMEJTYCY_CHESTPLATE)
				.add(CustomItems.REINFORCED_INFORMEJTYCY_CHESTPLATE);
		valueLookupBuilder(ItemTags.LEG_ARMOR_ENCHANTABLE)
				.add(CustomItems.INFORMEJTYCY_LEGGINGS)
				.add(CustomItems.REINFORCED_INFORMEJTYCY_LEGGINGS);
		valueLookupBuilder(ItemTags.FOOT_ARMOR_ENCHANTABLE)
				.add(CustomItems.INFORMEJTYCY_BOOTS)
				.add(CustomItems.REINFORCED_INFORMEJTYCY_BOOTS);
		valueLookupBuilder(ItemTags.DURABILITY_ENCHANTABLE)
				.add(CustomItems.INFORMEJTYCY_HELMET)
				.add(CustomItems.INFORMEJTYCY_CHESTPLATE)
				.add(CustomItems.INFORMEJTYCY_LEGGINGS)
				.add(CustomItems.INFORMEJTYCY_BOOTS)
				.add(CustomItems.REINFORCED_INFORMEJTYCY_HELMET)
				.add(CustomItems.REINFORCED_INFORMEJTYCY_CHESTPLATE)
				.add(CustomItems.REINFORCED_INFORMEJTYCY_LEGGINGS)
				.add(CustomItems.REINFORCED_INFORMEJTYCY_BOOTS)
				.add(CustomItems.PRESIDENT_HELMET);
	}
}
