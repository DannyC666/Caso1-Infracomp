package Caso1Infra;

import java.util.ArrayList;

public class Buffer {

        private ArrayList buff ;
        private int size ;
        private int totalProduce;
        public Buffer (int size , int totalProduce){
            this.size = size;
            this.totalProduce = totalProduce;
            this.buff = new ArrayList();
        }
        public synchronized void almacenar (Integer i ) throws InterruptedException {
            while (this.buff.size() == this.size) {
                wait();
            }
            notifyAll();
            this.buff.add(i);
        }
        public synchronized Integer retirar () throws InterruptedException {
            // TODO:  Quitar este while
            while (this.buff.isEmpty() && this.totalProduce>0){
                wait() ;
            }

            Integer a = null;
            if (this.totalProduce > 0 ) {
                a = (Integer) buff.remove (0) ;
                notifyAll () ;
                this.totalProduce--;
            }
            return a ;

        }

}
