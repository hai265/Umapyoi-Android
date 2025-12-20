# Umapyoi Android

A project I built using modern Jetpack compose, Room, hilt, and data from [umapyoi.net](https://umapyoi.net/docs/index.html)  
Structured based on [Google's recommended architecture](https://developer.android.com/topic/architecture).
//TODO: Update image with color char details and favorite button
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
The architecture is structed so that data lives one one layer and data is exposed through Flows to be consumed by the UI layer. 
Take the character list flow, where a user opens the list and taps the favorite button on a character
<img width="852" height="411" alt="nPB13e8m44Jl_OeUzU0F44CmAYUDYKJlJLbYazAIxGhXxmKFM495uyLhPz_CPgU98u5qBpMxb69JgGomLXBuAkRRcq0X2V0CbNMABBOHryhHgHWanm9J8tWdRCILSO34PG3hzBTXgRRDkazS3_BUaA8sZZ_c7Zhigg0vsXnqqEgf-4uZP6AMIGGmqUin8q700ZDpUnIYjgW81dsn_6O1_oeEA9rpcIl2OttaVoLChNvgWILWyluNFm00" src="https://github.com/user-attachments/assets/71d0b2e6-6522-4e14-9d3d-6e2f90e1d9c5" />  
1. Source of data lives in umapyoi.net, the repository calls the api to get the character list
2. Repository inserts characters into Room through the dao
3. Dao exposes the list of characters as a flow, the repository also exposes the list of characters as a flow
4. Viewmodel consumes the flow and exposes it to the composable screen to render characters
5. Screen sends a favorite event, viewmodel then passes it to the repository, where eventually the entry is updated in room
6. Since Flow is being used, an updated list is emitted, and the ui receives this event to update the favorite button icon

### The data layer is the sole source of truth, and all screens derive their UI based on this data. This ensures that when the data is shown in multiple screens, only one version of that data is shown.
* A user is able to set a favorite character in both the list and detailed page. Changes to this are automatically reflected in both pages since the character data is modified and comes from one source


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
