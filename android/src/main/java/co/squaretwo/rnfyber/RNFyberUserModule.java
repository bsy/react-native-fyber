package co.squaretwo.rnfyber;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.fyber.Fyber;
import com.fyber.user.User;

/**
 * Created by Heiko Weber on 17/05/2018.
 */
public class RNFyberUserModule extends ReactContextBaseJavaModule {
    private static final String TAG = "RNFyberUser";

    private ReactApplicationContext mContext;

    public RNFyberUserModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
    }

    @Override
    public String getName() {
        return TAG;
    }

    @ReactMethod
    public void set(final ReadableMap uprops) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (uprops.hasKey("age")) {
                        User.setAge(uprops.getInt("age"));
                    }
                    if (uprops.hasKey("custom")) {
                        ReadableMap custom = uprops.getMap("custom");
                        String[] pubs = { "pub0","pub1","pub2","pub3", "pub4", "pub5", "pub6", "pub7", "pub8", "pub9" };
                        for (String s: pubs) {
                            if (custom.hasKey(s)) {
                                User.addCustomValue(s, custom.getString(s));
                            }
                        }
                    }
                } catch (Exception e) {
                }
            }
        });
    }

    @ReactMethod
    public void startFyberSDK(final String appId, final String securityToken, final String userId) {
        Log.d(TAG, "startFyberSDK for appId:" + appId);
        Fyber.with(appId, getCurrentActivity()).withUserId(userId).withSecurityToken(securityToken).start();
    }
}
