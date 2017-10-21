package wjx.classmanager.presenter.impl;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;
import wjx.classmanager.model.ClassPhoto;
import wjx.classmanager.presenter.ClassPhotoPresenter;
import wjx.classmanager.utils.LogUtil;
import wjx.classmanager.view.ClassPhotoView;

import static android.R.attr.path;

/**
 * Created by wjx on 2017/10/18.
 */

public class ClassPhotoPresenterImpl implements ClassPhotoPresenter {

    private ClassPhotoView mClassPhotoView;
    private List<ClassPhoto> mClassPhotos = new ArrayList<>();

    public ClassPhotoPresenterImpl(ClassPhotoView classPhotoView){
        mClassPhotoView=classPhotoView;
        initData();
    }

    private void initData() {
        for(int i=0;i<5;i++){
            String url="/storage/emulated/0/BigNoxHD/cache/com_bignox_app_store_hd.png";
            mClassPhotos.add(new ClassPhoto(url));
        }
    }

    /**
     * 上传单一图片
     * @param path
     */
    private void postPicToBmob(String path) {
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    mClassPhotoView.onPicPostSuccess(bmobFile.getFileUrl());
                }else{
                    mClassPhotoView.onPicPostFailed(e.getMessage());
                }
            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });
    }

    /**
     * 批量上传图片
     * @param filePaths
     */
    private void postPicToBmob(String[] filePaths){
        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {
                Log.e( "onSuccess: ","图片上传成功" +files.size()+"_+_+"+urls.size());
                mClassPhotoView.onPicPostSuccess(urls);
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                Log.e( "onProgress: ",totalPercent+"" );
            }

            @Override
            public void onError(int stateCode, String errormsg) {
                Log.e( "onError: ", errormsg);
            }
        });
    }

    @Override
    public void loadPicFromBmob() {
        mClassPhotoView.onStartLoadPic("正在获取图片...");
    }

    @Override
    public void handleAlbumImage(Activity activity, Intent data) {
        mClassPhotoView.onStartLoadPic("正在上传图片...");
        if (Build.VERSION.SDK_INT >= 19) {
            //4.4以上系统使用这个方法处理图片
            String str=handleImageOnKitKat(activity,data);
            postPicToBmob(handleImageOnKitKat(activity,data));
            Log.e( "handleAlbumImage: ",str+"-=-" );
        } else {
            //4.4以下系统使用这个方法处理图片
            String str=handleImageBeforeKitKat(activity,data);
            postPicToBmob(handleImageBeforeKitKat(activity,data));
            Log.e( "handleAlbumImage: ",str+"-=-" );
        }
    }

    @Override
    public void handleCameraImage(Activity activity, Uri imageUri) {
        mClassPhotoView.onStartLoadPic("正在上传图片...");
        postPicToBmob(getCameraImagePath(activity,imageUri));
        String str =getCameraImagePath(activity,imageUri);
        Log.e( "handleCameraImage: ",str+"-=-=" );
    }

    @Override
    public void handleSelectorImage(String[] stringExtra) {
        mClassPhotoView.onStartLoadPic("正在上传图片...");
        postPicToBmob(stringExtra);
    }

    @Override
    public void downloadPicFromBmob(File name,String fileUrl) {
        BmobFile bmobfile =new BmobFile("download.png","",fileUrl);
        bmobfile.download(new DownloadFileListener() {
            @Override
            public void done(String path, BmobException e) {
                if(e==null){
                    mClassPhotoView.onDownPicSuccess(path);
                    Log.e("done: ",path+"-=-=" );
                }else{
                    mClassPhotoView.onDownPicFailed(e.getMessage());
                    Log.e( "done: ",e.getErrorCode()+"-=-="+e.getMessage() );
                }

            }

            @Override
            public void onProgress(Integer integer, long l) {

            }
        });
    }

    public List<ClassPhoto> getPicList(){
        return mClassPhotos;
    }

    /**
     * 4.4以上系统使用这个方法处理图片
     * @param activity
     * @param data
     */
    private String handleImageOnKitKat(Activity activity,Intent data) {
        String imagepath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(activity, uri)) {
            //如果是documents类型的Uri,则通过Document id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的ID
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagepath = getAlbumImagePath(activity,MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagepath = getAlbumImagePath(activity,contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的Uri,则使用普通方式处理
            imagepath = getAlbumImagePath(activity,uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的Uri,直接获取图片路径即可
            imagepath = uri.getPath();
        }
        return imagepath;
    }

    /**
     * 4.4以下系统使用这个方法处理图片
     * @param data
     */
    private String handleImageBeforeKitKat(Activity activity,Intent data) {
        Uri uri = data.getData();
        String imagePath = getAlbumImagePath(activity,uri, null);
        return imagePath;
    }

    /**
     * 获取相册的图片路径
     * @param activity
     * @param uri
     * @param selection
     * @return
     */
    private String getAlbumImagePath(Activity activity,Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = activity.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 获取相机拍照的图片路径
     * @param context
     * @param uri
     * @return
     */
    private String getCameraImagePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
