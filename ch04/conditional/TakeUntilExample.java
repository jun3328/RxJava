package ch04.conditional;

import commons.CommonUtils;
import commons.Log;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

/**
 *  takeUntil() 은 인자로 받은 Observable 에서 어떤 값을 발행하면
 *  현재 Observable 의 데이터 발행을 중단하고 즉시 완료(onComplete 이벤트 발생)
 */

public class TakeUntilExample {
    public static void main(String[] args) {

        String[] data = {"1", "2", "3", "4", "5", "6"};

        CommonUtils.exampleStart();

        Observable<String> source = Observable.fromArray(data)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (val, notUsed) -> val)
                .takeUntil(Observable.timer(500L, TimeUnit.MILLISECONDS));

        source.subscribe(Log::i);

        CommonUtils.sleep(1000);

    }
}
