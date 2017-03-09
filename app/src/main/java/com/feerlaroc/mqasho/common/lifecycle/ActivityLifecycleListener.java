package com.feerlaroc.mqasho.common.lifecycle;

import android.content.Intent;

/**
 * Listen to lifecycle events.
 */
public interface ActivityLifecycleListener {

  void onActivityResume();

  void onActivityPause();

  void onActivityStart();

  void onActivityStop();

  void onLowMemory();

  void onActivityResult(int requestCode, int resultCode, Intent data);
}
