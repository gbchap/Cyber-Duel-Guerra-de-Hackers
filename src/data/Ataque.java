package src.data;

public class Ataque{
    private String nome;
    private String tipo;
    private int poder;
    private int custo;
    private String descricao;

    public void get(){
        System.out.println("Nome: " + nome + ", Tipo: " + tipo + ", Poder: " + poder +
        ", Custo: " + custo + ", Descricao: " + descricao);
    }
}