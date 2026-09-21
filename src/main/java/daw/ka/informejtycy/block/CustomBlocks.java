package daw.ka.informejtycy.block;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.block.custom.BrainrotTableBlock;
import daw.ka.informejtycy.block.custom.FacingBlock;
import daw.ka.informejtycy.block.custom.RecyclerBlock;
import daw.ka.informejtycy.block.custom.TheoryForgeBlock;
import daw.ka.informejtycy.block.custom.ZmysioSummoningBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.SoundType;

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

	public static final BlockBehaviour.Properties SILVER_WOLF_ORE_SETTINGS = BlockBehaviour.Properties.of()
			.strength(4.0f, 1200f)
			.requiresCorrectToolForDrops()
			.mapColor(MapColor.SAND)
			.lightLevel(state -> 7);
	public static final BlockBehaviour.Properties DARK_GLOWSTONE_SETTINGS = BlockBehaviour.Properties.of()
			.strength(0.3F)
			.sound(SoundType.GLASS)
			.lightLevel(state -> 10)
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
			.strength(-1.0F, 3600000.0F)
			.noLootTable()
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
				TheoryForgeBlock.class
		);
		BRAINROT_TABLE_BLOCK = InformejtycyRegistry.registerCustomBlock("brainrot_table_block",
				BRAINROT_TABLE_BLOCK_SETTINGS,
				BRAINROT_TABLE_BLOCK_ITEM_SETTINGS,
				BrainrotTableBlock.class
		);
		RECYCLER_BLOCK = InformejtycyRegistry.registerCustomBlock("recycler_block",
				RECYCLER_BLOCK_SETTINGS,
				RECYCLER_BLOCK_ITEM_SETTINGS,
				RecyclerBlock.class
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
                ZmysioSummoningBlock.class
        );
        TRASH_CAN = InformejtycyRegistry.registerCustomBlock("trash_can",
                TRASH_CAN_SETTINGS,
                TRASH_CAN_ITEM_SETTINGS,
                ZmysioSummoningBlock.class
        );
        ZMYSIO_SUMMON_ANCHOR = InformejtycyRegistry.registerBlock("zmysio_summon_anchor",
                BlockBehaviour.Properties.of(),
                new Item.Properties()
        );
		// Brainrots
		BOMBARDINO_COCODRILO = InformejtycyRegistry.registerCustomBlock("bombardino_cocodrilo",
				BOMBARDINO_COCODRILO_SETTINGS,
				BOMBARDINO_COCODRILO_ITEM_SETTINGS,
				FacingBlock.class
		);
		TRALALERO_TRALALA = InformejtycyRegistry.registerCustomBlock("tralalero_tralala",
				TRALALERO_TRALALA_SETTINGS,
				TRALALERO_TRALALA_ITEM_SETTINGS,
				FacingBlock.class
		);
		TUNG_TUNG_SAHUR = InformejtycyRegistry.registerCustomBlock("tung_tung_sahur",
				TUNG_TUNG_SAHUR_SETTINGS,
				TUNG_TUNG_SAHUR_ITEM_SETTINGS,
				FacingBlock.class
		);
		CHIMPANZINI_BANANINI = InformejtycyRegistry.registerCustomBlock("chimpanzini_bananini",
				CHIMPANZINI_BANANINI_SETTINGS,
				CHIMPANZINI_BANANINI_ITEM_SETTINGS,
				FacingBlock.class
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
	}
}
