

import java.util.Scanner;

// =====================================
// CLASSE PRODUTO
// =====================================

class Produto {
    int codigo;
    String nome;
    int quantidade;
    double preco;

    public Produto(int codigo, String nome, int quantidade, double preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public String toString() {
        return "Codigo: " + codigo +
               " | Nome: " + nome +
               " | Quantidade: " + quantidade +
               " | Preco: R$ " + preco;
    }
}

// =====================================
// ARRAY DE PRODUTOS
// =====================================

class Catalogo {
    private Produto[] produtos = new Produto[5];
    private int tamanho = 0;

    public void cadastrar(Produto p) {
        if (buscarLinear(p.codigo).produto != null) {
            throw new IllegalArgumentException("Codigo ja cadastrado!");
        }

        if (tamanho == produtos.length) {
            Produto[] novo = new Produto[produtos.length * 2];

            for (int i = 0; i < tamanho; i++) {
                novo[i] = produtos[i];
            }

            produtos = novo;
        }

        int posicao = tamanho - 1;

        while (posicao >= 0 &&
               produtos[posicao].codigo > p.codigo) {
            produtos[posicao + 1] = produtos[posicao];
            posicao--;
        }

        produtos[posicao + 1] = p;
        tamanho++;
    }

    public void listar() {
        if (tamanho == 0) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        for (int i = 0; i < tamanho; i++) {
            System.out.println(produtos[i]);
        }
    }

    public Resultado buscarLinear(int codigo) {
        int comparacoes = 0;
        long inicio = System.nanoTime();

        for (int i = 0; i < tamanho; i++) {
            comparacoes++;

            if (produtos[i].codigo == codigo) {
                return new Resultado(produtos[i],
                    comparacoes, System.nanoTime() - inicio);
            }
        }

        return new Resultado(null, comparacoes,
            System.nanoTime() - inicio);
    }

    public Resultado buscarBinaria(int codigo) {
        int inicio = 0;
        int fim = tamanho - 1;
        int comparacoes = 0;

        long tempo = System.nanoTime();

        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            comparacoes++;

            if (produtos[meio].codigo == codigo) {
                return new Resultado(produtos[meio],
                    comparacoes, System.nanoTime() - tempo);
            }

            if (produtos[meio].codigo < codigo) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }

        return new Resultado(null, comparacoes,
            System.nanoTime() - tempo);
    }

    public int tamanho() {
        return tamanho;
    }
}

class Resultado {
    Produto produto;
    int comparacoes;
    long tempo;

    public Resultado(Produto p, int c, long t) {
        produto = p;
        comparacoes = c;
        tempo = t;
    }
}

// =====================================
// LISTA ENCADEADA GENERICA
// =====================================

class Lista<T> {
    private class No {
        T dado;
        No proximo;

        No(T dado) {
            this.dado = dado;
        }
    }

    private No inicio;
    private int quantidade = 0;

    public void inserir(T dado) {
        No novo = new No(dado);

        if (inicio == null) {
            inicio = novo;
        } else {
            No atual = inicio;

            while (atual.proximo != null) {
                atual = atual.proximo;
            }

            atual.proximo = novo;
        }

        quantidade++;
    }

    public boolean remover(T dado) {
        if (inicio == null) {
            throw new IllegalStateException("Lista vazia!");
        }

        if (inicio.dado.equals(dado)) {
            inicio = inicio.proximo;
            quantidade--;
            return true;
        }

        No atual = inicio;

        while (atual.proximo != null) {
            if (atual.proximo.dado.equals(dado)) {
                atual.proximo = atual.proximo.proximo;
                quantidade--;
                return true;
            }

            atual = atual.proximo;
        }

        return false;
    }

    public boolean buscar(T dado) {
        No atual = inicio;

        while (atual != null) {
            if (atual.dado.equals(dado)) {
                return true;
            }
            atual = atual.proximo;
        }

        return false;
    }

    public boolean vazia() {
        return inicio == null;
    }

