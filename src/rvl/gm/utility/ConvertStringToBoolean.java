package rvl.gm.utility;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;

@SuppressWarnings("rawtypes")
public class ConvertStringToBoolean implements Converter 
{

	public Object coerceToBean(Object val, org.zkoss.zk.ui.Component comp, BindContext ctx) 
	{
		if(val.toString().equalsIgnoreCase("false"))
			return "0";
		else
			return "1";
	}

	public Object coerceToUi(Object val, org.zkoss.zk.ui.Component comp, BindContext ctx) 
	{
		Boolean comodo = false;
		
		if(val==null)
			;
		else
			comodo = Boolean.valueOf( (val.toString().equalsIgnoreCase("0") || val.toString().equalsIgnoreCase("false")) ? "false" : "true"  );
		
		return comodo;

	}
}
