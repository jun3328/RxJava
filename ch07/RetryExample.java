package ch07;

import commons.CommonUtils;
import commons.Log;
import commons.OkHttpHelper;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class RetryExample {
    public static void main(String[] args) {
        RetryExample example = new RetryExample();
//        example.retry();
//        example.retryWithDelay();
//        example.retryUntil();
        example.retryWhen();
    }

    private void retry() {
        CommonUtils.exampleStart();

        String url = "https://api.github.com/zen";
        Observable<String> source = Observable.just(url)
                .map(OkHttpHelper::getT)
                .retry(5)
                .onErrorReturn(e -> CommonUtils.ERROR_CODE);

        source.subscribe(data -> Log.it("result : " + data));

        CommonUtils.exampleComplete();
    }

    private void retryWithDelay() {
        final int RETRY_MAX = 5;
        final int RETRY_DELAY = 1000;

        CommonUtils.exampleStart();

        String url = "https://api.github.com/zen";
        Observable<String> source = Observable.just(url)
                .map(OkHttpHelper::getT)
                .retry((retryCnt, e) -> {

                    Log.e("retryCnt = " + retryCnt);
                    CommonUtils.sleep(RETRY_DELAY);

                    return retryCnt < RETRY_MAX ? true : false;
                })
                .onErrorReturn(e -> CommonUtils.ERROR_CODE);

        source.subscribe(data -> Log.it("result : " + data));

        CommonUtils.exampleComplete();
    }

    private void retryUntil() {
        CommonUtils.exampleStart();

        String url = "https://api.github.com/zen";
        Observable<String> source = Observable.just(url)
                .map(OkHttpHelper::getT)
                .subscribeOn(Schedulers.io())
                .retryUntil(() -> {
                    if (CommonUtils.isNetworkAvailable()) return true;
                    CommonUtils.sleep(1000);
                    return false;
                });
        source.subscribe(Log::i);

        CommonUtils.sleep(5000);
        CommonUtils.exampleComplete();
    }

    private void retryWhen() {
        Observable.create((ObservableEmitter<String> emitter) ->{
            System.out.println("subscribing");
            emitter.onError(new RuntimeException("always fails"));
        }).retryWhen(attempts -> {
            return attempts.zipWith(Observable.range(1, 3), (n, i) -> i)
                    .flatMap(i -> {
                        Log.d("delay retry by " + i + " seconds");
                        return Observable.timer(i, TimeUnit.SECONDS);
                    });
        }).blockingForEach(System.out::println);
        CommonUtils.exampleComplete();
    }
}
