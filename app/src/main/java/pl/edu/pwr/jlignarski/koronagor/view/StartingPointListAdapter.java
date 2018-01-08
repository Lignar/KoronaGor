package pl.edu.pwr.jlignarski.koronagor.view;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pl.edu.pwr.jlignarski.koronagor.R;
import pl.edu.pwr.jlignarski.koronagor.model.StartingPoint;

/**
 * @author janusz on 08.12.17.
 */

public class StartingPointListAdapter extends RecyclerView.Adapter<StartingPointListAdapter.ViewHolder> {

    private final List<StartingPoint> startingPoints;

    public StartingPointListAdapter(List<StartingPoint> startingPoints) {
        this.startingPoints = startingPoints;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.starting_point_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StartingPoint startingPoint = startingPoints.get(position);
        holder.setName(startingPoint.getName());
        holder.setRequiredTime(startingPoint.getRequiredTime());
    }

    @Override
    public int getItemCount() {
        return startingPoints.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView requiredTime;
        private TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.startingPointName);
            requiredTime = itemView.findViewById(R.id.startingPointTime);
        }

        public void setRequiredTime(String height) {
            this.requiredTime.setText(height);
        }

        public void setName(String name) {
            this.name.setText(name);
        }

    }
}
