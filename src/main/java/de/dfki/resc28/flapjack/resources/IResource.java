package de.dfki.resc28.flapjack.resources;

import java.io.InputStream;
import java.util.Set;

import javax.ws.rs.core.Response;

import org.apache.jena.rdf.model.Model;

public interface IResource 
{
	//================================================================================
    // Getters/Setters
    //================================================================================
	
	public abstract String getURI();
	
	public abstract void setURI(String resourceURI);

	public abstract Model getModel();

	public abstract void setModel(Model model);

	public abstract String getTypeURI();
	
	public abstract Set<String> getAllowedMethods();
	
	//================================================================================
    // HTTP-related 
    //================================================================================
	
	public abstract Response read(final String contentType);
	
	public abstract Response delete();
	
	public abstract Response update(InputStream content, final String contentType);
}
