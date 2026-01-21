package frontend;

import backend.service.QuizService;
import backend.model.Frase;
import backend.exception.QuizException;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Tela principal do quiz com perguntas e timer
 * 
 * @author  [Helberth Renan Gomes de sousa]
 * @version 1.0
 */
public class TelaQuiz extends JFrame {
    private QuizService quizService;
    private String nickname;
    private List<Frase> frases;
    private int indiceAtual;
    private int tempoRestante;
    private Timer timer;
    
    private JLabel lblPergunta;
    private JLabel lblNumero;
    private JLabel lblTimer;
    private JLabel lblNivel;
    private JProgressBar progressBar;
    private JButton btnVerdadeiro;
    private JButton btnFalso;
    
    public TelaQuiz(QuizService quizService, String nickname) {
        this.quizService = quizService;
        this.nickname = nickname;
        this.frases = quizService.getFrasesDoQuiz();
        this.indiceAtual = 0;
        this.tempoRestante = 60;
        
        configurarJanela();
        criarComponentes();
        exibirPergunta();
        iniciarTimer();
        setVisible(true);
    }
    
    private void configurarJanela() {
        setTitle("Quiz POO - " + nickname);
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void criarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(15, 15));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelPrincipal.setBackground(new Color(240, 248, 255));
        
        // Painel superior - Info
        JPanel painelInfo = new JPanel(new BorderLayout());
        painelInfo.setBackground(new Color(70, 130, 180));
        painelInfo.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JPanel painelInfoEsq = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelInfoEsq.setBackground(new Color(70, 130, 180));
        lblNumero = new JLabel();
        lblNumero.setFont(new Font("Arial", Font.BOLD, 14));
        lblNumero.setForeground(Color.WHITE);
        lblNivel = new JLabel();
        lblNivel.setFont(new Font("Arial", Font.BOLD, 14));
        lblNivel.setForeground(Color.YELLOW);
        painelInfoEsq.add(lblNumero);
        painelInfoEsq.add(Box.createHorizontalStrut(20));
        painelInfoEsq.add(lblNivel);
        
        lblTimer = new JLabel();
        lblTimer.setFont(new Font("Arial", Font.BOLD, 18));
        lblTimer.setForeground(Color.WHITE);
        
        painelInfo.add(painelInfoEsq, BorderLayout.WEST);
        painelInfo.add(lblTimer, BorderLayout.EAST);
        
