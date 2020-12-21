package edu.nitt.vortex2021

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.media.AudioManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.BuildConfig
import edu.nitt.vortex2021.databinding.ActivitySplashBinding
import edu.nitt.vortex2021.helpers.viewLifecycle


class SplashActivity : AppCompatActivity() {

    private val binding by viewLifecycle(ActivitySplashBinding::inflate)
    private var canLaunchNextActivity = false
    private var nextActivityRunnable: Runnable? = null

    private var splashSkipInitiated = false

    private val runnableScheduler = Handler(Looper.getMainLooper())

    private val TAG = "AppVersionStatus"
    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initSplashAnimation()
        initSplashSkipListener()
        checkUpdateAvailability()
    }

    private fun initSplashSkipListener() {
        binding.root.setOnClickListener {
            if (canLaunchNextActivity and !splashSkipInitiated) {
                splashSkipInitiated = true
                startNextActivity(0)
            }
        }
    }

    private fun initSplashAnimation() {
        val uri = Uri.parse("android.resource://${packageName}/" + R.raw.splash)
        binding.video.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setAudioFocusRequest(AudioManager.AUDIOFOCUS_NONE)
            } else {
                val audioManager = applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                audioManager.requestAudioFocus(
                    null,
                    AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK
                )
            }
            setOnCompletionListener { start() }
            setVideoURI(uri)
            setOnPreparedListener {
                start()
            }
        }
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
            Log.i(TAG, "Available Version Code is " + appUpdateInfo.availableVersionCode())

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {

                Log.i(TAG, "App needs to be updated.")

                alertDialog = MaterialAlertDialogBuilder(this).apply {
                    val view = layoutInflater.inflate(R.layout.update_alert_dialog, binding.root, false)
                    setView(view)
                    setCancelable(false)
                    view.findViewById<MaterialButton>(R.id.btn_update).setOnClickListener {
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
                    setOnDismissListener {
                        finish()
                    }
                }.create().apply { show() }
            } else {
                Log.i(TAG, "Can't update the app now.")
                canLaunchNextActivity = true
                startNextActivity()
            }
        }

        appUpdateInfoTask.addOnFailureListener {
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

    private fun startNextActivity(delay: Long = 3000) {
        // If there is already a launch scheduled then disable it
        nextActivityRunnable?.apply {
            runnableScheduler.removeCallbacks(this)
        }

        val intent = Intent(this, MainActivity::class.java)

        nextActivityRunnable = Runnable {
            if (canLaunchNextActivity) {
                startActivity(intent)
                finish()
            }
        }.apply {
            runnableScheduler.postDelayed(this, delay)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        alertDialog?.dismiss()
        alertDialog = null
    }

    companion object {
        private const val APP_UPDATE_REQUEST_CODE = 17
    }
}
