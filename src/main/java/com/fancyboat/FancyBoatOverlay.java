package com.fancyboat;

import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.Perspective;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

import javax.inject.Inject;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class FancyBoatOverlay extends Overlay
{
    private final Client client;
    private final FancyBoatPlugin plugin;
    private final FancyBoatConfig config;

    @Inject
    private FancyBoatOverlay(Client client, FancyBoatPlugin plugin, FancyBoatConfig config)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (!config.enabled() || client.getLocalPlayer() == null)
        {
            return null;
        }

        // Check if player is on or near a boat
        if (!plugin.isPlayerOnBoat())
        {
            return null;
        }

        // Get current color based on hue and color scheme
        double currentHue = plugin.getRainbowHue();
        Color rainbowColor = getRainbowColor(currentHue, config.colorScheme());

        // Find and highlight boat objects
        WorldPoint playerLocation = client.getLocalPlayer().getWorldLocation();
        LocalPoint playerLocal = LocalPoint.fromWorld(client, playerLocation);

        if (playerLocal == null)
        {
            return null;
        }

        // Render rainbow effect on nearby boat objects
        for (GameObject obj : client.getScene().getGameObjects())
        {
            if (obj == null)
            {
                continue;
            }

            WorldPoint objLocation = obj.getWorldLocation();
            if (objLocation == null)
            {
                continue;
            }

            // Check if object is near player (potential boat)
            int distance = Math.max(
                Math.abs(objLocation.getX() - playerLocation.getX()),
                Math.abs(objLocation.getY() - playerLocation.getY())
            );

            if (distance < 5 && objLocation.getPlane() == playerLocation.getPlane())
            {
                renderBoatRainbowEffect(graphics, obj, rainbowColor);
            }
        }

        return null;
    }

    private void renderBoatRainbowEffect(Graphics2D graphics, GameObject obj, Color color)
    {
        LocalPoint localLocation = obj.getLocalLocation();
        if (localLocation == null)
        {
            return;
        }

        // Get the tile polygon for the object
        Polygon tilePoly = Perspective.getCanvasTilePoly(client, localLocation);
        if (tilePoly == null)
        {
            return;
        }

        // Apply rainbow color with transparency
        Color overlayColor = new Color(
            color.getRed(),
            color.getGreen(),
            color.getBlue(),
            100 // Semi-transparent
        );

        // Draw rainbow overlay on the boat
        graphics.setColor(overlayColor);
        graphics.fill(tilePoly);

        // Draw rainbow border
        graphics.setColor(color);
        graphics.setStroke(new BasicStroke(2));
        graphics.draw(tilePoly);
    }

    /**
     * Convert HSV to RGB color
     */
    private Color hsvToRgb(double h, double s, double v)
    {
        int c = (int) (v * s);
        int x = (int) (c * (1 - Math.abs((h * 6) % 2 - 1)));
        int m = (int) (v - c);

        double r = 0, g = 0, b = 0;

        if (h >= 0 && h < 1.0 / 6)
        {
            r = c;
            g = x;
            b = 0;
        }
        else if (h >= 1.0 / 6 && h < 2.0 / 6)
        {
            r = x;
            g = c;
            b = 0;
        }
        else if (h >= 2.0 / 6 && h < 3.0 / 6)
        {
            r = 0;
            g = c;
            b = x;
        }
        else if (h >= 3.0 / 6 && h < 4.0 / 6)
        {
            r = 0;
            g = x;
            b = c;
        }
        else if (h >= 4.0 / 6 && h < 5.0 / 6)
        {
            r = x;
            g = 0;
            b = c;
        }
        else if (h >= 5.0 / 6 && h < 1.0)
        {
            r = c;
            g = 0;
            b = x;
        }

        return new Color(
            (int) ((r + m) * 255),
            (int) ((g + m) * 255),
            (int) ((b + m) * 255)
        );
    }

    /**
     * Get rainbow color based on hue and color scheme
     */
    private Color getRainbowColor(double hue, FancyBoatConfig.ColorScheme scheme)
    {
        double adjustedHue = hue;
        double saturation = 1.0;
        double value = 1.0;

        switch (scheme)
        {
            case FULL_RAINBOW:
                // Use full hue range (0.0 to 1.0)
                break;
            case RED_ORANGE_YELLOW:
                // Map to red-orange-yellow range (0.0 to 0.17)
                adjustedHue = (hue % 1.0) * 0.17;
                break;
            case BLUE_PURPLE_PINK:
                // Map to blue-purple-pink range (0.5 to 0.83)
                adjustedHue = 0.5 + ((hue % 1.0) * 0.33);
                break;
            case GREEN_CYAN_BLUE:
                // Map to green-cyan-blue range (0.33 to 0.67)
                adjustedHue = 0.33 + ((hue % 1.0) * 0.34);
                break;
        }

        return hsvToRgb(adjustedHue, saturation, value);
    }
}
