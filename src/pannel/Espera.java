package pannel;

public class Espera {

    public static void esperar(double tempoSegundos) {
        try {
            long tempoMs = (long) (tempoSegundos * 1000); // converte double → long
            Thread.sleep(tempoMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
