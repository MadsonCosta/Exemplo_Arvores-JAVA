
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author anton
 */
public class arvore_B {
    //****ATRIBUTOS****//

    private NoB raiz;
    private int MIN_CHAVES;
    private int MAX_CHAVES;
    private String print;

    //****CONSTRUTOR PADRAO*****//
    //construtor da ArvoreB.
    //inicializa a raiz e o número mínimo e mAximo de chaves por página
    public arvore_B(NoB raiz, int Ordem) {
        super();
        this.raiz = raiz;
        this.MIN_CHAVES = Ordem;
        this.MAX_CHAVES = Ordem * 2; //vem da definição: 2d é o limite máximo para o número de chaves em uma página.
    }
    //****METODOS****//

    //Metodo auxiliar para buscarElemento(verifica se o elemento ja está na lista, inserirElemento.
    private NoB busca(NoB no, int x) {
        NoB verifica = no;// no serve apenas para inicializar a variavel verifica.
        if (verifica == null) {
            return null;
        } //nao achou o elemento
        else {
            //verifica o proximo elemento
            verifica = verifica.proximo;
            while (verifica.proximo != null && verifica.chave < x) {
                verifica = verifica.proximo;
            }
            if (verifica.chave < x && verifica.pagina != null) {
                verifica = busca(verifica.pagina, x);
            } else if (verifica.chave > x && verifica.anterior.pagina != null) {
                verifica = busca(verifica.anterior.pagina, x);
            }
            return (verifica);
        }
    }

    /*========================================================================================*/
    //Metodo de cisao para caso de exceder o limite na pagina da arvore.
    private void cisaoArvoreB(NoB cabecalho) {//Metodo que vai balancear ou reordenar a arvore quando atingir o limite.
        int n = 0;
        //temp1 e temp2 serão nós temporarios.
        NoB temp1;
        NoB temp2;
        //pag1 e pag2 são os nós
        NoB pag1 = cabecalho; //cabecalho é o cabeçalho da página desbalanceada
        NoB pag2 = new NoB(pag1.chave - MIN_CHAVES - 1, null, pag1, null);
        pag1.chave = MIN_CHAVES;
        temp1 = pag2.proximo;

        while (n <= MIN_CHAVES) { //temp1 aponta para o no que vai subir
            temp1 = temp1.proximo;
            n++;
        }
        temp2 = temp1.proximo; //temp2 guarda a nova página
        temp1.proximo = null;
        pag2.pagina = temp1.pagina; //caso o nó que vai subir seja "pai" de uma pagina, preserva-se
        if (pag2.pagina != null) {
            pag2.pagina.anterior = pag2;
        }
        //organizacao de ponteiros
        pag2.proximo = temp2;
        temp2.anterior = pag2;
        temp1.anterior.proximo = null;
        temp1.anterior = null;
        temp1.pagina = pag2;
        pag2.anterior = temp1;
        if (pag1.anterior == null) { //caso a chave que subir torne-se a nova raiz
            NoB pag3;
            pag3 = new NoB(1, pag1, temp1, null);
            pag1.anterior = pag3;
            temp1.anterior = pag3;
            raiz = pag3;
        } else {
            pag1 = pag1.anterior;
            temp2 = pag1.proximo;
            pag1.proximo = temp1;
            temp1.anterior = pag1;
            temp1.proximo = temp2;
            if (temp2 != null) {
                temp2.anterior = temp1;
            }
            while (pag1 != null && pag1.pagina != pag2) { //procura cabecalho
                pag2 = pag1;
                pag1 = pag1.anterior;
            }
            pag2.chave++;
            if (pag2.chave > MAX_CHAVES) {
                cisaoArvoreB(pag2);
            } //cisao executada recursivamente
        }
    }

    /*========================================================================================*/
    //Metodo de concatenacao da ArvoreB. Concatena cabecalho com a página seguinte
    private void cocatenacaoArvoreB(NoB cabecalho) {
        //Nos temporarios para auxilio na concatenacao
        NoB temp1;
        NoB temp2;

        NoB pag1 = cabecalho; //novamente, cabeçalho da página desbalanceada
        NoB pag2 = pag1.anterior;

        if (pag2.proximo != null) {
            pag2 = pag2.proximo;
        }//correcao da posição da página
        //organizacao de ponteiros
        pag1 = pag2.anterior;
        temp2 = pag2.proximo;
        temp1 = pag1.pagina;
        pag1.proximo = temp2;
        if (temp2 != null) {
            temp2.anterior = pag1;
        }
        temp2 = pag2.pagina;
        temp1.chave = temp1.chave + temp2.chave + 1; //atualizacao do cabecalho
        while (temp1.proximo != null) {
            temp1 = temp1.proximo; //vai pro fim da proxima pagina
        }		//mais organizacao de ponteiros
        temp1.proximo = pag2;
        pag2.anterior = temp1;
        temp2 = temp2.proximo;
        pag2.proximo = temp2;
        if (temp2 != null) {
            temp2.anterior = pag2;
        }
        temp2 = pag2.pagina.pagina;
        pag2.pagina = temp2;
        pag1 = pag2;
        while ((pag2 != null) && (pag2.pagina != pag1)) { //busca do cabecalho
            pag1 = pag2;
            pag2 = pag2.anterior;
        }
        temp1 = pag1;
        pag1 = pag2;
        while ((pag2 != null) && (pag2.pagina != pag1)) { //buscando o cabeçalho da página que "perdeu"
            pag1 = pag2;
            pag2 = pag2.anterior;
        }
        pag1.chave = pag1.chave - 1; //atualizando o cabeçalho da página que perdeu um nó
        if (temp1.chave > MAX_CHAVES) {
            cisaoArvoreB(temp1);
        }
        if (pag1.chave < MIN_CHAVES && pag1 != raiz) {
            cocatenacaoArvoreB(pag1); //necessita nova concatenacao
        } else if (pag1.chave == 0) {
            raiz = temp1;
            raiz.anterior = null;
        }
    }