        // Painel central - Pergunta
        JPanel painelPergunta = new JPanel(new BorderLayout(10, 10));
        painelPergunta.setBackground(Color.WHITE);
        painelPergunta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 3),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        
        lblPergunta = new JLabel();
        lblPergunta.setFont(new Font("Arial", Font.PLAIN, 18));
        lblPergunta.setHorizontalAlignment(SwingConstants.CENTER);
        lblPergunta.setVerticalAlignment(SwingConstants.CENTER);
        
        progressBar = new JProgressBar(0, 60);
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(34, 139, 34));
        
        painelPergunta.add(lblPergunta, BorderLayout.CENTER);
        painelPergunta.add(progressBar, BorderLayout.SOUTH);
        
        // Painel inferior - Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        painelBotoes.setBackground(new Color(240, 248, 255));
        
        btnVerdadeiro = new JButton("✓ VERDADEIRO");
        btnVerdadeiro.setFont(new Font("Arial", Font.BOLD, 18));
        btnVerdadeiro.setBackground(new Color(34, 139, 34));
        btnVerdadeiro.setForeground(Color.WHITE);
        btnVerdadeiro.setFocusPainted(false);
        btnVerdadeiro.setPreferredSize(new Dimension(200, 60));
        btnVerdadeiro.addActionListener(e -> processarResposta(true));
        
        btnFalso = new JButton("✗ FALSO");
        btnFalso.setFont(new Font("Arial", Font.BOLD, 18));
        btnFalso.setBackground(new Color(220, 20, 60));
        btnFalso.setForeground(Color.WHITE);
        btnFalso.setFocusPainted(false);
        btnFalso.setPreferredSize(new Dimension(200, 60));
        btnFalso.addActionListener(e -> processarResposta(false));
        
        painelBotoes.add(btnVerdadeiro);
        painelBotoes.add(btnFalso);
        
        painelPrincipal.add(painelInfo, BorderLayout.NORTH);
        painelPrincipal.add(painelPergunta, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        
        add(painelPrincipal);
    }
    
    private void exibirPergunta() {
        if (indiceAtual >= frases.size()) {
            finalizarQuiz();
            return;
        }
        
        Frase frase = frases.get(indiceAtual);
        lblNumero.setText(String.format("Pergunta %d/%d", indiceAtual + 1, frases.size()));
        lblPergunta.setText("<html><center>" + frase.getFraseAssertiva() + "</center></html>");
        
        String nivelTexto = "";
        switch (frase.getNivelDificuldade()) {
            case 'F': nivelTexto = "Nível: FÁCIL"; break;
            case 'M': nivelTexto = "Nível: MÉDIO"; break;
            case 'D': nivelTexto = "Nível: DIFÍCIL"; break;
        }
        lblNivel.setText(nivelTexto);
        
        tempoRestante = 60;
        progressBar.setValue(60);
        habilitarBotoes(true);
    }
    
    private void iniciarTimer() {
        timer = new Timer(1000, e -> {
            tempoRestante--;
            lblTimer.setText(String.format("⏱ %02d segundos", tempoRestante));
            progressBar.setValue(tempoRestante);
            
            if (tempoRestante <= 10) {
                progressBar.setForeground(Color.RED);
                lblTimer.setForeground(Color.RED);
            } else if (tempoRestante <= 30) {
                progressBar.setForeground(Color.ORANGE);
            }
            
            if (tempoRestante <= 0) {
                timer.stop();
                habilitarBotoes(false);
                JOptionPane.showMessageDialog(this,
                    "Tempo esgotado! Nenhum ponto marcado.",
                    "Tempo!", JOptionPane.WARNING_MESSAGE);
                proximaPergunta();
            }
        });
        timer.start();
    }
    
    private void processarResposta(boolean respostaUsuario) {
        timer.stop();
        habilitarBotoes(false);
        
        Frase frase = frases.get(indiceAtual);
        int tempoGasto = 60 - tempoRestante;
        
        try {
            quizService.processarResposta(frase, respostaUsuario, tempoGasto);
            
            if (frase.getResposta() == respostaUsuario) {
                JOptionPane.showMessageDialog(this,
                    "✓ Resposta CORRETA!\nTempo: " + tempoGasto + "s",
                    "Parabéns!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String respostaCorreta = frase.getResposta() ? "VERDADEIRO" : "FALSO";
                JOptionPane.showMessageDialog(this,
                    "✗ Resposta INCORRETA!\nA resposta correta é: " + respostaCorreta,
                    "Ops!", JOptionPane.ERROR_MESSAGE);
            }
        } catch (QuizException e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao processar resposta: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        proximaPergunta();
    }
    
    private void proximaPergunta() {
        indiceAtual++;
        if (indiceAtual < frases.size()) {
            progressBar.setForeground(new Color(34, 139, 34));
            lblTimer.setForeground(Color.WHITE);
            exibirPergunta();
            iniciarTimer();
        } else {
            finalizarQuiz();
        }
    }
    
    private void finalizarQuiz() {
        if (timer != null) {
            timer.stop();
        }
        
        try {
            int pontuacaoFinal = quizService.getPontuacaoAtual();
            quizService.salvarPontuacao(nickname);
            
            dispose();
            new TelaResultado(nickname, pontuacaoFinal);
        } catch (QuizException e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao salvar pontuação: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void habilitarBotoes(boolean habilitar) {
        btnVerdadeiro.setEnabled(habilitar);
        btnFalso.setEnabled(habilitar);
    }
}