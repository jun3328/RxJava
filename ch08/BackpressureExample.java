package ch08;

import commons.CommonUtils;
import commons.Log;
import io.reactivex.BackpressureOverflowStrategy;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class BackpressureExample {
    public static void main(String[] args) {

        // Flowable 클래스는 Observable 의 변형으로 배압(backpressure) 이슈를 위해 별도로 분리한 클래스

        BackpressureExample example = new BackpressureExample();
//        example.makeBackpressure();
//        example.usingBuffer();
//        example.usingDrop();
        example.usingLatest();
    }

    private void makeBackpressure() {
        CommonUtils.exampleStart();

        PublishSubject<Integer> subject = PublishSubject.create();

        subject.observeOn(Schedulers.computation())
                .subscribe(data -> {
                    CommonUtils.sleep(100); // 100ms 후 데이터 처리
                    Log.it(data);
                }, err -> Log.e(err.toString()));

        // Hot Observable 로 50,000,000 개의 데이터 연속 발행
        for (int i = 0; i < 50000000; i++) {
            subject.onNext(i);
        }
        subject.onComplete();
    }

    private void usingBuffer() {

        // onBackpressureBuffer() : 배압 이슈가 발생했을 때 별도의 버퍼에 저장
        // Flowable 클래스는 기본적으로 128개의 버퍼를 가짐

        CommonUtils.exampleStart();

        /**
         * 버퍼가 가득 찼을때 추가 실행전략
         *
         * -ERROR : 예외를 던지고 데이터 흐름 중단
         * -DROP_LATEST : 버퍼에 쌓인 최근 값을 제거
         * -DROP_OLDEST : 버퍼에 쌓인 가장 오래된 값 제거
         */

        Flowable.range(1, 50_000_000)
                .onBackpressureBuffer(128, // 버퍼의 개수 지정
                        () -> {}, // 버퍼가 넘쳤을 때 실행할 동작
                        BackpressureOverflowStrategy.DROP_OLDEST // 버퍼가 가득 찼을때 추가 실행전략
                ).observeOn(Schedulers.computation())
                .subscribe(data -> {
                    CommonUtils.sleep(100); // 100ms 후 데이터 처리
                    Log.it(data);
                }, err -> Log.e(err.toString()));

        CommonUtils.exampleComplete();
    }

    private void usingDrop() {

        // onBackpressureDrop() : 배압 이슈가 발생했을 때 해당 데이터를 무시


        CommonUtils.exampleStart();

        Flowable.range(1, 50_000_000)
                .onBackpressureDrop()
                .observeOn(Schedulers.computation())
                .subscribe(data -> {
                    CommonUtils.sleep(100); // 100ms 후 데이터 처리
                    Log.it(data);
                }, err -> Log.e(err.toString()));

        CommonUtils.sleep(20_000);
    }

    private void usingLatest() {

        // onBackpressureLatest() : 처리할 수 없어서 쌓이는 데이터를 무시하면서 최신 데이터만 유지

        CommonUtils.exampleStart();

        Flowable.range(1, 50_000_000)
                .onBackpressureLatest()
                .observeOn(Schedulers.computation())
                .subscribe(data -> {
                    CommonUtils.sleep(100); // 100ms 후 데이터 처리
                    Log.it(data);
                }, err -> Log.e(err.toString()));

        CommonUtils.sleep(20_000);
    }
}
