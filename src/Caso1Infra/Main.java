package Caso1Infra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // Crea la matriz segun la dimension dada
        int dimension = Integer.parseInt(reader.readLine());
        Matrix matrix = new Matrix(dimension);
        matrix.createMatrix();
        // Obtiene la matriz e imprime el estado 0
        int[][] newMatrix = matrix.getMatrix();
        Matrix.printMatrix(newMatrix, dimension);
        // Crea una matriz de buffers.
        Buffer[][] bufferId = new Buffer[dimension][dimension];
        // Recorre cada celda y por cada una crea un buzon.
        for (int i = 0; i < newMatrix.length; i++) {
            for (int j = 0; j < newMatrix[i].length; j++) {
                // Por cada celda recorrida de la matriz nxn, se saca una lista de vecinos
                ArrayList<int[]> neighborsLi = matrix.knowNeighbors(i, j);
                // Creo un nuevo buffer y lo coloco en la posicion correcta de la matriz de buffers
                bufferId[i][j] = new Buffer(i + 1, neighborsLi.size());
                // Creo una lista vacia con la cantidad de productores que habra (#productores = #vecinos)
                Cell[] productorThreads = new Cell[neighborsLi.size()];
                boolean valueMatrixConsumer = matrix.getValueMatrix(i, j);
                // Por cada vecino de la celda, creo un nuevo productor y lo guardo en productorThreads
                for (int k = 0; k < neighborsLi.size(); k++) {
                    int[] neighborPos = neighborsLi.get(k);
                    boolean valueMatrixProductors = matrix.getValueMatrix(neighborPos[0], neighborPos[1]);
                    productorThreads[k] = new Cell(valueMatrixProductors, neighborPos[0] ,neighborPos[1],bufferId[i][j],false,neighborsLi.size() );
                    productorThreads[k].start();
                }
                // Por cada celda, hay un nuevo consumidor que apunta a su respectivo buffer
                Cell consumerThread = new Cell(valueMatrixConsumer,i,j,bufferId[i][j],true, neighborsLi.size());

                consumerThread.start();

            }
        }

    }

}
