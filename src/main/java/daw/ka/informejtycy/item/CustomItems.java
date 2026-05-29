package daw.ka.informejtycy.item;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.item.custom.*;
import daw.ka.informejtycy.item.material.CustomArmorMaterials;
import daw.ka.informejtycy.item.material.CustomToolMaterials;
import daw.ka.informejtycy.sound.CustomSounds;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Rarity;
import net.minecraft.util.Unit;

public class CustomItems {
	public static Item SILVER_WOLF;
	public static Item GOLDEN_WOLF;
	public static Item BALLS_UNDER_MAGNIFIER;
	public static Item PITCH_CONTEST_TROPHY;
	public static Item STICKY_NOTES;
	public static Item LIGHT_FOOD;
    public static Item ZMYSIO_MILK_BUCKET;
	public static Item ZARZYK_GEL;
	public static Item CONCENTRATED_ZARZYK_GEL;
	public static Item DARK_GLOWSTONE_DUST;
	public static Item TALISMAN_OF_SHRIEK;
	public static Item PLUS;
	// Music discs
	public static Item RIDE_THE_LIGHTNING_MUSIC_DISC;
	public static Item HOLY_WARS_MUSIC_DISC;
	public static Item YOU_MUST_BURN_MUSIC_DISC;
	public static Item NO_MORE_TEARS_MUSIC_DISC;
    public static Item ZALEWIX_BEAT_MUSIC_DISC;
	public static Item STELLA_MUSIC_DISC;
	// Informejtycy toolset
	public static Item INFORMEJTYCY_SWORD;
	public static Item INFORMEJTYCY_PICKAXE;
	public static Item INFORMEJTYCY_SHOVEL;
	public static Item INFORMEJTYCY_AXE;
	public static Item INFORMEJTYCY_HOE;
	// Informejtycy armor
	public static Item INFORMEJTYCY_HELMET;
	public static Item INFORMEJTYCY_CHESTPLATE;
	public static Item INFORMEJTYCY_LEGGINGS;
	public static Item INFORMEJTYCY_BOOTS;
	// Reinforced Informejtycy toolset
	public static Item REINFORCED_INFORMEJTYCY_SWORD;
	public static Item REINFORCED_INFORMEJTYCY_PICKAXE;
	public static Item REINFORCED_INFORMEJTYCY_SHOVEL;
	public static Item REINFORCED_INFORMEJTYCY_AXE;
	public static Item REINFORCED_INFORMEJTYCY_HOE;
	// Reinforced Informejtycy armor
	public static Item REINFORCED_INFORMEJTYCY_HELMET;
	public static Item REINFORCED_INFORMEJTYCY_CHESTPLATE;
	public static Item REINFORCED_INFORMEJTYCY_LEGGINGS;
	public static Item REINFORCED_INFORMEJTYCY_BOOTS;

	// Custom armor upgrades
    public static Item ZMYSIO_SWORD;
    public static Item ZMYSIO_ELYTRA;
	public static Item PRESIDENT_HELMET;

