package com.h1ggsk.projcam.client.mixin;

import com.h1ggsk.projcam.client.ProjectileCameraController;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Keyboard;
import net.minecraft.client.input.KeyInput;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    private void projcam$detachOnEscape(long window, int action, KeyInput keyInput, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (action == GLFW.GLFW_PRESS
                && keyInput.key() == GLFW.GLFW_KEY_ESCAPE
                && ProjectileCameraController.isActive(client)) {
            ProjectileCameraController.detachFromCurrentProjectiles(client);
            ci.cancel();
        }
    }
}
