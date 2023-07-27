package ch02;

import io.reactivex.Observable;

public class JustExample {

    private void emit() {
        // just() 는 인자로 넣은 데이터(1개 부터 최대 10개까지 같은 자료형)를 차례로 발행
        // 실제 발행은 subscribe() 호출해야 시작)
        Observable.just(1, 2, 3, 4, 5, 6)
                .subscribe(System.out::println);
    }

    public static void main(String[] args) {
        JustExample demo = new JustExample();
        demo.emit();
    }
}