	public static final Item.Settings SILVER_WOLF_SETTINGS = new Item.Settings().maxCount(64);
	public static final Item.Settings GOLDEN_WOLF_SETTINGS = new Item.Settings().maxCount(64).fireproof().rarity(Rarity.UNCOMMON);
	public static final Item.Settings BALLS_UNDER_MAGNIFIER_SETTINGS = new Item.Settings().maxCount(16);
	public static final Item.Settings PITCH_CONTEST_TROPHY_SETTINGS = new Item.Settings().maxCount(4).fireproof().rarity(Rarity.UNCOMMON);
	public static final Item.Settings STICKY_NOTES_SETTINGS = new Item.Settings().maxCount(99);
    public static final Item.Settings LIGHT_FOOD_SETTINGS = new Item.Settings().food(CustomFoodComponents.LIGHT_FOOD, CustomFoodComponents.LIGHT_FOOD_CONSUMABLE);
    public static final Item.Settings ZMYSIO_MILK_BUCKET_SETTINGS = new Item.Settings().maxCount(1).useRemainder(Items.BUCKET).recipeRemainder(Items.BUCKET).food(CustomFoodComponents.ZMYSIO_MILK, CustomFoodComponents.ZMYSIO_MILK_CONSUMABLE).rarity(Rarity.UNCOMMON);
	public static final Item.Settings ZARZYK_GEL_SETTINGS = new Item.Settings().maxCount(1).useRemainder(Items.GLASS_BOTTLE).recipeRemainder(Items.GLASS_BOTTLE).food(CustomFoodComponents.ZARZYK_GEL, CustomFoodComponents.ZARZYK_GEL_CONSUMABLE);
	public static final Item.Settings CONCENTRATED_ZARZYK_GEL_SETTINGS = new Item.Settings().maxCount(1).recipeRemainder(Items.GLASS_BOTTLE);
	public static final Item.Settings DARK_GLOWSTONE_DUST_SETTINGS = new Item.Settings().maxCount(64);
    public static final Item.Settings TALISMAN_OF_SHRIEK_SETTINGS = new Item.Settings().maxCount(1).fireproof().rarity(Rarity.UNCOMMON);
    public static final Item.Settings PLUS_SETTINGS = new Item.Settings().maxCount(64).fireproof().rarity(Rarity.UNCOMMON);
    // Music discs
	public static final Item.Settings RIDE_THE_LIGHTNING_MUSIC_DISC_SETTINGS = new Item.Settings().jukeboxPlayable(CustomSounds.RIDE_THE_LIGHTNING_KEY).maxCount(1).rarity(Rarity.UNCOMMON);
	public static final Item.Settings HOLY_WARS_MUSIC_DISC_SETTINGS = new Item.Settings().jukeboxPlayable(CustomSounds.HOLY_WARS_KEY).maxCount(1).rarity(Rarity.UNCOMMON);
	public static final Item.Settings YOU_MUST_BURN_MUSIC_DISC_SETTINGS = new Item.Settings().jukeboxPlayable(CustomSounds.YOU_MUST_BURN_KEY).maxCount(1).rarity(Rarity.UNCOMMON);
	public static final Item.Settings NO_MORE_TEARS_MUSIC_DISC_SETTINGS = new Item.Settings().jukeboxPlayable(CustomSounds.NO_MORE_TEARS_KEY).maxCount(1).rarity(Rarity.UNCOMMON);
	public static final Item.Settings ZALEWIX_BEAT_MUSIC_DISC_SETTINGS = new Item.Settings().jukeboxPlayable(CustomSounds.ZALEWIX_BEAT_KEY).maxCount(1).rarity(Rarity.UNCOMMON);
    public static final Item.Settings STELLA_MUSIC_DISC_SETTINGS = new Item.Settings().jukeboxPlayable(CustomSounds.STELLA_KEY).maxCount(1).rarity(Rarity.UNCOMMON);
	// Informejtycy toolset
	public static final Item.Settings INFORMEJTYCY_SWORD_SETTINGS = new Item.Settings().sword(CustomToolMaterials.INFORMEJTYCY, 3, -2.4F);
	public static final Item.Settings INFORMEJTYCY_PICKAXE_SETTINGS = new Item.Settings().pickaxe(CustomToolMaterials.INFORMEJTYCY, 1, -2.8F);
	public static final Item.Settings INFORMEJTYCY_SHOVEL_SETTINGS = new Item.Settings().shovel(CustomToolMaterials.INFORMEJTYCY, 1.5F, -3.0F);
	public static final Item.Settings INFORMEJTYCY_AXE_SETTINGS = new Item.Settings().axe(CustomToolMaterials.INFORMEJTYCY, 5.0F, -3.0F);
	public static final Item.Settings INFORMEJTYCY_HOE_SETTINGS = new Item.Settings().hoe(CustomToolMaterials.INFORMEJTYCY, -4.0f, 0);
	// Informejtycy armor
	public static final Item.Settings INFORMEJTYCY_HELMET_SETTINGS = new Item.Settings().armor(CustomArmorMaterials.INFORMEJTYCY, EquipmentType.HELMET);
	public static final Item.Settings INFORMEJTYCY_CHESTPLATE_SETTINGS = new Item.Settings().armor(CustomArmorMaterials.INFORMEJTYCY, EquipmentType.CHESTPLATE);
	public static final Item.Settings INFORMEJTYCY_LEGGINGS_SETTINGS = new Item.Settings().armor(CustomArmorMaterials.INFORMEJTYCY, EquipmentType.LEGGINGS);
	public static final Item.Settings INFORMEJTYCY_BOOTS_SETTINGS = new Item.Settings().armor(CustomArmorMaterials.INFORMEJTYCY, EquipmentType.BOOTS);
	// Reinforced Informejtycy toolset
	public static final Item.Settings REINFORCED_INFORMEJTYCY_SWORD_SETTINGS = new Item.Settings().sword(CustomToolMaterials.REINFORCED_INFORMEJTYCY, 3, -2.4F);
	public static final Item.Settings REINFORCED_INFORMEJTYCY_PICKAXE_SETTINGS = new Item.Settings().pickaxe(CustomToolMaterials.REINFORCED_INFORMEJTYCY, 1, -2.8F);
	public static final Item.Settings REINFORCED_INFORMEJTYCY_SHOVEL_SETTINGS = new Item.Settings().shovel(CustomToolMaterials.REINFORCED_INFORMEJTYCY, 1.5F, -3.0F);
	public static final Item.Settings REINFORCED_INFORMEJTYCY_AXE_SETTINGS = new Item.Settings().axe(CustomToolMaterials.REINFORCED_INFORMEJTYCY, 5.0F, -3.0F);
	public static final Item.Settings REINFORCED_INFORMEJTYCY_HOE_SETTINGS = new Item.Settings().hoe(CustomToolMaterials.REINFORCED_INFORMEJTYCY, -4.0f, 0);
	// Reinforced Informejtycy armor
	public static final Item.Settings REINFORCED_INFORMEJTYCY_HELMET_SETTINGS = new Item.Settings().armor(CustomArmorMaterials.REINFORCED_INFORMEJTYCY, EquipmentType.HELMET);
	public static final Item.Settings REINFORCED_INFORMEJTYCY_CHESTPLATE_SETTINGS = new Item.Settings().armor(CustomArmorMaterials.REINFORCED_INFORMEJTYCY, EquipmentType.CHESTPLATE);
	public static final Item.Settings REINFORCED_INFORMEJTYCY_LEGGINGS_SETTINGS = new Item.Settings().armor(CustomArmorMaterials.REINFORCED_INFORMEJTYCY, EquipmentType.LEGGINGS);
	public static final Item.Settings REINFORCED_INFORMEJTYCY_BOOTS_SETTINGS = new Item.Settings().armor(CustomArmorMaterials.REINFORCED_INFORMEJTYCY, EquipmentType.BOOTS);

