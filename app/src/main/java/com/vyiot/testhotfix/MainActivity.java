package com.vyiot.testhotfix;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.vyiot.hotfix.HotFixManager;
import com.vyiot.hotfix.utils.PathUtil;

public class MainActivity extends AppCompatActivity {
    private HotFixDownloadImp download;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        download = new HotFixDownloadImp();

        download.setOnFileLoadListener(new HotFixDownloadImp.OnFileLoadListener() {
            @Override
            public void onSuccess() {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "下载补丁成功", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onFailure() {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "下载补丁失败", Toast.LENGTH_SHORT).show());


            }
        });

        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(v -> {
            downLoadPatch();
        });


        Button btnKill = findViewById(R.id.btn_kill);
        btnKill.setOnClickListener(v -> {
            killApp();
        });

        Button btnShow = findViewById(R.id.btn_show);
        btnShow.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "hello world!!!", Toast.LENGTH_SHORT).show();
        });

        Button md5 = findViewById(R.id.btn_md5);
        md5.setOnClickListener(v -> {
            String m = PathUtil.getPatchApkMd5(getApplicationContext());
            Log.e("wnwnwang", "patch file md5 = " + m);

            if (!TextUtils.isEmpty(m)) {
                Toast.makeText(MainActivity.this, m, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void downLoadPatch() {
        String value = "123_27e6bd540b257319baf9fa5eddcf685b";
        HotFixManager.getInstance().downloadPatch(download, value);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("wnwnwang", "HotFixActivity onDestroy");
    }

    private void killApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}