package ch03;

import io.reactivex.Observable;
import io.reactivex.Single;

public class FilterExample {
    public static void main(String[] args) {

        // filer() 는 boolean 값을 리턴하는 함수형 인터페이스 Predicate 를 인자로 전달

        String[] objs = {"1 CIRCLE", "2 DIAMOND", "3 TRIANGLE", "4 DIAMOND", "5 CIRCLE", "6 HEXAGON"};

        // CIRCLE 만 필터링
        Observable.fromArray(objs)
                .filter(obj -> obj.endsWith("CIRCLE"))
                .subscribe(System.out::println);


        Integer[] data = {100, 34, 27, 99, 50};

        // 짝수만 필터링
        Observable.fromArray(data)
                .filter(num -> num % 2 == 0)
                .subscribe(System.out::println);

        // filter() 와 유사한 함수 활용

        Integer[] numbers = {100, 200, 300, 400, 500};
        Single<Integer> single;
        Observable<Integer> source;

        // 1. first
        single = Observable.fromArray(numbers).first(-1);
        single.subscribe(value -> System.out.println("first() value = " + value));

        // 2.last
        single = Observable.fromArray(numbers).last(999);
        single.subscribe(value -> System.out.println("last() value = " + value));

        // 3. take(N)
        source = Observable.fromArray(numbers).take(3);
        source.subscribe(value -> System.out.println("take(3) value = " + value));

        // 4. takeLast(N)
        source = Observable.fromArray(numbers).takeLast(3);
        source.subscribe(value -> System.out.println("takeLast(3) value = " + value));

        // 5. skip(N)
        source = Observable.fromArray(numbers).skip(2);
        source.subscribe(value -> System.out.println("skip(2) value = " + value));

        // 6. skipLast(N)
        source = Observable.fromArray(numbers).skipLast(2);
        source.subscribe(value -> System.out.println("skipLast(2) value = " + value));
    }
}
