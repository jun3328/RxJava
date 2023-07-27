package ch02.subjects;

import io.reactivex.subjects.BehaviorSubject;

/**
 *  BehaviorSubject
 *
 *  구독자가 구독을 하면 가장 최근 값 혹은 기본값을 넘겨주는 클래스
 *  ex) 온도 센서에서 최근 온도값을 받아오는 동작
 *
 */

public class BehaviorSubjectExample {
    public static void main(String[] args) {
        BehaviorSubject<String> subject = BehaviorSubject.createDefault("6"); // 발행값이 없을 경우의 초기값
        subject.subscribe(data -> System.out.println("Subscriber #1 =>" + data));
        subject.onNext("1");
        subject.onNext("3");
        subject.subscribe(data -> System.out.println("Subscriber #2 =>" + data));
        subject.onNext("5");
        subject.onComplete();
    }
}
