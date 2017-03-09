package com.feerlaroc.mqasho;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.feerlaroc.mqasho.application.AppDataHolder;
import com.feerlaroc.mqasho.common.actionbar.ActionBarOwner;
import com.feerlaroc.mqasho.common.dagger.ObjectGraphService;
import com.feerlaroc.mqasho.common.flow.FlowHistoryDevHelper;
import com.feerlaroc.mqasho.common.flow.GsonParceler;
import com.feerlaroc.mqasho.common.flow.HandlesBack;
import com.feerlaroc.mqasho.common.lifecycle.LifecycleActivity;
import com.feerlaroc.mqasho.common.lifecycle.LifecycleOwner;
import com.feerlaroc.mqasho.schema.start.screen.AppStartScreen;
import com.google.gson.Gson;

import javax.inject.Inject;

import flow.Flow;
import flow.FlowDelegate;
import flow.History;
import flow.path.Path;
import flow.path.PathContainerView;
import mortar.MortarScope;
import mortar.MortarScopeDevHelper;
import mortar.bundler.BundleServiceRunner;

import static android.view.MenuItem.SHOW_AS_ACTION_ALWAYS;
import static mortar.bundler.BundleServiceRunner.getBundleServiceRunner;

public class InvoicesAppActivity extends LifecycleActivity
        implements ActionBarOwner.Activity, Flow.Dispatcher {

    private MortarScope activityScope;
    private MortarScope wizardScope;

    private ActionBarOwner.MenuAction actionBarMenuAction;

    @Inject
    ActionBarOwner actionBarOwner;

    @Inject
    LifecycleOwner lifecycleOwner;

    private PathContainerView container;
    private HandlesBack containerAsHandlesBack;
    private FlowDelegate flowDelegate;

    @Override
    public LifecycleOwner getLifecycleOwner() {
        return lifecycleOwner;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void dispatch(Flow.Traversal traversal, Flow.TraversalCallback callback) {
        Path newScreen = traversal.destination.top();
        String title = newScreen.getClass().getSimpleName();

        actionBarOwner.setConfig(
                new ActionBarOwner.Config(false, !(newScreen instanceof AppStartScreen), title, null));

        container.dispatch(traversal, callback);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GsonParceler parceler = new GsonParceler(new Gson());
        @SuppressWarnings("deprecation") FlowDelegate.NonConfigurationInstance nonConfig =
                (FlowDelegate.NonConfigurationInstance) getLastNonConfigurationInstance();

        MortarScope parentScope = MortarScope.getScope(getApplication());

        String scopeName = getLocalClassName() + "-task-" + getTaskId() + "-" + hashCode();

        activityScope = parentScope.findChild(scopeName);
        if (activityScope == null) {
            activityScope = parentScope.buildChild()
                    .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                    .withService(
                            ObjectGraphService.SERVICE_NAME,
                            ObjectGraphService.create(parentScope, new ActivityModule(this)))
                    .build(scopeName);
        }
        ObjectGraphService.inject(this, this);

        getBundleServiceRunner(activityScope).onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.toolbar, null);

        Toolbar mToolbar = new Toolbar(this);

        setSupportActionBar(mToolbar);

        actionBarOwner.takeView(this);
        setContentView(R.layout.root_layout);

        container = (PathContainerView) findViewById(R.id.container);
        containerAsHandlesBack = (HandlesBack) container;
        flowDelegate = FlowDelegate.onCreate(
                nonConfig,
                getIntent(),
                savedInstanceState,
                parceler,
                History.single(new AppStartScreen()), this);




    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        //getMenuInflater().inflate(R.menu.customers_toolbar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {

        AppDataHolder.getInstance().setActivityContext(this);
        AppDataHolder.getInstance().setWindowManager(getWindowManager());
        AppDataHolder.getInstance().setFragmentManager(getFragmentManager());



        return super.onCreateView(name, context, attrs);
    }

    public void addWizardScope() {

        if (activityScope == null) {
            return;
        }
        wizardScope = activityScope.findChild("WizardScope");
        if (wizardScope != null && !wizardScope.isDestroyed()) {
            return;
        }

        wizardScope = activityScope.buildChild()
                .withService(
                        ObjectGraphService.SERVICE_NAME,
                        ObjectGraphService.create(activityScope, new WizardModule()))
                .build("WizardScope");
    }

    public void removeWizardScope() {

        if (wizardScope == null || wizardScope.isDestroyed()) {
            wizardScope = null;
            return;
        }
        wizardScope.destroy();
        wizardScope = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        flowDelegate.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        flowDelegate.onResume();
    }

    @Override
    protected void onPause() {
        flowDelegate.onPause();
        super.onPause();
    }

    /*
      @SuppressWarnings("deprecation") // https://code.google.com/p/android/issues/detail?id=151346
      @Override
      public Object onRetainNonConfigurationInstance() {
        return flowDelegate.onRetainNonConfigurationInstance();
      }
    */

    @Override
    public Object getSystemService(String name) {
        if (flowDelegate != null) {
            Object flowService = flowDelegate.getSystemService(name);
            if (flowService != null) return flowService;
        }

        if (wizardScope != null && wizardScope.hasService(name)) {
            return wizardScope.getService(name);
        }

        return activityScope != null && activityScope.hasService(name) ? activityScope.getService(name)
                : super.getSystemService(name);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        flowDelegate.onSaveInstanceState(outState);
        getBundleServiceRunner(this).
                onSaveInstanceState(outState);
    }

    /**
     * Inform the view about back events.
     */
    @Override
    public void onBackPressed() {
        if (!containerAsHandlesBack.onBackPressed()) super.onBackPressed();
    }

    /**
     * Inform the view about up events.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) return containerAsHandlesBack.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    /**
     * Configure the action bar menu as required by {@link ActionBarOwner.Activity}.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (actionBarMenuAction != null) {
            menu.add(actionBarMenuAction.title)
                    .setShowAsActionFlags(SHOW_AS_ACTION_ALWAYS)
                    .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            actionBarMenuAction.action.call();
                            return true;
                        }
                    });
        }
        menu.add("Log Scope Hierarchy")
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Log.d(
                                InvoicesAppActivity.class.getSimpleName(),
                                MortarScopeDevHelper.scopeHierarchyToString(activityScope));
                        return true;
                    }
                });
        menu.add("Log Flow History")
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Log.d(
                                InvoicesAppActivity.class.getSimpleName(),
                                FlowHistoryDevHelper.flowHistoryToString(Flow.get(getContext()).getHistory()));
                        return true;
                    }
                });
        return true;
    }

    @Override
    protected void onDestroy() {
        actionBarOwner.dropView(this);
        actionBarOwner.setConfig(null);

        // activityScope may be null in case isWrongInstance() returned true in onCreate()
        if (isFinishing() && activityScope != null) {
            activityScope.destroy();
            activityScope = null;
        }

        super.onDestroy();
    }

    @Override
    public void setShowHomeEnabled(boolean enabled) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
    }

    @Override
    public void setUpButtonEnabled(boolean enabled) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(enabled);
        actionBar.setHomeButtonEnabled(enabled);
    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void setMenu(ActionBarOwner.MenuAction action) {
        if (action != actionBarMenuAction) {
            actionBarMenuAction = action;
            invalidateOptionsMenu();
        }
    }
}
