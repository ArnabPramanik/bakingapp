package com.arnab.android.bakingapp.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by arnab on 1/26/18.
 */

public class WidgetIntentService extends IntentService {

    public static final String ACTION_WIDGET = "com.arnab.android.bakingapp.action.update_widget";
    public static final String EXTRA_WIDGET = "com.arnab.android.bakingapp.extra.widget_ingredients";

    public WidgetIntentService() {
        super("com.arnab.android.bakingapp.Widget.WidgetIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent != null) {

            final String action = intent.getAction();
            if (ACTION_WIDGET.equals(action)) {

                String ingredientList = intent.getStringExtra(EXTRA_WIDGET);
                handleActionUpdateWidgetRecipe(ingredientList);
            }
        }
    }

    public static void widgetRefresh(Context context, String ingredientList) {

        Intent intent = new Intent(context, WidgetIntentService.class);
        intent.putExtra(EXTRA_WIDGET, ingredientList);
        intent.setAction(ACTION_WIDGET);
        context.startService(intent);
    }


    private void handleActionUpdateWidgetRecipe(String ingredientList) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidget.class));

        BakingWidget.updateWidgets(this, appWidgetManager, ingredientList, appWidgetIds);
    }
}
