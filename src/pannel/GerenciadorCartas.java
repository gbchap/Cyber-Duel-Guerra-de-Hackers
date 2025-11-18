package src.pannel;
import src.data.*;
import java.io.*;
import java.util.*;

public class GerenciadorCartas {
    private Map<String, CartaP> cartas = new HashMap<>();

    public GerenciadorCartas() {
        carregarAtaque("src/csv/ataque.csv");
        carregarDefesa("src/csv/defesa.csv");
        carregarSuporte("src/csv/suporte.csv");
    }

    private void carregarAtaque(String caminho) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            br.readLine();
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] c = linha.split(",");

                CartaP carta = new CartaP(c[0], Integer.parseInt(c[1]), Integer.parseInt(c[2]), "ataque");
                cartas.put(carta.getNome(), carta);
            }
        } catch (Exception e) {}
    }

    private void carregarDefesa(String caminho) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            br.readLine();
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] c = linha.split(",");

                CartaP carta = new CartaP(c[0], Integer.parseInt(c[1]), Integer.parseInt(c[2]), "defesa");
                cartas.put(carta.getNome(), carta);
            }
        } catch (Exception e) {}
    }

    private void carregarSuporte(String caminho) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            br.readLine();
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] c = linha.split(",");

                CartaSup carta = new CartaSup(c[0], Integer.parseInt(c[1]), Integer.parseInt(c[2]), c[3]);
                cartas.put(carta.getNome(), carta);
            }
        } catch (Exception e) {}
    }

    
    public CartaP getCarta(String nome) {
        return cartas.get(nome);
    }    

}
