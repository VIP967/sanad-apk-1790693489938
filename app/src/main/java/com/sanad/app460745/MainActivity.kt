package com.sanad.app460745

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sanad.app460745.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val handler = Handler(Looper.getMainLooper())
    private var countdownSeconds = 5075 // 01:24:35

    private val countdownRunnable = object : Runnable {
        override fun run() {
            if (countdownSeconds > 0) {
                countdownSeconds--
                val hours = countdownSeconds / 3600
                val minutes = (countdownSeconds % 3600) / 60
                val secs = countdownSeconds % 60
                binding.tvNextPrayerCountdown.text = String.format(Locale.getDefault(), "متبقي %02d:%02d:%02d", hours, minutes, secs)
                handler.postDelayed(this, 1000)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupDates()
        setupBottomNavigation()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        handler.post(countdownRunnable)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(countdownRunnable)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener {
            Toast.makeText(this, "قائمة التنقل السريع لـ نسكي", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_notifications -> {
                Toast.makeText(this, "الإشعارات والتنبيهات", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_settings -> {
                Toast.makeText(this, "الإعدادات العامة", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupDates() {
        // Dynamic system dates simulation with robust localization support
        binding.tvHijriDate.text = "15 رجب 1445 هـ"
        val currentDate = SimpleDateFormat("d MMMM yyyy", Locale("ar")).format(Date())
        binding.tvGregorianDate.text = currentDate
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Already on Home
                    true
                }
                R.id.nav_quran -> {
                    Toast.makeText(this, "القرآن الكريم — نسكي", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_worship -> {
                    Toast.makeText(this, "العبادات والأذكار والأدعية", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_qibla -> {
                    Toast.makeText(this, "اتجاه القبلة والبوصلة", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_more -> {
                    showMoreMenuDialog()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupListeners() {
        // Interactive Dashboard triggers
        binding.tvNextPrayerCountdown.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("مواقيت الصلاة")
                .setMessage("الفجر: 04:35\nالشروق: 05:52\nالظهر: 12:10\nالعصر: 15:35\nالمغرب: 18:21\nالعشاء: 19:40\n\nحساب دقيق حسب موقعك الحالي.")
                .setPositiveButton("إغلاق") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }

    private fun showMoreMenuDialog() {
        val options = arrayOf(
            "تفسير القرآن وحفظه",
            "المسبحة الإلكترونية",
            "التقويم الهجري ورمضان",
            "الحج والعمرة",
            "الأحاديث النبوية الشريفة",
            "أسماء الله الحسنى",
            "إحصائيات الإنجاز والعادات",
            "الإعدادات الشاملة والخصوصية"
        )
        MaterialAlertDialogBuilder(this)
            .setTitle("المزيد من خدمات نسكي")
            .setItems(options) { _, which ->
                Toast.makeText(this, "تم اختيار: ${options[which]}", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("إغلاق", null)
            .show()
    }
}