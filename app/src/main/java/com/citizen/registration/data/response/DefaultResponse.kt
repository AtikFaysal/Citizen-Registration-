package com.citizen.registration.data.response

import com.citizen.registration.data.model.DataModel
import com.citizen.registration.data.model.HoldingTypeModel
import com.google.gson.annotations.SerializedName

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 12/7/2021 at 1:55 PM
 */
class DefaultResponse
{
    @SerializedName("response_code") var responseCode = 0
    @SerializedName("message") lateinit var message : String
    @SerializedName("today") lateinit var today : String
    @SerializedName("last_seven") lateinit var lastWeek : String
    @SerializedName("total") lateinit var total : String
    @SerializedName("data") var data : DataModel?= DataModel()
    @SerializedName("holding_list") var holdingList = ArrayList<HoldingTypeModel>()
}