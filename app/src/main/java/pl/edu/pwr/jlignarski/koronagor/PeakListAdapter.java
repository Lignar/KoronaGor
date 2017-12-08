package pl.edu.pwr.jlignarski.koronagor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * @author janusz on 08.12.17.
 */

class PeakListAdapter extends RecyclerView.Adapter<PeakListAdapter.ViewHolder> {

    private final Context context;

    private final List<Peak> peakList;
    public PeakListAdapter(Context context, List<Peak> peakList) {
        this.context = context;
        this.peakList = peakList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.peak_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Peak peak = peakList.get(position);
        holder.setName(peak.getName());
        holder.setHeight(peak.getHeight());
        holder.setRange(peak.getRange());
    }

    @Override
    public int getItemCount() {
        return peakList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private static final String OVER_SEA_LEVEL = "m n.p.m.";
        private TextView range;
        private TextView height;
        private TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.peakName);
            height = itemView.findViewById(R.id.peakHeight);
            range = itemView.findViewById(R.id.peakRange);
        }

        public void setRange(String range) {
            this.range.setText(range);
        }

        @SuppressLint("SetTextI18n")
        public void setHeight(int height) {
            this.height.setText(height + OVER_SEA_LEVEL);
        }

        public void setName(String name) {
            this.name.setText(name);
        }
    }
}
