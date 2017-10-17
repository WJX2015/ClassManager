package wjx.classmanager.utils;

import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * 作者：国富小哥
 * 日期：2017/10/16
 * Created by Administrator
 */

public class FileUtil {

    /**
     * android获取一个用于打开Word文件的intent  
     * @param localFile
     * @return
     */
    public static Intent getWordFileIntent(File localFile){
        Intent intent=new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri=Uri.fromFile(localFile);
        intent.setDataAndType(uri,"application/msword");
        return intent;
    }

    /**
     * android获取一个用于打开Excel文件的intent  
     * @param localFile
     * @return
     */
    public static Intent getExcelFileIntent(File localFile){
        Intent intent=new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri=Uri.fromFile(localFile);
        intent.setDataAndType(uri,"application/vnd.ms-excel");
        return intent;
    }


    /**
     * android获取一个用于打开PPT文件的intent  
     * @param localFile
     * @return
     */
    public static Intent getPPTFileIntent(File localFile){
        Intent intent=new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri=Uri.fromFile(localFile);
        intent.setDataAndType(uri,"application/vnd.ms-powerpoint");
        return intent;
    }


    /**
     * android获取一个用于打开apk文件的intent  
     * @param localFile
     * @return
     */
    public static Intent getApkFileIntent(File localFile){
        Intent intent=new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri=Uri.fromFile(localFile);
        intent.setDataAndType(uri,"application/vnd.android.packge-archive");
        return intent;
    }


    /**
     * android获取一个用于打开CHM文件的intent  
     * @param localFile
     * @return
     */
    public static Intent getChmFileIntent(File localFile){
        Intent intent=new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri=Uri.fromFile(localFile);
        intent.setDataAndType(uri,"application/x-chm");
        return intent;
    }


    /**
     * android获取一个用于打开视频文件的intent  
     * @param localFile
     * @return
     */
    public static Intent getVideoFileIntent(File localFile){
        Intent intent=new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot",0);
        intent.putExtra("configchange",0);
        Uri uri=Uri.fromFile(localFile);
        intent.setDataAndType(uri,"video/*");
        return intent;
    }


    /**
     * android获取一个用于打开音频文件的intent  
     * @param localFile
     * @return
     */
    public static Intent getAudioFileIntent(File localFile){
        Intent intent=new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot",0);
        intent.putExtra("configchange",0);
        Uri uri=Uri.fromFile(localFile);
        intent.setDataAndType(uri,"audio/*");
        return intent;
    }


    /**
     * android获取一个用于打开文本文件的intent  
     * @param localFile
     * @return
     */
    public static Intent getTextFileIntent(File localFile){
        Intent intent=new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri=Uri.fromFile(localFile);
        intent.setDataAndType(uri,"text/plain");
        return intent;
    }


    /**
     * android获取一个用于打开PDF文件的intent  
     * @param localFile
     * @return
     */
    public static Intent getPdfFileIntent(File localFile){
        Intent intent=new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri=Uri.fromFile(localFile);
        intent.setDataAndType(uri,"application/pdf");
        return intent;
    }


    /**
     * android获取一个用于打开图片文件的intent  
     * @param localFile
     * @return
     */
    public static Intent getImageFileIntent(File localFile){
        Intent intent=new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri=Uri.fromFile(localFile);
        intent.setDataAndType(uri,"image/*");
        return intent;
    }


    /**
     * android获取一个用于打开Html文件的intent
     * @param localFile
     * @return
     */
    public static Intent getHtmlFileIntent(File localFile){

        Uri uri=Uri.parse(localFile.toString()).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(localFile.toString()).build();
        Intent intent=new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri,"text/html");
        return intent;
    }
}
