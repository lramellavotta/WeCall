package rvl.gm.ui;


import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.mariadb.jdbc.Driver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import rvl.gm.classi.Centralini;
import rvl.gm.classi.Nazioni;
import rvl.gm.classi.Telefoni;
import rvl.gm.classi.Utenti;
import rvl.gm.dao.CentraliniDAO;
import rvl.gm.dao.NazioniDAO;
import rvl.gm.dao.ParametriDAO;
import rvl.gm.dao.TelefoniDAO;
import rvl.gm.dao.UtentiDAO;
import rvl.gm.utility.LoginSecure;

/*
 * classe per la gestione eventi della videata di login
 */
public class Login extends Window
{
	private static final String Version="1.0.0  /  01-Nov-17";
	private static final long serialVersionUID = 1L;
	
	private Desktop gDesktop;
	private Utenti gUser;
	private String gServer;
	private List<Centralini> lCentralini;
	private Centralini utCentralini;
	
	private List<Telefoni> lstTelefoni;
	private Telefoni selCmbTelefoni;

	
	private List<Nazioni> lstNazioni;
	private Nazioni selCmbNazioni;
	
	private DriverManagerDataSource gDm;
	
	private final String  gTitoloVideata = "Videata di login";
	private Boolean gOk=true;

	List<Utenti> lstUtenti;
	Iterator<Utenti> itUtenti;
	Utenti utUtenti;
	
	final int ERR_DB_CONN = -9;
	final int ERR_SERVER = -10;
	final int ERR_USER_NOT_FOUND = -1;
	final int ERR_USER_PWD = -2;
	final int ERR_USER_NOT_ACTIVE = -3;
	final int ERR_USER_NOT_AUTH = -4;
	final int ERR_GENERIC = -99;
	final int ERR_OK = 0;
	
	@Wire("#winLogin")
	private Window winLogin;
	
	@Wire("#btnLogin")
	private Button btnLogin;
	@Wire("#txtUserName")
	private Textbox username;
	@Wire("#txtPasswD")
	private Textbox password;
	@Wire("#rg1")
	private Radiogroup rg1;
	@Wire("#lblVersione")
	private Label lblVersione;
	
	@Wire("#cmbNazioni")
	private Combobox cmbNazioni;
	@Wire("#cmbTelefoni")
	private Combobox cmbTelefoni;
	
	@SuppressWarnings("unused")
	private Driver driver;
	
   
	//
	//
	// Inizio codice
	//
	//
    
	@Init
    public void init() 
	{
		gUser = new Utenti();
		gOk=true;
		azzeraDatiSessione();
    }
		
