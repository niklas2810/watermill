package com.niklasarndt.watermill;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;

import com.niklasarndt.watermill.databinding.ActivitySettingsBinding;
import com.niklasarndt.watermill.storage.SettingsStorage;

public class SettingsActivity extends Activity {

    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.goalInput.setInputType(InputType.TYPE_CLASS_NUMBER);

        binding.submitButton.setOnClickListener(this::submit);
    }

    private void submit(View caller) {
        try {
            int newGoal = Integer.parseInt(binding.goalInput.getText().toString());
            if (newGoal <= 0)
                throw new IllegalArgumentException("Input must be positive");
            SettingsStorage.setDailyGoal(this, newGoal);
        } catch (Exception e) {
            binding.goalInput.setError("Number expected");
            return;
        }

        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        binding.goalInput.setText(SettingsStorage.getDailyGoal(this) + "");
    }
}