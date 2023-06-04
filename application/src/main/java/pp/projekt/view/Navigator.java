package pp.projekt.view;

import javafx.stage.Stage;
import pp.projekt.view.fileToPdfConverter.FileToPdfConverterModel;
import pp.projekt.view.fileToPdfConverter.FileToPdfConverterView;

public class Navigator {
    private final Stage primaryStage;

    public Navigator(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showWindow() {
        new FileToPdfConverterView(primaryStage, new FileToPdfConverterModel());
    }
}
