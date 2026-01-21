package backend.service;

import backend.model.Jogador;
import backend.exception.QuizException;
import java.io.*;
import java.util.*;

/**
 * Serviço para gerenciar pontuações dos jogadores
 * 
 * @author  [Helberth Renan Gomes de sousa]
 * @version 1.0
 */
public class PontuacaoService {
    private static final String ARQUIVO_SCORES = "data/scores.txt";
    
    public void salvarPontuacao(String nickname, int pontuacao) throws QuizException {
        try {
            List<Jogador> jogadores = carregarPontuacoes();
            
            // Procura se o jogador já existe
            boolean encontrado = false;
            for (Jogador j : jogadores) {
                if (j.getNickname().equalsIgnoreCase(nickname)) {
                    // Atualiza apenas se a nova pontuação for maior
                    if (pontuacao > j.getPontuacao()) {
                        j.setPontuacao(pontuacao);
                    }
                    encontrado = true;
                    break;
                }
            }
            
            // Se não existe, adiciona novo jogador
            if (!encontrado) {
                jogadores.add(new Jogador(nickname, pontuacao));
            }
            
            // Salva todos os jogadores
            try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_SCORES))) {
                for (Jogador j : jogadores) {
                    writer.println(j.getNickname() + "," + j.getPontuacao());
                }
            }
            
        } catch (IOException e) {
            throw new QuizException("Erro ao salvar pontuação", e);
        }
    }
    
    public List<Jogador> carregarPontuacoes() throws QuizException {
        List<Jogador> jogadores = new ArrayList<>();
        File arquivo = new File(ARQUIVO_SCORES);
        
        if (!arquivo.exists()) {
            return jogadores; // Retorna lista vazia
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 2) {
                    String nickname = partes[0].trim();
                    int pontuacao = Integer.parseInt(partes[1].trim());
                    jogadores.add(new Jogador(nickname, pontuacao));
                }
            }
        } catch (IOException | NumberFormatException e) {
            throw new QuizException("Erro ao carregar pontuações", e);
        }
        
        return jogadores;
    }
    
    public List<Jogador> getTop3() throws QuizException {
        List<Jogador> todos = carregarPontuacoes();
        Collections.sort(todos);
        
        int limite = Math.min(3, todos.size());
        return todos.subList(0, limite);
    }
}