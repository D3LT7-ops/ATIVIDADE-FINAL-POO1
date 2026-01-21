package backend.service;

import backend.model.Frase;
import backend.exception.CSVImportException;
import java.io.*;
import java.util.*;

/**
 * Classe responsável por importar dados do arquivo CSV
 * 
 * @author  [Helberth Renan Gomes de sousa]
 * @version 1.0
 */
public class CSVImporter {
    
    public List<Frase> importarFrases(String caminhoArquivo) throws CSVImportException {
        List<Frase> frases = new ArrayList<>();
        Set<Integer> idsUsados = new HashSet<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha = br.readLine(); // Pula cabeçalho
            int numeroLinha = 1;
            
            while ((linha = br.readLine()) != null) {
                numeroLinha++;
                linha = linha.trim();
                
                if (linha.isEmpty()) {
                    continue;
                }
                
                try {
                    Frase frase = parsearLinha(linha, numeroLinha, idsUsados);
                    frases.add(frase);
                    idsUsados.add(frase.getId());
                } catch (Exception e) {
                    throw new CSVImportException("Erro na linha " + numeroLinha + ": " + e.getMessage(), e);
                }
            }
            
            if (frases.isEmpty()) {
                throw new CSVImportException("O arquivo CSV está vazio ou não contém frases válidas");
            }
            
        } catch (FileNotFoundException e) {
            throw new CSVImportException("Arquivo não encontrado: " + caminhoArquivo, e);
        } catch (IOException e) {
            throw new CSVImportException("Erro ao ler o arquivo CSV", e);
        }
        
        return frases;
    }
    
    private Frase parsearLinha(String linha, int numeroLinha, Set<Integer> idsUsados) throws CSVImportException {
        String[] partes = linha.split(",", -1);
        
        if (partes.length < 5) {
            throw new CSVImportException("Linha com menos de 5 colunas");
        }
        
        try {
            // ID
            int id = Integer.parseInt(partes[0].trim());
            if (idsUsados.contains(id)) {
                throw new CSVImportException("ID duplicado: " + id);
            }
            if (id <= 0) {
                throw new CSVImportException("ID deve ser um número positivo");
            }
            
            // Frase
            String fraseAssertiva = partes[1].trim();
            if (fraseAssertiva.isEmpty()) {
                throw new CSVImportException("Frase assertiva não pode ser vazia");
            }
            
            // Categoria
            int categoria = Integer.parseInt(partes[2].trim());
            
            // Resposta
            String respostaStr = partes[3].trim().toUpperCase();
            if (!respostaStr.equals("V") && !respostaStr.equals("F")) {
                throw new CSVImportException("Resposta deve ser 'V' ou 'F'");
            }
            boolean resposta = respostaStr.equals("V");
            
            // Nível
            String nivelStr = partes[4].trim().toUpperCase();
            if (!nivelStr.equals("F") && !nivelStr.equals("M") && !nivelStr.equals("D")) {
                throw new CSVImportException("Nível deve ser 'F', 'M' ou 'D'");
            }
            char nivel = nivelStr.charAt(0);
            
            return new Frase(id, fraseAssertiva, categoria, resposta, nivel);
            
        } catch (NumberFormatException e) {
            throw new CSVImportException("Erro ao converter número: " + e.getMessage());
        }
    }
}