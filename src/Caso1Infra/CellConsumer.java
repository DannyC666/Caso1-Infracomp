package Caso1Infra;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CellConsumer extends Thread{
    private boolean alive ;
    private final int row;
    private final int col;
    private final Buffer buff;
    private int neighborsAlive;
    private int neighbors;
    private Matrix matrix;
    private final CyclicBarrier barrierCell;
    private final CyclicBarrier barrierMatrix;

    public CellConsumer(boolean alive, int row, int col, Buffer buff, int neighbors,Matrix matrix, CyclicBarrier barrierCell, CyclicBarrier barrierMatrix) {
        this.alive = alive;
        this.row = row;
        this.col = col;
        this.buff = buff;
        this.matrix = matrix;
        this.neighbors = neighbors;
        this.barrierCell = barrierCell;
        this.barrierMatrix = barrierMatrix;
    }

    public void run(){
        Integer ret;
        for (int i = 0; i < this.neighbors; i++){
            try {
                ret = this.buff.retirar();
                while(ret == null){
                    Thread.yield();
                    ret = this.buff.retirar();
                }
                this.neighborsAlive += ret;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        // Primera barrera que actualiza estados de la matriz
        try {
            barrierCell.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
        updateState();
        // Segunda barrera que espera hasta que todos los estados se actualicen para poder imprimir
        try {
            barrierMatrix.await();

        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isAliveCell() {
        return alive;
    }

    public void updateState(){
        int[][] matrixPointer =   this.matrix.getMatrix();
        // Caso  de nacimiento
        if(this.alive == false && this.neighborsAlive == 3 ){
            this.alive = true;
            matrixPointer[this.row][this.col] = 1;

        } else if (this.alive == true ) {
            // Caso de muerte
            if(this.neighborsAlive > 3 || this.neighborsAlive == 0){
                this.alive = false;
                matrixPointer[this.row][this.col] = 0;
            }
            // Caso de continuidad
            else if (this.neighborsAlive >=1 && this.neighborsAlive <= 3) {
                this.alive = true;
                matrixPointer[this.row][this.col] = 1;


            }
        }

    }
}



