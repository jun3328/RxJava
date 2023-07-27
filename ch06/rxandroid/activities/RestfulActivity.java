package io.github.jesterz91.rxandroid.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.github.jesterz91.rxandroid.R;
import io.github.jesterz91.rxandroid.adapters.RestfulAdapter;
import io.github.jesterz91.rxandroid.models.Contributor;
import io.github.jesterz91.rxandroid.services.GitHubService;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RestfulActivity extends AppCompatActivity {

    private static final String TAG = RestfulAdapter.class.getSimpleName();

    private Unbinder unbinder;

    // 생성된 모든 Observable 을 생명주기에 맞춰 모두 해제 가능
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @BindView(R.id.tvService)
    TextView tvService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restful);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        // Observable 해제
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onDestroy();
    }

    @OnClick(R.id.btnService)
    public void service() {
        GitHubService service = RestfulAdapter.getInstance().getServiceApi();
        Observable<List<Contributor>> observable = service.getObContributors("ReactiveX", "RxJava");

        compositeDisposable.add(
                observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Contributor>>() {
                    @Override
                    public void onNext(List<Contributor> contributors) {
                        for (Contributor c : contributors) {
                            Log.i(TAG, "onNext: " + c);
                            tvService.append(c.toString() + "\n\n");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete()");
                    }
                }));

    }
}
