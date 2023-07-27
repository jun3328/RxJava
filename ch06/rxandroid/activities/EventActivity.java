package io.github.jesterz91.rxandroid.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.github.jesterz91.rxandroid.R;
import io.github.jesterz91.rxandroid.fragments.OnClickFragment;

public class EventActivity extends AppCompatActivity {

    private OnClickFragment onClickFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        onClickFragment = (OnClickFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

    }

}
