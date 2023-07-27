package ch07.flowcontrol;

import commons.CommonUtils;
import commons.Log;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class WindowExample {
    public static void main(String[] args) {

        // windows() 는 count 를 인자로 받아서
        // count 만큼 데이터가 발행될 때 마다 새로운 Observable 을 생성

        Integer[] data = {1, 2, 3, 4, 5, 6};

        CommonUtils.exampleStart();

        // 앞의 3개는 100ms 간격으로 발행
        Observable<Integer> earlySource = Observable.fromArray(data)
                .take(3)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);

        // 가운데 1개 데이터는 300ms 후에 발행
        Observable<Integer> middleSource = Observable.just(data[3])
                .zipWith(Observable.timer(300L, TimeUnit.MILLISECONDS), (a, b) -> a);

        // 마지막 2개 데이터는 100ms 후에 발행
        Observable<Integer> lateSource = Observable.just(data[4], data[5])
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);

        // 데이터 3개씩 모아서 새로운 Observable 생성
        Observable<Observable<Integer>> source = Observable.concat(earlySource, middleSource, lateSource)
                .window(3);

        source.subscribe(observable -> {
            Log.dt("New Observable Started!!");
            observable.subscribe(Log::it);
        });

        CommonUtils.sleep(1000);
        CommonUtils.exampleComplete();
    }
}
