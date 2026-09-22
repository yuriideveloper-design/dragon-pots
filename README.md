# Neon Drift Syndicate

Мобильная игра в жанре киберпанк-менеджмент гоночного синдиката для Android.
Оффлайн, без бэкенда, без оплаты. Полностью локальное хранилище.

## Требования

- **Android Studio** Hedgehog (2023.1.1) или новее
- **JDK** 17
- **Android SDK** с API Level 35
- **Kotlin** 2.0.21
- **AGP** 8.12.0

## Локальная сборка

```bash
# Debug APK
./gradlew assembleDebug

# Release APK (требует keystore.properties)
./gradlew assembleRelease

# Release AAB
./gradlew bundleRelease
```

## Пути к артефактам

```
APK Debug:   app/build/outputs/apk/debug/app-debug.apk
APK Release: app/build/outputs/apk/release/app-release.apk
AAB Release: app/build/outputs/bundle/release/app-release.aab
```

## Release Signing

Создайте `keystore.properties` в корне проекта:

```properties
storeFile=upload-keystore.jks
storePassword=YOUR_STORE_PASSWORD
keyAlias=YOUR_KEY_ALIAS
keyPassword=YOUR_KEY_PASSWORD
```

Поместите `upload-keystore.jks` в корень проекта рядом с `keystore.properties`.

## Описание

**Neon Drift Syndicate** — стратегическая гоночная RPG в стиле киберпанк:

- 65 уникальных автомобилей (8 классов, 12 производителей)
- 40 гоночных событий (7 типов гонок)
- 30 членов команды (6 ролей)
- 120 запчастей для улучшения
- 25 достижений
- Система репутации и уровней
- Гараж с 7 зданиями (5 уровней каждое)
- Маркетплейс с покупкой/продажей авто и запчастей

## Технологии

- Kotlin 2.0.21 + Jetpack Compose + Material3
- Navigation Compose 2.7.7
- Lifecycle ViewModel
- SharedPreferences (локальное хранение)
- AMOLED-тема с неоновым UI
