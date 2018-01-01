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
 * This error form is adapted from LibrePlan (http://www.libreplan.org/)
 */

package rvl.gm.ui;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;

@SuppressWarnings("rawtypes")
public class _ErrorEvent extends GenericForwardComposer
{
	private static final long serialVersionUID = 1L;

	private static final Log LOG = LogFactory.getLog(_ErrorEvent.class);

    private Textbox stacktrace;
    private Component gComp;

    @SuppressWarnings("unchecked")
	@Override
    public void doAfterCompose(Component comp) throws Exception 
    {
    		super.doAfterCompose(comp);
    		comp.setAttribute("pageErrorController", this, true);
    		gComp = comp;
    		logError();
    		if ( stacktrace != null ) 
    		{
    			stacktrace.setValue(getStacktrace());
    		}
    }
    
    private void logError() 
    {
        Throwable exception = (Throwable) Executions.getCurrent().getAttribute("javax.servlet.error.exception");
        String errorMessage = (String) Executions.getCurrent().getAttribute("javax.servlet.error.message");
        Integer code = (Integer) Executions.getCurrent().getAttribute("javax.servlet.error.status_code");

        if ( code != null ) 
        {
            errorMessage += " [Status Code: " + code + "]";
            if ( code == HttpServletResponse.SC_FORBIDDEN ) 
            {
                String uri = (String) Executions.getCurrent().getAttribute("javax.servlet.error.request_uri");
                errorMessage += " [Request URI: " + uri + "]";
            }
        }
        LOG.error(errorMessage, exception);
    }
    
    private String getStacktrace() 
    {
        Throwable exception = (Throwable) Executions.getCurrent().getAttribute("javax.servlet.error.exception");
        if ( exception != null ) 
        {
            Writer stacktrace = new StringWriter();
            exception.printStackTrace(new PrintWriter(stacktrace));
            return stacktrace.toString();
        }
        return "";
    }
	
    public void onClick$cmdConferma() 
    {
        gComp.detach();
    }

}
