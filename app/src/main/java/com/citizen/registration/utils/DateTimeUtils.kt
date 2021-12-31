package com.citizen.registration.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.widget.DatePicker
import android.widget.TextView
import com.citizen.registration.utils.constants.AppConstants.Companion.DD_MMM_YYYY
import com.citizen.registration.utils.constants.AppConstants.Companion.DD_MM_YYYY
import com.citizen.registration.utils.constants.AppConstants.Companion.HH_MM_SS
import com.citizen.registration.utils.constants.AppConstants.Companion.MMMM_YYYY
import com.citizen.registration.utils.constants.AppConstants.Companion.MMM_DD_YYYY
import com.citizen.registration.utils.constants.AppConstants.Companion.MMM_DD_YYYY_HH_MM_AA
import com.citizen.registration.utils.constants.AppConstants.Companion.MM_YYYY
import com.citizen.registration.utils.constants.AppConstants.Companion.YYYY_MM
import com.citizen.registration.utils.constants.AppConstants.Companion.YYYY_MMM_DD_HH_MM_AA
import com.citizen.registration.utils.constants.AppConstants.Companion.YYYY_MM_DD
import com.citizen.registration.utils.constants.AppConstants.Companion.YYYY_MM_DD_HH_MM_SS
import com.citizen.registration.utils.DateTimeUtils.Companion.dateConversion
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 11/27/2021 at 3:35 PM
 */
class DateTimeUtils{
    companion object{
        private var dateFormatList = listOf(YYYY_MM_DD_HH_MM_SS,YYYY_MMM_DD_HH_MM_AA,MMM_DD_YYYY_HH_MM_AA,MMM_DD_YYYY, YYYY_MM_DD,DD_MMM_YYYY, DD_MM_YYYY, MMMM_YYYY, MM_YYYY, YYYY_MM, HH_MM_SS)

        /**
         * ...convert any date to specific date format
         * ...return converted date
         */
        @SuppressLint("SimpleDateFormat")
        fun dateConversion(orgDate : String, out : String) : String
        {
            var result = ""
            val outFormat = SimpleDateFormat(out)
            var format: SimpleDateFormat

            for(item in dateFormatList)
            {
                try {
                    format = SimpleDateFormat(item)
                    val date = format.parse(orgDate)
                    result = outFormat.format(date)
                    break
                }catch (ex : Exception)
                {
                    ex.printStackTrace()
                }
            }
            return result
        }
    }
}

/**
 * pick any date from date picker
 */
fun Activity.pickPreviousDate(tvDate: TextView) {
    val calendar = Calendar.getInstance()
    var day = calendar[Calendar.DAY_OF_MONTH]
    var month = calendar[Calendar.MONTH]
    var year = calendar[Calendar.YEAR]

    val mDateSetListener = OnDateSetListener { v: DatePicker, mYear: Int, monthOfYear: Int, dayOfMonth: Int ->
        year = mYear;month = monthOfYear + 1;day = dayOfMonth
        val strMonth = if (month < 10) "0$month" else month.toString()
        val strDay = if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth.toString()
        val date = "$mYear-$strMonth-$strDay"
        tvDate.text = dateConversion(date,MMM_DD_YYYY)
    }

    val datePickerDialog = DatePickerDialog(this, mDateSetListener, year, month, day)
    datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 1000
    datePickerDialog.show()
}