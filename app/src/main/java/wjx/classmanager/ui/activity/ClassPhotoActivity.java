package wjx.classmanager.ui.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import wjx.classmanager.R;
import wjx.classmanager.presenter.ClassPhotoPresenter;
import wjx.classmanager.presenter.impl.ClassPhotoPresenterImpl;
import wjx.classmanager.view.ClassPhotoView;

import static wjx.classmanager.R.string.picture;
import static wjx.classmanager.model.Constant.MyClass.CLASS_ALBUM;
import static wjx.classmanager.model.Constant.MyClass.CLASS_OPEN_CAMERA;


public class ClassPhotoActivity extends BaseActivity implements View.OnClickListener,ClassPhotoView{

    private Button mTakePhoto;
    private Button mPostPhoto;
    private RecyclerView mRecyclerView;

    private Uri imageUri;
    private File mOutputImage;

    private ClassPhotoPresenter mClassPhotoPresenter;

    @Override
    public void initView() {
        mTakePhoto = (Button) findViewById(R.id.take_photo);
        mPostPhoto = (Button) findViewById(R.id.post_photo);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_photo);
        mClassPhotoPresenter = new ClassPhotoPresenterImpl(this);
    }

    @Override
    public void initListener() {
        mTakePhoto.setOnClickListener(this);
        mPostPhoto.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_class_photo;
    }

    @Override
    public boolean isImmersive() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.take_photo:
                openCamera();
                break;
            case R.id.post_photo:
                startActivity(ImageLoaderActivity.class);
                break;
        }
    }

    private void openCamera() {
        //创建File对象，用于存储拍照后的图片
        mOutputImage = new File(getExternalCacheDir(), "output_image.jpg");
        try {
            if (mOutputImage.exists()) {
                mOutputImage.delete();
            }
            mOutputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(ClassPhotoActivity.this, "com.example.cameraalbumtest.fileprovider", mOutputImage);
        } else {
            imageUri = Uri.fromFile(mOutputImage);
        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CLASS_OPEN_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            switch (requestCode) {
                case CLASS_OPEN_CAMERA:
                    String path =getRealFilePath(mContext,imageUri);
                    mClassPhotoPresenter.postPicToBmob(path);
                    break;
                case CLASS_ALBUM:
                    //判断手机系统版本号
//                    if (Build.VERSION.SDK_INT >= 19) {
//                        //4.4以上系统使用这个方法处理图片
//                        handleImageOnKitKat(data);
//                    } else {
//                        //4.4以下系统使用这个方法处理图片
//                        handleImageBeforeKitKat(data);
//                    }
                    break;
            }
        }
    }

    public static String getRealFilePath(final Context context, final Uri uri ) {
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

    @Override
    public void onPicPostSuccess(String fileUrl) {
        Log.e( "onPicPostSuccess: ",fileUrl+"" );
    }

    @Override
    public void onPicPostFailed(String message) {
        Log.e( "onPicPostFailed: ",message );
    }
}
