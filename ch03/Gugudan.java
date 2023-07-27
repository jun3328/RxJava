package ch03;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

import java.util.Scanner;

public class Gugudan {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Gugudan input : ");

        // 일반적인 자바코드로 작성한 구구단
        int dan = Integer.parseInt(in.nextLine());
        for (int row = 1; row <= 9; row++) {
            System.out.println(dan + " * " + row + " = " + dan * row);
        }


        // for 문을 Observable 로 변환
        // Observable.range(start, count) 는 start 숫자부터 count 개의 숫자 값을 발행
        Observable<Integer> source = Observable.range(1, 9);
        source.subscribe(row -> System.out.println(dan + " * " + row + " = " + dan * row));


        // flatMap() 을 활용
        Function<Integer, Observable<String>> gugudan = num ->
                Observable.range(1, 9).map(row -> num + " * " + row + " = " + num * row);

        Observable.just(dan)
                .flatMap(gugudan)
                .subscribe(System.out::println);


        // 람다식
        Observable.just(dan)
                .flatMap(num -> Observable.range(1, 9).map(row -> num + " * " + row + " = " + num * row))
                .subscribe(System.out::println);


        // flatMap(Function<T, Observable<U>> mapper, BiFunction<T, U, R> resultSelector)
        // mapper 의 인자로 받은 T와 그것의 결과로 나오는 U를 기반으로, 새로운 Observable 을 생성)
        Observable.just(dan)
                .flatMap(gugu -> Observable.range(1, 9),
                        (gugu, i) -> gugu + " * " + i + " = " + gugu * i)
                .subscribe(System.out::println);
    }
}
