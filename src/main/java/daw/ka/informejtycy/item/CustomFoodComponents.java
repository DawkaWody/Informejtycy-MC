package daw.ka.informejtycy.item;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;

import java.util.List;

public class CustomFoodComponents {
	public static final FoodComponent LIGHT_FOOD = new FoodComponent.Builder()
			.nutrition(1)
			.saturationModifier(0.25f)
			.alwaysEdible()
			.build();
    public static final FoodComponent ZMYSIO_MILK = new FoodComponent.Builder()
            .nutrition(4)
            .saturationModifier(1.2f)
            .alwaysEdible()
            .build();
	public static final FoodComponent ZARZYK_GEL = new FoodComponent.Builder()
			.nutrition(6)
			.saturationModifier(0.2f)
			.alwaysEdible()
			.build();

	public static final ConsumableComponent LIGHT_FOOD_CONSUMABLE = ConsumableComponents.food().consumeEffect(
			new ApplyEffectsConsumeEffect(List.of(
					new StatusEffectInstance(StatusEffects.SLOW_FALLING, 200)
			))).build();
    public static final ConsumableComponent ZMYSIO_MILK_CONSUMABLE = ConsumableComponents.drink().consumeEffect(
            new ApplyEffectsConsumeEffect(List.of(
                    new StatusEffectInstance(StatusEffects.STRENGTH, 1200, 2),
                    new StatusEffectInstance(StatusEffects.NAUSEA, 1200, 2)
            ))).build();
	public static final ConsumableComponent ZARZYK_GEL_CONSUMABLE = ConsumableComponents.drink().consumeEffect(
				new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.LUCK, 6000, 0), 0.5f)
			).consumeEffect(
					new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.HASTE, 6000, 2), 0.5f)
			).build();
}
