package com.muratozturk.mova.ui.activity

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.muratozturk.mova.R
import com.muratozturk.mova.common.Resource
import com.muratozturk.mova.common.hideWithAnimation
import com.muratozturk.mova.common.hideWithoutAnimation
import com.muratozturk.mova.common.showToast
import com.muratozturk.mova.common.showWithAnimation
import com.muratozturk.mova.common.viewBinding
import com.muratozturk.mova.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import www.sanju.motiontoast.MotionToastStyle
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        hideNavigation()

        viewModel.getDarkMode()
        viewModel.getCurrentLanguageCode()
        collectData()

    }

    private fun collectData() {
        with(viewModel) {

            lifecycleScope.launchWhenCreated {
                darkMode.collectLatest { response ->
                    when (response) {
                        is Resource.Loading -> {
                        }

                        is Resource.Error -> {
                            showToast(
                                getString(R.string.error),
                                response.throwable.localizedMessage ?: "Error",
                                MotionToastStyle.ERROR
                            )

                        }

                        is Resource.Success -> {
                            try {
                                if (response.data) {
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                                } else {
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                                }
                            } catch (_: Exception) {
                            }
                        }
                    }
                }
            }

            lifecycleScope.launchWhenCreated {
                currentLanguageCode.collectLatest { response ->
                    when (response) {
                        is Resource.Loading -> {
                        }

                        is Resource.Error -> {
                            showToast(
                                getString(R.string.error),
                                response.throwable.localizedMessage ?: "Error",
                                MotionToastStyle.ERROR
                            )

                        }

                        is Resource.Success -> {

                            val locale = Locale(response.data)
                            val config = Configuration()
                            config.setLocale(locale)
                            val resources = this@MainActivity.resources
                            resources?.updateConfiguration(config, resources.displayMetrics)
                            setContentView(binding.root)

                            setupBottomNavigationView()

                        }
                    }
                }
            }
        }
    }

    private fun setupBottomNavigationView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.bottomNavigation.setupWithNavController(navHostFragment.navController)


        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashScreenFragment -> {
                    binding.bottomNavigation.hideWithoutAnimation(binding.fragmentContainerView)
                }

                R.id.signUpFragment -> {
                    binding.bottomNavigation.hideWithoutAnimation(binding.fragmentContainerView)
                }

                R.id.signInWithPasswordFragment -> {
                    binding.bottomNavigation.hideWithoutAnimation(binding.fragmentContainerView)
                }

                R.id.onBoardingFragment -> {
                    binding.bottomNavigation.hideWithoutAnimation(binding.fragmentContainerView)
                }

                R.id.signInWithSocialFragment -> {
                    binding.bottomNavigation.hideWithoutAnimation(binding.fragmentContainerView)
                }

                R.id.dialogFragment -> {
                    binding.bottomNavigation.hideWithoutAnimation(binding.fragmentContainerView)
                }

                R.id.videoPlayerFragment -> {
                    binding.bottomNavigation.hideWithoutAnimation(binding.fragmentContainerView)
                }

                R.id.previewImagesFragment -> {
                    binding.bottomNavigation.hideWithAnimation(binding.fragmentContainerView)
                }

                else -> {
                    binding.bottomNavigation.showWithAnimation(binding.fragmentContainerView)
                }
            }
        }
    }

    private fun hideNavigation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val decorView = window.decorView
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    }

}