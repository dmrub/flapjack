package de.dfki.resc28.flapjack.vocabularies;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

/**
 * 
 * @author resc01
 *
 */
public final class LDP
{
  /** 
   * Namespace URI as Jena resource 
   */
  public static final Resource NAMESPACE = ResourceFactory.createResource(CONSTANTS.getNSURI());

  /** 
   * Classes as Jena resources 
   */
  public static final Resource BasicContainer = resource(CONSTANTS.CLASS_BasicContainer);
  public static final Resource RDFSource = resource(CONSTANTS.CLASS_RDFSource);
  public static final Resource Page = resource(CONSTANTS.CLASS_Page);
  public static final Resource Container = resource(CONSTANTS.CLASS_Container);
  public static final Resource IndirectContainer = resource(CONSTANTS.CLASS_IndirectContainer);
  public static final Resource NonRDFSource = resource(CONSTANTS.CLASS_NonRDFSource);
  public static final Resource DirectContainer = resource(CONSTANTS.CLASS_DirectContainer);
  public static final Resource PageSortCriterion = resource(CONSTANTS.CLASS_PageSortCriterion);
  public static final Resource Resource = resource(CONSTANTS.CLASS_Resource);

  /** 
   * Properties as Jena properties 
   */
  public static final Property constrainedBy = property(CONSTANTS.PROP_constrainedBy);
  public static final Property pageSortCriteria = property(CONSTANTS.PROP_pageSortCriteria);
  public static final Property hasMemberRelation = property(CONSTANTS.PROP_hasMemberRelation);
  public static final Property isMemberOfRelation = property(CONSTANTS.PROP_isMemberOfRelation);
  public static final Property pageSequence = property(CONSTANTS.PROP_pageSequence);
  public static final Property pageSortOrder = property(CONSTANTS.PROP_pageSortOrder);
  public static final Property pageSortPredicate = property(CONSTANTS.PROP_pageSortPredicate);
  public static final Property pageSortCollation = property(CONSTANTS.PROP_pageSortCollation);
  public static final Property contains = property(CONSTANTS.PROP_contains);
  public static final Property membershipResource = property(CONSTANTS.PROP_membershipResource);
  public static final Property member = property(CONSTANTS.PROP_member);
  public static final Property insertedContentRelation = property(CONSTANTS.PROP_insertedContentRelation);


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
    public static final String NS = "http://www.w3.org/ns/ldp#";

    /**
     * Local and namespace names of RDF(S) classes as strings 
     */
    public static final String CLASS_LNAME_BasicContainer = "BasicContainer";
    public static final String CLASS_BasicContainer = nsName(CLASS_LNAME_BasicContainer);
    public static final String CLASS_LNAME_RDFSource = "RDFSource";
    public static final String CLASS_RDFSource = nsName(CLASS_LNAME_RDFSource);
    public static final String CLASS_LNAME_Page = "Page";
    public static final String CLASS_Page = nsName(CLASS_LNAME_Page);
    public static final String CLASS_LNAME_Container = "Container";
    public static final String CLASS_Container = nsName(CLASS_LNAME_Container);
    public static final String CLASS_LNAME_IndirectContainer = "IndirectContainer";
    public static final String CLASS_IndirectContainer = nsName(CLASS_LNAME_IndirectContainer);
    public static final String CLASS_LNAME_NonRDFSource = "NonRDFSource";
    public static final String CLASS_NonRDFSource = nsName(CLASS_LNAME_NonRDFSource);
    public static final String CLASS_LNAME_DirectContainer = "DirectContainer";
    public static final String CLASS_DirectContainer = nsName(CLASS_LNAME_DirectContainer);
    public static final String CLASS_LNAME_PageSortCriterion = "PageSortCriterion";
    public static final String CLASS_PageSortCriterion = nsName(CLASS_LNAME_PageSortCriterion);
    public static final String CLASS_LNAME_Resource = "Resource";
    public static final String CLASS_Resource = nsName(CLASS_LNAME_Resource);

    /**
     * Local and namespace names of RDF(S) properties as strings 
     */
    public static final String PROP_LNAME_constrainedBy = "constrainedBy";
    public static final String PROP_constrainedBy = nsName(PROP_LNAME_constrainedBy);
    public static final String PROP_LNAME_pageSortCriteria = "pageSortCriteria";
    public static final String PROP_pageSortCriteria = nsName(PROP_LNAME_pageSortCriteria);
    public static final String PROP_LNAME_hasMemberRelation = "hasMemberRelation";
    public static final String PROP_hasMemberRelation = nsName(PROP_LNAME_hasMemberRelation);
    public static final String PROP_LNAME_isMemberOfRelation = "isMemberOfRelation";
    public static final String PROP_isMemberOfRelation = nsName(PROP_LNAME_isMemberOfRelation);
    public static final String PROP_LNAME_pageSequence = "pageSequence";
    public static final String PROP_pageSequence = nsName(PROP_LNAME_pageSequence);
    public static final String PROP_LNAME_pageSortOrder = "pageSortOrder";
    public static final String PROP_pageSortOrder = nsName(PROP_LNAME_pageSortOrder);
    public static final String PROP_LNAME_pageSortPredicate = "pageSortPredicate";
    public static final String PROP_pageSortPredicate = nsName(PROP_LNAME_pageSortPredicate);
    public static final String PROP_LNAME_pageSortCollation = "pageSortCollation";
    public static final String PROP_pageSortCollation = nsName(PROP_LNAME_pageSortCollation);
    public static final String PROP_LNAME_contains = "contains";
    public static final String PROP_contains = nsName(PROP_LNAME_contains);
    public static final String PROP_LNAME_membershipResource = "membershipResource";
    public static final String PROP_membershipResource = nsName(PROP_LNAME_membershipResource);
    public static final String PROP_LNAME_member = "member";
    public static final String PROP_member = nsName(PROP_LNAME_member);
    public static final String PROP_LNAME_insertedContentRelation = "insertedContentRelation";
    public static final String PROP_insertedContentRelation = nsName(PROP_LNAME_insertedContentRelation);

 
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