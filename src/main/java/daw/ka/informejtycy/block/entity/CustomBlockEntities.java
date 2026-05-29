package daw.ka.informejtycy.block.entity;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.block.CustomBlocks;
import daw.ka.informejtycy.block.entity.custom.BrainrotTableBlockEntity;
import daw.ka.informejtycy.block.entity.custom.TheoryForgeBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;

public class CustomBlockEntities {
	public static BlockEntityType<TheoryForgeBlockEntity> THEORY_FORGE_BLOCK_ENTITY_TYPE = FabricBlockEntityTypeBuilder
			.create(TheoryForgeBlockEntity::new, CustomBlocks.THEORY_FORGE_BLOCK)
			.build();
	public static BlockEntityType<BrainrotTableBlockEntity> BRAINROT_TABLE_BLOCK_ENTITY_TYPE = FabricBlockEntityTypeBuilder
			.create(BrainrotTableBlockEntity::new, CustomBlocks.BRAINROT_TABLE_BLOCK)
			.build();

	public static void registerAll() {
		THEORY_FORGE_BLOCK_ENTITY_TYPE = InformejtycyRegistry.registerCustomBlockEntity("theory_forge_block", THEORY_FORGE_BLOCK_ENTITY_TYPE);
		BRAINROT_TABLE_BLOCK_ENTITY_TYPE = InformejtycyRegistry.registerCustomBlockEntity("brainrot_table_block", BRAINROT_TABLE_BLOCK_ENTITY_TYPE);
	}
}
