package com.fancyboat;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("fancyboat")
public interface FancyBoatConfig extends Config
{
    @ConfigSection(
        name = "General",
        description = "General settings for the Fancy Boat plugin",
        position = 0
    )
    String generalSection = "general";

    @ConfigItem(
        keyName = "enabled",
        name = "Enable Rainbow Colors",
        description = "Enable rainbow color skinning for the sailing boat",
        section = generalSection,
        position = 1
    )
    default boolean enabled()
    {
        return true;
    }

    @ConfigItem(
        keyName = "rainbowSpeed",
        name = "Rainbow Speed",
        description = "Speed of the rainbow color cycle (1 = slow, 10 = fast)",
        section = generalSection,
        position = 2
    )
    default int rainbowSpeed()
    {
        return 5;
    }

    @ConfigSection(
        name = "Color Scheme",
        description = "Color scheme options",
        position = 1
    )
    String colorSection = "color";

    @ConfigItem(
        keyName = "colorScheme",
        name = "Color Scheme",
        description = "Choose the color scheme for the boat",
        section = colorSection,
        position = 3
    )
    default ColorScheme colorScheme()
    {
        return ColorScheme.FULL_RAINBOW;
    }

    enum ColorScheme
    {
        FULL_RAINBOW,
        RED_ORANGE_YELLOW,
        BLUE_PURPLE_PINK,
        GREEN_CYAN_BLUE
    }
}

