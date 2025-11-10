public class Main {
    public static void main(String[] args) {
        // carregando as cartas:

        GerenciadorCartas gerenciador = new GerenciadorCartas();
        gerenciador.carregarCartas();
        System.out.println("Total de cartas carregadas: " + gerenciador.getTotalCartas());
    }
}
