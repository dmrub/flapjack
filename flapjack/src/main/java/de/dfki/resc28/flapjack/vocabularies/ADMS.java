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
public final class ADMS 
{
  /** 
   * Namespace URI as Jena resource 
   */
  public static final Resource NAMESPACE = ResourceFactory.createResource(CONSTANTS.getNSURI());

  /** 
   * Classes as Jena resources 
   */
  public static final Resource AssetRepository = resource(CONSTANTS.CLASS_AssetRepository);
  public static final Resource Identifier = resource(CONSTANTS.CLASS_Identifier);
  public static final Resource Asset = resource(CONSTANTS.CLASS_Asset);
  public static final Resource AssetDistribution = resource(CONSTANTS.CLASS_AssetDistribution);

  /** 
   * Properties as Jena properties 
   */
  public static final Property identifier = property(CONSTANTS.PROP_identifier);
  public static final Property schemeAgency = property(CONSTANTS.PROP_schemeAgency);
  public static final Property translation = property(CONSTANTS.PROP_translation);
  public static final Property sample = property(CONSTANTS.PROP_sample);
  public static final Property supportedSchema = property(CONSTANTS.PROP_supportedSchema);
  public static final Property last = property(CONSTANTS.PROP_last);
  public static final Property prev = property(CONSTANTS.PROP_prev);
  public static final Property interoperabilityLevel = property(CONSTANTS.PROP_interoperabilityLevel);
  public static final Property representationTechnique = property(CONSTANTS.PROP_representationTechnique);
  public static final Property next = property(CONSTANTS.PROP_next);
  public static final Property status = property(CONSTANTS.PROP_status);
  public static final Property versionNotes = property(CONSTANTS.PROP_versionNotes);
  public static final Property includedAsset = property(CONSTANTS.PROP_includedAsset);


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
    public static final String NS = "http://www.w3.org/ns/adms#";

    /**
     * Local and namespace names of RDF(S) classes as strings 
     */
    public static final String CLASS_LNAME_AssetRepository = "AssetRepository";
    public static final String CLASS_AssetRepository = nsName(CLASS_LNAME_AssetRepository);
    public static final String CLASS_LNAME_Identifier = "Identifier";
    public static final String CLASS_Identifier = nsName(CLASS_LNAME_Identifier);
    public static final String CLASS_LNAME_Asset = "Asset";
    public static final String CLASS_Asset = nsName(CLASS_LNAME_Asset);
    public static final String CLASS_LNAME_AssetDistribution = "AssetDistribution";
    public static final String CLASS_AssetDistribution = nsName(CLASS_LNAME_AssetDistribution);

    /**
     * Local and namespace names of RDF(S) properties as strings 
     */
    public static final String PROP_LNAME_identifier = "identifier";
    public static final String PROP_identifier = nsName(PROP_LNAME_identifier);
    public static final String PROP_LNAME_schemeAgency = "schemeAgency";
    public static final String PROP_schemeAgency = nsName(PROP_LNAME_schemeAgency);
    public static final String PROP_LNAME_translation = "translation";
    public static final String PROP_translation = nsName(PROP_LNAME_translation);
    public static final String PROP_LNAME_sample = "sample";
    public static final String PROP_sample = nsName(PROP_LNAME_sample);
    public static final String PROP_LNAME_supportedSchema = "supportedSchema";
    public static final String PROP_supportedSchema = nsName(PROP_LNAME_supportedSchema);
    public static final String PROP_LNAME_last = "last";
    public static final String PROP_last = nsName(PROP_LNAME_last);
    public static final String PROP_LNAME_prev = "prev";
    public static final String PROP_prev = nsName(PROP_LNAME_prev);
    public static final String PROP_LNAME_interoperabilityLevel = "interoperabilityLevel";
    public static final String PROP_interoperabilityLevel = nsName(PROP_LNAME_interoperabilityLevel);
    public static final String PROP_LNAME_representationTechnique = "representationTechnique";
    public static final String PROP_representationTechnique = nsName(PROP_LNAME_representationTechnique);
    public static final String PROP_LNAME_next = "next";
    public static final String PROP_next = nsName(PROP_LNAME_next);
    public static final String PROP_LNAME_status = "status";
    public static final String PROP_status = nsName(PROP_LNAME_status);
    public static final String PROP_LNAME_versionNotes = "versionNotes";
    public static final String PROP_versionNotes = nsName(PROP_LNAME_versionNotes);
    public static final String PROP_LNAME_includedAsset = "includedAsset";
    public static final String PROP_includedAsset = nsName(PROP_LNAME_includedAsset);

 
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