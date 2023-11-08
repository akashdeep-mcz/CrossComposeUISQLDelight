Kotlin Multiplatform Compose and SQLDelight Project
A Kotlin Multiplatform project using Jetpack Compose for UI and SQLDelight for databases. It supports Android, iOS, and desktop platforms.

Structure
Common Module: Platform-agnostic code.
Android Module: Android-specific code.
iOS Modules: iOS-specific code.
Desktop Module: Desktop-specific code.
Dependencies
Jetpack Compose
SQLDelight
SQLDelight Database
Uses "NoteDatabase" with package "org.example.project.database."
Models generated from SQLDelight source files in "sqldelight" folder.
Building and Running
Android: Use Android Studio or command line.
iOS: Use Xcode on macOS.
Desktop: Use native distributions or java -jar.
Compose Desktop Configuration
Configure in compose.desktop block in build.gradle.kts.
More Details
For specific platform setup and troubleshooting, refer to platform-specific documentation.

License
Open-source with specific license details.
