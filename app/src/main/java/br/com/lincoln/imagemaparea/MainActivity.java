package br.com.lincoln.imagemaparea;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.lincoln.imagemaparealib.ImageMapAreaView;

/**
 * Created by lincoln on 01/08/15.
 */
public class MainActivity extends Activity {

    private ImageMapAreaView imageMapAreaView;
    private String[] coordinates = {"362,77,155,64,125,39,122,20,116,9,116,4,114,0,110,0,110,3,107,11,108,28,102,45,4,43,3,80,0,84,3,92,3,107,31,108,49,114,53,120,51,130,53,143,56,165,34,196,37,200,40,219,42,223,44,226,44,243,38,244,38,248,44,253,47,275,106,276,105,284,102,288,104,308,113,315,122,318,132,325,142,326,148,317,151,314,159,311,166,308,183,308,195,317,203,320,222,319,222,318,227,321,240,320,246,315,252,314,255,316,247,313,255,320,257,321,256,331,258,335,269,335,269,326,267,322,268,309,292,275,312,261,321,245,335,235,344,199,351,187,352,171,350,165,350,115"};
    private List<Coordinate> list = new ArrayList<Coordinate>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageMapAreaView = (ImageMapAreaView) findViewById(R.id.image);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.regiaocentrooeste);
        imageMapAreaView.addImage(bitmap);

        long count = 1;
        for (String string : coordinates) {
            Coordinate obj = new Coordinate();
            obj.setId(count);
            obj.setName("Mato Grosso");
            obj.setCoord(string);
            list.add(obj);
            imageMapAreaView.addArea(count, string);
            count++;
        }

        imageMapAreaView.setTouchListener(new ImageMapAreaView.OnAreaTouchListener() {
            @Override
            public void onTouch(float x, float y) {
                Long idArea = imageMapAreaView.showArea(x, y);
                Log.d("ImageMapArea", ">>> foi");
                Log.d("ImageMapArea", "ID is " + idArea);
            }
        });
    }

    private class Coordinate{

        private Long id;
        private String name;
        private String coord;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCoord() {
            return coord;
        }

        public void setCoord(String coord) {
            this.coord = coord;
        }
    }
}
