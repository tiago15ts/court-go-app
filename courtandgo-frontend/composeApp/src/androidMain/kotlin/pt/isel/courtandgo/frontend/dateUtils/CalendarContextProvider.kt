package pt.isel.courtandgo.frontend.dateUtils

import android.content.Context

@Suppress("StaticFieldLeak")
lateinit var appCalendarContext: Context

fun initCalendarContext(context: Context) {
    appCalendarContext = context.applicationContext
}
