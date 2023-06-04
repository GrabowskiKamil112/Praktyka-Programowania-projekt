package pp.projekt.view.fileToPdfConverter;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pp.projekt.view.FileTableView;
import pp.projekt.view.FileType;
import pp.projekt.view.FileTypeConverter;

import java.io.File;

public class FileToPdfConverterView {
    private final FileToPdfConverterModel viewModel;
    private Label errorLabel;
    private Button submitTransferButton;
    private ComboBox<FileType> fileTypeComboBox;


    public FileToPdfConverterView(Stage stage, FileToPdfConverterModel viewModel) {
        this.viewModel = viewModel;
        this.submitTransferButton = new Button("Konwertuj do PDF");
        this.errorLabel = new Label("Musisz wybrać co najmniej jeden plik do konwersji spośród dodanych plików");
        this.fileTypeComboBox = new ComboBox<>();

    // Dropdown select
        fileTypeComboBox.getItems().addAll(FileType.EMPTY, FileType.XML, FileType.HTML, FileType.DOCX);
        fileTypeComboBox.setValue(FileType.EMPTY);
        fileTypeComboBox.setConverter(new FileTypeConverter());
        fileTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                viewModel.setChoosenFileType(newValue);
        });

    // Table to display all files
        FileTableView fileTableView = new FileTableView(viewModel);

    // Button to select file
        Button selectFileButton = new Button("Dodaj plik");
        selectFileButton.disableProperty().bind(viewModel.isChoosenFileTypeEmpty);
        selectFileButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            FileType choosenFileType = viewModel.getChoosenFileType();
            String fileExtension = "";

            switch (choosenFileType) {
                case XML:
                    fileExtension = "*.xml"; break;
                case HTML:
                    fileExtension = "*.html"; break;
                case DOCX:
                    fileExtension = "*.docx"; break;
                default: break;
            }

            if (!fileExtension.isEmpty()) {
                FileChooser.ExtensionFilter customFilter = new FileChooser.ExtensionFilter("Pliki " + choosenFileType, fileExtension);
                fileChooser.getExtensionFilters().add(customFilter);
            }

            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                viewModel.addSelectedFile(selectedFile);
                fileTableView.refresh();
            }
        });

        viewModel.getSelectedFiles().addListener((ListChangeListener<File>) change -> {
            int selectedFilesCount = viewModel.getSelectedFiles().size();
            if (selectedFilesCount > 0) {
                selectFileButton.setText("Dodaj kolejny plik");
            } else {
                selectFileButton.setText("Dodaj plik");
            }
        });

    // Label to display file name
        Label fileNameLabel = new Label("Nazwa pliku:  Brak");
        fileNameLabel.setMaxWidth(Double.MAX_VALUE);
        fileNameLabel.setWrapText(true);
        viewModel.selectedFileProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fileNameLabel.setText("Nazwa pliku:  " + newValue.getName());
            } else {
                fileNameLabel.setText("Nazwa pliku:  Brak");
            }
        });

    // Button to clear file list
        Button clearListButton = new Button("Wyczyść listę");
        clearListButton.setVisible(false);
        clearListButton.setOnAction(event -> {
            viewModel.resetSelectedFiles();
            viewModel.setChoosenFileType(FileType.EMPTY);
            fileTypeComboBox.setValue(FileType.EMPTY);
            fileTableView.refresh();
            errorLabel.setVisible(false);
            submitTransferButton.setText("Konwertuj do PDF");
        });

    // Convert button
        submitTransferButton.disableProperty().bind(viewModel.isConvertButtonDisabled);

        viewModel.getSelectedFiles().addListener((ListChangeListener<File>) change -> {
            int selectedFilesCount = viewModel.getSelectedFiles().size();
            if (selectedFilesCount == 0) fileNameLabel.setText("Nazwa pliku:  Brak");
            if (selectedFilesCount > 1) {
                submitTransferButton.setText("Konwertuj pliki do PDF (0)");
                fileNameLabel.setVisible(false);
                clearListButton.setVisible(true);
            } else {
                submitTransferButton.setText("Konwertuj do PDF");
                fileNameLabel.setVisible(true);
                clearListButton.setVisible(false);
            }
        });

        viewModel.getSelectedCheckedFiles().addListener((ListChangeListener<File>) change -> {
            int selectedFilesCount = viewModel.getSelectedCheckedFiles().size();
            submitTransferButton.setText("Konwertuj pliki do PDF (" + selectedFilesCount + ")");
        });

        submitTransferButton.setOnAction(event -> {
            ObservableList<File> selectedCheckedFiles = viewModel.getSelectedCheckedFiles();
            ObservableList<File> selectedFiles = viewModel.getSelectedFiles();

            if (selectedCheckedFiles.size() > 0) {
                // TODO konwersja plików z listy selectedCheckedFiles
                System.out.println("logika konwersji do PDF zaznaczonych plików z listy");
            } else if (selectedFiles.size() == 0){
                // TODO konwersja plików z listy selectedFiles
                System.out.println("logika konwersji do PDF pliku w sytuacji kiedy tylko jeden plik został wybrany i jeszcze nie ma listy wyświetlonej");
            }
        });

    // error label
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setVisible(false);
        submitTransferButton.setOnAction(event -> {
            int selectedFilesCount = viewModel.getSelectedFiles().size();
            int selectedCheckedFilesCount = viewModel.getSelectedCheckedFiles().size();
            if (selectedFilesCount > 1 && selectedCheckedFilesCount == 0) {
                errorLabel.setVisible(true);
            } else {
                errorLabel.setVisible(false);
            }
        });

    // Layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(15);

        gridPane.addRow(gridPane.getRowCount(), new Label("Typ pliku:"), fileTypeComboBox);
        gridPane.addRow(gridPane.getRowCount(), new Label(), selectFileButton);
        gridPane.addRow(gridPane.getRowCount(), fileNameLabel);

        GridPane.setHalignment(fileNameLabel, HPos.LEFT);
        GridPane.setColumnSpan(fileNameLabel, 2);

        ColumnConstraints leftColumnConstraints = new ColumnConstraints();
        leftColumnConstraints.setHalignment(HPos.RIGHT);
        leftColumnConstraints.setHgrow(Priority.NEVER);

        ColumnConstraints rightColumnConstraints = new ColumnConstraints();
        rightColumnConstraints.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(leftColumnConstraints, rightColumnConstraints);

        HBox actionButtonsBar = new HBox(clearListButton, submitTransferButton);
        VBox errorLabelMessage = new VBox(errorLabel);
        errorLabelMessage.setSpacing(5);
        errorLabelMessage.setAlignment(Pos.BASELINE_RIGHT);
        actionButtonsBar.setSpacing(5);
        actionButtonsBar.setAlignment(Pos.BASELINE_RIGHT);

        VBox panel = new VBox();
        panel.setPadding(new Insets(20));
        panel.setSpacing(10);

        panel.getChildren().addAll(
            gridPane,
            fileTableView,
            actionButtonsBar,
            errorLabelMessage
        );

        stage.setScene(new Scene(panel));
        stage.sizeToScene();
        stage.setMinWidth(520);
        stage.show();
    }
}
