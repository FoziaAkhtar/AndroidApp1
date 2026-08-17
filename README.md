# AndroidApp1
Tap Counter 2026 - Assignment 3

==============================================
# 🎈 Tap Counter Game
==============================================

## 📱 Android Assignment

This project is a simple **Tap Counter Game** developed using **Android Studio and Kotlin**.

The goal of the game is to tap the balloon as many times as possible before the 20-second timer reaches zero.

---

## 🎯 Project Description

The Tap Counter Game is an interactive Android application where the player taps a balloon to increase their score.

Each time the balloon is tapped:

* The tap counter increases.
* A tap sound plays.
* A **TAP!** animation appears.
* The balloon moves to a new random position.
* The balloon remains visible inside the game area.

When the timer reaches zero:

* The game stops.
* The balloon can no longer be tapped.
* A **GAME OVER** message appears.
* A game-over sound plays.
* The final score is saved to the leaderboard.

---

## ⭐ Features

### ⏱️ 20-Second Countdown

The game starts with a 20-second countdown.

The timer decreases every second until it reaches zero.

---

### 🎈 Moving Balloon

The player taps the balloon to score points.

The balloon moves to different positions inside the game area after each tap.

The movement is limited so the balloon stays visible on the screen.

---

### 👆 Tap Counter

Every successful balloon tap increases the player's score by one.

The current score is displayed in the middle of the screen.

---

### 💥 TAP! Animation

A **TAP!** message appears briefly whenever the balloon is tapped.

The message uses a small animation to make the game more interactive.

---

### 🔊 Sound Effects

The game includes two sound effects:

* `tap_sound.mp3` — played when the balloon is tapped.
* `game_over.mp3` — played when the timer reaches zero.

---

### 🎉 Game Over Message

When the 20-second timer finishes, the application displays:

**🎉 GAME OVER! 🎉**

The message is animated onto the screen.

---

### 🔄 Reset Game

The **RESET GAME** button:

* Stops the current timer.
* Resets the tap counter.
* Resets the timer to 20 seconds.
* Returns the balloon to its starting position.
* Hides the Game Over message.

The saved leaderboard scores are not deleted.

---

### 🏆 Top 5 Leaderboard

The application stores the five highest scores.

The scores are displayed from highest to lowest.

Example:

```text
1. 25
2. 21
3. 18
4. 15
5. 12
```

---

### 💾 SharedPreferences

The leaderboard uses Android `SharedPreferences` to save the scores locally.

This means the scores remain available after closing and reopening the application.

---

### 🗑️ Reset Scores

The **RESET SCORES** button removes all saved leaderboard scores.

The leaderboard then returns to:

```text
1. ---
2. ---
3. ---
4. ---
5. ---
```

---

## 🛠️ Technologies Used

* Kotlin
* Android Studio
* Android SDK
* XML
* ConstraintLayout
* SharedPreferences
* MediaPlayer
* CountDownTimer
* Android Animation
* Git
* GitHub

---

## 📂 Project Structure

```text
AndroidApp1
│
├── app
│   │
│   └── src
│       │
│       └── main
│           │
│           ├── java
│           │   └── com.example.androidapp1
│           │       └── MainActivity.kt
│           │
│           └── res
│               │
│               ├── drawable
│               │   └── baloon_button.png
│               │
│               ├── font
│               │   └── balloonish.otf
│               │
│               ├── layout
│               │   └── activity_main.xml
│               │
│               ├── raw
│               │   ├── game_over.mp3
│               │   └── tap_sound.mp3
│               │
│               └── values
│                   └── colors.xml
│
└── README.md
```

---

## 🎨 Resources

### Balloon Image

The game uses:

```text
baloon_button.png
```

The image is stored in:

```text
app/src/main/res/drawable/
```

---

### Custom Font

The project includes:

```text
balloonish.otf
```

The font is stored in:

```text
app/src/main/res/font/
```

---

### Audio

The project includes:

```text
tap_sound.mp3
game_over.mp3
```

Both sound files are stored in:

```text
app/src/main/res/raw/
```

---

## 🎮 How to Play

1. Open the application.
2. Tap the balloon.
3. The 20-second timer starts.
4. Continue tapping the balloon.
5. The balloon moves after each tap.
6. Try to get the highest possible score.
7. When the timer reaches zero, the game ends.
8. Your score is added to the Top 5 leaderboard.
9. Press **RESET GAME** to play again.
10. Press **RESET SCORES** to clear the leaderboard.

---

## 🧪 Testing

The following features were tested:

* [x] Application launches successfully
* [x] Balloon appears on screen
* [x] Balloon can be tapped
* [x] Balloon moves after tapping
* [x] Balloon remains visible
* [x] Tap counter increases
* [x] 20-second timer works
* [x] TAP! animation works
* [x] Tap sound works
* [x] Game-over sound works
* [x] GAME OVER message appears
* [x] Reset Game works
* [x] Top 5 scores are displayed
* [x] Scores are saved
* [x] Reset Scores works
* [x] Project builds successfully
* [x] Project is pushed to GitHub

---

## 📌 Important Resource Names

The application expects the following resource names:

```text
baloon_button.png
tap_sound.mp3
game_over.mp3
balloonish.otf
```

The spelling of `baloon_button.png` intentionally uses **one "l"** in `baloon`, matching the resource used by the Kotlin code.

---

## 👩‍💻 Author

**Fozia Akhtar**

Android Development Assignment

---

## 📚 Learning Outcomes

This project demonstrates the use of:

* Kotlin programming
* Android Activity lifecycle
* XML layouts
* ConstraintLayout
* UI controls
* Event listeners
* CountDownTimer
* Random positioning
* View animations
* MediaPlayer
* SharedPreferences
* Local data storage
* Android resources
* Git and GitHub version control

---

## ✅ Project Status

**Completed**

The Tap Counter Game builds successfully and has been pushed to GitHub.
