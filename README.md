# Umapyoi Android

A simple project I built using modern Jetpack compose, Room, hilt, and data from [umapyoi.net](https://umapyoi.net/docs/index.html)  
Structured based on [Google's recommended architecture](https://developer.android.com/topic/architecture).
<img width="1920" height="1080" alt="Umapyoi Github Image(1)" src="https://github.com/user-attachments/assets/8f68a078-16fa-42dd-be20-fad3a45d1f2b" />

## Download
See releases for the latest APK

## Tech Stack / Components
* Kotlin w/ flow and coroutines for asynchronous tasks
* Jetpack Libraries:
   * Compose: Declarative UI
   * ViewModel: Manage state and business logic and exposes state to the UI
   * Compose Navigation: Navigate between screens
   * Room: Cache data from the network for an offline-first experience
   * Hilt: Dependency Injection
* Retrofit: Transform Umapyoi.net's api into a java interface
* Testing:
   * Turbine - Framework to test kotlin flows
   * mockk - mocking framework for kotlin

## Architecture
The app follows an MVVM architecture, enforced by following Google's recommendation to use a UI and data layer.
TODO: Describe architecture
## Version History

* 0.1
    * View character from Umamusume and their support cards
    * Filter characters/cards by searching
    * Support favoriting characters
 

## License

This project is licensed under the [NAME HERE] License - see the LICENSE.md file for details

## Acknowledgments
Inspiration, code snippets, etc.
* [README-Template.md](https://gist.github.com/DomPizzie/7a5ff55ffa9081f2de27c315f5018afc)
* Icons downloaded from [flaticon.com](Icons taken from flaticon.com)
