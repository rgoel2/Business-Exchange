package com.androidevelopers.cs5540.businessexchange.firebase.notifications;


        import android.util.Log;

        import com.google.firebase.iid.FirebaseInstanceId;
        import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Rick on 8/8/17.
 */
public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String REG_TOKEN = "REG_TOKEN";

    @Override
    public void onTokenRefresh() {

        String recentToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(REG_TOKEN,recentToken);
    }
}