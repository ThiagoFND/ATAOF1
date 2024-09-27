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

        JPanel listaLivrosPanel = criarListaLivrosPanel();
        JPanel recomendadosPanel = criarRecomendadosPanel();
        gerenciadorDeLivros = new GerenciadorDeLivros(listaLivrosPanel, recomendadosPanel);

        JPanel formPanel = criarPainelFormulario();
        JPanel buscaPanel = criarPainelBusca(frame);
        JPanel botaoPanel = criarPainelBotoes();

        JScrollPane scrollPane = new JScrollPane(listaLivrosPanel);
        JScrollPane scrollRecomendadosPane = new JScrollPane(recomendadosPanel);
        scrollRecomendadosPane.setPreferredSize(new Dimension(350, 400));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buscaPanel, BorderLayout.SOUTH);
        mainPanel.add(botaoPanel, BorderLayout.WEST);
        mainPanel.add(scrollRecomendadosPane, BorderLayout.EAST);

        frame.add(formPanel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private static JPanel criarListaLivrosPanel() {
        JPanel listaLivrosPanel = new JPanel();
        listaLivrosPanel.setLayout(new BoxLayout(listaLivrosPanel, BoxLayout.Y_AXIS));
        listaLivrosPanel.setBackground(Color.WHITE);
        return listaLivrosPanel;
    }

    private static JPanel criarRecomendadosPanel() {
        JPanel recomendadosPanel = new JPanel();
        recomendadosPanel.setLayout(new BoxLayout(recomendadosPanel, BoxLayout.Y_AXIS));
        recomendadosPanel.setBackground(Color.LIGHT_GRAY);
        recomendadosPanel.setBorder(BorderFactory.createTitledBorder("Livros Recomendados"));
        recomendadosPanel.setMinimumSize(new Dimension(750, 600));
        return recomendadosPanel;
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

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(tituloLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(tituloField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(autorLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(autorField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(anoLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(anoField, gbc);

        JButton adicionarButton = criarBotao("Adicionar Livro");
        gbc.gridx = 1; gbc.gridy = 3; gbc.anchor = GridBagConstraints.CENTER;
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

    private static JPanel criarPainelBotoes() {
        JPanel botaoPanel = new JPanel();
        botaoPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento entre os componentes
        gbc.fill = GridBagConstraints.HORIZONTAL; // Preencher horizontalmente
    
        // Adicionando o rótulo "Execução"
        JLabel execucaoLabel = new JLabel("Execução");
        execucaoLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Estilo do rótulo
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.CENTER; // Centralizado
        gbc.weighty = 0; // Não usar peso vertical
        botaoPanel.add(execucaoLabel, gbc);
    
        // Botão "Ordenar por Autor"
        JButton ordenarPorAutorButton = criarBotao("Ordenar por Autor");
        // Adicionando ActionListener para o botão
        ordenarPorAutorButton.addActionListener(e -> gerenciadorDeLivros.ordenarPorAutor());
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.CENTER; // Centralizado
        gbc.weighty = 0; // Não usar peso vertical
        botaoPanel.add(ordenarPorAutorButton, gbc);
    
        // Botão "Ordenar por Título"
        JButton ordenarPorTituloButton = criarBotao("Ordenar por Título");
        // Adicionando ActionListener para o botão
        ordenarPorTituloButton.addActionListener(e -> gerenciadorDeLivros.ordenarPorTitulo());
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.CENTER; // Centralizado
        gbc.weighty = 0; // Não usar peso vertical
        botaoPanel.add(ordenarPorTituloButton, gbc);
    
        // Adicionando o rótulo "Dúvidas"
        JLabel duvidasLabel = new JLabel("Dúvidas");
        duvidasLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Estilo do rótulo
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.CENTER; // Centralizado
        gbc.weighty = 0; // Não usar peso vertical
        botaoPanel.add(duvidasLabel, gbc);
    
        // Botão "Explicação Sistema"
        JButton explicacaoSistemaButton = criarBotao("Explicação Sistema");
        // Adicionando ActionListener para o botão
        explicacaoSistemaButton.addActionListener(e -> ExplicacaoCodigo());
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.CENTER; // Centralizado
        gbc.weighty = 0; // Não usar peso vertical
        botaoPanel.add(explicacaoSistemaButton, gbc);
    
        // Botão "Explicação Árvore"
        JButton listarArvoreButton = criarBotao("Explicação Árvore");
        // Adicionando ActionListener para o botão
        listarArvoreButton.addActionListener(e -> listarArvore());
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.CENTER; // Centralizado
        gbc.weighty = 0; // Não usar peso vertical
        botaoPanel.add(listarArvoreButton, gbc);

        // termos"
        JButton termosButton = criarBotao("Termos");
        // Adicionando ActionListener para o botão
        termosButton.addActionListener(e -> termos());
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.CENTER; // Centralizado
        gbc.weighty = 0; // Não usar peso vertical
        botaoPanel.add(termosButton, gbc);
    
        return botaoPanel;
    }

    private static JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setBackground(new Color(70, 130, 180));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        
        botao.setPreferredSize(null);
    
        return botao;
    }
    

    private static void listarArvore() {

        UIManager.put("Panel.background", Color.DARK_GRAY);
        UIManager.put("OptionPane.background", Color.DARK_GRAY);
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Button.background", Color.GRAY);
        UIManager.put("Button.foreground", Color.WHITE);

        String mensagem = "A árvore binária de busca (ABB) é uma estrutura de dados que organiza os livros de forma hierárquica.\n\n" +
                          "Cada nó da árvore representa um livro, que possui um título, autor e ano de publicação. Na ABB, os nós são organizados da seguinte forma:\n" +
                          "- Todos os nós à esquerda de um nó possuem títulos menores que o título do nó pai.\n" +
                          "- Todos os nós à direita de um nó possuem títulos maiores ou iguais ao título do nó pai.\n" +
                          "\nPor exemplo, considere os seguintes livros:\n" +
                          "- \"O Senhor dos Anéis\"\n" +
                          "- \"Harry Potter\"\n" +
                          "- \"A Menina que Roubava Livros\"\n" +
                          "- \"O Hobbit\"\n" +
                          "\nAo inserir esses livros em uma árvore binária de busca:\n" +
                          "\n1. O primeiro livro, \"O Senhor dos Anéis\", se torna a raiz da árvore.\n" +
                          "\n   Raiz: \"O Senhor dos Anéis\"\n" +
                          "\n2. Ao inserir \"Harry Potter\", como \"Harry Potter\" é menor que \"O Senhor dos Anéis\", ele é colocado à esquerda.\n" +
                          "\n        O Senhor dos Anéis\n" +
                          "          /\n" +
                          "   Harry Potter\n" +
                          "\n3. Agora, ao inserir \"A Menina que Roubava Livros\", como este título é menor que \"O Senhor dos Anéis\" mas maior que \"Harry Potter\", ele é colocado à direita de \"Harry Potter\".\n" +
                          "\n        O Senhor dos Anéis\n" +
                          "          /\n" +
                          "   Harry Potter\n" +
                          "         \\\n" +
                          "     A Menina que Roubava Livros\n" +
                          "\n4. Por fim, ao inserir \"O Hobbit\", que é maior que \"O Senhor dos Anéis\", ele é colocado à direita da raiz.\n" +
                          "\n        O Senhor dos Anéis\n" +
                          "          / \\\n" +
                          "   Harry Potter   O Hobbit\n" +
                          "         \\\n" +
                          "     A Menina que Roubava Livros\n" +
                          "\nCom essa estrutura, podemos buscar um livro de forma eficiente, pois a árvore permite reduzir o número de comparações necessárias. Ao procurar um livro, começamos pela raiz e descemos pela árvore, seguindo as regras de menor e maior até encontrar o nó desejado ou chegar a um nó nulo (que significa que o livro não está na árvore).\n\n" +
                          "Além disso, quando inserimos um livro com o mesmo título de um já existente, este será sempre colocado à esquerda, garantindo que a estrutura da árvore seja mantida e evitando duplicações indesejadas.";
        
        JOptionPane.showMessageDialog(null, mensagem, "Como Funciona a Árvore Binária", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void ExplicacaoCodigo() {

        UIManager.put("Panel.background", Color.DARK_GRAY);
        UIManager.put("OptionPane.background", Color.DARK_GRAY);
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Button.background", Color.GRAY);
        UIManager.put("Button.foreground", Color.WHITE);

        String mensagem = "A árvore binária de busca (ABB) é uma estrutura de dados que organiza os livros de forma hierárquica.\n\n"
                + "Cada nó da árvore contém um livro e duas subárvores:\n"
                + "- A subárvore à esquerda contém livros com valores menores.\n"
                + "- A subárvore à direita contém livros com valores maiores ou iguais.\n\n"
                + "Vantagens da ABB:\n"
                + "- Busca Eficiente: Tempo médio de O(log n).\n"
                + "- Inserção Rápida: Mantém a ordem automaticamente.\n"
                + "- Ordenação Natural: Lista os livros em ordem crescente.\n\n"
                + "Funcionalidades do Sistema:\n"
                + "- Adicionar Livro: Insere novos livros na árvore.\n"
                + "- Remover Livro: Ajusta a árvore ao remover um livro.\n"
                + "- Ordenar Livros: Exibe a lista em ordem crescente.\n"
                + "- Recomendar Livros: Facilita a busca e listagem de livros recomendados.\n"
                + "- Visualização da Estrutura: Permite entender a organização dos livros.\n\n"
                + "A estrutura hierárquica da ABB melhora a velocidade de busca e inserção, "
                + "proporcionando uma visualização clara da relação entre os livros.";
    
        JOptionPane.showMessageDialog(null, mensagem, "Como Funciona a Árvore Binária", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void termos() {

        UIManager.put("Panel.background", Color.DARK_GRAY);
        UIManager.put("OptionPane.background", Color.DARK_GRAY);
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Button.background", Color.GRAY);
        UIManager.put("Button.foreground", Color.WHITE);
        
        String mensagem = "Termos de Uso do Sistema de Gerenciamento de Livros\n\n" +
                          "Bem-vindo ao Sistema de Gerenciamento de Livros. Este sistema foi desenvolvido com o propósito de facilitar " +
                          "a organização, recomendação e pesquisa de livros, utilizando uma árvore binária de busca (ABB) para estruturar e " +
                          "gerenciar os dados de forma eficiente e acessível.\n\n" +
                          "Finalidade do Sistema:\n" +
                          "O sistema foi criado para auxiliar usuários no armazenamento, consulta e recomendação de livros, possibilitando " +
                          "que bibliotecas pessoais ou institucionais mantenham um controle fácil e visual sobre os títulos disponíveis. " +
                          "A estrutura de árvore binária permite uma busca otimizada e uma organização hierárquica clara.\n\n" +
                          "Autorização de Uso:\n" +
                          "Todos os usuários deste sistema estão autorizados a utilizar as funcionalidades aqui descritas, incluindo a " +
                          "inserção, remoção, recomendação e organização de livros, bem como a visualização da estrutura da árvore binária. " +
                          "O uso deste sistema é livre para fins educacionais, pessoais ou institucionais, desde que não envolva a sua " +
                          "comercialização sem autorização prévia.\n\n" +
                          "Limitações de Responsabilidade:\n" +
                          "Este software é fornecido 'como está', sem garantias explícitas ou implícitas. O desenvolvedor não se responsabiliza " +
                          "por quaisquer danos decorrentes do uso do sistema, incluindo a perda de dados ou mal funcionamento inesperado.\n\n" +
                          "Privacidade:\n" +
                          "O sistema não coleta ou compartilha dados dos usuários com terceiros. Todos os dados inseridos no sistema são " +
                          "armazenados localmente e são de responsabilidade exclusiva do usuário que os insere.\n\n" +
                          "Ao utilizar este sistema, você concorda com os termos aqui descritos.\n\n" +
                          "Obrigado por utilizar o Sistema de Gerenciamento de Livros!\n";
    
        // Exibir mensagem com tema dark
        JOptionPane.showMessageDialog(null, mensagem, "Termos de Uso", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    
}
