package pp.projekt.view;

public enum FileType {
    EMPTY("--wybierz--"),
    XML("XML"),
    HTML("HTML"),
    DOCX("DOCX");

    private final String displayName;

    FileType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
