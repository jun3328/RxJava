package ch04.create;

import commons.CommonUtils;
import commons.Log;
import commons.OkHttpHelper;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class HeartBeat {
    public static void main(String[] args) {

        CommonUtils.exampleStart();

        String serverUrl = "https://api.github.com/zen";

        Observable.timer(2, TimeUnit.SECONDS)
                .map(val -> serverUrl)
                .map(OkHttpHelper::get)
                .repeat()
                .subscribe(res -> Log.it("Ping Result : " + res));

        CommonUtils.sleep(10000);

    }
}
