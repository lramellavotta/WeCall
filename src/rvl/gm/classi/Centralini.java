/*
 * This file is part of WeCall
 *
 * Copyright (C) 20017     Luca Ramella Votta
 *                         luca.ramellavotta@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package rvl.gm.classi;

import java.sql.Timestamp;

public class Centralini 
{
		int Progressivo;
		String Descrizione;
		String IndirizzoIP;
		int Nazione;
		String Manager_login;
		String Manager_password;
		Boolean Registra_telefonate;
		Boolean Anonimo;
		String ApacheIP;
		String ApacheUser;
		String ApachePass;
		String Contesto;
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

		public String getIndirizzoIP() {
			return IndirizzoIP;
		}

		public void setIndirizzoIP(String indirizzoIP) {
			IndirizzoIP = indirizzoIP;
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

		public int getNazione() {
			return Nazione;
		}

		public void setNazione(int nazione) {
			Nazione = nazione;
		}

		public String getManager_login() {
			return Manager_login;
		}

		public void setManager_login(String manager_login) {
			Manager_login = manager_login;
		}

		public String getManager_password() {
			return Manager_password;
		}

		public void setManager_password(String manager_password) {
			Manager_password = manager_password;
		}

		public Boolean getRegistra_telefonate() {
			return Registra_telefonate;
		}

		public void setRegistra_telefonate(Boolean registra_telefonate) {
			Registra_telefonate = registra_telefonate;
		}

		public String getApacheIP() {
			return ApacheIP;
		}

		public void setApacheIP(String apacheIP) {
			ApacheIP = apacheIP;
		}

		public String getApacheUser() {
			return ApacheUser;
		}

		public void setApacheUser(String apacheUser) {
			ApacheUser = apacheUser;
		}

		public String getApachePass() {
			return ApachePass;
		}

		public void setApachePass(String apachePass) {
			ApachePass = apachePass;
		}

		public Boolean getAnonimo() {
			return Anonimo;
		}

		public void setAnonimo(Boolean anonimo) {
			Anonimo = anonimo;
		}

		public String getContesto() {
			return Contesto;
		}

		public void setContesto(String contesto) {
			Contesto = contesto;
		}

}
