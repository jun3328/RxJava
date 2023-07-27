package ch03;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;

public class ReduceExample {
    public static void main(String[] args) {

        // reduce() 는 발행한 데이터를 모두 사용하여 어떤 최종 결과 데이터를 합성

        String[] balls = {"1", "3", "5"};

        Maybe<String> source = Observable.fromArray(balls)
                .reduce((ball1, ball2) -> ball2 + "(" + ball1 + ")");

        source.subscribe(System.out::println); // 5(3(1))


        // reduce() 함수와 람다 표현식 분리

        BiFunction<String, String, String> mergeBalls =
                (ball1, ball2) -> ball2 + "(" + ball1 + ")";

        source = Observable.fromArray(balls)
                .reduce(mergeBalls);
        source.subscribe(System.out::println);
    }
}
