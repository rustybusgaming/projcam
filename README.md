# ProjCam

ProjCam is a small client-side Fabric mod that moves your camera onto your projectile as soon as you fire it.

It is built for players who want a clean projectile follow-cam without extra menus or keybind setup.

## Features

- Automatically follows your latest projectile in first person
- Hides the projectile model and hand while the camera is attached
- Restores your normal view when the projectile hits, returns, or you cancel out
- Works especially well for arrows and tridents
- Press `Esc` to exit projectile camera instantly

## Controls

- Fire a projectile to attach the camera
- Press `Esc` to detach manually
- Moving, jumping, sneaking, or sprinting also detaches the camera

## Trident Behavior

Tridents now always detach the camera when their flight is effectively over:

- when they hit a block
- when they hit an entity
- when Loyalty starts pulling them back
- when they get picked back up

## Requirements

- Minecraft `1.21.11`
- Fabric Loader `0.18.4+`
- Fabric API `0.139.4+1.21.11`

## Installation

1. Install Fabric Loader for Minecraft `1.21.11`
2. Install Fabric API
3. Put `ProjCam` in your `mods` folder
4. Launch the game

## Compatibility

ProjCam is client-side only.

That means it can be used on servers without needing the mod installed server-side, as long as the server allows vanilla/Fabric clients normally.

## Notes

- The mod temporarily forces first-person view while following a projectile
- Your previous perspective is restored automatically after detaching
- If the tracked projectile leaves loaded chunks, the camera detaches to avoid getting stuck

## License

All rights reserved unless stated otherwise by the project owner.
# ProjCam
