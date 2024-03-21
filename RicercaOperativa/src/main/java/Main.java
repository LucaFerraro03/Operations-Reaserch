public class Main {

    public static void main(String[] args) {

        double[][] matrix = {{1, -2, -6, 0, 0, 0, 0, 0},
                {1, 0, 0, 1, 0, 0, 0, 2},
                {0, 1, 0, 0, 1, 0, 0, 3},
                {0, 0, 1, 0, 0 , 1, 0, 3},
                {1, 1, 1, 0, 0, 0, 1, 4}};

        Tableau tableau = new Tableau(matrix, "min");

        tableau.solve();
    }
}
