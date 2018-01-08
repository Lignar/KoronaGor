package pl.edu.pwr.jlignarski.koronagor.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import pl.edu.pwr.jlignarski.koronagor.R;
import pl.edu.pwr.jlignarski.koronagor.model.Peak;

/**
 * @author janusz on 08.12.17.
 */

public class PeakListAdapter extends RecyclerView.Adapter<PeakListAdapter.ViewHolder> {

    private final Context context;
    private final List<Peak> peakList;

    public PeakListAdapter(Context context, List<Peak> peakList, PeakListViewMvp.PeakListViewListener listener) {
        this.context = context;
        this.peakList = peakList;
        ViewHolder.setListener(listener);
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
        holder.setPeakId(peak.getId());
        if (!peak.isCompleted()) {
            holder.setNotCompleted();
        }
        if (position % 2 == 1) {
            holder.setBackgroundColor(Color.LTGRAY);
        }
    }

    @Override
    public int getItemCount() {
        return peakList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private static final String OVER_SEA_LEVEL = "m n.p.m.";
        private View.OnClickListener onClickListener;
        private static PeakListViewMvp.PeakListViewListener listener;
        private View rootView;
        private TextView range;
        private TextView height;
        private TextView name;
        private CheckBox completed;
        private int peakId;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            name = itemView.findViewById(R.id.peakName);
            height = itemView.findViewById(R.id.startingPointTime);
            range = itemView.findViewById(R.id.peakRange);
            completed = itemView.findViewById(R.id.completed);
            rootView.setOnClickListener(getOnClickListener());
        }

        public static void setListener(PeakListViewMvp.PeakListViewListener listener) {
            ViewHolder.listener = listener;
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

        public void setBackgroundColor(int color) {
            rootView.setBackgroundColor(color);
        }

        public void setNotCompleted() {
            completed.setVisibility(View.GONE);
        }

        @NonNull
        private View.OnClickListener getOnClickListener() {
            if (onClickListener == null) {
                onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onPeakListItemClick(peakId);

                    }
                };
            }
            return onClickListener;
        }

        public void setPeakId(int peakId) {
            this.peakId = peakId;
        }
    }
}
