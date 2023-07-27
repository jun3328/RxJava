package ch05.examples;

import commons.CommonUtils;
import commons.Log;
import commons.OkHttpHelper;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

public class CallbackHeaven {

    public static final String GITHUB_ROOT
            = "https://raw.githubusercontent.com/yudong80/reactivejava/master/";

    public static final String FIRST_URL = "https://api.github.com/zen";

    public static final String SECOND_URL = GITHUB_ROOT + "samples/callback_hell";

    private final OkHttpClient client = new OkHttpClient();


    public static void main(String[] args) {
        CommonUtils.exampleStart();

        // usingConcat()

        // 첫 번째 Observable 의 발행이 끝날때 까지 기다려야 함

//        Observable<String> source = Observable.just(FIRST_URL)
//                .subscribeOn(Schedulers.io())
//                .map(OkHttpHelper::get)
//                .concatWith(Observable.just(SECOND_URL)
//                        .map(OkHttpHelper::get));
//
//        source.subscribe(Log::it);

        // usingZip()

        // 동시 수행하고 결합

        Observable<String> first = Observable.just(FIRST_URL)
                .subscribeOn(Schedulers.io())
                .map(OkHttpHelper::get);

        Observable<String> second = Observable.just(SECOND_URL)
                .subscribeOn(Schedulers.io())
                .map(OkHttpHelper::get);

        Observable.zip(first, second, (f, s) -> ("\n>> " + f + "\n>> " + s))
                .subscribe(Log::it);


        CommonUtils.sleep(5000);
    }
}
