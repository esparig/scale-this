# Scale This 🎵

A simple Android app for practicing musical scales. The app displays a random scale in a circle, and tapping the circle shows the next scale in a shuffled sequence.

![Android](https://img.shields.io/badge/Platform-Android-green.svg)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-purple.svg)
![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-blue.svg)

## Features

✅ **Circle Display**: Shows a musical scale (tonality) from the 12 chromatic scales in a large, tappable circle  
✅ **Random Order**: Scales are shuffled randomly on first launch  
✅ **Tap to Continue**: Each tap shows the next scale in the shuffled sequence  
✅ **Auto-Reshuffle**: After going through all 12 scales, automatically reshuffles for a new random order  
✅ **Persistent State**: Saves your progress across app restarts - you'll see exactly where you left off!  
✅ **Previous Scale Indicator**: Small circle in the top left shows the scale you just practiced  
✅ **Completion Counter**: Top right displays how many times you've completed all 12 scales (×N)

## Screenshots

```
┌─────────────────────────────────┐
│  [Db]                ×3         │  ← Previous scale & Counter
│                                 │
│                                 │
│          [  F#  ]               │  ← Current scale (tap to advance)
│                                 │
│                                 │
└─────────────────────────────────┘
```

## How It Works

1. **First Launch**: The app shuffles all 12 scales randomly and shows the first one
2. **Tap the Circle**: Each tap advances to the next scale in the shuffled list
3. **Play the Tone**: Tap the speaker icon below the circle to hear the exact frequency of the current scale (e.g., C = 261.63 Hz)
3. **Previous Scale**: After your first tap, a smaller circle appears in the top left showing the scale you just practiced
4. **Completion Counter**: The top right shows "×N" indicating how many times you've completed all 12 scales
5. **Complete the Cycle**: After viewing all 12 scales, the counter increments and the app automatically reshuffles
6. **Close & Reopen**: Your exact position, shuffled order, previous scale, and completion count are all saved - when you reopen the app, you continue right where you left off!
## Musical Scales

The app includes all 12 chromatic scales with their corresponding frequencies:
```
The app includes all 12 chromatic scales:
Db  → 277.18 Hz    |    G   → 392.00 Hz
C, Db, D, Eb, E, F, F#, G, Ab, A, Bb, B
## Technical Details

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material Design 3
- **Data Persistence**: AndroidX DataStore Preferences
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 36

## State Persistence

The app uses **DataStore Preferences** to save:
- The current shuffled order of scales
- Your current position in that sequence
- The previous scale you practiced
- The completion counter (how many times you've finished all 12 scales)

This means:
- ✅ Close the app and reopen - you'll see the same scale you were on
- ✅ Your progress through the 12 scales is maintained
- ✅ The shuffled order persists until you complete all 12 scales
- ✅ The previous scale indicator and completion counter persist across sessions
- ✅ After completing all 12, the counter increments, a new random shuffle is generated and saved

## Building the App

1. Open the project in Android Studio
2. Sync Gradle to download dependencies (including DataStore)
3. Run the app on an emulator or physical device

### Requirements

- Android Studio Hedgehog or later
- Minimum SDK: 24 (Android 7.0)
- Target SDK: 36
- Kotlin 1.9+

## Installation

### From Source

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd MyApplication
   ```

2. Open in Android Studio and build

### APK Release

*Coming soon*

## Project Structure

```
app/src/main/java/com/example/myapplication/
├── MainActivity.kt          # Main UI with Jetpack Compose
├── ScaleManager.kt         # Data persistence & scale logic
└── ui/theme/              # Material Design 3 theme
```

## Contributing

Feel free to open issues or submit pull requests!

## License

*Add your license here*

Enjoy practicing your scales! 🎵
