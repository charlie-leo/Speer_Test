package com.speer.test

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Charles Raj I on 05/06/23.
 * @author Charles Raj I
 */

@HiltAndroidApp
class SpeerApplication: Application() {


    override fun registerActivityLifecycleCallbacks(callback: ActivityLifecycleCallbacks?) {
        super.registerActivityLifecycleCallbacks(callback)
    }

    override fun unregisterActivityLifecycleCallbacks(callback: ActivityLifecycleCallbacks?) {
        super.unregisterActivityLifecycleCallbacks(callback)
    }

}