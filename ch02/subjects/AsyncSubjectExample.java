package ch02.subjects;

import io.reactivex.Observable;
import io.reactivex.subjects.AsyncSubject;

/**
 *  AsyncSubject
 *
 *  Observable 에서 발행한 마지막 데이터를 얻어올수 있는 클래스
 *  완료되기 전 마지막 데이터에만 관심 있으며, 이전 데이터 무시
 *
 */

public class AsyncSubjectExample {
    public static void main(String[] args) {
        AsyncSubject<Integer> subject = AsyncSubject.create();
        subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
        subject.onNext(1);
        subject.onNext(3);
        subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));
        subject.onNext(5);
        subject.onComplete();


        // 구독자로 동작하는 AsyncSubject
        Float[] temperature = {10.1f, 13.4f, 12.5f};
        // Observable 생성
        Observable<Float> source = Observable.fromArray(temperature);
        // AsyncSubject 생성
        AsyncSubject<Float> asSubscriber = AsyncSubject.create();
        // 데이터 수신
        asSubscriber.subscribe(data -> System.out.println("asSubscriber #1 => " + data));
        // asSubscriber 가 source 를 구독
        source.subscribe(asSubscriber);

        // onComplete() 호출 후 구독

        AsyncSubject<Integer> afterComplete = AsyncSubject.create();
        afterComplete.onNext(10);
        afterComplete.onNext(11);
        afterComplete.subscribe(data -> System.out.println("afterComplete # 1 => " + data));
        afterComplete.onNext(12);
        afterComplete.onComplete();
        afterComplete.onNext(13);
        afterComplete.subscribe(data -> System.out.println("afterComplete # 2 => " + data));
        afterComplete.subscribe(data -> System.out.println("afterComplete # 3 => " + data));
    }
}
