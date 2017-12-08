package pl.edu.pwr.jlignarski.koronagor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity implements MenuViewMvc.MenuViewListener {

    private MenuViewMvc menuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menuView = new MenuViewMvcImpl(getLayoutInflater(), null);
        menuView.attachListener(this);
        setContentView(menuView.getRootView());
    }

    @Override
    public void onPeakListButtonClick() {
        Intent intent = new Intent(this, PeakListActivity.class);
        startActivity(intent);
    }
}
