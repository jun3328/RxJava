package io.github.jesterz91.rxandroid.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.jesterz91.rxandroid.R;
import io.github.jesterz91.rxandroid.adapters.AppInfoAdapter;
import io.github.jesterz91.rxandroid.models.AppInfo;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AppInfoActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private Unbinder unbinder;
    private AppInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        unbinder = ButterKnife.bind(this);

        adapter = new AppInfoAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.getPublishSubject()
                .subscribe(new Consumer<AppInfo>() {
                    @Override
                    public void accept(AppInfo appInfo) {
                        Toast.makeText(AppInfoActivity.this, appInfo.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (adapter == null) return;

        // 기기에 설치된 앱 정보가 Adapter 에 업데이트 됨
        getAppInfoObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AppInfo>() {
                    @Override
                    public void accept(AppInfo appInfo) {
                        adapter.updateItem(appInfo);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null) unbinder.unbind();
        super.onDestroy();
    }

    // 기기에 설치된 애플리케이션 정보를 Observable 로 반환
    private Observable<AppInfo> getAppInfoObservable() {

        final PackageManager pm = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        return Observable.fromIterable(pm.queryIntentActivities(intent, 0))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<ResolveInfo, AppInfo>() {
                    @Override
                    public AppInfo apply(ResolveInfo resolveInfo) {
                        Drawable image = resolveInfo.loadIcon(pm);
                        String title = resolveInfo.activityInfo.loadLabel(pm).toString();
                        return new AppInfo(image, title); // 애플리케이션 이미지와 이름을 mapping
                    }
                });

    }
}
