package pl.edu.pwr.jlignarski.koronagor.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import io.realm.RealmResults;
import pl.edu.pwr.jlignarski.koronagor.db.firebase.PeakF;
import pl.edu.pwr.jlignarski.koronagor.db.realm.PeakR;
import pl.edu.pwr.jlignarski.koronagor.db.realm.RealmSystemRepository;
import pl.edu.pwr.jlignarski.koronagor.view.MenuViewMvp;
import pl.edu.pwr.jlignarski.koronagor.view.MenuViewMvpImpl;
import pl.edu.pwr.jlignarski.koronagor.db.RepositoryDelegate;

public class MenuActivity extends AppCompatActivity implements MenuViewMvp.MenuViewListener {

    private static final String DB_UPDATE_MESSAGE = "Dostępna jest aktualizacja danych!\nCzy chcesz pobrać ją teraz?";
    private static final String OK = "OK";
    private static final String CANCEL = "Anuluj";
    private static final String DB_UPDATE_TITLE = "Nowa wersja bazy danych";
    private MenuViewMvp menuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menuView = new MenuViewMvpImpl(getLayoutInflater(), null);
        menuView.attachListener(this);
        setContentView(menuView.getRootView());
    }

    @Override
    public void onPeakListButtonClick() {
        Intent intent = new Intent(this, PeakListActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadAssets() {
        RepositoryDelegate.getSystemRepo().loadMapsToStorage();
    }

    @Override
    public void updateDatabase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference peaks = databaseReference.child("peaks");
        List<PeakF> allPeaks = RealmSystemRepository.getAllFirebasePeaks();
        for (PeakF peak : allPeaks) {
            peaks.child(String.valueOf(peak.getId())).setValue(peak);
        }
    }

//    @Override
//    public void updateDatabase() {
//        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
//        StorageReference child = storageReference.child("rysy_tile_-2_-2.png");
//        child.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                try {
//                    FileOutputStream fos = App.getAppContext().openFileOutput("rysy_tile_-2_-2.png", Context.MODE_PRIVATE);
//                    fos.write(bytes);
//                    fos.flush();
//                    fos.close();
//                } catch (java.io.IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//    }


//    @Override
//    public void updateDatabase() {
//        RealmSystemRepository.getInstance().migrateFromStatic();
//    }

//    @Override
//    public void updateDatabase() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(DB_UPDATE_TITLE);
//        builder.setMessage(DB_UPDATE_MESSAGE)
//                .setPositiveButton(OK, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                    }
//                })
//                .setNegativeButton(CANCEL, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                    }
//                });
//        builder.create().show();
//    }
}
