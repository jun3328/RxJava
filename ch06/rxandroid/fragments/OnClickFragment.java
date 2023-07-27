package io.github.jesterz91.rxandroid.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.jesterz91.rxandroid.R;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;

public class OnClickFragment extends Fragment {


    @BindView(R.id.btn_click_observer)
    Button button;

    Unbinder unbinder;

    public static final String TAG = OnClickFragment.class.getSimpleName();

    private int count = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.item_onclick, parent, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // onClick() 의 Observable 활용
        
//        getClickEventObservable()
//                .map(new Function<View, String>() {
//                    @Override
//                    public String apply(View view) {
//                        return "Clicked";
//                    }
//                }).subscribe(getObserver());


        // RxBinding 활용
        getClickEventObservableWithRxBinding()
                .subscribe(getObserver());
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) unbinder.unbind();
        super.onDestroyView();
    }
    // RxBinding 라이브러리의 RxView 객체를 사용하면
    // Observable 의 명시적 생성이 필요 없으며, 클릭 리스너 설정도 내부에서 자동으로 처리됨

    private Observable<String> getClickEventObservableWithRxBinding() {
        return RxView.clicks(button)
                .map(new Function<Object, String>() {
                    @Override
                    public String apply(Object o) {
                        return "Clicked RxBinding";
                    }
                });
    }

    private Observable<View> getClickEventObservable() {
        return Observable.create(new ObservableOnSubscribe<View>() {
            @Override
            public void subscribe(final ObservableEmitter<View> emitter) throws Exception {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        emitter.onNext(v);
                    }
                });
            }
        });

    }

    private Observer<String> getObserver() {
        return new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Toast.makeText(getActivity(), "onNext() : " + s + count++, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError() : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete()");
            }
        };
    }
}
