package rvl.gm.classi;

import java.sql.Timestamp;

public class Telefoni 
{
	int Progressivo;
	String Descrizione;
	String Indirizzo_IP;
	String Interno;
	
	Timestamp Data_inserimento;
	Timestamp Data_variazione;
	String Login_inserimento;
	String Login_variazione;
	
	public int getProgressivo() {
		return Progressivo;
	}
	public void setProgressivo(int progressivo) {
		Progressivo = progressivo;
	}
	public String getDescrizione() {
		return Descrizione;
	}
	public void setDescrizione(String descrizione) {
		Descrizione = descrizione;
	}
	public String getIndirizzo_IP() {
		return Indirizzo_IP;
	}
	public void setIndirizzo_IP(String indirizzo_IP) {
		Indirizzo_IP = indirizzo_IP;
	}
	public String getInterno() {
		return Interno;
	}
	public void setInterno(String interno) {
		Interno = interno;
	}
	public Timestamp getData_inserimento() {
		return Data_inserimento;
	}
	public void setData_inserimento(Timestamp data_inserimento) {
		Data_inserimento = data_inserimento;
	}
	public Timestamp getData_variazione() {
		return Data_variazione;
	}
	public void setData_variazione(Timestamp data_variazione) {
		Data_variazione = data_variazione;
	}
	public String getLogin_inserimento() {
		return Login_inserimento;
	}
	public void setLogin_inserimento(String login_inserimento) {
		Login_inserimento = login_inserimento;
	}
	public String getLogin_variazione() {
		return Login_variazione;
	}
	public void setLogin_variazione(String login_variazione) {
		Login_variazione = login_variazione;
	}
}
