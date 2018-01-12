package pl.edu.pwr.jlignarski.koronagor.db.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import io.realm.Realm;
import pl.edu.pwr.jlignarski.koronagor.db.realm.PeakR;
import pl.edu.pwr.jlignarski.koronagor.model.App;
import pl.edu.pwr.jlignarski.koronagor.presenter.MenuActivity;

/**
 * @author janusz on 09.01.18.
 */

public class FirebaseRepository {

    private int counter;
    private File internalStorage = App.getAppContext().getDir("firebase_storage", Context.MODE_PRIVATE);
    private Semaphore semaphore = new Semaphore(50);

    public void resetAndUpdateDB(MenuActivity context, long remoteVersion) {
        for (File file : App.getAppContext().getFilesDir().listFiles()) {
            file.delete();
        }
        updateDB(context, remoteVersion);
    }

    public void updateDB(MenuActivity context, long remoteVersion) {
        updateData(context, remoteVersion);
    }

    private void updateImages(final MenuActivity context, final List<PeakF> result, final long remoteVersion) {
        if (!internalStorage.exists()) {
            internalStorage.mkdirs();
        }
        for (File file : internalStorage.listFiles()) {
            file.delete();
        }
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        new Thread() {
            @Override
            public void run() {
                for (PeakF peakF : result) {
                    if (peakF.getMapInfo().getMapRegex() != null && !peakF.getMapInfo().getMapRegex().isEmpty()) {
                        downloadImage(context, remoteVersion, storageReference, peakF, 0, -1);
                        for (int i = 0; i <= peakF.getMapInfo().getiTiles(); i++) {
                            for (int j = 0; j <= peakF.getMapInfo().getjTiles(); j++) {
                                downloadImage(context, remoteVersion, storageReference, peakF, i, j);
                            }
                        }
                    }
                }
            }
        }.start();
    }

    private void downloadImage(final MenuActivity context, final long remoteVersion, StorageReference storageReference, PeakF peakF, int i, int j) {
        modifyCounter(1);
        final String formattedName = String.format(peakF.getMapInfo().getMapRegex(), i, j);
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        storageReference.child(formattedName).getBytes(Long.MAX_VALUE)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        try {
                            File newBitmap = new File(internalStorage, formattedName);
                            FileOutputStream fos = new FileOutputStream(newBitmap);
                            fos.write(bytes);
                            fos.flush();
                            fos.close();
                        } catch (java.io.IOException e) {
                            e.printStackTrace();
                        }
                        semaphore.release();
                        modifyCounter(-1);
                        tryOpenList(context, remoteVersion);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                semaphore.release();
                modifyCounter(-1);
                tryOpenList(context, remoteVersion);
            }
        });
    }

    private void tryOpenList(MenuActivity context, long remoteVersion) {
        if (counter == 0) {
            Toast.makeText(context, "Załadowano obrazy", Toast.LENGTH_SHORT).show();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
            preferences.edit().putLong("dbVersion", remoteVersion).apply();
            context.startListActivity();
        }
    }

    private synchronized void modifyCounter(int i) {
        counter += i;
    }

    private void updateData(final MenuActivity context, final long remoteVersion) {
        Toast.makeText(context, "Ładuję dane", Toast.LENGTH_SHORT).show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("peaks").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<PeakR> result = new ArrayList<>();
                final List<PeakF> resultF = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PeakF value = snapshot.getValue(PeakF.class);
                    resultF.add(value);
                    result.add(new PeakR(value));
                }
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.insertOrUpdate(result);
                realm.commitTransaction();
                realm.close();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
                final long imageDbVersion = preferences.getLong("dbVersion", 0);
                FirebaseDatabase.getInstance().getReference().child("imageVersion")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                long remoteImageVersion = (long) dataSnapshot.getValue();
                                if (remoteImageVersion > imageDbVersion) {
                                    Toast.makeText(context, "Załadowano dane, ładuję obrazy", Toast.LENGTH_LONG).show();
                                    updateImages(context, resultF, remoteVersion);
                                } else {
                                    Toast.makeText(context, "Załadowano dane", Toast.LENGTH_LONG).show();
                                    tryOpenList(context, remoteVersion);
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(context, "Załadowano dane", Toast.LENGTH_LONG).show();
                                tryOpenList(context, remoteVersion);
                            }
                        });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "Błąd ładowania danych", Toast.LENGTH_LONG).show();
                context.startListActivity();
            }
        });
    }
}
