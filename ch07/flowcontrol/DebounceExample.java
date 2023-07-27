package ch07.flowcontrol;

import commons.CommonUtils;
import commons.Log;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class DebounceExample {
    public static void main(String[] args) {
        Integer[] data = {1, 2, 3, 5};

        Observable<Integer> source = Observable.concat(
                Observable.timer(100L, TimeUnit.MILLISECONDS).map(i -> data[0]),
                Observable.timer(300L, TimeUnit.MILLISECONDS).map(i -> data[1]),
                Observable.timer(100L, TimeUnit.MILLISECONDS).map(i -> data[2]),
                Observable.timer(300L, TimeUnit.MILLISECONDS).map(i -> data[3]))
                .debounce(200L, TimeUnit.MILLISECONDS);

        source.subscribe(Log::i);

        CommonUtils.sleep(1000);
    }
}
