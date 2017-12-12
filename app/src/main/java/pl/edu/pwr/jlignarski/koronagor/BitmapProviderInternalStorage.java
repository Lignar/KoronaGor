package pl.edu.pwr.jlignarski.koronagor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.qozix.tileview.graphics.BitmapProvider;
import com.qozix.tileview.tiles.Tile;

import java.io.InputStream;

/**
 * @author janusz on 12.12.17.
 */

public class BitmapProviderInternalStorage implements BitmapProvider {

    private static final BitmapFactory.Options FACTORY_OPTIONS = new BitmapFactory.Options();
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
            try {
                InputStream inputStream = context.openFileInput(tileName);
                if( inputStream != null ) {
                    try {
                        result = BitmapFactory.decodeStream( inputStream, null, FACTORY_OPTIONS);
                    } catch( OutOfMemoryError | Exception e ) {
                        e.printStackTrace();
                    }
                }
            } catch( Exception e ) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
