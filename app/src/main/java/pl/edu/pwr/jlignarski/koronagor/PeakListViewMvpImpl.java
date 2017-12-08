package pl.edu.pwr.jlignarski.koronagor;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * @author janusz on 08.12.17.
 */

class PeakListViewMvpImpl implements PeakListViewMvp {
    private final Context context;
    private View rootView;
    private RecyclerView peakListView;
    private RecyclerView.LayoutManager layoutManager;

    public PeakListViewMvpImpl(Context context, ViewGroup container) {
        this.context = context;
        rootView = LayoutInflater.from(context).inflate(R.layout.activity_peak_list, container);
        initialize();
    }

    private void initialize() {
        peakListView = rootView.findViewById(R.id.peak_list_view);
        layoutManager = new LinearLayoutManager(context);
        peakListView.setLayoutManager(layoutManager);
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        peakListView.setAdapter(adapter);
    }
}