    /*========================================================================================*/
    //Metodo principal de insercao que recebe uma chave x e é chamado pelo usuario, faz uma especie de balanceamento da arvore
    protected boolean inserirElemento(int x) {
        NoB elem = busca(raiz, x);
        NoB novaPagina;
        NoB temp;

        if (elem == null) { //caso seja o primeiro elemento inserido

            novaPagina = new NoB(x, null, null, null);
            raiz = new NoB(1, null, novaPagina, null);
            novaPagina.anterior = raiz; // aloca o primeiro elemento na raiz.
            return (true);

        } else//nao e o primeiro
        {
            if (elem.chave == x) {
                return false;//elemento ja existe na arvore
            } else {
                novaPagina = new NoB(x, null, null, null);
                if (elem.chave < x) { //decide se irá inserir antes ou depois do nó retornado
                    elem.proximo = novaPagina;
                    novaPagina.anterior = elem;
                } else {
                    temp = elem.anterior;
                    temp.proximo = novaPagina;
                    novaPagina.proximo = elem;
                    elem.anterior = novaPagina;
                    novaPagina.anterior = temp;
                }
                while (novaPagina != null && novaPagina.pagina != elem) { //em busca do cabecalho
                    elem = novaPagina;
                    novaPagina = novaPagina.anterior;
                }
                elem.chave = elem.chave + 1; //atualizacao do cabecalho
                if (elem.chave > MAX_CHAVES) {
                    cisaoArvoreB(elem); //caso estoure a página
                }
                return (true);
            }
        }
    }

    /*========================================================================================*/
 /*Metodo que exibe a arvore em ordem (utiliza metodo privado para auxiliar) na representacao de parenteses aninhados*/
    public String exibirArvoreInOrdem() {
        print = exibirArvoreinOrdem(raiz);
        String s = print;
        print = "";
        return s;
    }

    //auxiliar para o metodo de exibir a arvore em ordem
    private String exibirArvoreinOrdem(NoB p) {
        if (p == null) {
            return "";
        }
        if (print == null) {
            print = "";
        }
        print = print + "(";
        while (p != null) {
            if (p.pagina != null) {
                print = exibirArvoreinOrdem(p.pagina);
            }
            p = p.proximo;
            if (p != null) {
                print = print + " " + p.chave + " ";
            }
        }
        print = print + ")";
        return print;
    }

    /*========================================================================================*/
    //Metodo de execucao da Arvore B
    public void start() {
        String parametro;
        String tipodeOperacao = " ";
        JOptionPane.showMessageDialog(null, "Arvore B \n Aceita entrada de valores inteiros", " Arvore B", JOptionPane.PLAIN_MESSAGE);
        System.out.println("**Historico de operações**");
        while (tipodeOperacao != null) {//inicio do loop
            tipodeOperacao = JOptionPane.showInputDialog("Entre com o tipo de operacao"
                    + " que deseja fazer:\n [1] Busca \n [2] Insercao "
                    + "\n [3] Impressao");
            if (tipodeOperacao == null || tipodeOperacao.length() == 0) {
                return;
            }//entrada vazia - encerra a execução

            boolean testeOperador = false;
            for (int j = 0; j < tipodeOperacao.length(); j++) {//testa se entrada nao é inteiro
                if (!Character.isDigit(tipodeOperacao.charAt(j))) {
                    testeOperador = true;
                }
            }
            if (testeOperador) {
                JOptionPane.showMessageDialog(null, "Entrada incorreta (entre com um "
                        + "numero de 1 a 3)", " Arvore B ", JOptionPane.PLAIN_MESSAGE);
            } else //IMPRESSAO
            {
                if (tipodeOperacao.length() == 1 && tipodeOperacao.charAt(0) == '3') {//impressao
                    JOptionPane.showMessageDialog(null, "A arvore atual e': " + exibirArvoreInOrdem(), "Impressao", JOptionPane.PLAIN_MESSAGE); //impressao
                    System.out.println("Impressao da arvore: " + this.exibirArvoreInOrdem());
                } else {
                    parametro = JOptionPane.showInputDialog("Entre com o parametro da operacao (Int)");
                    if (parametro == null || parametro.length() == 0) {
                        return;
                    }//teste de cancelamento ou entrada vazia

                    boolean testeParametro = false;
                    for (int j = 0; j < parametro.length(); j++) {
                        if (!Character.isDigit(parametro.charAt(j))) { //testa se entrada nao é inteiro
                            testeParametro = true;
                        }
                    }
                    if (testeParametro) {
                        JOptionPane.showMessageDialog(null, "Entrada incorreta (somente inteiros)", " Arvore B ", JOptionPane.ERROR_MESSAGE);
                    } else {
                        int x = Integer.parseInt(parametro);

                        //INSERCAO
                        if (tipodeOperacao.length() == 1 && tipodeOperacao.charAt(0) == '2') {//insercao
                            this.inserirElemento(x);
                            JOptionPane.showMessageDialog(null, "A arvore atual e': " + exibirArvoreInOrdem(), "Impressao", JOptionPane.PLAIN_MESSAGE);
                            System.out.println("Tentativa de insercao de: " + parametro);
                        } else if (tipodeOperacao == null) {//cancelou 
                            JOptionPane.showMessageDialog(null, "Fim da operacao");
                            System.exit(0);

                        } else {//Se diferente de todas as outras opcoes, entrada incorreta
                            JOptionPane.showMessageDialog(null, "Entrada incorreta", " Arvore B ", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
            System.out.println(exibirArvoreInOrdem());
        }

    }
}
