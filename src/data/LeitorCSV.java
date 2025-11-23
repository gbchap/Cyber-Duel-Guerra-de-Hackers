package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LeitorCSV {

    //ATAQUE e DEFESA
    public static ArrayList<CartaP> lerAtaqueEdefesa(String caminho) {

        ArrayList<CartaP> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            boolean cabecalho = true;

            while ((linha = br.readLine()) != null) {

                if (cabecalho) { 
                    cabecalho = false; 
                    continue; 
                }

                String[] p = linha.split(",");

                String nome = p[0];
                String tipo = p[1];
                int poder = Integer.parseInt(p[2]);
                int custo = Integer.parseInt(p[3]);
                String descricao = p[4];
                String efeito = "";

                lista.add(new CartaP(nome, tipo, poder, custo, efeito, descricao));
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler ataque.csv: " + e.getMessage());
        }

        return lista;
    }

    //SUPORTE
    public static ArrayList<CartaP> lerSuporte(String caminho) {

        ArrayList<CartaP> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {

            String linha;
            boolean cabecalho = true;

            while ((linha = br.readLine()) != null) {

                if (cabecalho) { 
                    cabecalho = false; 
                    continue; 
                }

                String[] p = linha.split(",");

                String nome = p[0];
                String tipo = p[1];
                double poder = Double.parseDouble(p[2]); // 0.2
                int custo = Integer.parseInt(p[3]);
                String efeito = p[4];
                String descricao = p[5];

                lista.add(new CartaP(nome, tipo, poder, custo, efeito, descricao));
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler suporte.csv: " + e.getMessage());
        }

        return lista;
    }
}

