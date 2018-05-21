import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Runner extends Application{

    @Override
    public void start(Stage primaryStage){

        Button btn = new Button("Learn Deeply");


        Image example = null;

        MNIST mnist = new MNIST();

        Canvas canvas = new Canvas(200, 200);

        // Get the first image from the training data set
        example = mnist.getExampleImage();

        // Get the graphics context of the canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(example, 0, 0, 200, 200);

        StackPane root = new StackPane();
        root.getChildren().add(canvas);
        root.getChildren().add(btn);
        btn.setTranslateY(115);

        Scene scene = new Scene(root, 260, 260);

        primaryStage.getIcons().add(new Image("deep_learning_icon.png"));

        primaryStage.setTitle("MNIST DL");
        primaryStage.setScene(scene);
        primaryStage.show();

        btn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){

                System.out.println("Lots of deep learning going on...");
                Image ex = mnist.getExampleImage();

                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.drawImage(ex, 0, 0, 200, 200);
            }
        });
    }

    public static void main(String[] args){
        launch(args);
    }
}
