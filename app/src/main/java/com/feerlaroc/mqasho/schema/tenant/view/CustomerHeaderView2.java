package com.feerlaroc.mqasho.schema.tenant.view;

import android.content.Context;
import android.util.AttributeSet;

import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.common.widget.CustomRelativeLayout;
import com.feerlaroc.mqasho.schema.tenant.screen.CustomerHeaderScreen2;
import com.feerlaroc.widgets.ReactiveTextView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;


public class CustomerHeaderView2 extends CustomRelativeLayout<CustomerHeaderScreen2.Presenter> {

    private static final String TAG = "CustomerHeaderView";

    @Inject CustomerHeaderScreen2.Presenter mPresenter;


    @InjectView(R.id.circle_image_customer)
    CircleImageView mCustomerImageView;

    @InjectView(R.id.text_customer_name)
    ReactiveTextView mTextCustomerName;

    public CustomerHeaderView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();
        ButterKnife.inject(this);
    }

    public ReactiveTextView getCustomerName(){

        return mTextCustomerName;
    }

    @Override
    public CustomerHeaderScreen2.Presenter getPresenter() {

        return mPresenter;
    }
}
