package daw.ka.informejtycy.entity;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.entity.custom.boss.ZmysioEntity;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfEntity;
import daw.ka.informejtycy.entity.custom.mob.ZarzykEntity;
import daw.ka.informejtycy.entity.custom.projectile.CigaretteProjectileEntity;
import daw.ka.informejtycy.entity.custom.projectile.MeteorProjectileEntity;
import daw.ka.informejtycy.entity.custom.projectile.MilkProjectileEntity;
import daw.ka.informejtycy.entity.custom.projectile.RockProjectileEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class CustomEntities {
    public static EntityType<ZmysioEntity> ZMYSIO_BOSS;
    public static EntityType<AlphaWolfEntity> ALPHA_WOLF_BOSS;
    public static EntityType<ZarzykEntity> ZARZYK;
    public static EntityType<MilkProjectileEntity> MILK_PROJECTILE;
    public static EntityType<RockProjectileEntity> ROCK_PROJECTILE;
    public static EntityType<CigaretteProjectileEntity> CIGARETTE_PROJECTILE;
    public static EntityType<MeteorProjectileEntity> METEOR_PROJECTILE;

    public static void registerAll() {
        ZMYSIO_BOSS = InformejtycyRegistry.registerMob("zmysio_boss", ZmysioEntity::new, MobCategory.MONSTER, 2.5f, 5);
        ALPHA_WOLF_BOSS = InformejtycyRegistry.registerMob("alpha_wolf_boss", AlphaWolfEntity::new, MobCategory.MONSTER, 5.3f, 7f);
        ZARZYK = InformejtycyRegistry.registerMob("zarzyk", ZarzykEntity::new, MobCategory.MONSTER, 2.25f, 7.75f);
        MILK_PROJECTILE = InformejtycyRegistry.registerMob("milk_projectile", MilkProjectileEntity::new, MobCategory.MISC, 0.5f, 0.5f);
        ROCK_PROJECTILE = InformejtycyRegistry.registerMob("rock_projectile", RockProjectileEntity::new, MobCategory.MISC, 1.25f, 1.25f);
        CIGARETTE_PROJECTILE = InformejtycyRegistry.registerMob("cigarette_projectile", CigaretteProjectileEntity::new, MobCategory.MISC, 0.5f, 0.5f);
        METEOR_PROJECTILE = InformejtycyRegistry.registerMob("meteor_projectile", MeteorProjectileEntity::new, MobCategory.MISC, 1.25f, 1.25f);

        FabricDefaultAttributeRegistry.register(ZMYSIO_BOSS, ZmysioEntity.createZmysioAttributes());
        FabricDefaultAttributeRegistry.register(ALPHA_WOLF_BOSS, AlphaWolfEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ZARZYK, ZarzykEntity.createZarzykAttributes());
    }
}
