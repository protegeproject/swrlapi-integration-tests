package org.swrlapi;

import org.junit.Assert;
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
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointClasses;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentClasses;
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

  @Test public void TestSWRLTBoxSCABuiltInWithAllUnboundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, SubClassOf(C1, C2));
    addOWLAxioms(ontology, SubClassOf(C2, C3));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "tbox:sca(?csub, ?csup) -> sqwrl:select(?csub, ?csup) ^ sqwrl:orderBy(?csub, ?csup)");

    Assert.assertEquals(result.getNumberOfRows(), 2);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(0).getShortName(), "C1");
    Assert.assertEquals(result.getClass(1).getShortName(), "C2");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(0).getShortName(), "C2");
    Assert.assertEquals(result.getClass(1).getShortName(), "C3");
  }

  @Test public void TestSWRLTBoxSCABuiltInWithAllBoundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, SubClassOf(C1, C2));
    addOWLAxioms(ontology, SubClassOf(C2, C3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:sca(C1, C2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  // TODO TestSWRLTBoxSCABuiltInWithBound1stUnbound2ndArguments
  // TODO TestSWRLTBoxSCABuiltInWithUnbound1stBound2ndArguments

  @Test public void TestSWRLTBoxECABuiltInWithAllUnboundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, EquivalentClasses(C1, C2));
    addOWLAxioms(ontology, EquivalentClasses(C2, C3));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "tbox:eca(?c1, ?c2) -> sqwrl:select(?c1, ?c2) ^ sqwrl:orderBy(?c1, ?c2)");

    Assert.assertEquals(result.getNumberOfRows(), 2);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(0).getShortName(), "C1");
    Assert.assertEquals(result.getClass(1).getShortName(), "C2");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(0).getShortName(), "C2");
    Assert.assertEquals(result.getClass(1).getShortName(), "C3");
  }

  @Test public void TestSWRLTBoxECABuiltInWithAllBoundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, EquivalentClasses(C1, C2));
    addOWLAxioms(ontology, EquivalentClasses(C2, C3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:eca(C1, C2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  // TODO TestSWRLTBoxECABuiltInWithBound1stUnbound2ndArguments
  // TODO TestSWRLTBoxECABuiltInWithUnbound1stBound2ndArguments

  @Test public void TestSWRLTBoxDCABuiltInWithAllUnboundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DisjointClasses(C1, C2));
    addOWLAxioms(ontology, DisjointClasses(C2, C3));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "tbox:dca(?c1, ?c2) -> sqwrl:select(?c1, ?c2) ^ sqwrl:orderBy(?c1, ?c2)");

    Assert.assertEquals(result.getNumberOfRows(), 2);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(0).getShortName(), "C1");
    Assert.assertEquals(result.getClass(1).getShortName(), "C2");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(0).getShortName(), "C2");
    Assert.assertEquals(result.getClass(1).getShortName(), "C3");
  }

  @Test public void TestSWRLTBoxDCABuiltInWithAllBoundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DisjointClasses(C1, C2));
    addOWLAxioms(ontology, DisjointClasses(C2, C3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:dca(C1, C2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  // TODO TestSWRLTBoxDCABuiltInWithBound1stUnbound2ndArguments
  // TODO TestSWRLTBoxDCABuiltInWithUnbound1stBound2ndArguments

  // FUNCTIONAL_OBJECT_PROPERTY, INVERSE_FUNCTIONAL_OBJECT_PROPERTY, FUNCTIONAL_DATA_PROPERTY,
  // OBJECT_PROPERTY_DOMAIN, OBJECT_PROPERTY_RANGE,
  // DATA_PROPERTY_DOMAIN, DATA_PROPERTY_RANGE,
  // DATATYPE_DEFINITION,
  // DISJOINT_UNION,
  // HAS_KEY

}