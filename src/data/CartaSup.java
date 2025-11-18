package src.data;

public class CartaSup extends CartaP {
    private String efeito;

     public CartaSup(String nome, String tipo, double poder, int custo, String efeito, String descricao) {
        super(nome, "SUPORTE", (int) poder, custo, descricao);
        this.efeito = efeito;
    }
    public String getEfeito() {
        return efeito;
    }
}
