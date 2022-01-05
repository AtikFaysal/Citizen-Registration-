package com.citizen.registration.utils.constants

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 11/27/2021 at 5:07 PM
 */
class AppConstants
{
    companion object{
        const val chooseItem = "চিহ্নিত করুন"

        const val MAXIMUM_IMAGE_SIZE = 500f

        //============================= DATE TIME FORMAT ============================
        const val YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"
        const val YYYY_MMM_DD_HH_MM_AA = "yyyy-MMM-dd hh:mm a"
        const val MMM_DD_YYYY_HH_MM_AA = "MMM dd, yyyy hh:mm a"
        const val MMM_DD_YYYY = "MMMM dd, yyyy"
        const val YYYY_MM_DD = "yyyy-MM-dd"
        const val DD_MMM_YYYY = "dd-MMM-yyyy"
        const val DD_MM_YYYY = "dd-MM-yyyy"
        const val MMMM_YYYY = "MMMM-yyyy"
        const val MM_YYYY = "MMyyyy"
        const val YYYY_MM = "yyyy-MM"
        const val HH_MM_SS = "hh:mm a"
        //============================= DATE TIME FORMAT ============================

        //============================= SELECTION CONSTANTS (1000)=========================
        const val GENDER_SELECTION = 1001
        const val HOLDING_SELECTION = 1002
        const val NID_OR_BIRTH_SELECTION = 1003
        const val OCCUPATION_SELECTION = 1004
        const val EDUCATION_SELECTION = 1005
        const val RELIGION_SELECTION = 1006
        const val LIVE_IN_SELECTION = 1007
        const val MARITAL_STATUS_SELECTION = 1008

        const val DIVISION_SELECTION = 1009
        const val DISTRICT_SELECTION = 1010
        const val SUB_DISTRICT_SELECTION = 1011
        const val WARD_SELECTION = 1012
        const val DIVISION_SELECTION_BN = 1013
        const val DISTRICT_SELECTION_BN = 1014
        const val SUB_DISTRICT_SELECTION_BN = 1015
        const val WARD_SELECTION_BN = 1016

        const val DIVISION_SELECTION_PERMANENT = 1017
        const val DISTRICT_SELECTION_PERMANENT = 1018
        const val SUB_DISTRICT_SELECTION_PERMANENT = 1024
        const val WARD_SELECTION_PERMANENT = 1019
        const val DIVISION_SELECTION_BN_PERMANENT = 1020
        const val DISTRICT_SELECTION_BN_PERMANENT = 1021
        const val SUB_DISTRICT_SELECTION_BN_PERMANENT = 1022
        const val WARD_SELECTION_BN_PERMANENT = 1023
        const val PARA_SUGGESTION = 1024
        const val POST_OFFICE_SUGGESTION = 1025

        //=================================== Intents Key title ========================
        const val phoneNumber: String = "phoneNumber"
        const val otpType: String = "otpType"
        const val hashKey: String = "hashKey"

        //=================================== Intents Key title ========================
        const val registrationOtp = 1
        const val forgotPasswordOtp = 2
    }
}