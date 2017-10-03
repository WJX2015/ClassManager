package wjx.classmanager.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by wjx on 2017/8/1.
 */

public class SPUtil {

    private static final String KEY="first";

    /**
     * @param context 获取导航页上下文
     * @return 是否第一次使用应用
     */
    public static boolean getFirstRunTag(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        boolean hasTag = sp.getBoolean(KEY, true);
        return hasTag;
    }

    /**
     * 第一次使用后添加使用标记
     *
     * @param context
     * @param isFirst
     */
    public static void addFirstRunTag(Context context,boolean isFirst) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(KEY, isFirst);
        editor.commit();
    }

    /**
     * 缓存新闻数据
     *
     * @param context
     * @param type
     * @param newsKey
     * @param newsDatas
     */
    public static void cacheNewsDatas(Context context, int type, String newsKey, String newsDatas) {
        SharedPreferences sp = context.getSharedPreferences(type + "", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(newsKey, newsDatas);
        editor.apply();
    }

    /**
     * 获取新闻缓存数据
     *
     * @param context
     * @param type
     * @param newsKey
     * @return
     */
    public static String getNewsDatas(Context context, int type, String newsKey) {
        SharedPreferences sp = context.getSharedPreferences(type + "", Context.MODE_PRIVATE);
        String newsDatas = sp.getString(newsKey, null);
        return newsDatas;
    }

    /**
     * 缓存笑话数据
     *
     * @param context
     * @param jokesKey
     * @param jokesDatas
     */
    public static void cacheJokesDatas(Context context, String jokesKey, String jokesDatas) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.putString(jokesKey, jokesDatas);
        editor.commit();
    }

    /**
     * 获取笑话数据
     *
     * @param context
     * @param jokesKey
     * @return
     */
    public static String getJokesDatas(Context context, String jokesKey) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String str = sp.getString(jokesKey, null);
        return str;
    }
}
