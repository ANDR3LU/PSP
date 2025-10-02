import java.util.Arrays;

public class Ejercico4 {

    public static class Parking {
        private int[] plazas;

        public Parking(int numPlazas) {
            this.plazas = new int[numPlazas];
            Arrays.fill(this.plazas, -1);
        }

        // Intenta a entrar a una plaza vacia (-1). Si entra deja su id y devuelve en que plaza está.
        // Si esta llena devuelve mernso uno para volver a intentarlo (while del run de Conductor)
        public synchronized int intentarEntrar(int idConductor) {
            for (int i = 0; i < plazas.length; i++) {
                if (plazas[i] == -1) { 
                    plazas[i] = idConductor; 
                    System.out.println("-- ENTRA  C-" + idConductor + " en plaza " + i);
                    mostrarEstado();
                    return i;
                }
            }
            return -1; 
        }

        // El conductor(hilo) sale de la plaza y lo avisa
        public synchronized void salir(int plaza, int idConductor) {
            plazas[plaza] = -1; 
            System.out.println("<< SALE   C-" + idConductor + " de plaza " + plaza);
            mostrarEstado();
        }

        // Muestra visualmente el parking
        private void mostrarEstado() {
            for (int i = 0; i < plazas.length; i++) {
                if (plazas[i] == -1)
                    System.out.print("[" + i + ": --] ");
                else
                    System.out.print("[" + i + ": C-" + plazas[i] + "] ");
            }
            System.out.println("\n");
        }

    }

    public static class Conductor extends Thread {
        private  int id;
        private  Parking parking;

        public Conductor(int id, Parking parking) {
            this.id = id;
            this.parking = parking;
        }

        // Itenta aparcar , y al aparcar espera un timepo aleatorio aparcado.
        // Al no conseguir entrar lo vuelve a intentar en otro tiempo aleatorio
        @Override
        public void run() {
            int plaza = -1;

            while (plaza == -1) {
                plaza = parking.intentarEntrar(id);
                if (plaza == -1) {
                    try {
                        Thread.sleep(50 + (int) (Math.random() * 100));
                    } catch (InterruptedException e) {
                    }
                }
            }
           
            try {
                Thread.sleep(1000 + (int) (Math.random() * 4000));
            } catch (InterruptedException e) {
            }

            parking.salir(plaza, id);
        }
    }

     public static void main(String[] args) {
        final int NUM_PLAZAS = 10;
        final int NUM_CONDUCTORES = 50;

        Parking parking = new Parking(NUM_PLAZAS);

        Thread[] hilos = new Thread[NUM_CONDUCTORES];
        for (int i = 0; i < NUM_CONDUCTORES; i++) {
            hilos[i] = new Conductor(i, parking);
            hilos[i].start();
        }

        System.out.println("Fin del main.");
    }
}

