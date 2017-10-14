package wjx.classmanager.listener;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * 作者：国富小哥
 * 日期：2017/10/13
 * Created by Administrator
 * 首先要计算软键盘高度h，
 * 然后通过软键盘高度h使整个布局上移h，
 */

public class SoftkeyListener {

    private View rootView;//activity的根视图  
    int rootViewVisibleHeight;//纪录根视图的显示高度  
    private OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener;

    public SoftkeyListener(Activity activity) {
        //获取activity的根视图
        rootView = activity.getWindow().getDecorView();

        //监听视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                //获取当前视图在屏幕上显示的大小
                Rect rect = new Rect();
                rootView.getWindowVisibleDisplayFrame(rect);
                int visibleHeight = rect.height();

                if (rootViewVisibleHeight == 0) {
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }

                //根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变  
                if (rootViewVisibleHeight == visibleHeight) {
                    return;
                }

                //根视图显示高度变小超过200，可以看作软键盘显示了  
                if (rootViewVisibleHeight - visibleHeight > 200) {
                    if (onSoftKeyBoardChangeListener != null) {
                        onSoftKeyBoardChangeListener.keyBoardShow(rootViewVisibleHeight - visibleHeight);

                    }

                    rootViewVisibleHeight = visibleHeight;
                    return;
                }

                //根视图显示高度变大超过200，可以看作软键盘隐藏了 
                if (visibleHeight - rootViewVisibleHeight > 200) {
                    if (onSoftKeyBoardChangeListener != null) {
                        onSoftKeyBoardChangeListener.keyBoardHide(visibleHeight - rootViewVisibleHeight);

                    }

                    rootViewVisibleHeight = visibleHeight;
                    return;
                }
            }
        });
    }

    private void setOnSoftKeyBoardChangeListener(OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
        this.onSoftKeyBoardChangeListener = onSoftKeyBoardChangeListener;
    }

    public static void setListener(Activity activity, OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
        SoftkeyListener softkeyListener = new SoftkeyListener(activity);
        softkeyListener.setOnSoftKeyBoardChangeListener(onSoftKeyBoardChangeListener);
    }


    public interface OnSoftKeyBoardChangeListener {
        void keyBoardShow(int height);
        void keyBoardHide(int height);
    }
}
