# My Weather

Android-приложение прогноза погоды. Показывает список городов России и мира,
по выбору города загружает текущую погоду через
[API Яндекс.Погоды](https://yandex.ru/dev/weather/) и сохраняет историю
запросов в локальной базе. Есть поиск по карте и определение местоположения.

## Возможности

- Список городов с переключением «Россия / Мир» (выбор запоминается в SharedPreferences)
- Экран города: температура, «ощущается как», состояние погоды
- История просмотренных городов (Room)
- Определение текущего местоположения через GPS и геокодер
- Google Maps: поиск адреса, маркеры, режимы карты (обычная / спутник / рельеф / пробки)
- Push-уведомления через Firebase Cloud Messaging
- Демо системных компонентов: сервисы (в т.ч. foreground), BroadcastReceiver,
  ContentProvider, чтение контактов

## Стек

| Область        | Технологии                                   |
|----------------|----------------------------------------------|
| Язык           | Kotlin                                       |
| Архитектура    | MVVM (ViewModel + LiveData), Repository      |
| DI             | Koin                                         |
| Асинхронность  | Kotlin Coroutines                            |
| Сеть           | Retrofit, OkHttp, Gson                       |
| Хранение       | Room, SharedPreferences                      |
| Изображения    | Picasso                                      |
| Карты и push   | Google Maps SDK, Firebase Cloud Messaging    |
| UI             | ViewBinding, Material Components             |

## Структура

```
app/src/main/java/com/tlenparty/weather/
├── di/             # Koin-модуль
├── framework/      # Application, push, ContentProvider, BroadcastReceiver
│   └── ui/         # MainActivity, адаптеры и фрагменты экранов
├── model/
│   ├── database/   # Room: сущность и DAO истории
│   ├── entites/    # модели City / Weather и списки городов
│   ├── repository/
│   └── rest/       # Retrofit-клиент Яндекс.Погоды и DTO
├── viewmodel/      # состояния экранов (Loading / Success / Error)
├── experiment/     # демо сервисов и вспомогательные классы
└── utils/
```

## Сборка

Тестовые ключи API (Яндекс.Погода, Google Maps) заданы в `app/build.gradle`
(`buildConfigField` и `resValue` в `defaultConfig`).

Требуется JDK 11–17 и Android SDK 31.

```bash
./gradlew assembleDebug
```

APK: `app/build/outputs/apk/debug/app-debug.apk`.
