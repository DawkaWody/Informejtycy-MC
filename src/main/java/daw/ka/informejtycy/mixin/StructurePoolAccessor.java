package daw.ka.informejtycy.mixin;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import com.mojang.datafixers.util.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(StructureTemplatePool.class)
public interface StructurePoolAccessor {
    @Mutable
    @Accessor("rawTemplates")
    void setElementWeights(List<Pair<StructurePoolElement, Integer>> elementWeights);

    @Accessor("templates")
    ObjectArrayList<StructurePoolElement> getElements();
}
