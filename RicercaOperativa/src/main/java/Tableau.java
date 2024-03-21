import java.util.Arrays;

public class Tableau {

    private double[][] tableau;
    private String mode;
    private double profit;

    public Tableau( double[][] matrix, String m) {
        tableau = new double[matrix.length][];

        for(int i = 0; i < tableau.length; i++)
            tableau[i] = Arrays.copyOf(matrix[i], matrix[i].length);

        mode = m;

        profit = 0;
    }

    public double[][] getTableau() {
        return tableau;
    }

    public String getMode() {
        return mode;
    }

    /**
     * This method chooses the first column that contains a potential pivot
     *
     * @return the column to choose from or -1 if it doesn't exist
     */
    private int chooseColumn() {

        int index = -1;

        for(int i = 0; i < tableau[0].length - 1; i++) {
            if(mode.equals("min") && tableau[0][i] < 0 ||
                    mode.equals("max") && tableau[0][i] > 0){
                index = i;
                break;
            }
        }

        return index;
    }


    /**
     * This method looks for the existence a potential pivot
     *
     * @param column the column in which you should search for the pivot
     * @return true only if it's possible to choose a pivot, false otherwise (the problem is unbounded)
     */
    private boolean pivotExists(int column){
        boolean possible = false;

        for(int i = 1; i < tableau.length - 1; i++) {
            if(tableau[i][column] > 0)
                possible = true;
        }

        return possible;
    }


    /**
     * This method creates an array of boolean values that are true if there is a possible pivot at that index, false otherwise
     *
     * @param column the column of the possible pivot
     * @return the array created
     */
    private boolean[] possibilities(int column) {
        boolean[] possibilities = new boolean[tableau.length - 1];

        for(int i = 0; i < tableau.length - 1; i++) {
            if(tableau[i + 1][column] < 0)
                possibilities[i] = false;
            else
                possibilities[i] = true;

        }

        return possibilities;
    }


    /**
     * This method search the min value in an array
     *
     * @param costs array to be searched through
     * @return the min value
     */
    private int searchMin(double[] costs) {
        int min = 0;

        for(int i = 0; i < costs.length; i++)
            if(costs[i] < costs[min] && costs[i] != 0)
                min = i;

        return min;
    }


    /**
     * This method chooses a pivot
     *
     * @param possibilities this array contains all the possible pivots (indicated with true)
     * @param column the column of the possible pivot
     * @return the row in which the pivot is located
     */
    private int choosePivot(boolean[] possibilities, int column) {
        double[] costs = new double[tableau.length - 1];
        int pivot = 0;

        for(int i = 0; i < tableau.length - 1; i++) {
            if(possibilities[i])
                costs[i] = tableau[i + 1][tableau[0].length - 1] / tableau[i + 1][column];
        }

        pivot = searchMin(costs);

        return ++pivot;
    }


    /**
     * This method divides an array by a factor
     *
     * @param v The array that has to be divided
     * @param factor The number used to divide
     * @return The array divided by the factor
     */
    private double[] divideArrayAbs(double[] v, double factor)
    {
        double[] dividedArray = new double[v.length];
        for(int i = 0; i < dividedArray.length; i++)
            dividedArray[i] = Math.abs(v[i] / factor);

        return dividedArray;
    }

    /**
     * This method controls if there are all zeroes but only one '1' in a column
     *
     * @param column Index of the column
     * @return True if there are all zeroes but only one '1', false otherwise
     */
    private boolean allZeroesButOne(int column){
        int cont = 0;

        for ( int i = 0; i < tableau.length; i++ ) {
            if ( this.tableau[i][column] == 0 )
                cont++;
        }

        if ( cont == tableau.length - 1 )
            return true;

        return false;
    }

    /**
     * This method looks for the only '1' in the column
     *
     * @param column Index of the column to choose from
     * @return The index of the one
     */
    private int indexOfOne(int column){

        int row = 0;

        for ( int i = 0; i < tableau.length; i++ ) {
            if ( this.tableau[i][column] == 1 )
                row = i;
        }

        return row;
    }


    /**
     * The method solves the tableau
     *
     * @return the solved tableau
     */
    public void solve() {

        System.out.println("Starting point:\n" + toString());

        int column = chooseColumn();

        if(column < 0) {
            System.out.println("Problem cannot be solved or it's already solved!");
            return;
        }

        boolean exists = pivotExists(column);

        if(!exists) {
            System.out.println("Problem unbounded! Unlimited solution!");
            return;
        }

        while(true) {
            boolean[] possiblePivot = possibilities(column);
            int row = choosePivot(possiblePivot, column);


            if(tableau[row][column] != 1) {
                tableau[row] = divideArrayAbs(tableau[row],tableau[row][column]);
            }

            for(int i = 0; i < tableau.length; i++) {
                if(i != row) {

                    double ratio = tableau[i][column] / tableau[row][column];

                    for(int j = 0; j < tableau[0].length; j++) {

                        if(tableau[row][j] != 0)
                            tableau[i][j] = tableau[i][j] - tableau[row][j] * ratio;
                    }

                }

            }

            column = chooseColumn();

            if(column < 0) {
                System.out.println("\nProblem solved!");
                profit = tableau[0][tableau[0].length - 1];

                System.out.println(toString());
                return;
            }

            if(!pivotExists(column)) {
                System.out.println("Problem unbounded! Unlimited solution!");
                return;
            }
        }

    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();

        for(double[] row : tableau)
            sb.append(Arrays.toString(row)).append("\n");

        for(int i = 0; i < tableau[0].length - 1; i++) {

            if(i == (tableau[0].length - tableau.length))
                sb1.append(")\nSLACK VARIABLES: (");

            sb1.append("x").append(i + 1).append(": ");

            if(allZeroesButOne(i))
                sb1.append(tableau[indexOfOne(i)][tableau[0].length - 1]);
            else
                sb1.append(0);

            if(i == tableau[0].length - 2 || i == (tableau[0].length - tableau.length) - 1)
                sb1.append("");
            else
                sb1.append(", ");
        }

        return "Tableau:\n" + sb +
                "\nMode = '" + mode + '\'' +
                "\nProfit = " + (-profit) +
                "\nSolution = (" + sb1 + ")";
    }
}
