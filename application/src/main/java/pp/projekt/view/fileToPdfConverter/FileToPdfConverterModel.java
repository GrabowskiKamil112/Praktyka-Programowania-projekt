package pp.projekt.view.fileToPdfConverter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.File;
import java.math.BigDecimal;

public class FileToPdfConverterModel {

    private ObjectProperty<File> selectedFile = new SimpleObjectProperty<>();
    public ObjectProperty<File> selectedFileProperty() {
        return selectedFile;
    }

    private String choosenFileType;

    final BooleanProperty isConvertButtonDisabled = new SimpleBooleanProperty(true);
    final BooleanProperty isChoosenFileTypeEmpty = new SimpleBooleanProperty(true);

    public FileToPdfConverterModel() {
        this.choosenFileType = "--wybierz--";
    }

    public File getSelectedFile() {
        return selectedFile.get();
    }

    public void setChoosenFileType(String choosenFileType) {
        boolean isChoosenFileTypeEmpty = choosenFileType.equals("--wybierz--");
        this.setSelectedFile(null);
        this.isChoosenFileTypeEmpty.set(isChoosenFileTypeEmpty);
        this.isConvertButtonDisabled.set(!(isChoosenFileTypeEmpty && this.getSelectedFile() != null));
        this.choosenFileType = choosenFileType;
    }

    public String getChoosenFileType() {
        return choosenFileType;
    }

    public void setSelectedFile(File file) {
        this.isConvertButtonDisabled.set(!true);
        selectedFile.set(file);
    }

}