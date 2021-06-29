package es.webandroid.marvel.core.platform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import es.webandroid.marvel.core.connectivity.base.ConnectivityProvider
import es.webandroid.marvel.core.extensions.toast
import es.webandroid.marvel.databinding.ActivityMainBinding
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    ConnectivityProvider.ConnectivityStateListener {

    @Inject
    lateinit var connectivityProvider: ConnectivityProvider
    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onResume() {
        super.onResume()
        navController = findNavController(binding.navHostFragment.id)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onStateChange(state: ConnectivityProvider.NetworkState) {
        if (!state.isNetworkAvailable()) {
            toast(getString(es.webandroid.marvel.R.string.not_internet_available))
        }
    }

    override fun onStart() {
        super.onStart()
        connectivityProvider.addListener(this)
    }

    override fun onStop() {
        super.onStop()
        connectivityProvider.removeListener(this)
    }
}
