package ch04.combine;

import commons.CommonUtils;
import commons.Log;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

/**
 *  merge() 는 입력 Observable 의 순서와 데이터 발행여부에 관여하지 않고
 *  어느 것이든 업스트림에서 먼저 입력되는 데이터를 그대로 발행
 */

public class MergeExample {
    public static void main(String[] args) {
        String[] data1 = {"1", "3"};
        String[] data2 = {"2", "4", "6"};

        CommonUtils.exampleStart();

        Observable<String> source1 = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(idx -> data1[idx])
                .take(data1.length);

        Observable<String> source2 = Observable.interval(50L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(idx -> data2[idx])
                .take(data2.length);

        // source1 , source2 는 개별 스레드에서 발행

        Observable<String> source = Observable.merge(source1, source2);
        source.subscribe(Log::i);

        CommonUtils.sleep(1000);
    }
}
