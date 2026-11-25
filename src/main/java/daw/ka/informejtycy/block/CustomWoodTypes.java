package daw.ka.informejtycy.block;

import daw.ka.informejtycy.InformejtycyRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class CustomWoodTypes {
	public static final WoodType ISTANBUL = WoodTypeBuilder.copyOf(WoodType.BAMBOO)
			.register(InformejtycyRegistry.id("istanbul"), BlockSetType.BAMBOO);
	public static final WoodType ZARZYK = WoodTypeBuilder.copyOf(WoodType.OAK)
			.register(InformejtycyRegistry.id("zarzyk"), BlockSetType.BAMBOO);
}
