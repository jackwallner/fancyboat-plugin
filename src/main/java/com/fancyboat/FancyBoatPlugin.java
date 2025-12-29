package com.fancyboat;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
    name = "Fancy Boat",
    description = "Add rainbow colors to your sailing boat",
    tags = {"sailing", "cosmetic", "visual", "boat", "rainbow"}
)
public class FancyBoatPlugin extends Plugin
{
    @Inject
    private Client client;

    @Inject
    private FancyBoatConfig config;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private FancyBoatOverlay overlay;

    private double rainbowHue = 0.0;

    @Override
    protected void startUp() throws Exception
    {
        overlayManager.add(overlay);
        log.info("Fancy Boat plugin started!");
    }

    @Override
    protected void shutDown() throws Exception
    {
        overlayManager.remove(overlay);
        log.info("Fancy Boat plugin stopped!");
    }

    @Provides
    FancyBoatConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(FancyBoatConfig.class);
    }

    @Subscribe
    public void onGameTick(GameTick event)
    {
        if (!config.enabled())
        {
            return;
        }

        // Update rainbow hue based on speed
        double speedMultiplier = config.rainbowSpeed() / 10.0;
        rainbowHue += 0.01 * speedMultiplier;
        if (rainbowHue >= 1.0)
        {
            rainbowHue = 0.0;
        }
    }

    public double getRainbowHue()
    {
        return rainbowHue;
    }

    public boolean isPlayerOnBoat()
    {
        if (client.getLocalPlayer() == null)
        {
            return false;
        }

        Player localPlayer = client.getLocalPlayer();
        WorldPoint playerLocation = localPlayer.getWorldLocation();

        // Check if player is on a boat by looking for boat objects/NPCs nearby
        // This is a simplified check - in practice, you'd need to identify
        // the specific boat object IDs or NPC IDs for sailing boats
        return isNearBoat(playerLocation);
    }

    private boolean isNearBoat(WorldPoint location)
    {
        // Check for boat objects in the scene
        Scene scene = client.getScene();
        if (scene == null)
        {
            return false;
        }

        Tile[][][] tiles = scene.getTiles();
        if (tiles == null)
        {
            return false;
        }

        int z = client.getPlane();
        if (z < 0 || z >= tiles.length)
        {
            return false;
        }

        Tile[][] planeTiles = tiles[z];
        if (planeTiles == null)
        {
            return false;
        }

        // Check tiles around player for boat objects
        int localX = location.getX() - (client.getBaseX() << 7);
        int localY = location.getY() - (client.getBaseY() << 7);
        int tileX = localX >> 7;
        int tileY = localY >> 7;

        // Check a 3x3 area around the player
        for (int dx = -1; dx <= 1; dx++)
        {
            for (int dy = -1; dy <= 1; dy++)
            {
                int checkX = tileX + dx;
                int checkY = tileY + dy;

                if (checkX >= 0 && checkX < planeTiles.length &&
                    checkY >= 0 && checkY < planeTiles[checkX].length)
                {
                    Tile tile = planeTiles[checkX][checkY];
                    if (tile != null)
                    {
                        // Check for game objects that might be boats
                        GameObject[] gameObjects = tile.getGameObjects();
                        if (gameObjects != null)
                        {
                            for (GameObject obj : gameObjects)
                            {
                                if (obj != null && isBoatObject(obj.getId()))
                                {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean isBoatObject(int objectId)
    {
        // These would need to be the actual object IDs for sailing boats
        // This is a placeholder - you'd need to identify the correct IDs
        // Common sailing boat object IDs might be in ranges like 40000-50000
        // For now, we'll use a simple heuristic
        return false; // Placeholder - needs actual boat object IDs
    }
}
