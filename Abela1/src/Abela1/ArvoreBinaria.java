package Abela1;

class ArvoreBinaria {
    No raiz;

    public void inserir(Livro livro) {
        raiz = inserirRecursivo(raiz, livro);
    }

    private No inserirRecursivo(No atual, Livro livro) {
        if (atual == null) {
            return new No(livro);
        }
        if (livro.getTitulo().compareToIgnoreCase(atual.livro.getTitulo()) < 0) {
            atual.esquerda = inserirRecursivo(atual.esquerda, livro);
        } else if (livro.getTitulo().compareToIgnoreCase(atual.livro.getTitulo()) > 0) {
            atual.direita = inserirRecursivo(atual.direita, livro);
        }
        return atual;
    }

    public void exibirLivrosEmOrdem() {
        exibirEmOrdemRecursivo(raiz);
    }

    private void exibirEmOrdemRecursivo(No no) {
        if (no != null) {
            exibirEmOrdemRecursivo(no.esquerda);
            System.out.println(no.livro);
            exibirEmOrdemRecursivo(no.direita);
        }
    }
}
