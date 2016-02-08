package org.swrlapi;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.Assert;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.swrlapi.core.IRIResolver;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.test.IntegrationTestBase;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ClassAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;

/**
 * NOTE: All tests are designed for parallel execution.
 */
public class RoundTripIT extends IntegrationTestBase
{
  private static final String NAMESPACE = "http://swrlapi.org/it#";
  private static final OWLClass PERSON = Class(iri(NAMESPACE + "Person"));
  private static final OWLClass ADULT = Class(iri(NAMESPACE + "Adult"));
  private static final OWLNamedIndividual P1 = NamedIndividual(iri(NAMESPACE + "p1"));
  private static final OWLDataProperty HAS_AGE = DataProperty(iri(NAMESPACE + "hasAge"));

  @Test public void TestSWRLRuleRoundTrip()
    throws SWRLParseException, SQWRLException, IOException, OWLOntologyCreationException, OWLOntologyStorageException
  {
    OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
    RDFXMLDocumentFormat format = new RDFXMLDocumentFormat();
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    IRIResolver iriResolver = SWRLAPIFactory.createIRIResolver(NAMESPACE);
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology, iriResolver);

    File file = File.createTempFile("temp", ".owl");

    addOWLAxioms(ontology, Declaration(ADULT), Declaration(PERSON), ClassAssertion(PERSON, P1),
      DataPropertyAssertion(HAS_AGE, P1, Literal("18", XSD_INT)));

    ruleEngine.createSWRLRule("R1", "Person(?p) ^ hasAge(?p, ?age) ^ swrlb:greaterThan(?age, 17) -> Adult(?p)");

    Set<@NonNull OWLAxiom> axioms = ontology.getABoxAxioms(Imports.INCLUDED);
    Assert.assertFalse(axioms.contains(ClassAssertion(ADULT, P1)));

    ontology.saveOntology(format, org.semanticweb.owlapi.model.IRI.create(file.toURI()));

    ontology = ontologyManager.loadOntologyFromOntologyDocument(file);
    ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);

    ruleEngine.infer();

    axioms = ontology.getABoxAxioms(Imports.INCLUDED);

    Assert.assertTrue(axioms.contains(ClassAssertion(ADULT, P1)));
  }

  @Test public void TestSQWRLQueryRoundTrip()
    throws SWRLParseException, SQWRLException, IOException, OWLOntologyCreationException, OWLOntologyStorageException
  {
    OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
    RDFXMLDocumentFormat format = new RDFXMLDocumentFormat();
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    IRIResolver iriResolver = SWRLAPIFactory.createIRIResolver(NAMESPACE);
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, iriResolver);

    File file = File.createTempFile("temp", ".owl");

    addOWLAxioms(ontology, Declaration(ADULT), Declaration(PERSON), ClassAssertion(PERSON, P1),
      DataPropertyAssertion(HAS_AGE, P1, Literal("18", XSD_INT)));

    queryEngine
      .createSQWRLQuery("Q1", "Person(?p) ^ hasAge(?p, ?age) ^ swrlb:greaterThan(?age, 17) -> sqwrl:select(?p)");

    ontology.saveOntology(format, org.semanticweb.owlapi.model.IRI.create(file.toURI()));

    ontology = ontologyManager.loadOntologyFromOntologyDocument(file);
    queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("Q1");

    Assert.assertTrue(result.next());
    // TODO Fix
    //Assert.assertEquals("p1", result.getNamedIndividual("p").getShortName());
  }
}
