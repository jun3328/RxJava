package ch04.transform;

import commons.CommonUtils;
import commons.Log;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class ConcatMapExample {
    public static void main(String[] args) {

        String[] balls = {"1", "3", "5"};

        // flatMap() 은 먼저 들어온 데이터를 처리하는 도중에 새로운 데이터가 들어오면
        // 나중에 들어온 데이터의 처리 결과가 먼저 출력될 수 도 있다. interleaving(끼어들기)

        System.out.println("flatMap()");
        CommonUtils.exampleStart();
        Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(idx -> balls[idx])
                .take(balls.length)
                .flatMap(ball -> Observable.interval(200L, TimeUnit.MILLISECONDS)
                        .map(notUsed -> ball + "<>")
                        .take(2)
                ).subscribe(Log::it);

        CommonUtils.sleep(2000);


        // concatMap() 은 먼저 들어온 데이터 순서대로 처리해서 결과를 내도록 보장

        System.out.println("concatMap()");
        CommonUtils.exampleStart();
        Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(idx -> balls[idx])
                .take(balls.length)
                .concatMap(ball -> Observable.interval(200L, TimeUnit.MILLISECONDS)
                        .map(notUsed -> ball + "<>")
                        .take(2)
                ).subscribe(Log::it);

        CommonUtils.sleep(2000);


    }
}
