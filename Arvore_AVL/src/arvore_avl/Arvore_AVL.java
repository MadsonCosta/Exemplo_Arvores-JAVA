/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvore_avl;


public class Arvore_AVL { //Estrutura de Arvore AVL

    private No raiz;
 
    private class No { //Características de um nó da arvore
        
        No(int valor, No pai) { //Contrutor para inicialização do Nó
            this.valor = valor;
            this.noPai = pai;
        }
        
        private int valor; //valor do nó
        private int varAltura; //Variação de altura a direita e esquerda
        private No esq, dir, noPai; //Nós filhos e pai
 
    }
 
    public boolean inserir(int valor) { //Função que insere um nó na arvore
        if (raiz == null)
            raiz = new No(valor, null);
        else {
            No n = raiz;
            No noPai;
            boolean flagLaco = true; //Flag que indica fim do laço
            while (flagLaco) {
                if (n.valor == valor) //Se o valor já existir na arvore, não precisa ser adicionado
                    return false;
 
                noPai = n;
 
                boolean menorQuePai = n.valor > valor; //variável que indica se o valor deve ser inserido a esquerda, isto é, se ele e menor que o nó pai
                n = menorQuePai ? n.esq : n.dir; //Operador Ternário, corresponde ao IF/ELSE
 
                if (n == null) { //O nó é adicionado a esquerda ou a direita de acordo com a variavel que indica se ele e menor ou maior que o valor pai
                    if (menorQuePai) {
                        noPai.esq = new No(valor, noPai);
                    } else {
                        noPai.dir = new No(valor, noPai);
                    }
                    balancear(noPai); //é feito o balanceamento após o nó ser inserido
                    flagLaco = false; //O no foi inserido e o laço pode ser finalizado
                }
            }
        }
        return true;
    }
 
    public void deletar(int chaveDel) { //Funçao que deleta um determinado nó da arvore de acordo com o seu valor
        if (raiz == null){
            //Não há o que deletar
        }
        else{
            No n = raiz;
            No noPai = raiz;
            No delNode = null;
            No noFilho = raiz;
 
            while (noFilho != null) {
                noPai = n;
                n = noFilho;
                noFilho = chaveDel >= n.valor ? n.dir : n.esq; 
                if (chaveDel == n.valor)
                    delNode = n;
            }
 
            if (delNode != null) {
                delNode.valor = n.valor;
 
                noFilho = n.esq != null ? n.esq : n.dir;
 
                if (raiz.valor == chaveDel) {
                    raiz = noFilho;
                } else {
                    if (noPai.esq == n) {
                        noPai.esq = noFilho;
                    } else {
                        noPai.dir = noFilho;
                    }
                    balancear(noPai);
                }
            }
        }
    }
 
    private void balancear(No n) { //Função que realiza as rotaçoes de acordo com a necessidades de balanceamento
        varAlturas(n);
 
        if (n.varAltura == -2) {
            if (altura(n.esq.esq) >= altura(n.esq.dir))
                n = RD(n);
            else
                n = RDD(n);
 
        } else if (n.varAltura == 2) {
            if (altura(n.dir.dir) >= altura(n.dir.esq))
                n = RE(n);
            else
                n = RDE(n);
        }
 
        if (n.noPai != null) {
            balancear(n.noPai);
        } else {
            raiz = n;
        }
    }
 
    private No RE(No a) { //Funçao de Rotação a esquerda
 
        No b = a.dir;
        b.noPai = a.noPai;
 
        a.dir = b.esq;
 
        if (a.dir != null)
            a.dir.noPai = a;
 
        b.esq = a;
        a.noPai = b;
 
        if (b.noPai != null) {
            if (b.noPai.dir == a) {
                b.noPai.dir = b;
            } else {
                b.noPai.esq = b;
            }
        }
 
        varAlturas(a, b);
 
        return b;
    }
 
    private No RD(No a) { //Função de Rotação a direita
 
        No b = a.esq;
        b.noPai = a.noPai;
 
        a.esq = b.dir;
 
        if (a.esq != null)
            a.esq.noPai = a;
 
        b.dir = a;
        a.noPai = b;
 
        if (b.noPai != null) {
            if (b.noPai.dir == a) {
                b.noPai.dir = b;
            } else {
                b.noPai.esq = b;
            }
        }
 
        varAlturas(a, b);
 
        return b;
    }
 
    private No RDD(No n) { //Funçao de dupla rotação a direita
        n.esq = RE(n.esq);
        return RD(n);
    }
 
    private No RDE(No n) { //Função de dupla rotaçao a esquerda
        n.dir = RD(n.dir);
        return RE(n);
    }
 
    private int altura(No n) { //Função que retorna a altura de um ddeterminado nó
        if (n == null)
            return -1;
        return 1 + Math.max(altura(n.esq), altura(n.dir));
    }
 
    private void varAlturas(No... nodes) { //Verifica a diferença de altura a esquerda e a direita, a "..." indica que a função pode receber várias variaveis do tipo nó ou mesmo nenhuma
        for (No n : nodes)
            n.varAltura = altura(n.dir) - altura(n.esq); //variaçao de altura a esquerda e direita
    }
 
 
    public static void main(String[] args) {
 
    }
}
