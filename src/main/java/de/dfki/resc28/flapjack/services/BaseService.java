/*
 * This file is part of flapjack. It is subject to the license terms in
 * the LICENSE file found in the top-level directory of this distribution.
 * You may not use this file except in compliance with the License.
 */
package de.dfki.resc28.flapjack.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFDataMgr;

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
	public BaseService ()
	{
		
	}

	@GET
	@Produces({ Constants.CT_APPLICATION_JSON_LD, Constants.CT_APPLICATION_NQUADS, Constants.CT_APPLICATION_NTRIPLES, Constants.CT_APPLICATION_RDF_JSON, Constants.CT_APPLICATION_RDFXML, Constants.CT_APPLICATION_TRIX, Constants.CT_APPLICATION_XTURTLE, Constants.CT_TEXT_N3, Constants.CT_TEXT_TRIG, Constants.CT_TEXT_TURTLE })
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
	
	@GET
	@Produces(MediaType.TEXT_HTML) 
	public Response getText()
	{
		// TODO: refactor!
		IResource r = getResourceManager().get(getCanonicalURL(fRequestUrl.getRequestUri()));
		
		if (r == null)
		{
			return Response.status(Status.NOT_FOUND).build();
		}
		
		final Model result = r.getModel();
		
		StreamingOutput out = new StreamingOutput() 
		{
			public void write(OutputStream output) throws IOException, WebApplicationException
			{
				RDFDataMgr.write(output, result, RDFDataMgr.determineLang(null, "text/turtle", null)) ;
			}
		};
		
		return Response.ok(out)
					   .type(MediaType.TEXT_HTML)
					   .build();
	}
	
	@GET
	@Produces( Constants.CT_IMAGE_SVG_XML )
	public Response getSVG()
	{
		IResource r = getResourceManager().get(getCanonicalURL(fRequestUrl.getRequestUri()));
		
		if (r == null)
		{
			return Response.status(Status.NOT_FOUND).build();
		}

		try
		{
	        String syntax = "RDF/XML";
	        StringWriter out = new StringWriter();
	        r.getModel().write(out, syntax);
	        
	        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
	        urlParameters.add(new BasicNameValuePair("rules", "http://rhizomik.net/html/redefer/rdf2svg/showgraph.jrule"));
	        urlParameters.add(new BasicNameValuePair("format", "RDF/XML"));
	        urlParameters.add(new BasicNameValuePair("rdf", out.toString()));
	        out.close();
	
			CloseableHttpClient client = HttpClients.createDefault();
	        HttpPost httpPost = new HttpPost("http://rhizomik.net/redefer-services/render");
	        httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));
	        
	        HttpResponse response = client.execute(httpPost);
	        
	        InputStream in = response.getEntity().getContent();
	        StringWriter writer = new StringWriter();
	        IOUtils.copy(in, writer, "UTF-8");
	        return Response.ok(writer.toString()).type("image/svg+xml").build();
		}
		catch (Exception e)
		{
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
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
	@Consumes({ Constants.CT_APPLICATION_JSON_LD, Constants.CT_APPLICATION_NQUADS, Constants.CT_APPLICATION_NTRIPLES, Constants.CT_APPLICATION_RDF_JSON, Constants.CT_APPLICATION_RDFXML, Constants.CT_APPLICATION_TRIX, Constants.CT_APPLICATION_XTURTLE, Constants.CT_TEXT_N3, Constants.CT_TEXT_TRIG, Constants.CT_TEXT_TURTLE })
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
	@Consumes({ Constants.CT_APPLICATION_JSON_LD, Constants.CT_APPLICATION_NQUADS, Constants.CT_APPLICATION_NTRIPLES, Constants.CT_APPLICATION_RDF_JSON, Constants.CT_APPLICATION_RDFXML, Constants.CT_APPLICATION_TRIX, Constants.CT_APPLICATION_XTURTLE, Constants.CT_TEXT_N3, Constants.CT_TEXT_TRIG, Constants.CT_TEXT_TURTLE })
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
	
	protected abstract IResourceManager getResourceManager();

	protected abstract IContainer getRootContainer();
	
    protected String getCanonicalURL (URI url)
    {
    	 if(url.toString().endsWith("/")) 
    	 {
    		 return url.toString().substring(0, url.toString().length() - 1);	
   		 }

    	return url.toString();
    }
 

	@Context HttpServletRequest fRequest;
	@Context protected ServletContext fContext;
	@Context protected HttpHeaders fRequestHeaders;
	@Context protected UriInfo fRequestUrl;
	protected static String fPublicURI = null;
}
