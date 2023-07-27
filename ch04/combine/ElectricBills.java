package ch04.combine;

import commons.Log;
import io.reactivex.Observable;
import models.Pair;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class ElectricBills {
    public static void main(String[] args) {

        Integer[] data = {
                100, // 910 + 93.3 * 100 = 10,240원
                300 // 1600 + 93.3 * 200 + 1600 + 187.9 * 100 = 30,050원
        };

        Observable<Integer> basePrice = Observable.fromArray(data)
                .map(val -> {
                    if (val <= 200) return 910;
                    if (val <= 400) return 1600;
                    return 7300;
                });

        Observable<Integer> usagePrice = Observable.fromArray(data)
                .map(val -> {
                    double series1 = min(200, val) * 93.3;
                    double series2 = min(200, max(val - 200, 0)) * 187.9;
                    double series3 = min(0, max(val - 400, 0)) * 280.65;
                    return (int) (series1 + series2 + series3);
                });

        Observable<Pair<Integer, Integer>> source = Observable.zip(
                basePrice,
                usagePrice,
                Observable.fromArray(data),
                (v1, v2, i) -> Pair.create(i, v1 + v2)
        );
        source.subscribe(val -> {
            StringBuilder sb = new StringBuilder();
            sb.append("Usage : " + val.first + "kWh => ");
            sb.append("price : " + val.second + "원");
            Log.i(sb.toString());
        });
    }
}
