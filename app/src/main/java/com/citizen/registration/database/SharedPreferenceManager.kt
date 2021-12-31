package com.citizen.registration.database

import android.content.Context
import android.content.SharedPreferences
import com.citizen.registration.data.model.UserModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 11/18/2021 at 1:20 PM
 */
@Singleton
open class SharedPreferenceManager @Inject constructor(private val preferences : SharedPreferences) : PreferenceHelper
{
    companion object{
        const val IS_REMEMBER = "is_remember"
        const val REM_USER_NAME = "remembered_user_name"
        const val REM_PASSWORD = "remembered_password"
        const val LOGIN_STATUS = "login_status"
        const val ACCESS_TOKEN = "access_token"
        const val USER_ID = "user_id"
        const val FULL_NAME = "name"
        const val EMAIL = "email"
        const val MOBILE = "mobile"
        const val AVATAR = "image"
        const val ADDRESS = "address"
    }

    override fun setRememberMe(isChecked: Boolean, userName: String, password: String) {
        preferences[IS_REMEMBER] = isChecked
        preferences[REM_USER_NAME] = userName
        preferences[REM_PASSWORD] = password
    }

    override fun setLoginStatus(isLogin: Boolean) {
        preferences[LOGIN_STATUS] = isLogin
    }

    override fun setAccessToken(token: String) {
        preferences[ACCESS_TOKEN] = token
    }

    override fun setUserInfo(user: UserModel) {
        preferences[USER_ID] = user.id
        preferences[FULL_NAME] = user.name
        preferences[MOBILE] = user.mobile
        preferences[EMAIL] = user.email
        preferences[AVATAR] = user.image
        preferences[ADDRESS] = user.address
    }

    override fun isRemembered() = preferences[IS_REMEMBER] ?: false

    override fun getRememberedUserName() = preferences[REM_USER_NAME]  ?: ""

    override fun getRememberedPassword() = preferences[REM_PASSWORD]  ?: ""

    override fun getLoginStatus() = preferences[LOGIN_STATUS] ?: false

    override fun getAccessToken() = preferences[ACCESS_TOKEN]  ?: ""

    override fun getUserId() = preferences[USER_ID]  ?: ""

    override fun getUserFullName() = preferences[FULL_NAME]  ?: ""

    override fun getPhone() = preferences[MOBILE]  ?: ""

    override fun getEmail(): String ?= preferences[EMAIL]

    override fun getAvatar(): String ?= preferences[AVATAR]

    override fun getAddress(): String ?= preferences[ADDRESS]

    override fun clearPrefManager() {
        preferences.edit().clear().apply()
    }
}

/**
 * SharedPreferences extension function, to listen the edit() and apply() fun calls
 * on every SharedPreferences operation.
 */
private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = this.edit()
    operation(editor)
    editor.apply()
}
/**
 * puts a key value pair in shared prefs if doesn't exists, otherwise updates value on given [key]
 */
private operator fun SharedPreferences.set(key: String, value: Any?) {
    when (value) {
        is String? -> edit { it.putString(key, value) }
        is Int -> edit { it.putInt(key, value) }
        is Boolean -> edit { it.putBoolean(key, value) }
        is Float -> edit { it.putFloat(key, value) }
        is Long -> edit { it.putLong(key, value) }
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}

/**
 * finds value on given key.
 * [T] is the type of value
 * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
 */
private inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T? {
    return when (T::class) {
        String::class -> getString(key, defaultValue as? String) as T?
        Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
        Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
        Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
        Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}