package nochesdecafeina.circle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class DrawView extends View {
	private Ball ringCircle; 	//Circulo exterior
    private Ball dragCircle; 	//Circulo interior desplazable
    private Ball dropNorth; 	//Letra Norte
    private Ball dropSouth; 	//Letra Sur
    private Ball dropWest; 		//Letra Oeste
    private Ball dropEast; 		//Letra Este
    private boolean touched = false;
    private Context cx;
    public DrawView(Context context, Point screenSize) {
        super(context);
        cx = context;
        setFocusable(true); // Necesario para responder a eventos de toque      
        
        ringCircle = new Ball(context, screenSize.x/2, screenSize.y/2, screenSize.x/2 - 40, Paint.Style.STROKE);
        dragCircle = new Ball(context, screenSize.x/2, screenSize.y/2, 20, Paint.Style.FILL);
        dropNorth = new Ball(context, ringCircle.getX() - 10, (ringCircle.getY() - ringCircle.getR() - 10), 20, Paint.Style.FILL);
        dropSouth = new Ball(context, ringCircle.getX() - 10, (ringCircle.getY() + ringCircle.getR() + 30), 20, Paint.Style.FILL);
        dropWest = new Ball(context, (ringCircle.getX() - ringCircle.getR()) - 30, ringCircle.getY() + 10, 20, Paint.Style.FILL);
        dropEast = new Ball(context, (ringCircle.getX() + ringCircle.getR() + 10), ringCircle.getY() + 10, 20, Paint.Style.FILL);
        
    }
    // Metodo que dibuja el Canvas en la pantalla	
    @Override 
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(ringCircle.getX(), ringCircle.getY(), ringCircle.getR(), ringCircle.getPaint());
        canvas.drawCircle(dragCircle.getX(), dragCircle.getY(), dragCircle.getR(), dragCircle.getPaint());
        canvas.drawText("N", dropNorth.getX(), dropNorth.getY(), dropNorth.getPaint());
        canvas.drawText("S", dropSouth.getX(), dropSouth.getY(), dropSouth.getPaint());
        canvas.drawText("W", dropWest.getX(), dropWest.getY(), dropWest.getPaint());
        canvas.drawText("E", dropEast.getX(), dropEast.getY(), dropEast.getPaint());
    }
    
    // Eventos para cuando se toca la pantalla
    
    public boolean onTouchEvent(MotionEvent event) {

        int eventaction = event.getAction(); 
        
        // Coordenadas del toque de la pantalla
        int X = (int) event.getX(); 
        int Y = (int) event.getY(); 
        
        switch (eventaction ) { 

        // Caso para cuando se produce un toque, se observa si esta dentro del radio del circulo interno
        case MotionEvent.ACTION_DOWN: 
        	int circleR = (int) dragCircle.getR();
        	int centerX = (int) dragCircle.getX();
        	int centerY = (int) dragCircle.getY();

        	double radCircle  = Math.sqrt( (double) (((centerX-X)*(centerX-X)) + (centerY-Y)*(centerY-Y)));
        	
        	if (radCircle <= circleR)
    			touched = true;
        	else 
        		touched = false;
        	break;
        	
        case MotionEvent.ACTION_MOVE:  
        	if (touched) {
        		dragCircle.setX(X);
        		dragCircle.setY(Y);
        	}
        	break;
   
        case MotionEvent.ACTION_UP:
        	double areaNorth  = Math.sqrt( (double) (((dropNorth.getX()+15-dragCircle.getX())*(dropNorth.getX()+15-dragCircle.getX())) + (dropNorth.getY()-15-dragCircle.getY())*(dropNorth.getY()-15-dragCircle.getY())));
        	double areaSouth  = Math.sqrt( (double) (((dropSouth.getX()+15-dragCircle.getX())*(dropSouth.getX()+15-dragCircle.getX())) + (dropSouth.getY()-15-dragCircle.getY())*(dropSouth.getY()-15-dragCircle.getY())));
        	double areaWest  = Math.sqrt( (double) (((dropWest.getX()+15-dragCircle.getX())*(dropWest.getX()+15-dragCircle.getX())) + (dropWest.getY()-15-dragCircle.getY())*(dropWest.getY()-15-dragCircle.getY())));
        	double areaEast  = Math.sqrt( (double) (((dropEast.getX()+15-dragCircle.getX())*(dropEast.getX()+15-dragCircle.getX())) + (dropEast.getY()-15-dragCircle.getY())*(dropEast.getY()-15-dragCircle.getY())));
        	Toast toast = Toast.makeText(cx, "Otra App que este en el telefono", Toast.LENGTH_SHORT);
        	
        	if (areaNorth <= dragCircle.getR()) {
        		String url = "http://www.nochesdecafeina.com";
            	Intent i = new Intent(Intent.ACTION_VIEW);
            	i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	i.setData(Uri.parse(url));
            	cx.startActivity(i);
        	}
        	else if (areaSouth <= dragCircle.getR()){
        		String url = "tel: *2";
            	Intent i = new Intent(Intent.ACTION_DIAL);
            	i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	i.setData(Uri.parse(url));
            	cx.startActivity(i);
        	}
        	else if (areaWest <= dragCircle.getR()){
        		String url = "geo:13.808743,-88.640442";
            	Intent i = new Intent(Intent.ACTION_VIEW);
            	i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	i.setData(Uri.parse(url));
            	cx.startActivity(i);
        	}
        	else if (areaEast <= dragCircle.getR()){
        		toast.show();
        	}
            break; 
        } 
        
        // Redibujar Canvas
        invalidate(); 
        return true;
    }
}
