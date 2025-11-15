package src.data;

public class CartaP{
    private String nome;
    private String tipo;
    private int poder;
    private int custo;
    private String descricao;

    public CartaP(String nome, String tipo, int poder, int custo, String descricao){
        this.nome = nome;
        this.tipo = tipo;
        this.poder = poder;
        this.custo = custo;
        this.descricao = descricao;
    }

    public void get(){
        System.out.println("Nome: " + nome + ", Tipo: " + tipo + ", Poder: " + poder +
        ", Custo: " + custo + ", Descricao: " + descricao);
    }
}