/**
 * 
 */
package de.dfki.resc28.flapjack.resources;

import java.io.InputStream;

import javax.ws.rs.core.Response;

/**
 * @author resc01
 *
 */
public interface IContainer extends IResource
{
	public abstract Response create(InputStream input, final String contentType);
//	public abstract void query(OutputStream outStream, String queryString, String resultsFormat);
//	public abstract Response postNonRDF(InputStream content, String stripCharset, String user, String slug);
}
