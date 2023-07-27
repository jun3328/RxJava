package ch04.etc;

import commons.CommonUtils;
import commons.Log;
import io.reactivex.Observable;
import io.reactivex.schedulers.Timed;

/**
 *  timeInterval() 은 어떤 값을 발행했을 때
 *  이전 값을 발행한 이후에 얼마나 시간이 흘렀는지 알려줌
 */

public class TimeIntervalExample {
    public static void main(String[] args) {
        Integer[] data = {1, 3, 7};

        CommonUtils.exampleStart();

        Observable<Timed<Integer>> source = Observable.fromArray(data)
                .delay(item -> {
                    CommonUtils.doSomething();
                    return Observable.just(item);
                }).timeInterval();

        source.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }
}
