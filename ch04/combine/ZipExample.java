package ch04.combine;

import commons.CommonUtils;
import commons.Log;
import commons.Shape;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class ZipExample {
    public static void main(String[] args) {

        String[] balls = {"BALL", "PENTAGON", "STAR"};
        String[] coloredTriangle = {"2-T", "6-T", "4-T"};

        // Zip() 은 2개 혹은 그 이상의 Observable 을 결합

        Observable<String> source =
                Observable.zip(
                        Observable.fromArray(balls).map(Shape::getSuffix),
                        Observable.fromArray(coloredTriangle).map(Shape::getColor),
                        (suffix, color) -> color + suffix
                );

        source.subscribe(Log::i);

        // 숫자 결합 예제
        Observable.zip(
                Observable.just(100 ,200, 300),
                Observable.just(10 ,20, 30),
                Observable.just(1 ,2, 3),
                (a, b, c) -> a + b + c
        ).subscribe(Log::i);

        // interval() 을 이용한 시간 결합

        Observable<String> source2 = Observable.zip(
                Observable.just("RED", "GREEN", "BLUE"),
                Observable.interval(200L, TimeUnit.MILLISECONDS),
                (value, i) -> value
        );

        CommonUtils.exampleStart();
        source2.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }
}
