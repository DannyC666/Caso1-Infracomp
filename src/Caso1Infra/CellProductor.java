package Caso1Infra;

public class CellProductor extends Thread {
    private boolean alive ;
    private int row;
    private int col;
    private Buffer buff;
    private int neighborsAlive;
    public CellProductor (boolean alive, int row,  int col, Buffer buff){
        this.alive = alive;
        this.row = row;
        this.col = col;
        this.buff = buff;
    }

    public void run(){

        int iAlive = iAmProductorAlive();
        try {
            this.buff.almacenar(iAlive);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public int iAmProductorAlive( ){
        return this.alive ? 1 : 0;
    }
}


