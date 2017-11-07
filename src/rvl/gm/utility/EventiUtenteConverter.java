package rvl.gm.utility;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Listcell;


@SuppressWarnings("rawtypes")
public class EventiUtenteConverter extends GenericForwardComposer implements TypeConverter, java.io.Serializable 
{

	private static final long serialVersionUID = -1861088338050584550L;

	public Object coerceToBean(Object arg0, Component arg1) 
	{
		return null;
	}


	//
	// Prima di essere elaborata dal browser posso ritoccare la videata
	//
	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Component comp) throws Exception 
	{
		super.doAfterCompose(comp);
	}
  
	public Object coerceToUi(Object arg0, Component arg1) 
	{
		if (arg0 == null) return null;

		Integer input = (Integer) arg0;
	    
		if (arg1 instanceof Listcell) 
		{
			Listcell cell = (Listcell) arg1;
			cell.setLabel(getLabelDes(input));
		} 
		return null;
	}
	  
	private String getLabelDes(Integer _int) 
	{
		String _comodo=null;
		switch (_int)
		{
			case 1: _comodo="Eseguito login"; break;
			case 2: _comodo="Uscito dal programma";break;
			case 3: _comodo="Cambiata password";break;
			case 4: _comodo="Comunicato password scaduta";break;
			default: _comodo="non gestito"; break;
		}
		return _comodo;
	}
} 
