package com.simplemobiletools.clock.fragments

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import com.simplemobiletools.clock.R
import com.simplemobiletools.clock.extensions.config
import com.simplemobiletools.clock.models.MyTimeZone
import com.simplemobiletools.commons.extensions.beVisibleIf
import com.simplemobiletools.commons.extensions.getAdjustedPrimaryColor
import com.simplemobiletools.commons.extensions.underlineText
import com.simplemobiletools.commons.extensions.updateTextColors
import kotlinx.android.synthetic.main.fragment_clock.view.*
import java.util.*

class ClockFragment(context: Context, attributeSet: AttributeSet) : MyViewPagerFragment(context, attributeSet) {
    private val ONE_SECOND = 1000L
    private var passedSeconds = 0
    private var calendar = Calendar.getInstance()

    private val updateHandler = Handler()

    override fun onFinishInflate() {
        super.onFinishInflate()
        val offset = calendar.timeZone.rawOffset
        passedSeconds = ((calendar.timeInMillis + offset) / 1000).toInt()
        updateCurrentTime()
        updateDate()

        time_zones_placeholder_2.apply {
            underlineText()
            setOnClickListener {
                placeholderClicked()
            }
        }

        setupViews()
    }

    private fun setupViews() {
        time_zones_holder.beVisibleIf(context.config.displayOtherTimeZones)
        context.updateTextColors(clock_fragment)
        time_zones_placeholder_2.setTextColor(context.getAdjustedPrimaryColor())
    }

    private fun updateCurrentTime() {
        val hours = (passedSeconds / 3600) % 24
        val minutes = (passedSeconds / 60) % 60
        val seconds = passedSeconds % 60
        var format = "%02d:%02d"

        val formattedText = if (context.config.showSeconds) {
            format += ":%02d"
            String.format(format, hours, minutes, seconds)
        } else {
            String.format(format, hours, minutes)
        }

        clock_time.text = formattedText

        if (hours == 0 && minutes == 0 && seconds == 0) {
            updateDate()
        }

        updateHandler.postDelayed({
            passedSeconds++
            updateCurrentTime()
        }, ONE_SECOND)
    }

    private fun updateDate() {
        calendar = Calendar.getInstance()
        val dayOfWeek = (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7    // make sure index 0 means monday
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)

        val dayString = context.resources.getStringArray(R.array.week_days)[dayOfWeek]
        val shortDayString = dayString.substring(0, Math.min(3, dayString.length))
        val monthString = context.resources.getStringArray(R.array.months)[month]

        val dateString = "$shortDayString, $dayOfMonth $monthString"
        clock_date.text = dateString
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        updateHandler.removeCallbacksAndMessages(null)
    }

    private fun placeholderClicked() {

    }

    override fun onActivityResume() {
        setupViews()
    }

