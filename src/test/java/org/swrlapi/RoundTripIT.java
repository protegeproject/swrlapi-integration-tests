package org.swrlapi;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.test.IntegrationTestBase;

import java.io.IOException;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;

public class RoundTripIT extends IntegrationTestBase
{
  private static final OWLClass PERSON = Class(IRI(":Person"));
  private static final OWLClass MALE = Class(IRI(":Male"));
  private static final OWLClass ADULT = Class(IRI(":Adult"));
  private static final OWLNamedIndividual P1 = NamedIndividual(IRI(":p1"));
  private static final OWLDataProperty HAS_AGE = DataProperty(IRI(":hasAge"));

  private SWRLRuleEngine ruleEngine;
  private SQWRLQueryEngine queryEngine;
  private OWLOntology ontology;

  @Before public void setUp() throws OWLOntologyCreationException
  {
    ontology = OWLManager.createOWLOntologyManager().createOntology();
    queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
    ruleEngine = queryEngine;
  }

  @Test public void TestRuleRoundTrip()
      throws SWRLParseException, SQWRLException, IOException, OWLOntologyCreationException, OWLOntologyStorageException
  {
    // TODO Need to figure out proper namespace and prefix handling.
    //    File file = File.createTempFile("temp", "owl");
    //
    //    addOWLAxioms(ontology, Declaration(ADULT), Declaration(PERSON), ClassAssertion(PERSON, P1),
    //        DataPropertyAssertion(HAS_AGE, P1, Literal("18", XSD_INT)));
    //
    //    ruleEngine.createSWRLRule("R1", "Person(?p) ^ hasAge(?p, ?age) ^ swrlb:greaterThan(?age, 17) -> Adult(?p)");
    //
    //    ontology.saveOntology(IRI.create(file.toURI()));
    //
    //    ontology = ontologyManager.loadOntologyFromOntologyDocument(file);
    //    queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
    //    ruleEngine = queryEngine;
    //
    //    ruleEngine.infer();
    //
    //    Set<OWLAxiom> axioms = ontology.getABoxAxioms(Imports.INCLUDED);
    //
    //    Assert.assertTrue(axioms.contains(ClassAssertion(ADULT, P1)));
    //
  }
}