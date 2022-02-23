package com.niklasarndt.watermill;

import android.app.Activity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

import com.niklasarndt.watermill.databinding.ActivityAddWaterBinding;
import com.niklasarndt.watermill.menu.AddWaterListAdapter;
import com.niklasarndt.watermill.storage.MetadataStorage;
import com.niklasarndt.watermill.storage.WaterStorage;

import java.time.LocalDate;

public class AddWaterActivity extends Activity {

    private ActivityAddWaterBinding binding;
    private AddWaterListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddWaterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        WearableRecyclerView recyclerView = binding.addWaterViewList;

        recyclerView.setHasFixedSize(true);
        recyclerView.setEdgeItemsCenteringEnabled(true);
        recyclerView.setLayoutManager(new WearableLinearLayoutManager(this));

        adapter = new AddWaterListAdapter(this, this::onEntryClicked);

        adapter.add(100);
        adapter.add(200);
        adapter.add(300);
        adapter.add(500);
        adapter.add(1000);
        adapter.add(-1);

        recyclerView.setAdapter(adapter);
        new LinearSnapHelper().attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        int index = MetadataStorage.getAddScrollIndex(this);
        binding.addWaterViewList.postDelayed(() -> binding.addWaterViewList.scrollToPosition(index), 100);
    }

    private void onEntryClicked(AddWaterListAdapter.AddWaterEntry entry) {
        if (entry == null)
            return;
        if (entry.getMl() < 0)
            WaterStorage.setWaterForDay(this, LocalDate.now(), 0);
        else
            WaterStorage.addWaterForDay(this, LocalDate.now(), entry.getMl());

        MetadataStorage.setAddScrollIndex(this, entry.index);

        finish();
    }
}