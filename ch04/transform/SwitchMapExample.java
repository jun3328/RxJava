package ch04.transform;

import commons.CommonUtils;
import commons.Log;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class SwitchMapExample {
    public static void main(String[] args) {
        // switchMap() 은 인터리빙이 발생할 수 있는 상황에서
        // 순서를 보장하기 위해 기존에 진행 중이던 작업을 바로 중단
        // 여러 개의 값이 발행되었을 때 마지막에 들어온 값만 처리하고 싶을 때 사용
        // 중간에 끊기더라도 마지막 데이터 처리는 보장하기 때문

        // ex) 센서 등의 값을 얻어와서 동적으로 처리하는 경우

        CommonUtils.exampleStart();
        String[] balls = {"1", "3", "5"};
        Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(idx -> balls[idx])
                .take(balls.length)
                .doOnNext(Log::dt)
                .switchMap(ball -> Observable.interval(200L, TimeUnit.MILLISECONDS)
                        .map(notUsed -> ball + "<>")
                        .take(2)
                ).subscribe(Log::it);
        CommonUtils.sleep(2000);

    }

    // Observable 은 데이터를 발행하는 스레드와, 그 값을 전달하는 스레드를 다르게 사용
}
