
public class NoB {
	public int chave;
	public NoB pagina;
	public NoB proximo;
	public NoB anterior;
	
	//construtor padrao
	public NoB(int chave, NoB pagina, NoB proximo, NoB anterior) {
		this.chave = chave;
		this.pagina = pagina;
		this.proximo = proximo;
		this.anterior = anterior;
	}
}
