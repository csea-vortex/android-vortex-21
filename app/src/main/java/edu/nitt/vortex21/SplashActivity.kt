package edu.nitt.vortex21

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.BuildConfig
import edu.nitt.vortex21.databinding.ActivitySplashBinding
import edu.nitt.vortex21.helpers.viewLifecycle

class SplashActivity : AppCompatActivity() {

    private val binding by viewLifecycle(ActivitySplashBinding::inflate)
    private var canLaunchNextActivity = false
    private val TAG = "AppVersionStatus"
    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        checkUpdateAvailability()
    }

    private fun checkUpdateAvailability() {

        val appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        Log.i(TAG, "The current version code: " + BuildConfig.VERSION_CODE)
        Log.i(TAG, "The current version name: " + BuildConfig.VERSION_NAME)

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->

            Log.i(
                TAG, "appUpdateInfo.updateAvailability() = " + appUpdateInfo.updateAvailability()
                        + " and update is available if it equals " + UpdateAvailability.UPDATE_AVAILABLE
                        + " and appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) is " +
                        appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            )

            Log.i(
                TAG, "Available Version Code is " + appUpdateInfo.availableVersionCode()
            )

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {

                Log.i(TAG, "App needs to be updated.")

                alertDialog = this.let {
                    val builder = AlertDialog.Builder(it)

                    val dialogView = it.layoutInflater.inflate(R.layout.update_alert_dialog, null)

                    builder.setView(dialogView)

                    val btnCancel = dialogView.findViewById<Button>(R.id.btn_cancel)
                    val btnUpdate = dialogView.findViewById<Button>(R.id.btn_update)

                    btnCancel.setOnClickListener {
                        Log.i(TAG, "User closed the app.")
                        finish()
                    }

                    btnUpdate.setOnClickListener {
                        Log.i(TAG, "Updating app.")

                        try {
                            appUpdateManager.startUpdateFlowForResult(
                                appUpdateInfo,
                                AppUpdateType.IMMEDIATE,
                                this@SplashActivity,
                                APP_UPDATE_REQUEST_CODE
                            )
                        } catch (e: IntentSender.SendIntentException) {
                            e.printStackTrace()
                        }
                        finish()
                    }

                    builder.create()
                }

                alertDialog!!.setCancelable(false)
                alertDialog!!.show()

            } else {
                Log.i(TAG, "Can't update the app now.")
                canLaunchNextActivity = true
                startNextActivity()
            }
        }

        appUpdateInfoTask.addOnFailureListener { appUpdateInfo ->
            Log.i(TAG, "Task failed.")
            canLaunchNextActivity = true
            startNextActivity()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == APP_UPDATE_REQUEST_CODE) {
            // or finish?
            canLaunchNextActivity = true
            startNextActivity()
        } else {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(
                    this,
                    "App Update failed, please try again on the next app launch.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun startNextActivity() {
        val intent = Intent(this, MainActivity::class.java)
        Handler(Looper.getMainLooper()).postDelayed({
            if (canLaunchNextActivity) {
                startActivity(intent)
                finish()
                overridePendingTransition(0, 0)
            }
        }, 1000)

    }

    override fun onDestroy() {
        super.onDestroy()
        if(alertDialog != null){
            alertDialog!!.dismiss()
            alertDialog = null
        }
    }


    companion object {
        private const val APP_UPDATE_REQUEST_CODE = 17
    }
}
