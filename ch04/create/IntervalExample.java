package ch04.create;

import io.reactivex.Observable;
import commons.CommonUtils;
import commons.Log;

import java.util.concurrent.TimeUnit;

public class IntervalExample {
    public static void main(String[] args) {
        CommonUtils.exampleStart();
        // interval() 은 주어진 시간 간격으로 0 부터 1씩 증가하는 Long 객체 발행
        Observable.interval(0L,100L, TimeUnit.MILLISECONDS)
                .map(data -> (data + 1) * 100)
                .take(5)
                .subscribe(Log::it); // RxComputationThreadPool-1 에서 실행

        // RxComputationThreadPool-1 은 계산 스케줄러에서 실행할 때 사용하는 스레드 이름


        // sleep()을 호출하는 이유는 RxComputationThreadPool-1 에서
        // 실행이 완료될 때까지 기다려야 하기 때문
        // 주석처리 시 프로그램이 바로 종료
        CommonUtils.sleep(1000);
    }
}