    public int contar() {
        return quantidade;
    }

    public void mostrar() {
        No atual = inicio;

        if (vazia()) {
            System.out.println("Historico vazio.");
            return;
        }

        while (atual != null) {
            System.out.println(atual.dado);
            atual = atual.proximo;
        }
    }
}

// =====================================
// FILA ENCADEADA GENERICA
// =====================================

class Fila<T> {
    private class No {
        T dado;
        No proximo;

        No(T dado) {
            this.dado = dado;
        }
    }

    private No inicio;
    private No fim;
    private int quantidade = 0;

    public void inserir(T dado) {
        No novo = new No(dado);

        if (inicio == null) {
            inicio = novo;
            fim = novo;
        } else {
            fim.proximo = novo;
            fim = novo;
        }

        quantidade++;
    }

    public T remover() {
        if (inicio == null) {
            throw new IllegalStateException("Fila vazia!");
        }

        T dado = inicio.dado;
        inicio = inicio.proximo;

        if (inicio == null) {
            fim = null;
        }

        quantidade--;
        return dado;
    }

    public boolean buscar(T dado) {
        No atual = inicio;

        while (atual != null) {
            if (atual.dado.equals(dado)) {
                return true;
            }
            atual = atual.proximo;
        }

        return false;
    }

    public boolean vazia() {
        return inicio == null;
    }

    public int contar() {
        return quantidade;
    }

    public void mostrar() {
        No atual = inicio;

        if (vazia()) {
            System.out.println("Fila vazia.");
            return;
        }

        while (atual != null) {
            System.out.println(atual.dado);
            atual = atual.proximo;
        }
    }
}

// =====================================
// ARVORE BINARIA DE BUSCA
// =====================================

class Arvore {
    private class No {
        Produto produto;
        No esquerda, direita;

        No(Produto p) {
            produto = p;
        }
    }

    private No raiz;

    public void inserir(Produto p) {
        raiz = inserirRec(raiz, p);
    }

    private No inserirRec(No atual, Produto p) {
        if (atual == null) {
            return new No(p);
        }

        if (p.codigo < atual.produto.codigo) {
            atual.esquerda = inserirRec(atual.esquerda, p);
        } else if (p.codigo > atual.produto.codigo) {
            atual.direita = inserirRec(atual.direita, p);
        }

        return atual;
    }

    public Produto buscar(int codigo) {
        No atual = raiz;

        while (atual != null) {
            if (codigo == atual.produto.codigo) {
                return atual.produto;
            }

            if (codigo < atual.produto.codigo) {
                atual = atual.esquerda;
            } else {
                atual = atual.direita;
            }
        }

        return null;
    }

    public void preOrdem() {
        pre(raiz);
    }

    private void pre(No n) {
        if (n != null) {
            System.out.println(n.produto);
            pre(n.esquerda);
            pre(n.direita);
        }
    }

    public void emOrdem() {
        em(raiz);
    }

    private void em(No n) {
        if (n != null) {
            em(n.esquerda);
            System.out.println(n.produto);
            em(n.direita);
        }
    }

    public void posOrdem() {
        pos(raiz);
    }

    private void pos(No n) {
        if (n != null) {
            pos(n.esquerda);
            pos(n.direita);
            System.out.println(n.produto);
        }
    }
}

// =====================================
// PROGRAMA PRINCIPAL
// =====================================

public class Main {

    static Scanner sc = new Scanner(System.in);

    static Catalogo catalogo = new Catalogo();
    static Lista<String> historico = new Lista<>();
    static Fila<String> fila = new Fila<>();
    static Arvore arvore = new Arvore();

    static int numeroPedido = 1;

