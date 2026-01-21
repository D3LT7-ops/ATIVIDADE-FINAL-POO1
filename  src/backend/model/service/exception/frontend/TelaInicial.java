package frontend;

import backend.service.QuizService;
import backend.service.PontuacaoService;
import backend.model.Jogador;
import backend.exception.QuizException;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Tela inicial do quiz com entrada de nickname e ranking
 * 
 * @author  [Helberth Renan Gomes de sousa]
 * @version 1.0
 */
public class TelaInicial extends JFrame {
    private QuizService quizService;
    private PontuacaoService pontuacaoService;
    private JTextField txtNickname;
    private JTextArea txtRanking;
    
    public TelaInicial() {
        quizService = new QuizService();
        pontuacaoService = new PontuacaoService();
        
        // Carrega frases do CSV
        try {
            quizService.carregarFrases("data/quiz_database.csv");
        } catch (QuizException e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar banco de dados: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        configurarJanela();
        criarComponentes();
        atualizarRanking();
        setVisible(true);
    }
    
    private void configurarJanela() {
        setTitle("Quiz POO - Bem-vindo!");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void criarComponentes() {
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelPrincipal.setBackground(new Color(240, 248, 255));
        
        // Painel superior - Título
        JPanel painelTitulo = new JPanel();
        painelTitulo.setBackground(new Color(70, 130, 180));
        JLabel lblTitulo = new JLabel("🎯 QUIZ POO - VERDADEIRO OU FALSO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        painelTitulo.add(lblTitulo);
        
        // Painel central
        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.setBackground(new Color(240, 248, 255));
        
        // Explicação do jogo
        JTextArea txtExplicacao = new JTextArea();
        txtExplicacao.setText(
            "📖 COMO JOGAR:\n\n" +
            "1. Digite seu nickname e clique em 'Iniciar Quiz'\n" +
            "2. Você terá 60 segundos para responder cada pergunta\n" +
            "3. Escolha entre VERDADEIRO ou FALSO\n" +
            "4. Sua pontuação depende do nível de dificuldade e tempo de resposta\n" +
            "5. Fórmula: 1200 × nível ÷ (tempo × total_perguntas)\n\n" +
            "🏆 Tente entrar no TOP 3!"
        );
        txtExplicacao.setEditable(false);
        txtExplicacao.setFont(new Font("Arial", Font.PLAIN, 13));
        txtExplicacao.setBackground(new Color(255, 255, 224));
        txtExplicacao.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(218, 165, 32), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Campo de nickname
        JPanel painelNickname = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelNickname.setBackground(new Color(240, 248, 255));
        JLabel lblNickname = new JLabel("Nickname:");
        lblNickname.setFont(new Font("Arial", Font.BOLD, 14));
        txtNickname = new JTextField(20);
        txtNickname.setFont(new Font("Arial", Font.PLAIN, 14));
        painelNickname.add(lblNickname);
        painelNickname.add(txtNickname);
        
        // Ranking TOP 3
        JPanel painelRanking = new JPanel(new BorderLayout());
        painelRanking.setBackground(new Color(240, 248, 255));
        JLabel lblRanking = new JLabel("🏆 TOP 3 - MELHORES PONTUAÇÕES");
        lblRanking.setFont(new Font("Arial", Font.BOLD, 14));
        lblRanking.setHorizontalAlignment(SwingConstants.CENTER);
        
        txtRanking = new JTextArea(4, 40);
        txtRanking.setEditable(false);
        txtRanking.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtRanking.setBackground(new Color(245, 245, 245));
        JScrollPane scrollRanking = new JScrollPane(txtRanking);
        
        painelRanking.add(lblRanking, BorderLayout.NORTH);
        painelRanking.add(scrollRanking, BorderLayout.CENTER);
        
        painelCentral.add(txtExplicacao);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 15)));
        painelCentral.add(painelNickname);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 15)));
        painelCentral.add(painelRanking);
        
        // Painel inferior - Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        painelBotoes.setBackground(new Color(240, 248, 255));
        
        JButton btnIniciar = new JButton("▶ Iniciar Quiz");
        btnIniciar.setFont(new Font("Arial", Font.BOLD, 16));
        btnIniciar.setBackground(new Color(34, 139, 34));
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setFocusPainted(false);
        btnIniciar.setPreferredSize(new Dimension(180, 40));
        btnIniciar.addActionListener(e -> iniciarQuiz());
        
        JButton btnConfig = new JButton("⚙ Configurações");
        btnConfig.setFont(new Font("Arial", Font.PLAIN, 14));
        btnConfig.setBackground(new Color(70, 130, 180));
        btnConfig.setForeground(Color.WHITE);
        btnConfig.setFocusPainted(false);
        btnConfig.setPreferredSize(new Dimension(180, 40));
        btnConfig.addActionListener(e -> abrirConfiguracoes());
        
        painelBotoes.add(btnIniciar);
        painelBotoes.add(btnConfig);
        
        painelPrincipal.add(painelTitulo, BorderLayout.NORTH);
        painelPrincipal.add(painelCentral, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        
        add(painelPrincipal);
    }
    
    private void atualizarRanking() {
        try {
            List<Jogador> top3 = pontuacaoService.getTop3();
            StringBuilder sb = new StringBuilder();
            
            if (top3.isEmpty()) {
                sb.append("Nenhuma pontuação registrada ainda.\nSeja o primeiro!");
            } else {
                String[] medalhas = {"🥇", "🥈", "🥉"};
                for (int i = 0; i < top3.size(); i++) {
                    Jogador j = top3.get(i);
                    sb.append(String.format("%s %d° - %-20s %6d pts\n", 
                        medalhas[i], i + 1, j.getNickname(), j.getPontuacao()));
                }
            }
            
            txtRanking.setText(sb.toString());
        } catch (QuizException e) {
            txtRanking.setText("Erro ao carregar ranking: " + e.getMessage());
        }
    }
    
    private void iniciarQuiz() {
        String nickname = txtNickname.getText().trim();
        
        if (nickname.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor, digite seu nickname!",
                "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            quizService.iniciarNovoQuiz();
            dispose();
            new TelaQuiz(quizService, nickname);
        } catch (QuizException e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao iniciar quiz: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void abrirConfiguracoes() {
        new TelaConfiguracao(this, quizService);
    }
    
    public void recarregar() {
        atualizarRanking();
    }
}