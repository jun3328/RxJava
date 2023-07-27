package ch05.schedulers;

import commons.CommonUtils;
import commons.Log;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.io.File;

/**
 *  IO 스케줄러 (네트워크 요청, 파일 입출력, DB 쿼리 등)
 */

public class IOSchedulerExample {
    public static void main(String[] args) {

        String root = "C:\\";

        File[] files = new File(root).listFiles();

        Observable<String> source = Observable.fromArray(files)
                .filter(f -> !f.isDirectory())
                .map(f -> f.getAbsolutePath())
                .subscribeOn(Schedulers.io());

        source.subscribe(Log::i);
        CommonUtils.sleep(500);

    }
}
