package gui;

public class Synchronizer {

	private static Locker monitor = new Locker();
	
	public Locker getSync(){
		return this.monitor;
	}
	
	public void setSync(String action){
		synchronized (this.monitor) {
			if(action.equals("START")){
				this.monitor.setStartAction();
			}else if(action.equals("STOP")){
				this.monitor.setStopAction();
			}else if(action.equals("SAVE")){
				this.monitor.setSaveAction();
			}
			this.monitor.notify();	
		}
	}
}
