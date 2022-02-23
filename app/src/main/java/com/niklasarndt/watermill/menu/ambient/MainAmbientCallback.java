package com.niklasarndt.watermill.menu.ambient;

import android.os.Bundle;

import androidx.wear.ambient.AmbientModeSupport;

import com.niklasarndt.watermill.MainActivity;

public class MainAmbientCallback extends AmbientModeSupport.AmbientCallback {

    private final MainActivity activity;

    public MainAmbientCallback(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);

        activity.enterAmbient();
    }

    @Override
    public void onExitAmbient() {
        super.onExitAmbient();

        activity.exitAmbient();
    }
}
