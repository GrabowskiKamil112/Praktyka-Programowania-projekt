package pp.projekt;

import javafx.application.Application;
import javafx.stage.Stage;
import pp.projekt.view.Navigator;
import pp.projekt.view.fileToPdfConverter.FileToPdfConverterFactory;

public class FxApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Navigator navigator = new Navigator(
            primaryStage,
            new FileToPdfConverterFactory()
        );

        navigator.showMainWindow();
    }
}
