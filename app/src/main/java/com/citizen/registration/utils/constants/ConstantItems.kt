package com.citizen.registration.utils.constants

import com.citizen.registration.data.model.*
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
            Gender("0", "চিহ্নিত করুন"),
            Gender("পুরুষ", "পুরুষ"),
            Gender("মহিলা", "মহিলা"),
            Gender("লিঙ্গ", "তৃতীয় লিঙ্গ"),
        )

        val identityType = listOf(
            Items("0", "আইডি চিহ্নিত করুন"),
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
            //Items("0", chooseItem),
            Items("1", "স্থায়ী"),
            //Items("2", "অস্থায়ী"),
        )

        val occupationList = listOf(
            Items("0", chooseItem),
            Items("9", "শিক্ষক"),
            Items("9", "গৃহিনী"),
            Items("1", "ডাক্তার"),
            Items("2", "ইঞ্জিনিয়ার"),
            Items("3", "ছাত্র"),
            Items("10", "ছাত্রী"),
            Items("4", "কৃষক"),
            Items("5", "শ্রমিক"),
            Items("6", "চাকুরিজীবী"),
            Items("7", "ব্যাবসায়ী"),
            Items("8", "অন্যান্য"),
        )

        val educationList = listOf(
            Items("0", chooseItem),
            Items("1", "প্রাথমিক শিক্ষা"),
            Items("2", "জে.এস.সি"),
            Items("3", "জে.ডি.সি"),
            Items("4", "এস.এস.সি"),
            Items("5", "দাখিল"),
            Items("6", "এইচ.এস.সি"),
            Items("7", "আলিম"),
            Items("8", "ডিপ্লোমা"),
            Items("9", "অনার্স"),
            Items("10", "ফাজিল"),
            Items("11", "কামিল"),
            Items("12", "মাস্টার্স"),
            Items("13", "এম.বি.বি.এস"),
            Items("14", "অন্যান্য"),
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

        fun getEmptyHoldingType() : HoldingTypeModel
        {
            val holdingType = HoldingTypeModel()
            with(holdingType)
            {
                id = "0"
                holdingName = "হোল্ডিং নং চিহ্নিত করুন"
                summary = ""
            }

            return holdingType
        }

        fun getEmptyDivision() : DivisionModel
        {
            val division = DivisionModel()
            with(division)
            {
                id = "0"
                divisionNameEn = chooseItem
                divisionNameBn = chooseItem
            }

            return division
        }

        fun getEmptyDistrict() : DistrictModel
        {
            val district = DistrictModel()
            with(district)
            {
                id = "0"
                districtNameEn = chooseItem
                districtNameBn = chooseItem
            }

            return district
        }

        fun getEmptySubDistrict() : SubDistrictModel
        {
            val subDistrict = SubDistrictModel()
            with(subDistrict)
            {
                id = "0"
                subDistrictNameEn = chooseItem
                subDistrictNameBn = chooseItem
            }

            return subDistrict
        }
    }
}