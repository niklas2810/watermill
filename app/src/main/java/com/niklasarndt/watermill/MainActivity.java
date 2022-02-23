package com.niklasarndt.watermill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.niklasarndt.watermill.anim.ProgressBarAnimation;
import com.niklasarndt.watermill.databinding.ActivityMainBinding;
import com.niklasarndt.watermill.storage.SettingsStorage;
import com.niklasarndt.watermill.storage.WaterStorage;

import java.time.LocalDate;

public class MainActivity extends Activity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.mainAddButton.setOnClickListener(this::onAddClicked);
        binding.mainProgressPercent.setOnClickListener(this::onSettingsOpened);
        binding.mainProgressCircle.setProgress(0);
    }

    private void onSettingsOpened(View caller) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void onAddClicked(View caller) {
        Intent intent = new Intent(this, AddWaterActivity.class);
        startActivity(intent);
    }

    private void refreshUi() {
        int current = Math.max(WaterStorage.getWaterForDay(this, LocalDate.now()), 0);
        int goal = SettingsStorage.getDailyGoal(this);
        double percent = Math.max(0, Math.min(100, current * 100.0 / goal));

        binding.mainProgressExact.setText(String.format(getResources().getString(R.string.milliliter), current));
        binding.mainProgressPercent.setText(String.format(getResources().getString(R.string.percent), (int) percent));

        ProgressBarAnimation anim = new ProgressBarAnimation(binding.mainProgressCircle, binding.mainProgressCircle.getProgress(), (float) (percent * 100));
        anim.setDuration(250);
        binding.mainProgressCircle.startAnimation(anim);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshUi();
    }
}