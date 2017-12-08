package pl.edu.pwr.jlignarski.koronagor;

/**
 * @author janusz on 08.12.17.
 */

public interface MenuViewMvc extends MvcView {
    interface MenuViewListener {

        void onPeakListButtonClick();
    }

    void attachListener(MenuViewListener listener);
}
