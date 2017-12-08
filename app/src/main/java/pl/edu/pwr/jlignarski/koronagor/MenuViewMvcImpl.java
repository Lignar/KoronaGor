package pl.edu.pwr.jlignarski.koronagor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * @author janusz on 08.12.17.
 */

class MenuViewMvcImpl implements MenuViewMvc {

    private View rootView;
    private Button peakListButton;
    private MenuViewListener actionListener;

    public MenuViewMvcImpl(LayoutInflater inflater, ViewGroup container) {
        this.rootView = inflater.inflate(R.layout.activity_menu, container);
        initialize();
    }

    private void initialize() {
        peakListButton = rootView.findViewById(R.id.peak_list_button);
        createListeners();
    }

    private void createListeners() {
        peakListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionListener.onPeakListButtonClick();
            }
        });
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void attachListener(MenuViewListener listener) {
        actionListener = listener;
    }
}
