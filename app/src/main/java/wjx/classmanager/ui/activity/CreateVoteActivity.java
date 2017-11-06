package wjx.classmanager.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import wjx.classmanager.R;
import wjx.classmanager.utils.FileUtil;

public class CreateVoteActivity extends BaseActivity implements View.OnClickListener{

    private ImageView mImageBack;
    private TextView mTextTitle;
    private ImageView mImageAdd;
    private EditText mEditName;
    private EditText mEditDesc;
    private EditText mEditPerson;
    private Button mBtnNext;
    private ImageView mImageViewBgAdd;//添加背景图
    private TextView mTextViewBgCount;//背景图的数量
    private String imageUrl;

    @Override
    public void initView() {
        mImageBack = (ImageView) findViewById(R.id.back_image);
        mTextTitle = (TextView) findViewById(R.id.back_title);
        mImageAdd = (ImageView) findViewById(R.id.back_add);
        mEditName = (EditText) findViewById(R.id.vote_name);
        mEditDesc = (EditText) findViewById(R.id.vote_desc);
        mEditPerson = (EditText) findViewById(R.id.vote_person);
        mBtnNext = (Button) findViewById(R.id.vote_next);
        mImageViewBgAdd= (ImageView) findViewById(R.id.btn_add_bg);
        mTextViewBgCount= (TextView) findViewById(R.id.bg_count);
    }

    @Override
    public void initListener() {
        mImageBack.setOnClickListener(this);
        mImageAdd.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
        mImageViewBgAdd.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mTextTitle.setText("活动投票");
    }

    @Override
    public int getLayout() {
        return R.layout.activity_create_vote;
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
            case R.id.vote_next:
                nextAdd();
                break;
            case R.id.btn_add_bg:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
                break;
        }
    }

    private void nextAdd() {
        final String title=mEditName.getText().toString();
        final String author=mEditPerson.getText().toString();
        final String introduce=mEditDesc.getText().toString();

        if (!TextUtils.isEmpty(imageUrl) && !TextUtils.isEmpty(title) && !TextUtils.isEmpty(introduce) && !TextUtils.isEmpty(author)) {
            Intent intent=new Intent(CreateVoteActivity.this,VoteNextActivity.class);
            intent.putExtra("title",title);
            intent.putExtra("author",author);
            intent.putExtra("introduce",introduce);
            intent.putExtra("image",imageUrl);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(mContext, "所有选项都必须完成！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (data != null) {
                Uri uri = data.getData();
                imageUrl = FileUtil.getPath(CreateVoteActivity.this, uri);
                mTextViewBgCount.setText("已添加背景图");
            }
        }
    }
}
