package com.example.androidapp1

// =====================================================
// IMPORTS
// =====================================================

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import kotlin.random.Random


// =====================================================
// MAIN ACTIVITY
// =====================================================
//
// TAP COUNTER GAME
//
// FEATURES:
//
// 1. 20-second countdown
// 2. Tap counter
// 3. Moving balloon
// 4. Balloon stays visible
// 5. Balloon moves left/right/up/down
// 6. TAP! animation
// 7. Tap sound
// 8. Game-over sound
// 9. Visible GAME OVER message
// 10. Reset game
// 11. Top 5 leaderboard
// 12. SharedPreferences storage
// 13. Reset scores
//
// =====================================================

class MainActivity : AppCompatActivity() {


    // =================================================
    // UI CONTROLS
    // =================================================

    private lateinit var timerText: TextView

    private lateinit var countText: TextView

    private lateinit var tapButton: ImageButton

    private lateinit var resetButton: Button

    private lateinit var resetScoresButton: Button

    private lateinit var tapText: TextView

    private lateinit var gameOverText: TextView

    private lateinit var topScoresText: TextView

    // Area where the balloon is allowed to move.
    private lateinit var gameArea: View


    // =================================================
    // GAME VARIABLES
    // =================================================

    private var tapCount = 0

    private var gameRunning = false

    private var countDownTimer: CountDownTimer? = null


    // =================================================
    // SOUND VARIABLES
    // =================================================

    private var tapSound: MediaPlayer? = null

    private var gameOverSound: MediaPlayer? = null


    // =================================================
    // LEADERBOARD
    // =================================================

    private val topScores =
        mutableListOf<Int>()

    private val SCORES_KEY =
        "top_scores"

    private val PREFS_NAME =
        "TapCounterPreferences"


    // =================================================
    // ON CREATE
    // =================================================

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display.
        enableEdgeToEdge()

        // Load XML.
        setContentView(
            R.layout.activity_main
        )


