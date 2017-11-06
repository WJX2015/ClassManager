package wjx.classmanager.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import wjx.classmanager.R;
import wjx.classmanager.model.Vote;
import wjx.classmanager.model.VoteResult;
import wjx.classmanager.utils.FileUtil;

public class VoteNextActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mImageBack;
    private TextView mTextTitle;
    private ImageView mImageAdd;
    private EditText mEditName;
    private EditText mEditDesc;
    private EditText mEditPerson;
    private TextView mTextViewHeaderCount;
    private ImageView mImageViewAddHeader;//添加头像
    private Button mNextAdd;
    private String iconUri;
    private ProgressDialog savingPd;
    private ProgressDialog finishingPd;

    //评优集
    private String title;
    private String author;
    private String imageUrl;
    private String introduce;

    @Override
    public void initView() {
        mImageBack = (ImageView) findViewById(R.id.back_image);
        mTextTitle = (TextView) findViewById(R.id.back_title);
        mImageAdd = (ImageView) findViewById(R.id.back_add);
        mEditName = (EditText) findViewById(R.id.next_name);
        mEditDesc = (EditText) findViewById(R.id.next_desc);
        mEditPerson = (EditText) findViewById(R.id.next_person);
        mTextViewHeaderCount= (TextView) findViewById(R.id.header_count);
        mImageViewAddHeader= (ImageView) findViewById(R.id.btn_add_header);
        mNextAdd = (Button) findViewById(R.id.next_next);
    }

    @Override
    public void initListener() {
        mImageBack.setOnClickListener(this);
        mImageAdd.setOnClickListener(this);
        mNextAdd.setOnClickListener(this);
        mImageViewAddHeader.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mTextTitle.setText("活动投票人");
    }

    @Override
    public int getLayout() {
        return R.layout.activity_vote_next;
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
                finishSave();
                break;
            case R.id.next_next:
                nextAdd();
                break;
            case R.id.btn_add_header:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
                break;
        }
    }

    private void nextAdd() {
        savingPd=new ProgressDialog(VoteNextActivity.this);
        savingPd.setMessage("saving...");
        savingPd.setCanceledOnTouchOutside(false);
        savingPd.show();

        final String name=mEditName.getText().toString();
        final String motto=mEditPerson.getText().toString();
        final String desc =mEditDesc.getText().toString();
        if (!TextUtils.isEmpty(iconUri) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(motto) && !TextUtils.isEmpty(desc)) {

            final BmobFile bmobFile = new BmobFile(new File(iconUri));

            bmobFile.upload(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e==null){
                        //上传头像成功
                        Vote vote=new Vote();
                        vote.setName(name);
                        vote.setDesc(desc);
                        vote.setTitle(title);
                        vote.setMotto(motto);
                        vote.setAvatar(bmobFile.getFileUrl());
                        vote.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e==null){
                                    //保存成功
                                    savingPd.dismiss();
                                    showToast("保存成功，添加下一个");
                                    Intent intent=new Intent(VoteNextActivity.this,VoteNextActivity.class);
                                    intent.putExtra("title",title);
                                    intent.putExtra("author",author);
                                    intent.putExtra("introduce",introduce);
                                    intent.putExtra("image",imageUrl);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    //保存失败
                                    savingPd.dismiss();
                                    showToast("保存失败");
                                }

                            }
                        });
                    }else {
                        savingPd.dismiss();
                        //上传头像失败
                        showToast("");
                    }


                }
            });
        }else {
            savingPd.dismiss();
            showToast("所有选项必须都填写！");
        }
    }

    private void getIntentData() {
        Intent intent=getIntent();
        title=intent.getStringExtra("title");
        author=intent.getStringExtra("author");
        imageUrl=intent.getStringExtra("image");
        introduce=intent.getStringExtra("introduce");
    }

    /**
     * 添加评选人完成
     */
    private void finishSave() {

        finishingPd=new ProgressDialog(this);
        finishingPd.setMessage("waiting...");
        finishingPd.setCanceledOnTouchOutside(false);
        finishingPd.show();

        final String name=mEditName.getText().toString();
        final String motto=mEditPerson.getText().toString();
        final String desc =mEditDesc.getText().toString();

        if (!TextUtils.isEmpty(iconUri) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(motto) && !TextUtils.isEmpty(desc) ) {

            final BmobFile bmobFile2 = new BmobFile(new File(iconUri));
            bmobFile2.upload(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        //上传图片成功
                        Vote vote2=new Vote();
                        vote2.setName(name);
                        vote2.setDesc(desc);
                        vote2.setTitle(title);
                        vote2.setMotto(motto);
                        vote2.setAvatar(bmobFile2.getFileUrl());

                        vote2.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    execureCreateOperate();
                                } else {
                                    showToast("保存失败");
                                    finishingPd.dismiss();

                                }
                            }
                        });
                    } else {
                        //上传图片失败
                        Log.e("AAA", e.getMessage());
                    }
                }
            });
        }else {
            savingPd.dismiss();
            showToast("所有选项必须都填写！");
        }
    }

    /**
     * 执行创建操作
     * */
    private void execureCreateOperate() {

        // BmobUser bmobUser = BmobUser.getCurrentUser();
        SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
        Date curDate=new Date(System.currentTimeMillis());
        final String time=format.format(curDate);
        final BmobFile bmobFile2 = new BmobFile(new File(imageUrl));

        //先上传评优活动的背景图
        bmobFile2.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    //背景图上传成功
                    VoteResult voteResult=new VoteResult();
                    voteResult.setNameForCreate(author);
                    voteResult.setTimeForCreate(time);
                    voteResult.setIntroduction(introduce);
                    voteResult.setAppraiseTitle(title);
                    voteResult.setBackgroundImage(bmobFile2.getFileUrl());

                    voteResult.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e==null){
                                //保存成功
                                savingPd.dismiss();
                                showToast("创建成功");
                                finish();
                            }else {
                                //保存失败
                                savingPd.dismiss();
                                showToast("创建失败");
                            }
                        }
                    });
                }else {
                    //背景图上传失败
                    Log.e("AAA", e.getMessage());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (data != null) {
                Uri uri = data.getData();
                //返回头像时，头像数量显示1
                mTextViewHeaderCount.setText("已添加头像");
                iconUri = FileUtil.getPath(VoteNextActivity.this, uri);

            }
        }
    }
}
