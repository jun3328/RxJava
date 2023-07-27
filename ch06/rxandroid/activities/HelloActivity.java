package io.github.jesterz91.rxandroid.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.jesterz91.rxandroid.R;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;

public class HelloActivity extends RxAppCompatActivity {

    // Observable 의 생명주기 관리(RxLifecycle)를 위해 RxAppCompatActivity 를 상속

    @BindView(R.id.textView)
    TextView textView;

    @BindView(R.id.textView2)
    TextView textView2;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        unbinder = ButterKnife.bind(this);

        usingCreate();

        usingJust();
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null) unbinder.unbind();
        super.onDestroy();
    }

    private void usingCreate() {
        Observer<String> observable = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                textView.setText(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Hello World! - create()");
                emitter.onComplete();
            }
        }).subscribe(observable);
    }

    private void usingJust() {
        // Hello Activity 가 종료되면 Observable 은 자동으로 해제(dispose)
        Observable.just("Hello World!! - just()")
                .compose(this.<String>bindToLifecycle()) //라이프 사이클을 관리하도록 추가
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        textView2.setText(s);
                    }
                });
    }
}