    public static final Item.Settings ZMYSIO_SWORD_SETTINGS = new Item.Settings().sword(CustomToolMaterials.ZMYSIO, 3, -2.4F).rarity(Rarity.RARE);
    public static final Item.Settings ZMYSIO_ELYTRA_SETTINGS = new Item.Settings().maxDamage(676).rarity(Rarity.EPIC)
            .component(DataComponentTypes.GLIDER, Unit.INSTANCE)
            .component(
                    DataComponentTypes.EQUIPPABLE,
                    EquippableComponent.builder(EquipmentSlot.CHEST)
                            .equipSound(SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA)
                            .model(CustomArmorMaterials.ZMYSIO_ELYTRA_KEY)
                            .damageOnHurt(false)
                            .build()
            );
	public static final Item.Settings PRESIDENT_HELMET_SETTINGS = new Item.Settings().armor(CustomArmorMaterials.PRESIDENT_HELMET, EquipmentType.HELMET);

	public static void registerAll() {
		SILVER_WOLF = InformejtycyRegistry.registerItem("silver_wolf", SILVER_WOLF_SETTINGS);
		GOLDEN_WOLF = InformejtycyRegistry.registerItem("golden_wolf", GOLDEN_WOLF_SETTINGS);
		BALLS_UNDER_MAGNIFIER = InformejtycyRegistry.registerItem("balls_under_magnifier", BALLS_UNDER_MAGNIFIER_SETTINGS);
		PITCH_CONTEST_TROPHY = InformejtycyRegistry.registerItem("pitch_contest_trophy", PITCH_CONTEST_TROPHY_SETTINGS);
		STICKY_NOTES = InformejtycyRegistry.registerItem("sticky_notes", STICKY_NOTES_SETTINGS);
        ZMYSIO_MILK_BUCKET = InformejtycyRegistry.registerItem("zmysio_milk_bucket", ZMYSIO_MILK_BUCKET_SETTINGS);
        ZARZYK_GEL = InformejtycyRegistry.registerItem("zarzyk_gel_bottle", ZARZYK_GEL_SETTINGS);
		CONCENTRATED_ZARZYK_GEL = InformejtycyRegistry.registerCustomItem("concentrated_zarzyk_gel_bottle", CONCENTRATED_ZARZYK_GEL_SETTINGS, AlwaysGlintItem.class);
		LIGHT_FOOD = InformejtycyRegistry.registerItem("light_food", LIGHT_FOOD_SETTINGS);
        DARK_GLOWSTONE_DUST = InformejtycyRegistry.registerItem("dark_glowstone_dust", DARK_GLOWSTONE_DUST_SETTINGS);
        TALISMAN_OF_SHRIEK = InformejtycyRegistry.registerCustomItem("talisman_of_shriek", TALISMAN_OF_SHRIEK_SETTINGS, ShriekTalismanItem.class);
        PLUS = InformejtycyRegistry.registerItem("plus", PLUS_SETTINGS);
        // Music discs
		RIDE_THE_LIGHTNING_MUSIC_DISC = InformejtycyRegistry.registerItem("music_disc_ride_the_lightning", RIDE_THE_LIGHTNING_MUSIC_DISC_SETTINGS);
		HOLY_WARS_MUSIC_DISC = InformejtycyRegistry.registerItem("music_disc_holy_wars", HOLY_WARS_MUSIC_DISC_SETTINGS);
		YOU_MUST_BURN_MUSIC_DISC = InformejtycyRegistry.registerItem("music_disc_you_must_burn", YOU_MUST_BURN_MUSIC_DISC_SETTINGS);
		NO_MORE_TEARS_MUSIC_DISC = InformejtycyRegistry.registerItem("music_disc_no_more_tears", NO_MORE_TEARS_MUSIC_DISC_SETTINGS);
		ZALEWIX_BEAT_MUSIC_DISC = InformejtycyRegistry.registerItem("music_disc_zalewix_beat", ZALEWIX_BEAT_MUSIC_DISC_SETTINGS);
		STELLA_MUSIC_DISC = InformejtycyRegistry.registerItem("music_disc_stella", STELLA_MUSIC_DISC_SETTINGS);
        // Informejtycy toolset
		INFORMEJTYCY_SWORD = InformejtycyRegistry.registerItem("informejtycy_sword", INFORMEJTYCY_SWORD_SETTINGS);
		INFORMEJTYCY_PICKAXE = InformejtycyRegistry.registerItem("informejtycy_pickaxe", INFORMEJTYCY_PICKAXE_SETTINGS);
		INFORMEJTYCY_SHOVEL = InformejtycyRegistry.registerItem("informejtycy_shovel", INFORMEJTYCY_SHOVEL_SETTINGS);
		INFORMEJTYCY_AXE = InformejtycyRegistry.registerItem("informejtycy_axe", INFORMEJTYCY_AXE_SETTINGS);
		INFORMEJTYCY_HOE = InformejtycyRegistry.registerItem("informejtycy_hoe", INFORMEJTYCY_HOE_SETTINGS);
		// Informejtycy armor
		INFORMEJTYCY_HELMET = InformejtycyRegistry.registerItem("informejtycy_helmet", INFORMEJTYCY_HELMET_SETTINGS);
		INFORMEJTYCY_CHESTPLATE = InformejtycyRegistry.registerItem("informejtycy_chestplate", INFORMEJTYCY_CHESTPLATE_SETTINGS);
		INFORMEJTYCY_LEGGINGS = InformejtycyRegistry.registerItem("informejtycy_leggings", INFORMEJTYCY_LEGGINGS_SETTINGS);
		INFORMEJTYCY_BOOTS = InformejtycyRegistry.registerItem("informejtycy_boots", INFORMEJTYCY_BOOTS_SETTINGS);
		// Reinforced Informejtycy toolset
		REINFORCED_INFORMEJTYCY_SWORD = InformejtycyRegistry.registerItem("reinforced_informejtycy_sword", REINFORCED_INFORMEJTYCY_SWORD_SETTINGS);
		REINFORCED_INFORMEJTYCY_PICKAXE = InformejtycyRegistry.registerItem("reinforced_informejtycy_pickaxe", REINFORCED_INFORMEJTYCY_PICKAXE_SETTINGS);
		REINFORCED_INFORMEJTYCY_SHOVEL = InformejtycyRegistry.registerItem("reinforced_informejtycy_shovel", REINFORCED_INFORMEJTYCY_SHOVEL_SETTINGS);
		REINFORCED_INFORMEJTYCY_AXE = InformejtycyRegistry.registerItem("reinforced_informejtycy_axe", REINFORCED_INFORMEJTYCY_AXE_SETTINGS);
		REINFORCED_INFORMEJTYCY_HOE = InformejtycyRegistry.registerItem("reinforced_informejtycy_hoe", REINFORCED_INFORMEJTYCY_HOE_SETTINGS);
		// Reinforced Informejtycy armor
		REINFORCED_INFORMEJTYCY_HELMET = InformejtycyRegistry.registerItem("reinforced_informejtycy_helmet", REINFORCED_INFORMEJTYCY_HELMET_SETTINGS);
		REINFORCED_INFORMEJTYCY_CHESTPLATE = InformejtycyRegistry.registerItem("reinforced_informejtycy_chestplate", REINFORCED_INFORMEJTYCY_CHESTPLATE_SETTINGS);
		REINFORCED_INFORMEJTYCY_LEGGINGS = InformejtycyRegistry.registerItem("reinforced_informejtycy_leggings", REINFORCED_INFORMEJTYCY_LEGGINGS_SETTINGS);
		REINFORCED_INFORMEJTYCY_BOOTS = InformejtycyRegistry.registerItem("reinforced_informejtycy_boots", REINFORCED_INFORMEJTYCY_BOOTS_SETTINGS);

        ZMYSIO_SWORD = InformejtycyRegistry.registerCustomItem("zmysio_sword", ZMYSIO_SWORD_SETTINGS, ZmysioSwordItem.class);
        ZMYSIO_ELYTRA = InformejtycyRegistry.registerCustomItem("zmysio_elytra", ZMYSIO_ELYTRA_SETTINGS.repairable(ZMYSIO_MILK_BUCKET), ZmysioElytraItem.class);
		PRESIDENT_HELMET = InformejtycyRegistry.registerCustomItem("president_helmet", PRESIDENT_HELMET_SETTINGS, PresidentHelmetItem.class);

		InformejtycyRegistry.registerAsFuel(STICKY_NOTES, 500);

		InformejtycyRegistry.registerMenuItem(ItemGroups.INGREDIENTS, SILVER_WOLF);
		InformejtycyRegistry.registerMenuItem(ItemGroups.INGREDIENTS, GOLDEN_WOLF);
		InformejtycyRegistry.registerMenuItem(ItemGroups.INGREDIENTS, BALLS_UNDER_MAGNIFIER);
		InformejtycyRegistry.registerMenuItem(ItemGroups.INGREDIENTS, PITCH_CONTEST_TROPHY);
		InformejtycyRegistry.registerMenuItem(ItemGroups.INGREDIENTS, STICKY_NOTES);
        InformejtycyRegistry.registerMenuItem(ItemGroups.FOOD_AND_DRINK, LIGHT_FOOD);
        InformejtycyRegistry.registerMenuItem(ItemGroups.FOOD_AND_DRINK, ZMYSIO_MILK_BUCKET);
        InformejtycyRegistry.registerMenuItem(ItemGroups.INGREDIENTS, DARK_GLOWSTONE_DUST);
        InformejtycyRegistry.registerMenuItem(ItemGroups.TOOLS, TALISMAN_OF_SHRIEK);
        InformejtycyRegistry.registerMenuItem(ItemGroups.INGREDIENTS, PLUS);
		InformejtycyRegistry.registerMenuItem(ItemGroups.INGREDIENTS, ZARZYK_GEL);
		InformejtycyRegistry.registerMenuItem(ItemGroups.INGREDIENTS, CONCENTRATED_ZARZYK_GEL);
		InformejtycyRegistry.registerMenuItem(ItemGroups.TOOLS, RIDE_THE_LIGHTNING_MUSIC_DISC);
		InformejtycyRegistry.registerMenuItem(ItemGroups.TOOLS, HOLY_WARS_MUSIC_DISC);
		InformejtycyRegistry.registerMenuItem(ItemGroups.TOOLS, YOU_MUST_BURN_MUSIC_DISC);
		InformejtycyRegistry.registerMenuItem(ItemGroups.TOOLS, NO_MORE_TEARS_MUSIC_DISC);
        InformejtycyRegistry.registerMenuItem(ItemGroups.TOOLS, ZALEWIX_BEAT_MUSIC_DISC);
		InformejtycyRegistry.registerMenuItem(ItemGroups.TOOLS, STELLA_MUSIC_DISC);
		InformejtycyRegistry.registerMenuItem(ItemGroups.COMBAT, INFORMEJTYCY_SWORD);
		InformejtycyRegistry.registerMenuItem(ItemGroups.TOOLS, INFORMEJTYCY_PICKAXE);
		InformejtycyRegistry.registerMenuItem(ItemGroups.TOOLS, INFORMEJTYCY_SHOVEL);
		InformejtycyRegistry.registerMenuItem(ItemGroups.TOOLS, INFORMEJTYCY_AXE);
		InformejtycyRegistry.registerMenuItem(ItemGroups.TOOLS, INFORMEJTYCY_HOE);
		InformejtycyRegistry.registerMenuItem(ItemGroups.COMBAT, INFORMEJTYCY_HELMET);
		InformejtycyRegistry.registerMenuItem(ItemGroups.COMBAT, INFORMEJTYCY_CHESTPLATE);
		InformejtycyRegistry.registerMenuItem(ItemGroups.COMBAT, INFORMEJTYCY_LEGGINGS);
		InformejtycyRegistry.registerMenuItem(ItemGroups.COMBAT, INFORMEJTYCY_BOOTS);
		InformejtycyRegistry.registerMenuItem(ItemGroups.COMBAT, REINFORCED_INFORMEJTYCY_SWORD);
		InformejtycyRegistry.registerMenuItem(ItemGroups.TOOLS, REINFORCED_INFORMEJTYCY_PICKAXE);
		InformejtycyRegistry.registerMenuItem(ItemGroups.TOOLS, REINFORCED_INFORMEJTYCY_SHOVEL);
		InformejtycyRegistry.registerMenuItem(ItemGroups.TOOLS, REINFORCED_INFORMEJTYCY_AXE);
		InformejtycyRegistry.registerMenuItem(ItemGroups.TOOLS, REINFORCED_INFORMEJTYCY_HOE);
		InformejtycyRegistry.registerMenuItem(ItemGroups.COMBAT, REINFORCED_INFORMEJTYCY_HELMET);
		InformejtycyRegistry.registerMenuItem(ItemGroups.COMBAT, REINFORCED_INFORMEJTYCY_CHESTPLATE);
		InformejtycyRegistry.registerMenuItem(ItemGroups.COMBAT, REINFORCED_INFORMEJTYCY_LEGGINGS);
		InformejtycyRegistry.registerMenuItem(ItemGroups.COMBAT, REINFORCED_INFORMEJTYCY_BOOTS);
        InformejtycyRegistry.registerMenuItem(ItemGroups.COMBAT, ZMYSIO_SWORD);
        InformejtycyRegistry.registerMenuItem(ItemGroups.TOOLS, ZMYSIO_ELYTRA);
		InformejtycyRegistry.registerMenuItem(ItemGroups.COMBAT, PRESIDENT_HELMET);
	}
}
