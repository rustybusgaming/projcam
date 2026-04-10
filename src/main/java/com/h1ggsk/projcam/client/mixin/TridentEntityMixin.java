package com.h1ggsk.projcam.client.mixin;

import com.h1ggsk.projcam.client.ProjectileCameraController;
import net.minecraft.entity.projectile.TridentEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TridentEntity.class)
public abstract class TridentEntityMixin {
    @Shadow
    private boolean dealtDamage;

    @Shadow
    protected int inGroundTime;

    @Shadow
    public int returnTimer;

    @Inject(method = "tick", at = @At("TAIL"))
    private void projcam$detachTrackedTridentOnFlightEnd(CallbackInfo ci) {
        if (this.dealtDamage || this.inGroundTime > 0 || this.returnTimer > 0) {
            ProjectileCameraController.handleProjectileFinishedFlight((TridentEntity) (Object) this);
        }
    }

    @Inject(method = "onPlayerCollision", at = @At("HEAD"))
    private void projcam$detachTrackedTridentOnPickup(net.minecraft.entity.player.PlayerEntity player, CallbackInfo ci) {
        ProjectileCameraController.handleProjectileFinishedFlight((TridentEntity) (Object) this);
    }
}
