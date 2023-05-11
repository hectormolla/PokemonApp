# PokemonApp
#### Pokemon themed app built with Kotlin and Jetpack Compose

## Functionality
This example app is composed by three screens:
- **Splash Screen**
- **Pokemon List Screen**: shows the full list of **Pokemons**. The list is paginated as an infinite scroll and allows the user to navigate to the character detail screen.
- **Pokemon Details Screen**: shows the details of a pokemon.

https://github.com/hectormolla/PokemonApp/assets/7500157/2525a717-2ed3-4440-9adb-54bb71b03f92

# Architecture
The project is based on a MVVM/Clean architecture composed by three layers.

## Presentation layer
This layer is composed by `ViewModels`, `Views` and `ScreenStates`.

The **ViewModel** component is the one in charge of keeping the Screen states updated, which are:
1. **LoadingState**: it represents whether the screen is currently loading a new state or not.
2. **ErrorState**: it represents whether some error happend while updating the screen state.
3. **ScreenState**: it represents the currently loaded data to show to the user for this particular screen. 

Depending on the screen logic, the ViewModel will call the needed UseCase/s on initialization or/and after some UI events. Through all the process, it will update the states to represent the current scenario.

## Domain layer
The **UseCase** component is in charge of executing one functional operation, requesting the needed information to the Data layer and transforming it when needed.

Actually, in this simple example app they are not used at all, but I decided to keep them anyway because it standardize the layers design and could be useful for future features.

## Data layer
This layer is composed by **Repositories** and **APIs**.

The **Repository** component is in charge of accesing data for every CRUD operation for an specific domain (like *Pokemon* in this example app). It makes use of other internal components like *APIs* (or *DAOS* 
in case we had persistence), depending on the source of the data, abstracting the Domain layer from it. The network calls made from the repository are executed under the **Dispatcher.IO**.
Also, it handles the different kind of errors that could happen from Network and wrap them in a `AppError` for easier processing in upper layers.

## Dependency Injection
To decouple the different layers I have make use of the dependency injection design pattern.
For this task, the **Koin** framework has been used because it is simple and intended for full Kotlin projects.

## Testing
The use of this architecture approach makes it easier for the project to be highly testable.

There are some **unit** and **UI** tests created as an example but due to the lack of time, these are limited.

## Screen Rotation
The detail screen has been customized to rearrange the elements as a simple desmotration of screen rotation.

## Animations
Some small animations have been added to the view components in the splash and the detail screen.

## Third-party libraries
- Ktor
- Koin
- Lottie
- Coil
- Mockk

# Future improvements
- Offline mode: in order to provide an offline mode, the repository would be in charge of saving the received pokemon information in the database, so that if at some future time the device did not have an internet connection, the information would be returned from persistence.
- Option to search a Pokemon by name: This would be an easy improvement as we are actually doing a request of the pokemon information by name.






