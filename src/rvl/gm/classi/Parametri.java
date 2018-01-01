package rvl.gm.classi;

import java.sql.Timestamp;

public class Parametri 
{
	String Campo;
	String Valore;
	String Descrizione;
	Timestamp Data_variazione;
	String Login_variazione;
	
	
	public String getCampo() {
		return Campo;
	}
	public void setCampo(String campo) {
		Campo = campo;
	}
	public String getValore() {
		return Valore;
	}
	public void setValore(String valore) {
		Valore = valore;
	}
	public String getDescrizione() {
		return Descrizione;
	}
	public void setDescrizione(String descrizione) {
		Descrizione = descrizione;
	}
	public Timestamp getData_variazione() {
		return Data_variazione;
	}
	public void setData_variazione(Timestamp data_variazione) {
		Data_variazione = data_variazione;
	}
	public String getLogin_variazione() {
		return Login_variazione;
	}
	public void setLogin_variazione(String login_variazione) {
		Login_variazione = login_variazione;
	}
	
}
