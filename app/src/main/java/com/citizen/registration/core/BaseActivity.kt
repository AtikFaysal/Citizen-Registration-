package com.citizen.registration.core

import android.content.Context
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.citizen.registration.interfaces.InitialComponent
import io.github.inflationx.viewpump.ViewPumpContextWrapper

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 11/18/2021 at 12:53 PM
 */
abstract class BaseActivity : AppCompatActivity() , InitialComponent
{
    abstract override fun init()

    abstract override fun setToolbarTitle(title: String)

    /**
     * ...set toolbar title
     * ...modify title and textView if needed
     * @param title toolbar title
     * @param tvTitle textView object
     */
    fun setToolbarTitle(title: String, tvTitle: TextView)
    {
        tvTitle.text = title
    }

    open fun hideToolbar() {}

    open fun showToolbar() {}

    /**
     * Override Method to active calligraphy font in this activity
     * @param newBase - activity base
     */
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }
}