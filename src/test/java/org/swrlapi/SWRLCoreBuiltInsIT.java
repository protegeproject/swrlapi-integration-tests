package org.swrlapi;

import org.junit.Assert;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.literal.XSDDate;
import org.swrlapi.literal.XSDDateTime;
import org.swrlapi.literal.XSDDuration;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.test.IntegrationTestBase;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;

/**
 * NOTE: All tests are designed for parallel execution.
 *
 * @see org.swrlapi.builtins.swrlb.SWRLBuiltInLibraryImpl
 */
public class SWRLCoreBuiltInsIT extends IntegrationTestBase
{
  private static final OWLNamedIndividual P1 = NamedIndividual(iri("p1"));
  private static final OWLDataProperty HAS_AGE = DataProperty(iri("hasAge"));
  private static final OWLDataProperty HAS_NAME = DataProperty(iri("hasName"));
  private static final OWLDataProperty IS_FRENCH = DataProperty(iri("isFrench"));
  private static final OWLDataProperty HAS_HEIGHT_IN_CM = DataProperty(iri("hasHeightInCM"));
  private static final OWLDataProperty HAS_DOB = DataProperty(iri("hasDOB"));
  private static final OWLDataProperty HAS_TOB = DataProperty(iri("hasTOB"));

  @Test public void TestSWRLCoreLessThanBuiltInWithByte()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_BYTE)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:lessThan(?age, \"43\"^^xsd:byte) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreGreaterThanBuiltInWithByte()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_BYTE)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:greaterThan(?age, \"41\"^^xsd:byte) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreEqualBuiltInWithByte()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_BYTE)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:equal(?age, \"42\"^^xsd:byte) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreLessThanBuiltInWithShort()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_SHORT)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:lessThan(?age, \"43\"^^xsd:short) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreGreaterThanBuiltInWithShort()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_SHORT)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:greaterThan(?age, \"41\"^^xsd:short) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreEqualBuiltInWithShort()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_SHORT)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:equal(?age, \"42\"^^xsd:short) -> sqwrl:select(p1)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual(0).getShortName());
  }

  @Test public void TestSWRLCoreLessThanBuiltInWithInt()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_INT)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:lessThan(?age, \"43\"^^xsd:int) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreGreaterThanBuiltInWithInt()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_INT)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:greaterThan(?age, \"41\"^^xsd:int) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreEqualBuiltInWithInt()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_INT)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:equal(?age, \"42\"^^xsd:int) -> sqwrl:select(p1)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual(0).getShortName());
  }

  @Test public void TestSWRLCoreLessThanBuiltInWithInteger()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_INTEGER)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:lessThan(?age, 43) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreGreaterThanBuiltInWithInteger()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_INTEGER)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:greaterThan(?age, 41) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreEqualBuiltInWithInteger()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_INTEGER)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:equal(?age, 42) -> sqwrl:select(p1)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual(0).getShortName());
  }

  @Test public void TestSWRLCoreLessThanBuiltInWithLong()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_LONG)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:lessThan(?age, \"43\"^^xsd:long) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreGreaterThanBuiltInWithLong()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_LONG)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:greaterThan(?age, \"41\"^^xsd:long) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreEqualBuiltInWithLong()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_LONG)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:equal(?age, \"42\"^^xsd:long) -> sqwrl:select(p1)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual(0).getShortName());
  }

  @Test public void TestSWRLCoreGreaterThanBuiltInWithFloat()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42.0", XSD_FLOAT)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:greaterThan(?age, \"41.0\"^^xsd:float) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreEqualBuiltInWithFloat()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT_IN_CM, P1, Literal("177.0", XSD_FLOAT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasHeightInCM(p1, ?height) ^ swrlb:equal(?height, \"177.0\"^^xsd:float) -> sqwrl:select(p1)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual(0).getShortName());
  }

  @Test public void TestSWRLCoreLessThanBuiltInWithFloat()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42.0", XSD_FLOAT)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:lessThan(?age, \"43.0\"^^xsd:float) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreGreaterThanBuiltInWithDecimal()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42.0", XSD_DECIMAL)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:greaterThan(?age, 41.0) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreEqualBuiltInWithDecimal()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT_IN_CM, P1, Literal("177.0", XSD_DECIMAL)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasHeightInCM(p1, ?height) ^ swrlb:equal(?height, 177.0) -> sqwrl:select(p1)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual(0).getShortName());
  }

  @Test public void TestSWRLCoreLessThanBuiltInWithDecimal()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42.0", XSD_DECIMAL)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:lessThan(?age, 43.0) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreLessThanBuiltInWithDouble()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42.0", XSD_DOUBLE)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:lessThan(?age, \"43\"^^xsd:double) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreGreaterThanBuiltInWithDouble()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42.0", XSD_DOUBLE)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:greaterThan(?age, \"41\"^^xsd:double) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreEqualBuiltInWithDouble()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT_IN_CM, P1, Literal("177.0", XSD_DOUBLE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasHeightInCM(p1, ?height) ^ swrlb:equal(?height, \"177.0\"^^xsd:double) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreEqualBuiltInWithBoolean()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(IS_FRENCH, P1, Literal("true", XSD_BOOLEAN)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "isFrench(p1, ?french) ^ swrlb:equal(?french, true)-> sqwrl:select(p1)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual(0).getShortName());
  }

  @Test public void TestSWRLCoreLessThanBuiltInWithString()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P1, Literal("Bob", XSD_STRING)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasName(p1, ?name) ^ swrlb:lessThan(?name, \"Fred\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreGreaterThanBuiltInWithString()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P1, Literal("Bob", XSD_STRING)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasName(p1, ?name) ^ swrlb:greaterThan(?name, \"Adam\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreEqualBuiltInWithString()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P1, Literal("Bob", XSD_STRING)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasName(p1, ?name) ^ swrlb:equal(?name, \"Bob\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreEqualBuiltInWithXSDDate()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_DOB, P1, Literal("2001-01-05", XSD_DATE)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasDOB(p1, ?dob) ^ swrlb:equal(?dob, \"2001-01-05\"^^xsd:date) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreEqualBuiltInWithXSDDateTime()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_TOB, P1, Literal("2001-01-05T10:10:10", XSD_DATETIME)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasTOB(p1, ?tob) ^ swrlb:equal(?tob, \"2001-01-05T10:10:10\"^^xsd:dateTime)-> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreEqualBuiltInWithXSDDuration()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("P42Y", XSD_DURATION)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:equal(?age, \"P42Y\"^^xsd:duration) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreEqualBuiltInWithXSDTime()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_TOB, P1, Literal("10:10:10.33", XSD_TIME)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasTOB(p1, ?bt) ^ swrlb:equal(?bt, \"10:10:10.33\"^^xsd:time) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreAddBuiltInWithShort()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String query = "swrlb:add(\"4\"^^xsd:short, \"2\"^^xsd:short, \"2\"^^xsd:short) -> sqwrl:select(0)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreAddBuiltInWithInt()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:add(4, 2, 2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreAddBuiltInWithFloat()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:add(4.1, 2.1, 2.0) -> sqwrl:select(\"Yes\")");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreAddBuiltInWithLong()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String query = "swrlb:add(\"4\"^^xsd:long, \"2\"^^xsd:long, \"2\"^^xsd:long) -> sqwrl:select(\"Yes\")";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreAddBuiltInWithDoubles()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String query = "swrlb:add(\"4.0\"^^xsd:double, \"2.0\"^^xsd:double, \"2.0\"^^xsd:double)" + " -> sqwrl:select(0)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreBooleanNotBuiltIn()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:booleanNot(true, false) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreStringConcatBuiltIn()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String query = "swrlb:stringConcat(?r, \"A\", \"B\") -> sqwrl:select(?r)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral("r");
    Assert.assertTrue(literal.isString());
    Assert.assertEquals("AB", literal.getString());
  }

  @Test public void TestSWRLCoreStringEqualIgnoreCaseBuiltIn()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "swrlb:stringEqualIgnoreCase(\"AAA\", \"aaa\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreSubStringBuiltIn()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "swrlb:substring(\"123\", \"12345\", 0, 3) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreStringLengthBuiltIn()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:stringLength(3, \"ABC\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreUpperCaseBuiltIn()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:upperCase(\"ABC\", \"abc\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreLowerCaseBuiltIn()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:lowerCase(\"abc\", \"ABC\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreContainsBuiltIn()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:contains(\"abcd\", \"bc\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreContainsIgnoreCaseBuiltIn()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "swrlb:containsIgnoreCase(\"abcd\", \"BC\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreStartsWithBuiltIn()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:startsWith(\"abcd\", \"ab\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreEndsWithBuiltIn()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:endsWith(\"abcd\", \"cd\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  // TODO swrlb:translate built-in implementation not correct
  // @Test
  // public void TestSWRLCoreTranslateBuiltIn() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  // {
  // SQWRLResult result = queryEngine.runSQWRLQuery("q1",
  // "swrlb:translate(\"AAA\", \"--aaa--\", \"abc-\", \"ABC\") -> sqwrl:select(0)");
  //
  // Assert.assertTrue(result.next());
  // }

  @Test public void TestSWRLCoreTokenizeBuiltIn()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "swrlb:tokenize(?t, \"sat,cat,bone\", \",\") -> sqwrl:select(?t) ^ sqwrl:orderBy(?t)");

    Assert.assertEquals(result.getNumberOfRows(), 3);
    Assert.assertTrue(result.next());
    Assert.assertEquals(result.getLiteral(0).getString(), "bone");
    Assert.assertTrue(result.next());
    Assert.assertEquals(result.getLiteral(0).getString(), "cat");
    Assert.assertTrue(result.next());
    Assert.assertEquals(result.getLiteral(0).getString(), "sat");
  }

  @Test public void TestSWRLCoreSubstringAfterBuiltIn()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "swrlb:substringAfter(\"ddd\", \"aaaddd\", \"aaa\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreSubstringBeforeBuiltIn()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "swrlb:substringBefore(\"aaa\", \"aaaddd\", \"ddd\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreMatchesBuiltIn() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "swrlb:matches(\"abracadabra\", \"^a.*a$\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreReplaceBuiltIn() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "swrlb:replace(\"a*cada*\", \"abracadabra\", \"bra\", \"*\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNormalizeSpaceBuiltIn()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "swrlb:normalizeSpace(\"fred\", \"  fred  \") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreYearMonthDurationBuiltIn()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String query = "swrlb:yearMonthDuration(?x, 3, 4) -> sqwrl:select(?x)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("x").isDuration());
    Assert.assertEquals(new XSDDuration("P3Y4M"), result.getLiteral("x").getDuration());
  }

  @Test public void TestSWRLCoreDayTimeDurationBuiltIn()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String query = "swrlb:dayTimeDuration(?x, 2, 3, 4, 5) -> sqwrl:select(?x)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("x").isDuration());
    Assert.assertEquals(new XSDDuration("P2DT3H4M5S"), result.getLiteral("x").getDuration());
  }

  @Test public void TestSWRLCoreTimeBuiltIn() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "swrlb:time(\"10:11:12\"^^xsd:time, 10, 11, 12, \"\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreAddDayTimeDurationToDate() throws Exception
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "swrlb:addDayTimeDurationToDate(?d, \"1999-01-01\"^^xsd:date, \"P1D\"^^xsd:duration) -> sqwrl:select(?d)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("d").isDate());
    Assert.assertEquals(new XSDDate("1999-01-02"), result.getLiteral("d").getDate());
  }

  @Test public void TestSWRLCoreAddDayTimeDurationToDateTime() throws Exception
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "swrlb:addDayTimeDurationToDateTime(?dt, \"1999-01-01T12:12:12\"^^xsd:dateTime, \"P1D\"^^xsd:duration) -> sqwrl:select(?dt)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("dt").isDateTime());
    Assert.assertEquals(new XSDDateTime("1999-01-02T12:12:12"), result.getLiteral("dt").getDateTime());
  }

}
