package ch02;

import io.reactivex.Observable;
import org.reactivestreams.Publisher;

public class ObservableFromPublisher {
    public static void main(String[] args) {
        Publisher<String> publisher = subscriber -> {
            subscriber.onNext("Hello Observable.fromPublisher()");
            subscriber.onComplete();
        };

        Observable.fromPublisher(publisher)
                .subscribe(System.out::println);
    }
}
