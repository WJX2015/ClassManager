package wjx.classmanager.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import wjx.classmanager.R;
import wjx.classmanager.utils.FileUtil;

public class CreateAppraseActivity extends BaseActivity {

    private EditText mEditTextTitle;//评优标题
    private ImageView mImageViewBgAdd;//添加背景图
    private TextView mTextViewBgCount;//背景图的数量
    private EditText mEditTextAuthor;//评优的发布人
    private EditText mEditTextIntroduce;//评优说明
    private Button mButtonNext;//下一步
    private String imageUrl;

    @Override
    public void initView() {
        mEditTextIntroduce= (EditText) findViewById(R.id.eva_desc);
        mEditTextTitle= (EditText) findViewById(R.id.eva_name);
        mEditTextAuthor= (EditText) findViewById(R.id.eva_person);
        mImageViewBgAdd= (ImageView) findViewById(R.id.btn_add_bg);
        mTextViewBgCount= (TextView) findViewById(R.id.bg_count);
        mButtonNext= (Button) findViewById(R.id.eva_next);

    }

    @Override
    public void initListener() {

        //选择图片
        mImageViewBgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });


        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String title=mEditTextTitle.getText().toString();
                final String author=mEditTextAuthor.getText().toString();
                final String introduce=mEditTextIntroduce.getText().toString();

                if (!TextUtils.isEmpty(imageUrl) && !TextUtils.isEmpty(title) && !TextUtils.isEmpty(introduce) && !TextUtils.isEmpty(author)) {
                    Intent intent=new Intent(CreateAppraseActivity.this,AppraseDetailActivity.class);
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
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_create_apprase;
    }

    @Override
    public boolean isImmersive() {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (data != null) {
                Uri uri = data.getData();
                imageUrl = FileUtil.getPath(CreateAppraseActivity.this, uri);
                mTextViewBgCount.setText("已添加背景图");
            }
        }
    }
}
