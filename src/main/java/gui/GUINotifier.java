package gui;

import java.util.ArrayList;
import java.util.Enumeration;

import megasena.megasena_crawler.NumSorteado;

public class GUINotifier {

	private static Object monitor = new Object();
	private static ArrayList<Integer> numerosSorteados;
	private static Integer numeroConcurso = 0;
	
	public void getSync(){
		synchronized (this.monitor) {
			try {
				this.monitor.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<Integer> getNumerosAdicionados(){
		return this.numerosSorteados;
	}
	
	public void setSync(ArrayList<Integer> numerosAdicionados){
		synchronized (this.monitor) {
			this.numerosSorteados = numerosAdicionados;
			this.monitor.notify();
		}
	}
	
	public void setSync(Integer numeroConcurso){
		this.numeroConcurso = numeroConcurso;
		synchronized (this.monitor) {
			this.monitor.notify();
		}
	}
	
	public Integer getNumeroConcurso(){
		return this.numeroConcurso;
	}
}
