package pl.edu.pwr.jlignarski.koronagor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

public class PeakListActivity extends AppCompatActivity {

    private PeakListViewMvp peakListView;
    private SystemRepository systemRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        peakListView = new PeakListViewMvpImpl(this, null);
        setContentView(peakListView.getRootView());
        systemRepo = RepositoryDelegate.getSystemRepo();
        fillPeakList();
    }

    private void fillPeakList() {
        List<Peak> peakList = systemRepo.getAllPeaks();
        PeakListAdapter adapter = new PeakListAdapter(this, peakList);
        peakListView.setAdapter(adapter);
    }
}
