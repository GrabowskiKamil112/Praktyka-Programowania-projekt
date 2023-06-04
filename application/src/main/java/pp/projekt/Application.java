package pp.projekt;

import javafx.stage.Stage;
import pp.projekt.view.Navigator;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Navigator navigator = new Navigator(primaryStage);
        navigator.showWindow();
    }
}
