package com.leo.demo;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by ZengLei on 2019/7/10.
 */
public class DebugTools {

    public static void init(Application application) {
        if (!LeakCanary.isInAnalyzerProcess(application)) {
            LeakCanary.install(application);
        }

        Stetho.initialize(Stetho.newInitializerBuilder(application)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(application))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(application))
                .build());
    }
}
