/**
 * 
 */
package de.dfki.resc28.flapjack.services;

import java.io.InputStream;
import java.net.URI;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import de.dfki.resc28.flapjack.resources.IContainer;
import de.dfki.resc28.flapjack.resources.IResource;
import de.dfki.resc28.flapjack.resources.IResourceManager;
import de.dfki.resc28.igraphstore.Constants;





/**
 * @author resc01
 *
 */
public abstract class BaseService 
{
	//================================================================================
    // Constructors
    //================================================================================
	public BaseService ()
	{
		
	}

	//================================================================================
    // HTTP-Request Handlers
    //================================================================================
	
	@GET
	@Produces({ Constants.CT_TEXT_TURTLE, Constants.CT_APPLICATION_RDFXML, Constants.CT_APPLICATION_XTURTLE, Constants.CT_APPLICATION_JSON, Constants.CT_APPLICATION_LD_JSON })
	public Response get( @HeaderParam(HttpHeaders.ACCEPT) String acceptType )
	{
		IResource r = getResourceManager().get(getCanonicalURL(fRequestUrl.getRequestUri()));
		
		if (r == null)
		{
			return Response.status(Status.NOT_FOUND).build();
		}
    	else if (!(r instanceof IResource))
    	{
    		return Response.status(Status.BAD_REQUEST).build();
    	}
		else if (!(r.getAllowedMethods().contains(HttpMethod.GET)))
		{
			return Response.status(Status.METHOD_NOT_ALLOWED).build();
		}

		return r.read(acceptType);
	}
	
	@DELETE
	public Response delete()
	{
		IResource r = getResourceManager().get(getCanonicalURL(fRequestUrl.getRequestUri()));
		
		if (r == null)
		{
			return Response.status(Status.NOT_FOUND).build();
		}
    	else if (!(r instanceof IResource))
    	{
    		return Response.status(Status.BAD_REQUEST).build();
    	}
		else if (!(r.getAllowedMethods().contains(HttpMethod.DELETE)))
		{
			return Response.status(Status.METHOD_NOT_ALLOWED).build();
		}
		
		return r.delete();
	}
	
	@PUT
	@Consumes({ Constants.CT_TEXT_TURTLE, Constants.CT_APPLICATION_RDFXML, Constants.CT_APPLICATION_XTURTLE, Constants.CT_APPLICATION_JSON, Constants.CT_APPLICATION_LD_JSON })
	public Response put( InputStream content, @HeaderParam(HttpHeaders.CONTENT_TYPE) String contentType )
	{
		IResource r = getResourceManager().get(getCanonicalURL(fRequestUrl.getRequestUri()));
		
		if (r == null)
		{
			return Response.status(Status.NOT_FOUND).build();
		}
    	else if (!(r instanceof IContainer))
		{
    		return Response.status(Status.BAD_REQUEST).build();
		}
    	else if (!(r.getAllowedMethods().contains("PUT")))
    	{
    		return Response.status(Status.METHOD_NOT_ALLOWED).build();
    	}
		
		return r.update(content, contentType);
	}
	
	@POST
	@Consumes({ Constants.CT_TEXT_TURTLE, Constants.CT_APPLICATION_RDFXML, Constants.CT_APPLICATION_XTURTLE, Constants.CT_APPLICATION_JSON, Constants.CT_APPLICATION_LD_JSON })
	public Response post( InputStream content, @HeaderParam(HttpHeaders.CONTENT_TYPE) String contentType )
	{
		IResource r = getResourceManager().get(getCanonicalURL(fRequestUrl.getRequestUri()));
		
		if (r == null)
		{
			return Response.status(Status.NOT_FOUND).build();
		}
    	else if (!(r instanceof IContainer))
		{
    		return Response.status(Status.BAD_REQUEST).build();
		}
    	else if (!(r.getAllowedMethods().contains("POST")))
    	{
    		return Response.status(Status.METHOD_NOT_ALLOWED).build();
    	}
		
		return ((IContainer)r).create(content, contentType);
		
	}
	
	//================================================================================
    // Protected Methods
    //================================================================================
	
	protected IResourceManager getResourceManager()
	{
		return fResourceManager;
	}
	
	protected IContainer getRootContainer()
	{
		return fRootContainer;
	}
	
    protected String getCanonicalURL (URI url)
    {
    	return url.toString();
    }
    
	//================================================================================
    // Member variables
    //================================================================================

	@Context HttpServletRequest fRequest;
	@Context protected ServletContext fContext;
	@Context protected HttpHeaders fRequestHeaders;
	@Context protected UriInfo fRequestUrl;
	
	protected IResourceManager fResourceManager;
	protected IContainer fRootContainer;
	
	protected static String fPublicURI = null;
}
