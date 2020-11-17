package com.eggy.consumerapp.ui.settings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.eggy.consumerapp.R
import com.eggy.consumerapp.alarm.AlarmReceiver
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        const val PREFS_NAME = "daily_prefs"
        const val DAILY_REMINDER ="daily_reminder"

    }

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportActionBar?.apply {
            title = "Settings"
            elevation = 0F
            setDisplayHomeAsUpEnabled(true)
        }


        alarmReceiver = AlarmReceiver()
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)


        btn_change.setOnClickListener(this)
        isSwitchChecked()
        sw_daily_reminder.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                alarmReceiver.setDailyReminder(this, AlarmReceiver.DAILY_TYPE, getString(R.string.daily_reminder_message))

            }else{
                alarmReceiver.cancelAlarm(this)
            }
            saveSwitchSetting(isChecked)
        }
    }

    private fun isSwitchChecked(){
        sw_daily_reminder.isChecked = sharedPreferences.getBoolean(DAILY_REMINDER, false)
    }

    private fun saveSwitchSetting(state:Boolean){
        sharedPreferences.edit()
            .putBoolean(DAILY_REMINDER, state)
            .apply()

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }



    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_change -> {
                val langIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(langIntent)
            }
        }
    }
}