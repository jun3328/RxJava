package ch04.combine;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.observables.ConnectableObservable;

import java.util.Scanner;

public class ReactiveSum {
    public static void main(String[] args) {
        new ReactiveSum().run();
    }

    public void run() {
        ConnectableObservable<String> source = userInput();

        Observable<Integer> a = source
                .filter(str -> str.startsWith("a:"))
                .map(str -> str.replace("a:", ""))
                .map(Integer::parseInt);

        Observable<Integer> b = source
                .filter(str -> str.startsWith("b:"))
                .map(str -> str.replace("b:", ""))
                .map(Integer::parseInt);

        // combineLatest() 처음 값을 발행하려면 a, b 모두 값을 발행해야 하기 때문에
        // startWith(0) 를 호출하여 0으로 초기화
        Observable.combineLatest(
                a.startWith(0),
                b.startWith(0),
                (x, y) -> x + y
        ).subscribe(res -> System.out.println("Result : " + res));

        source.connect();
    }

    private ConnectableObservable<String> userInput() {
        return Observable.create((ObservableEmitter<String> emitter) -> {
            Scanner in = new Scanner(System.in);
            while (true) {
                System.out.print("Input : ");
                String line = in.nextLine();
                emitter.onNext(line);

                if (line.indexOf("exit") >= 0) {
                    in.close();
                    break;
                }
            }
        }).publish();
    }
}
