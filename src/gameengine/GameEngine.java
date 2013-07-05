/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;



import java.util.HashMap;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
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
        
        Canvas canvas = new Canvas(800,600);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        
        initGC(gc);
        
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        
        primaryStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent t) {
                if(dX.get(t.getCode()) != null){
                    x += dX.get(t.getCode());
                    y += dY.get(t.getCode());
                }
                
                updateGraphics(gc);
            }
            
        });
        
        primaryStage.show();
    }
    
    private void initGC(GraphicsContext gc){
        gc.setFill(Color.GRAY); 
        gc.setStroke(Color.RED);
        gc.setLineWidth(5);
        
        x = 20;
        y = 20;
        
        updateGraphics(gc);
    }
    
    private void updateGraphics(GraphicsContext gc){
        gc.clearRect(0, 0, 800, 600);
        gc.strokeLine(x, y, x+1, y+1); // Een Lijn met lengte sqrt(2)
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
