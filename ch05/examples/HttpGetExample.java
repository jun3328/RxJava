package ch05.examples;

import commons.Log;
import okhttp3.*;

import java.io.IOException;

public class HttpGetExample {

    public static final String URL_README
            = "https://raw.githubusercontent.com/yudong80/reactivejava/master/README.md";

    private final OkHttpClient client = new OkHttpClient();

    public void run() {

        Request request =  new Request.Builder()
                .url(URL_README)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(response.body().string());
            }
        });

    }

    public static void main(String[] args) {
        HttpGetExample demo = new HttpGetExample();
        demo.run();
    }
}
