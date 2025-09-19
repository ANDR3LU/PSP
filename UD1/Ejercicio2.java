public class Ejercicio2 extends Thread {
    static final int NUM_HILOS = 10;
    static final int NUM_INCREMENTOS = 10;
    static int contador = 0;

    public void run() {
        for (int i = 0; i < NUM_INCREMENTOS; i++) {
            contador++;

            System.out.println("Incremento: " + contador);
        }
    }

    public static void main(String[] args) {
        Ejercicio2[] hilos = new Ejercicio2[NUM_HILOS];

        for (int i = 0; i < NUM_HILOS; i++) {
            hilos[i] = new Ejercicio2();
            hilos[i].start();
        }

        for (int i = 0; i < NUM_HILOS; i++) {
            try {
                hilos[i].sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        System.out.println("\n\nValor final del contador: " + contador);
        System.out.println("Valor esperado: " + (NUM_HILOS * NUM_INCREMENTOS));
    }
}
