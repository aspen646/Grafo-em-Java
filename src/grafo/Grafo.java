
package grafo;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author Arthur Henrique 0041556
 */
public class Grafo {
    public static final int branco=0;
    public static final int cinza=1;
    public static final int preto=2;
    
    public static int tempo; //marca o tempo
    public static int d[];
    public static int t[];
    public static String cor[];
    public static int antecessor[];
    public static boolean fimListaAdj;
    public static celula aux;
    public static boolean flagCiclico;
    public static int distancia[];

    private static celula adj[];
    
    public static void criaVazio(int n){ 
        adj= new celula[n];
        for (int i = 0; i < n; i++) {
            adj[i]= new celula();
            adj[i].vert=-1;
            adj[i].peso=-1;
            adj[i].prox= null;  
        }
        
        System.out.println("Grafo criado com sucesso");
    }
    
    public static boolean existeAresta(int u,int v){
        celula aux=new celula();
        
        aux = adj[u];
        while(aux.prox!=null){
            aux=aux.prox;
            if(aux.vert==v)
                return true;
        }
        return false;
    }
    
    public static void inserirAresta(int u,int v,int p){
        celula aux=new celula();
        aux=adj[u];
        while(aux.prox!=null){
            aux=aux.prox;        
        }
        aux.prox=new celula();
        aux.prox.vert=v;
        aux.prox.peso=p;
        aux.prox.prox=null;
    }
    
    public static void removerAresta(int u, int v){
        celula aux=new celula();
        celula aux2=new celula();
        
        aux=adj[u];
        aux2=aux.prox;
       
        while(aux2!=null){
            
            if(aux2.vert==v){
                aux.prox=aux2.prox;
                aux2=null;
                break;
            }
            
            aux=aux.prox;
            aux2=aux2.prox;
        }
        System.out.println("\nARESTA REMOVIDA COM SUCESSO!");
    
    }
    
    public static void destruir(){
        for (int i = 0; i < adj.length; i++) {
            adj[i]=null;            
        }
        
        System.out.println("\nDESTRUÍDO COM SUCESSO!");

    }
    
    public static void imprimir() {
        celula aux = new celula();
        for (int i = 0; i < adj.length; i++) {
            aux = adj[i];
            System.out.print("|" + i + "|-->");
            while (aux.prox != null) {
                aux = aux.prox;
                System.out.print("|" + aux.vert + "|" + aux.peso + "|-->");
            }
            System.out.println("NULL");
        }
    }
    
    public static boolean existeAdjacente(int u) {

        celula aux = adj[u];
        if (aux.prox == null) {
            return (false);
        } else {
            return (true);
        }
    }

    public static void proximoAdjacente(int u, int v) {
        celula aux = adj[u];

        while (aux.prox != null) {
            aux = aux.prox;
            if (aux.vert == v) {
                break;
            }
        }
        if (aux.prox == null) {
            System.out.println("Não existe próximo adjacente. ");
        } else {
            System.out.println("O próximo adjacente é o vertice "
                    + aux.prox.vert + " e o peso da aresta é " + aux.prox.peso);
        }
    }
    
    static void visitaDfs(int u,int op) {
        int v;
        cor[u] = "c";// vertice já foi visitado
        tempo++;//cronometro aumenta
        d[u] = tempo;// o vetor de cinza na pos do vertice recebe a marcação do cronômetro
        System.out.println("Visita o vertice: " + u + " Tempo Descoberta: " + d[u]+"  cinza.");
        if (existeAdjacente(u)) {// verifica se este vertice possui um adjacente
            
            v = primeiroAdjacente(u).vert;//aux recebe o primeiro adjacente
            fimListaAdj = false;//se a lista acabou ou não
            while (!fimListaAdj) {
                    
                if ("b".equals(cor[v])) {
                    
                    antecessor[u] = v;
                    visitaDfs(v,op);
                }
                
                v=proxAdjacente(u,v);
                
            }
        }
        cor[u] = "p";
        tempo++;
        t[u] = tempo;
        System.out.println("Visita ao Vertice: " + u + " Tempo termino: " + t[u]+" preto.");
        
    }
    static void visitaDfsCiclico(int u,int op) {
        int v;
        cor[u] = "c";// vertice já foi visitado
        tempo++;//cronometro aumenta
        d[u] = tempo;// o vetor de cinza na pos do vertice recebe a marcação do cronômetro
        
        if (existeAdjacente(u)) {// verifica se este vertice possui um adjacente
            
            v = primeiroAdjacente(u).vert;//aux recebe o primeiro adjacente
            fimListaAdj = false;//se a lista acabou ou não
            while (!fimListaAdj) {
                    
                if ("b".equals(cor[v])) {
                    
                    antecessor[u] = v;
                    visitaDfsCiclico(v,op);
                }
                else if("c".equals(cor[v]) && op==1){
                        flagCiclico=true;
                }
                v=proxAdjacente(u,v);
                
            }
        }
        cor[u] = "p";
        tempo++;
        t[u] = tempo;

        
    }
    static void buscaEmProfundidade(int tam,int op) {
        tempo = 0;//cronometro
        d = new int[tam];//marca quando o vertice fica cinza
        t = new int[tam];//marca quando o vertice fica preto
        cor = new String[tam];//cor do vertice
        antecessor = new int[tam];//armazena o adjacente anterior de cada vertice
        for (int u = 0; u < tam; u++) {//deixa todos os vertices brancos e o antecessor como -1
            cor[u] = "b";
            antecessor[u] = -1;
        }
        if(op==0){
                for (int u = 0; u < tam; u++) {//inicia a busca, pelo vertice 0
                if ("b".equals(cor[u])) {
                    visitaDfs(u,op);
                }
            }
        
        }else{
            flagCiclico=false;
            for (int u = 0; u < tam; u++) {//inicia a busca, pelo vertice 0
                if ("b".equals(cor[u])) {
                    visitaDfsCiclico(u,op);
              
                }
            }
            if(flagCiclico==true){
                System.out.println("O grafo é ciclico.");
            }
            else{
                System.out.println("O grafo não é cíclico.");
            }
        }       
    }
    
