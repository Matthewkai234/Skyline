package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class ForgotPasswordApp extends Application {
    @Override
    public void start(@NotNull Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ForgotPassword.fxml"));
        Parent root = loader.load();


        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/view/CreateAccount.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/view/verificationstyles.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/view/ResetPassword.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/view/ForgotPassword.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Forgot Password");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
