package daw.ka.informejtycy.mixin;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import com.mojang.datafixers.util.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(StructurePool.class)
public interface StructurePoolAccessor {
    @Mutable
    @Accessor("elementWeights")
    void setElementWeights(List<Pair<StructurePoolElement, Integer>> elementWeights);

    @Accessor("elements")
    ObjectArrayList<StructurePoolElement> getElements();
}
