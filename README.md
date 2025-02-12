# GitHub Users Explorer (Android)

A native Android application built with Kotlin that allows administrators to browse GitHub users, view detailed user information, and persist data for instant reload.

## 📌 Features
- Fetch and display GitHub users in a paginated list (20 users per fetch).
- Infinite scrolling.
- Caching mechanism for instant data retrieval on app relaunch.
- Detailed user profile page with additional information.
- MVVM + Redux architecture with Clean Architecture principles.
- Unit tests using JUnit & MockK.

## 📌 Documents
[Engineering Spec](EngineeringSpec.md)

## 🚀 Tech Stack
- **Kotlin** (Language)
- **MVVM + Redux + Clean Architecture** (Scalable app structure)
- **Retrofit** (API requests)
- **Room** (Local caching)
- **Jetpack Compose** (UI components)
- **Hilt** (Dependency Injection)
- **Jetpack Navigation** (Screen navigation)
- **JUnit + MockK** (Unit testing framework)

## 🔧 Installation & Setup
1. Open the project in **Android Studio**.
2. Sync Gradle dependencies.
3. Run the app on an emulator or a real device.


## 🧪 Running Tests
Unit tests are written using **JUnit** and **MockK**.
Run tests using:
```sh
./gradlew testDebugUnitTest
```

## 📜 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🤝 Contributing
Feel free to open issues and submit pull requests for improvements!


