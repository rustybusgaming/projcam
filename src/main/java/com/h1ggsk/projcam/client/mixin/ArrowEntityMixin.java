package com.h1ggsk.projcam.client.mixin;

import com.h1ggsk.projcam.client.ProjectileCameraController;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ArrowEntity.class)
public class ArrowEntityMixin {
    @Redirect(
            method = "spawnParticles",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;addParticleClient(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"
            )
    )
    private void projcam$hideTrackedArrowSpawnParticles(World world, ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        if (!ProjectileCameraController.shouldHideEntity(MinecraftClient.getInstance(), (ArrowEntity) (Object) this)) {
            world.addParticleClient(parameters, x, y, z, velocityX, velocityY, velocityZ);
        }
    }

    @Redirect(
            method = "handleStatus",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;addParticleClient(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"
            )
    )
    private void projcam$hideTrackedArrowStatusParticles(World world, ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        if (!ProjectileCameraController.shouldHideEntity(MinecraftClient.getInstance(), (ArrowEntity) (Object) this)) {
            world.addParticleClient(parameters, x, y, z, velocityX, velocityY, velocityZ);
        }
    }
}
