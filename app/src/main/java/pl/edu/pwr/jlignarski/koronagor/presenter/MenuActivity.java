package pl.edu.pwr.jlignarski.koronagor.presenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pl.edu.pwr.jlignarski.koronagor.db.firebase.FirebaseRepository;
import pl.edu.pwr.jlignarski.koronagor.model.App;
import pl.edu.pwr.jlignarski.koronagor.view.MenuViewMvp;
import pl.edu.pwr.jlignarski.koronagor.view.MenuViewMvpImpl;

public class MenuActivity extends AppCompatActivity {

    private static final String DB_UPDATE_MESSAGE = "Dostępna jest aktualizacja danych!\nCzy chcesz pobrać ją teraz?";
    private static final String OK = "OK";
    private static final String CANCEL = "Anuluj";
    private static final String DB_UPDATE_TITLE = "Nowa wersja bazy danych";
    private static final int TIMEOUT = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MenuViewMvp menuView = new MenuViewMvpImpl(getLayoutInflater(), null);
        setContentView(menuView.getRootView());
        checkForUpdates();
    }

    private void checkForUpdates() {

        Thread thread = new Thread() {
            boolean connected = false;
            boolean skipped = false;

            @Override
            public void run() {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
                final long dbVersion = preferences.getLong("dbVersion", 0);
                FirebaseDatabase.getInstance().getReference().child("version").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!skipped) {
                            connected = true;
                            long remoteVersion = (long) dataSnapshot.getValue();
                            if (remoteVersion != dbVersion) {
                                showUpdateDialog(remoteVersion);
                            } else {
                                Intent intent = new Intent(App.getAppContext(), PeakListActivity.class);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        if (!skipped) {
                            connected = true;
                            Intent intent = new Intent(App.getAppContext(), PeakListActivity.class);
                            startActivity(intent);
                        }
                    }
                });
                try {
                    sleep(TIMEOUT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!connected) {
                    skipped = true;
                    Intent intent = new Intent(App.getAppContext(), PeakListActivity.class);
                    startActivity(intent);
                }
            }
        };
        thread.start();
    }

//    @Override
//    public void updateDatabase() {
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference peaks = databaseReference.child("peaks");
//        List<PeakF> allPeaks = RealmSystemRepository.getAllFirebasePeaks();
//        for (PeakF peak : allPeaks) {
//            peaks.child(String.valueOf(peak.getId())).setValue(peak);
//        }
//    }

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

            public void showUpdateDialog(final long remoteVersion) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(DB_UPDATE_TITLE);
                builder.setMessage(DB_UPDATE_MESSAGE)
                        .setPositiveButton(OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                new FirebaseRepository().updateDB(MenuActivity.this, remoteVersion);
                            }
                        })
                        .setNegativeButton(CANCEL, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(App.getAppContext(), PeakListActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNeutralButton("Reset", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new FirebaseRepository().resetAndUpdateDB(MenuActivity.this, remoteVersion);
                            }
                        });
                builder.create().show();
            }
        }
