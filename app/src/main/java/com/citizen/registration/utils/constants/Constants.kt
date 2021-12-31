package com.citizen.registration.utils.constants

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 12/1/2021 at 11:17 AM
 */
class Constants
{
    companion object{
        const val PREF_NAME = "AmiBachelorPrefInfo"

        // server response codes
        const val RESPONSE_SUCCESS_CODE = 200 // success code
        const val INSERT_SUCCESS_CODE = 201 // success code
        const val RESPONSE_ERROR_CODE = 400 // error code
        const val RESPONSE_NOT_FOUND_CODE = 404 // error code
        const val SERVER_ERROR_CODE = 500 // error code
        const val DUPLICATE_ENTRY = 504 // error code
        const val CONNECTION_TIMEOUT = 505 // error code
        const val UNAUTHORIZED_ERROR_CODE = 401 // error code
        const val INTERNAL_SERVER_ERROR = 500 // error code
        const val NETWORK_ERROR_CODE = 1001 // error code
        const val SMS_RETRIEVED = 1000
    }
}