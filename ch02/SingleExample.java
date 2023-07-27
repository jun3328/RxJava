package ch02;

import io.reactivex.Observable;
import io.reactivex.Single;
import models.Order;

/**
 *  Single 클래스는 Observable 의 특수한 형태
 *  Observable 은 데이터를 무한하게 발행할 수 있지만
 *  Single 은 오직 1개의 데이터만 발행하도록 한정
 *
 *  또한 데이터 하나가 발행과 동시에 종료(onSuccess)
 *  라이프사이클 : onSuccess()와 onError()로 구성
 *
 *  결과가 유일한 서버 API 를 호출할때 유용하게 사용할 수 있음
 */

public class SingleExample {
    public static void main(String[] args) {

        // Single 클래스의 just() 활용
        Single<String> source = Single.just("Hello Single");
        source.subscribe(System.out::println);

        // Observable 에서 Single 클래스 사용

        // 1. 기존 Observable 에서 Single 로 변환
        Observable<String> source2 = Observable.just("Hello Single");
        Single.fromObservable(source2)
                .subscribe(System.out::println);

        // 2. Single() 을 호출해 생성
        Observable.just("Hello Single")
                .single("default item")
                .subscribe(System.out::println);

        // 3. first() 호출로 생성
        String[] colors = {"Red", "Blue", "Gold"};
        Observable.fromArray(colors)
                .first("default value")
                .subscribe(System.out::println);

        // 4. empty Observable 에서 생성
        Observable.empty()
                .single("default Value")
                .subscribe(System.out::println);

        // 5. take() 에서 생성
        Observable.just(new Order("ORD-1"), new Order("ORD-2"))
                .take(1)
                .single(new Order("default order"))
                .subscribe(System.out::println);

        // 값을 여러개 넣을 시 에러 발생
//        Observable.just("Hello Single", "Error")
//                .single("default item")
//                .subscribe(System.out::println);
    }
}
