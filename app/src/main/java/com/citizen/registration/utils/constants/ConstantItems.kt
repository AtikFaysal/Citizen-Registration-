package com.citizen.registration.utils.constants

import com.citizen.registration.data.model.Gender
import com.citizen.registration.data.model.HoldingTypeModel
import com.citizen.registration.data.model.Items
import com.citizen.registration.utils.constants.AppConstants.Companion.chooseItem


/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 11/28/2021 at 10:42 AM
 */
class ConstantItems
{
    companion object{
        val gender = listOf(
            Gender("0", chooseItem),
            Gender("পুরুষ", "পুরুষ"),
            Gender("মহিলা", "মহিলা"),
            Gender("লিঙ্গ", "তৃতীয় লিঙ্গ"),
        )

        val identityType = listOf(
            Items("0", chooseItem),
            Items("1", "ন্যশনাল আইডি"),
            Items("2", "জন্ম নিবন্ধন")
        )

        val maritalStatus = listOf(
            Items("0", chooseItem),
            Items("1", "বিবাহিত"),
            Items("2", "অবিবাহিত"),
            Items("3", "তালাক প্রাপ্ত"),
            Items("4", "বিধবা"),
            Items("5", "অন্যান্য"),
        )

        val religionList = listOf(
            Items("0", chooseItem),
            Items("1", "ইসলাম"),
            Items("2", "হিন্দু"),
            Items("3", "খ্রিস্ট ধর্ম"),
            Items("4", "বৌদ্ধ ধর্ম"),
            Items("5", "অন্যান্য"),
        )

        val liveInType = listOf(
            Items("0", chooseItem),
            Items("1", "স্থায়ী"),
            Items("2", "অস্থায়ী"),
        )

        val occupationList = listOf(
            Items("0", chooseItem),
            Items("1", "Software Engineer"),
            Items("2", "Doctor"),
        )

        val educationList = listOf(
            Items("0", chooseItem),
            Items("1", "B.S.C"),
            Items("2", "M.B.B.S"),
        )

        val wardNoListEn = listOf(
            Items("0", chooseItem),
            Items("1", "1"),
            Items("2", "2"),
            Items("3", "3"),
            Items("4", "4"),
            Items("5", "5"),
            Items("6", "6"),
            Items("7", "7"),
            Items("8", "8"),
            Items("9", "9"),
        )

        val wardNoListBn = listOf(
            Items("0", chooseItem),
            Items("1", "১"),
            Items("2", "২"),
            Items("3", "৩"),
            Items("4", "৪"),
            Items("5", "৫"),
            Items("6", "৬"),
            Items("7", "৭"),
            Items("8", "৮"),
            Items("9", "৯"),
        )

        val divisionListEn = listOf(
            Items("0", chooseItem),
            Items("1", "Dhaka"),
            Items("2", "Chittagong"),
            Items("3", "Rajshahi"),
            Items("4", "Khulna"),
            Items("5", "Barisal"),
            Items("6", "Sylhet"),
            Items("7", "Rangpur"),
            Items("8", "Maymensingh"),
        )

        val divisionListBn = listOf(
            Items("0", chooseItem),
            Items("1", "ঢাকা"),
            Items("2", "চট্রগ্রাম"),
            Items("3", "রাজশাহী"),
            Items("4", "খুলনা"),
            Items("5", "বরিশাল"),
            Items("6", "সিলেট"),
            Items("7", " রংপুর"),
            Items("8", "ময়মনসিংহ"),
        )

        val districtListEn = listOf(
            Items("0", chooseItem),
            Items("1", "Dhaka"),
            Items("2", "Chittagong"),
            Items("3", "Rajshahi"),
            Items("4", "Khulna"),
            Items("5", "Barisal"),
            Items("6", "Sylhet"),
            Items("7", "Rangpur"),
            Items("8", "Maymensingh"),
        )

        val districtListBn = listOf(
            Items("0", chooseItem),
            Items("1", "ঢাকা"),
            Items("2", "চট্রগ্রাম"),
            Items("3", "রাজশাহী"),
            Items("4", "খুলনা"),
            Items("5", "বরিশাল"),
            Items("6", "সিলেট"),
            Items("7", " রংপুর"),
            Items("8", "ময়মনসিংহ"),
        )

        val subDistrictListEn = listOf(
            Items("0", chooseItem),
            Items("1", "Dhaka"),
            Items("2", "Chittagong"),
            Items("3", "Rajshahi"),
            Items("4", "Khulna"),
            Items("5", "Barisal"),
            Items("6", "Sylhet"),
            Items("7", "Rangpur"),
            Items("8", "Maymensingh"),
        )

        val subDistrictListBn = listOf(
            Items("0", chooseItem),
            Items("1", "ঢাকা"),
            Items("2", "চট্রগ্রাম"),
            Items("3", "রাজশাহী"),
            Items("4", "খুলনা"),
            Items("5", "বরিশাল"),
            Items("6", "সিলেট"),
            Items("7", " রংপুর"),
            Items("8", "ময়মনসিংহ"),
        )

        fun getEmptyHoldingType() : HoldingTypeModel
        {
            val holdingType = HoldingTypeModel()
            with(holdingType)
            {
                id = "0"
                holdingName = chooseItem
                summary = ""
            }

            return holdingType
        }
    }
}