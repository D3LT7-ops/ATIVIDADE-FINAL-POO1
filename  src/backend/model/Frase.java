package backend.model;

/**
 * TAD (Tipo Abstrato de Dados) representando uma frase do quiz
 * 
 * @author  [Helberth Renan Gomes de sousa]
 * @version 1.0
 */
public class Frase {
    private int id;
    private String fraseAssertiva;
    private int categoria;
    private boolean resposta; // true = V, false = F
    private char nivelDificuldade; // 'F', 'M' ou 'D'
    
    public Frase(int id, String fraseAssertiva, int categoria, boolean resposta, char nivelDificuldade) {
        this.id = id;
        this.fraseAssertiva = fraseAssertiva;
        this.categoria = categoria;
        this.resposta = resposta;
        this.nivelDificuldade = nivelDificuldade;
    }
    
    public int getId() {
        return id;
    }
    
    public String getFraseAssertiva() {
        return fraseAssertiva;
    }
    
    public int getCategoria() {
        return categoria;
    }
    
    public boolean getResposta() {
        return resposta;
    }
    
    public char getNivelDificuldade() {
        return nivelDificuldade;
    }
    
    public int getNivelDificuldadeNumerico() {
        switch(nivelDificuldade) {
            case 'F': return 1;
            case 'M': return 2;
            case 'D': return 3;
            default: return 1;
        }
    }
    
    @Override
    public String toString() {
        return "ID: " + id + " | " + fraseAssertiva + " | Nível: " + nivelDificuldade;
    }
}