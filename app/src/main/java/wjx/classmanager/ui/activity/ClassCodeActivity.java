package wjx.classmanager.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import wjx.classlibrary.zxing.EncodingUtils;
import wjx.classmanager.R;

public class ClassCodeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mImageBack;
    private TextView mTextTitle;
    private ImageView mImageCode;

    @Override
    public void initView() {
        mImageBack = (ImageView) findViewById(R.id.back_image);
        mImageCode = (ImageView) findViewById(R.id.class_code);
        mTextTitle = (TextView) findViewById(R.id.back_title);
        mTextTitle.setText("班级二维码");
    }

    @Override
    public void initListener() {
        mImageBack.setOnClickListener(this);
    }

    @Override
    public void initData() {
        String string = getIntent().getStringExtra("");

        Display display = getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels ;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.title_bar_icon);
        mImageCode.setImageBitmap(EncodingUtils.createQRCode("123456", width, width, bitmap));

    }

    @Override
    public int getLayout() {
        return R.layout.activity_class_code;
    }

    @Override
    public boolean isImmersive() {
        return false;
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
