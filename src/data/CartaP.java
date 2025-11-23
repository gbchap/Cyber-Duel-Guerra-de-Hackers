package data;

public class CartaP{
    private String nome;
    private String tipo;
    private double poder;
    private int custo;
    private String efeito;
    private String descricao;

    public CartaP(String nome, String tipo, double poder, int custo, String efeito, String descricao){
        this.nome = nome;
        this.tipo = tipo;
        this.poder = poder;
        this.custo = custo;
        this.efeito = efeito;
        this.descricao = descricao;
    }

    public String getTipo(){
        return tipo;
    }

    public String getNome(){
        return nome;
    }

    public int getCusto(){
        return custo;
    }

    public String getEfeito(){
        return efeito;
    }

    public double getPoder(){
        return poder;
    }

    public void imprime() {
        if (this.tipo.equals("SUPORTE")) {
            System.out.println(
                "Nome: " + nome +
                " | Tipo: " + tipo +
                " | Poder: " + poder +
                " | Custo: " + custo +
                " | Efeito: " + efeito +
                " | Descricao: " + descricao
            );
        } else {
            System.out.println(
                "Nome: " + nome +
                " | Tipo: " + tipo +
                " | Poder: " + poder +
                " | Custo: " + custo +
                " | Descricao: " + descricao
            );
        }
    }

}