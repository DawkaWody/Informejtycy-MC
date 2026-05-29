package daw.ka.informejtycy;

import com.mojang.serialization.MapCodec;
import daw.ka.informejtycy.block.CustomBlocks;
import daw.ka.informejtycy.block.entity.CustomBlockEntities;
import daw.ka.informejtycy.enchantment.CustomEnchantmentEffects;
import daw.ka.informejtycy.entity.CustomEntities;
import daw.ka.informejtycy.item.CustomItemGroups;
import daw.ka.informejtycy.item.CustomItems;
import daw.ka.informejtycy.particle.CustomParticles;
import daw.ka.informejtycy.potion.CustomPotions;
import daw.ka.informejtycy.potion.effect.CustomEffects;
import daw.ka.informejtycy.recipe.CustomRecipes;
import daw.ka.informejtycy.sound.CustomSounds;
import daw.ka.informejtycy.villager.CustomVillagerTrades;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

public class InformejtycyRegistry {
	public static void registerAll() {
		CustomBlocks.registerAll();
        CustomEntities.registerAll();
		CustomBlockEntities.registerAll();
		CustomItems.registerAll();
		CustomItemGroups.registerAll();
		CustomVillagerTrades.registerAll();
		CustomSounds.registerAll();
		CustomRecipes.registerAll();
		CustomEnchantmentEffects.registerAll();
		CustomEffects.registerAll();
		CustomPotions.registerAll();
        CustomParticles.registerAll();
	}

	public static Block registerBlock(String name, AbstractBlock.Settings blockSettings, Item.Settings blockItemSettings) {
		Identifier id = id(name);
		RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, id);
		AbstractBlock.Settings settings = blockSettings.registryKey(key);
		Block block = new Block(settings);
		registerBlockItem(name, block, blockItemSettings);
		return Registry.register(Registries.BLOCK, key, block);
	}

	public static <T extends Block> Block registerCustomBlock(String name, AbstractBlock.Settings blockSettings, Item.Settings blockItemSettings, Class<T> customBlock) {
		Identifier id = id(name);
		RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, id);
		AbstractBlock.Settings settings = blockSettings.registryKey(key);
		try {
			Block block = customBlock.getDeclaredConstructor(AbstractBlock.Settings.class).newInstance(settings);
			registerBlockItem(name, block, blockItemSettings);
			return Registry.register(Registries.BLOCK, key, block);
		} catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

    public static <T extends Entity> EntityType<T> registerEntity(String name, EntityType.EntityFactory<T> factory,
                                                                  SpawnGroup spawnGroup, float width, float height) {
        Identifier id = id(name);
        RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, id);
        return Registry.register(Registries.ENTITY_TYPE, key, EntityType.Builder.create(factory, spawnGroup)
                .dimensions(width, height).build(key));
    }

	public static <T extends BlockEntity> BlockEntityType<T> registerCustomBlockEntity(String name, BlockEntityType<T> type) {
		Identifier id = id(name);
		RegistryKey<BlockEntityType<?>> key = RegistryKey.of(RegistryKeys.BLOCK_ENTITY_TYPE, id);
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, key, type);
	}

	public static void registerBlockItem(String name, Block block, Item.Settings blockItemSettings) {
		Identifier id = id(name);
		RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);
		Item.Settings settings = blockItemSettings.registryKey(key);
		Registry.register(Registries.ITEM, key, new BlockItem(block, settings));
	}

	public static Item registerItem(String name, Item.Settings itemSettings) {
		Identifier id = id(name);
		RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);
		Item.Settings settings = itemSettings.registryKey(key);
		return Registry.register(Registries.ITEM, key, new Item(settings));
	}

	public static <T extends Item> Item registerCustomItem(String name, Item.Settings itemSettings, Class<T> customItem) {
		Identifier id = id(name);
		RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);
		Item.Settings settings = itemSettings.registryKey(key);
		try {
			return Registry.register(Registries.ITEM, key, customItem.getDeclaredConstructor(Item.Settings.class).newInstance(settings));
		} catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	public static SoundEvent registerSoundEvent(String name) {
		Identifier id = id(name);
		RegistryKey<SoundEvent> key = RegistryKey.of(RegistryKeys.SOUND_EVENT, id);
		SoundEvent soundEvent = SoundEvent.of(id);
		return Registry.register(Registries.SOUND_EVENT, key, soundEvent);
	}

	public static MapCodec<? extends EnchantmentEntityEffect> registerEntityEffect(String name, MapCodec<? extends EnchantmentEntityEffect> codec) {
		Identifier id = id(name);
		return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, id, codec);
	}

	public static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect effect) {
		Identifier id = id(name);
		return Registry.registerReference(Registries.STATUS_EFFECT, id, effect);
	}

	public static RegistryEntry<Potion> registerPotion(String name, Potion potion) {
		Identifier id = id(name);
		return Registry.registerReference(Registries.POTION, id, potion);
	}

	public static ItemGroup registerMenuItemGroup(String name, ItemGroup group) {
		Identifier id = id(name);
		return Registry.register(Registries.ITEM_GROUP, id, group);
	}

	public static void registerMenuItem(RegistryKey<ItemGroup> group, Item item) {
		ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
	}

	public static void registerMenuBlock(RegistryKey<ItemGroup> group, Block block) {
		ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(block));
	}

	public static void registerAsFuel(Item item, int burnTime) {
		FuelRegistryEvents.BUILD.register((builder, event) -> builder.add(item, burnTime));
	}

    public static <T extends ParticleType<?>> T registerParticleType(String name, T particleType) {
        return Registry.register(Registries.PARTICLE_TYPE, id(name), particleType);
    }

    public static <T extends ParticleEffect> ParticleType<T> registerParticleType(
            String name, boolean alwaysShow, Function<ParticleType<T>, MapCodec<T>> codecGetter,
            Function<ParticleType<T>, PacketCodec<? super RegistryByteBuf, T>> packetCodecGetter) {
        Identifier id = id(name);
        return Registry.register(Registries.PARTICLE_TYPE, id, new ParticleType<T>(alwaysShow) {
            @Override
            public MapCodec<T> getCodec() { return codecGetter.apply(this); }
            @Override
            public PacketCodec<? super RegistryByteBuf, T> getPacketCodec() { return packetCodecGetter.apply(this); }
        });
    }

	public static Identifier id(String path) {
		return Identifier.of(Informejtycy.MOD_ID, path);
	}
}
