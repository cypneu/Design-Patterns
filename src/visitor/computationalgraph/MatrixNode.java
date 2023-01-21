package visitor.computationalgraph;

public class MatrixNode {
    private double[][] value;
    private int rowsNum, columnsNum;

    public MatrixNode(double[][] matrixValues) {
        setMatrix(matrixValues);
    }

    public MatrixNode() {
    }

    public void setMatrix(double[][] matrixValues) {
        this.value = matrixValues;
        this.rowsNum = matrixValues.length;
        this.columnsNum = (this.rowsNum > 0) ? matrixValues[0].length : 0;
    }

    public int getRowsNum() {
        return rowsNum;
    }


    public int getColumnsNum() {
        return columnsNum;
    }


    public double[][] getValue() {
        return value;
    }
}
