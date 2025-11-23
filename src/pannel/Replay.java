package pannel;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Replay {

    private ArrayList<String> log = new ArrayList<>();

    public void add(String linha) {
        log.add(linha);
    }

    public void salvar(String nomeArquivo) {
        try (FileWriter fw = new FileWriter(nomeArquivo)) {
            for (String l : log) {
                fw.write(l + System.lineSeparator());
            }
            System.out.println("\nREPLAY SALVO EM: " + nomeArquivo + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o replay: " + e.getMessage());
        }
    }
}
