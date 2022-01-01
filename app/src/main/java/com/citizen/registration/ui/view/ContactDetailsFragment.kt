package com.citizen.registration.ui.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.citizen.registration.R
import com.citizen.registration.core.BaseActivity
import com.citizen.registration.core.BaseFragment
import com.citizen.registration.database.SharedPreferenceManager
import com.citizen.registration.databinding.LayoutContactDetailsBinding
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel
import com.citizen.registration.utils.*
import com.citizen.registration.utils.constants.ConstantItems
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ContactDetailsFragment: BaseFragment<LayoutContactDetailsBinding>()
{
    private lateinit var binding: LayoutContactDetailsBinding
    private val viewModel : CitizenRegistrationViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.layout_contact_details

    @Inject
    lateinit var prefManager : SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
        loadingUtils = LoadingUtils(mContext)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = viewDataBinding
        binding.lifecycleOwner = this
        binding.contact = viewModel
        init()
        onDataChanged()
        onClickListener()
        holdingTypeObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.job.cancel()
    }

    override fun init() {
        (activity as BaseActivity).showToolbar() //display toolbar
        (activity as BaseActivity).setToolbarTitle("যোগাযোগের ঠিকানা")
    }

    override fun onClickListener() {
        binding.btnPreview.setOnClickListener {
            if(onDataValidation())
                goToNextFragment(R.id.action_contactDetailsFragment_to_previewDataFragment, mRootView, null)
        }
    }

    private fun onDataChanged()
    {
        viewModel.isLoading.observe(viewLifecycleOwner, {
            if(it) loadingUtils.showProgressDialog()
            else loadingUtils.dismissProgressDialog()
        })
    }

    private fun holdingTypeObserver()
    {
        viewModel.holdingNoInfo.observe(viewLifecycleOwner, {
            if(it.isNotEmpty())
            {
                holdingTypeList.clear()
                holdingTypeList.addAll(it)
                holdingTypeList.add(0, ConstantItems.getEmptyHoldingType())
            }
        })
    }

    private fun onDataValidation() : Boolean
    {
        return when(viewModel.contactInfoValidation()) {
            FormErrors.INVALID_PHONE->{
                binding.etPhone.requestFocus()
                binding.etPhone.error = ErrorMessage.INVALID_PHONE
                false
            }
            else -> true
        }
    }
}