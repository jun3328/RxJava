package io.github.jesterz91.rxandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.github.jesterz91.rxandroid.R;

public class MainActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null) unbinder.unbind();
        super.onDestroy();
    }

    @OnClick({R.id.hello, R.id.loop, R.id.event,
            R.id.search, R.id.appInfo, R.id.async,
            R.id.timer, R.id.retrofit})
    public void goActivity(View view) {
        switch (view.getId()) {
            case R.id.hello:
                startActivity(new Intent(this, HelloActivity.class));
                break;
            case R.id.loop:
                startActivity(new Intent(this, LoopActivity.class));
                break;
            case R.id.event:
                startActivity(new Intent(this, EventActivity.class));
                break;
            case R.id.search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            case R.id.appInfo:
                startActivity(new Intent(this, AppInfoActivity.class));
                break;
            case R.id.async:
                startActivity(new Intent(this, AsyncTaskActivity.class));
                break;
            case R.id.timer:
                startActivity(new Intent(this, TimerActivity.class));
                break;
            case R.id.retrofit:
                startActivity(new Intent(this, RestfulActivity.class));
                break;
        }

    }

}
