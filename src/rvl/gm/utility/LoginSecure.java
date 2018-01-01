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
package rvl.gm.utility;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;

import rvl.gm.classi.Utenti;


public class LoginSecure
{

	private final static int CICLI = 2000;

	public String[] codificaPassword(String txt_password) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException
	{
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");

		byte[] b64_verifica = new byte[8];
		sr.nextBytes(b64_verifica);

		byte[] b64_password = getHash(CICLI, txt_password, b64_verifica);

		String txt_password_secured = byteToBase64(b64_password);
		String txt_verifica_secured = byteToBase64(b64_verifica);

		String txt_ret[] = new String[2];
		txt_ret[0] = txt_password_secured;
		txt_ret[1] = txt_verifica_secured;
		return txt_ret;
	}

	/**
	 * Restituisce il corrispondente di una password, e di una verifica (salt) 
	 * dopo un certo numero di cicli per migliorare la difficoltà
	 * di decrypt da parte di hackers
	 * 
	 * @param cicli int Numero di cicli da eseguite
	 * @param password String 
	 * @param b64_txt_verifica byte[] Il (salt) 
	 * @return byte[] La password calcolata
	 * @throws NoSuchAlgorithmException / UnsupportedEncodingException
	 **/
	public byte[] getHash(int cicli, String password, byte[] b64_verifica) throws NoSuchAlgorithmException, UnsupportedEncodingException 
	{
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.reset();
		md.update(b64_verifica);
		byte[] input = md.digest(password.getBytes("UTF-8"));
		for (int i = 0; i < cicli; i++) 
		{
			md.reset();
			input = md.digest(input);
		}
		return input;
	}
	
	public boolean verificaPasswd(Utenti ut, String txt_password) throws SQLException, NoSuchAlgorithmException
	{			
		if(txt_password==null || txt_password.isEmpty()) 
			throw new SQLException("Password non compilata");
		else
		{
			try
			{
				byte[] b64_password = base64ToByte(ut.getPasswd());
				byte[] b64_verifica = base64ToByte(ut.getVerifica());
				byte[] b64_passwd_calc = getHash(CICLI, txt_password, b64_verifica);
				
				return Arrays.equals(b64_passwd_calc, b64_password);

			} catch (IOException ex)
			{
				throw new SQLException("Problemi nel calcolare correttamente campo verifica e password: "+ex.getMessage());
			}
		}
	}

	public byte[] base64ToByte(String data) throws IOException 
	{
		Base64 decoder = new Base64();
		return decoder.decode(data);
	}

	public String byteToBase64(byte[] data)
	{
		Base64 endecoder = new Base64();
		return endecoder.encodeToString(data);
	}
}