    val timezones = arrayListOf(
            MyTimeZone(1, "GMT-11:00 Midway", "Pacific/Midway"),
            MyTimeZone(2, "GMT-10:00 Honolulu", "Pacific/Honolulu"),
            MyTimeZone(3, "GMT-09:00 Anchorage", "America/Anchorage"),
            MyTimeZone(4, "GMT-08:00 Los Angeles", "America/Los_Angeles"),
            MyTimeZone(5, "GMT-08:00 Tijuana", "America/Tijuana"),
            MyTimeZone(6, "GMT-07:00 Phoenix", "America/Phoenix"),
            MyTimeZone(7, "GMT-07:00 Chihuahua", "America/Chihuahua"),
            MyTimeZone(8, "GMT-07:00 Denver", "America/Denver"),
            MyTimeZone(9, "GMT-06:00 Costa Rica", "America/Costa_Rica"),
            MyTimeZone(10, "GMT-06:00 Chicago", "America/Chicago"),
            MyTimeZone(11, "GMT-06:00 Mexico City", "America/Mexico_City"),
            MyTimeZone(12, "GMT-06:00 Regina", "America/Regina"),
            MyTimeZone(13, "GMT-05:00 Bogota", "America/Bogota"),
            MyTimeZone(14, "GMT-05:00 New York", "America/New_York"),
            MyTimeZone(15, "GMT-04:30 Caracas", "America/Caracas"),
            MyTimeZone(16, "GMT-04:00 Barbados", "America/Barbados"),
            MyTimeZone(17, "GMT-04:00 Halifax", "America/Halifax"),
            MyTimeZone(18, "GMT-04:00 Manaus", "America/Manaus"),
            MyTimeZone(19, "GMT-03:30 St. John's", "America/St_Johns"),
            MyTimeZone(20, "GMT-03:00 Santiago", "America/Santiago"),
            MyTimeZone(21, "GMT-03:00 Recife", "America/Recife"),
            MyTimeZone(22, "GMT-03:00 Sao Paulo", "America/Sao_Paulo"),
            MyTimeZone(23, "GMT-03:00 Buenos Aires", "America/Buenos_Aires"),
            MyTimeZone(24, "GMT-03:00 Nuuk", "America/Godthab"),
            MyTimeZone(25, "GMT-03:00 Montevideo", "America/Montevideo"),
            MyTimeZone(26, "GMT-02:00 South Georgia", "Atlantic/South_Georgia"),
            MyTimeZone(27, "GMT-01:00 Azores", "Atlantic/Azores"),
            MyTimeZone(28, "GMT-01:00 Cape Verde", "Atlantic/Cape_Verde"),
            MyTimeZone(29, "GMT+00:00 Casablanca", "Africa/Casablanca"),
            MyTimeZone(30, "GMT+00:00 Greenwich Mean Time", "Etc/Greenwich"),
            MyTimeZone(31, "GMT+01:00 Amsterdam", "Europe/Amsterdam"),
            MyTimeZone(32, "GMT+01:00 Belgrade", "Europe/Belgrade"),
            MyTimeZone(33, "GMT+01:00 Brussels", "Europe/Brussels"),
            MyTimeZone(34, "GMT+01:00 Madrid", "Europe/Madrid"),
            MyTimeZone(35, "GMT+01:00 Sarajevo", "Europe/Sarajevo"),
            MyTimeZone(36, "GMT+01:00 Brazzaville", "Africa/Brazzaville"),
            MyTimeZone(37, "GMT+02:00 Windhoek", "Africa/Windhoek"),
            MyTimeZone(38, "GMT+02:00 Amman", "Asia/Amman"),
            MyTimeZone(39, "GMT+02:00 Athens", "Europe/Athens"),
            MyTimeZone(40, "GMT+02:00 Istanbul", "Europe/Istanbul"),
            MyTimeZone(41, "GMT+02:00 Beirut", "Asia/Beirut"),
            MyTimeZone(42, "GMT+02:00 Cairo", "Africa/Cairo"),
            MyTimeZone(43, "GMT+02:00 Helsinki", "Europe/Helsinki"),
            MyTimeZone(44, "GMT+02:00 Jerusalem", "Asia/Jerusalem"),
            MyTimeZone(45, "GMT+02:00 Harare", "Africa/Harare"),
            MyTimeZone(46, "GMT+03:00 Minsk", "Europe/Minsk"),
            MyTimeZone(47, "GMT+03:00 Baghdad", "Asia/Baghdad"),
            MyTimeZone(48, "GMT+03:00 Moscow", "Europe/Moscow"),
            MyTimeZone(49, "GMT+03:00 Kuwait", "Asia/Kuwait"),
            MyTimeZone(50, "GMT+03:00 Nairobi", "Africa/Nairobi"),
            MyTimeZone(51, "GMT+03:30 Tehran", "Asia/Tehran"),
            MyTimeZone(52, "GMT+04:00 Baku", "Asia/Baku"),
            MyTimeZone(53, "GMT+04:00 Tbilisi", "Asia/Tbilisi"),
            MyTimeZone(54, "GMT+04:00 Yerevan", "Asia/Yerevan"),
            MyTimeZone(55, "GMT+04:00 Dubai", "Asia/Dubai"),
            MyTimeZone(56, "GMT+04:30 Kabul", "Asia/Kabul"),
            MyTimeZone(57, "GMT+05:00 Karachi", "Asia/Karachi"),
            MyTimeZone(58, "GMT+05:00 Oral", "Asia/Oral"),
            MyTimeZone(59, "GMT+05:00 Yekaterinburg", "Asia/Yekaterinburg"),
            MyTimeZone(60, "GMT+05:30 Kolkata", "Asia/Kolkata"),
            MyTimeZone(61, "GMT+05:30 Colombo", "Asia/Colombo"),
            MyTimeZone(62, "GMT+05:45 Kathmandu", "Asia/Kathmandu"),
            MyTimeZone(63, "GMT+06:00 Almaty", "Asia/Almaty"),
            MyTimeZone(64, "GMT+06:30 Rangoon", "Asia/Rangoon"),
            MyTimeZone(65, "GMT+07:00 Krasnoyarsk", "Asia/Krasnoyarsk"),
            MyTimeZone(66, "GMT+07:00 Bangkok", "Asia/Bangkok"),
            MyTimeZone(67, "GMT+07:00 Jakarta", "Asia/Jakarta"),
            MyTimeZone(68, "GMT+08:00 Shanghai", "Asia/Shanghai"),
            MyTimeZone(69, "GMT+08:00 Hong Kong", "Asia/Hong_Kong"),
            MyTimeZone(70, "GMT+08:00 Irkutsk", "Asia/Irkutsk"),
            MyTimeZone(71, "GMT+08:00 Kuala Lumpur", "Asia/Kuala_Lumpur"),
            MyTimeZone(72, "GMT+08:00 Perth", "Australia/Perth"),
            MyTimeZone(73, "GMT+08:00 Taipei", "Asia/Taipei"),
            MyTimeZone(74, "GMT+09:00 Seoul", "Asia/Seoul"),
            MyTimeZone(75, "GMT+09:00 Tokyo", "Asia/Tokyo"),
            MyTimeZone(76, "GMT+09:00 Yakutsk", "Asia/Yakutsk"),
            MyTimeZone(77, "GMT+09:30 Darwin", "Australia/Darwin"),
            MyTimeZone(78, "GMT+10:00 Brisbane", "Australia/Brisbane"),
            MyTimeZone(79, "GMT+10:00 Vladivostok", "Asia/Vladivostok"),
            MyTimeZone(80, "GMT+10:00 Guam", "Pacific/Guam"),
            MyTimeZone(81, "GMT+10:00 Magadan", "Asia/Magadan"),
            MyTimeZone(82, "GMT+10:30 Adelaide", "Australia/Adelaide"),
            MyTimeZone(83, "GMT+11:00 Hobart", "Australia/Hobart"),
            MyTimeZone(84, "GMT+11:00 Sydney", "Australia/Sydney"),
            MyTimeZone(85, "GMT+11:00 Noumea", "Pacific/Noumea"),
            MyTimeZone(86, "GMT+12:00 Majuro", "Pacific/Majuro"),
            MyTimeZone(87, "GMT+12:00 Fiji", "Pacific/Fiji"),
            MyTimeZone(88, "GMT+13:00 Auckland", "Pacific/Auckland"),
            MyTimeZone(89, "GMT+13:00 Tongatapu", "Pacific/Tongatapu")
    )
}
