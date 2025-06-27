# Fingenie

Fingenie is an Android application for tracking and managing shared expenses with friends, family, or groups. It helps users split bills, manage group expenses, and keep track of who owes what, all with a simple and intuitive interface.

## Features

- **User Authentication:** Secure login and registration using Firebase Authentication.
- **Group Management:** Create groups, add/remove members, and manage group details.
- **Expense Tracking:** Add, edit, and split expenses among group members.
- **Expense Summary:** View how much you owe and how much is owed to you.
- **Profile & Settings:** Manage your profile, preferences, and app settings.
- **Firebase Integration:** Uses Firebase Firestore for real-time data storage and synchronization.

## Screenshots

*(Add screenshots of your app here)*

## Getting Started

### Prerequisites

- Android Studio (latest version recommended)
- Android device or emulator (API 23+)
- A Firebase project (for authentication and Firestore)

### Setup

1. **Clone the repository:**
   ```bash
   git clone <your-repo-url>
   cd sefinal
   ```

2. **Firebase Configuration:**
   - Add your `google-services.json` file to `app/` (already present in this repo, but replace with your own for production).

3. **Build the project:**
   - Open the project in Android Studio.
   - Let Gradle sync and download dependencies.

4. **Run the app:**
   - Connect your device or start an emulator.
   - Click "Run" in Android Studio.

## Project Structure

```
app/
  ├── src/
  │   └── main/
  │       ├── java/com/fingenie/
  │       │   ├── ui/           # UI Fragments (auth, dashboard, groups, expenses, profile, settings)
  │       │   ├── models/       # Data models (User, Group, Expense, etc.)
  │       │   ├── MainActivity.kt
  │       │   └── FingenieApplication.kt
  │       ├── res/              # Resources (layouts, strings, icons)
  │       └── AndroidManifest.xml
  ├── build.gradle
  └── google-services.json
```

## Tech Stack

- **Kotlin** (primary language)
- **Android Jetpack** (Navigation, ViewModel, LiveData, ViewBinding)
- **Firebase** (Authentication, Firestore, Storage)
- **Coil** (image loading)
- **Material Components** (UI/UX)

## Dependencies

See `app/build.gradle` for the full list.

## Contributing

Contributions are welcome! Please open issues or submit pull requests for improvements and bug fixes. 
