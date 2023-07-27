package ch04.create;

import io.reactivex.Observable;
import commons.CommonUtils;
import commons.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimerExample {
    public static void main(String[] args) {
        CommonUtils.exampleStart();

        // timer() 는 interval() 과 유사하지만 한 번만 실행하는 함수(OL 발행)
        // 일정시간이 지난 후 한 개의 데이터를 발행하고 onComplete() 이벤트 발생
        // 일정 시간이 지난 후 특정 동작을 할떄 주로 사용
        Observable.timer(500L, TimeUnit.MILLISECONDS)
                .map(notUsed ->
                        new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
                .subscribe(Log::it);

        // 계산 스케줄러에서 실행되기 때문에 sleep() 호출
        CommonUtils.sleep(1000);
    }
}
