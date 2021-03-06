package wjx.classmanager.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import wjx.classmanager.R;

public class EvaNextActivity extends BaseActivity implements View.OnClickListener{

    private ImageView mImageBack;
    private TextView mTextTitle;
    private ImageView mImageAdd;
    private EditText mEditName;
    private EditText mEditDesc;
    private EditText mEditPerson;
    private Button mNextAdd;

    @Override
    public void initView() {
        mImageBack = (ImageView) findViewById(R.id.back_image);
        mTextTitle = (TextView) findViewById(R.id.back_title);
        mImageAdd = (ImageView) findViewById(R.id.back_add);
        mEditName = (EditText) findViewById(R.id.next_name);
        mEditDesc = (EditText) findViewById(R.id.next_desc);
        mEditPerson = (EditText) findViewById(R.id.next_person);
        mNextAdd = (Button) findViewById(R.id.next_next);
    }

    @Override
    public void initListener() {
        mImageBack.setOnClickListener(this);
        mImageAdd.setOnClickListener(this);
        mNextAdd.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mTextTitle.setText("评优竞选人");
    }

    @Override
    public int getLayout() {
        return R.layout.activity_eva_next;
    }

    @Override
    public boolean isImmersive() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_image:
                finish();
                break;
            case R.id.back_add:
                break;
            case R.id.next_next:
                break;
        }
    }
}
