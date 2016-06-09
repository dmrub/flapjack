/**
 * 
 */
package de.dfki.resc28.flapjack;

import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import javax.ws.rs.core.Application;

/**
 * @author resc01
 *
 */
public abstract class Server extends Application
{
	@Override
    public Set<Class<?>> getClasses() 
    {
    	configure();  	
    	return null;
    }
    
    protected void configure()
    {
    	try 
		{
			configuration = new XMLConfiguration("config.properties");
			// ROOT_APP_URL = (String) configuration.getProperty("server.rooturi");
			// ...
		}
		catch (ConfigurationException e)
		{
			e.printStackTrace();
		}
    }
    
    
	//================================================================================
    // Members
    //================================================================================
    public static XMLConfiguration configuration;

}
