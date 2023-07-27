package io.github.jesterz91.rxandroid.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.jesterz91.rxandroid.R;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.editText)
    EditText editText;

    private Unbinder unbinder;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        unbinder = ButterKnife.bind(this);

        // 1초 동안 다른 문자 입력이 없으면 입력문자를 Toast 메시지로 띄움

//        disposable = getObservable()
//                .debounce(1000, TimeUnit.MILLISECONDS)
//                .filter(new Predicate<CharSequence>() {
//                    @Override
//                    public boolean test(CharSequence charSequence) throws Exception {
//                        return !TextUtils.isEmpty(charSequence);
//                    }
//                }).observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(getObserver());


        // RxBinding
        disposable = RxTextView.textChangeEvents(editText)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .filter(new Predicate<TextViewTextChangeEvent>() {
                    @Override
                    public boolean test(TextViewTextChangeEvent textViewTextChangeEvent) throws Exception {
                        return !TextUtils.isEmpty(textViewTextChangeEvent.text().toString());
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserverLib());
    }

    private DisposableObserver<TextViewTextChangeEvent> getObserverLib() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                Toast.makeText(SearchActivity.this, textViewTextChangeEvent.text().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private DisposableObserver<CharSequence> getObserver() {
        return new DisposableObserver<CharSequence>() {
            @Override
            public void onNext(CharSequence charSequence) {
                Toast.makeText(SearchActivity.this, "Search " + charSequence.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private Observable<CharSequence> getObservable() {
        return  Observable.create(new ObservableOnSubscribe<CharSequence>() {
            @Override
            public void subscribe(final ObservableEmitter<CharSequence> emitter) throws Exception {
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        emitter.onNext(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null) unbinder.unbind();
        super.onDestroy();
    }
}
