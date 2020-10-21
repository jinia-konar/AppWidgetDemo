package com.example.appwidgetdemo

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi

/**
 * Implementation of App Widget functionality.
 */
class AppWidget : AppWidgetProvider() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        // String resource for the url
        val url = context.getString(R.string.widgetURL)

        // Construct an Intent object includes that parse the web adresss.
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = Uri.parse(url)

        // In widget we are not allowing to use intents as usually. We have to use PendingIntent instead of 'startActivity'
        val pending = PendingIntent.getActivity(context, 0, intent, 0)

        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.app_widget)
        views.setOnClickPendingIntent(R.id.section_layout, pending)
        views.setTextColor(R.id.sub_text, context.getColor(R.color.colorAccent))
        views.setImageViewResource(R.id.tap_image, R.drawable.tap)

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}