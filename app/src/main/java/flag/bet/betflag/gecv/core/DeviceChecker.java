package flag.bet.betflag.gecv.core;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import androidx.appcompat.app.AppCompatActivity;

public class DeviceChecker {

    private final TelephonyManager manager;

    public DeviceChecker(AppCompatActivity activity) {
        manager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
    }

    public boolean suitableDevice() {
        return !isGoogleDevice() && !isEmulator() && simDevice();
    }

    private boolean isGoogleDevice() {
        String deviceName = android.os.Build.MODEL;
        String deviceMan = android.os.Build.MANUFACTURER;

        return (deviceName + deviceMan).toLowerCase().contains("google");
    }

    private boolean isEmulator() {
        return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("sdk_gphone64_arm64")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator");
    }

    private boolean simDevice() {
        return manager.getSimState() == TelephonyManager.SIM_STATE_READY;
    }
}
