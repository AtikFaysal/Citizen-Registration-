package com.citizen.registration.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.citizen.registration.R
import com.citizen.registration.data.model.*
import com.citizen.registration.utils.constants.AppConstants.Companion.DISTRICT_SELECTION
import com.citizen.registration.utils.constants.AppConstants.Companion.DISTRICT_SELECTION_BN
import com.citizen.registration.utils.constants.AppConstants.Companion.DISTRICT_SELECTION_BN_PERMANENT
import com.citizen.registration.utils.constants.AppConstants.Companion.DISTRICT_SELECTION_PERMANENT
import com.citizen.registration.utils.constants.AppConstants.Companion.DIVISION_SELECTION
import com.citizen.registration.utils.constants.AppConstants.Companion.DIVISION_SELECTION_BN
import com.citizen.registration.utils.constants.AppConstants.Companion.DIVISION_SELECTION_BN_PERMANENT
import com.citizen.registration.utils.constants.AppConstants.Companion.DIVISION_SELECTION_PERMANENT
import com.citizen.registration.utils.constants.AppConstants.Companion.GENDER_SELECTION
import com.citizen.registration.utils.constants.AppConstants.Companion.HOLDING_SELECTION
import com.citizen.registration.utils.constants.AppConstants.Companion.SUB_DISTRICT_SELECTION
import com.citizen.registration.utils.constants.AppConstants.Companion.SUB_DISTRICT_SELECTION_BN
import com.citizen.registration.utils.constants.AppConstants.Companion.SUB_DISTRICT_SELECTION_BN_PERMANENT
import com.citizen.registration.utils.constants.AppConstants.Companion.SUB_DISTRICT_SELECTION_PERMANENT

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 11/28/2021 at 10:26 AM
 */
class SpinnerItemsAdapter(mContext: Context, private var itemList : List<Any>, private var tag : Int) : BaseAdapter()
{
    private var inflater = LayoutInflater.from(mContext)

    override fun getCount(): Int {
        return itemList.size
    }

    override fun getItem(i: Int): Any {
        return itemList[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(i: Int, mView: View?, viewGroup: ViewGroup): View {
        val view = mView ?: inflater.inflate(R.layout.model_spinner, null)
        try {
            val tvItem = view.findViewById(R.id.tv_item) as TextView
            val ivImage = view.findViewById(R.id.iv_drop_down) as ImageView
            val viewDivider = view.findViewById(R.id.view_divider) as View

            var selectedItem: String? = null

            selectedItem = when (tag) {
                GENDER_SELECTION//for gender selection
                -> {
                    val model = itemList[i] as Gender
                    model.title
                }
                HOLDING_SELECTION -> {
                    val model = itemList[i] as HoldingTypeModel
                    model.holdingName
                }

                DIVISION_SELECTION,DIVISION_SELECTION_PERMANENT -> {
                    val model = itemList[i] as DivisionModel
                    model.divisionNameEn
                }
                DIVISION_SELECTION_BN,DIVISION_SELECTION_BN_PERMANENT -> {
                    val model = itemList[i] as DivisionModel
                    model.divisionNameBn
                }

                DISTRICT_SELECTION,DISTRICT_SELECTION_PERMANENT -> {
                    val model = itemList[i] as DistrictModel
                    model.districtNameEn
                }
                DISTRICT_SELECTION_BN,DISTRICT_SELECTION_BN_PERMANENT -> {
                    val model = itemList[i] as DistrictModel
                    model.districtNameBn
                }

                SUB_DISTRICT_SELECTION,SUB_DISTRICT_SELECTION_PERMANENT -> {
                    val model = itemList[i] as SubDistrictModel
                    model.subDistrictNameEn
                }
                SUB_DISTRICT_SELECTION_BN,SUB_DISTRICT_SELECTION_BN_PERMANENT -> {
                    val model = itemList[i] as SubDistrictModel
                    model.subDistrictNameBn
                }

                else -> {
                    val model = itemList[i] as Items
                    model.title
                }
            }

            if (i > 0) {
                ivImage.visibility = View.GONE //drop down icon will be hide
                viewDivider.visibility = if(i == itemList.size - 1)  View.GONE else  View.GONE //drop down divider will be hide or visible
            }
            tvItem.text = selectedItem //set selected item name
        }catch (ex : Exception)
        {
            ex.printStackTrace()
        }

        return view
    }
}