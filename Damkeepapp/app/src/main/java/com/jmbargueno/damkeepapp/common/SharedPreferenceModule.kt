package com.jmbargueno.damkeepapp.common

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPreferenceModule {
    @Singleton
    @Provides
    fun getSharedPreferences(): SharedPreferences {
        return MyApp.instance?.getSharedPreferences(
            Constants.APP_SETTINGS_FILE, Context.MODE_PRIVATE
        )
    }

    fun setStringValue(label: String, value: String) {
        var editor: SharedPreferences.Editor = getSharedPreferences().edit()
        editor.putString(label, value)
        editor.commit()
    }

    fun getStringValue(dataLabel: String): String? {
        return getSharedPreferences().getString(dataLabel, null)
    }

    fun removeStringValue(dataLabel: String) {
        var editor: SharedPreferences.Editor = getSharedPreferences().edit()
        editor.remove(dataLabel)
        editor.commit()
    }
}