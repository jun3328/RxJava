package ch07.flowcontrol;

import commons.CommonUtils;
import commons.Log;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class SampleExample {
    public static void main(String[] args) {

        // sample() 은 특정한 시간 동안 가장 최근에 발행된 데이터만 걸러준다.
        // 아무리 많은 데이터가 들어오도 해당 구간의 마지막 데이터만 발행하고 나머지는 무시

        Integer[] data = {1, 7, 2, 3, 6};

        CommonUtils.exampleStart();

        // 앞의 4개 데이터는 100ms 간격으로 발행
        Observable<Integer> earlySource = Observable.fromArray(data)
                .take(4)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);

        // 마지막 데이터는 300ms 후에 발행
        Observable<Integer> lateSource = Observable.just(data[4])
                .zipWith(Observable.timer(300L, TimeUnit.MILLISECONDS), (a, b) -> a);

        // 2개의 Observable 을 결합하고 300ms로 샘플링

        // sample() 의 실행이 끝나지 않았는데 Observable 이 종료되는 경우에
        // 마지막 인자를 발행하려면 emitLast 인자를 true 로
        Observable<Integer> source = Observable.concat(earlySource, lateSource)
                .sample(300L, TimeUnit.MILLISECONDS);

        source.subscribe(Log::it);

        CommonUtils.sleep(1000);
    }
}
