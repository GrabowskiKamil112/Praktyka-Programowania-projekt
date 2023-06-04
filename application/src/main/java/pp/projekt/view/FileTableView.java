package pp.projekt.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.util.Callback;
import pp.projekt.view.fileToPdfConverter.FileToPdfConverterModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static pp.projekt.utils.Utils.getFileExtension;
import static pp.projekt.utils.Utils.removeExtension;

public class FileTableView extends TableView<File> {
    private final FileToPdfConverterModel viewModel;

    public FileTableView(FileToPdfConverterModel viewModel) {
        this.viewModel = viewModel;

        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setPlaceholder(new Label("Brak wybranych plików"));
        setVisible(false);

        TableColumn<File, String> fileNameColumn = new TableColumn<>("Nazwa pliku");
        fileNameColumn.setCellValueFactory(cellData -> {
            File file = cellData.getValue();
            String fileName = file != null ? removeExtension(file.getName()) : "";
            return new SimpleStringProperty(fileName);
        });

        TableColumn<File, String> fileExtensionColumn = new TableColumn<>("Rozszerzenie");
        fileExtensionColumn.setCellValueFactory(cellData -> {
            File file = cellData.getValue();
            String fileExtension = file != null ? getFileExtension(file) : "";
            return new SimpleStringProperty(fileExtension);
        });
        fileExtensionColumn.setStyle("-fx-alignment: CENTER;");
        fileExtensionColumn.setMaxWidth(90);
        fileExtensionColumn.setMinWidth(90);

        TableColumn<File, Boolean> selectColumn = new TableColumn<>("Zaznacz pliki do konwersji");
        selectColumn.setCellFactory(new Callback<TableColumn<File, Boolean>, TableCell<File, Boolean>>() {
            @Override
            public TableCell<File, Boolean> call(TableColumn<File, Boolean> param) {
                return new TableCell<File, Boolean>() {
                    private final CheckBox checkBox = new CheckBox();
                    {
                        checkBox.setOnAction(event -> {
                            File file = getTableRow().getItem();
                            if (file != null) {
                                boolean selected = checkBox.isSelected();
                                System.out.println(file);
                                    if (selected) {
                                        viewModel.removeFromSelectedList(file);
                                    } else {
                                        viewModel.addFileToSelectedlist(file);
                                    }
                            }
                        });
                        setAlignment(Pos.CENTER);
                    }

                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            checkBox.setSelected(item != null && item);
                            setGraphic(checkBox);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        List<TableColumn<File, ?>> columns = new ArrayList<>();
        columns.add(fileNameColumn);
        columns.add(fileExtensionColumn);
        columns.add(selectColumn);
        getColumns().setAll(columns);

        setItems(viewModel.getSelectedFiles());
        viewModel.getSelectedFiles().addListener((ListChangeListener<File>) change -> {
            boolean showTable = viewModel.getSelectedFiles().size() > 1;
            setVisible(showTable);
            refresh();
        });
    }
}