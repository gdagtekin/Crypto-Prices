package com.gdagtekin.cryptocurrencyapp.ui

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.gdagtekin.cryptocurrencyapp.R
import com.gdagtekin.cryptocurrencyapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var binding: ActivityMainBinding
    private lateinit var loadingDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainFragmentContainer) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBar()
        //setupBottomNavigationView()
        setupLoadingDialog()

    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.cryptoFragment, R.id.walletFragment)
        )
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

//    I gave up on adding the wallet part for now.
//    private fun setupBottomNavigationView() {
//        bottomNavigationView = binding.bottomNavigationView
//        bottomNavigationView.setupWithNavController(navController)
//    }

    private fun setupLoadingDialog() {
        loadingDialog = Dialog(applicationContext)
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loadingDialog.setContentView(R.layout.dialog_progress)
        loadingDialog.setCancelable(false)
    }

    fun showLoading() {
        if (!loadingDialog.isShowing){
            loadingDialog.show()
        }
    }

    fun hideLoading() {
        if (loadingDialog.isShowing){
            loadingDialog.dismiss()
        }
    }

}