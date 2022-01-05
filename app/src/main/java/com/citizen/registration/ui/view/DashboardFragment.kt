package com.citizen.registration.ui.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
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
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextRecognizer
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import javax.inject.Inject
import android.util.SparseArray
import com.google.android.gms.vision.text.TextBlock

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
            //ImageUtils.chooseImage(mActivity)
            //val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            //startActivityForResult(cameraIntent,100 )
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            if (resultCode == Activity.RESULT_OK && data != null && requestCode == 100) //select image from gallery
            {
                data.data?.let{
                    val imageUri = it
                    val filePath: String = ImageUtils.getRealPathFromURI(imageUri, mContext) // assign the file path
                    val bitmap = ImageUtils.convertUriToBitmapImageAndSetInImageView(mContext, filePath)
                    if(bitmap != null) recognizeText(bitmap)
                }?.run {
                    Log.d("isImageCaptured", "Image not found")
                }
            }else Log.d("isImageCaptured", "Failed to captured image")
        } catch (e: Exception) {
            Log.d("isImageCaptured", e.toString())
        }
    }

    private fun recognizeText(image : Bitmap)
    {
        val textRecognizer = TextRecognizer.Builder(mContext).build()
        val imageFrame = Frame.Builder()
            .setBitmap(image)
            .build()

        var imageText = ""

        val textBlocks: SparseArray<TextBlock> = textRecognizer.detect(imageFrame)
        for (i in 0 until textBlocks.size()) {
            val textBlock = textBlocks[textBlocks.keyAt(i)]
            imageText = textBlock.value // return string
        }
        Log.d("imageToText","text is: $imageText")
    }
}