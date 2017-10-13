package wjx.classmanager.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMucSharedFile;
import com.hyphenate.chat.EMPushConfigs;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import wjx.classlibrary.ease.EaseExpandGridView;
import wjx.classmanager.R;
import wjx.classmanager.listener.EaseGroupListener;
import wjx.classmanager.presenter.MyclassPresenter;
import wjx.classmanager.presenter.impl.MyclassPresenterImpl;
import wjx.classmanager.utils.EaseUserUtils;
import wjx.classmanager.utils.ThreadUtil;

import static wjx.classmanager.model.Constant.MyClass.CLASS_GROUP_ID;


public class MyClassActivity extends BaseActivity implements View.OnClickListener{

    private final String TAG="MyClassActivity";
    private LinearLayout mLinearLayout_back;
    private TextView mTextView_class_name;
    private ProgressBar mProgressBar;
    private EaseExpandGridView mEaseExpandGridView_admin;
    private RelativeLayout mRelativeLayout_class_file;
    private RelativeLayout mRelativeLayout_class_notification;
    private RelativeLayout mRelativeLayout_class_grade;
    private RelativeLayout mRelativeLayout_class_photo;
    private RelativeLayout mRelativeLayout_class_code;
    private RelativeLayout mRelativeLayout_change_class_name;
    private RelativeLayout mRelativeLayout_change_class_description;
    private Button mButton_exit_class;
    private Button mButton_exitdel_class;

    private String groupId;
    private EMGroup group;
    private String operationUserId = "";
    private EMPushConfigs pushConfigs;

    GroupChangeListener groupChangeListener;
    private AdminAdapter mAdminAdapter;
    private MyclassPresenter mMyclassPresenter;
    //管理员列表
    private List<String> adminList = Collections.synchronizedList(new ArrayList<String>());

    private void init() {
        groupId = getIntent().getStringExtra(CLASS_GROUP_ID);
        group = EMClient.getInstance().groupManager().getGroup(groupId);

        if(group == null){
            finish();
            return;
        }
    }

