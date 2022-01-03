package com.citizen.registration.core

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.citizen.registration.data.model.DistrictModel
import com.citizen.registration.data.model.DivisionModel
import com.citizen.registration.data.model.HoldingTypeModel
import com.citizen.registration.data.model.SubDistrictModel
import com.citizen.registration.database.room.entity.SuggestionEntity
import com.citizen.registration.interfaces.InitialComponent
import com.citizen.registration.utils.LoadingUtils
import com.google.android.material.snackbar.Snackbar

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 11/18/2021 at 12:53 PM
 */
abstract class BaseFragment<T : ViewDataBinding> : Fragment() , InitialComponent
{
    companion object{
        var holdingTypeList = ArrayList<HoldingTypeModel>()
        var subDistrictList = ArrayList<SubDistrictModel>()
        var districtList = ArrayList<DistrictModel>()
        var divisionList = ArrayList<DivisionModel>()
        var suggestionList = ArrayList<String>()
    }

    protected lateinit var loadingDialog : AlertDialog
    protected lateinit var mContext : Context
    protected lateinit var mActivity : Activity
    protected lateinit var baseActivity: BaseActivity
    protected lateinit var loadingUtils : LoadingUtils

    protected lateinit var mRootView: View
    protected lateinit var  viewDataBinding: T

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater,layoutId, container, false)
        mRootView = viewDataBinding.root
        hideOrVisibleLoading()
        return mRootView
    }

    override fun setToolbarTitle(title: String) { }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return super.onCreateAnimation(transit, enter, nextAnim)
    }

    override fun onDetach() {
        super.onDetach()
        //hideKeyboard(requireActivity())
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        hideKeyboard()
    }

    abstract override fun init()

    /**
     * ...Display a snackBar
     * ...If any error is encountered, then show this SnackBar
     * @param view root view
     */
    fun errorSnackBar(view: View, message: String)
    {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    /**
     * ...disable button
     * @param button button view
     */
    protected open fun onButtonDisable(button: Button) {
        button.isClickable = false
    }

    /**
     * ...disable button
     * @param button button view
     */
    protected open fun onButtonDisable(button: TextView) {
        button.isClickable = false
    }

    /**
     * ...enable button
     * @param button button view
     */
    protected open fun onButtonEnable(button: Button) {
        button.isClickable = true
    }

    /**
     * ...enable button
     * @param button button view
     */
    protected open fun onButtonEnable(button: TextView) {
        button.isClickable = true
    }

    /**
     * ...on click listener
     */
    abstract fun onClickListener()

    /**
     * ...hide and visible loading progress dialog
     * ...while value is true then visible progress dialog
     * ...otherwise dismiss progress dialog
     */
    protected open fun hideOrVisibleLoading(){}

    private fun hideKeyboard() {
        val view = mActivity.currentFocus
        if (view != null) {
            val imm = mActivity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun showKeyboard() {
        val imm = mActivity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
}