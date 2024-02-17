package Caso1Infra;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Matrix {
    private final int dimension;
    private int[][] matrix ;
    public Matrix(int dimension){
        this.dimension = dimension;
        this.matrix = new int[dimension][dimension];
    }

    public  void createMatrix() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < this.dimension; i++) {
            String rowInput = reader.readLine();
            String[] row = rowInput.split(" ");
            for (int j = 0; j < this.dimension; j++) {
                int numValue = row[j].equals("false") ? 0 : (row[j].equals("true") ? 1 : -1);
                this.matrix[i][j] = numValue;
            }
        }

    }

    public ArrayList<int[]> knowNeighbors(int row, int col){
        int[][] posibleNeighbors = {
                {row - 1, col},   // Up
                {row + 1, col},   // Down
                {row, col - 1},   // Left
                {row, col + 1}  ,  // Right
                {row-1, col + 1} ,   // Diag.Up-Right
                {row-1,col - 1}  ,  // Diag.up-left
                {row+1, col + 1} ,   // Diag.dw-Right
                {row+1, col - 1}  ,  // Diag.dw-left
        };
        ArrayList<int[]> neighborsList = new ArrayList<>();

        for (int[] pos : posibleNeighbors) {
            int neighborRow = pos[0];
            int neighborCol = pos[1];
            if (neighborRow >= 0 && neighborRow < this.dimension && neighborCol >= 0 && neighborCol < this.dimension) {
                int[] neighborPos = new int[]{neighborRow, neighborCol};
                neighborsList.add(neighborPos);
            }
        }
        return neighborsList;
    }

    public boolean getValueMatrix(int row, int col){
        return this.matrix[row][col] == 1;
    }


    public static void printMatrix(int[][] matrix, int dimension){
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();

        }
    }

    public int[][] getMatrix() {
        return matrix;
    }

}
