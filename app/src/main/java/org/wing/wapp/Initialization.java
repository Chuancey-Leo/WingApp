package org.wing.wapp;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liao on 2016/9/19.
 */
public class Initialization extends Application{
    public static final String LIKES = "likes";
    public static final String STATUS_DETAIL = "StatusDetail";
    public static final String DETAIL_ID = "detailId";
    public static final String CREATED_AT = "createdAt";
    public static final String FOLLOWER = "follower";
    public static final String FOLLOWME = "followme";

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化应用信息
        AVOSCloud.initialize(this, "TNdD3NEhBdYbC2oX0QWs15Lr-gzGzoHsz",
                "P7gb6Lzfj9XApgdA9LzNVSTf");
        AVOSCloud.setDebugLogEnabled(true);
        initImageLoader(this);
        // 启用崩溃错误统计
        AVAnalytics.enableCrashReport(this.getApplicationContext(), true);
        AVOSCloud.setLastModifyEnabled(true);
        AVOSCloud.setDebugLogEnabled(true);
    }

    //图片缓存
    public static void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "");
        ImageLoaderConfiguration config = StatusUtils.getImageLoaderConfig(context, cacheDir);
        ImageLoader.getInstance().init(config);
    }

    public static Map<String, AVUser> userCache = new HashMap<>();

    public static void registerUser(AVUser user) {
        userCache.put(user.getObjectId(), user);
    }

    public static void registerBatchUser(List<AVUser> users) {
        for (AVUser user : users) {
            registerUser(user);
        }
    }

    public static AVUser lookupUser(String userId) {
        return userCache.get(userId);
    }
}
