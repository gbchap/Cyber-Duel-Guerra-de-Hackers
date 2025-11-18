package src.data;

public class CartaSup extends CartaP {
    private String efeito;

    public CartaSup(String nome, int tipo, double poder, String efeito, String descricao) {
        super(nome, "SUPORTE", poder, efeito, descricao); 
        this.efeito = efeito;
    }
    public String getEfeito() {
        return efeito;
    }
}
