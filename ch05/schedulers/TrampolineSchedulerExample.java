package ch05.schedulers;

import commons.CommonUtils;
import commons.Log;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 *  트램펄린 스케줄러
 *
 *  새로운 스레드를 생성하지 않고
 *  현재 스레드에 무한한 크기의 대기 행렬(Queue)을 생성하는 스케줄러
 */

public class TrampolineSchedulerExample {
    public static void main(String[] args) {

        Integer[] arr = {1, 3, 5};

        Observable<Integer> source = Observable.fromArray(arr);

        // 구독 #1
        source.subscribeOn(Schedulers.trampoline())
                .map(data -> "<<" + data + ">>")
                .subscribe(Log::i);

        // 구독 #2
        source.subscribeOn(Schedulers.trampoline())
                .map(data -> "##" + data + "##")
                .subscribe(Log::i);

        CommonUtils.sleep(500);
    }
}
