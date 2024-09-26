package Abela1;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class Main {
    private static GerenciadorDeLivros gerenciadorDeLivros;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::criarInterface);
    }

    private static void criarInterface() {
        JFrame frame = new JFrame("Sistema de Livros");
        frame.setSize(1300, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel listaLivrosPanel = new JPanel();
        listaLivrosPanel.setLayout(new BoxLayout(listaLivrosPanel, BoxLayout.Y_AXIS));
        listaLivrosPanel.setBackground(Color.WHITE);

        JPanel recomendadosPanel = new JPanel();
        recomendadosPanel.setLayout(new BoxLayout(recomendadosPanel, BoxLayout.Y_AXIS));
        recomendadosPanel.setBackground(Color.LIGHT_GRAY);
        recomendadosPanel.setBorder(BorderFactory.createTitledBorder("Livros Recomendados")); // Borda para o painel de recomendações
        recomendadosPanel.setMinimumSize(new Dimension(750, 600)); // Aumenta a altura mínima do painel de recomendações

        gerenciadorDeLivros = new GerenciadorDeLivros(100, listaLivrosPanel, recomendadosPanel);

        JPanel formPanel = criarPainelFormulario();
        JPanel buscaPanel = criarPainelBusca(frame);

        JButton ordenarPorAutorButton = criarBotao("Ordenar por Autor");
        JButton ordenarPorTituloButton = criarBotao("Ordenar por Título");
        ordenarPorAutorButton.addActionListener(e -> gerenciadorDeLivros.ordenarPorAutor());
        ordenarPorTituloButton.addActionListener(e -> gerenciadorDeLivros.ordenarPorTitulo());

        JPanel mainPanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(listaLivrosPanel);
        JScrollPane scrollRecomendadosPane = new JScrollPane(recomendadosPanel);
        scrollRecomendadosPane.setPreferredSize(new Dimension(350, 400)); // Define um tamanho preferido para o JScrollPane das recomendações

        JPanel botaoPanel = new JPanel();
        botaoPanel.setLayout(new BoxLayout(botaoPanel, BoxLayout.Y_AXIS));
        botaoPanel.add(ordenarPorAutorButton);
        botaoPanel.add(ordenarPorTituloButton);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buscaPanel, BorderLayout.SOUTH);
        mainPanel.add(botaoPanel, BorderLayout.WEST);
        mainPanel.add(scrollRecomendadosPane, BorderLayout.EAST); // Painel de recomendações à direita

        frame.add(formPanel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private static JPanel criarPainelFormulario() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 240));
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                "Adicionar Novo Livro", TitledBorder.CENTER, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14), new Color(70, 130, 180)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel tituloLabel = new JLabel("Título:");
        JTextField tituloField = new JTextField(20);
        JLabel autorLabel = new JLabel("Autor:");
        JTextField autorField = new JTextField(20);
        JLabel anoLabel = new JLabel("Ano de Publicação:");
        JTextField anoField = new JTextField(5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(tituloLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(tituloField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(autorLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(autorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(anoLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(anoField, gbc);

        JButton adicionarButton = criarBotao("Adicionar Livro");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(adicionarButton, gbc);

        adicionarButton.addActionListener(e -> {
            String titulo = tituloField.getText().trim();
            String autor = autorField.getText().trim();

            try {
                int ano = Integer.parseInt(anoField.getText().trim());
                if (titulo.isEmpty() || autor.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
                } else {
                    gerenciadorDeLivros.adicionarLivro(titulo, autor, ano);
                    tituloField.setText("");
                    autorField.setText("");
                    anoField.setText("");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, insira um ano de publicação válido (número inteiro).");
            }
        });

        return formPanel;
    }

    private static JPanel criarPainelBusca(JFrame frame) {
        JPanel buscaPanel = new JPanel();
        buscaPanel.setLayout(new BoxLayout(buscaPanel, BoxLayout.X_AXIS));
        buscaPanel.setBackground(new Color(240, 240, 240));
        buscaPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                "Buscar Livro por Autor", TitledBorder.CENTER, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14), new Color(70, 130, 180)
        ));

        JTextField buscaAutorField = new JTextField(15);
        buscaAutorField.setPreferredSize(new Dimension(200, 30));
        JButton buscarButton = criarBotao("Buscar");

        buscaPanel.add(new JLabel("Autor:"));
        buscaPanel.add(buscaAutorField);
        buscaPanel.add(buscarButton);

        buscarButton.addActionListener(e -> {
            String autor = buscaAutorField.getText().trim();
            if (!autor.isEmpty()) {
                List<Livro> livrosEncontrados = gerenciadorDeLivros.buscarPorAutor(autor);
                if (!livrosEncontrados.isEmpty()) {
                    StringBuilder resultado = new StringBuilder("Livros encontrados:\n");
                    for (Livro livro : livrosEncontrados) {
                        resultado.append(livro).append("\n");
                    }
                    JOptionPane.showMessageDialog(frame, resultado.toString());
                } else {
                    JOptionPane.showMessageDialog(frame, "Nenhum livro encontrado para o autor: " + autor);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Por favor, insira o nome do autor.");
            }
        });

        return buscaPanel;
    }

    private static JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setBackground(new Color(70, 130, 180));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        return botao;
    }
}
