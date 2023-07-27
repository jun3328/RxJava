package ch02;

import io.reactivex.Observable;

public class ObservableCreateExample {
    public static void main(String[] args) {

        // create()는 onNext, onComplete, onError 같은 알림을 개발자가 직접 호출

        Observable<Integer> source = Observable.create(emitter -> {
            emitter.onNext(100);
            emitter.onNext(200);
            emitter.onNext(300);
            emitter.onComplete();
        });

        // subscribe() 를 호출하지 않으면, 아무것도 출력되지 않음

        // 메서드 레퍼런스 사용
        //source.subscribe(System.out::println);


        // 익명 객체를 활용
//        source.subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer data) throws Exception {
//                System.out.println("Result : " + data);
//            }
//        });

        // 람다 표현식 사용
        source.subscribe(data -> System.out.println("Result : " + data));
    }
}
