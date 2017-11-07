package rvl.gm.utility;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;

@SuppressWarnings("rawtypes")
public class ConvertStringToEsitoAcquisto implements Converter 
{

	public Object coerceToBean(Object val, org.zkoss.zk.ui.Component comp, BindContext ctx) 
	{
		return true;
	}

	public Object coerceToUi(Object val, org.zkoss.zk.ui.Component comp, BindContext ctx) 
	{
		String comodo="";
		if(val==null)
			;
		else
		{
			if(val.toString().equalsIgnoreCase("O")) comodo="Ok";
			else if(val.toString().equalsIgnoreCase("R")) comodo="Rifiuta";
			else if(val.toString().equalsIgnoreCase("I")) comodo="Irreperibile";
			else if(val.toString().equalsIgnoreCase("N")) comodo="Non passato";
			else if(val.toString().equalsIgnoreCase("S")) comodo="Senza mat.";
			else if(val.toString().equalsIgnoreCase("A")) comodo="Annullato";
			else if(val.toString().equalsIgnoreCase("D")) comodo="Donazione";
		}
		return comodo;

	}
}
