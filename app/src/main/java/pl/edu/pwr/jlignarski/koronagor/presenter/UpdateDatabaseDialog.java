package pl.edu.pwr.jlignarski.koronagor.presenter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import pl.edu.pwr.jlignarski.koronagor.R;

/**
 * @author janusz on 17.12.17.
 */

public class UpdateDatabaseDialog extends DialogFragment {

    private static final String DB_UPDATE_MESSAGE = "Dostępna jest aktualizacja bazy danych!\nCzy chcesz pobrać ją teraz?";
    private static final String OK = "OK";
    private static final String CANCEL = "Anuluj";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(DB_UPDATE_MESSAGE)
                .setPositiveButton(OK, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNegativeButton(CANCEL, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();

    }
}
