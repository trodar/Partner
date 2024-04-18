# Partner
I'm Jehovah's Witnesses and this is an application for personal use. It is for automatically track statistics and time of preaching service.

The project tries to combine popular Android tools and to demonstrate best development practices by utilizing up to date tech-stack like Compose, Kotlin Flow? Hilt and unit test JUnit5, Kaspresso.

The sample app layers its presentation through MVVM presentation pattern. Additionally, the application features animations like expanding and collapsing row items.
## Description

<p>
<img align="center" src="misc/Timer.gif" width="270"/>
<img align="center" src="misc/Swith theme.gif" width="270"/>
<img align="center" src="misc/Report.gif" width="270"/>
</p>


A Clean Architecture App to show use of  multi-module-architecture in a Jetpack Compose.

## Images
<p>
<img align="center" src="misc/Home.png" width="270" />
<img align="center" src="misc/Notepad.png" width="270" />
<img align="center" src="misc/Report.png" width="270" />
</p>

## Tech Stack

* Multi-Module-Architecture
* OTP Authentication
* [Kotlin](https://kotlinlang.org/) 100% coverage
* [Jetpack](https://developer.android.com/jetpack)
  * [Compose](https://developer.android.com/jetpack/compose)
  * [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) for navigation between composables
  * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) that stores, exposes and manages UI state
* [Material Design](https://m3.material.io/)
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency injection
* [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) and [Flow](https://developer.android.com/kotlin/flow) for async operations
* AndroidX
* Unit test: [JUnit5](https://junit.org/junit5/), [Kaspresso](https://github.com/KasperskyLab/Kaspresso)
* Solid Principles

## Presentation patterns layers
* View - Composable screens that consume state, apply effects and delegate events upstream.
* ViewModel - [AAC ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) that manages and set the state of the corresponding screen. Additionally, it intercepts UI events as callbacks and produces side-effects. The ViewModel is scoped to the lifetime of the corresponding screen composable in the backstack.
* Model - Data source classes that retrieve content. In a Clean architecture context, one could use UseCases or Interactors that tap into repositories or data sources directly.


## Did you find this repository helpful?

Don't forget give a star.

## Didn't you?

Then fork this repo, make it better and don't forget give a STAR.
