package ch05.schedulers;

import commons.CommonUtils;
import commons.Log;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 *  계산 스케줄러는 일반적인 계산작업에 사용
 */

public class ComputationSchedulerExample {
    public static void main(String[] args) {

        Integer[] arr = {1, 3, 5};

        Observable<Integer> source = Observable.fromArray(arr)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);

        // 구독 (Subscription) #1
        source.map(item -> "<<" + item + ">>")
                .subscribeOn(Schedulers.computation())
                .subscribe(Log::i);

        // 구독 (Subscription) #2
        source.map(item -> "##" + item + "##")
                .subscribeOn(Schedulers.computation())
                .subscribe(Log::i);

        CommonUtils.sleep(1000);
    }
}
