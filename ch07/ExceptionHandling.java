package ch07;

import commons.CommonUtils;
import commons.Log;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ExceptionHandling {
    public static void main(String[] args) {
        ExceptionHandling handling = new ExceptionHandling();

//        handling.tryCatch();
//        handling.onError();
//        handling.onErrorReturn();
//        handling.onErrorReturnItem();
        handling.onErrorResumeNext();
    }

    public void tryCatch(){

        System.out.println("\nTry Catch");

        Observable<String> source = Observable.create(
                emitter -> {
                    emitter.onNext("1");
                    emitter.onError(new Exception("Some Error"));
                    emitter.onNext("2");
                    emitter.onNext("3");
                }
        );

        try {
            source.subscribe(Log::i);
        } catch (Exception e) {
            // can not catch
            Log.e(e.getMessage());
        }
    }

    private void onError() {
        System.out.println("\nonError");

        String[] grades = {"70", "88", "$100", "93", "83"}; // $100 이 에러 데이터

        Observable<Integer> source = Observable.fromArray(grades)
                .map(Integer::parseInt);

        source.subscribe(
                data -> Log.d("Grade is " + data),
                e -> {
                    if (e instanceof NumberFormatException) {
                        e.printStackTrace();
                    }
                    Log.e("Wrong Data Found!");
                }
        );
    }

    private void onErrorReturn() {
        System.out.println("\nonErrorReturn");

        String[] grades = {"70", "88", "$100", "93", "83"}; // $100 이 에러 데이터

        Observable<Integer> source = Observable.fromArray(grades)
                .map(Integer::parseInt)
                .onErrorReturn(e -> {
                    if (e instanceof  NumberFormatException) {
                        e.printStackTrace();
                    }
                    return -1;
                });

        source.subscribe(data -> {
            if (data < 0){
                Log.e("Wrong Data found!!");
                return;
            }
            Log.i("Grade is " + data);
        });
    }

    private void onErrorReturnItem() {
        System.out.println("\nonErrorReturnItem");

        String[] grades = {"70", "88", "$100", "93", "83"}; // $100 이 에러 데이터

        Observable<Integer> source = Observable.fromArray(grades)
                .map(Integer::parseInt)
                .onErrorReturnItem(-1);

        source.subscribe(data -> {
            if (data < 0){
                Log.e("Wrong Data found!!");
                return;
            }
            Log.i("Grade is " + data);
        });
    }

    private void onErrorResumeNext() {
        // onErrorResumeNext() 는 에러가 발생했을 때 내가 원하는
        // observable 로 대체하는 방법

        String[] salesData = {"100", "200", "A300"}; // A300 은 에러 데이터

        Observable<Integer> onParseError = Observable.defer(() -> {
            Log.d("send email to administrator");
            return Observable.just(-1);
        }).subscribeOn(Schedulers.io());

        Observable<Integer> source = Observable.fromArray(salesData)
                .map(Integer::parseInt)
                .onErrorResumeNext(onParseError);

        source.subscribe(data -> {
            if (data < 0 ) {
                Log.e("Wrong Data found!");
                return;
            }

            Log.i("Sales data : " + data);
        });

        CommonUtils.sleep(1000);
    }
}
