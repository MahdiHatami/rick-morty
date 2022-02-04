package com.metis.rickmorty

import android.app.Application
import androidx.preference.PreferenceManager
import com.metis.rickmorty.data.source.repository.Repository
import com.metis.rickmorty.utils.ThemeManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class RickMortyApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
    initTheme()
  }

  private fun initTheme() {
    val preferences = PreferenceManager.getDefaultSharedPreferences(this)
    runCatching {
      ThemeManager.applyTheme(requireNotNull(preferences.getString("theme_key", "")))
    }.onFailure { exception ->
      Timber.e("Theme Manager: $exception")
    }
  }

}