    @Override
    public void initView() {
        init();
        mMyclassPresenter=new MyclassPresenterImpl();

        mLinearLayout_back= (LinearLayout) findViewById(R.id.ll_back);
        mTextView_class_name= (TextView) findViewById(R.id.class_name);
        mProgressBar= (ProgressBar) findViewById(R.id.progressBar);
        mEaseExpandGridView_admin= (EaseExpandGridView) findViewById(R.id.owner_and_administrators_grid_view);
        mRelativeLayout_class_file= (RelativeLayout) findViewById(R.id.rl_class_file);
        mRelativeLayout_class_code= (RelativeLayout) findViewById(R.id.rl_class_code);
        mRelativeLayout_class_grade= (RelativeLayout) findViewById(R.id.rl_class_grade);
        mRelativeLayout_class_notification= (RelativeLayout) findViewById(R.id.layout_class_notification);
        mRelativeLayout_class_photo= (RelativeLayout) findViewById(R.id.rl_class_photo);
        mRelativeLayout_change_class_name= (RelativeLayout) findViewById(R.id.rl_change_class_name);
        mRelativeLayout_change_class_description= (RelativeLayout) findViewById(R.id.rl_change_class_description);
        mButton_exit_class= (Button) findViewById(R.id.btn_exit_class);
        mButton_exitdel_class= (Button) findViewById(R.id.btn_exitdel_class);


        //当前用户不是管理员
        if (group.getOwner() == null || "".equals(group.getOwner())
                || !group.getOwner().equals(EMClient.getInstance().getCurrentUser())) {
            mButton_exit_class.setVisibility(View.GONE);
            mButton_exitdel_class.setVisibility(View.GONE);
        }
        //当前用户是管理员
        if (EMClient.getInstance().getCurrentUser().equals(group.getOwner())) {
            mButton_exit_class.setVisibility(View.GONE);
            mButton_exitdel_class.setVisibility(View.VISIBLE);
        }

        //获取pushConfigs
        pushConfigs = EMClient.getInstance().pushManager().getPushConfigs();

        //监听群组变化
        groupChangeListener = new GroupChangeListener();
        EMClient.getInstance().groupManager().addGroupChangeListener(groupChangeListener);

        //班级名称
        mTextView_class_name.setText(group.getGroupName() + "(" + group.getMemberCount() + ">人)");


        mAdminAdapter = new AdminAdapter(this, R.layout.em_grid_owner, new ArrayList<String>());
        mEaseExpandGridView_admin.setAdapter(mAdminAdapter);

        // 保证每次进详情看到的都是最新的group
        updateGroup();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finish();
                break;
        }
    }


    private class AdminAdapter extends ArrayAdapter<String> {
        private int res;


        public AdminAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
            super(context, resource, objects);

            res=resource;

        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder holder=null;
            if (convertView==null){
                holder=new ViewHolder();
                convertView= LayoutInflater.from(getContext()).inflate(res,null);
                holder.imageView= (ImageView) convertView.findViewById(R.id.iv_avatar);
                holder.textView= (TextView) convertView.findViewById(R.id.tv_name);
                holder.badgeDeleteView= (ImageView) convertView.findViewById(R.id.badge_delete);
                convertView.setTag(holder);

            }else {
                holder= (ViewHolder) convertView.getTag();

            }

            final LinearLayout button= (LinearLayout) convertView.findViewById(R.id.button_avatar);

            //添加的按钮
            if (position==getCount()-1){
                holder.textView.setText("");
                holder.imageView.setImageResource(R.drawable.em_smiley_add_btn);

                if (isCurrentOwner(group)){
                    //只有创建人才能添加管理员
                    convertView.setVisibility(View.VISIBLE);
                    //添加管理员按钮的监听事件
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 跳转到班级成员页面选人并返回结果
//                            startActivityForResult(
//                                    (new Intent(GroupDetailsActivity.this, GroupPickContactsActivity.class).putExtra("groupId", groupId)),
//                                    REQUEST_CODE_ADD_USER);
                        }
                    });
                }else {
                    convertView.setVisibility(View.INVISIBLE);
                }
                return  convertView;
            }else {
                //管理员的按钮
                final String userName=getItem(position);
                //绑定用户昵称
                EaseUserUtils.setUserNick(userName,holder.textView);
                //绑定用户头像
                EaseUserUtils.setUserAvatar(getContext(),userName,holder.imageView);
                //区分班级的创建人和管理员
                LinearLayout id_background = (LinearLayout) convertView.findViewById(R.id.l_bg_id);
                id_background.setBackgroundColor(convertView.getResources().getColor(
                        position == 0 ? R.color.holo_red_light :
                                R.color.holo_orange_light));

                //给管理员的图标设置点击事件
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isCurrentOwner(group)){
                            //如果当前用户不是群主
                            return;
                        }
                        if (userName.equals(group.getOwner())){
                            //如果当前点中的用户是群主
                            return;
                        }
                        operationUserId=userName;
                    }
                });
            }
            return convertView;
        }





        /**
         * 判断当前用户是否群主
         * @param group
         * @return
         */
        boolean isCurrentOwner(EMGroup group) {
            String owner = group.getOwner();
            if (owner == null || owner.isEmpty()) {
                return false;
            }
            return owner.equals(EMClient.getInstance().getCurrentUser());
        }


    }

    private static class ViewHolder{
        ImageView imageView;
        TextView textView;
        ImageView badgeDeleteView;
    }


    class MemberMenuDialog extends Dialog {

        private MemberMenuDialog(@NonNull Context context) {
            super(context);

            init();
        }

        void init() {
            final MemberMenuDialog dialog = this;
            dialog.setTitle("class");
            dialog.setContentView(R.layout.em_chatroom_member_menu);

            int ids[] = {
                    R.id.menu_item_rm_admin,
                    R.id.menu_item_transfer_owner,
            };

            for (int id : ids) {
                LinearLayout linearLayout = (LinearLayout)dialog.findViewById(id);
                linearLayout.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(final View v) {
                        dialog.dismiss();
                        mProgressBar.setVisibility(View.VISIBLE);

                        ThreadUtil.runOnBackgroundThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    switch (v.getId()) {
                                        case R.id.menu_item_rm_admin:
                                            EMClient.getInstance().groupManager().removeGroupAdmin(groupId, operationUserId);
                                            break;
                                        case R.id.menu_item_transfer_owner:
                                            EMClient.getInstance().groupManager().changeOwner(groupId, operationUserId);
                                            break;
                                        default:
                                            break;
                                    }
                                    updateGroup();
                                } catch (final HyphenateException e) {
                                    ThreadUtil.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MyClassActivity.this, e.getDescription(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
//
                                    e.printStackTrace();

                                } finally {
                                    ThreadUtil.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mProgressBar.setVisibility(View.INVISIBLE);
                                        }
                                    });
                                }
                            }
                        });

                    }
                });
            }
        }

        void setVisibility(boolean[] visibilities) throws Exception {
            if (ids.length != visibilities.length) {
                throw new Exception("");
            }

            for (int i = 0; i < ids.length; i++) {
                View view = this.findViewById(ids[i]);
                view.setVisibility(visibilities[i] ? View.VISIBLE : View.GONE);
            }
        }

        int[] ids = {
                R.id.menu_item_transfer_owner,
                R.id.menu_item_rm_admin,
        };
    }

    private void updateGroup() {

        ThreadUtil.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (pushConfigs==null){
                        EMClient.getInstance().pushManager().getPushConfigsFromServer();
                    }

                    try {
                        group=EMClient.getInstance().groupManager().getGroupFromServer(groupId);

                        adminList.clear();
                        adminList.addAll(group.getAdminList());

                    }catch (Exception e) {
                        //e.printStackTrace();  // User may have no permission for fetch mute, fetch black list operation
                    } finally {
//                        memberList.remove(group.getOwner());
//                        memberList.removeAll(adminList);
                    }

                    ThreadUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //重新刷新详情页面
                            refreshOwnerAdminAdapter();
                            mTextView_class_name.setText(group.getGroupName() + "(" + group.getMemberCount()
                                    + ")");
                            mProgressBar.setVisibility(View.INVISIBLE);

                            if (EMClient.getInstance().getCurrentUser().equals(group.getOwner())) {
                                // 显示解散按钮
                                mButton_exit_class.setVisibility(View.GONE);
                                mButton_exitdel_class.setVisibility(View.VISIBLE);
                            } else {
                                // 显示退出按钮
                                mButton_exit_class.setVisibility(View.VISIBLE);
                                mButton_exitdel_class.setVisibility(View.GONE);
                            }

                            boolean isOwner = isCurrentOwner(group);
                            mButton_exit_class.setVisibility(isOwner ? View.GONE : View.VISIBLE);
                            mButton_exitdel_class.setVisibility(isOwner ? View.VISIBLE : View.GONE);
                        }
                    });

                }catch (Exception e) {
                    ThreadUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setVisibility(View.INVISIBLE);
                        }
                    });

                }

            }
        });
    }

    /**
     * 判断是否班级创建者
     * @param group
     * @return
     */
    boolean isCurrentOwner(EMGroup group) {
        String owner = group.getOwner();
        if (owner == null || owner.isEmpty()) {
            return false;
        }
        return owner.equals(EMClient.getInstance().getCurrentUser());
    }

    private void refreshOwnerAdminAdapter() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdminAdapter.clear();
                mAdminAdapter.add(group.getOwner());
                synchronized (adminList) {
                    mAdminAdapter.addAll(adminList);
                }
                mAdminAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void initListener() {
        mLinearLayout_back.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_class;
    }


    /**
     * 群组事件监听
     */
    private class GroupChangeListener extends EaseGroupListener {

        //接收到群组加入邀请
        @Override
        public void onInvitationAccepted(String groupId, String inviter, String reason) {
//            runOnUiThread(new Runnable(){
//
//                @Override
//                public void run() {
//                    memberList = group.getMembers();
//                    memberList.remove(group.getOwner());
//                    memberList.removeAll(adminList);
//                    memberList.removeAll(muteList);
//                    refreshMembersAdapter();
//                }
//            });
        }

        @Override
        public void onUserRemoved(String groupId, String groupName) {
            finish();
        }

        @Override
        public void onGroupDestroyed(String groupId, String groupName) {
            finish();
        }


        @Override  //添加成员到禁言列表的通知
        public void onMuteListAdded(String groupId, final List<String> mutes, final long muteExpire) {
            updateGroup();
        }


        @Override  // 成员从禁言列表里移除通知
        public void onMuteListRemoved(String groupId, final List<String> mutes) {
            updateGroup();
        }


        @Override  //增加管理员的通知
        public void onAdminAdded(String groupId, String administrator) {
            updateGroup();
        }


        @Override //管理员移除的通知
        public void onAdminRemoved(String groupId, String administrator) {
            updateGroup();
        }


        @Override //群所有者变动通知
        public void onOwnerChanged(String groupId, String newOwner, String oldOwner) {


            ThreadUtil.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MyClassActivity.this, "onOwnerChanged", Toast.LENGTH_SHORT).show();
                }
            });
            updateGroup();
        }


        @Override  //群组加入新成员通知
        public void onMemberJoined(String groupId, String member) {
            EMLog.d(TAG, "onMemberJoined");
            updateGroup();
        }


        @Override  //群成员退出通知
        public void onMemberExited(String groupId, String member) {
            EMLog.d(TAG, "onMemberExited");
            updateGroup();
        }


        @Override  //群公告变动通知
        public void onAnnouncementChanged(String groupId, final String announcement) {
//            if(groupId.equals(MyClassActivity.this.groupId)) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                });
//            }
        }

        @Override  //增加共享文件的通知
        public void onSharedFileAdded(String groupId, final EMMucSharedFile sharedFile) {
            if(groupId.equals(MyClassActivity.this.groupId)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyClassActivity.this, "Group added a share file", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        @Override  //群共享文件删除通知
        public void onSharedFileDeleted(String groupId, String fileId) {
            if(groupId.equals(MyClassActivity.this.groupId)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyClassActivity.this, "Group deleted a share file", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}