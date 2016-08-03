package org.swrlapi;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.test.IntegrationTestBase;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubClassOf;

/**
 * NOTE: All tests are designed for parallel execution.
 *
 * @see org.swrlapi.builtins.tbox.SWRLBuiltInLibraryImpl
 */
public class SWRLTBoxBuiltInsIT extends IntegrationTestBase
{
  private static final OWLClass C1 = Class(iri("C1"));
  private static final OWLClass C2 = Class(iri("C2"));
  private static final OWLClass C3 = Class(iri("C3"));

  @Test public void TestSWRLTBoxSCABuiltInWithUnboundNamedClasses()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, SubClassOf(C1, C2));
    addOWLAxioms(ontology, SubClassOf(C2, C3));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "tbox:sca(?csub, ?csup) -> sqwrl:select(?csub, ?csup) ^ sqwrl:orderBy(?csub, ?csup)");

//    Assert.assertEquals(result.getNumberOfRows(), 2);
//    Assert.assertTrue(result.next());
//    Assert.assertTrue(result.hasClassValue(0));
//    Assert.assertTrue(result.hasClassValue(1));
//    Assert.assertEquals(result.getClass(0).getShortName(), "C1");
//    Assert.assertEquals(result.getClass(1).getShortName(), "C2");
//    Assert.assertTrue(result.next());
//    Assert.assertTrue(result.hasClassValue(0));
//    Assert.assertTrue(result.hasClassValue(1));
//    Assert.assertEquals(result.getClass(0).getShortName(), "C2");
//    Assert.assertEquals(result.getClass(1).getShortName(), "C3");
  }


  // SUBCLASS_OF, EQUIVALENT_CLASSES, DISJOINT_CLASSES, OBJECT_PROPERTY_DOMAIN, OBJECT_PROPERTY_RANGE, FUNCTIONAL_OBJECT_PROPERTY,
  // INVERSE_FUNCTIONAL_OBJECT_PROPERTY, DATA_PROPERTY_DOMAIN, DATA_PROPERTY_RANGE, FUNCTIONAL_DATA_PROPERTY,
  // DATATYPE_DEFINITION, DISJOINT_UNION, HAS_KEY

}