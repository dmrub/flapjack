/**
 * 
 */
package de.dfki.resc28.flapjack.resources;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.DCTerms;

import de.dfki.resc28.flapjack.vocabularies.LDP;
import de.dfki.resc28.igraphstore.IGraphStore;

/**
 * @author resc01
 *
 */
public abstract class Container extends Resource implements IContainer 
{
	//================================================================================
    // Constructors
    //================================================================================
	
	public Container(String resourceURI, IGraphStore graphStore) 
	{
		super(resourceURI, graphStore);
		this.fRDFType = LDP.Container;
	}

	

	//================================================================================
    // HTTP-related 
    //================================================================================

	// TODO: check if the new resource conforms to some shape!
	public Response create(InputStream input, final String contentType)
	{	
		// create a fresh URI for subResource to be added!
		// FIXME: this should depend on some configurable URI pattern!
		UUID subResourceID = java.util.UUID.randomUUID();
		String subResourceURI = String.format("%s/%s", fURI, subResourceID.toString());
		
		final Model subResourceModel = ModelFactory.createDefaultModel();
		RDFDataMgr.read(subResourceModel, input, subResourceURI,  RDFDataMgr.determineLang(null, contentType, null) );	
		
		
		if (subResourceModel == null)
		{
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		

		// Add membership triple to this container
		Model containerModel = this.getModel();
		org.apache.jena.rdf.model.Resource c = containerModel.getResource(fURI);
		org.apache.jena.rdf.model.Resource sr = containerModel.createResource(subResourceURI);
		containerModel.add(c, LDP.contains, sr);
		containerModel.add(c, DCTerms.modified, containerModel.createTypedLiteral(Calendar.getInstance()));
		this.setModel(containerModel);
		

		org.apache.jena.rdf.model.Resource r = subResourceModel.getResource(subResourceURI);
		subResourceModel.add(r, DCTerms.created, subResourceModel.createTypedLiteral(Calendar.getInstance()));
		
		
		
		return null;
		

	}
	
	
	//================================================================================
    // Misc
    //================================================================================
	
	public Set<String> getAllowedMethods() 
    {
		Set<String> allow = super.getAllowedMethods();
		allow.add(HttpMethod.POST);
	    return allow;
    }
}
