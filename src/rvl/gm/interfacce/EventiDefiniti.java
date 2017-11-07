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