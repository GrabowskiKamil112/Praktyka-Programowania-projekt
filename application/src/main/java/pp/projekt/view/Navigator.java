package pp.projekt.view;

import javafx.stage.Stage;
import pp.projekt.view.fileToPdfConverter.FileToPdfConverterFactory;

public class Navigator {
    private final Stage primaryStage;
    private final FileToPdfConverterFactory fileToPdfConverterFactory;

    public Navigator(Stage primaryStage, FileToPdfConverterFactory fileToPdfConverterFactory) {
        this.primaryStage = primaryStage;
        this.fileToPdfConverterFactory = fileToPdfConverterFactory;
    }

    public void showMainWindow() {
        fileToPdfConverterFactory.create(primaryStage);
    }
}
