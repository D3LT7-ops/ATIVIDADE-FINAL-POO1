package frontend;

import backend.service.QuizService;
import backend.exception.QuizException;
import javax.swing.*;
import java.awt.*;

/**
 * Tela de configurações do quiz
 * 
 * @author  [Helberth Renan Gomes de sousa]
 * @version 1.0
 */
public class TelaConfiguracao extends JDialog {
    private QuizService quizService;
    private TelaInicial telaInicial;
    private JSpinner spinnerQuantidade;
    
    public TelaConfiguracao(TelaInicial telaInicial, QuizService quizService) {
        super(telaInicial, "Configurações", true);
        this.telaInicial = telaInicial;
        this.quizService = quizService;
        
        configurarJanela();
        criarComponentes();
        setVisible(true);
    }
    
    private void configurarJanela() {
        setSize(400, 250);
        setLocationRelativeTo(telaInicial);
        setResizable(false);
    }
    
    private void criarComponentes() {
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelPrincipal.setBackground(new Color(240, 248, 255));
        
        // Título
        JLabel lblTitulo = new JLabel("⚙ Configurações do Quiz");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Painel quantidade
        JPanel painelQuantidade = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        painelQuantidade.setBackground(new Color(240, 248, 255));
        
        JLabel lblQuantidade = new JLabel("Quantidade de perguntas:");
        lblQuantidade.setFont(new Font("Arial", Font.PLAIN, 14));
        
        SpinnerNumberModel model = new SpinnerNumberModel(10, 5, 20, 1);
        spinnerQuantidade = new JSpinner(model);
        spinnerQuantidade.setFont(new Font("Arial", Font.BOLD, 14));
        ((JSpinner.DefaultEditor) spinnerQuantidade.getEditor()).getTextField().setEditable(false);
        
        // Carrega valor atual
        try {
            int quantidadeAtual = quizService.getQuantidadePerguntasConfigurada();
            spinnerQuantidade.setValue(quantidadeAtual);
        } catch (QuizException e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar configuração: " + e.getMessage(),
                "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        
        painelQuantidade.add(lblQuantidade);
        painelQuantidade.add(spinnerQuantidade);
        
        // Info
        JLabel lblInfo = new JLabel("<html><center>Mínimo: 5 perguntas<br>Máximo: 20 perguntas<br>Padrão: 10 perguntas</center></html>");
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 12));
        lblInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblInfo.setForeground(Color.GRAY);
        
        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        painelBotoes.setBackground(new Color(240, 248, 255));
        
        JButton btnSalvar = new JButton("✓ Salvar");
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalvar.setBackground(new Color(34, 139, 34));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setPreferredSize(new Dimension(120, 35));
        btnSalvar.addActionListener(e -> salvar());
        
        JButton btnCancelar = new JButton("✗ Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnCancelar.setBackground(new Color(128, 128, 128));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setpainelBotoes.add(btnSalvar);
    painelBotoes.add(btnCancelar);
    
    // Adicionar tudo
    painelPrincipal.add(lblTitulo);
    painelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));
    painelPrincipal.add(painelQuantidade);
    painelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
    painelPrincipal.add(lblInfo);
    painelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));
    painelPrincipal.add(painelBotoes);
    
    add(painelPrincipal);
}

private void salvar() {
    int quantidade = (Integer) spinnerQuantidade.getValue();
    
    try {
        quizService.salvarConfiguracao(quantidade);
        JOptionPane.showMessageDialog(this,
            "Configuração salva com sucesso!\nQuantidade de perguntas: " + quantidade,
            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        telaInicial.recarregar();
        dispose();
    } catch (QuizException e) {
        JOptionPane.showMessageDialog(this,
            "Erro ao salvar configuração: " + e.getMessage(),
            "Erro", JOptionPane.ERROR_MESSAGE);
    }
}