    public static void main(String[] args) {

        int opcao = -1;

        do {
            System.out.println("\n================================");
            System.out.println("     SISTEMA DE GESTAO DE ESTOQUE");
            System.out.println("================================");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Listar produtos");
            System.out.println("3 - Buscar produto");
            System.out.println("4 - Cadastrar pedido");
            System.out.println("5 - Processar pedido");
            System.out.println("6 - Mostrar fila");
            System.out.println("7 - Mostrar historico");
            System.out.println("8 - Percursos da arvore");
            System.out.println("9 - Buscar na arvore");
            System.out.println("0 - Sair");
            System.out.println("================================");
            System.out.print("Escolha uma opcao: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());

                switch (opcao) {
                    case 1:
                        cadastrar();
                        break;

                    case 2:
                        catalogo.listar();
                        break;

                    case 3:
                        buscarProduto();
                        break;

                    case 4:
                        cadastrarPedido();
                        break;

                    case 5:
                        processarPedido();
                        break;

                    case 6:
                        fila.mostrar();
                        System.out.println("Total: " + fila.contar());
                        break;

                    case 7:
                        historico.mostrar();
                        break;

                    case 8:
                        System.out.println("\nPRE-ORDEM:");
                        arvore.preOrdem();

                        System.out.println("\nEM-ORDEM:");
                        arvore.emOrdem();

                        System.out.println("\nPOS-ORDEM:");
                        arvore.posOrdem();
                        break;

                    case 9:
                        System.out.print("Codigo: ");
                        int codigo = Integer.parseInt(sc.nextLine());

                        Produto p = arvore.buscar(codigo);

                        System.out.println(p == null ?
                            "Produto nao encontrado." : p);
                        break;

                    case 0:
                        System.out.println("Sistema encerrado!");
                        break;

                    default:
                        System.out.println("Opcao invalida!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Digite um numero valido!");
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (IllegalStateException e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } while (opcao != 0);

        sc.close();
    }

    static void cadastrar() {
        System.out.println("\n--- CADASTRO DE PRODUTO ---");

        System.out.print("Codigo: ");
        int codigo = Integer.parseInt(sc.nextLine());

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Quantidade: ");
        int quantidade = Integer.parseInt(sc.nextLine());

        System.out.print("Preco: ");
        double preco = Double.parseDouble(
            sc.nextLine().replace(",", ".")
        );

        if (nome.trim().isEmpty() || quantidade < 0 ||
            preco < 0) {
            throw new IllegalArgumentException("Dados invalidos!");
        }

        Produto p = new Produto(codigo, nome, quantidade, preco);

        catalogo.cadastrar(p);
        arvore.inserir(p);
        historico.inserir("Produto cadastrado: " + nome);

        System.out.println("Produto cadastrado com sucesso!");
    }

    static void buscarProduto() {
        System.out.print("Digite o codigo: ");
        int codigo = Integer.parseInt(sc.nextLine());

        Resultado linear = catalogo.buscarLinear(codigo);
        Resultado binaria = catalogo.buscarBinaria(codigo);

        System.out.println("\nBUSCA LINEAR:");

        if (linear.produto != null) {
            System.out.println(linear.produto);
        } else {
            System.out.println("Produto nao encontrado.");
        }

        System.out.println("Comparacoes: " + linear.comparacoes);
        System.out.println("Tempo: " + linear.tempo + " ns");

        System.out.println("\nBUSCA BINARIA:");

        if (binaria.produto != null) {
            System.out.println(binaria.produto);
        } else {
            System.out.println("Produto nao encontrado.");
        }

        System.out.println("Comparacoes: " + binaria.comparacoes);
        System.out.println("Tempo: " + binaria.tempo + " ns");
    }

    static void cadastrarPedido() {
        System.out.print("Descricao do pedido: ");
        String descricao = sc.nextLine();

        if (descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descricao vazia!");
        }

        String pedido = "Pedido " + numeroPedido++ +
                        ": " + descricao;

        fila.inserir(pedido);
        historico.inserir("Pedido adicionado: " + pedido);

        System.out.println("Pedido cadastrado!");
    }

    static void processarPedido() {
        String pedido = fila.remover();

        System.out.println("Pedido processado: " + pedido);

        historico.inserir("Pedido processado: " + pedido);
    }
}
