package ch03;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class FlatMapExample {
    public static void main(String[] args) {
        // flatMap() 은 결과가 Observable 로 나옴
        // map() 이 1:1 관계라면, flatMap() 은 1:1 or 1:N 관계

        Function<String, Observable<String>> getDoubleDiamond =
                ball -> Observable.just(ball + "<>", ball + "<>");

        String[] balls = {"1", "3", "5"};

        Observable.fromArray(balls)
                .flatMap(getDoubleDiamond)
                .subscribe(System.out::println);

        // 람다 표현식
        Observable.fromArray(balls)
                .flatMap(ball -> Observable.just(ball + "<>", ball + "<>"))
                .subscribe(System.out::println);


    }
}
