package ch04.create;

import io.reactivex.Observable;
import commons.Log;

public class RangeExample {
    public static void main(String[] args) {

        // range(start, count) 는 주어진 start 값 부터 count 개의 Integer 객체 발행
        // 스케줄러에서 실행되지 않고, 반복문(for, while)을 대체할 수 있다.
        Observable.range(1, 10)
                .filter(num -> num % 2 == 0)
                .subscribe(Log::it);
    }
}
