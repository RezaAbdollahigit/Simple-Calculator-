import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        TextField inputField = new TextField();
        Button calculateButton = new Button("Calculate");

        calculateButton.setOnAction(e -> {
            String input = inputField.getText();
            try {
                Lexer lexer = new Lexer(input);
                Parser parser = new Parser(lexer);
                double result = parser.expression();
                showAlert(AlertType.INFORMATION, "Result", "Result: " + result);
            } catch (Exception ex) {
                showAlert(AlertType.ERROR, "Error", "Invalid expression");
            }
        });

        VBox vbox = new VBox(10, inputField, calculateButton);
        Scene scene = new Scene(vbox, 400, 250);

        primaryStage.setTitle("Simple Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

