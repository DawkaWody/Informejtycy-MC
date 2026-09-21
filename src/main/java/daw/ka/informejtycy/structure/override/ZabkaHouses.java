package daw.ka.informejtycy.structure.override;

import com.mojang.datafixers.util.Pair;
import daw.ka.informejtycy.Informejtycy;
import daw.ka.informejtycy.mixin.StructurePoolAccessor;
import net.fabricmc.fabric.api.event.registry.DynamicRegistrySetupCallback;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ZabkaHouses {
    private static final List<String> VILLAGE_TYPES = List.of("plains", "desert", "savanna", "snowy", "taiga");
    private static final Set<Identifier> HOUSE_POOLS = VILLAGE_TYPES.stream()
            .flatMap(type -> Stream.of(
                    Identifier.withDefaultNamespace("village/" + type + "/houses"),
                    Identifier.withDefaultNamespace("village/" + type + "/zombie/houses")
            )).collect(Collectors.toSet());
    private static final List<String> ZABKA_HOUSES = List.of(
            "zabka1",
            "zabka2",
            "zabka3",
            "zabka4"
    );
    private static final int WEIGHT = 4;

    public static void register() {
        DynamicRegistrySetupCallback.EVENT.register(view -> {
            view.registerEntryAdded(Registries.TEMPLATE_POOL, ((rawId, id, pool) -> {
                if (HOUSE_POOLS.contains(id)) {
                    addHouses(pool);
                }
            }));
        });
    }

    private static void addHouses(StructureTemplatePool pool) {
        StructurePoolAccessor accessor = (StructurePoolAccessor) pool;
        List<Pair<StructurePoolElement, Integer>> weights = new ArrayList<>(pool.getTemplates());

        for (String house : ZABKA_HOUSES) {
            String location = Informejtycy.MOD_ID + ":village/zabka/" + house;
            StructurePoolElement element = StructurePoolElement.legacy(location)
                    .apply(StructureTemplatePool.Projection.RIGID);

            weights.add(Pair.of(element, WEIGHT));
            for (int i = 0; i < WEIGHT; i++) {
                accessor.getElements().add(element);
            }
        }

        accessor.setElementWeights(weights);
    }
}
