package org.swrlapi;

import org.junit.Assert;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.test.IntegrationTestBase;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ClassAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectUnionOf;

/**
 * NOTE: All tests are designed for parallel execution.
 *
 * @see org.swrlapi.builtins.abox.SWRLBuiltInLibraryImpl
 */
public class SWRLABoxBuiltInsIT extends IntegrationTestBase
{
  private static final OWLClass C1 = Class(iri("C1"));
  private static final OWLClass C2 = Class(iri("C2"));
  private static final OWLClass C3 = Class(iri("C3"));
  private static final OWLNamedIndividual P1 = NamedIndividual(iri("p1"));
  private static final OWLNamedIndividual P2 = NamedIndividual(iri("p2"));
  private static final OWLNamedIndividual P3 = NamedIndividual(iri("p3"));

  @Test public void TestSWRLABoxCAABuiltInWithUnboundNamedClassAndNamedIndividual()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(C1, P1));
    addOWLAxioms(ontology, ClassAssertion(C2, P2));
    addOWLAxioms(ontology, ClassAssertion(C3, P3));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "abox:caa(?c, ?i) -> sqwrl:select(?c, ?i) ^ sqwrl:orderBy(?c, ?i)");

    Assert.assertEquals(result.getNumberOfRows(), 3);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertEquals(result.getClass(0).getShortName(), "C1");
    Assert.assertTrue(result.hasNamedIndividualValue(1));
    Assert.assertEquals(result.getNamedIndividual(1).getShortName(), "p1");
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertEquals(result.getClass(0).getShortName(), "C2");
    Assert.assertTrue(result.hasNamedIndividualValue(1));
    Assert.assertEquals(result.getNamedIndividual(1).getShortName(), "p2");
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertEquals(result.getClass(0).getShortName(), "C3");
    Assert.assertTrue(result.hasNamedIndividualValue(1));
    Assert.assertEquals(result.getNamedIndividual(1).getShortName(), "p3");
  }

  @Test public void TestSWRLABoxCAABuiltInWithBoundNamedClassAndNamedIndividual()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(C1, P1));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:caa(C1, p1) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLABoxCAABuiltInWithBoundNamedClassAndUnboundNamedIndividual()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(C1, P1));
    addOWLAxioms(ontology, ClassAssertion(C1, P2));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:caa(C1, ?i) -> sqwrl:select(?i) ^ sqwrl:orderBy(?i)");

    Assert.assertEquals(result.getNumberOfRows(), 2);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertEquals(result.getNamedIndividual(0).getShortName(), "p1");
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertEquals(result.getNamedIndividual(0).getShortName(), "p2");
  }

  @Test public void TestSWRLABoxCAABuiltInWithUnboundNamedClassAndBoundNamedIndividual()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(C1, P1));
    addOWLAxioms(ontology, ClassAssertion(C2, P1));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:caa(?c, p1) -> sqwrl:select(?c) ^ sqwrl:orderBy(?c)");

    Assert.assertEquals(result.getNumberOfRows(), 2);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertEquals(result.getClass(0).getShortName(), "C1");
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertEquals(result.getClass(0).getShortName(), "C2");
  }

  @Test public void TestSWRLABoxCAABuiltInWithUnboundClassExpressionAndUnBoundNamedIndividual()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(ObjectUnionOf(C1, C2), P1));
    addOWLAxioms(ontology, ClassAssertion(ObjectUnionOf(C2, C3), P2));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "abox:caa(?c, ?i) -> sqwrl:select(?c, ?i) ^ sqwrl:orderBy(?c, ?i)");

    Assert.assertEquals(result.getNumberOfRows(), 2);
  }

}