package com.citizen.registration.ui.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.citizen.registration.R
import com.citizen.registration.core.BaseActivity
import com.citizen.registration.core.BaseFragment
import com.citizen.registration.databinding.LayoutScanNidBinding
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel
import com.citizen.registration.utils.LoadingUtils
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 1/6/2022 at 1:38 PM
 */
@AndroidEntryPoint
class NidScanFragment : BaseFragment<LayoutScanNidBinding>()
{
    private lateinit var binding: LayoutScanNidBinding
    private val viewModel : CitizenRegistrationViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.layout_scan_nid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
        loadingUtils = LoadingUtils(mContext)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = viewDataBinding
        binding.lifecycleOwner = this
        binding.scan = viewModel
        init()
        onDataChanged()
        onClickListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.job.cancel()
    }

    override fun init() {
        (activity as BaseActivity).showToolbar() //display toolbar
        (activity as BaseActivity).setToolbarTitle("ন্যাশনাল আইডি স্ক্যান")
    }

    override fun onClickListener() {
        binding.fab.setOnClickListener {
            //ImageUtils.chooseImage(mActivity)
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent,100)
        }
    }

    private fun onDataChanged()
    {
        viewModel.isLoading.observe(viewLifecycleOwner, {
            if(it) loadingUtils.showProgressDialog()
            else loadingUtils.dismissProgressDialog()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (resultCode == Activity.RESULT_OK && data != null && requestCode == 100) //select image from gallery
            {
                data.let{
                    //val imageUri = it.data!!
                    //val filePath: String = ImageUtils.getRealPathFromURI(imageUri, mContext) // assign the file path
                    val bitmap = it.extras?.get("data")//ImageUtils.convertUriToBitmapImageAndSetInImageView(mContext, filePath)
                    if(bitmap != null) Log.d("isImageCaptured", "Bitmap found")
                    else Log.d("isImageCaptured", "Bitmap not found")
                }
            }else Log.d("isImageCaptured", "Failed to captured image")
        } catch (e: Exception) {
            Log.d("isImageCapturedError", e.toString())
        }
    }

    private fun recognizeText()
    {

    }
}