package daw.ka.informejtycy.client;

import daw.ka.informejtycy.client.datagen.*;
import daw.ka.informejtycy.enchantment.CustomEnchantments;
import daw.ka.informejtycy.world.CustomConfiguredFeatures;
import daw.ka.informejtycy.world.CustomPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class InformejtycyDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(InformejtycyBlockTagProvider::new);
		pack.addProvider(InformejtycyItemTagProvider::new);
		pack.addProvider(InformejtycyLootTableProvider::new);
		pack.addProvider(InformejtycyModelProvider::new);
		pack.addProvider(InformejtycyRecipeProvider::new);
		pack.addProvider(InformejtycyRegistryProvider::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(Registries.FEATURE, CustomConfiguredFeatures::bootstrap);
		registryBuilder.add(Registries.PLACED_FEATURE, CustomPlacedFeatures::bootstrap);
		registryBuilder.add(Registries.ENCHANTMENT, CustomEnchantments::bootstrap);
	}
}
