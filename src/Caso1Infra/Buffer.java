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
            this.buff.add(i);
            notify();
        }
        public synchronized Integer retirar () throws InterruptedException {
            Integer a;
            if (this.buff.isEmpty()){
                 a = null;
            }

            else {
                a = (Integer) buff.remove (0) ;
                notify (); ;

            }
            return a ;

        }

}
