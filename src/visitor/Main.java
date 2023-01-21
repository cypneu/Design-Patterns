package visitor;

import visitor.computationalgraph.*;
import visitor.graphvisitor.GraphVisitor;

public class Main {
    public static void main(String[] args) {
        double[][] AValues = {
                {1, 3, 5},
                {4, 0, 11},
                {8, 7, 2}
        };
        double[][] BValues = {
                {0, 1, 3},
                {4, -2, 2},
                {-3, 5, 2}
        };
        double[][] CValues = {
                {-1, 2, -3},
                {3, 0, 4},
                {-6, 9, 1}
        };
        MatrixNode A = new MatrixNode(AValues);
        MatrixNode B = new MatrixNode(BValues);
        MatrixNode C = new MatrixNode(CValues);
        double alfa = 2;

        ComputationalGraph graph = new ComputationalGraph();

        MatrixNode node1 = graph.add(new Addition(A, B));
        MatrixNode node2 = graph.add(new Subtraction(node1, C));
        MatrixNode node3 = graph.add(new MatrixMultiplication(node1, node2));
        MatrixNode node4 = graph.add(new ScalarMultiplication(node3, alfa));
        MatrixNode node5 = graph.add(new Inverse(node4));

        GraphVisitor visitor = new GraphVisitor();
        for (OperationNode node : graph.vertices) {
            try {
                node.accept(visitor);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
