package pl.edu.pwr.jlignarski.koronagor;

import android.support.v7.widget.RecyclerView;

/**
 * @author janusz on 08.12.17.
 */

interface PeakListViewMvp extends MvpView {
    void setAdapter(RecyclerView.Adapter adapter);
}
