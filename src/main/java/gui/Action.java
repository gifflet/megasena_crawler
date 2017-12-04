package gui;

public enum Action {

	UPDATE_NUMBERS, UPDATE_NUMERO_CONCURSO, UPDATE_ESTADO_EXECUCAO;

	boolean update_numbers, update_numero_concurso, update_estado_execucao;
	
	public void setAction(Action action){
		if(action == UPDATE_ESTADO_EXECUCAO){
			this.update_estado_execucao = true;
			this.update_numbers = false;
			this.update_numero_concurso = false;
		}else if(action == UPDATE_NUMERO_CONCURSO){
			this.update_estado_execucao = false;
			this.update_numbers = false;
			this.update_numero_concurso = true;	
		}else if(action == UPDATE_NUMBERS){
			this.update_estado_execucao = false;
			this.update_numbers = true;
			this.update_numero_concurso = false;
		}
	}
	
	public Action getAction(){
		if(update_estado_execucao)
			return Action.UPDATE_ESTADO_EXECUCAO;
		if(update_numbers)
			return Action.UPDATE_NUMBERS;
		if(update_numero_concurso)
			return Action.UPDATE_NUMERO_CONCURSO;
		return this;
	}
}
