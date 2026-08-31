package daw.ka.informejtycy.block;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.block.custom.BrainrotTableBlock;
import daw.ka.informejtycy.block.custom.FacingBlock;
import daw.ka.informejtycy.block.custom.RecyclerBlock;
import daw.ka.informejtycy.block.custom.TheoryForgeBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.sound.BlockSoundGroup;

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

	public static final AbstractBlock.Settings SILVER_WOLF_ORE_SETTINGS = AbstractBlock.Settings.create()
			.strength(4.0f, 1200f)
			.requiresTool()
			.mapColor(MapColor.PALE_YELLOW)
			.luminance(state -> 7);
	public static final AbstractBlock.Settings DARK_GLOWSTONE_SETTINGS = AbstractBlock.Settings.create()
			.strength(0.3F)
			.sounds(BlockSoundGroup.GLASS)
			.luminance(state -> 10)
			.solidBlock(Blocks::never);
	public static final AbstractBlock.Settings THEORY_FORGE_BLOCK_SETTINGS = AbstractBlock.Settings.create()
			.strength(3.5f, 3.0f)
			.requiresTool()
			.sounds(BlockSoundGroup.STONE)
			.nonOpaque();
	public static final AbstractBlock.Settings BRAINROT_TABLE_BLOCK_SETTINGS = AbstractBlock.Settings.create()
			.strength(2f, 3.0f)
			.requiresTool()
			.sounds(BlockSoundGroup.WOOD)
			.nonOpaque();
	public static final AbstractBlock.Settings RECYCLER_BLOCK_SETTINGS = AbstractBlock.Settings.create()
			.strength(-1.0F, 3600000.0F)
			.dropsNothing()
			.sounds(BlockSoundGroup.STONE)
			.nonOpaque();
    public static final AbstractBlock.Settings GLINIANKA_BLOCK_SETTINGS = AbstractBlock.Settings.create()
            .strength(0.6f)
            .sounds(BlockSoundGroup.GRAVEL)
            .mapColor(MapColor.BROWN);
    public static final AbstractBlock.Settings TRASH_CAN_SETTINGS = AbstractBlock.Settings.create()
            .strength(2.0f)
            .sounds(BlockSoundGroup.METAL)
            .nonOpaque();
    public static final AbstractBlock.Settings SILVER_WOLF_BLOCK_SETTINGS = AbstractBlock.Settings.create()
            .strength(5f, 6.0f)
            .requiresTool()
            .sounds(BlockSoundGroup.METAL)
            .nonOpaque();
    public static final AbstractBlock.Settings GOLDEN_WOLF_BLOCK_SETTINGS = AbstractBlock.Settings.create()
            .strength(3f, 12.0f)
            .requiresTool()
            .sounds(BlockSoundGroup.METAL)
            .nonOpaque();
	// Brainrots
	public static final AbstractBlock.Settings BOMBARDINO_COCODRILO_SETTINGS = AbstractBlock.Settings.create()
			.strength(40f, 4.0f)
			.requiresTool()
			.sounds(BlockSoundGroup.IRON)
			.nonOpaque();
	public static final AbstractBlock.Settings TRALALERO_TRALALA_SETTINGS = AbstractBlock.Settings.create()
			.strength(25f, 4.0f)
			.requiresTool()
			.sounds(BlockSoundGroup.WET_SPONGE)
			.nonOpaque();
	public static final AbstractBlock.Settings TUNG_TUNG_SAHUR_SETTINGS = AbstractBlock.Settings.create()
			.strength(30f, 4.0f)
			.requiresTool()
			.sounds(BlockSoundGroup.WOOD)
			.nonOpaque();
	public static final AbstractBlock.Settings CHIMPANZINI_BANANINI_SETTINGS = AbstractBlock.Settings.create()
			.strength(30f, 4.0f)
			.requiresTool()
			.sounds(BlockSoundGroup.BIG_DRIPLEAF)
			.nonOpaque();

	public static final Item.Settings SILVER_WOLF_ORE_ITEM_SETTINGS = new Item.Settings()
			.maxCount(64);
	public static final Item.Settings DARK_GLOWSTONE_ITEM_SETTINGS = new Item.Settings()
			.maxCount(64);
	public static final Item.Settings THEORY_FORGE_BLOCK_ITEM_SETTINGS = new Item.Settings()
			.maxCount(64);
	public static final Item.Settings BRAINROT_TABLE_BLOCK_ITEM_SETTINGS = new Item.Settings()
			.maxCount(64);
	public static final Item.Settings RECYCLER_BLOCK_ITEM_SETTINGS = new Item.Settings()
			.maxCount(1);
    public static final Item.Settings GLINIANKA_BLOCK_ITEM_SETTINGS = new Item.Settings()
            .maxCount(64);
    public static final Item.Settings TRASH_CAN_ITEM_SETTINGS = new Item.Settings()
            .maxCount(1);
    public static final Item.Settings SILVER_WOLF_BLOCK_ITEM_SETTINGS = new Item.Settings()
            .maxCount(64);
    public static final Item.Settings GOLDEN_WOLF_BLOCK_ITEM_SETTINGS = new Item.Settings()
            .maxCount(64);
	// Brainrots
	public static final Item.Settings BOMBARDINO_COCODRILO_ITEM_SETTINGS = new Item.Settings()
			.maxCount(16);
	public static final Item.Settings TRALALERO_TRALALA_ITEM_SETTINGS = new Item.Settings()
			.maxCount(16);
	public static final Item.Settings TUNG_TUNG_SAHUR_ITEM_SETTINGS = new Item.Settings()
			.maxCount(16);
	public static final Item.Settings CHIMPANZINI_BANANINI_ITEM_SETTINGS = new Item.Settings()
			.maxCount(16);

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
                FacingBlock.class
        );
        TRASH_CAN = InformejtycyRegistry.registerCustomBlock("trash_can",
                TRASH_CAN_SETTINGS,
                TRASH_CAN_ITEM_SETTINGS,
                FacingBlock.class
        );
        ZMYSIO_SUMMON_ANCHOR = InformejtycyRegistry.registerBlock("zmysio_summon_anchor",
                AbstractBlock.Settings.create(),
                new Item.Settings()
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

		InformejtycyRegistry.registerMenuBlock(ItemGroups.NATURAL, SILVER_WOLF_ORE);
		InformejtycyRegistry.registerMenuBlock(ItemGroups.NATURAL, DARK_GLOWSTONE);
		InformejtycyRegistry.registerMenuBlock(ItemGroups.FUNCTIONAL, THEORY_FORGE_BLOCK);
		InformejtycyRegistry.registerMenuBlock(ItemGroups.FUNCTIONAL, BRAINROT_TABLE_BLOCK);
		InformejtycyRegistry.registerMenuBlock(ItemGroups.BUILDING_BLOCKS, SILVER_WOLF_BLOCK);
		InformejtycyRegistry.registerMenuBlock(ItemGroups.BUILDING_BLOCKS, GOLDEN_WOLF_BLOCK);
        InformejtycyRegistry.registerMenuBlock(ItemGroups.FUNCTIONAL, GLINIANKA_BLOCK);
        InformejtycyRegistry.registerMenuBlock(ItemGroups.FUNCTIONAL, TRASH_CAN);
		// Brainrots
		InformejtycyRegistry.registerMenuBlock(ItemGroups.COLORED_BLOCKS, BOMBARDINO_COCODRILO);
		InformejtycyRegistry.registerMenuBlock(ItemGroups.COLORED_BLOCKS, TRALALERO_TRALALA);
		InformejtycyRegistry.registerMenuBlock(ItemGroups.COLORED_BLOCKS, TUNG_TUNG_SAHUR);
		InformejtycyRegistry.registerMenuBlock(ItemGroups.COLORED_BLOCKS, CHIMPANZINI_BANANINI);
	}
}
