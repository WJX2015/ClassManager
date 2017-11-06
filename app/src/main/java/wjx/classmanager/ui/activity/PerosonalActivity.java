package wjx.classmanager.ui.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import wjx.classmanager.R;

public class PerosonalActivity extends BaseActivity {

    private TextView mTextSchool;
    private TextView mTextDepartment;
    private TextView mTextProfess;
    private TextView mTextName;
    private TextView mTextClass;
    private TextView mTextPhone;
    private TextView mTextEmail;
    private FloatingActionButton mFab;
    private ImageView mImageBack;
    private ImageView mImageAdd;
    private TextView mTextTitle;
    @Override
    public void initView() {
        mTextSchool = (TextView) findViewById(R.id.text_school);
        mTextDepartment = (TextView) findViewById(R.id.text_department);
        mTextProfess = (TextView) findViewById(R.id.text_profess);
        mTextName = (TextView) findViewById(R.id.text_name);
        mTextClass = (TextView) findViewById(R.id.text_class);
        mTextPhone = (TextView) findViewById(R.id.text_phone);
        mTextEmail = (TextView) findViewById(R.id.text_email);
        mFab = (FloatingActionButton) findViewById(R.id.person_fab);
        mImageBack = (ImageView) findViewById(R.id.back_image);
        mImageAdd = (ImageView) findViewById(R.id.back_add);
        mTextTitle = (TextView) findViewById(R.id.back_title);
    }

    @Override
    public void initListener() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerosonalActivity.this,EdtPersonActivity.class);
                intent.putExtra("school",mTextSchool.getText().toString());
                intent.putExtra("department",mTextDepartment.getText().toString());
                intent.putExtra("profess",mTextProfess.getText().toString());
                intent.putExtra("name",mTextName.getText().toString());
                intent.putExtra("class",mTextClass.getText().toString());
                intent.putExtra("phone",mTextPhone.getText().toString());
                intent.putExtra("email",mTextEmail.getText().toString());
                Log.e( "onClick: ", mTextSchool.getText().toString());
                startActivityForResult(intent,2000);
            }
        });

        mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        mTextTitle.setText("我的信息");
        mImageAdd.setVisibility(View.GONE);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_perosonal;
    }

    @Override
    public boolean isImmersive() {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 2000:
                    updateInfo(data);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateInfo(Intent data) {
        mTextSchool.setText(data.getStringExtra("school"));
        mTextDepartment.setText(data.getStringExtra("department"));
        mTextProfess.setText(data.getStringExtra("profess"));
        mTextName.setText(data.getStringExtra("name"));
        mTextClass.setText(data.getStringExtra("class"));
        mTextPhone.setText(data.getStringExtra("phone"));
        mTextEmail.setText(data.getStringExtra("email"));
    }
}
