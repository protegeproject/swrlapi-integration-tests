package org.swrlapi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.test.IntegrationTestBase;

import java.util.Set;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ClassAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;

public class PublicAPIIT extends IntegrationTestBase
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
    queryEngine = createSQWRLQueryEngine();
    ruleEngine = queryEngine;
    ontology = queryEngine.getOWLOntology();
  }

  @Test public void TestSWRLRuleEngineInfer() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, Declaration(ADULT), Declaration(PERSON), ClassAssertion(PERSON, P1),
        DataPropertyAssertion(HAS_AGE, P1, Literal("18", XSD_INT)));

    ruleEngine.createSWRLRule("R1", "Person(?p) ^ hasAge(?p, ?age) ^ swrlb:greaterThan(?age, 17) -> Adult(?p)");

    ruleEngine.infer();

    Set<OWLAxiom> axioms = ontology.getABoxAxioms(Imports.INCLUDED);

    Assert.assertTrue(axioms.contains(ClassAssertion(ADULT, P1)));
  }

  @Test public void TestSQWRLQuery() throws SQWRLException, SWRLParseException
  {
    addOWLAxioms(ontology, ClassAssertion(MALE, P1));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "Male(p1) -> sqwrl:select(p1)");

    Assert.assertTrue(result.next());
    Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
  }

  @Test public void TestCascadingRuleAndQuery() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, Declaration(ADULT), Declaration(PERSON), ClassAssertion(PERSON, P1),
        DataPropertyAssertion(HAS_AGE, P1, Literal("18", XSD_INT)));

    ruleEngine.createSWRLRule("R1", "Person(?p) ^ hasAge(?p, ?age)^ swrlb:greaterThan(?age, 17) -> Adult(?p)");

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "Adult(p1) -> sqwrl:select(p1)");

    Assert.assertTrue(result.next());
    Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
  }
}