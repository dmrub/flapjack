/*
 * This file is part of flapjack. It is subject to the license terms in
 * the LICENSE file found in the top-level directory of this distribution.
 * You may not use this file except in compliance with the License.
 */
package de.dfki.resc28.flapjack.services;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;

import de.dfki.resc28.flapjack.resources.IContainer;
import de.dfki.resc28.flapjack.resources.IResource;
import de.dfki.resc28.flapjack.resources.IResourceManager;
import de.dfki.resc28.igraphstore.Constants;
import de.dfki.resc28.igraphstore.util.ProxyConfigurator;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Variant;

/**
 * @author resc01
 *
 */
public abstract class BaseService {

    private String rhizomikUri;
    private String rhizomikShowGraphRuleUri;
    private String rhizomikRenderUri;

    public static final List<Variant> DEFAULT_RDF_MEDIA_TYPE
            = Variant.mediaTypes(
                    MediaType.valueOf(Constants.CT_TEXT_TURTLE)
            ).build();
    public static final List<Variant> RDF_MEDIA_TYPES
            = Variant.mediaTypes(
                    MediaType.valueOf(Constants.CT_APPLICATION_JSON_LD),
                    MediaType.valueOf(Constants.CT_APPLICATION_NQUADS),
                    MediaType.valueOf(Constants.CT_APPLICATION_NTRIPLES),
                    MediaType.valueOf(Constants.CT_APPLICATION_RDF_JSON),
                    MediaType.valueOf(Constants.CT_APPLICATION_RDFXML),
                    MediaType.valueOf(Constants.CT_APPLICATION_TRIX),
                    MediaType.valueOf(Constants.CT_APPLICATION_XTURTLE),
                    MediaType.valueOf(Constants.CT_TEXT_N3),
                    MediaType.valueOf(Constants.CT_TEXT_TRIG),
                    MediaType.valueOf(Constants.CT_TEXT_TURTLE)
            ).build();

    public BaseService() {
        this(System.getProperty("rhizomikURI", "http://rhizomik.net"));
    }

    public BaseService(final String rhizomikUri) {
        this(rhizomikUri + "/redefer-services/render", rhizomikUri + "/html/redefer/rdf2svg/showgraph.jrule");
    }

    public BaseService(String rhizomikRenderUri, String rhizomikShowGraphRuleUri) {
        this.rhizomikRenderUri = rhizomikRenderUri;
        this.rhizomikShowGraphRuleUri = rhizomikShowGraphRuleUri;
    }

    protected MediaType negotiateRDFMediaType() {
        // Check default RDF type
        Variant bestVariant = fRequest.selectVariant(DEFAULT_RDF_MEDIA_TYPE);
        // Check all supported RDF types
        if (bestVariant == null) {
            bestVariant = fRequest.selectVariant(RDF_MEDIA_TYPES);
            if (bestVariant == null) {
                /* Based on results, the optimal response variant can not be
                   determined from the list given.  */
                return null;
            }
        }
        return bestVariant.getMediaType();
    }

    @GET
    @Produces({Constants.CT_APPLICATION_JSON_LD, Constants.CT_APPLICATION_NQUADS,
        Constants.CT_APPLICATION_NTRIPLES, Constants.CT_APPLICATION_RDF_JSON,
        Constants.CT_APPLICATION_RDFXML, Constants.CT_APPLICATION_TRIX,
        Constants.CT_APPLICATION_XTURTLE, Constants.CT_TEXT_N3,
        Constants.CT_TEXT_TRIG, Constants.CT_TEXT_TURTLE})
    public Response get(@HeaderParam(HttpHeaders.ACCEPT) @DefaultValue(Constants.CT_TEXT_TURTLE) String acceptType) {
        System.err.format("BaseService.get: request URI: %s%n", fRequestUrl.getRequestUri());
        IResource r = getResourceManager().get(getResourceURI(fRequestUrl));

        if (r == null) {
            return Response.status(Status.NOT_FOUND).header(HttpHeaders.VARY, HttpHeaders.ACCEPT).build();
        } else if (!(r instanceof IResource)) {
            return Response.status(Status.BAD_REQUEST).header(HttpHeaders.VARY, HttpHeaders.ACCEPT).build();
        } else if (!(r.getAllowedMethods().contains(HttpMethod.GET))) {
            return Response.status(Status.METHOD_NOT_ALLOWED).header(HttpHeaders.VARY, HttpHeaders.ACCEPT).build();
        }

        final MediaType responseMediaType = negotiateRDFMediaType();
        if (responseMediaType == null) {
            return Response.notAcceptable(RDF_MEDIA_TYPES).build();
        }
        return r.read(responseMediaType.getType() + "/" + responseMediaType.getSubtype());
    }

//	@GET
//	@Produces(MediaType.TEXT_HTML)
//	public Response getText()
//	{
//		// TODO: refactor!
//		IResource r = getResourceManager().get(getResourceURI(fRequestUrl));
//
//		if (r == null)
//		{
//			return Response.status(Status.NOT_FOUND).build();
//		}
//
//		final Model result = r.getModel();
//
//		StreamingOutput out = new StreamingOutput()
//		{
//			public void write(OutputStream output) throws IOException, WebApplicationException
//			{
//				RDFDataMgr.write(output, result, RDFDataMgr.determineLang(null, "text/turtle", null)) ;
//			}
//		};
//
//		return Response.ok(out)
//					   .type(MediaType.TEXT_HTML)
//					   .build();
//	}
    @GET
    @Produces({MediaType.TEXT_HTML, Constants.CT_IMAGE_SVG_XML})
    public Response getSVG() {
        IResource r = getResourceManager().get(getResourceURI(fRequestUrl));

        if (r == null) {
            return Response.status(Status.NOT_FOUND).header(HttpHeaders.VARY, HttpHeaders.ACCEPT).build();
        }

        try {
            String syntax = "RDF/XML";
            List<NameValuePair> urlParameters;
            try (StringWriter out = new StringWriter()) {
                r.getModel().write(out, syntax);
                urlParameters = new ArrayList<>();
                urlParameters.add(new BasicNameValuePair("rules", rhizomikShowGraphRuleUri));
                urlParameters.add(new BasicNameValuePair("format", "RDF/XML"));
                urlParameters.add(new BasicNameValuePair("rdf", out.toString()));
            }

            CloseableHttpClient client = ProxyConfigurator.createHttpClient();
            HttpPost httpPost = new HttpPost(rhizomikRenderUri);
            httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));

