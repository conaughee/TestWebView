package flag.bet.betflag.gecv.core;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import flag.bet.betflag.gecv.R;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class FirebaseWrapper {

    private static final String APP_PREFERENCES = "WebViewTestPrefs";
    private static final String APP_PREFERENCES_URL = "PrefUrl";

    private final SharedPreferences sharedPreferences;
    private final FirebaseRemoteConfig firebaseRemoteConfig;

    public FirebaseWrapper(AppCompatActivity activity) {
        sharedPreferences = activity.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        configureFirebase();
    }

    public String getUrl() {
        String url = getLocalUrl();
        if (url.isEmpty()) url = firebaseRemoteConfig.getString("url");
        if (!url.isEmpty()) setLocalUrl(url);
        return url;
    }

    private void setLocalUrl(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(APP_PREFERENCES_URL, url);
        editor.apply();
    }

    private String getLocalUrl() {
        return sharedPreferences.getString(APP_PREFERENCES_URL, "");
    }

    private void configureFirebase(){
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(120)
                .build();
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        firebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
        firebaseRemoteConfig.fetchAndActivate();
    }
}
