package This.is.TwoSeaweed.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.logging.Logger;

import This.is.TwoSeaweed.R;

public class SettingActivity extends AppCompatActivity {
    AlertDialog.Builder builder;
    LinearLayout LL,DEV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        LL = findViewById(R.id.setting_version);
        DEV =findViewById(R.id.setting_developer);

        builder = new AlertDialog.Builder(this);


        LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("앱 버전");
                String version = getVersionInfo(SettingActivity.this);
                builder.setMessage(version);
                builder.show();
            }
        });
        DEV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("개발자");
                String del = "이김임";
                builder.setMessage(del);
                builder.show();
            }
        });

    }
    public String getVersionInfo(Context context){
        String version = "Unknown";
        PackageInfo packageInfo;

        if (context == null) {
            return version;
        }
        try {
            packageInfo = context.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(context.getApplicationContext().getPackageName(), 0 );
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("asd", "getVersionInfo :" + e.getMessage());
        }
        return version;
    }
}
