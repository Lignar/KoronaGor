package pl.edu.pwr.jlignarski.koronagor.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pl.edu.pwr.jlignarski.koronagor.R;

/**
 * @author janusz on 08.12.17.
 */

public class MenuViewMvpImpl implements MenuViewMvp {

    private View rootView;

    public MenuViewMvpImpl(LayoutInflater inflater, ViewGroup container) {
        this.rootView = inflater.inflate(R.layout.activity_menu, container);
    }


    @Override
    public View getRootView() {
        return rootView;
    }

}
