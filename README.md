# Fancy Boat - RuneLite Plugin

A RuneLite plugin that adds rainbow color skinning to your sailing boat in Old School RuneScape.

## Features

- **Rainbow Colors**: Apply vibrant rainbow colors to your sailing boat
- **Customizable Speed**: Adjust the speed of the rainbow color cycle
- **Color Schemes**: Choose from multiple color scheme options:
  - Full Rainbow (complete spectrum)
  - Red-Orange-Yellow
  - Blue-Purple-Pink
  - Green-Cyan-Blue
- **Easy Toggle**: Enable or disable the effect with a simple toggle

## Installation

### Prerequisites

- Java 11 or higher
- IntelliJ IDEA (recommended) or another Java IDE
- RuneLite client source code (for development)

### Building the Plugin

1. Clone or download this repository
2. Open the project in IntelliJ IDEA
3. Ensure the project SDK is set to Java 11:
   - Go to `File > Project Structure > Project`
   - Set the Project SDK to Java 11
4. Install the Lombok plugin in IntelliJ:
   - Go to `File > Settings > Plugins`
   - Search for "Lombok" and install it
   - Restart IntelliJ
5. Build the project:
   ```bash
   ./gradlew build
   ```

### Running the Plugin

1. Ensure you have the RuneLite client repository cloned
2. Add this plugin as a dependency or copy the built JAR to the RuneLite plugins directory
3. Run the RuneLite client with the plugin enabled

## Configuration

The plugin can be configured through RuneLite's plugin configuration panel:

- **Enable Rainbow Colors**: Toggle to enable/disable the rainbow effect
- **Rainbow Speed**: Adjust the speed of the color cycle (1 = slow, 10 = fast)
- **Color Scheme**: Choose your preferred color scheme

## Usage

1. Enable the plugin in RuneLite's plugin panel
2. Configure your preferred settings
3. When you're on or near a sailing boat, the rainbow colors will automatically be applied
4. The colors will cycle continuously based on your speed setting

## Development

### Project Structure

```
fancyboat-plugin/
├── src/main/java/com/fancyboat/
│   ├── FancyBoatPlugin.java          # Main plugin class
│   ├── FancyBoatConfig.java           # Configuration interface
│   └── FancyBoatOverlay.java          # Overlay for rendering rainbow effects
├── src/main/resources/
│   └── runelite-plugin.properties     # Plugin metadata
├── build.gradle                       # Gradle build configuration
├── settings.gradle                    # Gradle settings
└── README.md                          # This file
```

### Technical Details

- The plugin uses HSV color space for smooth rainbow color transitions
- Colors are applied using RuneLite's overlay system
- The plugin detects boat objects near the player and applies rainbow effects
- Color cycling is updated on each game tick

## Contributing

Contributions are welcome! Please feel free to submit issues or pull requests.

## License

This project is licensed under the BSD 2-Clause License.

## Disclaimer

This plugin is a fan-made modification for RuneLite. It is not affiliated with or endorsed by Jagex or RuneLite. Use at your own risk.
