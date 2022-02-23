package com.niklasarndt.watermill.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.niklasarndt.watermill.R;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AddWaterListAdapter extends RecyclerView.Adapter<AddWaterListAdapter.ItemHolder> {
    private final Consumer<AddWaterEntry> onClick;
    private List<AddWaterEntry> data = new ArrayList<>();
    private Context context;

    public AddWaterListAdapter(Context context, Consumer<AddWaterEntry> onClick) {
        this.context = context;
        this.onClick = onClick;
    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_water_item, parent, false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        AddWaterEntry entry = isValidIndex(position) ? data.get(position) : null;

        holder.bind(entry);
        holder.container.setOnClickListener(v -> {
            entry.index = holder.getBindingAdapterPosition();
            if (onClick != null) onClick.accept(entry);
        });
    }

    private boolean isValidIndex(int position) {
        return position >= 0 && position < getItemCount();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void add(int amount) {
        data.add(new AddWaterEntry(amount));
        notifyItemInserted(data.size() - 1);
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        TextView itemText;
        LinearLayout container;

        public ItemHolder(View view) {
            super(view);
            itemText = view.findViewById(R.id.addWaterText);
            container = view.findViewById(R.id.addWaterContainer);
        }

        public void bind(AddWaterEntry entry) {
            itemText.setText(entry.getMl() + "");
        }
    }

    public static class AddWaterEntry {
        public int index = 0;
        private final int ml;

        AddWaterEntry(int ml) {
            this.ml = ml;
        }

        public int getMl() {
            return ml;
        }
    }
}
