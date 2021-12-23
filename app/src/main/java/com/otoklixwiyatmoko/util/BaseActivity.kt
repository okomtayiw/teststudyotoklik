package com.otoklixwiyatmoko.util

import android.app.Dialog
import android.util.Log.v
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.otoklixwiyatmoko.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_progress.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    private lateinit var mProgressDialog: Dialog
    /**
     * A function to show the success and error messages in snack bar component.
     */
    fun showErrorSnackBar(message: String, errorMessage: Boolean) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if (errorMessage) {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.red
                )
            )
        } else {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.green
                )
            )
        }
        snackBar.show()
    }

    fun getConvertTimeStampToDate(strDate: String, pattern: String = "dd MMM yyyy"): String? {
        try {
            val df = SimpleDateFormat(pattern, Locale.getDefault())
            val parsedDate = Date(strDate.toLong() * 1000);
            val timestampAsDateString = df.format(parsedDate);
            return timestampAsDateString
        } catch (e: NumberFormatException) {
            v("Log", "Number Exception : $e")
        }
        return null
    }

    fun showDialogProgress() {
        mProgressDialog = Dialog(this)
        mProgressDialog.setContentView(R.layout.dialog_progress)
        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()
    }


    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }


}