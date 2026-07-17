package com.ytshare.app

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ShareReceiverActivity : AppCompatActivity() {

    private lateinit var videoUrl: String
    private var isPlaylist = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_receiver)

        // Handle shared YouTube link
        if (intent?.action == Intent.ACTION_SEND && intent.type == "text/plain") {
            videoUrl = intent.getStringExtra(Intent.EXTRA_TEXT) ?: ""
            
            // Detect if it's a playlist
            isPlaylist = videoUrl.contains("list=")
            
            setupUI()
        }
    }

    private fun setupUI() {
        val titleText = findViewById<TextView>(R.id.tv_title)
        val urlText = findViewById<TextView>(R.id.tv_url)
        val btnDownload = findViewById<Button>(R.id.btn_download)
        val btnSelectVideos = findViewById<Button>(R.id.btn_select_videos)
        val qualitySpinner = findViewById<Spinner>(R.id.spinner_quality)

        titleText.text = if (isPlaylist) "تم اكتشاف قائمة تشغيل" else "تم اكتشاف فيديو"
        urlText.text = videoUrl.take(60) + if (videoUrl.length > 60) "..." else ""

        // Quality options
        val qualities = arrayOf("1080p", "720p", "480p", "360p", "صوت فقط (MP3)")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, qualities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        qualitySpinner.adapter = adapter

        if (isPlaylist) {
            btnSelectVideos.visibility = android.view.View.VISIBLE
            btnDownload.text = "Download Entire Playlist"
        } else {
            btnSelectVideos.visibility = android.view.View.GONE
            btnDownload.text = "تحميل الفيديو"
        }

        btnDownload.setOnClickListener {
            val selectedQuality = qualitySpinner.selectedItem.toString()
            startDownload(selectedQuality)
        }

        btnSelectVideos.setOnClickListener {
            showPlaylistSelectionDialog()
        }
    }

    private fun startDownload(quality: String) {
            Toast.makeText(this, "جاري التحميل بجودة $quality...", Toast.LENGTH_LONG).show()
        
        // Simulate download progress (in real app use WorkManager + yt-dlp)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        val statusText = findViewById<TextView>(R.id.tv_status)
        
        progressBar.visibility = android.view.View.VISIBLE
        statusText.visibility = android.view.View.VISIBLE
        
        // Fake download simulation
        Thread {
            for (i in 0..100 step 10) {
                Thread.sleep(300)
                runOnUiThread {
                    progressBar.progress = i
                    statusText.text = "جاري التحميل... $i%"
                }
            }
            runOnUiThread {
                statusText.text = "تم التحميل بنجاح!"
                Toast.makeText(this, "Saved to Downloads!", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun showPlaylistSelectionDialog() {
        val videos = listOf(
            "Video 1 - Introduction",
            "Video 2 - Getting Started", 
            "Video 3 - Advanced Techniques",
            "Video 4 - Final Tips"
        )
        
        val checkedItems = BooleanArray(videos.size) { true }
        
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Select Videos to Download")
            .setMultiChoiceItems(videos.toTypedArray(), checkedItems) { _, which, isChecked ->
                checkedItems[which] = isChecked
            }
            .setPositiveButton("Download Selected") { _, _ ->
                val selectedCount = checkedItems.count { it }
                Toast.makeText(this, "Downloading $selectedCount videos...", Toast.LENGTH_SHORT).show()
                // Start download for selected videos
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}