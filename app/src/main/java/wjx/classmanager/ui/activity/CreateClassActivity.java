package wjx.classmanager.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import wjx.classlibrary.ease.EaseAlertDialog;
import wjx.classmanager.R;
import wjx.classmanager.presenter.CreateClassPresenter;
import wjx.classmanager.presenter.impl.CreateClassPresenterImpl;
import wjx.classmanager.view.CreateClassView;

import static android.provider.Contacts.GroupMembership.GROUP_ID;
import static com.hyphenate.chat.a.a.a.f;
import static wjx.classmanager.model.Constant.MyClass.CLASS_GROUP_ID;

public class CreateClassActivity extends BaseActivity implements CreateClassView, View.OnClickListener {

    private LinearLayout mBackLinear;
    private LinearLayout mOpenLinear;

    private Button mSaveClass;
    private TextView mSecondDesc;

    private EditText mGroupNameEdit;
    private EditText mGroupIntroductEdit;

    private CheckBox mPublicBox;
    private CheckBox mInviterBox;

    private CreateClassPresenter mCreateClassPresenter;
    protected List<EMGroup> grouplist;

    private static String mGroupId;

    @Override
    public void initView() {
        mBackLinear = (LinearLayout) findViewById(R.id.back);
        mOpenLinear = (LinearLayout) findViewById(R.id.ll_open_invite);

        mSaveClass = (Button) findViewById(R.id.save_class);
        mSecondDesc = (TextView) findViewById(R.id.second_desc);

        mGroupNameEdit = (EditText) findViewById(R.id.edit_group_name);
        mGroupIntroductEdit = (EditText) findViewById(R.id.edit_group_introduction);

        mPublicBox = (CheckBox) findViewById(R.id.cb_public);
        mInviterBox = (CheckBox) findViewById(R.id.cb_member_inviter);

        mCreateClassPresenter = new CreateClassPresenterImpl(this);
    }

    @Override
    public void initListener() {
        mBackLinear.setOnClickListener(this);
        mSaveClass.setOnClickListener(this);

        mPublicBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mSecondDesc.setText("加入班级需要管理员同意");
                } else {
                    mSecondDesc.setText("开放班级成员邀请");
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_create_class;
    }

    @Override
    public void classNameErroe() {
        new EaseAlertDialog(this, "班级名称不能为空！").show();
    }

    @Override
    public void onCreateClass() {
        showProgress("正在创建班级...");
    }

    @Override
    public void onCreateSuccess() {
        hideProgress();
        try {
            grouplist = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
        grouplist = EMClient.getInstance().groupManager().getAllGroups();
        String className=mGroupNameEdit.getText().toString();

        for (EMGroup group:grouplist){
            if (group.getGroupName().equals(className)){
                mGroupId=group.getGroupId();
                startActivity(MyClassActivity.class,CLASS_GROUP_ID,mGroupId);
                finish();
                break;
            }
        }
    }

    public static String getGroupId(){
        return mGroupId;
    }

    @Override
    public void onCreateFailed() {
        hideProgress();
        showToast("创建失败");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.save_class:
                createClass();
                break;
        }
    }

    /**
     * 创建班级
     */
    private void createClass() {
        String className=mGroupNameEdit.getText().toString();
        String classDesc=mGroupIntroductEdit.getText().toString();
        boolean isPublic=mPublicBox.isChecked();
        boolean isMemberInviter=mInviterBox.isChecked();
        mCreateClassPresenter.createClass(className,classDesc,isPublic,isMemberInviter);
    }
}
