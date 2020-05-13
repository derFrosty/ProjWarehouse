package warehouse;

public class Pair {
    private Cell cell1;
    private Cell cell2;
    private int value;

    public Pair(Cell cell1, Cell cell2) {
        this.cell1 = cell1;
        this.cell2 = cell2;
    }

    public Cell getCell1() {
        return cell1;
    }

    public Cell getCell2() {
        return cell2;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return cell1.getLine() + "-" + cell1.getColumn() + " / " + cell2.getLine() + "-" + cell2.getColumn() + ": " + value + "\n";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;

        int cell1L = cell1.getLine();
        int cell1C = cell1.getColumn();

        int cell2L = cell2.getLine();
        int cell2C = cell2.getColumn();

        result = prime * result + (cell1L);
        result = prime * result + (cell1C);
        result = prime * result + (cell2L);
        result = prime * result + (cell2C);

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pair other = (Pair) obj;
        if ((cell1 == other.cell1 && cell2 == other.cell2) || (cell1 == other.cell2 && cell2 == other.cell1))
            return true;
        return false;
    }

    public void setCell1(Cell cell1) {
        this.cell1 = cell1;
    }

    public void setCell2(Cell cell2) {
        this.cell2 = cell2;
    }
}
