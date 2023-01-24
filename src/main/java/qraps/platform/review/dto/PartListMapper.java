package qraps.platform.review.dto;

public enum PartListMapper {
    StepDownIC(1),
    Diode(2),
    Transistor(3);

    private final int tableIndex;

    PartListMapper(int tableIndex) {
        this.tableIndex = tableIndex;
    }

    public int getTableIndex() {
        return tableIndex;
    }

}
