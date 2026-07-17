package com.ytshare.app

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.yausername.youtubedl_android.YoutubeDL
import com.yausername.youtubedl_android.YoutubeDLRequest
import java.io.File

class ShareReceiverActivity : AppCompatActivity() {

    private lateinit var videoUrl: String
    private var isPlaylist = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_receiver)

        if (intent?.action == android.content.Intent.ACTION_SEND) {
            videoUrl = intent.getStringExtra(android.content.Intent.EXTRA_TEXT) ?: ""
            isPlaylist = videoUrl.contains("list=")
            setupUI()
        }

        // طلب صلاحيات التخزين
        checkStoragePermission()
    }

    private fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                100
            )
        }
    }

    private fun setupUI() {
        val titleText = findViewById<TextView>(R.id.tv_title)
        val urlText = findViewById<TextView>(R.id.tv_url)
        val btnDownload = findViewById<Button>(R.id.btn_download)
        val qualitySpinner = findViewById<Spinner>(R.id.spinner_quality)

        titleText.text = if (isPlaylist) "قائمة تشغيل يوتيوب" else "فيديو يوتيوب"
        urlText.text = videoUrl.take(70) + "..."

        val qualities = arrayOf("1080p", "720p", "480p", "صوت فقط")
        qualitySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, qualities)

        btnDownload.setOnClickListener {
            val quality = qualitySpinner.selectedItem.toString()
            startRealDownload(quality)
        }
    }

    private fun startRealDownload(quality: String) {
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        val statusText = findViewById<TextView>(R.id.tv_status)

        progressBar.visibility = android.view.View.VISIBLE
        statusText.visibility = android.view.View.VISIBLE
        statusText.text = "جاري التحميل..."

        Thread {
            try {
                YoutubeDL.getInstance().init(this)

                val request = YoutubeDLRequest(videoUrl)
                val downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath

                request.addOption("-o", "$downloadPath/%(title)s.%(ext)s")
                request.addOption("-f", getFormat(quality))

                YoutubeDL.getInstance().execute(request) { progress, _, _ ->
                    runOnUiThread {
                        progressBar.progress = progress.toInt()
                        statusText.text = "جاري التحميل... $progress%"
                    }
                }

                runOnUiThread {
                    statusText.text = "تم التحميل بنجاح!"
                    Toast.makeText(this, "تم الحفظ في التنزيلات", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    statusText.text = "فشل التحميل: ${e.message}"
                }
            }
        }.start()
    }

    private fun getFormat(quality: String): String {
        return when (quality) {
            "1080p" -> "bestvideo[height<=1080]+bestaudio/best"
            "720p" -> "bestvideo[height<=720]+bestaudio/best"
            "480p" -> "bestvideo[height<=480]+bestaudio/best"
            "صوت فقط" -> "bestaudio"
            else -> "best"
        }
    }
}
