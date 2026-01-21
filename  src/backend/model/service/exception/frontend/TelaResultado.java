package frontend;

import javax.swing.*;
import java.awt.*;

/**
 * Tela de resultado final do quiz
 * 
 * @author  [Helberth Renan Gomes de sousa]
 * @version 1.0
 */
public class TelaResultado extends JFrame {
    private String nickname;
    private int pontuacao;
    
    public TelaResultado(String nickname, int pontuacao) {
        this.nickname = nickname;
        this.pontuacao = pontuacao;
        
        configurarJanela();
        criarComponentes();
        setVisible(true);
    }
    
    private void configurarJanela() {
        setTitle("Quiz POO - Resultado Final");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void criarComponentes() {
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        painelPrincipal.setBackground(new Color(240, 248, 255));
        
        // Título
        JLabel lblTitulo = new JLabel("🎉 QUIZ FINALIZADO! 🎉");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(70, 130, 180));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Nickname
        JLabel lblNickname = new JLabel("Jogador: " + nickname);
        lblNickname.setFont(new Font("Arial", Font.BOLD, 20));
        lblNickname.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Pontuação
        JPanel painelPontuacao = new JPanel();
        painelPontuacao.setBackground(new Color(255, 215, 0));
        painelPontuacao.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(218, 165, 32), 3),
            BorderFactory.createEmptyBorder(20, 40, 20, 40)
        ));
        
        JLabel lblPontuacao = new JLabel(String.format("⭐ %d PONTOS ⭐", pontuacao));
        lblPontuacao.setFont(new Font("Arial", Font.BOLD, 32));
        lblPontuacao.setForeground(new Color(139, 69, 19));
        painelPontuacao.add(lblPontuacao);
        
        // Mensagem
        String mensagem;
        if (pontuacao >= 150) {
            mensagem = "🏆 EXCELENTE! Você é um mestre em POO!";
        } else if (pontuacao >= 100) {
            mensagem = "👏 MUITO BOM! Continue estudando!";
        } else if (pontuacao >= 50) {
            mensagem = "👍 BOM! Há espaço para melhorar!";
        } else {
            mensagem = "📚 Continue praticando! Você vai melhorar!";
        }
        
        JLabel lblMensagem = new JLabel(mensagem);
        lblMensagem.setFont(new Font("Arial", Font.ITALIC, 16));
        lblMensagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Botão voltar
        JButton btnVoltar = new JButton("↩ Voltar ao Início");
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 16));
        btnVoltar.setBackground(new Color(70, 130, 180));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVoltar.setPreferredSize(new Dimension(200, 45));
        btnVoltar.setMaximumSize(new Dimension(200, 45));
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaInicial();
        });
        
        // Adicionar componentes
        painelPrincipal.add(lblTitulo);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));
        painelPrincipal.add(lblNickname);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 30)));
        painelPrincipal.add(painelPontuacao);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 30)));
        painelPrincipal.add(lblMensagem);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 40)));
        painelPrincipal.add(btnVoltar);
        
        add(painelPrincipal);
    }
}