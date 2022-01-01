package com.citizen.registration.network.api

import com.citizen.registration.data.response.DefaultResponse
import com.citizen.registration.data.response.LoginResponse
import retrofit2.http.*

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 11/18/2021 at 12:49 PM
 */
interface ApiServices
{
    @FormUrlEncoded
    @POST("api/login")
    suspend fun login(
        @Field("email") userName : String ,
        @Field("password") password : String
    ): LoginResponse

    @GET("api/get-dashboard-data")
    suspend fun getDashboardReport(): DefaultResponse

    @GET("api/get-holding-name-type")
    suspend fun getHoldingType(): DefaultResponse

    @GET("api/check-nid-number")
    suspend fun checkDuplicateIdentity(
        @Query("nid") nid : String
    ): DefaultResponse

    @GET("api/get-location-data")
    suspend fun getPlaceInfo(): DefaultResponse

    @FormUrlEncoded
    @POST("api/store-citizenship-info")
    suspend fun citizenRegistration(
        @Field("tracking_number") trackingNumber : String ,
        @Field("user_id") userId : String,
        @Field("sub_user_id") subUserId : String,
        @Field("user_name") userName : String,
        @Field("sameaspresentaddress") sameAsAddress : String,
        @Field("nid_or_birth_registration") identityType : String,
        @Field("pictures") picture : String,
        @Field("holding_no") holdingNo : String,
        @Field("national_id") nationalId : String,
        @Field("birth_registration") birthReg : String,
        @Field("passport") passport : String,
        @Field("date_of_birth") dateOfBirth : String,
        @Field("english_name") englishName : String,
        @Field("bangla_name") banglaName : String,
        @Field("gender") gender : String,
        @Field("marital_status") maritalStatus : String,
        @Field("en_father_name") enFatherName : String,
        @Field("bn_father_name") bnFatherName : String,
        @Field("en_mother_name") enMotherName : String,
        @Field("bn_mother_name") bnMotherName : String,
        @Field("pesha") occupation : String,
        @Field("educational_qualifications") education : String,
        @Field("religion") religion : String,
        @Field("basinda") liveIn : String,
        @Field("p_en_address") pEnAddress : String,
        @Field("p_bn_address") pBnAddress : String,
        @Field("p_en_bloc") pEnBlock : String,
        @Field("p_bn_bloc") pBnBlock : String,
        @Field("p_en_word") pEnWord : String,
        @Field("p_bn_word") pBnWord : String,
        @Field("p_en_post") pEnPost : String,
        @Field("p_bn_post") pBnPost : String,
        @Field("p_en_division") pEnDivision : String,
        @Field("p_bn_division") pBnDivision : String,
        @Field("p_en_district") pEnDistrict : String,
        @Field("p_bn_district") pBnDistrict : String,
        @Field("p_en_upazila") pEnSubDistrict : String,
        @Field("p_bn_upazila") pBnSubDistrict : String,
        @Field("perma_en_address") perEnAddress : String,
        @Field("perma_bn_address") perBnAddress : String,
        @Field("perma_en_bloc") perEnBlock : String,
        @Field("perma_bn_bloc") perBnBlock : String,
        @Field("perma_en_word") perEnWord : String,
        @Field("perma_bn_word") perBnWord : String,
        @Field("perma_en_post") perEnPost : String,
        @Field("perma_bn_post") perBnPost : String,
        @Field("perma_en_division") perEnDivision : String,
        @Field("perma_bn_division") perBnDivision : String,
        @Field("perma_en_district") perEnDistrict : String,
        @Field("perma_bn_district") perBnDistrict : String,
        @Field("perma_en_upazila") perEnSubDistrict : String,
        @Field("perma_bn_upazila") perBnSubDistrict : String,
        @Field("conta_mobail") mobile : String,
        @Field("conta_mail") mail : String,
        @Field("en_attachment") enAttachment : String,
        @Field("bn_attachment") bnAttachment : String,
        @Field("en_Husband_name") husbandNameEn : String,
        @Field("bn_Husband_name") husbandNameBn : String
    ): DefaultResponse
}