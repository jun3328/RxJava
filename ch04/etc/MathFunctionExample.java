package ch04.etc;

import commons.Log;
import hu.akarnokd.rxjava2.math.MathFlowable;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class MathFunctionExample {
    public static void main(String[] args) {
        Integer[] data = {1, 2, 3, 4};

        // 1. count
        Single<Long> source = Observable.fromArray(data)
                .count();
        source.subscribe(count -> System.out.println("count is " + count));

        // 2. max() & min()
        Flowable.fromArray(data)
                .to(MathFlowable::max)
                .subscribe(max -> Log.i("max is = " + max));

        Flowable.fromArray(data)
                .to(MathFlowable::min)
                .subscribe(min -> Log.i("min is = " + min));

        // 3. sum() & average()
        Flowable.fromArray(data)
                .to(MathFlowable::sumInt)
                .subscribe(sum -> Log.i("sum is = " + sum));

        Observable.fromArray(data)
                .toFlowable(BackpressureStrategy.BUFFER)
                .to(MathFlowable::averageDouble)
                .subscribe(avg -> Log.i("average is = " + avg));
    }
}
