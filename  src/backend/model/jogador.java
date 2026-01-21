package backend.model;

/**
 * Classe representando um jogador do quiz
 * 
 * @author  [Helberth Renan Gomes de sousa]
 * @version 1.0
 */
public class Jogador implements Comparable<Jogador> {
    private String nickname;
    private int pontuacao;
    
    public Jogador(String nickname, int pontuacao) {
        this.nickname = nickname;
        this.pontuacao = pontuacao;
    }
    
    public String getNickname() {
        return nickname;
    }
    
    public int getPontuacao() {
        return pontuacao;
    }
    
    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }
    
    @Override
    public int compareTo(Jogador outro) {
        return Integer.compare(outro.pontuacao, this.pontuacao); // Ordem decrescente
    }
    
    @Override
    public String toString() {
        return nickname + ": " + pontuacao + " pontos";
    }
}