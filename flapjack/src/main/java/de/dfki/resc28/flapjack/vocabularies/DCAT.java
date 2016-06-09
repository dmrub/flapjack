/**
 * 
 */
package de.dfki.resc28.flapjack.vocabularies;

/**
 * @author resc01
 *
 */

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

/**
 * 
 * @author resc01
 *
 */
public final class DCAT 
{
  /** 
   * Namespace URI as Jena resource 
   */
  public static final Resource NAMESPACE = ResourceFactory.createResource(CONSTANTS.getNSURI());

  /** 
   * Classes as Jena resources 
   */
  public static final Resource Dataset = resource(CONSTANTS.CLASS_Dataset);
  public static final Resource Download = resource(CONSTANTS.CLASS_Download);
  public static final Resource Catalog = resource(CONSTANTS.CLASS_Catalog);
  public static final Resource CatalogRecord = resource(CONSTANTS.CLASS_CatalogRecord);
  public static final Resource Feed = resource(CONSTANTS.CLASS_Feed);
  public static final Resource WebService = resource(CONSTANTS.CLASS_WebService);
  public static final Resource Distribution = resource(CONSTANTS.CLASS_Distribution);

  /** 
   * Properties as Jena properties 
   */
  public static final Property size = property(CONSTANTS.PROP_size);
  public static final Property byteSize = property(CONSTANTS.PROP_byteSize);
  public static final Property dataset = property(CONSTANTS.PROP_dataset);
  public static final Property accessURL = property(CONSTANTS.PROP_accessURL);
  public static final Property dataQuality = property(CONSTANTS.PROP_dataQuality);
  public static final Property contactPoint = property(CONSTANTS.PROP_contactPoint);
  public static final Property dataDictionary = property(CONSTANTS.PROP_dataDictionary);
  public static final Property bytes = property(CONSTANTS.PROP_bytes);
  public static final Property themeTaxonomy = property(CONSTANTS.PROP_themeTaxonomy);
  public static final Property mediaType = property(CONSTANTS.PROP_mediaType);
  public static final Property keyword = property(CONSTANTS.PROP_keyword);
  public static final Property downloadURL = property(CONSTANTS.PROP_downloadURL);
  public static final Property record = property(CONSTANTS.PROP_record);
  public static final Property granularity = property(CONSTANTS.PROP_granularity);
  public static final Property landingPage = property(CONSTANTS.PROP_landingPage);
  public static final Property theme = property(CONSTANTS.PROP_theme);
  public static final Property distribution = property(CONSTANTS.PROP_distribution);


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
    public static final String NS = "http://www.w3.org/ns/dcat#";

    /**
     * Local and namespace names of RDF(S) classes as strings 
     */
    public static final String CLASS_LNAME_Dataset = "Dataset";
    public static final String CLASS_Dataset = nsName(CLASS_LNAME_Dataset);
    public static final String CLASS_LNAME_Download = "Download";
    public static final String CLASS_Download = nsName(CLASS_LNAME_Download);
    public static final String CLASS_LNAME_Catalog = "Catalog";
    public static final String CLASS_Catalog = nsName(CLASS_LNAME_Catalog);
    public static final String CLASS_LNAME_CatalogRecord = "CatalogRecord";
    public static final String CLASS_CatalogRecord = nsName(CLASS_LNAME_CatalogRecord);
    public static final String CLASS_LNAME_Feed = "Feed";
    public static final String CLASS_Feed = nsName(CLASS_LNAME_Feed);
    public static final String CLASS_LNAME_WebService = "WebService";
    public static final String CLASS_WebService = nsName(CLASS_LNAME_WebService);
    public static final String CLASS_LNAME_Distribution = "Distribution";
    public static final String CLASS_Distribution = nsName(CLASS_LNAME_Distribution);

    /**
     * Local and namespace names of RDF(S) properties as strings 
     */
    public static final String PROP_LNAME_size = "size";
    public static final String PROP_size = nsName(PROP_LNAME_size);
    public static final String PROP_LNAME_byteSize = "byteSize";
    public static final String PROP_byteSize = nsName(PROP_LNAME_byteSize);
    public static final String PROP_LNAME_dataset = "dataset";
    public static final String PROP_dataset = nsName(PROP_LNAME_dataset);
    public static final String PROP_LNAME_accessURL = "accessURL";
    public static final String PROP_accessURL = nsName(PROP_LNAME_accessURL);
    public static final String PROP_LNAME_dataQuality = "dataQuality";
    public static final String PROP_dataQuality = nsName(PROP_LNAME_dataQuality);
    public static final String PROP_LNAME_contactPoint = "contactPoint";
    public static final String PROP_contactPoint = nsName(PROP_LNAME_contactPoint);
    public static final String PROP_LNAME_dataDictionary = "dataDictionary";
    public static final String PROP_dataDictionary = nsName(PROP_LNAME_dataDictionary);
    public static final String PROP_LNAME_bytes = "bytes";
    public static final String PROP_bytes = nsName(PROP_LNAME_bytes);
    public static final String PROP_LNAME_themeTaxonomy = "themeTaxonomy";
    public static final String PROP_themeTaxonomy = nsName(PROP_LNAME_themeTaxonomy);
    public static final String PROP_LNAME_mediaType = "mediaType";
    public static final String PROP_mediaType = nsName(PROP_LNAME_mediaType);
    public static final String PROP_LNAME_keyword = "keyword";
    public static final String PROP_keyword = nsName(PROP_LNAME_keyword);
    public static final String PROP_LNAME_downloadURL = "downloadURL";
    public static final String PROP_downloadURL = nsName(PROP_LNAME_downloadURL);
    public static final String PROP_LNAME_record = "record";
    public static final String PROP_record = nsName(PROP_LNAME_record);
    public static final String PROP_LNAME_granularity = "granularity";
    public static final String PROP_granularity = nsName(PROP_LNAME_granularity);
    public static final String PROP_LNAME_landingPage = "landingPage";
    public static final String PROP_landingPage = nsName(PROP_LNAME_landingPage);
    public static final String PROP_LNAME_theme = "theme";
    public static final String PROP_theme = nsName(PROP_LNAME_theme);
    public static final String PROP_LNAME_distribution = "distribution";
    public static final String PROP_distribution = nsName(PROP_LNAME_distribution);

 
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
