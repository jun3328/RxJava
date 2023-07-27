package ch04.combine;

import commons.CommonUtils;
import commons.Log;
import commons.Shape;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

/**
 * combineLatest() 는 2개 이상의 Observable 을 기반으로
 * Observable 각각의 값이 변경되었을 때 갱신해주는 함수
 * <p>
 * 마지막 인자로 combiner 는 Observable 을 결합하여
 * 어떤 결과를 만들어주는 역할(zip 의 zipper 인자와 동일)
 */


public class CombineLatestExample {
    public static void main(String[] args) {
        String[] data1 = {"6", "7", "4", "2"};
        String[] data2 = {"DIAMOND", "STAR", "PENTAGON"};


        CommonUtils.exampleStart();
        Observable<String> source = Observable.combineLatest(
                Observable.fromArray(data1)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS),
                        (shape, notUsed) -> Shape.getColor(shape)),
                Observable.fromArray(data2)
                .zipWith(Observable.interval(150L, TimeUnit.MILLISECONDS),
                        (shape, notUsed) -> Shape.getSuffix(shape)),
                (v1, v2) -> v1 + v2
        );
        source.subscribe(Log::i);
        CommonUtils.sleep(1000);
    }
}
