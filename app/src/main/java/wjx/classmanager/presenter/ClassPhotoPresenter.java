package wjx.classmanager.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.util.List;

import wjx.classmanager.model.ClassPhoto;
import wjx.classmanager.ui.activity.ClassPhotoActivity;

import static android.R.attr.data;

/**
 * Created by wjx on 2017/10/18.
 */

public interface ClassPhotoPresenter {
    void loadPicFromBmob();
    void handleAlbumImage(Activity activity, Intent data);
    void handleCameraImage(Activity activity, Uri imageUri);

    void handleSelectorImage(String[] stringExtra);
}
