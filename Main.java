import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class Main extends Application{

    Stage window;
    Button button;
    Scene scene, scene2;

    public static void main(String[]args){
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) {
        window=primaryStage;
        window.setTitle("MINI sample");

        Button button=new Button("sign In");
        Button button1=new Button("Sign Up");
        Button button2=new Button("   Forgot Password   ");

        //REDIRECT TO SIGN-UP
        button1.setOnAction(event -> window.setScene(scene2));

        GridPane.setConstraints(button1, 1,50);
        GridPane.setConstraints(button, 1,48 );
        GridPane.setConstraints(button2,1 ,52 );
        GridPane loginPage=new GridPane() ;

        GridPane SignUp=new GridPane();

        loginPage.setPadding(new Insets(10,10,10,10));
        loginPage.setVgap(8);
        loginPage.setHgap(10);

        SignUp.setPadding(new Insets(10,10,10,10));
        SignUp.setVgap(8);
        SignUp.setHgap(10);

        //Create username bar for login
        Label nameLabel=new Label("Username:");
        GridPane.setConstraints(nameLabel,0 ,45 );

//User type name

        TextField nameInput=new TextField();//PreSet text
        nameInput.setPromptText("Username");
        GridPane.setConstraints(nameInput,1 ,45 );

//Password bar
        Label passwordLabel=new Label("Password:");
        GridPane.setConstraints(passwordLabel,2 ,45 );

//Password type

        TextField passwordInput=new TextField();
        passwordInput.setPromptText("Password");
        GridPane.setConstraints(passwordInput, 3,45);

        Label name=new Label("Name:");
        GridPane.setConstraints(name,0 ,3 );

//User type name
        TextField insertName=new TextField();//PreSet text
        nameInput.setPromptText("Name");
        GridPane.setConstraints(insertName,1 ,3 );



        loginPage.getChildren().addAll(nameLabel,nameInput,passwordLabel,passwordInput,button,button1,button2);
        SignUp.getChildren().addAll(name,insertName);
        scene=new Scene(loginPage,500,600);
        scene2=new Scene(SignUp,500,600);

        window.setScene(scene);
        window.show();





    }
}
