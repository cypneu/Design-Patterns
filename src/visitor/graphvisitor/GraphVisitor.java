package visitor.graphvisitor;

import visitor.computationalgraph.*;

public class GraphVisitor implements Visitor {
    @Override
    public void visit(Addition addition) throws Exception {
        addition.compute();
        System.out.println("\u001B[32m" + "Addition between two matrices has been computed" + "\u001b[0m");
        System.out.println("A:");
        printMatrix(addition.getA().getValue());
        System.out.println("+");
        System.out.println("B:");
        printMatrix(addition.getB().getValue());
        System.out.println("Result:");
        printMatrix(addition.getResNode().getValue());
        System.out.println();
    }

    @Override
    public void visit(Subtraction subtraction) throws Exception {
        subtraction.compute();
        System.out.println("\u001B[32m" + "Subtraction between two matrices has been computed" + "\u001b[0m");
        System.out.println("A:");
        printMatrix(subtraction.getA().getValue());
        System.out.println("-");
        System.out.println("B:");
        printMatrix(subtraction.getB().getValue());
        System.out.println("Result:");
        printMatrix(subtraction.getResNode().getValue());
        System.out.println();
    }

    @Override
    public void visit(ScalarMultiplication scalarMultiplication) {
        scalarMultiplication.compute();
        System.out.println("\u001B[32m" + "Matrix has been multiplied by scalar" + "\u001b[0m");
        System.out.println("A:");
        printMatrix(scalarMultiplication.getA().getValue());
        System.out.println("*");
        System.out.println("Scalar:");
        System.out.println(scalarMultiplication.getScalar());
        System.out.println("Result:");
        printMatrix(scalarMultiplication.getResNode().getValue());
        System.out.println();
    }

    @Override
    public void visit(MatrixMultiplication matrixMultiplication) throws Exception {
        matrixMultiplication.compute();
        System.out.println("\u001B[32m" + "Matrices multiplication has been computed" + "\u001b[0m");
        System.out.println("A:");
        printMatrix(matrixMultiplication.getA().getValue());
        System.out.println("*");
        System.out.println("B:");
        printMatrix(matrixMultiplication.getB().getValue());
        System.out.println("Result:");
        printMatrix(matrixMultiplication.getResNode().getValue());
        System.out.println();
    }

    @Override
    public void visit(Inverse inverse) throws Exception {
        inverse.compute();
        System.out.println("\u001B[32m" + "Matrix inverse has been computed" + "\u001b[0m");
        System.out.println("A^(-1):");
        printMatrix(inverse.getA().getValue());
        System.out.println("Result:");
        printMatrix(inverse.getResNode().getValue());
        System.out.println();
    }

    private void printMatrix(double[][] matrixValues) {
        for (double[] vec : matrixValues) {
            System.out.print("[ ");
            for (double val : vec) System.out.print(val + " ");

            System.out.println("]");
        }
    }
}
