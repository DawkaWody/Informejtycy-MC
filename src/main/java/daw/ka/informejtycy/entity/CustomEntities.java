package daw.ka.informejtycy.entity;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.entity.custom.boss.ZmysioEntity;
import daw.ka.informejtycy.entity.custom.mob.ZarzykEntity;
import daw.ka.informejtycy.entity.custom.projectile.MilkProjectileEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class CustomEntities {
    public static EntityType<ZmysioEntity> ZMYSIO_BOSS;
    public static EntityType<ZarzykEntity> ZARZYK;
    public static EntityType<MilkProjectileEntity> MILK_PROJECTILE;

    public static void registerAll() {
        ZMYSIO_BOSS = InformejtycyRegistry.registerMob("zmysio_boss", ZmysioEntity::new, MobCategory.MONSTER, 2.5f, 5);
        ZARZYK = InformejtycyRegistry.registerMob("zarzyk", ZarzykEntity::new, MobCategory.MONSTER, 1.1f, 3.3f);
        MILK_PROJECTILE = InformejtycyRegistry.registerMob("milk_projectile", MilkProjectileEntity::new, MobCategory.MISC, 0.5f, 0.5f);

        FabricDefaultAttributeRegistry.register(ZMYSIO_BOSS, ZmysioEntity.createZmysioAttributes());
        FabricDefaultAttributeRegistry.register(ZARZYK, ZarzykEntity.createZarzykAttributes());
    }
}
