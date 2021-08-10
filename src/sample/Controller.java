package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import sample.core.Core;

public class Controller {

    @FXML
    private Button startButton;
    @FXML
    private TextArea logTestArea;
    @FXML
    private Button loginLink;
    @FXML
    private Button quitButton;

    Core core = new Core();

    @FXML
    void initialize(){
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Java\\driver\\chromedriver.exe ");
        loginLink.setOnAction(action -> { core.openSecond(); } );
        startButton.setOnAction(action -> { core.openShiply(); } ); }
}
