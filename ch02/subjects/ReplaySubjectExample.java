package ch02.subjects;

import io.reactivex.subjects.ReplaySubject;

/**
 *  ReplaySubject
 *
 *  차가운 Observable 처럼 동작하는 클래스
 *
 *  구독자가 생기면 항상 데이터의 처음부터 끝까지 발행하는 것을 보장
 *  모든 데이터 내용을 저장해두는 과정 중 메모리 누수가 발생할 가능성을 염두에 두고 사용할때 주의가 필요
 */

public class ReplaySubjectExample {
    public static void main(String[] args) {

        ReplaySubject<Integer> subject = ReplaySubject.create();
        subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
        subject.onNext(1);
        subject.onNext(3);
        subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));
        subject.onNext(5);
        subject.onComplete();

    }
}
