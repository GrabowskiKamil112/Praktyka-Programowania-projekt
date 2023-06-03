package pp.projekt.view.fileToPdfConverter;

import javafx.stage.Stage;

public class FileToPdfConverterFactory {
    public void create(Stage stage) {
        new FileToPdfConverterView(stage, new FileToPdfConverterModel());
    }
}
