package com.vyiot.testhotfix;


import androidx.annotation.NonNull;

import com.vyiot.hotfix.Download;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HotFixDownloadImp implements Download {

    public interface OnFileLoadListener {
        void onSuccess();
        void onFailure();
    }

    private OnFileLoadListener onFileLoadListener;

    public void setOnFileLoadListener(OnFileLoadListener listener) {
        this.onFileLoadListener = listener;
    }

    @Override
    public void net(@NonNull Listener listener) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://192.168.100.67/patch.apk").get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFail();
                e.printStackTrace();
                if (onFileLoadListener != null) {
                    onFileLoadListener.onFailure();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    listener.onSuccess(response.body().bytes());
                    if (onFileLoadListener != null) {
                        onFileLoadListener.onSuccess();
                    }
                } else {
                    listener.onFail();
                    if (onFileLoadListener != null) {
                        onFileLoadListener.onFailure();
                    }
                }
            }
        });
    }
}
