package co.squaretwo.rnfyber;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.support.annotation.Nullable;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.fyber.Fyber;
import com.fyber.ads.AdFormat;
import com.fyber.requesters.OfferWallRequester;
import com.fyber.requesters.RequestCallback;
import com.fyber.requesters.RequestError;
import com.facebook.react.bridge.Callback;

/**
 * Created by benyee on 16/01/2016.
 */
public class RNFyberOfferWallModule extends ReactContextBaseJavaModule {
    private static final String TAG = "RNFyberOfferWall";
    private static final int OFFER_WALL_REQUEST = 1;

    private RequestCallback requestCallback;
    private ReactApplicationContext mContext;
    private Intent mOfferWallIntent;
    private Callback requestAdCallback;

    private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {

        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
            // handle the closing of the video
            if (resultCode == Activity.RESULT_OK && requestCode == OFFER_WALL_REQUEST) {

                // OfferWall Closed
                Log.d(TAG, "The offerwall is closed");
                sendEvent("offerWallClosed", null);
            }
        }
    };

    public RNFyberOfferWallModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
        // Add the listener for `onActivityResult`
        reactContext.addActivityEventListener(mActivityEventListener);
    }

    @Override
    public String getName() {
        return TAG;
    }

    @ReactMethod
    public void initializeOfferWall(final String appId, final String securityToken, final String userId) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Settings appId:" + appId);
                Fyber.Settings settings = Fyber.with(appId, getCurrentActivity()).withUserId(userId).withSecurityToken(securityToken).start();
                requestOfferWall(new Callback() {
                    public void invoke(Object... args) {}
                });
            }
        });
    }

    @ReactMethod
    public void requestOfferWall(final Callback callback) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, ">> Requesting OfferWall");
                requestCallback = new RequestCallback() {
                    @Override
                    public void onRequestError(RequestError requestError) {
                        Log.d(TAG, "Something went wrong with the request: " + requestError.getDescription());
                        sendEvent("offerWallFailedToLoad", null);
                    }

                    @Override
                    public void onAdAvailable(Intent intent) {
                        Log.d(TAG, "Offers are available");
                        mOfferWallIntent = intent;
                        sendEvent("offerWallAvailable", null);
                    }

                    @Override
                    public void onAdNotAvailable(AdFormat adFormat) {
                        Log.d(TAG, "No ad available");
                        sendEvent("offerWallUnavailable", null);
                        callback.invoke("OfferWall is not ready.");
                    }
                };
                OfferWallRequester.create(requestCallback).request(mContext);
            }
        });
    }

    private void sendEvent(String eventName, @Nullable WritableMap params) {
        getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }

    @ReactMethod
    public void showOfferWall() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
              Log.d(TAG, "showOfferWall started");
              sendEvent("offerWallDidStart", null);
              mContext.startActivityForResult(mOfferWallIntent, OFFER_WALL_REQUEST, null);
            }
        });
    }
}
