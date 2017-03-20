package com.feerlaroc.mqasho.schema.tenant.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.common.widget.CustomLinearLayout;
import com.feerlaroc.mqasho.schema.tenant.screen.TenantPropertySelectorScreen;
import com.feerlaroc.widgets.ReactiveTextView;
import com.feerlaroc.widgets.ReactiveWheelView;

import butterknife.InjectView;

public class TenantPropertySelectorView extends CustomLinearLayout<TenantPropertySelectorScreen.Presenter> {

    TenantPropertySelectorScreen.Presenter mPresenter;

    @InjectView(R.id.text_wheel_header) ReactiveTextView mWheelHeader;
    @InjectView(R.id.view_property_block_selector_placeholder) FrameLayout mBlockSelectorLayout;
    @InjectView(R.id.wheel_view_room_number_selector) ReactiveWheelView mRoomNumberSelector;

    public TenantPropertySelectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();
    }

    @Override
    public TenantPropertySelectorScreen.Presenter getPresenter() {

        return mPresenter;
    }
}
