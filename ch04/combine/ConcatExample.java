package ch04.combine;

import commons.CommonUtils;
import commons.Log;
import io.reactivex.Observable;
import io.reactivex.functions.Action;

import java.util.concurrent.TimeUnit;

/**
 *  concat() : 입력된 Observable 을 Observable 단위로 이어 붙임
 *  첫 번째 Observable 에서 onComplete 이벤트가 발생해야 두번 째 Observable 을 구독
 */

public class ConcatExample {
    public static void main(String[] args) {

        String[] data1 = {"1", "3", "5"};
        String[] data2 = {"2", "4", "6"};

        Action onCompleteAction = () -> Log.d("onComplete()");

        CommonUtils.exampleStart();

        Observable<String> source1 = Observable.fromArray(data1)
                .doOnComplete(onCompleteAction);

        Observable<String> source2 = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(idx -> data2[idx])
                .take(data2.length)
                .doOnComplete(onCompleteAction);

        Observable<String> source = Observable.concat(source1, source2)
        .doOnComplete(onCompleteAction);

        source.subscribe(Log::i);
        CommonUtils.sleep(1000);
    }
}
