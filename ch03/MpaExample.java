package ch03;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class MpaExample {
    public static void main(String[] args) {
        String[] balls = {"RED", "YELLOW", "GREEN", "BLUE"};

        // 람다 표힌식 map()
        Observable.fromArray(balls)
                .map(ball -> ball + "<>")
                .subscribe(System.out::println);

        // Function<T, R>  제네릭 타입 T를 인자로 전달받아 제네릭 타입 R을 반환

        // Function 인터페이스를 적용한 map()
        Function<String, String> getDiamond = ball -> ball + "<>";
        Observable.fromArray(balls)
                .map(getDiamond)
                .subscribe(System.out::println);

        // 데이터 타입 추론
        Function<String, Integer> ballToIndex = ball -> {
            switch (ball) {
                case "RED":
                    return 1;
                case "YELLOW":
                    return 2;
                case "GREEN":
                    return 3;
                case "BLUE":
                    return 5;
                default:
                    return -1;
            }
        };
        Observable.fromArray(balls)
                .map(ballToIndex) // 명시적 타입 변환 없이 바로 사용할 수 있음.
                .subscribe(System.out::println);

    }
}
