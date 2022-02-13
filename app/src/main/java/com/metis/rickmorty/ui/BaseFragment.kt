package com.metis.rickmorty.ui

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.metis.rickmorty.R
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

  @Inject
  lateinit var viewModelFactoryProvider: ViewModelProvider.Factory

  fun showShortSnackBar(message: String) {
    Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
  }

  fun showLongSnackBar(message: String) {
    Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
  }

  fun showErrorMessage() {
    Toast.makeText(context, R.string.error_message, Toast.LENGTH_LONG).show()
  }

  fun showOfflineMessage() {
    Toast.makeText(context, R.string.offline_app, Toast.LENGTH_LONG).show()
  }

  fun onNoOfflineData() {
    Toast.makeText(context, R.string.no_offline_data, Toast.LENGTH_LONG).show()
    activity?.onBackPressed()
  }
}
