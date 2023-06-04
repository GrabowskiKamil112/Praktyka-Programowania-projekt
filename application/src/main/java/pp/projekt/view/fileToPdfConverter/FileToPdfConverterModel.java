package pp.projekt.view.fileToPdfConverter;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

public class FileToPdfConverterModel {
    private ObservableList<File> selectedFiles;
    private String choosenFileType;
    private ObjectProperty<File> selectedFileProperty;

    public ObjectProperty<File> selectedFileProperty() {
        if (selectedFileProperty == null) {
            selectedFileProperty = new SimpleObjectProperty<>();
            if (!selectedFiles.isEmpty()) {
                selectedFileProperty.set(selectedFiles.get(0));
            }
        }
        return selectedFileProperty;
    }

    public FileToPdfConverterModel() {
        this.choosenFileType = "--wybierz--";
        this.selectedFiles = FXCollections.observableArrayList();
    }

    final BooleanProperty isConvertButtonDisabled = new SimpleBooleanProperty(true);
    final BooleanProperty isChoosenFileTypeEmpty = new SimpleBooleanProperty(true);

    public void setChoosenFileType(String choosenFileType) {
        boolean isChoosenFileTypeEmpty = choosenFileType.equals("--wybierz--");
        if(isChoosenFileTypeEmpty){
            this.resetSelectedFiles();
        }
        this.isChoosenFileTypeEmpty.set(isChoosenFileTypeEmpty);
        this.isConvertButtonDisabled.set(!(isChoosenFileTypeEmpty && this.getSelectedFile(0) != null));
        this.choosenFileType = choosenFileType;
    }
    public String getChoosenFileType() {
        return choosenFileType;
    }

    // handling of added files
    public void addSelectedFile(File selectedFile) {
        System.out.println("Dodawanie pliku: " + selectedFile.getName());
        this.isConvertButtonDisabled.set(!true);
        selectedFiles.add(selectedFile);
        if (this.selectedFiles.size() == 1) {
            this.selectedFileProperty.set(selectedFiles.get(0));
        }
    }
    public File getSelectedFile(int index) {
        if (!selectedFiles.isEmpty() && index >= 0 && index < selectedFiles.size()) {
            return selectedFiles.get(index);
        }
        return null;
    }
    public void resetSelectedFiles() {
        selectedFiles.clear();
    }
    public void showSelectedFiles() {
        if (!selectedFiles.isEmpty()) {
            System.out.println(selectedFiles);
        } else {
            System.out.println("Brak dodanych plików");
        }
    }
    public ObservableList<File> getSelectedFiles() {
        System.out.println("Selected Files:" + selectedFiles);
        for (File file : selectedFiles) {
            System.out.println(file.getName());
        }
        return selectedFiles;
    }

}