package ch04.conditional;

import commons.CommonUtils;
import commons.Log;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

/**
 *  skipUntil() 은 인자로 받은 Observable 에서 어떤 값을 발행하는 순간부터
 *  원래 Observable 에서 값을 정상적으로 발행
 */

public class SkipUntilExample {
    public static void main(String[] args) {
        String[] data = {"1", "2", "3", "4", "5", "6"};

        CommonUtils.exampleStart();

        Observable<String> source = Observable.fromArray(data)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (val, notUsed) -> val)
                .skipUntil(Observable.timer(500L, TimeUnit.MILLISECONDS));

        source.subscribe(Log::i);

        CommonUtils.sleep(1000);
    }
}
