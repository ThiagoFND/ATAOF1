package Abela1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GerenciadorDeLivros {
    private No raiz;
    private JPanel listaLivrosPanel;
    private JPanel recomendadosPanel;
    private List<Livro> livrosRecomendados;

    public GerenciadorDeLivros(JPanel listaLivrosPanel, JPanel recomendadosPanel) {
        this.raiz = null;
        this.listaLivrosPanel = listaLivrosPanel;
        this.recomendadosPanel = recomendadosPanel;
        this.livrosRecomendados = new ArrayList<>();
    }

    public void adicionarLivro(String titulo, String autor, int ano) {
        Livro novoLivro = new Livro(titulo, autor, ano);
        raiz = adicionarRecursivo(raiz, novoLivro);
        listarLivros(); // Atualiza a lista após adicionar
    }

    private No adicionarRecursivo(No atual, Livro livro) {
        if (atual == null) {
            return new No(livro);
        }

        // Inserção à esquerda se o título for menor ou igual
        if (livro.getTitulo().compareToIgnoreCase(atual.livro.getTitulo()) <= 0) {
            atual.esquerda = adicionarRecursivo(atual.esquerda, livro);
        } else {
            atual.direita = adicionarRecursivo(atual.direita, livro);
        }
        return atual;
    }

    public void listarLivros() {
        listaLivrosPanel.removeAll();
        if (raiz == null) {
            listaLivrosPanel.add(new JLabel("Nenhum livro no acervo."));
        } else {
            List<Livro> livrosOrdenados = new ArrayList<>();
            coletarLivrosEmOrdem(raiz, livrosOrdenados);
            atualizarListaLivros(livrosOrdenados);
        }
        listaLivrosPanel.revalidate();
        listaLivrosPanel.repaint();
    }

    private void coletarLivrosEmOrdem(No node, List<Livro> lista) {
        if (node != null) {
            coletarLivrosEmOrdem(node.esquerda, lista);
            lista.add(node.livro);
            coletarLivrosEmOrdem(node.direita, lista);
        }
    }

    private void atualizarListaLivros(List<Livro> livrosOrdenados) {
        listaLivrosPanel.removeAll();
        for (Livro livro : livrosOrdenados) {
            JPanel livroPanel = new JPanel();
            JLabel livroLabel = new JLabel(livro.toString());
            JButton removerButton = new JButton("Remover");
            JButton estrelaButton = new JButton("★");
            estrelaButton.setForeground(Color.YELLOW);

            removerButton.addActionListener(e -> removerLivro(livro.getTitulo()));
            estrelaButton.addActionListener(e -> adicionarARecomendados(livro));

            livroPanel.add(livroLabel);
            livroPanel.add(removerButton);
            livroPanel.add(estrelaButton);
            listaLivrosPanel.add(livroPanel);
        }
        listaLivrosPanel.revalidate();
        listaLivrosPanel.repaint();
    }

    public void removerLivro(String titulo) {
        raiz = removerRecursivo(raiz, titulo);
        listarLivros(); // Atualiza a lista após remover
    }

    private No removerRecursivo(No atual, String titulo) {
        if (atual == null) {
            return null;
        }

        if (titulo.compareToIgnoreCase(atual.livro.getTitulo()) < 0) {
            atual.esquerda = removerRecursivo(atual.esquerda, titulo);
        } else if (titulo.compareToIgnoreCase(atual.livro.getTitulo()) > 0) {
            atual.direita = removerRecursivo(atual.direita, titulo);
        } else {
            // Se o nó a ser removido for encontrado
            if (atual.esquerda == null) {
                return atual.direita;
            } else if (atual.direita == null) {
                return atual.esquerda;
            }

            // Encontrar o menor valor na subárvore direita
            atual.livro = encontrarMenorValor(atual.direita);
            atual.direita = removerRecursivo(atual.direita, atual.livro.getTitulo());
        }

        return atual;
    }

    private Livro encontrarMenorValor(No raiz) {
        return (raiz.esquerda == null) ? raiz.livro : encontrarMenorValor(raiz.esquerda);
    }

    public List<Livro> buscarPorAutor(String autor) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        buscarPorAutorRecursivo(raiz, autor, livrosEncontrados);
        return livrosEncontrados;
    }

    private void buscarPorAutorRecursivo(No atual, String autor, List<Livro> lista) {
        if (atual != null) {
            buscarPorAutorRecursivo(atual.esquerda, autor, lista);
            if (atual.livro.getAutor().equalsIgnoreCase(autor)) {
                lista.add(atual.livro);
            }
            buscarPorAutorRecursivo(atual.direita, autor, lista);
        }
    }

    private void adicionarARecomendados(Livro livro) {
        if (!livrosRecomendados.contains(livro)) {
            livrosRecomendados.add(livro);
            atualizarListaRecomendados();
            JOptionPane.showMessageDialog(null, "Livro adicionado às recomendações!");
        } else {
            JOptionPane.showMessageDialog(null, "Livro já está nas recomendações.");
        }
    }

    private void atualizarListaRecomendados() {
        recomendadosPanel.removeAll();
        if (livrosRecomendados.isEmpty()) {
            recomendadosPanel.add(new JLabel("Nenhum livro recomendado."));
        } else {
            for (Livro livro : livrosRecomendados) {
                criarPainelRecomendacao(livro);
            }
        }
        recomendadosPanel.revalidate();
        recomendadosPanel.repaint();
    }

    private void criarPainelRecomendacao(Livro livro) {
        JPanel recomendacaoPanel = new JPanel();
        JLabel livroLabel = new JLabel(livro.toString());
        JButton removerButton = new JButton("★");

        removerButton.addActionListener(e -> removerRecomendacao(livro));

        recomendacaoPanel.add(livroLabel);
        recomendacaoPanel.add(removerButton);
        recomendadosPanel.add(recomendacaoPanel);
    }

    public void removerRecomendacao(Livro livro) {
        if (livrosRecomendados.remove(livro)) {
            atualizarListaRecomendados();
            JOptionPane.showMessageDialog(null, "Livro removido das recomendações.");
        }
    }

    public void ordenarPorTitulo() {
        List<Livro> livrosOrdenados = new ArrayList<>();
        coletarLivrosEmOrdem(raiz, livrosOrdenados);
        // Ordena a lista por título
        livrosOrdenados.sort(Comparator.comparing(Livro::getTitulo, String.CASE_INSENSITIVE_ORDER));
        atualizarListaLivros(livrosOrdenados);
    }

    public void ordenarPorAutor() {
        List<Livro> livrosOrdenados = new ArrayList<>();
        coletarLivrosEmOrdem(raiz, livrosOrdenados);
        // Ordena a lista por autor
        livrosOrdenados.sort(Comparator.comparing(Livro::getAutor, String.CASE_INSENSITIVE_ORDER));
        atualizarListaLivros(livrosOrdenados);
    }
}
