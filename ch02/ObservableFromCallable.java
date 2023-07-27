package ch02;

import io.reactivex.Observable;

import java.util.concurrent.Callable;

public class ObservableFromCallable {
    public static void main(String[] args) {
        Callable<String> callable = () -> {
            Thread.sleep(1000);
            return "Hello Callable";
        };

        Observable.fromCallable(callable)
                .subscribe(System.out::println);


    }
}
