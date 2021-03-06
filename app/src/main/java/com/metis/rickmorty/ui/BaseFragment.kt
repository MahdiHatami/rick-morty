package com.metis.rickmorty.ui

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.metis.rickmorty.R

abstract class BaseFragment : Fragment() {

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
