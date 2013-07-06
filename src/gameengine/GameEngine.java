package gameengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author Sebastiaan
 */
public class GameEngine extends Application {
    
    private double x;
    private double y;
    
    private static final HashMap<KeyCode,Double> dX;
    private static final HashMap<KeyCode,Double> dY;
    private static final double OFFSET = 10;
    
    private List<Rectangle> rectangles = new ArrayList<>();
    private boolean bgDrawn = false;
    
    static {
        dX = new HashMap<>();
        dY = new HashMap<>();
        
        dX.put(KeyCode.RIGHT, OFFSET);
        dY.put(KeyCode.RIGHT, 0.0);
        
        dX.put(KeyCode.LEFT, -OFFSET);
        dY.put(KeyCode.LEFT, 0.0);
        
        dX.put(KeyCode.UP, 0.0);
        dY.put(KeyCode.UP, -OFFSET);
        
        dX.put(KeyCode.DOWN, 0.0);
        dY.put(KeyCode.DOWN, OFFSET);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Game Engine Test");
        Group root = new Group();
        
        Canvas canvas = new Canvas(1024,768);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        
        initGC(gc);
        
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        
        /*
         * Test code
         */
        rectangles.add(new Rectangle(40, 100, 50, 200));
        rectangles.add(new Rectangle(300,200,420,230));
        
        primaryStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent t) {
                
                gc.clearRect(x-3, y-3, 8, 8);
                
                double nx = x;
                double ny = y;
                
                if(dX.get(t.getCode()) != null){
                    nx += dX.get(t.getCode());
                    ny += dY.get(t.getCode());
                }
                
                if (!pointCollides(nx, ny)){
                    x = nx;
                    y = ny;
                }
                
                updateGraphics(gc);
            }
            
        });
        
        bgDrawn = false;
        updateGraphics(gc);
        
        primaryStage.show();
    }
    
    private void initGC(GraphicsContext gc){
        gc.setFill(Color.GRAY); 
        gc.setStroke(Color.RED);
        gc.setLineWidth(5);
        
        x = 10;
        y = 10;
        
        updateGraphics(gc);
    }
    
    private void updateGraphics(GraphicsContext gc){
        gc.strokeLine(x, y, x+1, y+1); // Een Lijn met lengte sqrt(2)
        
        if(!bgDrawn){
            for (Rectangle rect : rectangles){
                System.out.println("redrawing background");
                gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
            }
            
            bgDrawn = true;
        }
    }
    
    private boolean pointCollides(double x, double y){
        
        for (Rectangle rect : rectangles){
            if (x <= rect.getX()+rect.getWidth() && x >= rect.getX() &&
                    y <= rect.getY()+rect.getHeight() && y >= rect.getY()){
                return true;
            }
            
            if (x >= 1024 || y >= 768 || x <= 0 || y <= 0){
                return true;
            }
        }
        
        return false;
        
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
