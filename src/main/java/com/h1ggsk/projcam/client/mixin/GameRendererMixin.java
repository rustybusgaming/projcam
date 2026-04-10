package com.h1ggsk.projcam.client.mixin;

import com.h1ggsk.projcam.client.ProjectileCameraController;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "renderHand", at = @At("HEAD"), cancellable = true)
    private void projcam$hideHandInProjectileCamera(float tickProgress, boolean renderBlockOutline, Matrix4f matrix4f, CallbackInfo ci) {
        if (ProjectileCameraController.isActive(MinecraftClient.getInstance())) {
            ci.cancel();
        }
    }
}
