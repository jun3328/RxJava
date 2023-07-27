package ch01;

import io.reactivex.Observable;

/**
 *
 *  Observable 클래스는 데이터의 변화가 발생하는 데이터 소스
 *
 *  just() 함수는 가장 단순한 Observable 선언 방식
 *  데이터 소스에서 'Hello' 와 'RxJava 2!!'를 발행
 *  래퍼 타입부터 사용자 정의 클래스 객체까지 인자로 전달 가능
 *
 *  subscribe() 함수는 Observable 을 구독
 *  Observable 은 subscribe() 을 호출해야 비로소 변화한 데이터를 구독자에게 발행
 *
 *  System.out::println 자바8의 메서드 레퍼런스 활용
 *  레퍼런스를 활용하지 않으면 data -> System.out.println(data) 와 동일
 *
 */
public class FirstExample {

    private void emit() {
        Observable.just("Hello", "RxJava 2!!")
                .subscribe(System.out::println);
    }

    public static void main(String[] args) {
        FirstExample demo = new FirstExample();
        demo.emit();
    }
}
