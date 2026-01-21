package backend.model;

/**
 * Classe para armazenar configurações do quiz
 * 
 * @author [Helberth Renan Gomes de sousa]
 * @version 1.0
 */
public class Configuracao {
    private int quantidadePerguntas;
    
    public Configuracao() {
        this.quantidadePerguntas = 10; // Valor padrão
    }
    
    public Configuracao(int quantidadePerguntas) {
        setQuantidadePerguntas(quantidadePerguntas);
    }
    
    public int getQuantidadePerguntas() {
        return quantidadePerguntas;
    }
    
    public void setQuantidadePerguntas(int quantidadePerguntas) {
        if (quantidadePerguntas < 5 || quantidadePerguntas > 20) {
            this.quantidadePerguntas = 10; // Valor padrão se inválido
        } else {
            this.quantidadePerguntas = quantidadePerguntas;
        }
    }
}