package com.example.linnea.baddruginteractions2;

/**
 * Created by Linnea on 4/4/2018.
 */

import android.app.Activity;
import android.content.Intent;

public class ThemeUtils
{
    public static int sTheme;
    public static int checked = 0;
    public static String fontSize = "med";
    public final static int THEME_DEFAULT = 0;
    public final static int THEME_DARK = 1;
    public final static int THEME_SMALL = 2;
    public final static int THEME_MED = 3;
    public final static int THEME_LARGE = 4;
    public final static int THEME_DARK_SMALL = 5;
    public final static int THEME_DARK_MED = 6;
    public final static int THEME_DARK_LARGE = 7;
    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(Activity activity, int theme)
    {
        switch(theme)
        {
            case 0:
                checked = 0;
                fontSize = "med";
                break;
            case 1:
                checked = 1;
                fontSize = "med";
                break;
            case 2:
                fontSize = "small";
                checked = 0;
                break;
            case 3:
                fontSize = "med";
                checked = 0;
                break;
            case 4:
                fontSize = "large";
                checked = 0;
                break;
            case 5:
                fontSize = "small";
                checked = 1;
                break;
            case 6:
                fontSize = "med";
                checked = 1;
                break;
            case 7:
                fontSize = "large";
                checked = 1;
                break;
        }
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
            case THEME_SMALL:
                activity.setTheme(R.style.smallFontTheme);
                break;
            case THEME_MED:
                activity.setTheme(R.style.AppTheme);
                break;
            case THEME_LARGE:
                activity.setTheme(R.style.bigFontTheme);
                break;
            case THEME_DARK_SMALL:
                activity.setTheme(R.style.DarkThemeSmall);
                break;
            case THEME_DARK_MED:
                activity.setTheme(R.style.DarkTheme);
                break;
            case THEME_DARK_LARGE:
                activity.setTheme(R.style.DarkThemeBig);
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

