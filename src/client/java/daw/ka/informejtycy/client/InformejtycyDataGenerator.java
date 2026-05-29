package daw.ka.informejtycy.client;

import daw.ka.informejtycy.client.datagen.*;
import daw.ka.informejtycy.enchantment.CustomEnchantments;
import daw.ka.informejtycy.world.CustomConfiguredFeatures;
import daw.ka.informejtycy.world.CustomPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

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
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, CustomConfiguredFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, CustomPlacedFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.ENCHANTMENT, CustomEnchantments::bootstrap);
	}
}
