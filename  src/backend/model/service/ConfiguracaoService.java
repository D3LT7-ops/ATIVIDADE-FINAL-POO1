package backend.service;

import backend.model.Configuracao;
import backend.exception.QuizException;
import java.io.*;

/**
 * Serviço para gerenciar configurações persistidas
 * 
 * @author  [Helberth Renan Gomes de sousa]
 * @version 1.0
 */
public class ConfiguracaoService {
    private static final String ARQUIVO_CONFIG = "data/config.txt";
    
    public void salvarConfiguracao(Configuracao config) throws QuizException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_CONFIG))) {
            writer.println(config.getQuantidadePerguntas());
        } catch (IOException e) {
            throw new QuizException("Erro ao salvar configuração", e);
        }
    }
    
    public Configuracao carregarConfiguracao() throws QuizException {
        File arquivo = new File(ARQUIVO_CONFIG);
        
        if (!arquivo.exists()) {
            return new Configuracao(); // Retorna configuração padrão
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha = reader.readLine();
            if (linha != null) {
                int quantidade = Integer.parseInt(linha.trim());
                return new Configuracao(quantidade);
            }
        } catch (IOException | NumberFormatException e) {
            throw new QuizException("Erro ao carregar configuração", e);
        }
        
        return new Configuracao();
    }
}