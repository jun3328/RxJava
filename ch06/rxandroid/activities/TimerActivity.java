package io.github.jesterz91.rxandroid.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.github.jesterz91.rxandroid.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class TimerActivity extends AppCompatActivity {

    private static final String TAG = TimerActivity.class.getSimpleName();


    private boolean isRunning = false;

    private Unbinder unbinder;
    private List<String> logs = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private DisposableObserver<String> observer;

    @BindView(R.id.listView)
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        unbinder = ButterKnife.bind(this);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, logs);
        listView.setAdapter(adapter);

        observer = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext: " + s);
                adapter.add(s);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete()");
            }
        };
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null) unbinder.unbind();
        observer.dispose();
        super.onDestroy();
    }

    @OnClick(R.id.rxTimer)
    public void rxTimer() {
        startPolling();
    }

    private void startPolling() {
        Observable<String> ob = Observable.interval(3, TimeUnit.SECONDS)
                .flatMap(new Function<Long, Observable<String>>() {
                    @Override
                    public Observable<String> apply(Long aLong) {
                        return Observable.just("polling v1" + aLong);
                    }
                });

        ob.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
