package ch03;

import io.reactivex.Observable;
import models.Pair;

import java.util.ArrayList;
import java.util.List;

public class QueryTvSales {
    public static void main(String[] args) {
        // TV : $2,500 , Camera : $300 , tv : $1,600 , Phone : $800

        // tv 매출 총합 계산 : 전체 매출 데이터 입력 -> tv 매출만 필터링 -> tv 매출의 합을 구함
        List<Pair<String, Integer>> sales = new ArrayList<>();
        sales.add(Pair.create("TV", 2500));
        sales.add(Pair.create("Camera", 300));
        sales.add(Pair.create("TV", 1600));
        sales.add(Pair.create("Phone", 800));

        Observable.fromIterable(sales)
                .filter(product -> product.first.equals("TV")) //매출 데이터 중 TV 매출을 필터링
                .map(product -> product.second)
                .reduce((price1, price2) -> price1 + price2) // TV 매출의 합을 구함
                .subscribe(tot -> System.out.println("TV Sales : $" + tot));

    }
}
