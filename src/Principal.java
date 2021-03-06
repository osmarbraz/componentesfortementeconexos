/*
 * Universidade Federal de Santa Catarina - UFSC
 * Departamento de Informática e Estatística - INE
 * Programa de Pós-Graduação em Ciências da Computação - PROPG
 * Disciplina: Projeto e Análise de Algoritmos
 * Prof Alexandre Gonçalves da Silva 
 *
 * Baseado nos slides 21 da aula do dia 20/10/2017 
 *
 * Página 448 Thomas H. Cormen 3a Ed
 *
 * Componentes Fortemente Conexos/Strongly Connected Components
 */

/**
 * @author Osmar de Oliveira Braz Junior
 */

import java.util.Stack;

public class Principal {

    //A medida que o grafo é percorrido, os vértices visitados vão
    //sendo coloridos. Cada vértice tem uma das seguintes cores:
    final static int BRANCO = 0;//Vértce ainda não visitado
    final static int CINZA = 1; //Vértice visitado mas não finalizado
    final static int PRETO = 2; //Vértice visitado e finalizado

    //Vetor da situação vértice, armazena uma das cores
    static int cor[];
    //d[x] armazena o instante de descoberta de x.
    static int d[];
    //f[x] armazena o instante de finalização de x.
    static int f[];    
    //Vetor dos pais de um vértice
    static int pi[];
    static int tempo;
        
    /**
     * Troca um número que representa a posição pela vértice do grafo.
     *
     * @param i Posição da letra
     * @return Uma String com a letra da posição
     */
    public static String trocar(int i) {
        String letras = "cgfhdbea";        
        if ((i >=0) && (i<=letras.length())) {
            return letras.charAt(i) + "";
        } else {
            return "-";
        }     
    }

    /**
     * Troca a letra pela posição na matriz de adjacência
     *
     * @param v Letra a ser troca pela posição
     * @return Um inteiro com a posição da letra no grafo
     */
    public static int destrocar(char v) {
        String letras = "cgfhdbea";
        int pos = -1;
        for (int i = 0; i < letras.length(); i++) {
            if (letras.charAt(i) == v) {
                pos = i;
            }
        }
        return pos;
    }

    /**
     * Mostra o caminho de s até v no grafo G
     *
     * @param G Matriz de adjacência do grafo
     * @param s Origem no grafo
     * @param v Destino no grafo
     */
    public static void mostrarCaminho(int[][] G, int s, int v) {
        if (v == s) {
            System.out.println("Cheguei em:" + trocar(s));
        } else {
            if (pi[v] == -1) {
                System.out.println("Não existe caminho de " + trocar(s) + " a " + trocar(v));
            } else {
                mostrarCaminho(G, s, pi[v]);
                System.out.println("Visitando:" + trocar(v));
            }
        }
    }

    /**
     * Constrói recursivamente uma Árvore de Busca em profundidade. com raiz u.
     *
     * Consumo de tempo Adj[u] vezes
     *
     * Método DFS-Visit(G,u) com empilhamento
     * 
     * @param G Matriz de adjacência do grafo
     * @param u Vértice raiz da árvore de busca
     * @param q Pilha da ordem de visita
     */
    public static void buscaEmProfundidadeVisita(int[][] G, int u, Stack q) {
        //Quantidade vértices do grafo
        int n = G.length;
        cor[u] = CINZA;
        tempo = tempo + 1; //Vértice branco u acabou de ser descoberto
        d[u] = tempo;
        // Exporar as arestas (u,v)
        for (int v = 0; v < n; v++) {
            //Somente com os adjancentes ao vértice u
            if (G[u][v] != 0) {
                //Somente vértices nao visitados
                if (cor[v] == BRANCO) {
                    pi[v] = u;
                    buscaEmProfundidadeVisita(G, v, q);
                }
            }
        }
        //Vértice u foi visitado e finalizado
        cor[u] = PRETO;
        tempo = tempo + 1;
        f[u] = tempo;       
        
        //Empilha v que foi visitado
        q.push(u);
    }

     /**
     * Constrói recursivamente uma Árvore de Busca em profundidade com raiz u.
     *
     * Consumo de tempo Adj[u] vezes
     *
     * Método DFS-Visit(G,u)
     * 
     * @param G Matriz de adjacência do grafo
     * @param u Vértice raiz da árvore de busca
     */
    public static void buscaEmProfundidadeVisita2(int[][] G, int u) {
        //Quantidade vértices do grafo
        int n = G.length;

        System.out.println("Vértice:" + trocar(u));
        cor[u] = CINZA;
        tempo = tempo + 1; //Vértice branco u acabou de ser descoberto
        d[u] = tempo;        
        // Exporar as arestas (u,v)
        for (int v = 0; v < n; v++) {
            //Somente com os adjancentes ao vértice u
            if (G[u][v] != 0) {
                //Somente vértices nao visitados
                if (cor[v] == BRANCO) {                    
                    pi[v] = u;
                    buscaEmProfundidadeVisita2(G, v);
                }
            }
        }        
        //Vértice u foi visitado e finalizado
        cor[u] = PRETO;
        tempo = tempo + 1;
        f[u] = tempo;               
    }
    
