package ch02;

import io.reactivex.Observable;

import java.util.stream.IntStream;

public class ObservableFromArray {
    public static void main(String[] args) {
        Integer[] integerArr = {100, 200, 300};
        int[] intArr = {400, 500, 600};

        Observable<Integer> source = Observable.fromArray(integerArr);
        source.subscribe(System.out::println);

        // RxJava 에서 int 배열을 인식시키려면 Integer[]로 변환해야 함.
        // 자바8의 Stream API 로 변환처리
        Observable<Integer> source2 = Observable.fromArray(
                IntStream.of(intArr)
                        .boxed()
                        .toArray(Integer[]::new)
        );
        source2.subscribe(System.out::println);
    }
}
