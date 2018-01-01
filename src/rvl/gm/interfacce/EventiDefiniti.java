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
package rvl.gm.interfacce;

public interface EventiDefiniti 
{
	
	int LOGIN        = 1;
	int LOGOUT       = 2;
	int CAMBIO_PWD   = 3;
	int PWD_SCADUTA  = 4;
	
	String DES_LOGIN    = "Eseguito login";
	String DES_LOGOUT   = "Uscito dal programma";
	String DES_MOD_PWD  = "Modificata password";
	String DES_PWD_SCAD = "Avviso di password scaduta";
}