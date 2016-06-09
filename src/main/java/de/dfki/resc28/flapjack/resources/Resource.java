/**
 * 
 */
package de.dfki.resc28.flapjack.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDFS;

import de.dfki.resc28.igraphstore.IGraphStore;





/**
 * @author resc01
 *
 */
public abstract class Resource implements IResource 
{
	//================================================================================
    // Member Variables
    //================================================================================
	
	protected String fURI;
	protected IGraphStore fGraphStore;
	protected org.apache.jena.rdf.model.Resource fRDFType;
	
	//================================================================================
    // Constructors
    //================================================================================
	
	/**
	 * 
	 * @param resourceURI
	 * @param model
	 */
	public Resource(String resourceURI, IGraphStore graphStore)
	{
		this.fURI = resourceURI;
		this.fGraphStore = graphStore;
		this.fRDFType = RDFS.Resource;
	}
	
	//================================================================================
    // Getters/Setters
    //================================================================================
	
	
	public String getURI() 
	{
		return fURI; 
	}

	public void setURI(String resourceURI) 
	{
		this.fURI = resourceURI;
	}

	public Model getModel() 
	{
		return fGraphStore.getNamedGraph(fURI);
	}

	public void setModel(Model model) 
	{
		fGraphStore.replaceNamedGraph(fURI, model);
	}

	public String getTypeURI() 
	{
		return this.fRDFType.getURI();
	}
	
	//================================================================================
    // HTTP-related 
    //================================================================================

	
	public Response read(final String contentType)
	{
		final Model description = fGraphStore.getNamedGraph(fURI);
			
		StreamingOutput out = new StreamingOutput() 
		{
			public void write(OutputStream output) throws IOException, WebApplicationException
			{
				RDFDataMgr.write(output, description, RDFDataMgr.determineLang(null, contentType, null)) ;
			}
		};
		
		return Response.ok(out)
					   .type(RDFDataMgr.determineLang(null, contentType, null).getName())
					   .build();
	}
	
	
	// TODO: delete membershipTriples from parentContainer!
	// TODO: if this is a container, delete all subResources!
	public Response delete()
	{
		fGraphStore.deleteNamedGraph(fURI);
		
		return Response.ok(Status.GONE).build();
	}
	

	// TODO: check if updateModel conforms to this resourceShape
	public Response update(InputStream content, final String contentType)
	{
		final Model updateModel = ModelFactory.createDefaultModel();
		RDFDataMgr.read(updateModel, content, fURI,  RDFDataMgr.determineLang(null, contentType, null) );	
		
		updateModel.add(updateModel.getResource(fURI), DCTerms.modified, updateModel.createTypedLiteral(Calendar.getInstance()));	
		
		fGraphStore.replaceNamedGraph(fURI, updateModel);

		StreamingOutput out = new StreamingOutput() 
		{
			public void write(OutputStream output) throws IOException, WebApplicationException
			{
				RDFDataMgr.write(output, updateModel, RDFDataMgr.determineLang(null, contentType, null)) ;
			}
		};
		
		return Response.ok(out)
				   	   .type(RDFDataMgr.determineLang(null, contentType, null).getName())
				   	   .build();
	}
	

	//================================================================================
    // Misc
    //================================================================================
	
	public Set<String> getAllowedMethods()
    {
		HashSet<String> allowedMethods = new HashSet<String>();
		allowedMethods.add(HttpMethod.GET);
		allowedMethods.add(HttpMethod.PUT);
		allowedMethods.add(HttpMethod.DELETE);
		allowedMethods.add(HttpMethod.HEAD);
		allowedMethods.add(HttpMethod.OPTIONS);
		allowedMethods.add("PATCH");

	    return allowedMethods;
    }
}
