package co.squaretwo.rnfyber;

import android.os.Handler;
import android.os.Looper;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.devsupport.JSException;
import com.fyber.user.User;

import org.json.JSONException;

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
}
