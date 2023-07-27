package ch04.transform;

import commons.Log;
import io.reactivex.Observable;

public class ScanExample {
    public static void main(String[] args) {
        String[] balls = {"1", "3", "5"};
        Observable.fromArray(balls)
                .scan((ball1, ball2) -> ball2 + "(" + ball1 +")")
                .subscribe(Log::i);
    }
}
