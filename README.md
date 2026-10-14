# My Weather

An Android weather forecast app. It shows a list of cities in Russia and around the world,
loads the current weather for the selected city through the
[Yandex Weather API](https://yandex.ru/dev/weather/) and saves the request
history in a local database. It also has map search and location detection.

## Features

- City list with a "Russia / World" switch (the choice is stored in SharedPreferences)
- City screen: temperature, "feels like", weather condition
- History of viewed cities (Room)
- Current location detection via GPS and a geocoder
- Google Maps: address search, markers, map modes (normal / satellite / terrain / traffic)
- Push notifications via Firebase Cloud Messaging
- System component demos: services (including foreground), BroadcastReceiver,
  ContentProvider, reading contacts

## Tech stack

| Area           | Technologies                                 |
|----------------|----------------------------------------------|
| Language       | Kotlin                                       |
| Architecture   | MVVM (ViewModel + LiveData), Repository      |
| DI             | Koin                                         |
| Concurrency    | Kotlin Coroutines                            |
| Networking     | Retrofit, OkHttp, Gson                       |
| Storage        | Room, SharedPreferences                      |
| Images         | Picasso                                      |
| Maps and push  | Google Maps SDK, Firebase Cloud Messaging    |
| UI             | ViewBinding, Material Components             |

## Project structure

```
app/src/main/java/com/tlenparty/weather/
├── di/             # Koin module
├── framework/      # Application, push, ContentProvider, BroadcastReceiver
│   └── ui/         # MainActivity, adapters and screen fragments
├── model/
│   ├── database/   # Room: history entity and DAO
│   ├── entites/    # City / Weather models and city lists
│   ├── repository/
│   └── rest/       # Yandex Weather Retrofit client and DTOs
├── viewmodel/      # screen states (Loading / Success / Error)
├── experiment/     # service demos and helper classes
└── utils/
```

## Build

Test API keys (Yandex Weather, Google Maps) are set in `app/build.gradle`
(`buildConfigField` and `resValue` in `defaultConfig`).

Requires JDK 11–17 and Android SDK 31.

```bash
./gradlew assembleDebug
```

APK: `app/build/outputs/apk/debug/app-debug.apk`.
