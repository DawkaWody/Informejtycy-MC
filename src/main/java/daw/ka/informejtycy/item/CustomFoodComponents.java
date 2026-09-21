package daw.ka.informejtycy.item;

import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

import java.util.List;

public class CustomFoodComponents {
	public static final FoodProperties LIGHT_FOOD = new FoodProperties.Builder()
			.nutrition(1)
			.saturationModifier(0.25f)
			.alwaysEdible()
			.build();
    public static final FoodProperties ZMYSIO_MILK = new FoodProperties.Builder()
            .nutrition(4)
            .saturationModifier(1.2f)
            .alwaysEdible()
            .build();
	public static final FoodProperties ZARZYK_GEL = new FoodProperties.Builder()
			.nutrition(6)
			.saturationModifier(0.2f)
			.alwaysEdible()
			.build();

	public static final Consumable LIGHT_FOOD_CONSUMABLE = Consumables.defaultFood().onConsume(
			new ApplyStatusEffectsConsumeEffect(List.of(
					new MobEffectInstance(MobEffects.SLOW_FALLING, 200)
			))).build();
    public static final Consumable ZMYSIO_MILK_CONSUMABLE = Consumables.defaultDrink().onConsume(
            new ApplyStatusEffectsConsumeEffect(List.of(
                    new MobEffectInstance(MobEffects.STRENGTH, 1200, 2),
                    new MobEffectInstance(MobEffects.NAUSEA, 1200, 2)
            ))).build();
	public static final Consumable ZARZYK_GEL_CONSUMABLE = Consumables.defaultDrink().onConsume(
				new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.LUCK, 6000, 0), 0.5f)
			).onConsume(
					new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HASTE, 6000, 2), 0.5f)
			).build();
}
