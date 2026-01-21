package test;

import backend.service.QuizService;
import backend.model.Frase;
import backend.exception.QuizException;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Classe de teste para a API backend
 * 
 * @author [Helberth Renan Gomes de sousa]
 * @version 1.0
 */
public class TestBackend extends JFrame {
    private QuizService quizService;
    private JTextArea txtResultado;
    
    public TestBackend() {
        quizService = new QuizService();
        
        setTitle("Teste Backend - API Quiz");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        criarComponentes();
        setVisible(true);
    }
    
    private void criarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        txtResultado = new JTextArea();
        txtResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtResultado.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtResultado);
        
        JPanel painelBotoes = new JPanel(new GridLayout(4, 2, 10, 10));
        
        JButton btnCarregar = new JButton("1. Carregar CSV");
        btnCarregar.addActionListener(e -> testarCarregarCSV());
        
        JButton btnListar = new JButton("2. Listar Todas");
        btnListar.addActionListener(e -> testarListarTodas());
        
        JButton btnAleatorio = new JButton("3. Frase Aleatória");
        btnAleatorio.addActionListener(e -> testarFraseAleatoria());
        
        JButton btnIniciar = new JButton("4. Iniciar Quiz");
        btnIniciar.addActionListener(e -> testarIniciarQuiz());
        
        JButton btnResposta = new JButton("5. Processar Resposta");
        btnResposta.addActionListener(e -> testarProcessarResposta());
        
        JButton btnConfig = new JButton("6. Configuração");
        btnConfig.addActionListener(e -> testarConfiguracao());
        
        JButton btnPontuacao = new JButton("7. Salvar Pontuação");
        btnPontuacao.addActionListener(e -> testarSalvarPontuacao());
        
        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> txtResultado.setText(""));
        
        painelBotoes.add(btnCarregar);
        painelBotoes.add(btnListar);
        painelBotoes.add(btnAleatorio);
        painelBotoes.add(btnIniciar);
        painelBotoes.add(btnResposta);
        painelBotoes.add(btnConfig);
        painelBotoes.add(btnPontuacao);
        painelBotoes.add(btnLimpar);
        
        painelPrincipal.add(scroll, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        
        add(painelPrincipal);
    }
    
    private void testarCarregarCSV() {
        try {
            quizService.carregarFrases("data/quiz_database.csv");
            log("✓ CSV carregado com sucesso!");
        } catch (QuizException e) {
            log("✗ ERRO: " + e.getMessage());
        }
    }
    
    private void testarListarTodas() {
        try {
            List<Frase> frases = quizService.getTodasFrases();
            log("=== TODAS AS FRASES (" + frases.size() + ") ===");
            for (Frase f : frases) {
                log(f.toString());
            }
        } catch (Exception e) {
            log("✗ ERRO: " + e.getMessage());
        }
    }
    
    private void testarFraseAleatoria() {
        try {
            Frase frase = quizService.getFraseAleatoria();
            log("=== FRASE ALEATÓRIA ===");
            log(frase.toString());
            log("Resposta: " + (frase.getResposta() ? "VERDADEIRO" : "FALSO"));
        } catch (QuizException e) {
            log("✗ ERRO: " + e.getMessage());
        }
    }
    
    private void testarIniciarQuiz() {
        try {
            quizService.iniciarNovoQuiz();
            List<Frase> frases = quizService.getFrasesDoQuiz();
            log("=== QUIZ INICIADO ===");
            log("Quantidade de perguntas: " + frases.size());
            for (int i = 0; i < frases.size(); i++) {
                log((i + 1) + ". " + frases.get(i).getFraseAssertiva());
            }
        } catch (QuizException e) {
            log("✗ ERRO: " + e.getMessage());
        }
    }
    
    private void testarProcessarResposta() {
        try {
            List<Frase> frases = quizService.getFrasesDoQuiz();
            if (frases.isEmpty()) {
                log("Inicie o quiz primeiro!");
                return;
            }
            
            Frase frase = frases.get(0);
            quizService.processarResposta(frase, true, 10);
            log("=== RESPOSTA PROCESSADA ===");
            log("Frase: " + frase.getFraseAssertiva());
            log("Resposta dada: VERDADEIRO");
            log("Tempo: 10 segundos");
            log("Pontuação atual: " + quizService.getPontuacaoAtual());
        } catch (QuizException e) {
            log("✗ ERRO: " + e.getMessage());
        }
    }
    
    private void testarConfiguracao() {
        try {
            quizService.salvarConfiguracao(15);
            int qtd = quizService.getQuantidadePerguntasConfigurada();
            log("=== CONFIGURAÇÃO ===");
            log("Quantidade salva: 15");
            log("Quantidade carregada: " + qtd);
        } catch (QuizException e) {
            log("✗ ERRO: " + e.getMessage());
        }
    }
    
    private void testarSalvarPontuacao() {
        try {
            quizService.salvarPontuacao("TesteUsuario");
            log("=== PONTUAÇÃO SALVA ===");
            log("Nickname: TesteUsuario");
            log("Pontos: " + quizService.getPontuacaoAtual());
        } catch (QuizException e) {
            log("✗ ERRO: " + e.getMessage());
        }
    }
    
    private void log(String mensagem) {
        txtResultado.append(mensagem + "\n");
        txtResultado.setCaretPosition(txtResultado.getDocument().getLength());
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TestBackend());
    }
}