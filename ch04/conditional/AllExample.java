package ch04.conditional;

import commons.Shape;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 *  all() 은 주어진 조건이 100% 맞을 때만 true 값을 발행하고,
 *  조건에 맞지 않는 데이터가 발행되면 바로 false 값을 발행
 */

public class AllExample {
    public static void main(String[] args) {
        String[] data = {"1", "2", "3", "4"};

        Single<Boolean> source = Observable.fromArray(data)
                .map(Shape::getShape)
                .all(Shape.BALL::equals);

        source.subscribe(System.out::println);
    }
}
