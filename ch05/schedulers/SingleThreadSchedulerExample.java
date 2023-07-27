package ch05.schedulers;

import commons.CommonUtils;
import commons.Log;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * 싱글 스레드 스케줄러
 *
 * RxJava 내부에서 단일 스레드를 별도로 생성하여 구독 작업을 처리
 * 리액티브 프로그래밍이 비동기 프로그래밍을 지향하기 때문에 활용할 확률은 낮음
 */

public class SingleThreadSchedulerExample {
    public static void main(String[] args) {

        Observable<Integer> numbers = Observable.range(100, 5);

        Observable<String> chars = Observable.range(0, 5)
                .map(CommonUtils::numberToAlphabet);

        numbers.subscribeOn(Schedulers.single())
                .subscribe(Log::i);

        chars.subscribeOn(Schedulers.single())
                .subscribe(Log::i);

        CommonUtils.sleep(500);
    }
}
