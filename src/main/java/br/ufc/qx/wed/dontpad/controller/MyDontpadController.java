package main.java.br.ufc.qx.wed.dontpad.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public abstract class MyDontpadController extends HttpServlet{

	private static final long serialVersionUID = -7169839991248123034L;
	
	private enum TipoMensagem {
		SUCESSO("sucesso"), ERRO("erro"), INFO("info"), ATENCAO("atencao");
		
		private String tipo;
		
		TipoMensagem(String tipo){
			this.tipo = tipo;
		}
		
		public String getTipoAsText(){
			return this.tipo;
		}
		
		@Override
		public String toString() {
			return getTipoAsText();
		}
	}
	
	private void feedbackMensagem(TipoMensagem tipo, String mensagem, HttpServletRequest request){
		request.setAttribute("feedback", tipo.toString());
		request.setAttribute("mensagem", mensagem);
	}
	
	public void sucessoMensagem(String mensagem, HttpServletRequest request){
		feedbackMensagem(TipoMensagem.SUCESSO, mensagem, request);
	}
	
	public void erroMensagem(String mensagem, HttpServletRequest request){
		feedbackMensagem(TipoMensagem.ERRO, mensagem, request);
	}

}
