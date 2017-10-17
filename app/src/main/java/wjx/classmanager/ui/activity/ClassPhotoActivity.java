package wjx.classmanager.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import wjx.classmanager.R;


public class ClassPhotoActivity extends BaseActivity implements View.OnClickListener{

    private Button mTakePhoto;
    private Button mPostPhoto;
    private RecyclerView mRecyclerView;

    @Override
    public void initView() {
        mTakePhoto = (Button) findViewById(R.id.take_photo);
        mPostPhoto = (Button) findViewById(R.id.post_photo);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_photo);
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
                break;
            case R.id.post_photo:
                startActivity(ImageLoaderActivity.class);
                break;
        }
    }
}
