package ch02.subjects;

import io.reactivex.subjects.PublishSubject;

/**
 *  PublisherSubject
 *
 *  구독자가 subscribe() 를 호출하면 값을 발행하기 시작
 *  해당시간에 발생한 데이터를 그대로 구독자에게 전달
 */

public class PublisherSubjectExample {
    public static void main(String[] args) {
        PublishSubject<String> subject = PublishSubject.create();
        subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
        subject.onNext("1");
        subject.onNext("2");
        subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));
        subject.onNext("5");
        subject.onComplete();
    }
}
