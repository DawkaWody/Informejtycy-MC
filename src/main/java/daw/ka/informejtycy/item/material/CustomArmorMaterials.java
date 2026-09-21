package daw.ka.informejtycy.item.material;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.tag.CustomTags;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.EnumMap;

public class CustomArmorMaterials {
	static ResourceKey<? extends Registry<EquipmentAsset>> REGISTRY_KEY = ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("equipment_asset"));
	public static final ResourceKey<EquipmentAsset> INFORMEJTYCY_KEY = ResourceKey.create(REGISTRY_KEY, InformejtycyRegistry.id("informejtycy"));
	public static final ResourceKey<EquipmentAsset> REINFORCED_INFORMEJTYCY_KEY = ResourceKey.create(REGISTRY_KEY, InformejtycyRegistry.id("reinforced_informejtycy"));
    public static final ResourceKey<EquipmentAsset> ZMYSIO_ELYTRA_KEY = ResourceKey.create(REGISTRY_KEY, InformejtycyRegistry.id("zmysio_elytra"));
	public static final ResourceKey<EquipmentAsset> PRESIDENT_HELMET_KEY = ResourceKey.create(REGISTRY_KEY, InformejtycyRegistry.id("president_helmet"));

	public static final ArmorMaterial INFORMEJTYCY = new ArmorMaterial(33,
			Util.make(new EnumMap<>(ArmorType.class), map -> {
				map.put(ArmorType.BOOTS, 3);
				map.put(ArmorType.LEGGINGS, 6);
				map.put(ArmorType.CHESTPLATE, 8);
				map.put(ArmorType.HELMET, 3);
				map.put(ArmorType.BODY, 11);
	}), 15, SoundEvents.ARMOR_EQUIP_WOLF, 4, 0.15f, CustomTags.Items.INFORMEJTYCY_REPAIR, INFORMEJTYCY_KEY);
	public static final ArmorMaterial REINFORCED_INFORMEJTYCY = new ArmorMaterial(50,
			Util.make(new EnumMap<>(ArmorType.class), map -> {
				map.put(ArmorType.BOOTS, 5);
				map.put(ArmorType.LEGGINGS, 8);
				map.put(ArmorType.CHESTPLATE, 10);
				map.put(ArmorType.HELMET, 5);
				map.put(ArmorType.BODY, 11);
	}), 20, SoundEvents.ARMOR_EQUIP_WOLF, 5, 0.2f, CustomTags.Items.REINFORCED_INFORMEJTYCY_REPAIR, REINFORCED_INFORMEJTYCY_KEY);
	public static final ArmorMaterial PRESIDENT_HELMET = new ArmorMaterial(50,
			Util.make(new EnumMap<>(ArmorType.class), map -> {
				map.put(ArmorType.HELMET, 5);
	}), 20, SoundEvents.ARMOR_EQUIP_LEATHER, 6, 0.2f, CustomTags.Items.ZMYSIO_REPAIR, PRESIDENT_HELMET_KEY);
}
