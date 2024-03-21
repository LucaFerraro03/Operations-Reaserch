public class Main {

    public static void main(String[] args) {

        double[][] matrix1 = {{1, -2, -6, 0, 0, 0, 0, 0},
                {1, 0, 0, 1, 0, 0, 0, 2},
                {0, 1, 0, 0, 1, 0, 0, 3},
                {0, 0, 1, 0, 0 , 1, 0, 3},
                {1, 1, 1, 0, 0, 0, 1, 4}};

        double[][] matrix2 = {{1, 3, 0, 0, 0},
                             {1, -2, 1, 0, 4},
                             {1, -1, 0, 1, 8}};

        Tableau tableau;

        tableau = new Tableau(matrix1, "max");
        tableau.solve();


        tableau = new Tableau(matrix2, "max");
        tableau.solve();
    }
}
