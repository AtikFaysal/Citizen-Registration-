package com.citizen.registration.ui.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.citizen.registration.R
import com.citizen.registration.core.BaseActivity
import com.citizen.registration.core.BaseFragment
import com.citizen.registration.database.SharedPreferenceManager
import com.citizen.registration.databinding.LayoutDashboardBinding
import com.citizen.registration.ui.viewmodel.DashboardViewModel
import com.citizen.registration.utils.*
import com.citizen.registration.utils.constants.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : BaseFragment<LayoutDashboardBinding>()
{
    private lateinit var binding: LayoutDashboardBinding
    private val viewModel : DashboardViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.layout_dashboard

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
        binding.dashboard = viewModel
        init()
        onReportObserver()
        onDataChanged()
        onClickListener()
        viewModel.getReport()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.job.cancel()
    }

    override fun init() {
        (activity as BaseActivity).showToolbar() //display toolbar
        (activity as BaseActivity).setToolbarTitle("ড্যাশবোর্ড")

        binding.tvUserName.text = prefManager.getUserFullName()
        binding.tvPhone.text = prefManager.getPhone()
    }

    override fun onClickListener() {
        binding.tvLogout.setOnClickListener {
            prefManager.clearPrefManager()
            goToNextFragment(R.id.action_dashboard_to_loginFragment, mRootView, null)
        }

        binding.btnAddNew.setOnClickListener {
            goToNextFragment(R.id.action_dashboard_to_basicInfoFragment, mRootView, null)
        }
    }

    private fun onDataChanged()
    {
        viewModel.isLoading.observe(viewLifecycleOwner, {
            if(it) loadingUtils.showProgressDialog()
            else loadingUtils.dismissProgressDialog()
        })
    }

    private fun onReportObserver()
    {
        viewModel.reportData.observe(viewLifecycleOwner, {
            when (it.responseCode) {
                Constants.RESPONSE_SUCCESS_CODE -> {
                    viewModel.mlToday.value = it.today
                    viewModel.mlLastWeek.value = it.lastWeek
                    viewModel.mlTotal.value = it.total
                }
                Constants.NETWORK_ERROR_CODE -> {
                    handleNetworkError(mContext) { viewModel.getReport() }
                }
                else -> {
                    viewModel.mlToday.value = "0"
                    viewModel.mlLastWeek.value = "0"
                    viewModel.mlTotal.value = "0"
                }
            }
        })
    }

}