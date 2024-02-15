package Caso1Infra;


import java.util.ArrayList;

public class Cell extends Thread{
    private boolean alive ;
    private  boolean isConsumer;
    private int row;
    private int col;
    private Buffer buff;
    private int neighbors;
    private int neighborsAlive;

    public Cell (boolean alive, int row,  int col, Buffer buff, boolean isConsumer, int neighbors) {
        this.alive = alive;
        this.row = row;
        this.col = col;
        this.buff = buff;
        this.isConsumer = isConsumer;
        this.neighbors = neighbors;

    }

    public void run(){
        //TODO: Separar esta clase con el otro else, para que sea mas legible el codigo
        if (this.isConsumer){

            while(true){
                try {

                    //System.out.println("el hilo "+this.row+","+this.col+" entro como consumidor" );
                    Integer i = this.buff.retirar();
                    if (i == null){
                        System.out.println("La cantidad de vecinos vivos  de "+this.row+","+this.col+" es: "+this.neighborsAlive+"vecinos");
                        break;

                    }
                    else{

                        this.neighborsAlive += i;
                    }
//                    if (this.row == 1 && this.col == 1) {
//
//                        System.out.println("Soy consumidor para  "+this.row+","+this.col+"" );
//
//                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //TODO:: todo esto deberia ir en clases aparte para que sea mas legible y entendible
        }else{

                try {
//                    if (this.row == 1 && this.col == 1) {
//                        System.out.println("Soy productor para  "+this.row+","+this.col+"" );
//                    }
                    int iAlive = iAmProductorAlive();
                    this.buff.almacenar(iAlive);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


        }

    }

    public boolean isAliveCell() {
        return alive;
    }

    public int iAmProductorAlive( ){
        return this.alive ? 1 : 0;
    }

    public int getNeighborsAlive(){return this.neighborsAlive;}

    public void setAlive(boolean status) {
        this.alive = status;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(int neighbors) {
        this.neighbors = neighbors;

    }

}
