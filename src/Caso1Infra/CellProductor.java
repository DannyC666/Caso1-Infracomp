package Caso1Infra;

public class CellProductor extends Thread {
    private boolean alive ;
    private Buffer buff;
    private int neighborsAlive;
    public CellProductor (boolean alive,  Buffer buff){
        this.alive = alive;
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


