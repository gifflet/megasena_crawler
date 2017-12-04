package megasena.megasena_crawler;

import java.util.ArrayList;

import gui.GUINotifier;

public class Analyzer {

	private ArrayList<NumSorteado> numerosSorteados;
	private GUINotifier guiNotifier;
	
	public Analyzer(){
		this.numerosSorteados = new ArrayList<NumSorteado>();
		this.guiNotifier = new GUINotifier();
	}
	
	public void addReceivedNumbers(ArrayList<Integer> numbersReceived){
		boolean found = false;
		this.guiNotifier.setSync(numbersReceived);
		/*
		for(Integer number : numbersReceived){
			if(!this.numerosSorteados.contains(number)){
				this.numerosSorteados.add(new NumSorteado(number));
			}else{
				for(NumSorteado numeroSorteado : this.numerosSorteados){
					if(numeroSorteado.getNumSorteado() == number){
						numeroSorteado.add();
						break;
					}
				}
			}
		}*/
		for(Integer numReceived : numbersReceived){
			for(NumSorteado numeroSorteado : this.numerosSorteados){
				if(numeroSorteado.getNumSorteado() == numReceived){
					numeroSorteado.add();
					found = true;
					break;
				}
			}
			if(!found){
				this.numerosSorteados.add(new NumSorteado(numReceived));
				found = false;
			}
		}
	}
	
	public ArrayList<NumSorteado> getNumerosSorteados(){
		return this.numerosSorteados;
	}
}
