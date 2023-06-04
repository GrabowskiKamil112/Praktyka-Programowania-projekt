package pp.projekt.view.fileToPdfConverter;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pp.projekt.view.FileType;

import java.io.File;

public class FileToPdfConverterModel {
    private ObservableList<File> selectedFiles;
    private ObservableList<File> selectedCheckedFiles;
    private FileType choosenFileType;
    private ObjectProperty<File> selectedFileProperty;

    public ObjectProperty<File> selectedFileProperty() {
        if (selectedFileProperty == null) {
            selectedFileProperty = new SimpleObjectProperty<>();
            if (!selectedFiles.isEmpty()) selectedFileProperty.set(selectedFiles.get(0));
        }
        return selectedFileProperty;
    }

    public FileToPdfConverterModel() {
        this.choosenFileType = FileType.EMPTY;
        this.selectedFiles = FXCollections.observableArrayList();
        this.selectedCheckedFiles = FXCollections.observableArrayList();
    }

    final BooleanProperty isConvertButtonDisabled = new SimpleBooleanProperty(true);
    final BooleanProperty isChoosenFileTypeEmpty = new SimpleBooleanProperty(true);

    public void setChoosenFileType(FileType choosenFileType) {
        boolean isChoosenFileTypeEmpty = choosenFileType.equals(FileType.EMPTY);
        if(isChoosenFileTypeEmpty){
            this.resetSelectedFiles();
        }
        this.isChoosenFileTypeEmpty.set(isChoosenFileTypeEmpty);
        this.isConvertButtonDisabled.set(!(isChoosenFileTypeEmpty && this.getSelectedFile(0) != null));
        if(this.getSelectedFiles().size() > 1) this.isConvertButtonDisabled.set(false);
        this.choosenFileType = choosenFileType;
    }

    public FileType getChoosenFileType() {
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
        selectedCheckedFiles.clear();
    }
    public void showSelectedFiles() {
        if (!selectedFiles.isEmpty()) {
            //System.out.println(selectedFiles);
        } else {
           System.out.println("Brak dodanych plików");
        }
    }
    public ObservableList<File> getSelectedFiles() {
        System.out.println("Selected Files:" + selectedFiles);
        return selectedFiles;
    }

    public void addFileToSelectedlist(File file) {
        System.out.println("file to add to checked"+file);
        this.selectedCheckedFiles.remove(file);
        System.out.println(this.selectedCheckedFiles);
    }
    public void removeFromSelectedList(File file) {
        System.out.println("file to remove from checked"+file);
        this.selectedCheckedFiles.add(file);
        System.out.println(this.selectedCheckedFiles);
    }
    public ObservableList<File> getSelectedCheckedFiles() {
        return selectedCheckedFiles;
    }
}