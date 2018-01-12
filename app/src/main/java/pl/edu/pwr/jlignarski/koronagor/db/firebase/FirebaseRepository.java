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

import io.realm.Realm;
import pl.edu.pwr.jlignarski.koronagor.db.realm.PeakR;
import pl.edu.pwr.jlignarski.koronagor.model.App;
import pl.edu.pwr.jlignarski.koronagor.presenter.MenuActivity;

/**
 * @author janusz on 09.01.18.
 */

public class FirebaseRepository {

    private int counter;

    public void resetAndUpdateDB(MenuActivity context, long remoteVersion) {
        for (File file : App.getAppContext().getFilesDir().listFiles()) {
            file.delete();
        }
        updateDB(context, remoteVersion);
    }

    public void updateDB(MenuActivity context, long remoteVersion) {
        updateData(context, remoteVersion);
    }

    private void updateImages(final MenuActivity context, List<PeakR> result, final long remoteVersion) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        for (PeakR peakR : result) {
            if (peakR.getMapInfo().getMapRegex() != null && !peakR.getMapInfo().getMapRegex().isEmpty()) {
                for (int i = -1; i < 5; i++) {
                    for (int j = -1; j < 4; j++) {
                        modifyCounter(1);
                        final String formattedName = String.format(peakR.getMapInfo().getMapRegex(), i, j);
                        storageReference.child(formattedName).getBytes(Long.MAX_VALUE)
                                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {
                                        try {
                                            FileOutputStream fos = App.getAppContext().openFileOutput(formattedName, Context.MODE_PRIVATE);
                                            fos.write(bytes);
                                            fos.flush();
                                            fos.close();
                                        } catch (java.io.IOException e) {
                                            e.printStackTrace();
                                        }
                                        modifyCounter(-1);
                                        tryOpenList(context, remoteVersion);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                modifyCounter(-1);
                                tryOpenList(context, remoteVersion);
                            }
                        });
                    }
                }
            }
        }
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
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PeakF value = snapshot.getValue(PeakF.class);
                    result.add(new PeakR(value));
                }
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.insertOrUpdate(result);
                realm.commitTransaction();
                realm.close();
                Toast.makeText(context, "Załadowano dane, ładuję obrazy", Toast.LENGTH_LONG).show();
                updateImages(context, result, remoteVersion);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "Błąd ładowania danych", Toast.LENGTH_LONG).show();
                context.startListActivity();
            }
        });
    }
}
