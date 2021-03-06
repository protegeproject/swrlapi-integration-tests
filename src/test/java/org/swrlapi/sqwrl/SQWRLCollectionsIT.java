package org.swrlapi.sqwrl;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.sqwrl.values.SQWRLNamedIndividualResultValue;
import org.swrlapi.test.IntegrationTestBase;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

/**
 * NOTE: All tests are designed for parallel execution.
 */
public class SQWRLCollectionsIT extends IntegrationTestBase
{
  private static final OWLClass PERSON = Class(iri("Person"));
  private static final OWLClass AZT = Class(iri("AZT"));
  private static final OWLClass DDI = Class(iri("DDI"));
  private static final OWLClass BBT = Class(iri("BBT"));
  private static final OWLNamedIndividual P1 = NamedIndividual(iri("p1"));
  private static final OWLNamedIndividual P2 = NamedIndividual(iri("p2"));
  private static final OWLNamedIndividual P3 = NamedIndividual(iri("p2"));
  private static final OWLDataProperty HAS_ALIAS = DataProperty(iri("hasAlias"));
  private static final OWLDataProperty HAS_AGE = DataProperty(iri("hasAge"));
  private static final OWLNamedIndividual BOB = NamedIndividual(iri("Bob"));
  private static final OWLNamedIndividual FRED = NamedIndividual(iri("Fred"));

  @Rule public final ExpectedException thrown = ExpectedException.none();

