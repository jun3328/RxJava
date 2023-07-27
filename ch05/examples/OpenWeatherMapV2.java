package ch05.examples;

import commons.CommonUtils;
import commons.Log;
import commons.OkHttpHelper;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpenWeatherMapV2 {
    private static final String URL = "http://api.openweathermap.org/data/2.5/weather?q=London&APPID=";

    public void run() {

        CommonUtils.exampleStart();

        Observable<String> source = Observable.just(URL + CommonUtils.API_KEY)
                .map(OkHttpHelper::getWithLog)
                .subscribeOn(Schedulers.io())
                .share()
                .observeOn(Schedulers.newThread());

        // ConnectableObservable 클래스를 사용하여
        // 1개의 Observable 을 여러 구독자가 공유하는 방식으로
        // 차가운 Observable 을 뜨거운 Observable 로 변환

        // share() 는 ConnectableObservable 클래스의 publish() 와 refCount() 를 합한 것

        source.map(this::parseTemperature)
              .subscribe(Log::it);

        source.map(this::parseCityName)
              .subscribe(Log::it);

        source.map(this::parseCountry)
              .subscribe(Log::it);


        CommonUtils.sleep(1000);
    }

    private String parseTemperature(String json) {
        return parse(json, "\"temp\":[0-9]*.[0-9]*");
    }

    private String parseCityName(String json) {
        return parse(json, "\"name\":\"[a-zA-Z]*\"");
    }

    private String parseCountry(String json) {
        return parse(json, "\"country\":\"[a-zA-Z]*\"");
    }

    private String parse(String json, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(json);
        if (match.find()) {
            return match.group();
        }
        return "N/DoOnExample";
    }

    public static void main(String[] args) {
        OpenWeatherMapV2 demo = new OpenWeatherMapV2();
        demo.run();
    }
}
