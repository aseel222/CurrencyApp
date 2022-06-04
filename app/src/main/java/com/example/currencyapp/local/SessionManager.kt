package com.example.currencyapp.local

import android.content.Context
import android.content.SharedPreferences


class SessionManager(context: Context) {

    init {
        pref = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
    }

    companion object {

        private lateinit var pref: SharedPreferences
        private const val USER_TOKEN = "USER_TOKEN"
        private const val USER_ID = "USER_ID"







        fun gettoken() = pref.getString(USER_TOKEN, null)
        fun deletetoken() = pref.edit().remove("USER_TOKEN").apply()
        fun deleteuserid() = pref.edit().remove("USER_ID").apply()
       fun getuserid() = pref.getInt(USER_ID, 0)






        fun savetoken(token: String) = pref.edit()
            .putString(USER_TOKEN, token)
            .apply()

        fun saveuserid(userid: Int) = pref.edit()
            .putInt(USER_ID, userid)
            .apply()



    }

}