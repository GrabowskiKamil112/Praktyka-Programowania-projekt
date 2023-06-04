package pp.projekt.view;

import javafx.util.StringConverter;
import pp.projekt.view.FileType;

public class FileTypeConverter extends StringConverter<FileType> {
    @Override
    public String toString(FileType fileType) {
        return fileType.getDisplayName();
    }

    @Override
    public FileType fromString(String string) {
        return null;
    }
}
