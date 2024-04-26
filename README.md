![CryptoMarket Logo](/assets/crypto-logo.png)

# CryptoMarket

**Description:**  
CryptoMarket is an application for monitoring cryptocurrency prices expressed in dollars. It features 20 different cryptocurrencies. Additionally, users can search through the list of currencies.

<img src="/assets/cryptomarket.png" width="224">

This project also serves to demonstrate various architectural decisions, the application of patterns aimed at achieving a scalable, maintainable, and testable project. Moreover, the project includes UI implementation using the Compose library, which is the latest method for crafting UI in Android implementations.

## Used Libraries, Concepts, and More

- Kotlin
- Coroutines
- Compose
- Ktor
- Clean architectural principles
- MVI
- Koil
- Android framework
- Hilt

## Project Structure and Organization

**Description:**  
The project is divided into three main parts: UI, data, and domain. Each of these parts consists of carefully organized sections.

**Domain:**
- `application`: Contains the domain logic including entities, components, features, etc.
- `configuration`
- `utils`

**UI:**
- `app`: The main module representing the application
- `design`: Reserved for design definitions and structures
- `elements`: Reusable UI components
- `scenes`: Contains scenes specific to the application

**Data:**
- `remote`: Reserved for implementing various connections with the "outside world"
- `device`: Implements connection with the system (Android operating system and various system features)
- `storage`: Implements various uses of the file system.

## Main Feature - CryptoMarketplace

The most interesting part is the implementation of the domain part of this feature.

## Unit Tests

The project includes unit tests. However, for demonstration purposes, the number of tests is small and serves more to show the testability of the system.

## Future Steps

1. Cleaner definition of dependencies in `gradle.kts` files.
2. Additional time invested in improving UI performance and the organization of UI modules.

## Screenshots

<p>
  <img src="/assets/no-internet-connection.png" width="224">
  <img src="/assets/error-state.png" width="224">
  <img src="/assets/no-results.png" width="224">
</p>
