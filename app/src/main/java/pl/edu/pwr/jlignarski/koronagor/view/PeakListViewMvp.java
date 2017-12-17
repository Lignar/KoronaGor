package pl.edu.pwr.jlignarski.koronagor.view;

import android.support.v7.widget.RecyclerView;

import pl.edu.pwr.jlignarski.koronagor.model.Peak;

/**
 * @author janusz on 08.12.17.
 */

public interface PeakListViewMvp extends MvpView {

    interface PeakListViewListener {
        void onPeakListItemClick(Peak peak);
    }

    void setAdapter(RecyclerView.Adapter adapter);
}
