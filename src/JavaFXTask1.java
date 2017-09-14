import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.Random;

public class JavaFXTask1 extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        Random random = new Random();

        final Button singleThread = new Button("Single Thread");

        singleThread.setTranslateX(10);
        singleThread.setTranslateY(10);

        Rectangle rectangle = new Rectangle(random.nextInt(300),random.nextInt(300),random.nextInt(300),random.nextInt(100));
        rectangle.setTranslateX(20);
        rectangle.setTranslateY(10);

        root.getChildren().addAll(singleThread);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setWidth(400);
        primaryStage.setHeight(400);

        final Thread thread = new Thread(() -> {
            while(true) {
                final double x = rectangle.getTranslateX() + 2;
                System.out.println("х=" + x);

                Platform.runLater(() -> {
                    rectangle.setTranslateX(x);
                });

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        singleThread.setOnMouseClicked(event -> {
            thread.start();
        });
    }
}


