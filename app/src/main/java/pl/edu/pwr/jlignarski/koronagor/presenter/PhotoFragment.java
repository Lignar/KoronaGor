package pl.edu.pwr.jlignarski.koronagor.presenter;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import pl.edu.pwr.jlignarski.koronagor.R;

public class PhotoFragment extends Fragment {

    private Bitmap bitmap;

    public PhotoFragment() {
    }

    public static PhotoFragment newInstance(Bitmap bitmap) {
        PhotoFragment fragment = new PhotoFragment();
        fragment.setBitmap(bitmap);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photo, container, false);
        ImageView photoView = rootView.findViewById(R.id.takenPhotoView);
        photoView.setImageBitmap(bitmap);
        rootView.setBackgroundColor(Color.BLACK);
        return rootView;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
