package com.android.llc.proringer.pro.proringerpro.pushNotification;

import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;

/**
 * Created by apple on 23/05/17.
 */


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
       // Logger.printMessage("@@ ", "Refreshedtoken: " + refreshedToken);
        Logger.printMessage("token",refreshedToken);

        if (refreshedToken!=null)
        {
            ProConstant.firebasedevice_token=refreshedToken;
        }


        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    private void sendRegistrationToServer(String token) {
        HashMap<String, String> Params = new HashMap<>();
        Params.put("user_id",  ProApplication.getInstance().getUserId());
        Params.put("device_token", token);

        new CustomJSONParser().fireAPIForPostMethod(getApplication(), ProConstant.BASEURL + "users_device_update", Params, null, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
              Logger.printMessage("devicetoken",result);
            }

            @Override
            public void onError(String error, String response) {

            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onStart() {

            }
        });
    }
}