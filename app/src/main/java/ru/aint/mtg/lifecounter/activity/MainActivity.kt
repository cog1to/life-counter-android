package ru.aint.mtg.lifecounter.activity

import android.os.Bundle
import android.util.TypedValue
import android.view.ViewConfiguration
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.aint.mtg.lifecounter.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<LifeCounterViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Make status bar transaprent.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        // Prevent screen turn off.
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViewModel()
        setUpViews()
    }

    // MARK: - Private

    private fun setUpViewModel() {
        viewModel.onTotalsChanged = { playerTotal, opponentTotal ->
            binding.textPlayerLifeTotal.text = "$playerTotal"
            binding.textOpponentLifeTotal.text = "$opponentTotal"
        }

    }

    private fun setUpViews() {
        // If there are no hardware menu buttons, make space for software ones
        // by increasing the bottom margin on container layout.
        if (!ViewConfiguration.get(this).hasPermanentMenuKey()) {
            val newBottomMargin = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                32f,
                resources.displayMetrics
            )

            val params = binding.layoutBase.layoutParams as FrameLayout.LayoutParams
            params.setMargins(0, params.topMargin, 0, newBottomMargin.toInt())
            binding.layoutBase.layoutParams = params
        }

        val (player, opponent) = viewModel.getTotals()
        binding.textPlayerLifeTotal.text = "$player"
        binding.textOpponentLifeTotal.text = "$opponent"

        binding.buttonPlayerMinus.setOnClickListener { _ ->
            viewModel.changePlayerTotal(LifeCounterViewModel.Change.minusOne)
        }

        binding.buttonPlayerPlus.setOnClickListener { _ ->
            viewModel.changePlayerTotal(LifeCounterViewModel.Change.plusOne)
        }

        binding.buttonOpponentMinus.setOnClickListener { _ ->
            viewModel.changeOpponentTotal(LifeCounterViewModel.Change.minusOne)
        }

        binding.buttonOpponentPlus.setOnClickListener { _ ->
            viewModel.changeOpponentTotal(LifeCounterViewModel.Change.plusOne)
        }

        binding.buttonReset.setOnLongClickListener { _ ->
            viewModel.reset()
            true
        }
    }
}