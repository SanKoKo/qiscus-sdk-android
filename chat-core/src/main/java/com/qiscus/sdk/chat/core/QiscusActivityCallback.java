/*
 * Copyright (c) 2016 Qiscus.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.qiscus.sdk.chat.core;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.qiscus.sdk.chat.core.util.QiscusAndroidUtil;

import java.util.concurrent.ScheduledFuture;

/**
 * Created on : February 09, 2017
 * Author     : zetbaitsu
 * Name       : Zetra
 * GitHub     : https://github.com/zetbaitsu
 */
enum QiscusActivityCallback implements Application.ActivityLifecycleCallbacks {
    INSTANCE;

    private static final long MAX_ACTIVITY_TRANSITION_TIME = 2000;

    private ScheduledFuture<?> activityTransition;
    private boolean foreground;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        stopActivityTransitionTimer();
    }

    @Override
    public void onActivityPaused(Activity activity) {
        startActivityTransitionTimer();
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    boolean isForeground() {
        return foreground;
    }

    private void startActivityTransitionTimer() {
        activityTransition = QiscusAndroidUtil.runOnBackgroundThread(() -> foreground = false,
                MAX_ACTIVITY_TRANSITION_TIME);
    }

    private void stopActivityTransitionTimer() {
        if (activityTransition != null) {
            activityTransition.cancel(true);
        }

        foreground = true;
    }
}
