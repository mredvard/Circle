package nochesdecafeina.circle;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class CircleActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Display display = getWindowManager().getDefaultDisplay();
        Point displaySize = new Point();
        displaySize.set(display.getWidth(), display.getHeight());
        
        setContentView(new DrawView(this, displaySize));

    }
}