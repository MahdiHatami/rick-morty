package com.metis.rickmorty.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SharedPreferenceHelper {

    companion object {

        private var prefs: SharedPreferences? = null

        @Volatile
        private var instance: SharedPreferenceHelper? = null

        /**
         * This checks if there is an existing instance of the [SharedPreferences] in the
         * specified [context] and creates one if there isn't or else, it returns the
         * already existing instance. This function ensures that the [SharedPreferences] is
         * accessed at any instance by a single thread.
         */
        fun getInstance(context: Context): SharedPreferenceHelper {
            synchronized(this) {
                val _instance = instance
                if (_instance == null) {
                    prefs = PreferenceManager.getDefaultSharedPreferences(context)
                    instance = _instance
                }
                return SharedPreferenceHelper()
            }
        }
    }

    /**
     * This function gets the value of the cache duration the user set in the
     * Settings Fragment.
     */
    fun getUserSetCacheDuration() = prefs?.getString("cache_key", "0")

    /**
     * This function gets the value of the app theme the user set in the
     * Settings Fragment.
     */
    fun getSelectedThemePref() = prefs?.getString("theme_key", "")

    /**
     * This function gets the value of the temperature unit the user set in the
     * Settings Fragment.
     */
    fun getSelectedTemperatureUnit() = prefs?.getString("unit_key", "")
}
