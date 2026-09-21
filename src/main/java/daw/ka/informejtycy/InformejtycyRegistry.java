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
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jspecify.annotations.NonNull;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

public class InformejtycyRegistry {
	public static void registerAll() {
		CustomBlocks.registerAll();
        CustomEntities.registerAll();
		CustomBlockEntities.registerAll();
		CustomItems.registerAll();
		CustomItemGroups.registerAll();
		CustomSounds.registerAll();
		CustomRecipes.registerAll();
		CustomEnchantmentEffects.registerAll();
		CustomEffects.registerAll();
		CustomPotions.registerAll();
        CustomParticles.registerAll();
	}

	public static Block registerBlock(String name, BlockBehaviour.Properties blockSettings, Item.Properties blockItemSettings) {
		Identifier id = id(name);
		ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, id);
		BlockBehaviour.Properties settings = blockSettings.setId(key);
		Block block = new Block(settings);
		registerBlockItem(name, block, blockItemSettings);
		return Registry.register(BuiltInRegistries.BLOCK, key, block);
	}

	public static <T extends Block> Block registerCustomBlock(String name, BlockBehaviour.Properties blockSettings, Item.Properties blockItemSettings, Class<T> customBlock) {
		Identifier id = id(name);
		ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, id);
		BlockBehaviour.Properties settings = blockSettings.setId(key);
		try {
			Block block = customBlock.getDeclaredConstructor(BlockBehaviour.Properties.class).newInstance(settings);
			registerBlockItem(name, block, blockItemSettings);
			return Registry.register(BuiltInRegistries.BLOCK, key, block);
		} catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

    public static <T extends Entity> EntityType<T> registerMob(String name, EntityType.EntityFactory<T> factory,
                                                                  MobCategory spawnGroup, float width, float height) {
        Identifier id = id(name);
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, id);
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, EntityType.Builder.of(factory, spawnGroup)
                .sized(width, height).build(key));
    }

	public static <T extends BlockEntity> BlockEntityType<T> registerCustomBlockEntity(String name, BlockEntityType<T> type) {
		Identifier id = id(name);
		ResourceKey<BlockEntityType<?>> key = ResourceKey.create(Registries.BLOCK_ENTITY_TYPE, id);
		return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, key, type);
	}

	public static void registerBlockItem(String name, Block block, Item.Properties blockItemSettings) {
		Identifier id = id(name);
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, id);
		Item.Properties settings = blockItemSettings.setId(key);
		Registry.register(BuiltInRegistries.ITEM, key, new BlockItem(block, settings));
	}

	public static Item registerItem(String name, Item.Properties itemSettings) {
		Identifier id = id(name);
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, id);
		Item.Properties settings = itemSettings.setId(key);
		return Registry.register(BuiltInRegistries.ITEM, key, new Item(settings));
	}

	public static <T extends Item> Item registerCustomItem(String name, Item.Properties itemSettings, Class<T> customItem) {
		Identifier id = id(name);
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, id);
		Item.Properties settings = itemSettings.setId(key);
		try {
			return Registry.register(BuiltInRegistries.ITEM, key, customItem.getDeclaredConstructor(Item.Properties.class).newInstance(settings));
		} catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	public static SoundEvent registerSoundEvent(String name) {
		Identifier id = id(name);
		ResourceKey<SoundEvent> key = ResourceKey.create(Registries.SOUND_EVENT, id);
		SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(id);
		return Registry.register(BuiltInRegistries.SOUND_EVENT, key, soundEvent);
	}

	public static MapCodec<? extends EnchantmentEntityEffect> registerEntityEffect(String name, MapCodec<? extends EnchantmentEntityEffect> codec) {
		Identifier id = id(name);
		return Registry.register(BuiltInRegistries.ENCHANTMENT_ENTITY_EFFECT_TYPE, id, codec);
	}

	public static Holder<MobEffect> registerStatusEffect(String name, MobEffect effect) {
		Identifier id = id(name);
		return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, id, effect);
	}

	public static Holder<Potion> registerPotion(String name, Potion potion) {
		Identifier id = id(name);
		return Registry.registerForHolder(BuiltInRegistries.POTION, id, potion);
	}

	public static CreativeModeTab registerMenuItemGroup(String name, CreativeModeTab group) {
		Identifier id = id(name);
		return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id, group);
	}

	public static void registerMenuItem(ResourceKey<CreativeModeTab> group, Item item) {
		CreativeModeTabEvents.modifyOutputEvent(group).register(output -> output.accept(item));
	}

	public static void registerMenuBlock(ResourceKey<CreativeModeTab> group, Block block) {
		CreativeModeTabEvents.modifyOutputEvent(group).register(output -> output.accept(block));
	}

    public static <T extends ParticleType<?>> T registerParticleType(String name, T particleType) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, id(name), particleType);
    }

    public static <T extends ParticleOptions> ParticleType<T> registerParticleType(
            String name, boolean alwaysShow, Function<ParticleType<T>, MapCodec<T>> codecGetter,
            Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> packetCodecGetter) {
        Identifier id = id(name);
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, id, new ParticleType<T>(alwaysShow) {
            @Override
            public @NonNull MapCodec<T> codec() { return codecGetter.apply(this); }
            @Override
            public @NonNull StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() { return packetCodecGetter.apply(this); }
        });
    }

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(Informejtycy.MOD_ID, path);
	}
}
