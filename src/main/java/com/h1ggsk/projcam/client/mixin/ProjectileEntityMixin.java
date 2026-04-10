package com.h1ggsk.projcam.client.mixin;

import com.h1ggsk.projcam.client.ProjectileCameraController;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ProjectileEntity.class)
public class ProjectileEntityMixin {
    @Inject(method = "onCollision", at = @At("HEAD"))
    private void projcam$restorePlayerCameraOnCollision(HitResult hitResult, CallbackInfo ci) {
        ProjectileCameraController.handleProjectileCollision((ProjectileEntity) (Object) this);
    }
}
