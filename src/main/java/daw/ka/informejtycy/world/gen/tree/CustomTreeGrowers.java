package daw.ka.informejtycy.world.gen.tree;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.world.CustomConfiguredFeatures;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.grower.TreeGrower;

public class CustomTreeGrowers {
    public static final TreeGrower ISTANBUL_TREE_GROWER = new TreeGrower(InformejtycyRegistry.id("istanbul_tree").toString(),
            WeightedList.of(CustomConfiguredFeatures.ISTANBUL_TREE_KEY), WeightedList.of(), WeightedList.of(), CustomConfiguredFeatures.ISTANBUL_TREE_KEY);
    public static final TreeGrower ZARZYK_TREE_GROWER = new TreeGrower(InformejtycyRegistry.id("zarzyk_tree").toString(),
            WeightedList.of(CustomConfiguredFeatures.ZARZYK_TREE_KEY), WeightedList.of(), WeightedList.of(), CustomConfiguredFeatures.ZARZYK_TREE_KEY);
}
