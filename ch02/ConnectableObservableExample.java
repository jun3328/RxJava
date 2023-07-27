package ch02;

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;

import java.util.concurrent.TimeUnit;

/**
 *  ConnectableObservable
 *
 *  여러 구독자에게 데이터를 발행하기 위해 connect() 호출 전까지 데이터 발행을 유예
 */

public class ConnectableObservableExample {
    public static void main(String[] args) throws InterruptedException {
        String[] dt = {"1","3","5"};
        Observable<String> balls = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(i -> dt[i])
                .take(dt.length);


        ConnectableObservable<String> source = balls.publish();
        source.subscribe(data -> System.out.println("Subscriber #1 => " + data));
        source.subscribe(data -> System.out.println("Subscriber #2 => " + data));
        source.connect();

        Thread.sleep(250);
        source.subscribe(data -> System.out.println("Subscriber #3 => " + data));
        Thread.sleep(100);
    }
}
