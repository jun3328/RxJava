package ch04.create;

import io.reactivex.Observable;
import commons.CommonUtils;
import commons.Log;

import java.util.concurrent.TimeUnit;

public class IntervalRangeExample {
    public static void main(String[] args) {

        // IntervalRange 는 interval()과 range()를 혼합해놓은 함수. 리턴타입은 Long
        // 일정한 시간 간격으로 start 숫자로부터 count 개 만큼의 값만 생성하고 onComplete() 발생

        CommonUtils.exampleStart();

        // IntervalRange(start, count, initialDelay, period, timeUnit)
        Observable.intervalRange(1, 5, 100L, 100L, TimeUnit.MILLISECONDS)
                .subscribe(Log::it);

        CommonUtils.sleep(1000);

        // interval() 함수로 IntervalRange() 함수 구현

        CommonUtils.exampleStart();

        Observable.interval(100L, 100L, TimeUnit.MILLISECONDS)
                .map(val -> val + 1)
                .take(5)
                .subscribe(Log::it);

        CommonUtils.sleep(1000);

    }
}
