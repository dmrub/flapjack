package de.dfki.resc28.flapjack.resources;

import org.apache.jena.rdf.model.Model;

import de.dfki.resc28.igraphstore.IGraphStore;



public abstract class ResourceManager implements IResourceManager
{
	//================================================================================
    // Constructors
    //================================================================================	

	public ResourceManager(IGraphStore graphStore)
	{
		this.fGraphStore = graphStore;

	}

	//================================================================================
    //  Methods
    //================================================================================
	
	public void delete(IResource r)
	{
		fGraphStore.deleteNamedGraph(r.getURI());
	}

	public void put(IResource r, boolean overwrite) 
	{
		if (!fGraphStore.containsNamedGraph(r.getURI()) || overwrite)
		{
			fGraphStore.replaceNamedGraph(r.getURI(), (Model) r.getModel());
		}
	}
	
	//================================================================================
    // Member variables
    //================================================================================
	
	protected IGraphStore fGraphStore;
}
