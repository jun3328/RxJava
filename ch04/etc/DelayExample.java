package ch04.etc;

import commons.CommonUtils;
import commons.Log;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

/**
 *  delay() 는 특정 시간만큼 Observable 의 데이터 발행을 지연
 *  interval() 처럼 계산 스케쥴러에서 실행
 */
public class DelayExample {
    public static void main(String[] args) {
        Integer[] data = {1, 7, 2, 3, 4,};

        CommonUtils.exampleStart();
        Observable.fromArray(data)
                .delay(100L, TimeUnit.MILLISECONDS)
                .subscribe(Log::it);
        CommonUtils.sleep(1000);
    }
}
