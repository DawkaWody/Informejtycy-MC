package daw.ka.informejtycy.item;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.item.custom.*;
import daw.ka.informejtycy.item.material.CustomArmorMaterials;
import daw.ka.informejtycy.item.material.CustomToolMaterials;
import daw.ka.informejtycy.sound.CustomSounds;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.component.CookingFuel;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Rarity;
import net.minecraft.util.Unit;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;

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
	public static Item RECYCLABLE_BOTTLE;
	// Music discs
	public static Item RIDE_THE_LIGHTNING_MUSIC_DISC;
	public static Item HOLY_WARS_MUSIC_DISC;
	public static Item YOU_MUST_BURN_MUSIC_DISC;
	public static Item NO_MORE_TEARS_MUSIC_DISC;
    public static Item ZALEWIX_BEAT_MUSIC_DISC;
	public static Item STELLA_MUSIC_DISC;
	public static Item MEGAMIKSKLASA2_MUSIC_DISC;
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

	public static final Item.Properties SILVER_WOLF_SETTINGS = new Item.Properties().stacksTo(64);
	public static final Item.Properties GOLDEN_WOLF_SETTINGS = new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.UNCOMMON);
	public static final Item.Properties BALLS_UNDER_MAGNIFIER_SETTINGS = new Item.Properties().stacksTo(16);
	public static final Item.Properties PITCH_CONTEST_TROPHY_SETTINGS = new Item.Properties().stacksTo(4).fireResistant().rarity(Rarity.UNCOMMON);
	public static final Item.Properties STICKY_NOTES_SETTINGS = new Item.Properties().stacksTo(99).component(DataComponents.COOKING_FUEL, new CookingFuel(ResourceKey.create(Registries.CONTEXT_INT_PROVIDER, InformejtycyRegistry.id("sticky_notes_burn_time")), ContextFloatProviders.COOKING_DEFAULT_SPEED_MULTIPLIER));
    public static final Item.Properties LIGHT_FOOD_SETTINGS = new Item.Properties().food(CustomFoodComponents.LIGHT_FOOD, CustomFoodComponents.LIGHT_FOOD_CONSUMABLE);
    public static final Item.Properties ZMYSIO_MILK_BUCKET_SETTINGS = new Item.Properties().stacksTo(1).usingConvertsTo(Items.BUCKET).craftRemainder(Items.BUCKET).food(CustomFoodComponents.ZMYSIO_MILK, CustomFoodComponents.ZMYSIO_MILK_CONSUMABLE).rarity(Rarity.UNCOMMON);
	public static final Item.Properties ZARZYK_GEL_SETTINGS = new Item.Properties().stacksTo(1).usingConvertsTo(Items.GLASS_BOTTLE).craftRemainder(Items.GLASS_BOTTLE).food(CustomFoodComponents.ZARZYK_GEL, CustomFoodComponents.ZARZYK_GEL_CONSUMABLE);
	public static final Item.Properties CONCENTRATED_ZARZYK_GEL_SETTINGS = new Item.Properties().stacksTo(1).craftRemainder(Items.GLASS_BOTTLE);
	public static final Item.Properties DARK_GLOWSTONE_DUST_SETTINGS = new Item.Properties().stacksTo(64);
    public static final Item.Properties TALISMAN_OF_SHRIEK_SETTINGS = new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON);
    public static final Item.Properties PLUS_SETTINGS = new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.UNCOMMON);
	public static final Item.Properties RECYCLABLE_BOTTLE_SETTINGS = new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON);
    // Music discs
	public static final Item.Properties RIDE_THE_LIGHTNING_MUSIC_DISC_SETTINGS = new Item.Properties().jukeboxPlayable(CustomSounds.RIDE_THE_LIGHTNING_KEY).stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON);
	public static final Item.Properties HOLY_WARS_MUSIC_DISC_SETTINGS = new Item.Properties().jukeboxPlayable(CustomSounds.HOLY_WARS_KEY).stacksTo(1).rarity(Rarity.UNCOMMON);
	public static final Item.Properties YOU_MUST_BURN_MUSIC_DISC_SETTINGS = new Item.Properties().jukeboxPlayable(CustomSounds.YOU_MUST_BURN_KEY).stacksTo(1).rarity(Rarity.UNCOMMON);
	public static final Item.Properties NO_MORE_TEARS_MUSIC_DISC_SETTINGS = new Item.Properties().jukeboxPlayable(CustomSounds.NO_MORE_TEARS_KEY).stacksTo(1).rarity(Rarity.UNCOMMON);
	public static final Item.Properties ZALEWIX_BEAT_MUSIC_DISC_SETTINGS = new Item.Properties().jukeboxPlayable(CustomSounds.ZALEWIX_BEAT_KEY).stacksTo(1).rarity(Rarity.UNCOMMON);
    public static final Item.Properties STELLA_MUSIC_DISC_SETTINGS = new Item.Properties().jukeboxPlayable(CustomSounds.STELLA_KEY).stacksTo(1).rarity(Rarity.UNCOMMON);
	public static final Item.Properties MEGAMIKSKLASA2_MUSIC_DISC_SETTINGS = new Item.Properties().jukeboxPlayable(CustomSounds.MEGAMIKSKLASA2_KEY).stacksTo(1).rarity(Rarity.UNCOMMON);
	// Informejtycy toolset
	public static final Item.Properties INFORMEJTYCY_SWORD_SETTINGS = new Item.Properties().sword(CustomToolMaterials.INFORMEJTYCY, 3, -2.4F);
	public static final Item.Properties INFORMEJTYCY_PICKAXE_SETTINGS = new Item.Properties().pickaxe(CustomToolMaterials.INFORMEJTYCY, 1, -2.8F);
	public static final Item.Properties INFORMEJTYCY_SHOVEL_SETTINGS = new Item.Properties().shovel(CustomToolMaterials.INFORMEJTYCY, 1.5F, -3.0F);
	public static final Item.Properties INFORMEJTYCY_AXE_SETTINGS = new Item.Properties().axe(CustomToolMaterials.INFORMEJTYCY, 5.0F, -3.0F);
	public static final Item.Properties INFORMEJTYCY_HOE_SETTINGS = new Item.Properties().hoe(CustomToolMaterials.INFORMEJTYCY, -4.0f, 0);
	// Informejtycy armor
	public static final Item.Properties INFORMEJTYCY_HELMET_SETTINGS = new Item.Properties().humanoidArmor(CustomArmorMaterials.INFORMEJTYCY, ArmorType.HELMET);
	public static final Item.Properties INFORMEJTYCY_CHESTPLATE_SETTINGS = new Item.Properties().humanoidArmor(CustomArmorMaterials.INFORMEJTYCY, ArmorType.CHESTPLATE);
	public static final Item.Properties INFORMEJTYCY_LEGGINGS_SETTINGS = new Item.Properties().humanoidArmor(CustomArmorMaterials.INFORMEJTYCY, ArmorType.LEGGINGS);
	public static final Item.Properties INFORMEJTYCY_BOOTS_SETTINGS = new Item.Properties().humanoidArmor(CustomArmorMaterials.INFORMEJTYCY, ArmorType.BOOTS);
	// Reinforced Informejtycy toolset
	public static final Item.Properties REINFORCED_INFORMEJTYCY_SWORD_SETTINGS = new Item.Properties().sword(CustomToolMaterials.REINFORCED_INFORMEJTYCY, 3, -2.4F);
	public static final Item.Properties REINFORCED_INFORMEJTYCY_PICKAXE_SETTINGS = new Item.Properties().pickaxe(CustomToolMaterials.REINFORCED_INFORMEJTYCY, 1, -2.8F);
	public static final Item.Properties REINFORCED_INFORMEJTYCY_SHOVEL_SETTINGS = new Item.Properties().shovel(CustomToolMaterials.REINFORCED_INFORMEJTYCY, 1.5F, -3.0F);
	public static final Item.Properties REINFORCED_INFORMEJTYCY_AXE_SETTINGS = new Item.Properties().axe(CustomToolMaterials.REINFORCED_INFORMEJTYCY, 5.0F, -3.0F);
	public static final Item.Properties REINFORCED_INFORMEJTYCY_HOE_SETTINGS = new Item.Properties().hoe(CustomToolMaterials.REINFORCED_INFORMEJTYCY, -4.0f, 0);
	// Reinforced Informejtycy armor
	public static final Item.Properties REINFORCED_INFORMEJTYCY_HELMET_SETTINGS = new Item.Properties().humanoidArmor(CustomArmorMaterials.REINFORCED_INFORMEJTYCY, ArmorType.HELMET);
	public static final Item.Properties REINFORCED_INFORMEJTYCY_CHESTPLATE_SETTINGS = new Item.Properties().humanoidArmor(CustomArmorMaterials.REINFORCED_INFORMEJTYCY, ArmorType.CHESTPLATE);
	public static final Item.Properties REINFORCED_INFORMEJTYCY_LEGGINGS_SETTINGS = new Item.Properties().humanoidArmor(CustomArmorMaterials.REINFORCED_INFORMEJTYCY, ArmorType.LEGGINGS);
	public static final Item.Properties REINFORCED_INFORMEJTYCY_BOOTS_SETTINGS = new Item.Properties().humanoidArmor(CustomArmorMaterials.REINFORCED_INFORMEJTYCY, ArmorType.BOOTS);

    public static final Item.Properties ZMYSIO_SWORD_SETTINGS = new Item.Properties().sword(CustomToolMaterials.ZMYSIO, 3, -2.4F).rarity(Rarity.RARE);
    public static final Item.Properties ZMYSIO_ELYTRA_SETTINGS = new Item.Properties().durability(676).rarity(Rarity.EPIC)
            .component(DataComponents.GLIDER, Unit.INSTANCE)
            .component(
                    DataComponents.EQUIPPABLE,
                    Equippable.builder(EquipmentSlot.CHEST)
                            .setEquipSound(SoundEvents.ARMOR_EQUIP_ELYTRA)
                            .setAsset(CustomArmorMaterials.ZMYSIO_ELYTRA_KEY)
                            .setDamageOnHurt(false)
                            .build()
            );
	public static final Item.Properties PRESIDENT_HELMET_SETTINGS = new Item.Properties().humanoidArmor(CustomArmorMaterials.PRESIDENT_HELMET, ArmorType.HELMET);

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
		RECYCLABLE_BOTTLE = InformejtycyRegistry.registerItem("recyclable_bottle", RECYCLABLE_BOTTLE_SETTINGS);
        // Music discs
		RIDE_THE_LIGHTNING_MUSIC_DISC = InformejtycyRegistry.registerItem("music_disc_ride_the_lightning", RIDE_THE_LIGHTNING_MUSIC_DISC_SETTINGS);
		HOLY_WARS_MUSIC_DISC = InformejtycyRegistry.registerItem("music_disc_holy_wars", HOLY_WARS_MUSIC_DISC_SETTINGS);
		YOU_MUST_BURN_MUSIC_DISC = InformejtycyRegistry.registerItem("music_disc_you_must_burn", YOU_MUST_BURN_MUSIC_DISC_SETTINGS);
		NO_MORE_TEARS_MUSIC_DISC = InformejtycyRegistry.registerItem("music_disc_no_more_tears", NO_MORE_TEARS_MUSIC_DISC_SETTINGS);
		ZALEWIX_BEAT_MUSIC_DISC = InformejtycyRegistry.registerItem("music_disc_zalewix_beat", ZALEWIX_BEAT_MUSIC_DISC_SETTINGS);
		STELLA_MUSIC_DISC = InformejtycyRegistry.registerItem("music_disc_stella", STELLA_MUSIC_DISC_SETTINGS);
		MEGAMIKSKLASA2_MUSIC_DISC = InformejtycyRegistry.registerItem("music_disc_megamiksklasa2", MEGAMIKSKLASA2_MUSIC_DISC_SETTINGS);
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

		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.INGREDIENTS, SILVER_WOLF);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.INGREDIENTS, GOLDEN_WOLF);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.INGREDIENTS, BALLS_UNDER_MAGNIFIER);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.INGREDIENTS, PITCH_CONTEST_TROPHY);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.INGREDIENTS, STICKY_NOTES);
        InformejtycyRegistry.registerMenuItem(CreativeModeTabs.FOOD_AND_DRINKS, LIGHT_FOOD);
        InformejtycyRegistry.registerMenuItem(CreativeModeTabs.FOOD_AND_DRINKS, ZMYSIO_MILK_BUCKET);
        InformejtycyRegistry.registerMenuItem(CreativeModeTabs.INGREDIENTS, DARK_GLOWSTONE_DUST);
        InformejtycyRegistry.registerMenuItem(CreativeModeTabs.TOOLS_AND_UTILITIES, TALISMAN_OF_SHRIEK);
        InformejtycyRegistry.registerMenuItem(CreativeModeTabs.INGREDIENTS, PLUS);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.INGREDIENTS, ZARZYK_GEL);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.INGREDIENTS, CONCENTRATED_ZARZYK_GEL);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.TOOLS_AND_UTILITIES, RIDE_THE_LIGHTNING_MUSIC_DISC);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.TOOLS_AND_UTILITIES, MEGAMIKSKLASA2_MUSIC_DISC);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.TOOLS_AND_UTILITIES, HOLY_WARS_MUSIC_DISC);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.TOOLS_AND_UTILITIES, YOU_MUST_BURN_MUSIC_DISC);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.TOOLS_AND_UTILITIES, NO_MORE_TEARS_MUSIC_DISC);
        InformejtycyRegistry.registerMenuItem(CreativeModeTabs.TOOLS_AND_UTILITIES, ZALEWIX_BEAT_MUSIC_DISC);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.TOOLS_AND_UTILITIES, STELLA_MUSIC_DISC);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.COMBAT, INFORMEJTYCY_SWORD);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.TOOLS_AND_UTILITIES, INFORMEJTYCY_PICKAXE);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.TOOLS_AND_UTILITIES, INFORMEJTYCY_SHOVEL);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.TOOLS_AND_UTILITIES, INFORMEJTYCY_AXE);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.TOOLS_AND_UTILITIES, INFORMEJTYCY_HOE);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.COMBAT, INFORMEJTYCY_HELMET);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.COMBAT, INFORMEJTYCY_CHESTPLATE);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.COMBAT, INFORMEJTYCY_LEGGINGS);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.COMBAT, INFORMEJTYCY_BOOTS);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.COMBAT, REINFORCED_INFORMEJTYCY_SWORD);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.TOOLS_AND_UTILITIES, REINFORCED_INFORMEJTYCY_PICKAXE);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.TOOLS_AND_UTILITIES, REINFORCED_INFORMEJTYCY_SHOVEL);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.TOOLS_AND_UTILITIES, REINFORCED_INFORMEJTYCY_AXE);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.TOOLS_AND_UTILITIES, REINFORCED_INFORMEJTYCY_HOE);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.COMBAT, REINFORCED_INFORMEJTYCY_HELMET);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.COMBAT, REINFORCED_INFORMEJTYCY_CHESTPLATE);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.COMBAT, REINFORCED_INFORMEJTYCY_LEGGINGS);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.COMBAT, REINFORCED_INFORMEJTYCY_BOOTS);
        InformejtycyRegistry.registerMenuItem(CreativeModeTabs.COMBAT, ZMYSIO_SWORD);
        InformejtycyRegistry.registerMenuItem(CreativeModeTabs.TOOLS_AND_UTILITIES, ZMYSIO_ELYTRA);
		InformejtycyRegistry.registerMenuItem(CreativeModeTabs.COMBAT, PRESIDENT_HELMET);
	}
}
