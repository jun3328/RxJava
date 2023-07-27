package ch05.schedulers;

import commons.CommonUtils;
import commons.Log;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *  자바에서 재공하는 실행자(Executor) 를 변환하여 스케줄러를 생성
 *  기존에 사용하던 Executor 클래스를 재사용하는 경우에 한정적으로 활용
 */

public class ExecutorSchedulerExample {
    public static void main(String[] args) {

        final int THREAD_NUM = 10;

        Integer[] arr = {1, 3, 5};

        Observable<Integer> source = Observable.fromArray(arr);
        Executor executor = Executors.newFixedThreadPool(THREAD_NUM);

        source.subscribeOn(Schedulers.from(executor))
                .subscribe(Log::i);

        source.subscribeOn(Schedulers.from(executor))
                .subscribe(Log::i);

        CommonUtils.sleep(500);
    }
}
