package pl.edu.pwr.jlignarski.koronagor.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.qozix.tileview.graphics.BitmapProvider;
import com.qozix.tileview.tiles.Tile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author janusz on 12.12.17.
 */

public class BitmapProviderInternalStorage implements BitmapProvider {

    private static final BitmapFactory.Options FACTORY_OPTIONS = new BitmapFactory.Options();
    private static final File SYSTEM_IMAGE_STORAGE = App.getAppContext().getDir("firebase_storage", Context.MODE_PRIVATE);
    static {
        FACTORY_OPTIONS.inPreferredConfig = Bitmap.Config.RGB_565;
    }

    @Override
    public Bitmap getBitmap(Tile tile, Context context ) {
        Object data = tile.getData();
        Bitmap result = null;
        if( data instanceof Peak ) {
            String mapRegex = ((Peak) data).getMapRegex();
            String tileName = String.format(mapRegex, tile.getColumn(), tile.getRow() );
            result = getBitmap(context, tileName);
        }
        return result;
    }

    public static Bitmap getBitmap(Context context, String fileName) {
        Bitmap result = null;
        try {
            File bitmapFile = new File(SYSTEM_IMAGE_STORAGE, fileName);
            if( bitmapFile.exists()) {
                InputStream inputStream = new FileInputStream(bitmapFile);
                try {
                    result = BitmapFactory.decodeStream( inputStream, null, FACTORY_OPTIONS);
                } catch( OutOfMemoryError | Exception e ) {
                    e.printStackTrace();
                }
            }
        } catch( Exception e ) {
            e.printStackTrace();
        }
        return result;
    }
    public static Bitmap getUserBitmap(Context context, String fileName) {
        Bitmap result = null;
        try {
            InputStream inputStream = context.openFileInput(fileName);
            if( inputStream != null ) {
                try {
                    result = BitmapFactory.decodeStream( inputStream, null, FACTORY_OPTIONS);
                    inputStream.close();
                } catch( OutOfMemoryError | Exception e ) {
                    e.printStackTrace();
                }
            }
        } catch( Exception e ) {
            e.printStackTrace();
        }
        return result;
    }

    public static void saveBitmap(Bitmap bitmap, String formattedFileName) {
        FileOutputStream fos = null;
        try {
            fos = App.getAppContext().openFileOutput(formattedFileName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, fos);
            fos.flush();
            fos.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
