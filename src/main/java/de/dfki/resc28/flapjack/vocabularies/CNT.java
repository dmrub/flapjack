package de.dfki.resc28.flapjack.vocabularies;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

/**
 * 
 * @author resc01
 *
 */
public final class CNT 
{
  /** 
   * Namespace URI as Jena resource 
   */
  public static final Resource NAMESPACE = ResourceFactory.createResource(CONSTANTS.getNSURI());

  /** 
   * Classes as Jena resources 
   */
  public static final Resource ContentAsBase64 = resource(CONSTANTS.CLASS_ContentAsBase64);
  public static final Resource ContentAsXML = resource(CONSTANTS.CLASS_ContentAsXML);
  public static final Resource Content = resource(CONSTANTS.CLASS_Content);
  public static final Resource DoctypeDecl = resource(CONSTANTS.CLASS_DoctypeDecl);
  public static final Resource ContentAsText = resource(CONSTANTS.CLASS_ContentAsText);

  /** 
   * Properties as Jena properties 
   */
  public static final Property systemId = property(CONSTANTS.PROP_systemId);
  public static final Property chars = property(CONSTANTS.PROP_chars);
  public static final Property leadingMisc = property(CONSTANTS.PROP_leadingMisc);
  public static final Property rest = property(CONSTANTS.PROP_rest);
  public static final Property standalone = property(CONSTANTS.PROP_standalone);
  public static final Property declaredEncoding = property(CONSTANTS.PROP_declaredEncoding);
  public static final Property version = property(CONSTANTS.PROP_version);
  public static final Property characterEncoding = property(CONSTANTS.PROP_characterEncoding);
  public static final Property publicId = property(CONSTANTS.PROP_publicId);
  public static final Property doctypeName = property(CONSTANTS.PROP_doctypeName);
  public static final Property internalSubset = property(CONSTANTS.PROP_internalSubset);
  public static final Property dtDecl = property(CONSTANTS.PROP_dtDecl);
  public static final Property bytes = property(CONSTANTS.PROP_bytes);


  /**
   * Returns a Jena resource for the given namespace name 
   * @param nsName  the full namespace name of a vocabulary element as a string
   * @return the vocabulary element with given namespace name as a org.apache.jena.rdf.model.Resource
   */
  private static final Resource resource(String nsName)
  {
    return ResourceFactory.createResource(nsName); 
  }

  /**
   * Returns a Jena property for the given namespace name
   * @param nsName  the full namespace name of a vocabulary element as a string
   * @return the vocabulary element with given namespace name as a org.apache.jena.rdf.model.Property
   */
  private static final Property property(String nsName)
  { 
    return ResourceFactory.createProperty(nsName);
  }

  /**
   * 
   * @author resc01
   *
   */
  public static final class CONSTANTS 
  {
    /**
     * Vocabulary namespace URI as string 
     */
    public static final String NS = "http://www.w3.org/2011/content#";

    /**
     * Local and namespace names of RDF(S) classes as strings 
     */
    public static final String CLASS_LNAME_ContentAsBase64 = "ContentAsBase64";
    public static final String CLASS_ContentAsBase64 = nsName(CLASS_LNAME_ContentAsBase64);
    public static final String CLASS_LNAME_ContentAsXML = "ContentAsXML";
    public static final String CLASS_ContentAsXML = nsName(CLASS_LNAME_ContentAsXML);
    public static final String CLASS_LNAME_Content = "Content";
    public static final String CLASS_Content = nsName(CLASS_LNAME_Content);
    public static final String CLASS_LNAME_DoctypeDecl = "DoctypeDecl";
    public static final String CLASS_DoctypeDecl = nsName(CLASS_LNAME_DoctypeDecl);
    public static final String CLASS_LNAME_ContentAsText = "ContentAsText";
    public static final String CLASS_ContentAsText = nsName(CLASS_LNAME_ContentAsText);

    /**
     * Local and namespace names of RDF(S) properties as strings 
     */
    public static final String PROP_LNAME_systemId = "systemId";
    public static final String PROP_systemId = nsName(PROP_LNAME_systemId);
    public static final String PROP_LNAME_chars = "chars";
    public static final String PROP_chars = nsName(PROP_LNAME_chars);
    public static final String PROP_LNAME_leadingMisc = "leadingMisc";
    public static final String PROP_leadingMisc = nsName(PROP_LNAME_leadingMisc);
    public static final String PROP_LNAME_rest = "rest";
    public static final String PROP_rest = nsName(PROP_LNAME_rest);
    public static final String PROP_LNAME_standalone = "standalone";
    public static final String PROP_standalone = nsName(PROP_LNAME_standalone);
    public static final String PROP_LNAME_declaredEncoding = "declaredEncoding";
    public static final String PROP_declaredEncoding = nsName(PROP_LNAME_declaredEncoding);
    public static final String PROP_LNAME_version = "version";
    public static final String PROP_version = nsName(PROP_LNAME_version);
    public static final String PROP_LNAME_characterEncoding = "characterEncoding";
    public static final String PROP_characterEncoding = nsName(PROP_LNAME_characterEncoding);
    public static final String PROP_LNAME_publicId = "publicId";
    public static final String PROP_publicId = nsName(PROP_LNAME_publicId);
    public static final String PROP_LNAME_doctypeName = "doctypeName";
    public static final String PROP_doctypeName = nsName(PROP_LNAME_doctypeName);
    public static final String PROP_LNAME_internalSubset = "internalSubset";
    public static final String PROP_internalSubset = nsName(PROP_LNAME_internalSubset);
    public static final String PROP_LNAME_dtDecl = "dtDecl";
    public static final String PROP_dtDecl = nsName(PROP_LNAME_dtDecl);
    public static final String PROP_LNAME_bytes = "bytes";
    public static final String PROP_bytes = nsName(PROP_LNAME_bytes);

 
    /**
     * Returns the namespace of the vocabulary as a string
     * @return the namespace of the vocabulary as a string
     */
    private static String getNSURI()
    {
      return NS;
    }

    /**
     * Returns the full namespace name of a vocabulary element as a string
     * @param localName  the local name of a vocabulary element as a string
     * @return the full namespace name of a vocabulary element as a string
     */
    private static String nsName(String localName) 
    {
      return NS + localName;
    }

    /**
     * Returns the local name of a vocabulary element as a string
     * @param nsName  the full namespace name of a vocabulary element
     * @return the local name of a vocabulary element as a string
     */
    public static String localName(String nsName)
    {
      return nsName.replace(NS, "");
    }
  }
}