package ch05.schedulers;

import commons.CommonUtils;
import commons.Log;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 *  NewThreadScheduler 는 새로운 스레드를 생성하여 원하는 동작을 처리
 */

public class NewThreadSchedulerExample {
    public static void main(String[] args) {

        Integer[] arr = {1, 3, 5};

        Observable.fromArray(arr)
                .doOnNext(data -> Log.v("Original data " + data))
                .map(data -> "<<" + data + ">>")
                .subscribeOn(Schedulers.newThread())
                .subscribe(Log::i);
        CommonUtils.sleep(500);

        Observable.fromArray(arr)
                .doOnNext(data -> Log.v("Original data " + data))
                .map(data -> "##" + data + "##")
                .subscribeOn(Schedulers.newThread())
                .subscribe(Log::i);
        CommonUtils.sleep(500);


    }
}