	@AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);
        
		lblVersione.setValue(Version);

        btnLogin.setDisabled(false);     
        
        
		WebApplicationContext wac = ContextLoaderListener.getCurrentWebApplicationContext();
		if (wac == null)
		{
			throw new IllegalStateException("Errore nel recupero beans applicativo...");
		}
		else
		{
			Object dataSource = wac.getBean("dataSource");
			gDm = (DriverManagerDataSource) dataSource;
			gServer = gDm.getUrl().substring(gDm.getUrl().indexOf("/")+2,gDm.getUrl().lastIndexOf(":")).toString();
			checkMySQL(false);
			if(gOk) 
				caricaNazioni();
			else
			{
				Messagebox.show("Database:"+gServer+" non raggiungibile. Impossibile continuare\nContattare l'assistenza", gTitoloVideata, Messagebox.OK , Messagebox.ERROR);
				btnLogin.setDisabled(true);
			}
		}
		username.setFocus(true);
    }
			
	private boolean checkIpServer(String pServer)
	{
		try 
		{
			InetAddress address = InetAddress.getByName(pServer);
			if(!address.isReachable(500))
				gOk = false;
			else 
				gOk = true;
		}
		catch (Exception e)
		{
			Messagebox.show(e.getMessage().toString(), gTitoloVideata, Messagebox.OK , Messagebox.ERROR);
			gOk = false;
		}
		return gOk;
	}
	
	private boolean checkMySQL(Boolean pMessaggio)
	{
		try
		{
			driver = new Driver();
			UtentiDAO pdao = (UtentiDAO)SpringUtil.getBean("UtentiDAO");
			pdao.checkConnection();
		} catch (org.springframework.dao.DataAccessException dae)
		{
			if(pMessaggio) Messagebox.show(dae.getMessage(),gTitoloVideata,Messagebox.OK,Messagebox.ERROR);
			gOk = false;
		}
		return gOk;
	}
	
	@Command
	public void caricaTelefoni()
	{
		cmbTelefoni.setValue("<non trovato>");
		TelefoniDAO ndao = (TelefoniDAO)SpringUtil.getBean("TelefoniDAO");
		List<Telefoni> lTelefoni;
		lTelefoni = ndao.findTelefonoUtente(username.getValue().toString().trim());
		
		lstTelefoni = ndao.findAll();
		
		Telefoni utTelefoni;
		Iterator<Telefoni> itTelefoni;
		for (itTelefoni = lTelefoni.iterator(); itTelefoni.hasNext(); ) 
		{
			utTelefoni = (Telefoni)itTelefoni.next(); 
		    selCmbTelefoni = utTelefoni;
		    cmbTelefoni.setValue(utTelefoni.getDescrizione());
		}
		BindUtils.postNotifyChange(null,null,Login.this,"lstTelefoni");
		
	}
	
	
	private void caricaNazioni()
	{
		cmbNazioni.setValue("<non trovato>");
		int naz = 106;	// Italia
		NazioniDAO ndao = (NazioniDAO)SpringUtil.getBean("NazioniDAO");
		List<Nazioni> lNazioni;
		lNazioni = ndao.findNazione(naz);
		
		lstNazioni = ndao.findAll("A");
		
		Nazioni utNazioni;
		Iterator<Nazioni> itNazioni;
		for (itNazioni = lNazioni.iterator(); itNazioni.hasNext(); ) 
		{
		    utNazioni = (Nazioni)itNazioni.next(); 
		    selCmbNazioni = utNazioni;
		    cmbNazioni.setValue(utNazioni.getDescrizione());
		}
	}
	
	@Command
	public void onLogin()
	{
		List<Utenti> lstUtenti;
		Iterator<Utenti> itUtenti;
		int check_status=ERR_OK;
		
		CentraliniDAO ddao = (CentraliniDAO)SpringUtil.getBean("CentraliniDAO");
        		
		azzeraDatiSessione();
		gOk=true;
				
		//
		// Ritesto se è tutto ok come al caricamento iniziale
		//
		checkIpServer(gServer);
		if(gOk) checkMySQL(true);

		
		if(username.getValue().length()<1)
		{
			Messagebox.show("Utente non compilato", gTitoloVideata, Messagebox.OK , Messagebox.ERROR);
			gOk = false;
		}
		
		if(password.getValue().length()<1)
		{
			Messagebox.show("Password non compilata", gTitoloVideata, Messagebox.OK , Messagebox.ERROR);
			gOk = false;
		}
		
		if(gOk)
		{
			UtentiDAO udao = (UtentiDAO)SpringUtil.getBean("UtentiDAO");
			lstUtenti = udao.findUser(username.getValue().toString().trim());
			if(lstUtenti == null || lstUtenti.isEmpty())
			{
				Messagebox.show("Utente non presente. \nCorreggi il campo utente e riprova",
						gTitoloVideata,Messagebox.OK,Messagebox.ERROR);
			}
			else
			{
				itUtenti = lstUtenti.iterator();
				gUser = itUtenti.next();
				LoginSecure ls = new LoginSecure();
				try 
				{					
					if(ls.verificaPasswd(gUser, password.getValue().toString().trim()))
					{
						if(gUser.getAbilitato().equalsIgnoreCase("0"))
							check_status=ERR_USER_NOT_ACTIVE;
						else
						{
							check_status=ERR_OK;
							if(gUser.getTipo().equalsIgnoreCase("A"))
								;
							else
							{
								if(gUser.getNazione()!=selCmbNazioni.getProgressivo())
									check_status=ERR_USER_NOT_AUTH;
							}
						}

					} else
						check_status=ERR_USER_PWD;

				} catch (NoSuchAlgorithmException e) 
				{
					Messagebox.show(e.getMessage(),gTitoloVideata,Messagebox.OK,Messagebox.ERROR);
					check_status=ERR_GENERIC;
				} catch (WrongValueException e) 
				{
					Messagebox.show(e.getMessage(),gTitoloVideata,Messagebox.OK,Messagebox.ERROR);
					check_status=ERR_GENERIC;
				} catch (SQLException e) 
				{
					Messagebox.show(e.getMessage(),gTitoloVideata,Messagebox.OK,Messagebox.ERROR);
					check_status=ERR_GENERIC;
				}
					

				switch(check_status)
				{
				case ERR_USER_NOT_ACTIVE: 
					Messagebox.show("Utente disabilitato \nContattare l'assistenza",
							gTitoloVideata,Messagebox.OK,Messagebox.ERROR);
					break;
				case ERR_USER_NOT_AUTH: 
					Messagebox.show("Utente non abilitato per la nazione selezionata \nContattare l'assistenza",
							gTitoloVideata,Messagebox.OK,Messagebox.ERROR);
					break;
				case ERR_USER_PWD:
					Messagebox.show("Password errata. \nCorreggi il campo password e riprova",
							gTitoloVideata,Messagebox.OK,Messagebox.ERROR);
					break;
				case ERR_GENERIC:
					break;
				}
				
				if(check_status==ERR_OK)
				{
					lCentralini = ddao.findCentralino(gUser.getCentralino_utilizzato());
					try
					{
						utCentralini=lCentralini.get(0);
					} catch(Exception e)
					{
						Messagebox.show(e.getMessage(),gTitoloVideata,Messagebox.OK,Messagebox.ERROR);
						check_status=ERR_GENERIC;
					}
				}


				if(check_status==ERR_OK)
				{

					URL r = this.getClass().getResource("/");
					String decoded=null;
					try {
						decoded = URLDecoder.decode(r.getFile(), "UTF-8");
					} catch (UnsupportedEncodingException e) 
					{
						Messagebox.show(e.getMessage(),gTitoloVideata,Messagebox.OK,Messagebox.ERROR);
					}
					
					gUser.setCanale_telefonico(selCmbTelefoni.getDescrizione());
					gUser.setInterno_telefonico(selCmbTelefoni.getInterno());
					
					Map<String, Object> map = new HashMap<String, Object>();
					ParametriDAO pdao = (ParametriDAO)SpringUtil.getBean("ParametriDAO");
					map=pdao.leggiParametro("debug");
					gDesktop.setAttribute("gDebug",map.get("valore").toString());

					gDesktop.setAttribute("gUtente", gUser);
					gDesktop.setAttribute("isConnected", new Boolean(true));
					gDesktop.setAttribute("gReportPath", decoded+"rvl/gm/ui/reports/");
					gDesktop.setAttribute("winLogin", winLogin);

					gDesktop.setAttribute("gNazione", selCmbNazioni);
					gDesktop.setAttribute("gCentralino", utCentralini);
					
					// Per la gestione stampe ho necessità di questi dati 
					//
					gDesktop.setAttribute("gDataSourceURL", 	gDm.getUrl());
					gDesktop.setAttribute("gDataSourceUsername",gDm.getUsername());
					gDesktop.setAttribute("gDataSourcePassword",gDm.getPassword());

					Window menuWin = (Window)Executions.createComponents("menu.zul",null,null);
					menuWin.doOverlapped();

				}
			}
		}
	}

	private void azzeraDatiSessione()
	{
		gDesktop = Executions.getCurrent().getDesktop(); 
		
		gDesktop.removeAttribute("gUtente");
		gDesktop.removeAttribute("gServer");
		gDesktop.removeAttribute("isConnected");
		gDesktop.removeAttribute("gReportPath");
		gDesktop.removeAttribute("winLogin");
		gDesktop.removeAttribute("gDebug");
		gDesktop.removeAttribute("gNazione");
		gDesktop.removeAttribute("gDesNazione");
		gDesktop.removeAttribute("gCentralino");
		gDesktop.removeAttribute("gDataSourceURL");
		gDesktop.removeAttribute("gDataSourceUsername");
		gDesktop.removeAttribute("gDataSourcePassword");
	}

	public Combobox getCmbNazioni() 
	{
		return cmbNazioni;
	}

	public void setCmbNazioni(Combobox cmbNazioni) 
	{
		this.cmbNazioni = cmbNazioni;
	}
	
	public Nazioni getSelCmbNazioni() 
	{
		return selCmbNazioni;
	}

	public void setSelCmbNazioni(Nazioni selCmbNazioni) 
	{
		this.selCmbNazioni = selCmbNazioni;
	}

	public List<Nazioni> getLstNazioni() 
	{
		return lstNazioni;
	}

	public void setLstNazioni(List<Nazioni> lstNazioni) 
	{
		this.lstNazioni = lstNazioni;
	}

	public List<Telefoni> getLstTelefoni() {
		return lstTelefoni;
	}

	public void setLstTelefoni(List<Telefoni> lstTelefoni) {
		this.lstTelefoni = lstTelefoni;
	}

	public Telefoni getSelCmbTelefoni() {
		return selCmbTelefoni;
	}

	public void setSelCmbTelefoni(Telefoni selCmbTelefoni) {
		this.selCmbTelefoni = selCmbTelefoni;
	}

}
