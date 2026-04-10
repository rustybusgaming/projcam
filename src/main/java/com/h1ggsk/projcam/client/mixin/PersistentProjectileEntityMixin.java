package com.h1ggsk.projcam.client.mixin;

import com.h1ggsk.projcam.client.ProjectileCameraController;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PersistentProjectileEntity.class)
public class PersistentProjectileEntityMixin {
    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;addParticleClient(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"
            )
    )
    private void projcam$hideTrackedProjectileTickParticles(World world, ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        if (!ProjectileCameraController.shouldHideEntity(MinecraftClient.getInstance(), (PersistentProjectileEntity) (Object) this)) {
            world.addParticleClient(parameters, x, y, z, velocityX, velocityY, velocityZ);
        }
    }

    @Redirect(
            method = "spawnBubbleParticles",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;addParticleClient(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"
            )
    )
    private void projcam$hideTrackedProjectileBubbleParticles(World world, ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        if (!ProjectileCameraController.shouldHideEntity(MinecraftClient.getInstance(), (PersistentProjectileEntity) (Object) this)) {
            world.addParticleClient(parameters, x, y, z, velocityX, velocityY, velocityZ);
        }
    }
}
