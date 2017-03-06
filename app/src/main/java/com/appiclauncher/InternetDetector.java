package com.appiclauncher;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

public class InternetDetector {

    private static Context context;
    static ConnectivityManager cm;

    // return network state
    public static boolean detectInternet(Context ctx) {
        context = ctx;
        cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > 22)
            return detectApiG22();
        else
            return detectApiL22();
    }

    // G for greater than
    // L for less than

    private static boolean detectApiG22() {
        cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting())
            return true;
        return false;
    }

    @SuppressWarnings("deprecation")
    private static boolean detectApiL22() {
        NetworkInfo[] info = cm.getAllNetworkInfo();
        if (info != null)
            for (int i = 0; i < info.length; ++i)
                if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    return true;
        return false;
    }


//    @SuppressLint("NewApi")
//    private static boolean detectApiG22() {
//        Network[] networks = cm.getAllNetworks();
//        if (networks != null) {
//            for (int i = 0; i < networks.length; ++i) {
//                Network network = networks[i];
//                NetworkInfo info = cm.getNetworkInfo(network);
//                if (info.getState() == NetworkInfo.State.CONNECTED)
//                    return true;
//            }
//        }
//        return false;
//    }
}
