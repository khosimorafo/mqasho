package com.feerlaroc.mqasho.schema.tenant.screen;

import com.feerlaroc.mqasho.ActivityModule;
import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.common.flow.Layout;
import com.feerlaroc.mqasho.common.mortarscreen.WithModule;
import com.feerlaroc.mqasho.schema.tenant.view.TenantPropertySelectorView;

import javax.inject.Singleton;

import flow.path.Path;
import mortar.ViewPresenter;

@Layout(R.layout.layout_tenant_property_selector)
@WithModule(TenantPropertySelectorScreen.Module.class)
public class TenantPropertySelectorScreen extends Path {

    @dagger.Module(injects = {TenantPropertySelectorView.class}, addsTo = ActivityModule.class)
    public class Module {}

    @Singleton
    public static class Presenter extends ViewPresenter<TenantPropertySelectorView> {


    }
}
