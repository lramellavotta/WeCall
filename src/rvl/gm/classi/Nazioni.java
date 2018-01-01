package rvl.gm.classi;

import java.sql.Timestamp;

public class Nazioni 
{
	int Progressivo;
	String Descrizione;
	String Sigla;
	Boolean Abilitata;
	String Prefisso_telefonico;
	String Anonimo;
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

	public String getSigla() {
		return Sigla;
	}

	public void setSigla(String sigla) {
		Sigla = sigla;
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

	public Boolean getAbilitata() {
		return Abilitata;
	}

	public void setAbilitata(Boolean abilitata) {
		Abilitata = abilitata;
	}

	public String getPrefisso_telefonico() {
		return Prefisso_telefonico;
	}

	public void setPrefisso_telefonico(String prefisso_telefonico) {
		Prefisso_telefonico = prefisso_telefonico;
	}

	public String getAnonimo() {
		return Anonimo;
	}

	public void setAnonimo(String anonimo) {
		Anonimo = anonimo;
	}
}