    /**
     * Busca em Profundidade (Breadth-first Search) recursivo. 
     * 
     * Recebe um grafo G e devolve 
     * (i) os instantes d[v] e f[v] para cada v 
     * (ii) uma Floresta de Busca em Profundiade
     *
     * Consumo de tempo é O(V)+V chamadas 
     * Complexidade de tempo é O(V+E)
     *
     * Método DFS(G)
     * @param G Grafo na forma de uma matriz de adjacência
     * @param q Pilha da ordem de visita
     */
    public static void buscaEmProfundidadeRecursivo(int[][] G, Stack q) {
        //Quantidade vértices do grafo
        int n = G.length;

        //Inicializa os vetores
        cor = new int[n];
        d = new int[n];        
        f = new int[n];        
        pi = new int[n];

        //Inicialização dos vetores
        //Consome Theta(V)
        for (int u = 0; u < n; u++) {
            //Vértice i não foi visitado
            cor[u] = BRANCO;
            d[u] = -1;
            pi[u] = -1;
        }
        tempo = 0;

    
        //Percorre todos os vértices do grafo
        for (int u = 0; u < n; u++) {
            //Somente vértices nao visitados
           if (cor[u] == BRANCO) {
                buscaEmProfundidadeVisita(G, u, q);
            }                    
        }
    }    
    
     /**
     * Busca em Profundidade (Breadth-first Search) recursivo. 
     * 
     * Recebe um grafo G e devolve 
     * (i) os instantes d[v] e f[v] para cada v 
     * (ii) uma Floresta de Busca em Profundiade
     *
     * Consumo de tempo é O(V)+V chamadas 
     * Complexidade de tempo é O(V+E)
     *
     * Método DFS(G)
     * @param G Grafo na forma de uma matriz de adjacência
     * @param q Pilha da ordem de visita
     */
    public static void buscaEmProfundidadeRecursivo2(int[][] G, Stack q) {
        //Vertices de cada árvore visitada
        
        //Quantidade vértices do grafo
        int n = G.length;

        //Inicializa os vetores
        cor = new int[n];
        d = new int[n];        
        f = new int[n];        
        pi = new int[n];

        //Inicialização dos vetores
        //Consome Theta(V)
        for (int u = 0; u < n; u++) {
            //Vértice i não foi visitado
            cor[u] = BRANCO;
            d[u] = -1;
            pi[u] = -1;
        }
        tempo = 0;
      
        //Utiliza a pilha realizada com a DFS em G para mostrar 
        //os componentes fortemente conexos
        while(!q.isEmpty()) {
            int u = (int)q.pop();                
            if (cor[u] == BRANCO) {
                //Conjuntos de vértices de cada árvore da Floresta de Busca em Profundidade obtida.
                System.out.println("\nComponente:"+trocar(u));
                buscaEmProfundidadeVisita2(G, u);
            }                
        }          
    }    
    
    /**
     * Gera a matriz transposta de G
     * @param G Matriz a ser gerada a transposta
     * @return Matriz transposta de G
     */
    public static int[][] transposta(int[][] G ){
        int[][] T = new int[G.length][G.length];
        for (int i = 0;i<G.length;i++){
            for (int j = 0;j<G.length;j++){
                T[i][j] = G[j][i];
            }
        }
        return T;
    }
    
    /**
     * Mostrar os componentes fortemente conexos no grafo G        
     * 
     * @param G Grafo na forma de uma matriz de adjacência a ser 
     * identificado os componentes fortemente conexos
     */    
    public static void componentesFortementeConexos(int[][] G) {
        
        //Pilha para armazenar a ordem de visita dos vértices pela DFS em G
        Stack q = new Stack();       
        
        //Passo 1 - Executa o DFS(G) empilhando o elemento visitado        
        buscaEmProfundidadeRecursivo(G, q);
                       
        System.out.println();
        System.out.println("Ordem de execução de d[x]/f[x] em G ");             
        for (int i = 0; i < G.length; i++) {
            System.out.println(trocar(i) + "=" + d[i] + "/" + f[i]);
        }
                        
        //Gera a transposta de G
        int[][] Gt = transposta(G);
        
        //Realiza a busca em profundidade mostrando os componentes conexos
        buscaEmProfundidadeRecursivo2(Gt, q);            
    }

    public static void main(String args[]) {

        //Matriz de adjacência para um grafo direcionado     
        //Grafo do slide 23
        //Página 449 Thomas H. Cormen 3ed
       
        int G[][] =                         
               //c  g  f  h  d  b  e  a
               {{0, 1, 0, 0, 1, 0, 0, 0},//c
                {0, 0, 1, 1, 0, 0, 0, 0},//g
                {0, 1, 0, 0, 0, 0, 0, 0},//f
                {0, 0, 0, 1, 0, 0, 0, 0},//h
                {1, 0, 0, 1, 0, 0, 0, 0},//d
                {1, 0, 1, 0, 0, 0, 1, 0},//b
                {0, 0, 1, 0, 0, 0, 0, 1},//e
                {0, 0, 0, 0, 0, 1, 0, 0}};//a                
    
        System.out.println(">>> Componentes Fortemente Conexos/Strongly Connected Components <<<");

        //Monta os componentes fortemente conexos do grafo G
        componentesFortementeConexos(G);

        System.out.println("\nMostrando todos dados:");
        for (int i = 0; i < G.length; i++) {
            System.out.println(trocar(pi[i]) + " -> " +  trocar(i) + " custo: " + d[i]);
        }        
    }
}