  @Test public void TestSQWRLClassBagsEqual() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, Declaration(DDI), Declaration(AZT));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeBag(?s1, AZT)"
      + " ^ sqwrl:makeBag(?s2, AZT) ^ sqwrl:makeBag(?s2, AZT) . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSQWRLClassSetsEqual() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, Declaration(DDI), Declaration(AZT));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeSet(?s1, DDI) ^ sqwrl:makeSet(?s1, AZT)"
      + " ^ sqwrl:makeSet(?s2, AZT) ^ sqwrl:makeSet(?s2, AZT) . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSQWRLIndividualBagsEqual()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, Declaration(BOB), Declaration(FRED));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, Bob) ^ sqwrl:makeBag(?s1, Fred)"
      + " ^ sqwrl:makeBag(?s2, Bob) ^ sqwrl:makeBag(?s2, Fred) . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSQWRLIndividualSetsEqual()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, Declaration(BOB), Declaration(FRED));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeSet(?s1, Bob) ^ sqwrl:makeSet(?s1, Fred)"
      + " ^ sqwrl:makeSet(?s2, Bob) ^ sqwrl:makeSet(?s2, Fred) . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSQWRLByteBagsEqual() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, \"32\"^^xsd:byte) ^ sqwrl:makeBag(?s2, \"39\"^^xsd:byte)"
        + " ^ sqwrl:makeBag(?s1, \"32\"^^xsd:byte) ^ sqwrl:makeBag(?s1, \"39\"^^xsd:byte) . sqwrl:equal(?s1, ?s2)"
        + " -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSQWRLByteSetsEqual() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeSet(?s1, \"32\"^^xsd:byte) ^ sqwrl:makeSet(?s2, \"39\"^^xsd:byte)"
        + " ^ sqwrl:makeSet(?s1, \"32\"^^xsd:byte) ^ sqwrl:makeSet(?s1, \"39\"^^xsd:byte) . sqwrl:equal(?s1, ?s2)"
        + " -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSQWRLShortBagsEqual() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, \"32\"^^xsd:short) ^ sqwrl:makeBag(?s2, \"39\"^^xsd:short)"
        + " ^ sqwrl:makeBag(?s1, \"32\"^^xsd:short) ^ sqwrl:makeBag(?s1, \"39\"^^xsd:short) "
        + " . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSQWRLShortSetsEqual() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeSet(?s1, \"32\"^^xsd:short) ^ sqwrl:makeSet(?s2, \"39\"^^xsd:short)"
        + " ^ sqwrl:makeSet(?s1, \"32\"^^xsd:short) ^ sqwrl:makeSet(?s1, \"39\"^^xsd:short) . "
        + " sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSQWRLIntegerBagsEqual() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, 3) ^ sqwrl:makeBag(?s1, 5)"
      + " ^ sqwrl:makeBag(?s2, 3) ^ sqwrl:makeBag(?s2, 5) . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSQWRLIntegerSetsEqual() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeSet(?s1, 3) ^ sqwrl:makeSet(?s1, 5)"
      + " ^ sqwrl:makeSet(?s2, 3) ^ sqwrl:makeSet(?s2, 5) . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSQWRLLongBagsEqual() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, \"32\"^^xsd:long) ^ sqwrl:makeBag(?s2, \"39\"^^xsd:long)"
        + " ^ sqwrl:makeBag(?s1, \"32\"^^xsd:long) ^ sqwrl:makeBag(?s1, \"39\"^^xsd:long) . sqwrl:equal(?s1, ?s2)"
        + " -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSQWRLLongSetsEqual() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeSet(?s1, \"32\"^^xsd:long) ^ sqwrl:makeSet(?s1, \"39\"^^xsd:long)"
        + " ^ sqwrl:makeSet(?s2, \"32\"^^xsd:long) ^ sqwrl:makeSet(?s2, \"39\"^^xsd:long) "
        + " ^ sqwrl:makeSet(?s2, \"39\"^^xsd:long) . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSQWRLFloatBagsEqual() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, 3.1) ^ sqwrl:makeBag(?s1, 5.1)"
      + " ^ sqwrl:makeBag(?s2, 3.1) ^ sqwrl:makeBag(?s2, 5.1) ^ sqwrl:makeBag(?s2, 5.1) "
      + " . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSQWRLFloatSetsEqual() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeSet(?s1, 3.1) ^ sqwrl:makeSet(?s1, 5.1)"
      + " ^ sqwrl:makeSet(?s2, 3.1) ^ sqwrl:makeSet(?s2, 5.1) ^ sqwrl:makeSet(?s2, 5.1)"
      + ". sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSQWRLDoubleBagsEqual() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, \"32.1\"^^xsd:double) ^ sqwrl:makeBag(?s2, \"39.3\"^^xsd:double)"
        + " ^ sqwrl:makeBag(?s1, \"32.1\"^^xsd:double) ^ sqwrl:makeBag(?s1, \"39.3\"^^xsd:double) . sqwrl:equal"
        + "(?s1, ?s2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSQWRLDoubleSetsEqual() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, \"32.1\"^^xsd:double) ^ sqwrl:makeBag(?s1, \"39.3\"^^xsd:double)"
        + " ^ sqwrl:makeBag(?s2, \"32.1\"^^xsd:double) ^ sqwrl:makeBag(?s2, \"39.3\"^^xsd:double) "
        + " sqwrl:makeBag(?s2, \"39.3\"^^xsd:double) . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSQWRLStringBagsEqual() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"A\") ^ sqwrl:makeBag(?s1, \"B\")"
      + " ^ sqwrl:makeBag(?s2, \"A\") ^ sqwrl:makeBag(?s2, \"B\") . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSQWRLStringSetsEqual() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeSet(?s1, \"A\") ^ sqwrl:makeSet(?s1, \"B\")"
      + " ^ sqwrl:makeBag(?s2, \"A\") ^ sqwrl:makeBag(?s2, \"B\") ^ sqwrl:makeBag(?s2, \"B\")"
      + " . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSQWRLBooleanBagsEqual() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, true) ^ sqwrl:makeBag(?s1, false)"
      + " ^ sqwrl:makeBag(?s2, true) ^ sqwrl:makeBag(?s2, false) . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSQWRLBooleanSetsEqual() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeSet(?s1, true) ^ sqwrl:makeSet(?s1, false)"
      + " ^ sqwrl:makeSet(?s2, true) ^ sqwrl:makeSet(?s2, false) ^ sqwrl:makeSet(?s2, false) "
      + " . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSQWRLURIBagsEqual() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String homePage1 = "http://stanford.edu/~fred";
    String homePage2 = "http://stanford.edu/~bob";

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, \"" + homePage1 + "\"^^xsd:anyURI) ^ sqwrl:makeBag(?s2, \"" + homePage2 + "\"^^xsd:anyURI)"
        + " ^ sqwrl:makeBag(?s1, \"" + homePage1 + "\"^^xsd:anyURI) ^ sqwrl:makeBag(?s2, \"" + homePage2
        + "\"^^xsd:anyURI) . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSQWRLURISetsEqual() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String homePage1 = "http://stanford.edu/~fred";
    String homePage2 = "http://stanford.edu/~bob";

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeSet(?s1, \"" + homePage1 + "\"^^xsd:anyURI) " + " ^ sqwrl:makeSet(?s1, \"" + homePage2
        + "\"^^xsd:anyURI)" + " ^ sqwrl:makeSet(?s2, \"" + homePage1 + "\"^^xsd:anyURI) " + " ^ sqwrl:makeSet"
        + "(?s2, \"" + homePage2 + "\"^^xsd:anyURI) " + " ^ sqwrl:makeSet(?s2, \"" + homePage2 + "\"^^xsd:anyURI) "
        + " . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSQWRLBagSizeWithVariables()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(PERSON, P1), ClassAssertion(PERSON, P2));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("20", XSD_INTEGER)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_INTEGER)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "Person(?p) ^ hasAge(?p, ?age) . sqwrl:makeBag(?b, ?age) . sqwrl:size(?size, ?b) -> sqwrl:select(?size)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral("size");
    Assert.assertTrue(literal.isInteger());
    Assert.assertEquals(BigInteger.valueOf(2), literal.getInteger());
  }

  @Test public void TestSQWRLBagGroupByWithVariables()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(PERSON, P1), ClassAssertion(PERSON, P2));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_ALIAS, P1, Literal("Bob", XSD_STRING)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_ALIAS, P2, Literal("Fred", XSD_STRING)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_ALIAS, P2, Literal("Freddy", XSD_STRING)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "Person(?p) ^ hasAlias(?p, ?alias) . sqwrl:makeBag(?b, ?alias) ^ sqwrl:groupBy(?b, ?p) . sqwrl:size(?size, ?b) "
        + " -> sqwrl:select(?p, ?size) ^ sqwrl:orderBy(?size)");

    Assert.assertTrue(result.next());
    SQWRLNamedIndividualResultValue person1 = result.getNamedIndividual("p");
    Assert.assertEquals("p1", person1.getShortName());
    SQWRLLiteralResultValue size1 = result.getLiteral("size");
    Assert.assertTrue(size1.isInteger());
    Assert.assertEquals(BigInteger.valueOf(1), size1.getInteger());
    Assert.assertTrue(result.next());
    SQWRLNamedIndividualResultValue person2 = result.getNamedIndividual("p");
    Assert.assertEquals("p2", person2.getShortName());
    SQWRLLiteralResultValue size2 = result.getLiteral("size");
    Assert.assertTrue(size2.isInteger());
    Assert.assertEquals(BigInteger.valueOf(2), size2.getInteger());
  }

  @Test public void TestSQWRLSetGroupByWithVariables()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(PERSON, P1), ClassAssertion(PERSON, P2));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_ALIAS, P1, Literal("Bob", XSD_STRING)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_ALIAS, P2, Literal("Fred", XSD_STRING)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_ALIAS, P2, Literal("Fred", XSD_STRING)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "Person(?p) ^ hasAlias(?p, ?alias) . sqwrl:makeSet(?s, ?alias) ^ sqwrl:groupBy(?s, ?p) . sqwrl:size(?size, ?s) "
        + " -> sqwrl:select(?p, ?size) ^ sqwrl:orderBy(?size)");

    Assert.assertTrue(result.next());
    SQWRLNamedIndividualResultValue person1 = result.getNamedIndividual("p");
    Assert.assertEquals("p1", person1.getShortName());
    SQWRLLiteralResultValue size1 = result.getLiteral("size");
    Assert.assertTrue(size1.isInteger());
    Assert.assertEquals(BigInteger.valueOf(1), size1.getInteger());
    Assert.assertTrue(result.next());
    SQWRLNamedIndividualResultValue person2 = result.getNamedIndividual("p");
    Assert.assertEquals("p2", person2.getShortName());
    SQWRLLiteralResultValue size2 = result.getLiteral("size");
    Assert.assertTrue(size2.isInteger());
    Assert.assertEquals(BigInteger.valueOf(1), size2.getInteger());
  }

  @Test public void TestSQWRLBagsSize() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, Declaration(DDI), Declaration(AZT));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeBag(?s1, AZT) . sqwrl:size(?size, ?s1) -> sqwrl:select(?size)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral("size");
    Assert.assertTrue(literal.isInteger());
    Assert.assertEquals(BigInteger.valueOf(2), literal.getInteger());
  }

  @Test public void TestSQWRLSetSizeWithVariables()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(PERSON, P1), ClassAssertion(PERSON, P2), ClassAssertion(PERSON, P3));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_INTEGER)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_INTEGER)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("20", XSD_INTEGER)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "Person(?p) ^ hasAge(?p, ?age) . sqwrl:makeSet(?b, ?age) . sqwrl:size(?size, ?b) -> sqwrl:select(?size)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral("size");
    Assert.assertTrue(literal.isInteger());
    Assert.assertEquals(BigInteger.valueOf(2), literal.getInteger());
  }

  @Test public void TestSQWRLSetsSize() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, Declaration(DDI), Declaration(AZT));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeSet(?s1, AZT) ^ sqwrl:makeSet(?s1, AZT)"
        + " . sqwrl:size(?size, ?s1) -> sqwrl:select(?size)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral("size");
    Assert.assertTrue(literal.isInteger());
    Assert.assertEquals(BigInteger.valueOf(2), literal.getInteger());
  }

  @Test public void TestSQWRLBagsSizeEqual() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, Declaration(DDI), Declaration(AZT));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeBag(?s1, AZT) . sqwrl:size(?size, ?s1) "
        + " ^ swrlb:equal(?size, 2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSQWRLSetSizeEqual() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, Declaration(DDI), Declaration(AZT));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeSet(?s1, DDI) ^ sqwrl:makeSet(?s1, AZT) ^ sqwrl:makeSet(?s1, AZT)"
        + " . sqwrl:size(?size, ?s1)  ^ swrlb:equal(?size, 2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSQWRLClassesBagFirst() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, Declaration(DDI), Declaration(AZT));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeBag(?s1, AZT) . sqwrl:first(?first, ?s1) -> sqwrl:select(?first)");

    Assert.assertTrue(result.next());
    Assert.assertEquals("AZT", result.getClass("first").getShortName());
  }

  @Test public void TestSQWRLNamedIndividualsBagFirst()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, Declaration(FRED), Declaration(BOB));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, Fred) ^ sqwrl:makeBag(?s1, Bob) . sqwrl:first(?first, ?s1) -> sqwrl:select(?first)");

    Assert.assertTrue(result.next());
    Assert.assertEquals("Bob", result.getNamedIndividual("first").getShortName());
  }

  @Test public void TestSQWRLByteBagMin() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, \"77\"^^xsd:byte) ^ sqwrl:makeBag(?s1, \"23\"^^xsd:byte) "
        + ". sqwrl:min(?min, ?s1) -> sqwrl:select(?min)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("min").isByte());
    Assert.assertEquals(23, result.getLiteral("min").getByte());
  }

  @Test public void TestSQWRLShortBagMin() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, \"77\"^^xsd:short) ^ sqwrl:makeBag(?s1, \"23\"^^xsd:short) "
        + ". sqwrl:min(?min, ?s1) -> sqwrl:select(?min)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("min").isShort());
    Assert.assertEquals(23, result.getLiteral("min").getShort());
  }

  @Test public void TestSQWRLIntBagMin() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, \"77\"^^xsd:int) ^ sqwrl:makeBag(?s1, \"23\"^^xsd:int) "
        + ". sqwrl:min(?min, ?s1) -> sqwrl:select(?min)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("min").isInt());
    Assert.assertEquals(23, result.getLiteral("min").getInt());
  }

  @Test public void TestSQWRLIntegerBagMin() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, 77) ^ sqwrl:makeBag(?s1, 23) . sqwrl:min(?min, ?s1) -> sqwrl:select(?min)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("min").isInteger());
    Assert.assertEquals(BigInteger.valueOf(23), result.getLiteral("min").getInteger());
  }

  @Test public void TestSQWRLLongBagMin() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, \"77\"^^xsd:long) ^ sqwrl:makeBag(?s1, \"23\"^^xsd:long) "
        + ". sqwrl:min(?min, ?s1) -> sqwrl:select(?min)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("min").isLong());
    Assert.assertEquals(23L, result.getLiteral("min").getLong());
  }

  @Test public void TestSQWRLFloatBagMin() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, \"77.32\"^^xsd:float) ^ sqwrl:makeBag(?s1, \"23.3\"^^xsd:float) "
        + ". sqwrl:min(?min, ?s1) -> sqwrl:select(?min)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("min").isFloat());
    Assert.assertEquals(23.3f, result.getLiteral("min").getDouble(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLDoubleBagMin() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, \"77.32\"^^xsd:double) ^ sqwrl:makeBag(?s1, \"23.3\"^^xsd:double) "
        + ". sqwrl:min(?min, ?s1) -> sqwrl:select(?min)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("min").isDouble());
    Assert.assertEquals(23.3d, result.getLiteral("min").getDouble(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLDecimalBagMin() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, 77.4) ^ sqwrl:makeBag(?s1, 23.3) . sqwrl:min(?min, ?s1) -> sqwrl:select(?min)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("min").isDecimal());
    Assert.assertEquals(BigDecimal.valueOf(23.3), result.getLiteral("min").getDecimal());
  }

  @Test public void TestSQWRLByteBagMax() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, \"77\"^^xsd:byte) ^ sqwrl:makeBag(?s1, \"23\"^^xsd:byte) "
        + ". sqwrl:max(?max, ?s1) -> sqwrl:select(?max)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("max").isByte());
    Assert.assertEquals(77, result.getLiteral("max").getByte());
  }

  @Test public void TestSQWRLShortBagMax() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, \"77\"^^xsd:short) ^ sqwrl:makeBag(?s1, \"23\"^^xsd:short) "
        + ". sqwrl:max(?max, ?s1) -> sqwrl:select(?max)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("max").isShort());
    Assert.assertEquals(77, result.getLiteral("max").getShort());
  }

  @Test public void TestSQWRLIntBagMax() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, \"77\"^^xsd:int) ^ sqwrl:makeBag(?s1, \"23\"^^xsd:int) "
        + ". sqwrl:max(?max, ?s1) -> sqwrl:select(?max)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("max").isInt());
    Assert.assertEquals(77, result.getLiteral("max").getInt());
  }

  @Test public void TestSQWRLIntegerBagMax() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, 77) ^ sqwrl:makeBag(?s1, 23) . sqwrl:max(?max, ?s1) -> sqwrl:select(?max)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("max").isInteger());
    Assert.assertEquals(BigInteger.valueOf(77), result.getLiteral("max").getInteger());
  }

  @Test public void TestSQWRLLongBagMax() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, \"77\"^^xsd:long) ^ sqwrl:makeBag(?s1, \"23\"^^xsd:long) "
        + ". sqwrl:max(?max, ?s1) -> sqwrl:select(?max)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("max").isLong());
    Assert.assertEquals(77L, result.getLiteral("max").getLong());
  }

  @Test public void TestSQWRLFloatBagMax() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, \"77.32\"^^xsd:float) ^ sqwrl:makeBag(?s1, \"23.3\"^^xsd:float) "
        + ". sqwrl:max(?max, ?s1) -> sqwrl:select(?max)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("max").isFloat());
    Assert.assertEquals(77.32f, result.getLiteral("max").getDouble(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLDoubleBagMax() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, \"77.32\"^^xsd:double) ^ sqwrl:makeBag(?s1, \"23.3\"^^xsd:double) "
        + ". sqwrl:max(?max, ?s1) -> sqwrl:select(?max)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("max").isDouble());
    Assert.assertEquals(77.32d, result.getLiteral("max").getDouble(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLDecimalBagMax() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, 77.4) ^ sqwrl:makeBag(?s1, 23.3) . sqwrl:max(?max, ?s1) -> sqwrl:select(?max)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("max").isDecimal());
    Assert.assertEquals(BigDecimal.valueOf(77.4), result.getLiteral("max").getDecimal());
  }

  @Test public void TestSQWRLStringBagFirst() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, \"Fred\"^^xsd:string) ^ sqwrl:makeBag(?s1, \"Bob\"^^xsd:string) "
        + ". sqwrl:first(?first, ?s1) -> sqwrl:select(?first)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("first").isString());
    Assert.assertEquals("Bob", result.getLiteral("first").getString());
  }

  @Test public void TestSQWRLStringSetFirst() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeSet(?s1, \"Fred\"^^xsd:string) ^ sqwrl:makeSet(?s1, \"Bob\"^^xsd:string) "
        + ". sqwrl:first(?first, ?s1) -> sqwrl:select(?first)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("first").isString());
    Assert.assertEquals("Bob", result.getLiteral("first").getString());
  }

  @Test public void TestSQWRLBagSumShortBroadest()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24\"^^xsd:byte) ^ "
      + "sqwrl:makeBag(?s1, \"23\"^^xsd:byte) ^ sqwrl:makeBag(?s1, \"25\"^^xsd:short) "
      + ". sqwrl:sum(?sum, ?s1) -> sqwrl:select(?sum)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("sum").isShort());
    Assert.assertEquals(72, result.getLiteral("sum").getShort());
  }

  @Test public void TestSQWRLSetSumShortBroadest()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeSet(?s1, \"24\"^^xsd:byte) ^ "
      + "sqwrl:makeSet(?s1, \"23\"^^xsd:byte) ^ sqwrl:makeSet(?s1, \"25\"^^xsd:short) "
      + ". sqwrl:sum(?sum, ?s1) -> sqwrl:select(?sum)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("sum").isShort());
    Assert.assertEquals(72, result.getLiteral("sum").getShort());
  }

  @Test public void TestSQWRLBagSumIntBroadest() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24\"^^xsd:byte) ^ "
      + "sqwrl:makeBag(?s1, \"23\"^^xsd:byte) ^ sqwrl:makeBag(?s1, \"25\"^^xsd:int) "
      + ". sqwrl:sum(?sum, ?s1) -> sqwrl:select(?sum)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("sum").isInt());
    Assert.assertEquals(72, result.getLiteral("sum").getInt());
  }

  @Test public void TestSQWRLBagSumLongBroadest()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24\"^^xsd:byte) ^ "
      + "sqwrl:makeBag(?s1, \"23\"^^xsd:byte) ^ sqwrl:makeBag(?s1, \"25\"^^xsd:long) "
      + ". sqwrl:sum(?sum, ?s1) -> sqwrl:select(?sum)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("sum").isLong());
    Assert.assertEquals(72L, result.getLiteral("sum").getLong());
  }

  @Test public void TestSQWRLBagSumFloatBroadest()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24\"^^xsd:byte) ^ "
      + "sqwrl:makeBag(?s1, \"23\"^^xsd:byte) ^ sqwrl:makeBag(?s1, \"25.0\"^^xsd:float) "
      + ". sqwrl:sum(?sum, ?s1) -> sqwrl:select(?sum)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("sum").isFloat());
    Assert.assertEquals(72f, result.getLiteral("sum").getFloat(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLByteBagSumDoubleBroadest()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24\"^^xsd:byte) ^ "
      + "sqwrl:makeBag(?s1, \"23\"^^xsd:byte) ^ sqwrl:makeBag(?s1, \"25.0\"^^xsd:double) "
      + ". sqwrl:sum(?sum, ?s1) -> sqwrl:select(?sum)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("sum").isDouble());
    Assert.assertEquals(72d, result.getLiteral("sum").getDouble(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLByteBagSum() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24\"^^xsd:byte) ^ "
      + "sqwrl:makeBag(?s1, \"23\"^^xsd:byte) ^ sqwrl:makeBag(?s1, \"25\"^^xsd:byte) "
      + ". sqwrl:sum(?sum, ?s1) -> sqwrl:select(?sum)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("sum").isByte());
    Assert.assertEquals(72, result.getLiteral("sum").getByte());
  }

  @Test public void TestSQWRLShortBagSum() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24\"^^xsd:short) ^ "
      + "sqwrl:makeBag(?s1, \"23\"^^xsd:short) ^ sqwrl:makeBag(?s1, \"25\"^^xsd:short) "
      + ". sqwrl:sum(?sum, ?s1) -> sqwrl:select(?sum)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("sum").isShort());
    Assert.assertEquals(72, result.getLiteral("sum").getShort());
  }

  @Test public void TestSQWRLIntBagSum() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24\"^^xsd:int) ^ "
      + "sqwrl:makeBag(?s1, \"23\"^^xsd:int) ^ sqwrl:makeBag(?s1, \"25\"^^xsd:int) "
      + ". sqwrl:sum(?sum, ?s1) -> sqwrl:select(?sum)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("sum").isInt());
    Assert.assertEquals(72, result.getLiteral("sum").getInt());
  }

  @Test public void TestSQWRLLongBagSum() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24\"^^xsd:long) ^ "
      + "sqwrl:makeBag(?s1, \"23\"^^xsd:long) ^ sqwrl:makeBag(?s1, \"25\"^^xsd:long) "
      + ". sqwrl:sum(?sum, ?s1) -> sqwrl:select(?sum)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("sum").isLong());
    Assert.assertEquals(72L, result.getLiteral("sum").getLong());
  }

  @Test public void TestSQWRLFloatBagSum() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24.0\"^^xsd:float) ^ "
      + "sqwrl:makeBag(?s1, \"23.0\"^^xsd:float) ^ sqwrl:makeBag(?s1, \"25.0\"^^xsd:float) "
      + ". sqwrl:sum(?sum, ?s1) -> sqwrl:select(?sum)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("sum").isFloat());
    Assert.assertEquals(72.0f, result.getLiteral("sum").getFloat(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLDoubleBagSum() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24.0\"^^xsd:double) ^ "
      + "sqwrl:makeBag(?s1, \"23.0\"^^xsd:double) ^ sqwrl:makeBag(?s1, \"25.0\"^^xsd:double) "
      + ". sqwrl:sum(?sum, ?s1) -> sqwrl:select(?sum)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("sum").isDouble());
    Assert.assertEquals(72.0d, result.getLiteral("sum").getDouble(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLBagAvgShortBroadest()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24\"^^xsd:byte) ^ "
      + "sqwrl:makeBag(?s1, \"23\"^^xsd:byte) ^ sqwrl:makeBag(?s1, \"25\"^^xsd:short) "
      + ". sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("avg").isShort());
    Assert.assertEquals(24, result.getLiteral("avg").getShort());
  }

  @Test public void TestSQWRLBagAvgIntBroadest() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24\"^^xsd:byte) ^ "
      + "sqwrl:makeBag(?s1, \"23\"^^xsd:byte) ^ sqwrl:makeBag(?s1, \"25\"^^xsd:int) "
      + ". sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("avg").isInt());
    Assert.assertEquals(24, result.getLiteral("avg").getInt());
  }

  @Test public void TestSQWRLBagAvgLongBroadest()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24\"^^xsd:byte) ^ "
      + "sqwrl:makeBag(?s1, \"23\"^^xsd:byte) ^ sqwrl:makeBag(?s1, \"25\"^^xsd:long) "
      + ". sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("avg").isLong());
    Assert.assertEquals(24L, result.getLiteral("avg").getLong());
  }

  @Test public void TestSQWRLBagAvgFloatBroadest()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24\"^^xsd:byte) ^ "
      + "sqwrl:makeBag(?s1, \"23\"^^xsd:byte) ^ sqwrl:makeBag(?s1, \"25.0\"^^xsd:float) "
      + ". sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("avg").isFloat());
    Assert.assertEquals(24.0f, result.getLiteral("avg").getFloat(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLBagDoubleBroadest() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24\"^^xsd:byte) ^ "
      + "sqwrl:makeBag(?s1, \"23\"^^xsd:byte) ^ sqwrl:makeBag(?s1, \"25.0\"^^xsd:double) "
      + ". sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("avg").isDouble());
    Assert.assertEquals(24.0d, result.getLiteral("avg").getDouble(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLByteBagAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24\"^^xsd:byte) ^ "
      + "sqwrl:makeBag(?s1, \"23\"^^xsd:byte) ^ sqwrl:makeBag(?s1, \"25\"^^xsd:byte) "
      + ". sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("avg").isByte());
    Assert.assertEquals(24, result.getLiteral("avg").getByte());
  }

  @Test public void TestSQWRLByteSetAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeSet(?s1, \"24\"^^xsd:byte) ^ "
      + "sqwrl:makeSet(?s1, \"23\"^^xsd:byte) ^ sqwrl:makeSet(?s1, \"25\"^^xsd:byte) "
      + "sqwrl:makeSet(?s1, \"25\"^^xsd:byte) . sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("avg").isByte());
    Assert.assertEquals(24, result.getLiteral("avg").getByte());
  }

  @Test public void TestSQWRLShortBagAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24\"^^xsd:short) ^ "
      + "sqwrl:makeBag(?s1, \"23\"^^xsd:short)  ^ sqwrl:makeBag(?s1, \"25\"^^xsd:short) "
      + ". sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("avg").isShort());
    Assert.assertEquals(24, result.getLiteral("avg").getShort());
  }

  @Test public void TestSQWRLShortSetAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24\"^^xsd:short) ^ "
      + "sqwrl:makeBag(?s1, \"23\"^^xsd:short) ^ sqwrl:makeBag(?s1, \"23\"^^xsd:short)"
      + "sqwrl:makeBag(?s1, \"25\"^^xsd:short) . sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("avg").isShort());
    Assert.assertEquals(24, result.getLiteral("avg").getShort());
  }

  @Test public void TestSQWRLIntSetAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24\"^^xsd:short) ^ "
      + "sqwrl:makeBag(?s1, \"23\"^^xsd:int) ^ sqwrl:makeBag(?s1, \"23\"^^xsd:int)"
      + "sqwrl:makeBag(?s1, \"25\"^^xsd:int) . sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("avg").isInt());
    Assert.assertEquals(24, result.getLiteral("avg").getInt());
  }

  @Test public void TestSQWRLIntegerBagAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, 24) ^ " + "sqwrl:makeBag(?s1, 23) " + "sqwrl:makeBag(?s1, 25) "
        + ". sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("avg").isInteger());
    Assert.assertEquals(BigInteger.valueOf(24), result.getLiteral("avg").getInteger());
  }

  @Test public void TestSQWRLIntBagAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, \"24\"^^xsd:int) ^ " + "sqwrl:makeBag(?s1, \"23\"^^xsd:int) "
        + "sqwrl:makeBag(?s1, \"25\"^^xsd:int) " + ". sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("avg").isInt());
    Assert.assertEquals(24, result.getLiteral("avg").getInt());
  }

  @Test public void TestSQWRLIntegerSetAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeSet(?s1, 24) ^ sqwrl:makeSet(?s1, 23) "
      + "^ sqwrl:makeSet(?s1, 25) ^ sqwrl:makeSet(?s1, 25) . sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("avg").isInteger());
    Assert.assertEquals(BigInteger.valueOf(24), result.getLiteral("avg").getInteger());
  }

  @Test public void TestSQWRLLongBagAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, \"24\"^^xsd:long) ^ " + "sqwrl:makeBag(?s1, \"23\"^^xsd:long) "
        + "sqwrl:makeBag(?s1, \"25\"^^xsd:long) " + ". sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("avg").isLong());
    Assert.assertEquals(24L, result.getLiteral("avg").getLong());
  }

  @Test public void TestSQWRLLongSetAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeSet(?s1, \"24\"^^xsd:long) ^ "
      + "sqwrl:makeSet(?s1, \"23\"^^xsd:long) ^ sqwrl:makeSet(?s1, \"25\"^^xsd:long) "
      + "sqwrl:makeSet(?s1, \"25\"^^xsd:long) . sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("avg").isLong());
    Assert.assertEquals(24L, result.getLiteral("avg").getLong());
  }

  @Test public void TestSQWRLDecimalBagAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, 24.2) ^ " + "sqwrl:makeBag(?s1, 23.2) " + "sqwrl:makeBag(?s1, 25.2) "
        + ". sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("avg").isDecimal());
    Assert.assertEquals(BigDecimal.valueOf(24.2), result.getLiteral("avg").getDecimal());
  }

  @Test public void TestSQWRLDecimalSetAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeSet(?s1, 24.2) ^ " + "sqwrl:makeSet(?s1, 23.2) "
      + "sqwrl:makeBag(?s1, 25.2) ^ sqwrl:makeBag(?s1, 25.2) . sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("avg").isDecimal());
    Assert.assertEquals(BigDecimal.valueOf(24.2), result.getLiteral("avg").getDecimal());
  }

  @Test public void TestSQWRLDoubleBagAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24.2\"^^xsd:double) ^ "
      + "sqwrl:makeBag(?s1, \"23.2\"^^xsd:double) ^ sqwrl:makeBag(?s1, \"25.2\"^^xsd:double) "
      + ". sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("avg").isDouble());
    Assert.assertEquals(24.2d, result.getLiteral("avg").getDouble(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLDoubleSetAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24.2\"^^xsd:double) ^ "
      + "sqwrl:makeBag(?s1, \"23.2\"^^xsd:double)  ^ sqwrl:makeBag(?s1, \"25.2\"^^xsd:double) "
      + "sqwrl:makeBag(?s1, \"25.2\"^^xsd:double) . sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("avg").isDouble());
    Assert.assertEquals(24.2d, result.getLiteral("avg").getDouble(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLBagClassesLast() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, Declaration(DDI), Declaration(AZT));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeBag(?s1, AZT) . sqwrl:last(?last, ?s1) -> sqwrl:select(?last)");

    Assert.assertTrue(result.next());
    Assert.assertEquals("DDI", result.getClass("last").getShortName());
  }

  @Test public void TestSQWRLBagNamedIndividualLast()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, Declaration(FRED), Declaration(BOB));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      ". sqwrl:makeBag(?s1, Bob) ^ sqwrl:makeBag(?s1, Fred) . sqwrl:last(?last, ?s1) -> sqwrl:select(?last)");

    Assert.assertTrue(result.next());
    Assert.assertEquals("Fred", result.getNamedIndividual("last").getShortName());
  }

  @Test public void TestSQWRLNth() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, Declaration(DDI), Declaration(AZT), Declaration(BBT));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      " . sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeBag(?s1, AZT) ^ sqwrl:makeBag(?s1, BBT) "
        + " . sqwrl:nth(?second, ?s1, 2) -> sqwrl:select(?second)");

    Assert.assertTrue(result.next());
    Assert.assertEquals("BBT", result.getClass("second").getShortName());
  }

  @Test public void TestSQWRLNthLast() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, Declaration(DDI), Declaration(AZT), Declaration(BBT));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      " . sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeBag(?s1, AZT) ^ sqwrl:makeBag(?s1, BBT) "
        + " . sqwrl:nthLast(?secondLast, ?s1, 2) -> sqwrl:select(?secondLast)");

    Assert.assertTrue(result.next());
    Assert.assertEquals("BBT", result.getClass("secondLast").getShortName());
  }

  @Test public void TestSQWRLInvalidGroupByArgumentNumber()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("groupBy must have at least two arguments");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, Declaration(DDI));

    queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, DDI) ^ sqwrl:groupBy(?secondLast) -> ");
  }

  @Test public void TestSQWRLInvalidUnboundGroupByArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("unbound group argument passed to groupBy for collection s1");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, Declaration(DDI));

    queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, DDI) ^ sqwrl:groupBy(?s1, ?secondLast) -> ");
  }

  @Test public void TestSQWRLInvalidGroupByCollectionArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("groupBy applied to undefined collection s2");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, Declaration(DDI));

    queryEngine.runSQWRLQuery("q1", ". sqwrl:makeBag(?s1, DDI) ^ sqwrl:groupBy(?s2, ?secondLast) -> ");
  }
}