            HttpResponse response = client.execute(httpPost);

            InputStream in = response.getEntity().getContent();
            StringWriter writer = new StringWriter();
            IOUtils.copy(in, writer, "UTF-8");
            return Response.ok(writer.toString()).header(HttpHeaders.VARY, HttpHeaders.ACCEPT).type("image/svg+xml").build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    public Response delete() {
        IResource r = getResourceManager().get(getResourceURI(fRequestUrl));

        if (r == null) {
            return Response.status(Status.NOT_FOUND).build();
        } else if (!(r instanceof IResource)) {
            return Response.status(Status.BAD_REQUEST).build();
        } else if (!(r.getAllowedMethods().contains(HttpMethod.DELETE))) {
            return Response.status(Status.METHOD_NOT_ALLOWED).build();
        }

        return r.delete();
    }

    @PUT
    @Consumes({Constants.CT_APPLICATION_JSON_LD, Constants.CT_APPLICATION_NQUADS, Constants.CT_APPLICATION_NTRIPLES, Constants.CT_APPLICATION_RDF_JSON, Constants.CT_APPLICATION_RDFXML, Constants.CT_APPLICATION_TRIX, Constants.CT_APPLICATION_XTURTLE, Constants.CT_TEXT_N3, Constants.CT_TEXT_TRIG, Constants.CT_TEXT_TURTLE})
    public Response put(InputStream content, @HeaderParam(HttpHeaders.CONTENT_TYPE) String contentType) {
        IResource r = getResourceManager().get(getResourceURI(fRequestUrl));

        if (r == null) {
            return Response.status(Status.NOT_FOUND).build();
        } else if (!(r instanceof IContainer)) {
            return Response.status(Status.BAD_REQUEST).build();
        } else if (!(r.getAllowedMethods().contains("PUT"))) {
            return Response.status(Status.METHOD_NOT_ALLOWED).build();
        }

        return r.update(content, contentType);
    }

    @POST
    @Consumes({Constants.CT_APPLICATION_JSON_LD, Constants.CT_APPLICATION_NQUADS, Constants.CT_APPLICATION_NTRIPLES, Constants.CT_APPLICATION_RDF_JSON, Constants.CT_APPLICATION_RDFXML, Constants.CT_APPLICATION_TRIX, Constants.CT_APPLICATION_XTURTLE, Constants.CT_TEXT_N3, Constants.CT_TEXT_TRIG, Constants.CT_TEXT_TURTLE})
    public Response post(InputStream content, @HeaderParam(HttpHeaders.CONTENT_TYPE) String contentType) {
        IResource r = getResourceManager().get(getResourceURI(fRequestUrl));

        if (r == null) {
            return Response.status(Status.NOT_FOUND).build();
        } else if (!(r instanceof IContainer)) {
            return Response.status(Status.BAD_REQUEST).build();
        } else if (!(r.getAllowedMethods().contains("POST"))) {
            return Response.status(Status.METHOD_NOT_ALLOWED).build();
        }

        return ((IContainer) r).create(content, contentType);

    }

    protected abstract IResourceManager getResourceManager();

    protected abstract IContainer getRootContainer();

    /**
     * Maps UriInfo to resource URI
     *
     * @param uriInfo UriInfo structure of the HTTP request
     * @return resource URI as string
     */
    protected String getResourceURI(UriInfo uriInfo) {
        return getCanonicalURL(uriInfo.getRequestUri());
    }

    protected String getCanonicalURL(String url) {
        if (url.endsWith("/")) {
            return url.substring(0, url.length() - 1);
        }
        return url;
    }

    protected String getCanonicalURL(URI url) {
        return getCanonicalURL(url.toString());
    }

    @Context
    protected Request fRequest;

    @Context
    HttpServletRequest fServletRequest;
    @Context
    protected ServletContext fServletContext;
    @Context
    protected HttpHeaders fRequestHeaders;
    @Context
    protected UriInfo fRequestUrl;
    protected static String fPublicURI = null;
}
