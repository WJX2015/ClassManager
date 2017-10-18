package wjx.classmanager.utils;

import android.content.Context;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import wjx.classmanager.application.MyApplication;
import wjx.classmanager.model.BmobClass;
import wjx.classmanager.model.Constant;

import static com.hyphenate.chat.a.a.a.e;

/**
 * 作者：国富小哥
 * 日期：2017/10/14
 * Created by Administrator
 */

public class GroupUtil {

    private static GroupUtil sInstance;
    private Context mContext= MyApplication.getMyContext();
    private List<EMGroup> mEmGroup;
    private String mGroupId="";
    private String mObjectId="";

    public static GroupUtil getInstance(){
        if(sInstance==null){
            synchronized (GroupUtil.class){
                if(sInstance==null){
                    sInstance = new GroupUtil();
                }
            }
        }
        return sInstance;
    }

    public void searchGroupIdFromBmob(String objectId){
        BmobQuery<BmobClass> bmobQuery = new BmobQuery<BmobClass>();
        bmobQuery.getObject(objectId, new QueryListener<BmobClass>() {
            @Override
            public void done(BmobClass bmobClass, BmobException e) {
                if(e==null){
                    mGroupId=bmobClass.getGroupid();
                    SPUtil.huanXinGroupId(mContext,mGroupId);
                    Log.e( "done: ","Bmob查询数据成功" );
                }else {
                    Log.e( "done: ","Bmob查询数据失败" );
                }
            }
        });
    }

    public void searchGroupIdFromHuanXin(){
        ThreadUtil.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {

                try {  //先获取群组列表
                    mEmGroup=EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                    ThreadUtil.sleep(3000);
                    mGroupId=mEmGroup.get(0).getGroupId();
                    SPUtil.huanXinGroupId(mContext,mGroupId);
                    Log.e( "run: ","环信查找群组ID成功" );
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.e( "run: ","环信查找群组ID失败" );
                }
            }
        });
    }

    /**
     *
     * @param column 列名
     * @param value 列值
     */
    public void searchObjectIdFromBmob(String column,String value){
        BmobQuery<BmobClass> query = new BmobQuery<BmobClass>();
        query.addWhereEqualTo(column,value);
        query.setLimit(10);
        query.findObjects(new FindListener<BmobClass>() {
            @Override
            public void done(List<BmobClass> object, BmobException e) {
                if(e==null){
                    mObjectId =object.get(0).getObjectId();
                    SPUtil.bmobObjectId(mContext,mObjectId);
                    Log.e( "done: ","Bmob查找objectId成功" );
                }else{
                    Log.e( "done: ","Bmob查找objectId失败" +e.toString()+e.getErrorCode());
                }
            }
        });
    }
}
