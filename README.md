# WeatherApp 🌤️

[![Android CI/CD](https://github.com/katsfak/WeatherAppMobile/actions/workflows/android.yml/badge.svg)](https://github.com/katsfak/WeatherAppMobile/actions/workflows/android.yml)
[![License: Apache 2.0](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](LICENSE)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-purple.svg)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-brightgreen.svg)](https://developer.android.com/jetpack/compose)
[![Min SDK](https://img.shields.io/badge/Min%20SDK-24-orange.svg)](https://developer.android.com/about/dashboards)

A modern, open-source native Android weather application built with **Kotlin**, **Jetpack Compose (Material 3)**, and **Clean Architecture**. Powered by the free and public **[Open-Meteo API](https://open-meteo.com/)**, this app requires zero key configuration to run out-of-the-box!

---

## 📸 Features

- 📍 **Location-Based Weather**: Automatically retrieves weather data for the user's current device location using Fused Location Provider.
- 🔍 **City Search**: Search weather forecasts for any city worldwide with instant geocoding.
- 🕒 **Hourly Forecast**: View detailed hour-by-hour temperature, humidity, wind speed, and weather condition trends.
- 📅 **Daily Forecast**: Multi-day weather predictions with high/low temperatures and conditions.
- 🍃 **Air Quality Index**: Real-time European Air Quality Index (AQI) and UV index metrics.
- 💾 **Offline Caching**: Offline-first design using **Room Database** and **Preferences DataStore**.
- 🎨 **Modern Material 3 Design**: Native Jetpack Compose UI with adaptive layouts and dynamic weather cards.

---

## 🛠️ Tech Stack & Libraries

- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
- **Architecture**: Clean Architecture + MVVM + Repository Pattern
- **Dependency Injection**: [Hilt](https://dagger.dev/hilt/)
- **Networking**:
  - [Open-Meteo APIs](https://open-meteo.com/) (Weather, Geocoding, Air Quality)
  - [Retrofit 2](https://square.github.io/retrofit/) & [OkHttp 4](https://square.github.io/okhttp/)
  - [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)
- **Local Storage & Persistence**:
  - [Room Database](https://developer.android.com/training/data-storage/room)
  - [Preferences DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
- **Location & Async**:
  - Google Play Services Fused Location Provider
  - Kotlin Coroutines & Flow
- **Image Loading**: [Coil Compose](https://coil-kt.github.io/coil/)
- **Analytics**: Firebase Analytics

---

## 🏗️ Project Architecture

The project strictly follows **Clean Architecture** principles:

```
app/src/main/java/com/example/weatherapp/
├── data/                  # Data layer (Open-Meteo APIs, Room DB, DataStore, DTOs, Mappers)
│   ├── local/             # Room Database, DAO, Entities, DataStore Repository
│   ├── location/          # Fused Location Provider Implementation
│   ├── remote/            # Retrofit Interfaces & Data Transfer Objects
│   └── repository/        # WeatherRepository Implementation
├── di/                    # Dependency Injection Modules (Hilt)
├── domain/                # Domain layer (Models, Interfaces)
│   ├── location/          # LocationTracker Contract
│   ├── model/             # WeatherInfo, WeatherData, WeatherType, CityLocation
│   └── repository/        # WeatherRepository Contract
└── presentation/          # UI layer (Compose Screens, ViewModels, States)
    ├── WeatherViewModel.kt
    ├── WeatherState.kt
    └── ... (UI Composable components & theme)
```

---

## 🚀 Quick Start (Public Build)

Because this app utilizes Open-Meteo's open-access endpoints, **no API keys are required** to compile and run the project!

### Prerequisites

- **Android Studio**: Ladybug (2024.2+) or newer
- **JDK**: Java 17 (Temurin recommended)
- **Android SDK**: Compile SDK `37`, Min SDK `24`
- **Gradle**: 9.6.0 (via Gradle Wrapper)

### 1. Clone & Build

```bash
git clone https://github.com/katsfak/WeatherAppMobile.git
cd WeatherApp
./gradlew assembleDebug
```

### 2. Optional Configuration (`local.properties`)

If you want to supply custom credentials or key overrides:
```properties
WEATHER_API_KEY=your_optional_api_key
```

---

## 🧪 Build & Test Commands

Run the following commands using the Gradle wrapper (`./gradlew` on Linux/macOS or `gradlew.bat` on Windows):

| Task | Command |
| :--- | :--- |
| **Run Unit Tests** | `./gradlew testDebugUnitTest` |
| **Build Debug APK** | `./gradlew assembleDebug` |
| **Build Release APK** | `./gradlew assembleRelease` |
| **Build App Bundle (AAB)** | `./gradlew bundleRelease` |

---

## 🔄 Public CI/CD Pipeline (GitHub Actions)

The repository comes pre-configured with a public **GitHub Actions** CI/CD pipeline ([`.github/workflows/android.yml`](.github/workflows/android.yml)):

- **Automated Testing**: Triggers unit test suites on every `push` or `pull_request` to `main`, `master`, and `develop`.
- **Debug Builds**: Automatically compiles and uploads debug APK artifacts.
- **Release & Tagging**: On creating a git tag (e.g., `v1.0.0`), GitHub Actions will build release binaries and automatically create a **GitHub Release** with APK and AAB assets attached.

---

## 🤝 Contributing

Contributions are welcome! Feel free to open an issue or submit a pull request:

1. Fork the Repository
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📝 License

Distributed under the Apache 2.0 License. See `LICENSE` for more information.
```
Copyright 2026 WeatherApp

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License.
```