        static celula primeiroAdjacente(int u) {
        aux = adj[u];
        return aux.prox;
    }
        
        static int proxAdjacente(int u, int v) 
    {
       
        aux = adj[u];
        while (aux .prox!= null) {
            aux = aux.prox;
            if (aux.vert == v) {
                break;
            }
        }
        if (aux.prox == null) {
            fimListaAdj = true;
            return(-1);
        }
        else{
            return(aux.prox.vert);
        }
    }
  
    static void buscaLargura(int tam){
        distancia = new int[tam];
        cor=new String[tam];
        antecessor= new int[tam];
        
        for (int i = 0; i < tam; i++) {
            cor[i]="b";
            antecessor[i]=-1;
            distancia[i]=-1;
        }
        
        for (int i = 0; i < tam; i++) {
            if (cor[i] == "b") {
                visitaBfs(i);
            }
            
        }
    }
    static void visitaBfs(int u){
        int item,v;
        Queue<Integer> fila = new LinkedList();
        cor[u]="c";
        distancia[u]=0;
        fila.clear();
        item=u;
        fila.add(item);
        System.out.println("Visita: "+u+" Cor branca, distância: "+distancia[u]);
        while(!fila.isEmpty()){
            item=fila.remove();
            u=item;
            if(existeAdjacente(u)){
                v=primeiroAdjacente(u).vert;
                fimListaAdj=false;
                while(fimListaAdj==false){
                    if(cor[v]=="b"){
                        cor[v]="c";
                        distancia[v]=distancia[u]+1;
                        antecessor[v]=u;
                        System.out.println("Visita: "+u+" Cor cinza, distância: "+distancia[u]);
                        item=v;
                        fila.add(item);
                    }
                    v= proxAdjacente(u, v);
                }
            }
            cor[u]="p";
            System.out.println("Visita: "+u+" Cor preta, distância: "+distancia[u]);
            Imprimir(fila);
        }
    }
    
    public static void Imprimir (Queue<Integer> fila){
        Iterator it = fila.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }
    
    public static void menu(){
        System.out.println("\n\n1 - Criar Grafo");
        System.out.println("2 - Verificar Aresta");
        System.out.println("3 - Inserir Aresta");
        System.out.println("4 - Remover Aresta");
        System.out.println("5 - Destruir");
        System.out.println("6 - Imprimir");
        System.out.println("7 - Verificar Adjacente");
        System.out.println("8 - Primeiro Adjacente");
        System.out.println("9 - Proximo Adjacente");
        System.out.println("10 - Busca em profundidade");
        System.out.println("11 - Verificar se é cíclico");
        System.out.println("12 - Busca em largura");
        System.out.println("0 - Sair");   
    }

    public static void main(String[] args){
        Scanner x = new Scanner(System.in);
        
        int n = 0,op=0,u,v,p;        

        do{
            menu();
            op=x.nextInt();
            
            switch(op){
                case 0:
                    break;
                case 1: //criaVazia
                    System.out.print("Entre com o tamanho: ");
                    n=x.nextInt();
                    criaVazio(n);
                    break;
                case 2: //existeAresta
                    System.out.print("Entre com a 1° aresta: ");
                    u=x.nextInt();
                    System.out.print("Entre com a 2° aresta: ");
                    v=x.nextInt();
                    if(existeAresta(u,v)==true)
                        System.out.println("\nARESTA ENCONTRADA");
                    else
                        System.out.println("\nNÃO EXISTE ARESTA");
                    break;
                case 3: //inserirAresta
                    System.out.print("Entre com a 1° aresta: ");
                    u=x.nextInt();
                    System.out.print("Entre com a 2° aresta: ");
                    v=x.nextInt();
                    System.out.print("Entre com o peso: ");
                    p=x.nextInt();
                    
                    inserirAresta(u,v,p);
                    break;
                case 4: //removeAresta
                    System.out.print("Entre com a 1° aresta: ");
                    u=x.nextInt();
                    System.out.print("Entre com a 2° aresta: ");
                    v=x.nextInt();
                    
                    removerAresta(u,v);
                    break;
                case 5: //destruir
                    destruir();
                    break;
                case 6: //imprimir
                    imprimir();
                    break;
                case 7: //existeAdj
                    System.out.print("Entre com a aresta: ");
                    u=x.nextInt();
                    if(existeAdjacente(u)==true)
                        System.out.println("\nEXISTE ADJACENTE");
                    else
                        System.out.println("\nNÃO EXISTE ADJACENTE");
                    break;
                 case 8:
                    System.out.println("Digite o número do vertice: ");
                    u = x.nextInt();
                 break;
                case 9: //proximoAdj
                    System.out.print("Entre com a 1° aresta: ");
                    u=x.nextInt();
                    System.out.print("Entre com a 2° aresta: ");
                    v=x.nextInt();
                    proximoAdjacente(u,v);
                    break; 
                case 10:
                    buscaEmProfundidade(n,0);
                    break;      
                case 11:
                    buscaEmProfundidade(n,1);
                    break;
                case 12:
                    buscaLargura(n);
                    break;
                   
            }
        }while(op!=0);
    } 
}
