package com.example.linnea.baddruginteractions2;

/**
 * Created by Linnea on 4/4/2018.
 */

import android.app.Activity;
import android.content.Intent;

public class ThemeUtils
{
    private static int sTheme;
    public static int checked = 0;
    public final static int THEME_DEFAULT = 0;
    public final static int THEME_DARK = 1;
    public final static int THEME_BLUE = 2;
    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(Activity activity, int theme)
    {
        if (theme == 1) {checked = 1;}
        else if (theme == 0) {checked = 0;}
        sTheme = theme;
        activity.finish();

        activity.startActivity(new Intent(activity, activity.getClass()));
    }
    /** Set the theme of the activity, according to the configuration. */
    public static void onActivityCreateSetTheme(Activity activity)
    {
        switch (sTheme)
        {
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.AppTheme);
                break;
            case THEME_DARK:
                activity.setTheme(R.style.DarkTheme);
                break;

        }
    }

    public static String getTheme()
    {
        if(sTheme == 1)
        {
            return "dark";
        }
        else
        {
            return "default";
        }

    }
}

