package Caso1Infra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Main {
    public static void main(String[] args) throws IOException, BrokenBarrierException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // Crea la matriz segun la dimension dada
        int dimension = Integer.parseInt(reader.readLine());
        Matrix matrix = new Matrix(dimension);
        matrix.createMatrix();
        // Obtiene la matriz e imprime el estado 0
        int[][] newMatrix = matrix.getMatrix();
        System.out.println("Tablero de la generacion inicial: ");
        Matrix.printMatrix(newMatrix, dimension);
        Buffer[][] bufferId = new Buffer[dimension][dimension];

        for (int i = 0; i < 4; i++) {
            //Contador que verifica si las dos barreras se pasan
            CountDownLatch latch = new CountDownLatch(2);
            //Barrera que actualiza el valor de las celdas
            CyclicBarrier barrierUpdateCell = new CyclicBarrier(dimension*dimension,() ->{
                System.out.println("Actualizando celdas... ");
                latch.countDown();
            });
            // Barrera que actualiza el tablero
            int finalI = i;
            CyclicBarrier barrierUpdateMatrix = new CyclicBarrier(dimension*dimension,() ->{
                System.out.println("Tablero de la "+ (finalI +1) +" generacion: ");
                Matrix.printMatrix(newMatrix, dimension);
                latch.countDown();
            });
            runMatrix(bufferId,newMatrix,matrix,barrierUpdateCell,barrierUpdateMatrix);
            // itera de nuevo (siguiente generacion) , hasta que las dos barreras se pasen correctamente
            latch.await();
        }

    }

    public static void runMatrix(Buffer[][] bufferId, int[][] newMatrix, Matrix matrix,
                                  CyclicBarrier barrierUpdateCell, CyclicBarrier barrierUpdateMatrix){

        // Recorre cada celda y por cada una crea un buzon.
        for (int i = 0; i < newMatrix.length; i++) {
            for (int j = 0; j < newMatrix[i].length; j++) {
                // Por cada celda recorrida de la matriz nxn, se saca una lista de vecinos
                ArrayList<int[]> neighborsLi = matrix.knowNeighbors(i, j);
                // Creo un nuevo buffer y lo coloco en la posicion correcta de la matriz de buffers
                bufferId[i][j] = new Buffer(i + 1, neighborsLi.size());
                // Creo una lista vacia con la cantidad de productores que habra (#productores = #vecinos)
                CellProductor[] productorThreads = new CellProductor[neighborsLi.size()];
                boolean valueMatrixConsumer = matrix.getValueMatrix(i, j);
                // Por cada vecino de la celda, creo un nuevo productor y lo guardo en productorThreads
                for (int k = 0; k < neighborsLi.size(); k++) {
                    int[] neighborPos = neighborsLi.get(k);
                    boolean valueMatrixProductors = matrix.getValueMatrix(neighborPos[0], neighborPos[1]);
                    productorThreads[k] = new CellProductor(valueMatrixProductors, neighborPos[0] ,neighborPos[1],bufferId[i][j] );
                    productorThreads[k].start();
                }
                // Por cada celda, hay un nuevo consumidor que apunta a su respectivo buffer
                CellConsumer consumerThread = new CellConsumer(valueMatrixConsumer,i,j,bufferId[i][j], neighborsLi.size(), matrix,barrierUpdateCell, barrierUpdateMatrix);
                consumerThread.start();


            }
        }

    }
}
