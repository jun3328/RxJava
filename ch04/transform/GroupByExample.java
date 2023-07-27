package ch04.transform;

import commons.Shape;
import io.reactivex.Observable;
import io.reactivex.observables.GroupedObservable;

public class GroupByExample {
    public static void main(String[] args) {
        // GroupBy 는 어떤 기준(keySelector) 으로 단일 Observable 을 그룹으로 만듬

        String[] objs = {"6", "4", "2-T", "2", "6-T", "4-T"};

        Observable<GroupedObservable<String, String>> source =
                Observable.fromArray(objs)
                        .groupBy(Shape::getShape);

        // obj = GroupedObservable<String, String>
        source.subscribe(obj ->
                obj.subscribe(val ->
                            System.out.println("GROUP : " + obj.getKey() + "\t Value : " + val))
        );
    }
}
