/**
 * 
 */
package de.dfki.resc28.flapjack.resources;




/**
 * @author resc01
 *
 */
public interface IResourceManager 
{
	public void put(IResource ldpr, boolean overwrite);
	
	public IResource get(String resourceURI);
}
