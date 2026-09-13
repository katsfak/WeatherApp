# WeatherApp 🌤️

A modern, native Android weather application built with **Kotlin**, **Jetpack Compose**, and **Clean Architecture**. The app provides real-time weather updates, hourly and daily forecasts, air quality index data, and location-based weather tracking.

---

## 📸 Features

- 📍 **Location-Based Weather**: Automatically retrieves weather data for the user's current device location using Fused Location Provider.
- 🔍 **City Search**: Search weather forecasts for any city worldwide with instant geocoding.
- 🕒 **Hourly Forecast**: View detailed hour-by-hour temperature and weather condition trends.
- 📅 **Daily Forecast**: Multi-day weather predictions with high/low temperatures and conditions.
- 🍃 **Air Quality Index**: Real-time air quality metrics and indicators.
- 💾 **Local Caching**: Offline-first design using **Room Database** and **Preferences DataStore**.
- 🎨 **Modern Material 3 Design**: Built using Jetpack Compose with responsive layouts and dark/light theme support.

---

## 🛠️ Tech Stack & Libraries

- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
- **Architecture**: Clean Architecture + MVVM + Repository Pattern
- **Dependency Injection**: [Hilt](https://dagger.dev/hilt/)
- **Networking**:
  - [Retrofit](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/)
  - [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)
- **Local Data & Caching**:
  - [Room Database](https://developer.android.com/training/data-storage/room)
  - [Preferences DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
- **Location & Async**:
  - Google Play Services Fused Location Provider
  - Kotlin Coroutines & Flow
- **Image Loading**: [Coil Compose](https://coil-kt.github.io/coil/)
- **Analytics**: Firebase Analytics

---

## 🏗️ Project Architecture

The project follows **Clean Architecture** principles, separating concerns into three primary layers:

```
app/src/main/java/com/example/weatherapp/
├── data/                  # Data layer (Remote API, Local Room DB, DataStore, DTOs, Mappers)
│   ├── local/             # Room Database, DAO, Entities, DataStore
│   ├── location/          # Fused Location Provider Tracker Implementation
│   ├── remote/            # Retrofit APIs & Data Transfer Objects
│   └── repository/        # Repository Implementations
├── di/                    # Dependency Injection Modules (Hilt)
├── domain/                # Domain layer (Models, Interfaces, Use Cases)
│   ├── location/          # LocationTracker interface
│   ├── model/             # Core Business Models (WeatherInfo, WeatherData, WeatherType)
│   └── repository/        # Repository Interfaces
└── presentation/          # UI layer (Jetpack Compose Screens, ViewModels, States)
    ├── WeatherViewModel.kt
    ├── WeatherState.kt
    └── ... (UI Composable components)
```

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio**: Ladybug / 2024.2+ or higher
- **JDK**: Java 17
- **Android SDK**: Compile SDK `37`, Min SDK `24`
- **Gradle**: 9.6.0 (via Gradle Wrapper)

### Setup Instructions

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/WeatherApp.git
   cd WeatherApp
   ```

2. **Configure Local Properties**
   Create a `local.properties` file in the root directory if it does not already exist, and add your API keys:
   ```properties
   WEATHER_API_KEY=your_api_key_here
   ```

3. **Firebase Configuration**
   Ensure `google-services.json` is present in the `app/` directory (a template or config file is included).

4. **Build & Run**
   Open the project in **Android Studio**, sync Gradle, and run on an emulator or physical Android device.

---

## 🧪 Running Tests & Build Commands

Using the Gradle Wrapper (`./gradlew` on Linux/macOS or `gradlew.bat` on Windows):

- **Run Unit Tests**:
  ```bash
  ./gradlew testDebugUnitTest
  ```
- **Build Debug APK**:
  ```bash
  ./gradlew assembleDebug
  ```
- **Build Release APK**:
  ```bash
  ./gradlew assembleRelease
  ```
- **Build Release App Bundle (AAB)**:
  ```bash
  ./gradlew bundleRelease
  ```

---

## 🔄 CI/CD Pipeline

The project uses **GitHub Actions** for continuous integration and continuous deployment (`.github/workflows/android.yml`).

### Workflow Jobs:
1. **Unit Tests & Code Checks**: Runs unit test suites on pushes to `main`, `master`, and `develop` branches or PRs.
2. **Build Debug APK**: Compiles the debug APK and uploads it as a workflow artifact.
3. **Build Release Artifacts**: Generates release APK and App Bundle (AAB) automatically when changes are pushed to `main` or `master`.

To set up the CI pipeline in your GitHub repository:
- Add `WEATHER_API_KEY` to **Repository Secrets** under `Settings > Secrets and variables > Actions`.

---

## 📝 License

```
Copyright 2026 WeatherApp

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License.
```
