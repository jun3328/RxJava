package ch04.create;

import commons.CommonUtils;
import commons.Log;
import commons.MarbleDiagram;
import commons.Shape;
import io.reactivex.Observable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.Callable;

public class DeferExample implements MarbleDiagram {

    Iterator<String> colors = Arrays.asList("1", "3", "5", "6").iterator();

    @Override
    public void marbleDiagram() {
        Callable<Observable<String>> supplier = () -> getObservable();
        Observable<String> source = Observable.defer(supplier);

        source.subscribe(val -> Log.i("subscriber #1 : " + val));
        source.subscribe(val -> Log.i("subscriber #2 : " + val));
        CommonUtils.exampleComplete();
    }

    private Observable<String> getObservable() {
        if (colors.hasNext()) {
            String color = colors.next();
            return Observable.just(
                    Shape.getString(color, Shape.BALL),
                    Shape.getString(color, Shape.RECTANGLE),
                    Shape.getString(color, Shape.PENTAGON)
            );
        }
        return Observable.empty();
    }

    public void notDeferred() {
        Observable<String> source = getObservable();

        source.subscribe(val -> Log.i("Subscriber #1:" + val));
        source.subscribe(val -> Log.i("Subscriber #2:" + val));
        CommonUtils.exampleComplete();
    }

    public static void main(String[] args) {

        // defer() 는 timer() 와 비슷하지만 데이터 흐름 생성을
        // 구독자가 subscribe() 함수를 호출할 때 까지 미룰수 있다.
        // 이 때 새로운 Observable 이 생성, 메인 스레드에서 실행

        DeferExample demo = new DeferExample();
        demo.marbleDiagram();
//        demo.notDeferred();
    }
}
