package daw.ka.informejtycy.block;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.block.custom.BrainrotTableBlock;
import daw.ka.informejtycy.block.custom.FacingBlock;
import daw.ka.informejtycy.block.custom.RecyclerBlock;
import daw.ka.informejtycy.block.custom.TheoryForgeBlock;
import daw.ka.informejtycy.block.custom.ZmysioSummoningBlock;
import daw.ka.informejtycy.world.tree.CustomTreeGrowers;
import net.fabricmc.fabric.api.item.v1.BlockTransformerHelper;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.sounds.AmbientLeavesBlockSoundPlayer;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.material.PushReaction;

public class CustomBlocks {
	public static Block SILVER_WOLF_ORE;
	public static Block DARK_GLOWSTONE;
	public static Block THEORY_FORGE_BLOCK;
	public static Block BRAINROT_TABLE_BLOCK;
	public static Block RECYCLER_BLOCK;
    public static Block GLINIANKA_BLOCK;
    public static Block TRASH_CAN;
    public static Block SILVER_WOLF_BLOCK;
    public static Block GOLDEN_WOLF_BLOCK;
    public static Block ZMYSIO_SUMMON_ANCHOR;
	// Brainrots
	public static Block BOMBARDINO_COCODRILO;
	public static Block TRALALERO_TRALALA;
	public static Block TUNG_TUNG_SAHUR;
	public static Block CHIMPANZINI_BANANINI;
	// Wood
	public static Block ISTANBUL_LOG;
	public static Block ISTANBUL_WOOD;
	public static Block STRIPPED_ISTANBUL_LOG;
	public static Block STRIPPED_ISTANBUL_WOOD;
	public static Block ISTANBUL_PLANKS;
	public static Block ISTANBUL_STAIRS;
	public static Block ISTANBUL_SLAB;
	public static Block ISTANBUL_FENCE;
	public static Block ISTANBUL_FENCE_GATE;
	public static Block ISTANBUL_PRESSURE_PLATE;
	public static Block ISTANBUL_BUTTON;
	public static Block ISTANBUL_TRAPDOOR;
	public static Block ISTANBUL_DOOR;
	public static Block ISTANBUL_LEAVES;
	public static Block ISTANBUL_SAPLING;
	public static Block POTTED_ISTANBUL_SAPLING;
	public static Block ZARZYK_LOG;
	public static Block ZARZYK_WOOD;
	public static Block STRIPPED_ZARZYK_LOG;
	public static Block STRIPPED_ZARZYK_WOOD;
	public static Block ZARZYK_PLANKS;
	public static Block ZARZYK_STAIRS;
	public static Block ZARZYK_SLAB;
	public static Block ZARZYK_FENCE;
	public static Block ZARZYK_FENCE_GATE;
	public static Block ZARZYK_PRESSURE_PLATE;
	public static Block ZARZYK_BUTTON;
	public static Block ZARZYK_TRAPDOOR;
	public static Block ZARZYK_DOOR;
	public static Block ZARZYK_LEAVES;
	public static Block ZARZYK_SAPLING;
	public static Block POTTED_ZARZYK_SAPLING;

