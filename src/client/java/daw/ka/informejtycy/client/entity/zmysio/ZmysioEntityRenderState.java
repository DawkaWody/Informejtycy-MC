package daw.ka.informejtycy.client.entity.zmysio;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

public class ZmysioEntityRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState shootRightAnimationState = new AnimationState();
    public final AnimationState shootLeftAnimationState = new AnimationState();
    public final AnimationState spinAnimationState = new AnimationState();
    public final AnimationState gasAnimationState = new AnimationState();
    public final AnimationState deathAnimationState = new AnimationState();
    public boolean armored;
}
