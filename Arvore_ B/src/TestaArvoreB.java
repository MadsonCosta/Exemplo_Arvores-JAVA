
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
public class TestaArvoreB {
    
	public static void main(String[] args) {
		
		//Teste da arvore B com JOptionPane
		String Ordem = JOptionPane.showInputDialog("Entre com a ordem da arvore:");
		if(Ordem == null || Ordem.length() == 0){return;}//entrada vazia(cancelamento do panel)

		for(int j =0; j < Ordem.length(); j++){//testa se entrada nao é inteiro
			if (!Character.isDigit(Ordem.charAt(j))){ 
				JOptionPane.showMessageDialog(null,"Entrada incorreta (entre com um " +
						"numero"," Arvore B ",JOptionPane.PLAIN_MESSAGE);
				return;
			}	
		}
		int x = Integer.parseInt(Ordem);
		arvore_B arvore = new arvore_B(null, x);
		arvore.start();
		
	}


}
