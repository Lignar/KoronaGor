package pl.edu.pwr.jlignarski.koronagor.view;

/**
 * @author janusz on 08.12.17.
 */

public interface MenuViewMvp extends MvpView {
    interface MenuViewListener {

        void onPeakListButtonClick();

        void loadAssets();
    }

    void attachListener(MenuViewListener listener);
}
