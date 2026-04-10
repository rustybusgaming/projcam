package com.h1ggsk.projcam.client;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;

public final class ProjectileCameraController {
    private static Integer trackedProjectileId;
    private static Perspective previousPerspective;
    private static final Set<Integer> collidedProjectileIds = new HashSet<>();
    private static final Set<Integer> ignoredProjectileIds = new HashSet<>();
    private static Vec3d playerAnchorPos;

    private ProjectileCameraController() {
    }

    public static void tick(MinecraftClient client) {
        if (client.world == null || client.player == null) {
            resetCamera(client);
            return;
        }

        cleanupCollidedProjectiles(client);
        cleanupIgnoredProjectiles(client);

        ProjectileEntity trackedProjectile = getTrackedProjectile(client);
        if (trackedProjectile != null && shouldDetach(client, trackedProjectile)) {
            detachFromCurrentProjectiles(client);
            return;
        }

        ProjectileEntity bestProjectile = findBestProjectile(client);
        if (bestProjectile == null) {
            resetCamera(client);
            return;
        }

        beginTracking(client, bestProjectile);
        ensureFirstPerson(client);
    }

    public static void handleProjectileCollision(Entity entity) {
        handleProjectileFinishedFlight(entity);
    }

    public static void handleProjectileFinishedFlight(Entity entity) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.player == null || !entity.getEntityWorld().isClient()) {
            return;
        }

        if (!(entity instanceof ProjectileEntity projectile) || projectile.getOwner() != client.player) {
            return;
        }

        collidedProjectileIds.add(projectile.getId());
        if (trackedProjectileId != null && trackedProjectileId == projectile.getId()) {
            detachFromCurrentProjectiles(client);
        }
    }

    public static boolean isActive(MinecraftClient client) {
        return getTrackedProjectile(client) != null;
    }

    public static boolean shouldHideEntity(MinecraftClient client, Entity entity) {
        return trackedProjectileId != null && isActive(client) && entity.getId() == trackedProjectileId;
    }

    public static void detachFromCurrentProjectiles(MinecraftClient client) {
        ignoreCurrentProjectiles(client);
        resetCamera(client);
    }

    public static ProjectileEntity getTrackedProjectile(MinecraftClient client) {
        if (trackedProjectileId == null || client.world == null) {
            return null;
        }

        Entity entity = client.world.getEntityById(trackedProjectileId);
        if (entity instanceof ProjectileEntity projectile && !projectile.isRemoved()) {
            return projectile;
        }

        ignoredProjectileIds.add(trackedProjectileId);
        trackedProjectileId = null;
        return null;
    }

    private static ProjectileEntity findBestProjectile(MinecraftClient client) {
        ProjectileEntity trackedProjectile = getTrackedProjectile(client);
        ProjectileEntity newestProjectile = null;

        for (Entity entity : client.world.getEntities()) {
            if (!(entity instanceof ProjectileEntity projectile)) {
                continue;
            }

            if (projectile.isRemoved() || projectile.getOwner() != client.player) {
                continue;
            }

            if (collidedProjectileIds.contains(projectile.getId()) || ignoredProjectileIds.contains(projectile.getId())) {
                continue;
            }

            if (trackedProjectile != null && projectile.getId() == trackedProjectile.getId()) {
                continue;
            }

            if (newestProjectile == null || projectile.age < newestProjectile.age) {
                newestProjectile = projectile;
            }
        }

        if (trackedProjectile == null) {
            return newestProjectile;
        }

        if (newestProjectile != null && newestProjectile.age < trackedProjectile.age) {
            return newestProjectile;
        }

        return trackedProjectile;
    }

    private static void beginTracking(MinecraftClient client, ProjectileEntity projectile) {
        if (trackedProjectileId == null || trackedProjectileId != projectile.getId()) {
            trackedProjectileId = projectile.getId();
            playerAnchorPos = client.player.getEntityPos();
        }
    }

    private static void ensureFirstPerson(MinecraftClient client) {
        if (client.options.getPerspective() != Perspective.FIRST_PERSON) {
            if (previousPerspective == null) {
                previousPerspective = client.options.getPerspective();
            }
            client.options.setPerspective(Perspective.FIRST_PERSON);
        }
    }

    private static void cleanupCollidedProjectiles(MinecraftClient client) {
        collidedProjectileIds.removeIf(id -> client.world.getEntityById(id) == null);
    }

    private static void cleanupIgnoredProjectiles(MinecraftClient client) {
        ignoredProjectileIds.removeIf(id -> client.world.getEntityById(id) == null);
    }

    private static boolean shouldDetach(MinecraftClient client, ProjectileEntity projectile) {
        if (playerAnchorPos != null && client.player.getEntityPos().squaredDistanceTo(playerAnchorPos) > 1.0E-6) {
            return true;
        }

        if (client.options.forwardKey.isPressed()
                || client.options.backKey.isPressed()
                || client.options.leftKey.isPressed()
                || client.options.rightKey.isPressed()
                || client.options.jumpKey.isPressed()
                || client.options.sneakKey.isPressed()
                || client.options.sprintKey.isPressed()) {
            return true;
        }

        ChunkPos chunkPos = new ChunkPos(projectile.getBlockPos());
        return !client.world.isChunkLoaded(chunkPos.x, chunkPos.z);
    }

    private static void ignoreCurrentProjectiles(MinecraftClient client) {
        if (client.world == null || client.player == null) {
            return;
        }

        for (Entity entity : client.world.getEntities()) {
            if (entity instanceof ProjectileEntity projectile
                    && !projectile.isRemoved()
                    && projectile.getOwner() == client.player) {
                ignoredProjectileIds.add(projectile.getId());
            }
        }
    }

    private static void resetCamera(MinecraftClient client) {
        trackedProjectileId = null;
        playerAnchorPos = null;

        if (previousPerspective != null && client.options.getPerspective() != previousPerspective) {
            client.options.setPerspective(previousPerspective);
        }

        previousPerspective = null;
    }
}
