package daw.ka.informejtycy.item.material;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.tag.CustomTags;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.EnumMap;

public class CustomArmorMaterials {
	static RegistryKey<? extends Registry<EquipmentAsset>> REGISTRY_KEY = RegistryKey.ofRegistry(Identifier.ofVanilla("equipment_asset"));
	public static final RegistryKey<EquipmentAsset> INFORMEJTYCY_KEY = RegistryKey.of(REGISTRY_KEY, InformejtycyRegistry.id("informejtycy"));
	public static final RegistryKey<EquipmentAsset> REINFORCED_INFORMEJTYCY_KEY = RegistryKey.of(REGISTRY_KEY, InformejtycyRegistry.id("reinforced_informejtycy"));
    public static final RegistryKey<EquipmentAsset> ZMYSIO_ELYTRA_KEY = RegistryKey.of(REGISTRY_KEY, InformejtycyRegistry.id("zmysio_elytra"));
	public static final RegistryKey<EquipmentAsset> PRESIDENT_HELMET_KEY = RegistryKey.of(REGISTRY_KEY, InformejtycyRegistry.id("president_helmet"));

	public static final ArmorMaterial INFORMEJTYCY = new ArmorMaterial(33,
			Util.make(new EnumMap<>(EquipmentType.class), map -> {
				map.put(EquipmentType.BOOTS, 3);
				map.put(EquipmentType.LEGGINGS, 6);
				map.put(EquipmentType.CHESTPLATE, 8);
				map.put(EquipmentType.HELMET, 3);
				map.put(EquipmentType.BODY, 11);
	}), 15, SoundEvents.ITEM_ARMOR_EQUIP_WOLF, 4, 0.15f, CustomTags.Items.INFORMEJTYCY_REPAIR, INFORMEJTYCY_KEY);
	public static final ArmorMaterial REINFORCED_INFORMEJTYCY = new ArmorMaterial(50,
			Util.make(new EnumMap<>(EquipmentType.class), map -> {
				map.put(EquipmentType.BOOTS, 5);
				map.put(EquipmentType.LEGGINGS, 8);
				map.put(EquipmentType.CHESTPLATE, 10);
				map.put(EquipmentType.HELMET, 5);
				map.put(EquipmentType.BODY, 11);
	}), 20, SoundEvents.ITEM_ARMOR_EQUIP_WOLF, 5, 0.2f, CustomTags.Items.REINFORCED_INFORMEJTYCY_REPAIR, REINFORCED_INFORMEJTYCY_KEY);
	public static final ArmorMaterial PRESIDENT_HELMET = new ArmorMaterial(50,
			Util.make(new EnumMap<>(EquipmentType.class), map -> {
				map.put(EquipmentType.HELMET, 5);
	}), 20, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 6, 0.2f, CustomTags.Items.ZMYSIO_REPAIR, PRESIDENT_HELMET_KEY);
}