        // =================================================
        // SYSTEM BAR PADDING
        // =================================================

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { view, insets ->

            val systemBars =
                insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                )

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }


        // =================================================
        // CONNECT XML CONTROLS
        // =================================================

        timerText =
            findViewById(R.id.timerText)

        countText =
            findViewById(R.id.countText)

        tapButton =
            findViewById(R.id.tapButton)

        resetButton =
            findViewById(R.id.resetButton)

        resetScoresButton =
            findViewById(
                R.id.resetScoresButton
            )

        tapText =
            findViewById(R.id.tapText)

        gameOverText =
            findViewById(
                R.id.gameOverText
            )

        topScoresText =
            findViewById(
                R.id.topScoresText
            )

        // IMPORTANT:
        //
        // XML must contain:
        //
        // android:id="@+id/gameArea"
        //
        gameArea =
            findViewById(R.id.gameArea)


        // =================================================
        // CREATE TAP SOUND
        // =================================================

        tapSound =
            MediaPlayer.create(
                this,
                R.raw.tap_sound
            )


        // =================================================
        // CREATE GAME OVER SOUND
        // =================================================

        gameOverSound =
            MediaPlayer.create(
                this,
                R.raw.game_over
            )


        // =================================================
        // LOAD SCORES
        // =================================================

        loadTopScores()

        updateTopScoresDisplay()


        // =================================================
        // INITIAL GAME SCREEN
        // =================================================

        timerText.text =
            "20"

        countText.text =
            "0"


        // Make balloon visible.
        tapButton.visibility =
            View.VISIBLE

        tapButton.isEnabled =
            true


        // Start at original XML position.
        tapButton.translationX =
            0f

        tapButton.translationY =
            0f


        // Hide TAP text.
        tapText.visibility =
            View.GONE


        // Hide GAME OVER.
        gameOverText.visibility =
            View.GONE


        // =================================================
        // BALLOON CLICK
        // =================================================

        tapButton.setOnClickListener {

            tapButtonPressed()
        }


        // =================================================
        // RESET GAME
        // =================================================

        resetButton.setOnClickListener {

            resetGame()
        }


        // =================================================
        // RESET SCORES
        // =================================================

        resetScoresButton.setOnClickListener {

            resetScores()
        }
    }


    // =====================================================
    // BALLOON PRESSED
    // =====================================================

    private fun tapButtonPressed() {


        // Start game on first tap.
        if (!gameRunning) {

            startGame()
        }


        // Increase score.
        tapCount++


        // Display score.
        countText.text =
            tapCount.toString()


        // Play tap sound.
        playTapSound()


        // Show TAP animation.
        showTapText()


        // Move balloon.
        moveTapButton()
    }


    // =====================================================
    // START GAME
    // =====================================================

    private fun startGame() {

        gameRunning =
            true


        tapCount =
            0


        countText.text =
            "0"


        timerText.text =
            "20"


        tapButton.visibility =
            View.VISIBLE


        tapButton.isEnabled =
            true


        // Hide GAME OVER.
        gameOverText.visibility =
            View.GONE


        // Cancel old timer.
        countDownTimer?.cancel()


        // =================================================
        // CREATE 20 SECOND TIMER
        // =================================================

        countDownTimer =
            object : CountDownTimer(
                20_000,
                1_000
            ) {


                // =========================================
                // ON TICK
                // =========================================

                override fun onTick(
                    millisUntilFinished: Long
                ) {

                    val seconds =
                        millisUntilFinished /
                                1_000


                    timerText.text =
                        seconds.toString()
                }


                // =========================================
                // ON FINISH
                // =========================================

                override fun onFinish() {

                    gameRunning =
                        false


                    timerText.text =
                        "0"


                    // Disable tapping.
                    tapButton.isEnabled =
                        false


                    // IMPORTANT:
                    // Keep balloon visible.
                    tapButton.visibility =
                        View.VISIBLE


                    // Hide TAP animation.
                    tapText.visibility =
                        View.GONE


                    // Show GAME OVER.
                    showGameOverMessage()


                    // Play sound.
                    playGameOverSound()


                    // Save score.
                    addScore(
                        tapCount
                    )
                }

            }.start()
    }


    // =====================================================
    // MOVE BALLOON
    // =====================================================

    /**
     * Moves the balloon around its original position.
     *
     * The balloon can move:
     *
     * LEFT
     * RIGHT
     * UP
     * DOWN
     *
     * It does NOT continuously move toward the
     * bottom-right corner.
     */
    private fun moveTapButton() {


        // =================================================
        // WAIT FOR GAME AREA TO BE MEASURED
        // =================================================

        if (gameArea.width <= 0 ||
            gameArea.height <= 0
        ) {

            gameArea.post {

                moveTapButton()
            }

            return
        }


        // =================================================
        // WAIT FOR BALLOON TO BE MEASURED
        // =================================================

        if (tapButton.width <= 0 ||
            tapButton.height <= 0
        ) {

            tapButton.post {

                moveTapButton()
            }

            return
        }


        // =================================================
        // CALCULATE CENTER MOVEMENT AREA
        // =================================================

        val horizontalSpace =
            (
                    gameArea.width -
                            tapButton.width
                    ) / 2


        val verticalSpace =
            (
                    gameArea.height -
                            tapButton.height
                    ) / 2


        // =================================================
        // SAFETY MARGIN
        // =================================================

        val safeHorizontalSpace =
            (
                    horizontalSpace - 35
                    ).coerceAtLeast(0)


        val safeVerticalSpace =
            (
                    verticalSpace - 35
                    ).coerceAtLeast(0)


        // =================================================
        // RANDOM X
        // =================================================

        val randomX =
            if (
                safeHorizontalSpace > 0
            ) {

                Random.nextInt(
                    -safeHorizontalSpace,
                    safeHorizontalSpace + 1
                )

            } else {

                0
            }


        // =================================================
        // RANDOM Y
        // =================================================

        val randomY =
            if (
                safeVerticalSpace > 0
            ) {

                Random.nextInt(
                    -safeVerticalSpace,
                    safeVerticalSpace + 1
                )

            } else {

                0
            }


        // =================================================
        // MOVE BALLOON
        // =================================================

        tapButton.animate()
            .cancel()


        tapButton.animate()
            .translationX(
                randomX.toFloat()
            )
            .translationY(
                randomY.toFloat()
            )
            .setDuration(180)
            .start()
    }


    // =====================================================
    // SHOW TAP TEXT
    // =====================================================

    private fun showTapText() {

        tapText.visibility =
            View.VISIBLE


        tapText.animate().cancel()


        tapText.alpha =
            1f


        tapText.scaleX =
            1f


        tapText.scaleY =
            1f


        tapText.animate()
            .scaleX(1.35f)
            .scaleY(1.35f)
            .alpha(0f)
            .setDuration(250)
            .withEndAction {

                tapText.alpha =
                    1f

                tapText.scaleX =
                    1f

                tapText.scaleY =
                    1f

                tapText.visibility =
                    View.GONE
            }
            .start()
    }


    // =====================================================
    // SHOW GAME OVER
    // =====================================================

    private fun showGameOverMessage() {

        gameOverText.visibility =
            View.VISIBLE


        gameOverText.alpha =
            0f


        gameOverText.scaleX =
            0.7f


        gameOverText.scaleY =
            0.7f


        gameOverText.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(400)
            .start()
    }


    // =====================================================
    // TAP SOUND
    // =====================================================

    private fun playTapSound() {

        tapSound?.let { player ->

            if (player.isPlaying) {

                player.pause()
            }

            player.seekTo(0)

            player.start()
        }
    }


    // =====================================================
    // GAME OVER SOUND
    // =====================================================

    private fun playGameOverSound() {

        gameOverSound?.let { player ->

            if (player.isPlaying) {

                player.pause()
            }

            player.seekTo(0)

            player.start()
        }
    }


    // =====================================================
    // ADD SCORE
    // =====================================================

    private fun addScore(
        score: Int
    ) {

        topScores.add(
            score
        )


        topScores.sortDescending()


        while (
            topScores.size > 5
        ) {

            topScores.removeAt(
                topScores.lastIndex
            )
        }


        saveTopScores()

        updateTopScoresDisplay()
    }


    // =====================================================
    // SAVE SCORES
    // =====================================================

    private fun saveTopScores() {

        val preferences =
            getSharedPreferences(
                PREFS_NAME,
                MODE_PRIVATE
            )


        val scoresString =
            topScores.joinToString(",")


        preferences.edit()
            .putString(
                SCORES_KEY,
                scoresString
            )
            .apply()
    }


    // =====================================================
    // LOAD SCORES
    // =====================================================

    private fun loadTopScores() {

        val preferences =
            getSharedPreferences(
                PREFS_NAME,
                MODE_PRIVATE
            )


        val scoresString =
            preferences.getString(
                SCORES_KEY,
                ""
            )


        if (
            !scoresString.isNullOrEmpty()
        ) {

            val savedScores =
                scoresString.split(",")


            for (
            score in savedScores
            ) {

                val number =
                    score.toIntOrNull()


                if (
                    number != null
                ) {

                    topScores.add(
                        number
                    )
                }
            }
        }


        topScores.sortDescending()


        while (
            topScores.size > 5
        ) {

            topScores.removeAt(
                topScores.lastIndex
            )
        }
    }


    // =====================================================
    // DISPLAY TOP SCORES
    // =====================================================

    private fun updateTopScoresDisplay() {

        val display =
            StringBuilder()


        for (
        position in 0 until 5
        ) {

            val score =
                if (
                    position <
                    topScores.size
                ) {

                    topScores[position]
                        .toString()

                } else {

                    "---"
                }


            display.append(
                "${position + 1}. $score"
            )


            if (
                position < 4
            ) {

                display.append(
                    "\n"
                )
            }
        }


        topScoresText.text =
            display.toString()
    }


    // =====================================================
    // RESET GAME
    // =====================================================

    private fun resetGame() {

        // Cancel timer.
        countDownTimer?.cancel()


        // Reset variables.
        tapCount =
            0

        gameRunning =
            false


        // Reset display.
        timerText.text =
            "20"


        countText.text =
            "0"


        // Stop balloon animation.
        tapButton.animate()
            .cancel()


        // Return balloon to original center.
        tapButton.translationX =
            0f


        tapButton.translationY =
            0f


        // Make balloon visible.
        tapButton.visibility =
            View.VISIBLE


        tapButton.isEnabled =
            true


        // Hide TAP.
        tapText.animate()
            .cancel()


        tapText.visibility =
            View.GONE


        // Hide GAME OVER.
        gameOverText.animate()
            .cancel()


        gameOverText.alpha =
            1f


        gameOverText.scaleX =
            1f


        gameOverText.scaleY =
            1f


        gameOverText.visibility =
            View.GONE
    }


    // =====================================================
    // RESET SCORES
    // =====================================================

    private fun resetScores() {

        topScores.clear()


        val preferences =
            getSharedPreferences(
                PREFS_NAME,
                MODE_PRIVATE
            )


        preferences.edit()
            .remove(
                SCORES_KEY
            )
            .apply()


        updateTopScoresDisplay()
    }


    // =====================================================
    // ON DESTROY
    // =====================================================

    override fun onDestroy() {

        countDownTimer?.cancel()


        tapSound?.release()

        tapSound = null


        gameOverSound?.release()

        gameOverSound = null


        super.onDestroy()
    }
}

