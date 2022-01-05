package com.citizen.registration.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.*
import kotlin.math.roundToInt
import android.graphics.Bitmap
import com.citizen.registration.utils.constants.AppConstants.Companion.MAXIMUM_IMAGE_SIZE
import androidx.core.app.ActivityCompat.startActivityForResult


/**
 * Created by Atik Faysal (Android Developer)
 * Create on 4/8/2021
 * Email: mdatikfaysal@gmail.com
 * *** Happy Coding ***
 */
class ImageUtils
{
    companion object{
        const val imagePicker = 1024
        /**
         * Choose image from gallery
         * on permission manager
         * @param activity Activity
         */
        fun chooseImage(activity: Activity) {
            // permission is granted so open the gallery
            // check for permanent denial of permission
            // navigate user to app settings
            val permissionListener: MultiplePermissionsListener = object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted())
                    {
                        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        //startActivityForResult(activity,cameraIntent,100,null)
                        startActivityForResult(activity, cameraIntent,100,null )
                    } else activity.warningToast("You need to allow all permission")
                }

                override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                    token.continuePermissionRequest()
                }
            }
            if (activity.isImageCapturePermissionGranted()) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(activity,cameraIntent,100,null)
            } else activity.requesftForCaptureImage(permissionListener)
        }

        @JvmStatic
        fun chooseImage(activity: Activity, requestCode : Int) {
            // permission is granted so open the gallery
            // check for permanent denial of permission
            // navigate user to app settings
            val permissionListener: MultiplePermissionsListener = object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted())
                        pickImageFromGallery(activity, requestCode)
                    else activity.warningToast("You need to allow all permission")
                }

                override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                    token.continuePermissionRequest()
                }
            }
            if (activity.isImageCapturePermissionGranted())pickImageFromGallery(activity, requestCode)
            else activity.requesftForCaptureImage(permissionListener)

        }

        /**
         * Open gallery to pick an image
         */
        @JvmStatic
        fun pickImageFromGallery(activity: Activity, GALLERY_REQUEST_CODE: Int) {
            //Create an Intent with action as ACTION_PICK
            val intent = Intent(Intent.ACTION_PICK)
            // Sets the type as image/*. This ensures only components of type image are selected
            intent.type = "image/*"
            //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            // Launching the Intent
            activity.startActivityForResult(intent, GALLERY_REQUEST_CODE)
        }

        /**
         * Get the absolute file path from the uri
         *
         * @param contentURI - image uri
         * @return - return absolute file path
         */
        fun getRealPathFromURI(contentURI: Uri, context: Context): String {
            val result: String
            val cursor = context.contentResolver.query(contentURI, null, null, null, null)
            if (cursor == null) {
                result = contentURI.path!!
            } else {
                cursor.moveToFirst()
                val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                result = cursor.getString(idx)
                cursor.close()
            }
            return result
        }

        /**
         * Convert image uri to bitmap, calculate the size and if it's less than 400 kb then set in circular image view
         *
         * @param path - image file path - String
         */
        fun convertUriToBitmapImageAndSetInImageView(context: Context, path: String): Bitmap? {
            // convert the image uri to bitmap in order to load it in image view
            lateinit var bitmap: Bitmap
            try {
                bitmap = compressImage(path)

                //Bitmap bitmap1 = bitmap;
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val imageInByte = stream.toByteArray()
                val lengthbmp = imageInByte.size.toLong()
                val fileSizeInKB = lengthbmp / 1024f // convert bmp to kb
                if (fileSizeInKB > MAXIMUM_IMAGE_SIZE) { // image size more than 400kb
                    (context as Activity).warningToast("Image size is more than $MAXIMUM_IMAGE_SIZE Kb!")
                    return null // return
                }

                // execute the following codes if image file less than 400kb
                val exif = ExifInterface(path)
                val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1)
                //Log.d("EXIF", "Exif: " + orientation);
                val matrix = Matrix()
                if (orientation == 6) {
                    matrix.postRotate(90f)
                } else if (orientation == 3) {
                    matrix.postRotate(180f)
                } else if (orientation == 8) {
                    matrix.postRotate(270f)
                }
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true) // rotating bitmap
            } catch (e: IOException) {
            }
            return bitmap
        }

        /**
         * Reduce image size without change quality
         * @param filePath image path
         * @return image bitmap
         */
        private fun compressImage(filePath: String): Bitmap {

            //String filePath = getPath(imageUri);
            lateinit var scaledBitmap: Bitmap
            val options = BitmapFactory.Options()

            //by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
            //you try the use the bitmap here, you will get null.
            options.inJustDecodeBounds = true
            var bmp = BitmapFactory.decodeFile(filePath, options)
            var actualHeight = options.outHeight
            var actualWidth = options.outWidth

            //max Height and width values of the compressed image is taken as 816x612
            val maxHeight = 816.0f
            val maxWidth = 612.0f
            var imgRatio = (actualWidth / actualHeight).toFloat()
            val maxRatio = maxWidth / maxHeight

            //width and height values are set maintaining the aspect ratio of the image
            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                when {
                    imgRatio < maxRatio -> {
                        imgRatio = maxHeight / actualHeight
                        actualWidth = (imgRatio * actualWidth).toInt()
                        actualHeight = maxHeight.toInt()
                    }
                    imgRatio > maxRatio -> {
                        imgRatio = maxWidth / actualWidth
                        actualHeight = (imgRatio * actualHeight).toInt()
                        actualWidth = maxWidth.toInt()
                    }
                    else -> {
                        actualHeight = maxHeight.toInt()
                        actualWidth = maxWidth.toInt()
                    }
                }
            }

            //setting inSampleSize value allows to load a scaled down version of the original image
            options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight)

            //inJustDecodeBounds set to false to load the actual bitmap
            options.inJustDecodeBounds = false

            //this options allow android to claim the bitmap memory if it runs low on memory
            options.inPurgeable = true
            options.inInputShareable = true
            options.inTempStorage = ByteArray(16 * 1024)
            try {
            //load the bitmap from its path
                bmp = BitmapFactory.decodeFile(filePath, options)
            } catch (exception: OutOfMemoryError) {
                exception.printStackTrace()
            }
            try {
                scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888)
            } catch (exception: OutOfMemoryError) {
                exception.printStackTrace()
            }
            val ratioX = actualWidth / options.outWidth.toFloat()
            val ratioY = actualHeight / options.outHeight.toFloat()
            val middleX = actualWidth / 2.0f
            val middleY = actualHeight / 2.0f
            val scaleMatrix = Matrix()
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)
            val canvas = Canvas(scaledBitmap!!)
            canvas.setMatrix(scaleMatrix)
            canvas.drawBitmap(bmp, middleX - bmp.width / 2, middleY - bmp.height / 2, Paint(Paint.FILTER_BITMAP_FLAG))

            //check the rotation of the image and display it properly
            val exif: ExifInterface
            try {
                exif = ExifInterface(filePath)
                val orientation = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION, 0)
                Log.d("EXIF", "Exif: $orientation")
                val matrix = Matrix()
                when (orientation) {
                    6 -> {
                        matrix.postRotate(90f)
                        Log.d("EXIF", "Exif: $orientation")
                    }
                    3 -> {
                        matrix.postRotate(180f)
                        Log.d("EXIF", "Exif: $orientation")
                    }
                    8 -> {
                        matrix.postRotate(270f)
                        Log.d("EXIF", "Exif: $orientation")
                    }
                }
                scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                        scaledBitmap.width, scaledBitmap.height, matrix,
                        true)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val out: FileOutputStream
            val filename = getFilename()
            try {
                out = FileOutputStream(filename)

            //write the compressed bitmap at the destination specified by filename.
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            return scaledBitmap
        }

        /**
         * Get real path
         * @return file path
         */
        private fun getFilename(): String {
            val file = File(Environment.getExternalStorageDirectory().path, "MyFolder/Images")
            if (!file.exists()) {
                file.mkdirs()
            }
            return file.absolutePath + "/" + System.currentTimeMillis() + ".jpg"
        }

        /**
         * Calculate size
         * @param options bitmap option
         * @param reqWidth image height
         * @param reqHeight image width
         * @return size
         */
        private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1
            if (height > reqHeight || width > reqWidth) {
                val heightRatio = (height.toFloat() / reqHeight.toFloat()).roundToInt()
                val widthRatio = (width.toFloat() / reqWidth.toFloat()).roundToInt()
                inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
            }
            val totalPixels = (width * height).toFloat()
            val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()
            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++
            }
            return inSampleSize
        }

        fun encodeImage(bm: Bitmap): String {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            return Base64.encodeToString(byteArray, Base64.DEFAULT)
        }
    }
}