package rvl.gm.classi;

import java.sql.Timestamp;

public class EventiUtente 
{
	  int Progressivo;
	  String Utente;
	  Timestamp Data_evento;
	  int Evento;
	  String des_evento;
	  
	public int getProgressivo() {
		return Progressivo;
	}
	public void setProgressivo(int progressivo) {
		Progressivo = progressivo;
	}
	public String getUtente() {
		return Utente;
	}
	public void setUtente(String utente) {
		Utente = utente;
	}
	public Timestamp getData_evento() {
		return Data_evento;
	}
	public void setData_evento(Timestamp data_evento) {
		Data_evento = data_evento;
	}
	public int getEvento() {
		return Evento;
	}
	public void setEvento(int evento) {
		Evento = evento;
	}
	public String getDes_evento() {
		return des_evento;
	}
	public void setDes_evento(String des_evento) {
		this.des_evento = des_evento;
	}

	
	  
}
