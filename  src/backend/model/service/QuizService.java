package backend.service;

import backend.model.Frase;
import backend.model.Configuracao;
import backend.exception.QuizException;
import java.util.*;

/**
 * API principal do backend para gerenciamento do quiz
 * 
 * @author  [Helberth Renan Gomes de sousa]
 * @version 1.0
 */
public class QuizService {
    private List<Frase> todasFrases;
    private List<Frase> frasesDoQuiz;
    private int pontuacaoAtual;
    private Configuracao configuracao;
    private CSVImporter csvImporter;
    private ConfiguracaoService configService;
    private PontuacaoService pontuacaoService;
    
    public QuizService() {
        this.csvImporter = new CSVImporter();
        this.configService = new ConfiguracaoService();
        this.pontuacaoService = new PontuacaoService();
        this.pontuacaoAtual = 0;
    }
    
    public void carregarFrases(String caminhoCSV) throws QuizException {
        this.todasFrases = csvImporter.importarFrases(caminhoCSV);
    }
    
    public List<Frase> getTodasFrases() {
        return new ArrayList<>(todasFrases);
    }
    
    public void iniciarNovoQuiz() throws QuizException {
        if (todasFrases == null || todasFrases.isEmpty()) {
            throw new QuizException("Nenhuma frase carregada. Carregue o arquivo CSV primeiro.");
        }
        
        configuracao = configService.carregarConfiguracao();
        int quantidade = configuracao.getQuantidadePerguntas();
        
        if (quantidade > todasFrases.size()) {
            quantidade = todasFrases.size();
        }
        
        // Seleciona frases aleatórias
        List<Frase> copia = new ArrayList<>(todasFrases);
        Collections.shuffle(copia);
        frasesDoQuiz = copia.subList(0, quantidade);
        pontuacaoAtual = 0;
    }
    
    public List<Frase> getFrasesDoQuiz() {
        return new ArrayList<>(frasesDoQuiz);
    }
    
    public Frase getFraseAleatoria() throws QuizException {
        if (todasFrases == null || todasFrases.isEmpty()) {
            throw new QuizException("Nenhuma frase disponível");
        }
        
        Random random = new Random();
        int indice = random.nextInt(todasFrases.size());
        return todasFrases.get(indice);
    }
    
    public void processarResposta(Frase frase, boolean respostaUsuario, int tempoGasto) throws QuizException {
        if (frase == null) {
            throw new QuizException("Frase não pode ser nula");
        }
        
        if (tempoGasto < 1 || tempoGasto > 60) {
            return; // Sem pontuação se tempo inválido
        }
        
        if (frase.getResposta() == respostaUsuario) {
            // Fórmula: 1200 * nd / (s * qp)
            int nd = frase.getNivelDificuldadeNumerico();
            int qp = configuracao.getQuantidadePerguntas();
            int pontos = (int) Math.round((1200.0 * nd) / (tempoGasto * qp));
            pontuacaoAtual += pontos;
        }
    }
    
    public int getPontuacaoAtual() {
        return pontuacaoAtual;
    }
    
    public void salvarPontuacao(String nickname) throws QuizException {
        pontuacaoService.salvarPontuacao(nickname, pontuacaoAtual);
    }
    
    public void salvarConfiguracao(int quantidadePerguntas) throws QuizException {
        Configuracao config = new Configuracao(quantidadePerguntas);
        configService.salvarConfiguracao(config);
    }
    
    public int getQuantidadePerguntasConfigurada() throws QuizException {
        Configuracao config = configService.carregarConfiguracao();
        return config.getQuantidadePerguntas();
    }
}