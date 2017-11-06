package wjx.classmanager.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import wjx.classmanager.R;

public class EdtPersonActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mImageBack;
    private ImageView mImageFinish;
    private ImageView mImageAdd;
    private TextView mTextTitle;
    private EditText mEditSchool;
    private EditText mEditDepartment;
    private EditText mEditProfess;
    private EditText mEditName;
    private EditText mEditClass;
    private EditText mEditPhone;
    private EditText mEditEmail;

    @Override
    public void initView() {
        mImageBack = (ImageView) findViewById(R.id.back_image);
        mImageFinish = (ImageView) findViewById(R.id.back_add);
        mImageAdd = (ImageView) findViewById(R.id.image_add_icon);
        mTextTitle = (TextView) findViewById(R.id.back_title);
        mEditSchool = (EditText) findViewById(R.id.edt_school);
        mEditDepartment = (EditText) findViewById(R.id.edt_department);
        mEditProfess = (EditText) findViewById(R.id.edt_profess);
        mEditName = (EditText) findViewById(R.id.edt_name);
        mEditClass = (EditText) findViewById(R.id.edt_class);
        mEditPhone = (EditText) findViewById(R.id.edt_phone);
        mEditEmail = (EditText) findViewById(R.id.edt_email);
    }

    @Override
    public void initListener() {
        mImageBack.setOnClickListener(this);
        mImageFinish.setOnClickListener(this);
        mImageAdd.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mTextTitle.setText("个人信息编辑");

        Intent intent = getIntent();
        if (intent != null) {
            mEditSchool.setText(intent.getStringExtra("school"));
            mEditDepartment.setText(intent.getStringExtra("department"));
            mEditProfess.setText(intent.getStringExtra("profess"));
            mEditName.setText(intent.getStringExtra("name"));
            mEditClass.setText(intent.getStringExtra("class"));
            mEditPhone.setText(intent.getStringExtra("phone"));
            mEditEmail.setText(intent.getStringExtra("email"));
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_edt_person;
    }

    @Override
    public boolean isImmersive() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_image:
                finish();
                break;
            case R.id.back_add:
                infoFinishEdit();
                break;
            case R.id.image_add_icon:
                selectIcon();
                break;
        }
    }

    private void selectIcon() {
    }

    private void infoFinishEdit() {
        Intent intent = new Intent();
        intent.putExtra("school", mEditSchool.getText().toString());
        intent.putExtra("department", mEditDepartment.getText().toString());
        intent.putExtra("profess", mEditProfess.getText().toString());
        intent.putExtra("name", mEditName.getText().toString());
        intent.putExtra("class", mEditClass.getText().toString());
        intent.putExtra("phone", mEditPhone.getText().toString());
        intent.putExtra("email", mEditEmail.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
