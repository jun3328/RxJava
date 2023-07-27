package ch07.flowcontrol;

import commons.CommonUtils;
import commons.Log;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class ThrottleExample {
    public static void main(String[] args) {

        // throttleFirst() 는 주어진 조건에서 가장 먼저 입력된 값을 발행
        // throttleLast() 는 주어진 조건에서 가장 마지막에 입력된 값을 발행, sample()과 기본 개념은 동일

        Integer[] data = {1, 2, 3, 4, 5, 6};

        CommonUtils.exampleStart();

        // 앞의 1개는 100ms 간격으로 발행
        Observable<Integer> earlySource = Observable.just(data[0])
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);

        // 다음 1개 데이터는 300ms 후에 발행
        Observable<Integer> middleSource = Observable.just(data[1])
                .zipWith(Observable.timer(300L, TimeUnit.MILLISECONDS), (a, b) -> a);

        // 마지막 4개 데이터는 100ms 후에 발행
        Observable<Integer> lateSource = Observable.just(data[2], data[3], data[4], data[5])
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a)
                .doOnNext(Log::dt);

        // 200ms 간격으로 throttleFirst() 실행
        Observable<Integer> source = Observable.concat(earlySource, middleSource, lateSource)
                .throttleFirst(200L, TimeUnit.MILLISECONDS);

        source.subscribe(Log::it);

        CommonUtils.sleep(1000);

    }
}
