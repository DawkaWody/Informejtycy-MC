package daw.ka.informejtycy.client.entity.alphawolf;

import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfAction;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import org.jspecify.annotations.NonNull;

import java.util.EnumMap;
import java.util.Map;

public class AlphaWolfModel extends EntityModel<AlphaWolfRenderState> {
	private final Map<AlphaWolfAction, KeyframeAnimation> animations = new EnumMap<>(AlphaWolfAction.class);

	public AlphaWolfModel(ModelPart root, Map<AlphaWolfAction, AnimationDefinition> definitions) {
		super(root);
		definitions.forEach((action, definition) -> animations.put(action, definition.bake(root)));
	}

	@Override
	public void setupAnim(@NonNull AlphaWolfRenderState state) {
		super.setupAnim(state);
		animations.forEach((action, animation) ->
				animation.apply(state.animationStates.get(action), state.ageInTicks, 1f));
	}
}
