package src.pannel;
import src.data.*;
//import java.io.*;
import java.util.*;

public class GerenciadorCartas {
    private Map<String, CartaP> cartas = new HashMap<>();

    public GerenciadorCartas() {
        //carregarAtaque("src/csv/ataque.csv");
        //carregarDefesa("src/csv/defesa.csv");
        //carregarSuporte("src/csv/suporte.csv");
    }

    //fzr carregar definiçoes
    

    public CartaP getCarta(String nome) {
        return cartas.get(nome);
    }    

}
