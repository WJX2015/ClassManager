package wjx.classmanager.ui.activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadBatchListener;
import wjx.classmanager.R;
import wjx.classmanager.adapter.ImageAdapter;
import wjx.classmanager.model.FolderBean;
import wjx.classmanager.presenter.impl.ImageLoaderPresenterImpl;
import wjx.classmanager.view.ImageLoaderView;
import wjx.classmanager.widget.ImageDirPopupWindow;

import static android.R.attr.path;
import static com.hyphenate.chat.a.a.a.i;
import static wjx.classmanager.adapter.ImageAdapter.getSelectedImage;
import static wjx.classmanager.model.Constant.MyClass.CLASS_EDIT;
import static wjx.classmanager.model.Constant.MyClass.PHOTO_SELECT;

public class ImageLoaderActivity extends BaseActivity implements View.OnClickListener,ImageLoaderView{

    private GridView mGridView;
    private ImageAdapter mImageAdapter;
    private RelativeLayout mBottomLayout;

    private List<String> mImages;
    private List<FolderBean> mFolderBeans = new ArrayList<>();

    private TextView mDirName;
    private TextView mDirCount;

    private File mCurrentDir;
    private int mMaxCount;

    private ProgressDialog mProgressDialog;
    private ImageDirPopupWindow mImageDirPopupWindow;
    private ImageLoaderPresenterImpl mImageLoaderPresenter;

    private TextView mTextTitle;
    private ImageView mImageBack;
    private ImageView mImageAdd;

    @Override
    public void initView() {
        mImageLoaderPresenter = new ImageLoaderPresenterImpl(this);
        mGridView = (GridView) findViewById(R.id.grid_view);
        mBottomLayout = (RelativeLayout) findViewById(R.id.bottom_layout);
        mDirName = (TextView) findViewById(R.id.dir_name);
        mDirCount = (TextView) findViewById(R.id.dir_count);

        mTextTitle = (TextView) findViewById(R.id.back_title);
        mImageBack = (ImageView) findViewById(R.id.back_image);
        mImageAdd = (ImageView) findViewById(R.id.back_add);
        mTextTitle.setText("图片选择器");
    }

    @Override
    public void initListener() {
        mImageBack.setOnClickListener(this);
        mImageAdd.setOnClickListener(this);
        mBottomLayout.setOnClickListener(this);
    }

    @Override
    public void initData() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            showToast("当前存储卡不能用");
            return;
        }
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver contentResolver = ImageLoaderActivity.this.getContentResolver();
                Cursor cursor = contentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED);

                Set<String> mDirPaths = new HashSet<String>();

                while (cursor.moveToNext()) {
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null)
                        continue;

                    String dirPath = parentFile.getAbsolutePath();
                    FolderBean folderBean = null;

                    //避免重复扫描
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                        folderBean = new FolderBean();
                        folderBean.setDir(dirPath);
                        folderBean.setFirstPath(path);
                    }

                    if (parentFile.list() == null)
                        continue;

                    //得到图片的数量
                    int picSize = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                            if (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png"))
                                return true;
                            return false;
                        }
                    }).length;

                    folderBean.setCount(picSize);
                    mFolderBeans.add(folderBean);

                    if (picSize > mMaxCount) {
                        mMaxCount = picSize;
                        mCurrentDir = parentFile;
                    }
                }
                cursor.close();

                //图片扫描完成，发送通知
                mMyHandler.sendEmptyMessage(1001);
            }
        }).start();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_image_loader;
    }

    @Override
    public boolean isImmersive() {
        return false;
    }

    private Handler mMyHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mProgressDialog.dismiss();
            //绑定数据到View中
            date2View();
            //有数据后可以初始化
            initDirPopupWindow();
        }
    };

    private void date2View() {
        if (mCurrentDir == null) {
            showToast("未扫描到任何图片");
            return;
        }
        mImages = Arrays.asList(mCurrentDir.list());
        mImageAdapter = new ImageAdapter(this, mImages, mCurrentDir.getAbsolutePath());
        mGridView.setAdapter(mImageAdapter);

        mDirCount.setText(mMaxCount + "");
        mDirName.setText(mCurrentDir.getName());
    }

    private void initDirPopupWindow() {
        mImageDirPopupWindow = new ImageDirPopupWindow(this, mFolderBeans);
        mImageDirPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        mImageDirPopupWindow.setOnDirSelectedListener(new ImageDirPopupWindow.DirSelectedListener() {
            @Override
            public void onSelected(FolderBean folderBean) {
                mCurrentDir = new File(folderBean.getDir());

                mImages = Arrays.asList(mCurrentDir.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        if (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png"))
                            return true;
                        return false;
                    }
                }));

                mImageAdapter = new ImageAdapter(ImageLoaderActivity.this,mImages,mCurrentDir.getAbsolutePath());
                mGridView.setAdapter(mImageAdapter);

                mDirCount.setText(mImages.size()+"");
                mDirName.setText(folderBean.getName());

                mImageDirPopupWindow.dismiss();
            }
        });
    }

    private void lightOn() {    //内容区域变亮
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        getWindow().setAttributes(lp);
    }

    private void lightOff() {    //内容区域变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.8f;
        getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_image:
                finish();
                break;
            case R.id.back_add:
                String[] strings =mImageAdapter.getSelectedImage().toArray(new String[mImageAdapter.getSelectedImage().size()]);
                setResult(RESULT_OK,new Intent().putExtra(PHOTO_SELECT,strings));
                finish();
                break;
            case R.id.bottom_layout:
                mImageDirPopupWindow.setAnimationStyle(R.style.PopupWindowAnim);
                mImageDirPopupWindow.showAsDropDown(mBottomLayout, 0, 0);
                lightOff();
                break;
        }
    }

    private String[] getPostPicUrl(String[] imageUrls) {
        for(int i=0;i<imageUrls.length;i++){
            if(checkImageUrlSuffix(imageUrls[i])){  //有后缀名
                Log.e( "getPostPicUrl: ","图片路径有后缀" );
            }else{ //无后缀名
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(imageUrls[i], options);
                String type = options.outMimeType;
                Log.e("image type -> ", type);

                if (TextUtils.isEmpty(type)) {
                    type = "未能识别的图片";
                } else {
                    type = type.substring(6, type.length());
                    imageUrls[i]=addImageSuffix(imageUrls[i],type);
                }
            }
        }
        return imageUrls;
    }

    /**
     * 添加图片后缀名
     * @param url
     * @param suffix
     * @return
     */
    private String addImageSuffix(String url,String suffix){
        Log.e( "addImageSuffix: ", url+"."+suffix);
        return url+"."+suffix;
    }

    /**
     * 检查图片路径是否有后缀名
     * @param path
     * @return
     */
    private boolean checkImageUrlSuffix(String path){
        return path.contains(".");
    }

    private void postPicToBmob(String[] filePaths){
        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {
                Log.e( "onSuccess: ","图片上传成功" +files.size()+"_+_+"+urls.size());
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

}
