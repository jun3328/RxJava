package ch05.schedulers;

import commons.CommonUtils;
import commons.Log;
import commons.Shape;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * 스케줄러는 스레드를 지정할 수 있게 해줌
 *
 * subscribeOn() : 구독자가 subscribe() 를 호출하여 구독할 때 실행되는 스레드를 지정
 * observableOn() : Observable 에서 생성한 데이터 흐름이 여러 함수를 거치며 처리될 때 동작하는를 스레드 지정
 *
 */

public class FlipExample {
    public static void main(String[] args) {

        String[] objs = {"1-S", "2-T", "3-P"};

        Observable<String> source = Observable.fromArray(objs)
                .doOnNext(data -> Log.v("Original data = " + data))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .map(Shape::flip);

        source.subscribe(Log::i);

        CommonUtils.sleep(500);
    }
}