	public static final BlockBehaviour.Properties SILVER_WOLF_ORE_SETTINGS = BlockBehaviour.Properties.of()
			.strength(4.0f, 1200f)
			.requiresCorrectToolForDrops()
			.mapColor(MapColor.SAND)
			.lightLevel(_ -> 7);
	public static final BlockBehaviour.Properties DARK_GLOWSTONE_SETTINGS = BlockBehaviour.Properties.of()
			.strength(0.3F)
			.sound(SoundType.GLASS)
			.lightLevel(_ -> 10)
			.isRedstoneConductor(Blocks::never);
	public static final BlockBehaviour.Properties THEORY_FORGE_BLOCK_SETTINGS = BlockBehaviour.Properties.of()
			.strength(3.5f, 3.0f)
			.requiresCorrectToolForDrops()
			.sound(SoundType.STONE)
			.noOcclusion();
	public static final BlockBehaviour.Properties BRAINROT_TABLE_BLOCK_SETTINGS = BlockBehaviour.Properties.of()
			.strength(2f, 3.0f)
			.requiresCorrectToolForDrops()
			.sound(SoundType.WOOD)
			.noOcclusion();
	public static final BlockBehaviour.Properties RECYCLER_BLOCK_SETTINGS = BlockBehaviour.Properties.of()
			.strength(50.0F, 3600000.0F)
			.requiresCorrectToolForDrops()
			.sound(SoundType.STONE)
			.noOcclusion();
    public static final BlockBehaviour.Properties GLINIANKA_BLOCK_SETTINGS = BlockBehaviour.Properties.of()
            .strength(0.6f)
            .sound(SoundType.GRAVEL)
            .mapColor(MapColor.COLOR_BROWN);
    public static final BlockBehaviour.Properties TRASH_CAN_SETTINGS = BlockBehaviour.Properties.of()
            .strength(2.0f)
            .sound(SoundType.METAL)
            .noOcclusion();
    public static final BlockBehaviour.Properties SILVER_WOLF_BLOCK_SETTINGS = BlockBehaviour.Properties.of()
            .strength(5f, 6.0f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.METAL)
            .noOcclusion();
    public static final BlockBehaviour.Properties GOLDEN_WOLF_BLOCK_SETTINGS = BlockBehaviour.Properties.of()
            .strength(3f, 12.0f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.METAL)
            .noOcclusion();
	// Brainrots
	public static final BlockBehaviour.Properties BOMBARDINO_COCODRILO_SETTINGS = BlockBehaviour.Properties.of()
			.strength(40f, 4.0f)
			.requiresCorrectToolForDrops()
			.sound(SoundType.IRON)
			.noOcclusion();
	public static final BlockBehaviour.Properties TRALALERO_TRALALA_SETTINGS = BlockBehaviour.Properties.of()
			.strength(25f, 4.0f)
			.requiresCorrectToolForDrops()
			.sound(SoundType.WET_SPONGE)
			.noOcclusion();
	public static final BlockBehaviour.Properties TUNG_TUNG_SAHUR_SETTINGS = BlockBehaviour.Properties.of()
			.strength(30f, 4.0f)
			.requiresCorrectToolForDrops()
			.sound(SoundType.WOOD)
			.noOcclusion();
	public static final BlockBehaviour.Properties CHIMPANZINI_BANANINI_SETTINGS = BlockBehaviour.Properties.of()
			.strength(30f, 4.0f)
			.requiresCorrectToolForDrops()
			.sound(SoundType.BIG_DRIPLEAF)
			.noOcclusion();
	// Wood
	public static final BlockBehaviour.Properties ISTANBUL_LOG_SETTINGS = Blocks.logProperties(MapColor.WOOD, MapColor.PODZOL, SoundType.BAMBOO_WOOD);
	public static final BlockBehaviour.Properties ISTANBUL_WOOD_SETTINGS = BlockBehaviour.Properties.of()
			.mapColor(MapColor.WOOD)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2.0F)
			.sound(SoundType.BAMBOO_WOOD)
			.noOcclusion()
			.ignitedByLava();
	public static final BlockBehaviour.Properties STRIPPED_ISTANBUL_LOG_SETTINGS = Blocks.logProperties(MapColor.WOOD, MapColor.WOOD, SoundType.BAMBOO_WOOD);
	public static final BlockBehaviour.Properties STRIPPED_ISTANBUL_WOOD_SETTINGS = BlockBehaviour.Properties.of()
			.mapColor(MapColor.WOOD)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2.0F)
			.sound(SoundType.BAMBOO_WOOD)
			.noOcclusion()
			.ignitedByLava();
	public static final BlockBehaviour.Properties ISTANBUL_PLANKS_SETTINGS = BlockBehaviour.Properties.of()
			.mapColor(MapColor.WOOD)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2.0F)
			.sound(SoundType.BAMBOO_WOOD)
			.noOcclusion()
			.ignitedByLava();
	public static final BlockBehaviour.Properties ISTANBUL_STAIRS_SETTINGS = BlockBehaviour.Properties.of()
			.mapColor(MapColor.WOOD)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2.0F, 3.0F)
			.sound(SoundType.BAMBOO_WOOD)
			.ignitedByLava();
	public static final BlockBehaviour.Properties ISTANBUL_SLAB_SETTINGS = BlockBehaviour.Properties.of()
			.mapColor(MapColor.WOOD)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2.0F, 3.0F)
			.sound(SoundType.BAMBOO_WOOD)
			.ignitedByLava();
	public static final BlockBehaviour.Properties ISTANBUL_FENCE_SETTINGS = BlockBehaviour.Properties.of()
			.mapColor(MapColor.WOOD)
			.forceSolidOn()
			.instrument(NoteBlockInstrument.BASS)
			.strength(2.0F, 3.0F)
			.sound(SoundType.BAMBOO_WOOD)
			.ignitedByLava();
	public static final BlockBehaviour.Properties ISTANBUL_FENCE_GATE_SETTINGS = BlockBehaviour.Properties.of()
			.mapColor(MapColor.WOOD)
			.forceSolidOn()
			.instrument(NoteBlockInstrument.BASS)
			.strength(2.0F, 3.0F)
			.ignitedByLava();
	public static final BlockBehaviour.Properties ISTANBUL_PRESSURE_PLATE_SETTINGS = BlockBehaviour.Properties.of()
			.mapColor(MapColor.WOOD)
			.forceSolidOn()
			.instrument(NoteBlockInstrument.BASS)
			.noCollision()
			.strength(0.5F)
			.ignitedByLava()
			.pushReaction(PushReaction.POPPED);
	public static final BlockBehaviour.Properties ISTANBUL_BUTTON_SETTINGS = Blocks.buttonProperties();
	public static final BlockBehaviour.Properties ISTANBUL_TRAPDOOR_SETTINGS = BlockBehaviour.Properties.of()
			.mapColor(MapColor.WOOD)
			.instrument(NoteBlockInstrument.BASS)
			.strength(3.0F)
			.noOcclusion()
			.isValidSpawn(Blocks::never)
			.ignitedByLava();
	public static final BlockBehaviour.Properties ISTANBUL_DOOR_SETTINGS = BlockBehaviour.Properties.of()
			.mapColor(MapColor.WOOD)
			.instrument(NoteBlockInstrument.BASS)
			.strength(3.0F)
			.noOcclusion()
			.ignitedByLava()
			.pushReaction(PushReaction.POPPED);
	public static final BlockBehaviour.Properties ISTANBUL_LEAVES_SETTINGS = Blocks.leavesProperties(SoundType.POPLAR_LEAVES);
	public static final BlockBehaviour.Properties ISTANBUL_SAPLING_SETTINGS = BlockBehaviour.Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.randomTicks()
			.instabreak()
			.sound(SoundType.GRASS)
			.pushReaction(PushReaction.POPPED);
	public static final BlockBehaviour.Properties POTTED_ISTANBUL_SAPLING_SETTINGS = BlockBehaviour.Properties.of()
			.instabreak()
			.noOcclusion()
			.pushReaction(PushReaction.POPPED);
	public static final BlockBehaviour.Properties ZARZYK_LOG_SETTINGS = Blocks.logProperties(MapColor.WOOD, MapColor.PODZOL, SoundType.WOOD);
	public static final BlockBehaviour.Properties ZARZYK_WOOD_SETTINGS = BlockBehaviour.Properties.of()
			.mapColor(MapColor.WOOD)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2.0F)
			.sound(SoundType.WOOD)
			.noOcclusion()
			.ignitedByLava();
	public static final BlockBehaviour.Properties STRIPPED_ZARZYK_LOG_SETTINGS = Blocks.logProperties(MapColor.WOOD, MapColor.WOOD, SoundType.WOOD);
	public static final BlockBehaviour.Properties STRIPPED_ZARZYK_WOOD_SETTINGS = BlockBehaviour.Properties.of()
			.mapColor(MapColor.WOOD)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2.0F)
			.sound(SoundType.WOOD)
			.noOcclusion()
			.ignitedByLava();
	public static final BlockBehaviour.Properties ZARZYK_PLANKS_SETTINGS = BlockBehaviour.Properties.of()
			.mapColor(MapColor.WOOD)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2.0F)
			.sound(SoundType.WOOD)
			.noOcclusion()
			.ignitedByLava();
	public static final BlockBehaviour.Properties ZARZYK_STAIRS_SETTINGS = BlockBehaviour.Properties.of()
			.mapColor(MapColor.WOOD)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2.0F, 3.0F)
			.sound(SoundType.WOOD)
			.ignitedByLava();
	public static final BlockBehaviour.Properties ZARZYK_SLAB_SETTINGS = BlockBehaviour.Properties.of()
			.mapColor(MapColor.WOOD)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2.0F, 3.0F)
			.sound(SoundType.WOOD)
			.ignitedByLava();
	public static final BlockBehaviour.Properties ZARZYK_FENCE_SETTINGS = BlockBehaviour.Properties.of()
			.mapColor(MapColor.WOOD)
			.forceSolidOn()
			.instrument(NoteBlockInstrument.BASS)
			.strength(2.0F, 3.0F)
			.sound(SoundType.WOOD)
			.ignitedByLava();
	public static final BlockBehaviour.Properties ZARZYK_FENCE_GATE_SETTINGS = BlockBehaviour.Properties.of()
			.mapColor(MapColor.WOOD)
			.forceSolidOn()
			.instrument(NoteBlockInstrument.BASS)
			.strength(2.0F, 3.0F)
			.ignitedByLava();
	public static final BlockBehaviour.Properties ZARZYK_PRESSURE_PLATE_SETTINGS = BlockBehaviour.Properties.of()
			.mapColor(MapColor.WOOD)
			.forceSolidOn()
			.instrument(NoteBlockInstrument.BASS)
			.noCollision()
			.strength(0.5F)
			.ignitedByLava()
			.pushReaction(PushReaction.POPPED);
	public static final BlockBehaviour.Properties ZARZYK_BUTTON_SETTINGS = Blocks.buttonProperties();
	public static final BlockBehaviour.Properties ZARZYK_TRAPDOOR_SETTINGS = BlockBehaviour.Properties.of()
			.mapColor(MapColor.WOOD)
			.instrument(NoteBlockInstrument.BASS)
			.strength(3.0F)
			.noOcclusion()
			.isValidSpawn(Blocks::never)
			.ignitedByLava();
	public static final BlockBehaviour.Properties ZARZYK_DOOR_SETTINGS = BlockBehaviour.Properties.of()
			.mapColor(MapColor.WOOD)
			.instrument(NoteBlockInstrument.BASS)
			.strength(3.0F)
			.noOcclusion()
			.ignitedByLava()
			.pushReaction(PushReaction.POPPED);
	public static final BlockBehaviour.Properties ZARZYK_LEAVES_SETTINGS = Blocks.leavesProperties(SoundType.AZALEA_LEAVES);
	public static final BlockBehaviour.Properties ZARZYK_SAPLING_SETTINGS = BlockBehaviour.Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.randomTicks()
			.instabreak()
			.sound(SoundType.GRASS)
			.pushReaction(PushReaction.POPPED);
	public static final BlockBehaviour.Properties POTTED_ZARZYK_SAPLING_SETTINGS = BlockBehaviour.Properties.of()
			.instabreak()
			.noOcclusion()
			.pushReaction(PushReaction.POPPED);

	public static final Item.Properties SILVER_WOLF_ORE_ITEM_SETTINGS = new Item.Properties()
			.stacksTo(64);
	public static final Item.Properties DARK_GLOWSTONE_ITEM_SETTINGS = new Item.Properties()
			.stacksTo(64);
	public static final Item.Properties THEORY_FORGE_BLOCK_ITEM_SETTINGS = new Item.Properties()
			.stacksTo(64);
	public static final Item.Properties BRAINROT_TABLE_BLOCK_ITEM_SETTINGS = new Item.Properties()
			.stacksTo(64);
	public static final Item.Properties RECYCLER_BLOCK_ITEM_SETTINGS = new Item.Properties()
			.stacksTo(1);
    public static final Item.Properties GLINIANKA_BLOCK_ITEM_SETTINGS = new Item.Properties()
            .stacksTo(64);
    public static final Item.Properties TRASH_CAN_ITEM_SETTINGS = new Item.Properties()
            .stacksTo(1);
    public static final Item.Properties SILVER_WOLF_BLOCK_ITEM_SETTINGS = new Item.Properties()
            .stacksTo(64);
    public static final Item.Properties GOLDEN_WOLF_BLOCK_ITEM_SETTINGS = new Item.Properties()
            .stacksTo(64);
	// Brainrots
	public static final Item.Properties BOMBARDINO_COCODRILO_ITEM_SETTINGS = new Item.Properties()
			.stacksTo(16);
	public static final Item.Properties TRALALERO_TRALALA_ITEM_SETTINGS = new Item.Properties()
			.stacksTo(16);
	public static final Item.Properties TUNG_TUNG_SAHUR_ITEM_SETTINGS = new Item.Properties()
			.stacksTo(16);
	public static final Item.Properties CHIMPANZINI_BANANINI_ITEM_SETTINGS = new Item.Properties()
			.stacksTo(16);

	public static final Item.Properties DEFAULT_ITEM_SETTINGS = new Item.Properties()
			.stacksTo(64);

	public static void registerAll() {
		SILVER_WOLF_ORE = InformejtycyRegistry.registerBlock("silver_wolf_ore",
				SILVER_WOLF_ORE_SETTINGS,
				SILVER_WOLF_ORE_ITEM_SETTINGS
		);
		DARK_GLOWSTONE = InformejtycyRegistry.registerBlock("dark_glowstone",
				DARK_GLOWSTONE_SETTINGS,
				DARK_GLOWSTONE_ITEM_SETTINGS
		);
		THEORY_FORGE_BLOCK = InformejtycyRegistry.registerCustomBlock("theory_forge_block",
				THEORY_FORGE_BLOCK_SETTINGS,
				THEORY_FORGE_BLOCK_ITEM_SETTINGS,
				TheoryForgeBlock::new
		);
		BRAINROT_TABLE_BLOCK = InformejtycyRegistry.registerCustomBlock("brainrot_table_block",
				BRAINROT_TABLE_BLOCK_SETTINGS,
				BRAINROT_TABLE_BLOCK_ITEM_SETTINGS,
				BrainrotTableBlock::new
		);
		RECYCLER_BLOCK = InformejtycyRegistry.registerCustomBlock("recycler_block",
				RECYCLER_BLOCK_SETTINGS,
				RECYCLER_BLOCK_ITEM_SETTINGS,
				RecyclerBlock::new
		);
		SILVER_WOLF_BLOCK = InformejtycyRegistry.registerBlock("silver_wolf_block",
				SILVER_WOLF_BLOCK_SETTINGS,
				SILVER_WOLF_BLOCK_ITEM_SETTINGS
		);
		GOLDEN_WOLF_BLOCK = InformejtycyRegistry.registerBlock("golden_wolf_block",
				GOLDEN_WOLF_BLOCK_SETTINGS,
				GOLDEN_WOLF_BLOCK_ITEM_SETTINGS
		);
        GLINIANKA_BLOCK = InformejtycyRegistry.registerCustomBlock("glinianka_block",
                GLINIANKA_BLOCK_SETTINGS,
                GLINIANKA_BLOCK_ITEM_SETTINGS,
                ZmysioSummoningBlock::new
        );
        TRASH_CAN = InformejtycyRegistry.registerCustomBlock("trash_can",
                TRASH_CAN_SETTINGS,
                TRASH_CAN_ITEM_SETTINGS,
                ZmysioSummoningBlock::new
        );
        ZMYSIO_SUMMON_ANCHOR = InformejtycyRegistry.registerBlock("zmysio_summon_anchor",
                BlockBehaviour.Properties.of(),
                new Item.Properties()
        );
		// Brainrots
		BOMBARDINO_COCODRILO = InformejtycyRegistry.registerCustomBlock("bombardino_cocodrilo",
				BOMBARDINO_COCODRILO_SETTINGS,
				BOMBARDINO_COCODRILO_ITEM_SETTINGS,
				FacingBlock::new
		);
		TRALALERO_TRALALA = InformejtycyRegistry.registerCustomBlock("tralalero_tralala",
				TRALALERO_TRALALA_SETTINGS,
				TRALALERO_TRALALA_ITEM_SETTINGS,
				FacingBlock::new
		);
		TUNG_TUNG_SAHUR = InformejtycyRegistry.registerCustomBlock("tung_tung_sahur",
				TUNG_TUNG_SAHUR_SETTINGS,
				TUNG_TUNG_SAHUR_ITEM_SETTINGS,
				FacingBlock::new
		);
		CHIMPANZINI_BANANINI = InformejtycyRegistry.registerCustomBlock("chimpanzini_bananini",
				CHIMPANZINI_BANANINI_SETTINGS,
				CHIMPANZINI_BANANINI_ITEM_SETTINGS,
				FacingBlock::new
		);
		// Wood
		ISTANBUL_LOG = InformejtycyRegistry.registerCustomBlock("istanbul_log",
				ISTANBUL_LOG_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				RotatedPillarBlock::new
		);
		STRIPPED_ISTANBUL_LOG = InformejtycyRegistry.registerCustomBlock("stripped_istanbul_log",
				STRIPPED_ISTANBUL_LOG_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				RotatedPillarBlock::new
		);
		ISTANBUL_WOOD = InformejtycyRegistry.registerCustomBlock("istanbul_wood",
				ISTANBUL_WOOD_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				RotatedPillarBlock::new
		);
		STRIPPED_ISTANBUL_WOOD = InformejtycyRegistry.registerCustomBlock("stripped_istanbul_wood",
				STRIPPED_ISTANBUL_WOOD_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				RotatedPillarBlock::new
		);
		ISTANBUL_PLANKS = InformejtycyRegistry.registerBlock("istanbul_planks",
				ISTANBUL_PLANKS_SETTINGS,
				DEFAULT_ITEM_SETTINGS
		);
		ISTANBUL_STAIRS = InformejtycyRegistry.registerCustomBlock("istanbul_stairs",
				ISTANBUL_STAIRS_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				properties -> new StairBlock(ISTANBUL_PLANKS.defaultBlockState(), properties)
		);
		ISTANBUL_SLAB = InformejtycyRegistry.registerCustomBlock("istanbul_slab",
				ISTANBUL_SLAB_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				SlabBlock::new
		);
		ISTANBUL_FENCE = InformejtycyRegistry.registerCustomBlock("istanbul_fence",
				ISTANBUL_FENCE_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				FenceBlock::new
		);
		ISTANBUL_FENCE_GATE = InformejtycyRegistry.registerCustomBlock("istanbul_fence_gate",
				ISTANBUL_FENCE_GATE_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				properties -> new FenceGateBlock(WoodType.BAMBOO, properties)
		);
		ISTANBUL_PRESSURE_PLATE = InformejtycyRegistry.registerCustomBlock("istanbul_pressure_plate",
				ISTANBUL_PRESSURE_PLATE_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				properties -> new PressurePlateBlock(BlockSetType.BAMBOO, properties)
		);
		ISTANBUL_BUTTON = InformejtycyRegistry.registerCustomBlock("istanbul_button",
				ISTANBUL_BUTTON_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				properties -> new ButtonBlock(BlockSetType.BAMBOO, 30, properties)
		);
		ISTANBUL_TRAPDOOR = InformejtycyRegistry.registerCustomBlock("istanbul_trapdoor",
				ISTANBUL_TRAPDOOR_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				properties -> new TrapDoorBlock(BlockSetType.BAMBOO, properties)
		);
		ISTANBUL_DOOR = InformejtycyRegistry.registerCustomBlock("istanbul_door",
				ISTANBUL_DOOR_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				properties -> new DoorBlock(BlockSetType.BAMBOO, properties)
		);
		ISTANBUL_LEAVES =InformejtycyRegistry.registerCustomBlock("istanbul_leaves",
				ISTANBUL_LEAVES_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				properties -> new UntintedParticleLeavesBlock(0.02F,
						ParticleTypes.DRIPPING_OBSIDIAN_TEAR, AmbientLeavesBlockSoundPlayer.noAmbientSound(), properties)
		);
		ISTANBUL_SAPLING = InformejtycyRegistry.registerCustomBlock("istanbul_sapling",
				ISTANBUL_SAPLING_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				properties -> new SaplingBlock(CustomTreeGrowers.ISTANBUL_TREE_GROWER, properties)
		);
		POTTED_ISTANBUL_SAPLING = InformejtycyRegistry.registerCustomBlockWithoutItem("potted_istanbul_sapling",
				POTTED_ISTANBUL_SAPLING_SETTINGS,
				properties -> new FlowerPotBlock(ISTANBUL_SAPLING, properties)
		);
		ZARZYK_LOG = InformejtycyRegistry.registerCustomBlock("zarzyk_log",
				ZARZYK_LOG_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				RotatedPillarBlock::new
		);
		STRIPPED_ZARZYK_LOG = InformejtycyRegistry.registerCustomBlock("stripped_zarzyk_log",
				STRIPPED_ZARZYK_LOG_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				RotatedPillarBlock::new
		);
		ZARZYK_WOOD = InformejtycyRegistry.registerCustomBlock("zarzyk_wood",
				ZARZYK_WOOD_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				RotatedPillarBlock::new
		);
		STRIPPED_ZARZYK_WOOD = InformejtycyRegistry.registerCustomBlock("stripped_zarzyk_wood",
				STRIPPED_ZARZYK_WOOD_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				RotatedPillarBlock::new
		);
		ZARZYK_PLANKS = InformejtycyRegistry.registerBlock("zarzyk_planks",
				ZARZYK_PLANKS_SETTINGS,
				DEFAULT_ITEM_SETTINGS
		);
		ZARZYK_STAIRS = InformejtycyRegistry.registerCustomBlock("zarzyk_stairs",
				ZARZYK_STAIRS_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				properties -> new StairBlock(ZARZYK_PLANKS.defaultBlockState(), properties)
		);
		ZARZYK_SLAB = InformejtycyRegistry.registerCustomBlock("zarzyk_slab",
				ZARZYK_SLAB_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				SlabBlock::new
		);
		ZARZYK_FENCE = InformejtycyRegistry.registerCustomBlock("zarzyk_fence",
				ZARZYK_FENCE_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				FenceBlock::new
		);
		ZARZYK_FENCE_GATE = InformejtycyRegistry.registerCustomBlock("zarzyk_fence_gate",
				ZARZYK_FENCE_GATE_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				properties -> new FenceGateBlock(WoodType.BAMBOO, properties)
		);
		ZARZYK_PRESSURE_PLATE = InformejtycyRegistry.registerCustomBlock("zarzyk_pressure_plate",
				ZARZYK_PRESSURE_PLATE_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				properties -> new PressurePlateBlock(BlockSetType.BAMBOO, properties)
		);
		ZARZYK_BUTTON = InformejtycyRegistry.registerCustomBlock("zarzyk_button",
				ZARZYK_BUTTON_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				properties -> new ButtonBlock(BlockSetType.BAMBOO, 30, properties)
		);
		ZARZYK_TRAPDOOR = InformejtycyRegistry.registerCustomBlock("zarzyk_trapdoor",
				ZARZYK_TRAPDOOR_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				properties -> new TrapDoorBlock(BlockSetType.BAMBOO, properties)
		);
		ZARZYK_DOOR = InformejtycyRegistry.registerCustomBlock("zarzyk_door",
				ZARZYK_DOOR_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				properties -> new DoorBlock(BlockSetType.BAMBOO, properties)
		);
		ZARZYK_LEAVES =InformejtycyRegistry.registerCustomBlock("zarzyk_leaves",
				ZARZYK_LEAVES_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				properties -> new UntintedParticleLeavesBlock(0.02F,
						ParticleTypes.DRIPPING_OBSIDIAN_TEAR, AmbientLeavesBlockSoundPlayer.noAmbientSound(), properties)
		);
		ZARZYK_SAPLING = InformejtycyRegistry.registerCustomBlock("zarzyk_sapling",
				ZARZYK_SAPLING_SETTINGS,
				DEFAULT_ITEM_SETTINGS,
				properties -> new SaplingBlock(CustomTreeGrowers.ZARZYK_TREE_GROWER, properties)
		);
		POTTED_ZARZYK_SAPLING = InformejtycyRegistry.registerCustomBlockWithoutItem("potted_zarzyk_sapling",
				POTTED_ZARZYK_SAPLING_SETTINGS,
				properties -> new FlowerPotBlock(ZARZYK_SAPLING, properties)
		);

		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.NATURAL_BLOCKS, SILVER_WOLF_ORE);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.NATURAL_BLOCKS, DARK_GLOWSTONE);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.FUNCTIONAL_BLOCKS, THEORY_FORGE_BLOCK);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.FUNCTIONAL_BLOCKS, BRAINROT_TABLE_BLOCK);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.BUILDING_BLOCKS, SILVER_WOLF_BLOCK);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.BUILDING_BLOCKS, GOLDEN_WOLF_BLOCK);
        InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.FUNCTIONAL_BLOCKS, GLINIANKA_BLOCK);
        InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.FUNCTIONAL_BLOCKS, TRASH_CAN);
		// Brainrots
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.COLORED_BLOCKS, BOMBARDINO_COCODRILO);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.COLORED_BLOCKS, TRALALERO_TRALALA);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.COLORED_BLOCKS, TUNG_TUNG_SAHUR);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.COLORED_BLOCKS, CHIMPANZINI_BANANINI);
		// Wood
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.NATURAL_BLOCKS, ISTANBUL_LOG);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.NATURAL_BLOCKS, STRIPPED_ISTANBUL_LOG);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.NATURAL_BLOCKS, ISTANBUL_WOOD);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.NATURAL_BLOCKS, STRIPPED_ISTANBUL_WOOD);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.BUILDING_BLOCKS, ISTANBUL_PLANKS);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.BUILDING_BLOCKS, ISTANBUL_STAIRS);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.BUILDING_BLOCKS, ISTANBUL_SLAB);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.BUILDING_BLOCKS, ISTANBUL_FENCE);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.BUILDING_BLOCKS, ISTANBUL_FENCE_GATE);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.BUILDING_BLOCKS, ISTANBUL_DOOR);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.BUILDING_BLOCKS, ISTANBUL_TRAPDOOR);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.BUILDING_BLOCKS, ISTANBUL_PRESSURE_PLATE);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.BUILDING_BLOCKS, ISTANBUL_BUTTON);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.NATURAL_BLOCKS, ISTANBUL_LEAVES);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.NATURAL_BLOCKS, ZARZYK_LOG);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.NATURAL_BLOCKS, STRIPPED_ZARZYK_LOG);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.NATURAL_BLOCKS, ZARZYK_WOOD);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.NATURAL_BLOCKS, STRIPPED_ZARZYK_WOOD);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.BUILDING_BLOCKS, ZARZYK_PLANKS);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.BUILDING_BLOCKS, ZARZYK_STAIRS);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.BUILDING_BLOCKS, ZARZYK_SLAB);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.BUILDING_BLOCKS, ZARZYK_FENCE);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.BUILDING_BLOCKS, ZARZYK_FENCE_GATE);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.BUILDING_BLOCKS, ZARZYK_DOOR);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.BUILDING_BLOCKS, ZARZYK_TRAPDOOR);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.BUILDING_BLOCKS, ZARZYK_PRESSURE_PLATE);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.BUILDING_BLOCKS, ZARZYK_BUTTON);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.NATURAL_BLOCKS, ZARZYK_LEAVES);
		InformejtycyRegistry.registerMenuBlock(CreativeModeTabs.NATURAL_BLOCKS, ZARZYK_SAPLING);

        FlammableBlockRegistry.getDefaultInstance().add(ISTANBUL_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_ISTANBUL_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(ISTANBUL_WOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_ISTANBUL_WOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(ISTANBUL_PLANKS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ISTANBUL_STAIRS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ISTANBUL_SLAB, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ISTANBUL_FENCE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ISTANBUL_FENCE_GATE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ISTANBUL_LEAVES, 30, 60);

		BlockTransformerHelper.registerStripping(ISTANBUL_LOG, STRIPPED_ISTANBUL_LOG);
		BlockTransformerHelper.registerStripping(ISTANBUL_WOOD, STRIPPED_ISTANBUL_WOOD);
		BlockTransformerHelper.registerStripping(ZARZYK_LOG, STRIPPED_ZARZYK_LOG);
		BlockTransformerHelper.registerStripping(ZARZYK_WOOD, STRIPPED_ZARZYK_WOOD);
	}
}
