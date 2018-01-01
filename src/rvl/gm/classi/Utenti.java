package rvl.gm.classi;

import java.sql.Timestamp;

public class Utenti 
{
	String Utente;
	String Passwd;
	String Verifica;
	String Tipo;
	String Cognome;
	String Nome;
	Boolean Abilitato;
	String Interno_telefonico;
	int Centralino_utilizzato;
	String Canale_telefonico;
	String Colore_chiamata;
	int Nazione;
	String Avatar;
	Timestamp Data_cambio_passwd;
	Timestamp Data_abilitazione;
	Timestamp Data_disabilitazione;
	Timestamp Data_ultimo_login;
	Timestamp Data_inserimento;
	Timestamp Data_variazione;
	String Login_inserimento;
	String Login_variazione;
	
	public String getUtente() {
		return Utente;
	}
	public void setUtente(String utente) {
		Utente = utente;
	}
	public String getPasswd() {
		return Passwd;
	}
	public void setPasswd(String passwd) {
		Passwd = passwd;
	}
	public String getVerifica() {
		return Verifica;
	}
	public void setVerifica(String verifica) {
		Verifica = verifica;
	}
	public String getTipo() {
		return Tipo;
	}
	public void setTipo(String tipo) {
		Tipo = tipo;
	}
	public String getCognome() {
		return Cognome;
	}
	public void setCognome(String cognome) {
		Cognome = cognome;
	}
	public String getNome() {
		return Nome;
	}
	public void setNome(String nome) {
		Nome = nome;
	}
	public Boolean getAbilitato() {
		return Abilitato;
	}
	public void setAbilitato(Boolean abilitato) {
		Abilitato = abilitato;
	}
	public String getInterno_telefonico() {
		return Interno_telefonico;
	}
	public void setInterno_telefonico(String interno_telefonico) {
		Interno_telefonico = interno_telefonico;
	}
	public int getCentralino_utilizzato() {
		return Centralino_utilizzato;
	}
	public void setCentralino_utilizzato(int centralino_utilizzato) {
		Centralino_utilizzato = centralino_utilizzato;
	}
	public String getCanale_telefonico() {
		return Canale_telefonico;
	}
	public void setCanale_telefonico(String canale_telefonico) {
		Canale_telefonico = canale_telefonico;
	}
	public String getColore_chiamata() {
		return Colore_chiamata;
	}
	public void setColore_chiamata(String colore_chiamata) {
		Colore_chiamata = colore_chiamata;
	}
	public int getNazione() {
		return Nazione;
	}
	public void setNazione(int nazione) {
		Nazione = nazione;
	}
	public String getAvatar() {
		return Avatar;
	}
	public void setAvatar(String avatar) {
		Avatar = avatar;
	}
	public Timestamp getData_cambio_passwd() {
		return Data_cambio_passwd;
	}
	public void setData_cambio_passwd(Timestamp data_cambio_passwd) {
		Data_cambio_passwd = data_cambio_passwd;
	}
	public Timestamp getData_abilitazione() {
		return Data_abilitazione;
	}
	public void setData_abilitazione(Timestamp data_abilitazione) {
		Data_abilitazione = data_abilitazione;
	}
	public Timestamp getData_disabilitazione() {
		return Data_disabilitazione;
	}
	public void setData_disabilitazione(Timestamp data_disabilitazione) {
		Data_disabilitazione = data_disabilitazione;
	}
	public Timestamp getData_ultimo_login() {
		return Data_ultimo_login;
	}
	public void setData_ultimo_login(Timestamp data_ultimo_login) {
		Data_ultimo_login = data_ultimo_login;
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
