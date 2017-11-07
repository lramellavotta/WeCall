package rvl.gm.utility;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zul.Listcell;

@SuppressWarnings("rawtypes")
public class ConvertStringToEsitoContatto implements Converter 
{

	public Object coerceToBean(Object val, org.zkoss.zk.ui.Component comp, BindContext ctx) 
	{
		return "";
	}

	public Object coerceToUi(Object val, org.zkoss.zk.ui.Component comp, BindContext ctx) 
	{
		if(val.toString().equalsIgnoreCase("R")) ((Listcell) comp).setValue("Ricontattare");
		else if(val.toString().equalsIgnoreCase("N")) ((Listcell) comp).setValue("Non risponde");
		else if(val.toString().equalsIgnoreCase("V")) ((Listcell) comp).setValue("Da visitare");
		return ((Listcell) comp).getValue();
	}
}
