package wjx.classmanager.ui.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;

import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import wjx.classmanager.R;
import wjx.classmanager.adapter.ClassPhotoAdapter;
import wjx.classmanager.model.ClassPhoto;
import wjx.classmanager.presenter.impl.ClassPhotoPresenterImpl;
import wjx.classmanager.view.ClassPhotoView;

import static android.R.attr.path;
import static wjx.classmanager.model.Constant.MyClass.CLASS_ALBUM;
import static wjx.classmanager.model.Constant.MyClass.CLASS_OPEN_CAMERA;
import static wjx.classmanager.model.Constant.MyClass.OPEN_ALBUM;
import static wjx.classmanager.model.Constant.MyClass.OPEN_CAMERA;
import static wjx.classmanager.model.Constant.MyClass.OPEN_STORAGE;
import static wjx.classmanager.model.Constant.MyClass.PHOTO_SELECT;
import static wjx.classmanager.model.Constant.MyClass.PHOTO_SELECTER;


public class ClassPhotoActivity extends BaseActivity implements View.OnClickListener,ClassPhotoView{

    private Button mTakePhoto;
    private Button mAlbumPhoto;
    private Button mPostPhoto;
    private RecyclerView mRecyclerView;
    private ClassPhotoAdapter mClassPhotoAdapter;
    private ImageView mImageBack;
    private TextView mTextTitle;
    private Uri imageUri;
    private File mOutputImage;
    private ClassPhotoPresenterImpl mClassPhotoPresenter;

    @Override
    public void initView() {
        mClassPhotoPresenter = new ClassPhotoPresenterImpl(this);
        mTakePhoto = (Button) findViewById(R.id.take_photo);
        mAlbumPhoto = (Button) findViewById(R.id.album_photo);
        mPostPhoto = (Button) findViewById(R.id.post_photo);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_photo);
        GridLayoutManager manager = new GridLayoutManager(mContext,3);
        mRecyclerView.setLayoutManager(manager);
        mClassPhotoAdapter = new ClassPhotoAdapter(mClassPhotoPresenter.getPicList());
        mRecyclerView.setAdapter(mClassPhotoAdapter);
        mImageBack = (ImageView) findViewById(R.id.back_image);
        mTextTitle = (TextView) findViewById(R.id.back_title);
    }

    @Override
    public void initListener() {
        mTakePhoto.setOnClickListener(this);
        mAlbumPhoto.setOnClickListener(this);
        mPostPhoto.setOnClickListener(this);
        mImageBack.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mTextTitle.setText("班级相册");
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
                if(hasPermission(Manifest.permission.CAMERA)){
                    openCamera();
                }else{
                    requestPermission(ClassPhotoActivity.this,OPEN_CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
                break;
            case R.id.album_photo:
                if(hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    openAlbum();
                }else{  //权限不允许
                    requestPermission(ClassPhotoActivity.this,OPEN_ALBUM,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
                break;
            case R.id.post_photo:
                if(hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Intent intent = new Intent(ClassPhotoActivity.this,ImageLoaderActivity.class);
                    startActivityForResult(intent,PHOTO_SELECTER);
                }else{  //权限不允许
                    requestPermission(ClassPhotoActivity.this,OPEN_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
                break;
            case R.id.back_image:
                finish();
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
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CLASS_OPEN_CAMERA:
                    mClassPhotoPresenter.handleCameraImage(this,imageUri);
                    break;
                case CLASS_ALBUM:
                    mClassPhotoPresenter.handleAlbumImage(ClassPhotoActivity.this,data);
                    break;
                case PHOTO_SELECTER:
                    mClassPhotoPresenter.handleSelectorImage(data.getStringArrayExtra(PHOTO_SELECT));
                    break;
            }
        }
    }

    @Override
    public void onPicPostSuccess(final String fileUrl) {
        Log.e( "onPicPostSuccess: ",fileUrl+"" );
        post(new Runnable() {
            @Override
            public void run() {
                mClassPhotoAdapter.addPhoto(new ClassPhoto(fileUrl));
            }
        });
        hideProgress();
    }

    @Override
    public void onPicPostFailed(String message) {
        hideProgress();
        Log.e( "onPicPostFailed: ",message );
    }

    @Override
    public void onPicPostSuccess(List<String> urls) {
        hideProgress();
    }

    @Override
    public void onStartLoadPic(String s) {
        showProgress(s);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case OPEN_ALBUM:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    showToast("You denied the permission");
                }
                break;
            case OPEN_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    showToast("You denied the permission");
                }
                break;
            case OPEN_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(ImageLoaderActivity.class);
                } else {
                    showToast("You denied the permission");
                }
                break;
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CLASS_ALBUM);//打开相册
    }

}
