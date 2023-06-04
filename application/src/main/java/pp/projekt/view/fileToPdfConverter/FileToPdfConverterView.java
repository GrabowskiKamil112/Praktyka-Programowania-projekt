package pp.projekt.view.fileToPdfConverter;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.stream.IntStream;

public class FileToPdfConverterView {
    private final FileToPdfConverterModel viewModel;
    private TableView<File> fileListTable;

    public FileToPdfConverterView(Stage stage, FileToPdfConverterModel viewModel) {
        this.viewModel = viewModel;
        this.fileListTable = new TableView<>();

    // Dropdown select
        ComboBox<String> fileTypeComboBox = new ComboBox<>();
        fileTypeComboBox.getItems().addAll("--wybierz--", "XML", "HTML", "DOCX");
        fileTypeComboBox.setValue("--wybierz--");
        fileTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                viewModel.setChoosenFileType(newValue);
        });

    // Table to display file names
        fileListTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<File, String> fileNameColumn = new TableColumn<>("Wybrane pliki");
        fileListTable.setPlaceholder(new Label("Brak wybranych plików"));
        fileNameColumn.setCellValueFactory(cellData -> {
            File file = cellData.getValue();
            System.out.println("inside table: " + file.getName());
            String fileName = file != null ? file.getName() : "";
            return new SimpleStringProperty(fileName);
        });
        fileListTable.getColumns().add(fileNameColumn);
        fileListTable.setItems(viewModel.getSelectedFiles());
        viewModel.getSelectedFiles().addListener((ListChangeListener<File>) change -> {
            System.out.println("refresh");
            fileListTable.refresh();
        });


    // Button to select file
        Button selectFileButton = new Button("Dodaj plik");
        selectFileButton.disableProperty().bind(viewModel.isChoosenFileTypeEmpty);
        selectFileButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            String choosenFileType = viewModel.getChoosenFileType();
            String fileExtension = "";

            if (choosenFileType.equals("XML")) {
                fileExtension = "*.xml";
            } else if (choosenFileType.equals("HTML")) {
                fileExtension = "*.html";
            } else if (choosenFileType.equals("DOCX")) {
                fileExtension = "*.docx";
            }

            if (!fileExtension.isEmpty()) {
                FileChooser.ExtensionFilter customFilter = new FileChooser.ExtensionFilter("Pliki " + choosenFileType, fileExtension);
                fileChooser.getExtensionFilters().add(customFilter);
            }

            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                viewModel.addSelectedFile(selectedFile);
                viewModel.showSelectedFiles();
                fileListTable.refresh();
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


    // Convert button
        Button submitTransferButton = new Button("Konwertuj do PDF");
        submitTransferButton.disableProperty().bind(viewModel.isConvertButtonDisabled);


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
        List<RowConstraints> constraints = IntStream.rangeClosed(1, gridPane.getRowCount())
            .mapToObj(i -> {
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setValignment(VPos.BASELINE);

                return rowConstraints;
            })
            .toList();
        gridPane.getRowConstraints().addAll(constraints);

        HBox actionButtonsBar = new HBox(
            submitTransferButton
        );
        actionButtonsBar.setSpacing(5);
        actionButtonsBar.setAlignment(Pos.BASELINE_RIGHT);

        VBox panel = new VBox();
        panel.setPadding(new Insets(20));
        panel.setSpacing(10);

        panel.getChildren().addAll(
            gridPane,
            fileListTable,
            actionButtonsBar
        );

        stage.setScene(new Scene(panel));
        stage.sizeToScene();
        stage.setMinWidth(360);
        stage.show();
    }
}
