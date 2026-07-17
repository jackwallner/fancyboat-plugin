# FancyBoat Plugin

## Project Overview
RuneLite plugin that adds rainbow color skinning effects to sailing boats in Old School RuneScape with customizable color schemes and cycling speeds.

## Tech Stack
- **Language:** Java
- **Platform:** RuneLite plugin system
- **Build:** Gradle

## Structure
- `src/main/java/com/fancyboat/` — Plugin source
  - `FancyBoatPlugin.java` — Main plugin class
  - `FancyBoatConfig.java` — Configuration interface
  - `FancyBoatOverlay.java` — Rendering overlay
- `src/main/resources/` — Plugin metadata
- `build.gradle` / `settings.gradle` — Build configuration

## Development
```bash
./gradlew build    # Build plugin
./gradlew test     # Run tests
```

## Conventions
- Follow RuneLite plugin conventions and API patterns.
- Config options via RuneLite's `@ConfigItem` annotations.
- Keep overlay rendering efficient — runs every game tick.

## Subagent delegation
Follow the global CLAUDE.md subagent rules: ask Jack for the model before spawning, spawn at most one at a time unless Jack explicitly approves more, and never allow a subagent to spawn another subagent.
