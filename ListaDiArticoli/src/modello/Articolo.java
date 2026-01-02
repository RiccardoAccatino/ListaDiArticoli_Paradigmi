package modello;

public class Articolo {
	private String nome="";
	private int prezzo=0;
	private String categoria="Non categorizzato";
	private String nota="";
	public Articolo(String nome,String categoria, int prezzo, String nota)
	{
		setNome(nome);
		setPrezzo(prezzo);
		setCategoria(categoria);
		setNota(nota);
	}
	public Articolo(String nome, int prezzo,String categoria)
	{
		setNome(nome);
		setPrezzo(prezzo);
		setCategoria(categoria);
	}
	public Articolo(String nome, int prezzo)
	{
		setNome(nome);
		setPrezzo(prezzo);
	}
	public Articolo(String nome)
	{
		setNome(nome);
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(int prezzo) {
		this.prezzo = prezzo;
	}
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
}
