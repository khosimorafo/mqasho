package com.feerlaroc.mqasho.rx;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.feerlaroc.widgets.view.FeerlarocMultiStateToggleButton;
import com.feerlaroc.widgets.view.FeerlarocToggleButton;

import rx.Observable;
import rx.subjects.PublishSubject;

public class RxHelper {

    public static Observable<String> getTextWatcherObservable(@NonNull final EditText editText) {

        final PublishSubject<String> subject = PublishSubject.create();
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                subject.onNext(s.toString());
            }
        });

        return subject;
    }

    public static Observable<Integer> getToggleWatcherObservable(@NonNull final FeerlarocMultiStateToggleButton button){

        final PublishSubject<Integer> subject = PublishSubject.create();

        button.setOnValueChangedListener(new FeerlarocToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {

                subject.onNext(value);
            }
        });

        return subject;
    }
}
