package com.clm.getallappspermissions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Intent mainIntent;
    List pkgAppsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter intentFilter =new IntentFilter("android.intent.action.PACKAGE_INSTALL");
        applicationInstallReceiver receiver = new applicationInstallReceiver();
        registerReceiver(receiver,intentFilter);

        IntentFilter intentFilter2 =new IntentFilter("android.intent.action.BATTERY_CHANGED");
        registerReceiver(receiver,intentFilter2);



        mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        pkgAppsList = getPackageManager().queryIntentActivities(mainIntent, 0);

        for (Object obj : pkgAppsList) {
            ResolveInfo resolveInfo = (ResolveInfo) obj;
            PackageInfo packageInfo = null;
            try {
                packageInfo = getPackageManager().getPackageInfo(resolveInfo.activityInfo.packageName, PackageManager.GET_PERMISSIONS);
            } catch (PackageManager.NameNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            String[] requestedPermissions = packageInfo.requestedPermissions;
        }
    }

    public class applicationInstallReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("*onReceive",intent.getAction());
            Toast.makeText(context,""+intent.getAction(),Toast.LENGTH_SHORT);
        }
    }
    

}
