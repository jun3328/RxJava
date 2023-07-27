package ch02;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class ObservableNotification {
    public static void main(String[] args) {
        Observable<String> source = Observable.just("RED", "GREEN", "BLUE");

        /**
         *  Observable 은 3 가지의 알림을 구독자에게 전달
         *
         *  onNext() : 데이터의 발행을 알림, 기존 옵저버 패턴과 동일
         *  onComplete() : 모든 데이터의 발행 완료를 알림. 단 한번만 발생하며, 발생 후에는 더이상 onNext 이벤트가 발생해서는 안됨
         *  onError() : 에러 발생을 알림(이후 onNext, onComplete 이벤트가 발생하지 않음), Observable 의 실행을 종료
         */
        Disposable d = source.subscribe(
                v -> System.out.println("onNext() : value : " + v),
                err -> System.err.println("onError() : err : " + err.getMessage()),
                () -> System.out.println("onComplete()")
        );

        System.out.println("isDisposed() : " + d.isDisposed());
    }
}
