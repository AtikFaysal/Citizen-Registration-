package com.citizen.registration.data.model

import com.google.gson.annotations.SerializedName

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 1/1/2022 at 12:45 PM
 */
class DivisionModel
{
    @SerializedName("id") var id = ""
    @SerializedName("div_name") var divisionNameEn = ""
    @SerializedName("bn_name") var divisionNameBn = ""
}

class DistrictModel
{
    @SerializedName("id") var id = ""
    @SerializedName("division_id") var divisionId = ""
    @SerializedName("ds_name") var districtNameEn = ""
    @SerializedName("bn_name") var districtNameBn = ""
}

class SubDistrictModel
{
    @SerializedName("id") var id = ""
    @SerializedName("district_id") var districtId = ""
    @SerializedName("up_name") var subDistrictNameEn = ""
    @SerializedName("bn_name") var subDistrictNameBn = ""
}