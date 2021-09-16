package com.example.buildingmanagement

import android.app.Activity
import android.app.Application
import android.os.Bundle

class AppInBackgroundHandler : Application.ActivityLifecycleCallbacks {
    private var activeActivities = 0
    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {}
    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityResumed(activity: Activity) {
        activeActivities++
    }

    override fun onActivityPaused(activity: Activity) {
        activeActivities--
    }

    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}
    fun isAppInBackground(): Boolean { return activeActivities == 0 }
}