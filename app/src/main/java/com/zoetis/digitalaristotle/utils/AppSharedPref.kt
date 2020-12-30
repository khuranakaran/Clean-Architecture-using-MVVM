package com.zoetis.digitalaristotle.utils


import android.content.Context
import android.content.SharedPreferences

class AppSharedPref(context: Context) {
    private var mSharedPref: SharedPreferences = context.getSharedPreferences(
        PreferenceConstants.APP_SHARE_PREF_NAME,
        Context.MODE_PRIVATE
    )

    companion object {

        private var mAppSharedPref: AppSharedPref? = null
        fun getInstance(mContext: Context): AppSharedPref? {
            if (mAppSharedPref == null) {
                mAppSharedPref = AppSharedPref(mContext)
            }
            return mAppSharedPref
        }
    }

    fun setStringData(key: String?, value: String?) {
        val editor = mSharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun setBooleanData(key: String?, value: Boolean) {
        val editor = mSharedPref.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun setIntegerData(key: String?, value: Int) {
        val editor = mSharedPref.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getStringData(key: String?): String? {
        return mSharedPref.getString(key, "")
    }

    fun getInt(key: String?): Int {
        return mSharedPref.getInt(key, 1)
    }

    fun getLanguageId(key: String?): Int {
        return mSharedPref.getInt(key, 1)
    }

    fun getCategorySelectedInt(key: String?): Int {
        return mSharedPref.getInt(key, 1)
    }

    fun getBoolean(key: String?): Boolean {
        return mSharedPref.getBoolean(key, false)
    }

    fun removeStringData(key: String?) {
        val editor = mSharedPref.edit()
        editor.remove(key)
        editor.apply()
    }

    fun clearPreferences() {
        mSharedPref.edit().clear().apply()
    }
}