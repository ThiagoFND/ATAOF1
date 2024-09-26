# Sistema de Gerenciamento de Livros

Este projeto é um **Sistema de Gerenciamento de Livros** desenvolvido em **Java** utilizando a biblioteca **Swing** para a interface gráfica. Ele permite gerenciar uma coleção de livros, oferecendo funcionalidades como recomendação, busca e ordenação. O sistema utiliza uma **Árvore Binária de Busca (Binary Search Tree)** para armazenar e consultar livros de forma eficiente, além de implementar algoritmos clássicos de ordenação.

## Funcionalidades

- **Adicionar Livros**: O sistema permite cadastrar novos livros, informando título, autor e ano de publicação.
- **Remover Livros**: Livros podem ser removidos do sistema por meio de sua interface gráfica.
- **Ordenação de Livros**:
  - **Por Título**: Utiliza o algoritmo de **QuickSort** para ordenar os livros em ordem alfabética pelo título.
  - **Por Autor**: Também usa o **QuickSort** para ordenar por nome de autor, proporcionando uma rápida organização.
- **Busca de Livros**:
  - O sistema permite realizar buscas rápidas por **autor**, utilizando uma **Árvore Binária de Busca (BST)**, que otimiza a busca de livros pela eficiência O(log n).
- **Recomendação de Livros**:
  - O usuário pode marcar livros como recomendados, e essas recomendações podem ser visualizadas posteriormente.
- **Interface Intuitiva**: A interface gráfica foi desenvolvida com **Swing** e utiliza **JOptionPane** para capturar interações do usuário, tornando a experiência simples e intuitiva.

## Tecnologias Utilizadas

- **Java SE**: O projeto é desenvolvido inteiramente em Java, utilizando conceitos de orientação a objetos e estruturas de dados.
- **Swing**: Framework utilizado para a construção da interface gráfica, com diálogos de entrada e menus dinâmicos.
- **JOptionPane**: Utilizado para exibir mensagens e capturar informações diretamente do usuário.
- **QuickSort**: Um dos algoritmos de ordenação mais eficientes, implementado para ordenar a coleção de livros.
- **Árvore Binária de Busca (BST)**: Estrutura de dados responsável por armazenar os livros e permitir buscas rápidas por autor.

## Estrutura do Projeto

O projeto segue uma arquitetura modular, dividida em classes principais:

- **Main**: A classe principal que inicializa o sistema e carrega a interface gráfica.
- **GerenciadorDeLivros**: Contém toda a lógica principal para adicionar, remover, recomendar, ordenar e buscar livros. Atua como o controlador da aplicação.
- **ArvoreBinariaBusca**: Classe que implementa uma **Árvore Binária de Busca**. Essa estrutura permite armazenar os livros por autor e realizar buscas de maneira eficiente.
  - **Inserção**: Os livros são inseridos na árvore de acordo com o nome do autor.
  - **Busca**: A busca por autor é otimizada devido à estrutura de árvore binária, reduzindo a complexidade de tempo em comparação com listas simples.
- **Livro**: A classe que define os atributos de um livro (título, autor, ano de publicação) e os métodos necessários para manipular essas informações.

## Algoritmos Implementados

- **QuickSort**: Usado para ordenar a coleção de livros tanto por título quanto por autor. O algoritmo é conhecido por sua eficiência em ordenação, com uma complexidade média de O(n log n).
- **Binary Search Tree (BST)**: Estrutura de dados utilizada para armazenar os livros por autor, permitindo inserção, remoção e busca eficientes com complexidade O(log n) na média dos casos.

## Como Executar o Projeto

Para executar o projeto em sua máquina local, siga os passos abaixo:

1. Clone o repositório:
   ```bash
   git clone https://github.com/thiagofnd/ATAOF1.git
