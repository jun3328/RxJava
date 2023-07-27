package ch04.create;

import commons.Log;
import io.reactivex.Observable;

public class RepeatExample {
    public static void main(String[] args) {
        // repeat() 은 단순히 실행을 반복함
        // 일반적으로 서버가 잘 살아있는지 확인(ping 또는 heartbeat) 하는데 사용

        String[] balls = {"1", "3", "5"};

        Observable.fromArray(balls)
                .repeat(3)
                .doOnComplete(() -> Log.d("onComplete")) // onComplete() 호출 시 로그 출력
                .subscribe(Log::i);

        // 3번 반복후 onComplete 이벤트 발생
    }
}

