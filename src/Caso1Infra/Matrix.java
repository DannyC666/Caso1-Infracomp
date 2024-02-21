package Caso1Infra;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Matrix {
    private int dimension;
    private int[][] matrix ;
    private String filePath;
    public Matrix(String filePath){
        this.filePath = filePath;

    }

    public  void createMatrix() throws IOException {
        Scanner scanner = new Scanner(new File(this.filePath));
        this.dimension = scanner.nextInt();
        this.matrix = new int[dimension][dimension];
        ArrayList<ArrayList<Integer>> matrizTemporal = new ArrayList<>();
        for (int i = 0; i < this.dimension; i++) {
            String[] valores = scanner.next().split(",");
            ArrayList<Integer> fila = new ArrayList<>();
            for (String valor : valores) {
                int numValue = valor.equals("false") ? 0 : (valor.equals("true") ? 1 : -1);
                fila.add(numValue);
            }
            matrizTemporal.add(fila);
        }
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                this.matrix[i][j] = matrizTemporal.get(i).get(j);
            }
        }
        scanner.close();

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

    public int getDimension() {
        return dimension;
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
