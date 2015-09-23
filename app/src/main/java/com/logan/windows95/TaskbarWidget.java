package com.logan.windows95;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RemoteViews;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link TaskbarWidgetConfigureActivity TaskbarWidgetConfigureActivity}
 */
public class TaskbarWidget extends AppWidgetProvider {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        initViews(context);
    }

    private void initViews(Context context) {
        RemoteViews views = null;
            Intent layoutIntent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
            Bundle layoutBundle = new Bundle();

            // I put in the layoutId of the next layout. Note that the layoutId is not
            // from R. I just made up some easy to remember number for my layoutId
            layoutBundle.putInt("layout_id", 1);

            PendingIntent lPendingIntent = PendingIntent.getBroadcast(context, 0,
                    layoutIntent, PendingIntent.FLAG_ONE_SHOT);


            // Set the layout to the first layout
            //views = new RemoteViews(context.getPackageName(), R.layout.layout_zero);


            // I used buttons to trigger a layout change
            //views.setOnClickPendingIntent(R.id.btnNext, lPendingIntent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningTaskInfo> recentTasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        Log.d("TEST", "Can you read this?");

        for (int i = 0; i < recentTasks.size(); i++)
        {
            Log.d("Executed app", "Application executed : " + recentTasks.get(i).baseActivity.toShortString() + "\t\t ID: " + recentTasks.get(i).id + "");
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            TaskbarWidgetConfigureActivity.deleteTitlePref(context, appWidgetIds[i]);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = TaskbarWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.taskbar_widget);
        //views.addView(R.id.time, null);
        //views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}

