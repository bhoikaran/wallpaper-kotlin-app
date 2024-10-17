# Wallpaper App

This is a Kotlin-based Wallpaper application that uses the Google Pixel API to fetch wallpaper data. Users can browse a variety of wallpapers and set them as their device's background. The app follows modern Android design principles and leverages Material Design for its user interface.

## Purpose

This application was created as a learning project to explore Kotlin, as I had previously worked with Java. The goal was to gain hands-on experience with Kotlin syntax, Android development practices, and API integration using modern Android libraries.

## Features

- **Google Pixel API Integration**: Fetches a collection of high-quality wallpapers from the Google Pixel API.
- **Set Wallpaper Functionality**: Users can easily set any wallpaper directly from the app.
- **Material Design**: The app uses Material Design components to provide a clean and user-friendly interface.
- **Permissions Handling**: Requests necessary permissions (like `SET_WALLPAPER`) to ensure smooth functionality.
- **Offline Caching**: Wallpapers are cached for offline use.

## Tech Stack

- **Kotlin**: For all application logic and UI components.
- **Google Pixel API**: To fetch wallpaper data.
- **Retrofit**: For API calls to retrieve the wallpaper data.
- **Glide**: For efficient image loading and caching.
- **Material Components**: Provides a modern and consistent UI experience.
- **Coroutines**: For handling asynchronous operations.

## Download

[Download the APK here](https://1drv.ms/u/c/960dde80e05245a0/EUqBVo7qeQBMhry5SSDsO3MBjFrFsYIbdwV3UdEpbQm3Vg?e=Ch6gON)  

Make sure to allow installation from unknown sources if prompted.

## Permissions

The app requires the following permissions:

- `SET_WALLPAPER`: Allows the app to set wallpapers on the user's device.
- `INTERNET`: To fetch wallpapers from the Google Pixel API.

Make sure to enable these permissions in your `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.SET_WALLPAPER" />
<uses-permission android:name="android.permission.INTERNET" />
