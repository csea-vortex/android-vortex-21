package edu.nitt.vortex21

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import edu.nitt.vortex21.databinding.ActivitySplashBinding
import edu.nitt.vortex21.helpers.viewLifecycle

class SplashActivity : AppCompatActivity() {

    private val binding by viewLifecycle(ActivitySplashBinding::inflate)
    private var canLaunchNextActivity = false
    val TAG="AppVersionStatus"

    override fun onStart() {
        super.onStart()
        checkUpdateAvailability()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        startNextActivity()
    }

    private fun checkUpdateAvailability() {

        var alertDialog: AlertDialog? = null
        val appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->

            Log.i(TAG,"So regarding update "+appUpdateInfo.updateAvailability()+"->"+
                    UpdateAvailability.UPDATE_AVAILABLE+" okay.")

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {

                Log.i(TAG,"Have to update app.")

                alertDialog = this.let {
                    val builder = AlertDialog.Builder(it)

                    builder.apply {
                        setPositiveButton("UPDATE",
                            DialogInterface.OnClickListener { dialog, id ->
                                Log.i(TAG,"Updating app.")

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

                            })

                        setNegativeButton("CLOSE",
                            DialogInterface.OnClickListener { dialog, id ->
                                Log.i(TAG,"User closed app.")
                                finish()
                            })
                    }

                    builder.create()
                }

                alertDialog!!.setCancelable(false)
                alertDialog!!.show()

            } else {
                Log.i(TAG,"Latest app already exist.")
                canLaunchNextActivity = true
            }
        }

        appUpdateInfoTask.addOnFailureListener { appUpdateInfo ->
            Log.i(TAG,"Task failed.")
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == APP_UPDATE_REQUEST_CODE) {
            if (resultCode != Activity.RESULT_OK) {
                Toast.makeText(this,
                    "App Update failed, please try again on the next app launch.",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun startNextActivity() {
        val intent = Intent(this, MainActivity::class.java)
        Handler(Looper.getMainLooper()).postDelayed({
            if(canLaunchNextActivity) {
                startActivity(intent)
                finish()
                overridePendingTransition(0, 0)
            }
        }, 1000)

    }

    companion object {
        private const val APP_UPDATE_REQUEST_CODE = -15
    }
}
