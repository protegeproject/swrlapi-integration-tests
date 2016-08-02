package org.swrlapi;

import org.junit.Assert;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.test.IntegrationTestBase;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ClassAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.OWLThing;

/**
 * NOTE: All tests are designed for parallel execution.
 *
 * @see org.swrlapi.builtins.swrlx.SWRLBuiltInLibraryImpl
 */
public class SWRLExtensionsBuiltInsIT extends IntegrationTestBase
{
  private static final OWLNamedIndividual P1 = NamedIndividual(iri("p1"));
  private static final OWLNamedIndividual P2 = NamedIndividual(iri("p2"));
  private static final OWLNamedIndividual P3 = NamedIndividual(iri("p3"));

  @Test public void TestSWRLExtensionsMakeOWLThingBuiltIn()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(OWLThing(), P1));
    addOWLAxioms(ontology, ClassAssertion(OWLThing(), P2));
    addOWLAxioms(ontology, ClassAssertion(OWLThing(), P3));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "owl:Thing(?x) ^ swrlx:makeOWLThing(?t, ?x) -> sqwrl:select(?t)");

    Assert.assertEquals(result.getNumberOfRows(), 3);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
  }

  @Test public void TestSWRLExtensionsMakeOWLClassBuiltIn()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(OWLThing(), P1));
    addOWLAxioms(ontology, ClassAssertion(OWLThing(), P2));
    addOWLAxioms(ontology, ClassAssertion(OWLThing(), P3));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "owl:Thing(?x) ^ swrlx:makeOWLClass(?t, ?x) -> sqwrl:select(?t)");

    Assert.assertEquals(result.getNumberOfRows(), 3);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
  }
}