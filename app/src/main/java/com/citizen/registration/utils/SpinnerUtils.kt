package com.citizen.registration.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import com.citizen.registration.adapter.SpinnerItemsAdapter
import com.citizen.registration.data.model.Gender
import com.citizen.registration.data.model.HoldingTypeModel
import com.citizen.registration.interfaces.ItemSelectionListener
import com.citizen.registration.utils.constants.AppConstants.Companion.GENDER_SELECTION
import com.citizen.registration.utils.constants.AppConstants.Companion.HOLDING_SELECTION

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 11/28/2021 at 10:34 AM
 */

/**
 * ...select an item from spinner
 * @param spinner spinner object
 * @param items list of items
 * @param tag identify the item list
 */
fun Context.selectItemFromSpinner(spinner: Spinner, items: List<Any>, tag: Int, onItemSelection : ItemSelectionListener) {
    val adapter = SpinnerItemsAdapter(this, items, tag)
    spinner.adapter = adapter

    try {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                val value: String
                if(position > 0)
                {
                    when (tag) {
                        GENDER_SELECTION -> {
                            val model = items[position] as Gender
                            value = model.title
                            onItemSelection.onItemSelected(tag,value)
                        }
                        HOLDING_SELECTION -> {
                            val model = items[position] as HoldingTypeModel
                            value = model.holdingName
                            onItemSelection.onItemSelected(tag,value)
                        }
                        else -> onItemSelection.onItemSelected(tag,items[position])
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }catch (ex : Exception)
    {
        Log.d("spinnerException", ex.toString())
    }
}