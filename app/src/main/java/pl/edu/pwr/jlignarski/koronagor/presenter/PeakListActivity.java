package pl.edu.pwr.jlignarski.koronagor.presenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import pl.edu.pwr.jlignarski.koronagor.view.PeakListAdapter;
import pl.edu.pwr.jlignarski.koronagor.view.PeakListViewMvp;
import pl.edu.pwr.jlignarski.koronagor.view.PeakListViewMvpImpl;
import pl.edu.pwr.jlignarski.koronagor.model.Peak;
import pl.edu.pwr.jlignarski.koronagor.db.RepositoryDelegate;
import pl.edu.pwr.jlignarski.koronagor.db.SystemRepository;

public class PeakListActivity extends AppCompatActivity implements PeakListViewMvp.PeakListViewListener {

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
        PeakListAdapter adapter = new PeakListAdapter(this, peakList, this);
        peakListView.setAdapter(adapter);
    }

    @Override
    public void onPeakListItemClick(int peakId) {
        Intent intent = new Intent(this, PeakMapActivity.class);
        BundleKey.PEAK_ID.addToIntent(intent, peakId);
        startActivity(intent);
    }
}
