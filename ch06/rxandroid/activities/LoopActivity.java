package io.github.jesterz91.rxandroid.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.github.jesterz91.rxandroid.R;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class LoopActivity extends AppCompatActivity {

    public static final String TAG = LoopActivity.class.getSimpleName();

    private List<String> samples = Arrays.asList("banana", "orange", "apple", "apple mango", "melon", "watermelon");

    private Unbinder unbinder;

    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null) unbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.btn_loop)
    public void javaLoop() {
        for (String s : samples) {
            if (s.contains("apple")) {
                textView.setText(s + " - java loop");
                return;
            }
        }
    }

    @OnClick(R.id.btn_loop2)
    public void rxLoop() {
        Observable.fromIterable(samples)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s.contains("apple");
                    }
                })
                .first("Nt Found")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        textView.setText(s + " - rx filter");
                    }
                });
    }

}
