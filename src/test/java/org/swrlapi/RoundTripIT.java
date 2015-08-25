package org.swrlapi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
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

public class RoundTripIT extends IntegrationTestBase
{
  private static final OWLClass PERSON = Class(iri("Person"));
  private static final OWLClass MALE = Class(iri("Male"));
  private static final OWLClass ADULT = Class(iri("Adult"));
  private static final OWLNamedIndividual P1 = NamedIndividual(iri("p1"));
  private static final OWLDataProperty HAS_AGE = DataProperty(iri("hasAge"));

  private OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
  RDFXMLOntologyFormat format = new RDFXMLOntologyFormat();

  @Before public void setUp() throws OWLOntologyCreationException
  {
    DefaultPrefixManager pm = new DefaultPrefixManager();
    pm.setPrefix(":", NS);
    format.setPrefixManager(pm);
  }

  @Test public void TestRuleRoundTrip()
      throws SWRLParseException, SQWRLException, IOException, OWLOntologyCreationException, OWLOntologyStorageException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
    SWRLRuleEngine ruleEngine = queryEngine;

    File file = File.createTempFile("temp", "owl");

    addOWLAxioms(ontology, Declaration(ADULT), Declaration(PERSON), ClassAssertion(PERSON, P1),
        DataPropertyAssertion(HAS_AGE, P1, Literal("18", XSD_INT)));

    ruleEngine.createSWRLRule("R1", "Person(?p) ^ hasAge(?p, ?age) ^ swrlb:greaterThan(?age, 17) -> Adult(?p)");

    ontology.saveOntology(format, org.semanticweb.owlapi.model.IRI.create(file.toURI()));

    ontology = ontologyManager.loadOntologyFromOntologyDocument(file);
    queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
    ruleEngine = queryEngine;

    ruleEngine.infer();

    Set<OWLAxiom> axioms = ontology.getABoxAxioms(Imports.INCLUDED);

    Assert.assertTrue(axioms.contains(ClassAssertion(ADULT, P1)));
  }
}
