package megasena.megasena_crawler;

public class NumSorteado {

	private int numSorteado;
	private int quant;
	
	public NumSorteado(int numSorteado){
		this.numSorteado = numSorteado;
		this.quant = 1;
	}
	
	public int getNumSorteado(){
		return this.numSorteado;
	}
	
	public void add(){
		this.quant++;
	}
	
	public int getQuantity(){
		return this.quant;
	}
	
	public String getNumSorteadoInfo(){
		return String.format("<numSorteado numero=\"%d\" quantidade=\"%d\"/>", this.numSorteado, this.quant);
	}
}
