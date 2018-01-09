package pl.edu.pwr.jlignarski.koronagor.db.firebase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import pl.edu.pwr.jlignarski.koronagor.db.realm.PeakR;
import pl.edu.pwr.jlignarski.koronagor.model.App;
import pl.edu.pwr.jlignarski.koronagor.presenter.PeakListActivity;

/**
 * @author janusz on 09.01.18.
 */

public class FirebaseRepository {
    private static OnFailureListener listener;

    public void resetAndUpdateDB(Context context, long remoteVersion) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        realm.close();
        updateDB(context, remoteVersion);
    }

    public void updateDB(Context context, long remoteVersion) {
        updateData(context);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
        preferences.edit().putLong("dbVersion", remoteVersion).apply();
    }

    private void updateImages(Context context, List<PeakR> result) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        for (PeakR peakR : result) {
            if (peakR.getMapInfo().getMapRegex() != null && !peakR.getMapInfo().getMapRegex().isEmpty()) {
                for (int i = -1; i < 6; i++) {
                    for (int j = -1; j < 6; j++) {
                        final String formattedName = String.format(peakR.getMapInfo().getMapRegex(), i, j);
                        try {
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
                                        }
                                    });
                        } catch (Exception ignored) {

                        }
                    }
                }
            }
        }
        Intent intent = new Intent(context, PeakListActivity.class);
        context.startActivity(intent);
    }

    private void updateData(final Context context) {
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
                updateImages(context, result);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
