package ch07;

import commons.CommonUtils;
import commons.Log;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class DoOnExample {

    public static void main(String[] args) {
        DoOnExample example = new DoOnExample();

        example.basic();
        example.withError();
        example.doOnEach();
        example.doOnEachObserver();
        example.doOnSubscribeAndDispose();
        example.doOnLifecycle();
        example.doOnTerminate();
    }

    private void basic() {
        System.out.println("\nbasic()");

        Integer[] arr = {1, 3, 5};

        Observable<Integer> source = Observable.fromArray(arr);

        source.doOnNext(data -> Log.d("onNext()", data))
                .doOnComplete(() -> Log.d("onComplete()"))
                .doOnError(e -> Log.e("onError()", e.getMessage()))
                .subscribe(Log::i);
    }

    private void withError() {
        // withError() - divide by zero
        System.out.println("\nwithError()");

        Integer[] divider = {10, 5, 0};

        Observable.fromArray(divider)
                .map(div -> 1000 / div)
                .doOnNext(data -> Log.d("onNext()", data))
                .doOnComplete(() -> Log.d("onComplete()"))
                .doOnError(e -> Log.e("onError()", e.getMessage()))
                .subscribe(Log::i);
    }

    private void doOnEach() {
        // doOnEach() - Notification<T> 객체 사용
        System.out.println("\ndoOnEach()");

        String[] num = {"ONE", "TWO", "THREE"};
        Observable.fromArray(num)
                .doOnEach(noti -> {
                    if (noti.isOnNext()) Log.d("onNext()", noti.getValue());
                    if (noti.isOnComplete()) Log.d("onComplete()");
                    if (noti.isOnError()) Log.e("onError()", noti.getError().getMessage());
                }).subscribe(System.out::println);
    }

    private void doOnEachObserver() {

        // doOnEachObserver() - Observer 인터페이스 사용
        System.out.println("\ndoOnEachObserver()");

        Integer[] arr = {1, 3, 5};

        Observable.fromArray(arr)
                .doOnEach(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // doOnEach() 에서는 onSubscribe() 함수가 호출되지 않음
                        // doOnEach() 는 onNext(), onError(), OnComplete() 이벤트만 처리
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d("onNext()", integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError()", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("onComplete()");
                    }
                })
                .subscribe(Log::i);

    }

    private void doOnSubscribeAndDispose() {
        // doOnSubscribe(), doOnDispose()
        System.out.println("\ndoOnSubscribe(), doOnDispose()");

        Observable<Integer> observable = Observable.fromArray(new Integer[]{1, 3, 5, 2, 6})
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a)
                .doOnSubscribe(d -> Log.d("onSubscribe()"))
                .doOnDispose(() -> Log.d("onDispose()"));

        Disposable d = observable.subscribe(Log::i);

        CommonUtils.sleep(200);
        d.dispose();
        CommonUtils.sleep(300);
    }

    private void doOnLifecycle() {

        System.out.println("\ndoOnLifecycle()");

        Observable<Integer> source = Observable.fromArray(new Integer[]{1, 3, 5, 2, 6})
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a)
                .doOnLifecycle(
                        d -> Log.d("onSubscribe()"),
                        () -> Log.d("onDispose()"));

        Disposable d = source.subscribe(Log::i);

        CommonUtils.sleep(200);
        d.dispose();
        CommonUtils.sleep(300);
    }

    private void doOnTerminate() {
        // doOnTerminate() 는 Observable 이 끝나는 조건인 doOnComplete()
        // 혹은 onError() 이벤트 발생 직전에 호출
        System.out.println("\ndoOnTerminate()");

        Observable<Integer> source = Observable.fromArray(new Integer[]{1, 3, 5});

        source.doOnTerminate(() -> Log.d("onTerminate()"))
                .doOnComplete(() -> Log.d("doOnComplete"))
                .doOnError(e -> Log.e("onError()", e.getMessage()))
                .subscribe(Log::i);

    }
}
