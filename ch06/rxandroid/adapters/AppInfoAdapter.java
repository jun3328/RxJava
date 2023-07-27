package io.github.jesterz91.rxandroid.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.jesterz91.rxandroid.R;
import io.github.jesterz91.rxandroid.models.AppInfo;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.subjects.PublishSubject;

public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.AppInfoHolder> {

    // 설치된 애플리케이션 리스트
    private List<AppInfo> items = new ArrayList<>();

    // Hot Observable 인 PublishSubject 를 사용하는 이유는
    // 구독자가 없더라도 실시간으로 처리되어 소비해야하는 Click 이벤트의 특성 때문
    private PublishSubject<AppInfo> publishSubject;

    public AppInfoAdapter() {
        this.publishSubject = PublishSubject.create();
    }

    @NonNull
    @Override
    public AppInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appinfo, parent, false);
        return new AppInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppInfoHolder appInfoHolder, int position) {
        AppInfo info = items.get(position);
        appInfoHolder.appImage.setImageDrawable(info.getImage());
        appInfoHolder.appTitle.setText(info.getTitle());

        // 아이템뷰의 클릭 이벤트를 publishSubject 가 구독
        appInfoHolder.getClickObserver(info).subscribe(publishSubject);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateItem(AppInfo appInfo) {
        items.add(appInfo);
    }

    public PublishSubject<AppInfo> getPublishSubject() {
        return publishSubject;
    }

    public static class AppInfoHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_image)
        ImageView appImage;
        @BindView(R.id.item_title)
        TextView appTitle;

        public AppInfoHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        // 클릭이벤트를 분리된 Observable 에 생성
        public Observable<AppInfo> getClickObserver(final AppInfo appInfo) {
            return Observable.create(new ObservableOnSubscribe<AppInfo>() {
                @Override
                public void subscribe(final ObservableEmitter<AppInfo> emitter) throws Exception {

                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            emitter.onNext(appInfo);
                        }
                    });
                }
            });
        }
    }
}
