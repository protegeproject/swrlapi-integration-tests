package org.swrlapi.sqwrl;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.literal.XSDDate;
import org.swrlapi.literal.XSDDateTime;
import org.swrlapi.literal.XSDDuration;
import org.swrlapi.literal.XSDTime;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.test.IntegrationTestBase;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ClassAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;

/**
 * NOTE: All tests are designed for parallel execution.
 */
public class SQWRLCoreIT extends IntegrationTestBase
{
  private static final OWLClass MALE = Class(iri("Male"));
  private static final OWLNamedIndividual P1 = NamedIndividual(iri("p1"));
  private static final OWLNamedIndividual P2 = NamedIndividual(iri("p2"));
  private static final OWLNamedIndividual P3 = NamedIndividual(iri("p3"));
  private static final OWLNamedIndividual P4 = NamedIndividual(iri("p4"));
  private static final OWLNamedIndividual P5 = NamedIndividual(iri("p5"));
  private static final OWLNamedIndividual P6 = NamedIndividual(iri("p6"));
  private static final OWLDataProperty HAS_AGE = DataProperty(iri("hasAge"));
  private static final OWLDataProperty HAS_TOB = DataProperty(iri("hasTOB"));
  private static final OWLDataProperty HAS_DOB = DataProperty(iri("hasDOB"));
  private static final OWLDataProperty HAS_HOMEPAGE = DataProperty(iri("hasHomePage"));
  private static final OWLDataProperty HAS_NAME = DataProperty(iri("hasName"));
  private static final OWLDataProperty HAS_HEIGHT = DataProperty(iri("hasHeight"));

  @Rule public final ExpectedException thrown = ExpectedException.none();

  @Test public void TestSQWRLCoreColumnName() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "-> sqwrl:select(3, 4) ^ sqwrl:columnNames(\"col1\", \"col2\")");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal1 = result.getLiteral("col1");
    Assert.assertTrue(literal1.isInteger());
    Assert.assertEquals(BigInteger.valueOf(3), literal1.getInteger());
    SQWRLLiteralResultValue literal2 = result.getLiteral("col2");
    Assert.assertTrue(literal2.isInteger());
    Assert.assertEquals(BigInteger.valueOf(4), literal2.getInteger());
  }

  @Test public void TestSQWRLCoreByteResult() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"34\"^^xsd:byte)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isByte());
    Assert.assertEquals("xsd:byte", literal.getDatatypePrefixedName());
    Assert.assertEquals(34, literal.getByte());
  }

  @Test public void TestSQWRLCoreShortResult() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"34\"^^xsd:short)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isShort());
    Assert.assertEquals("xsd:short", literal.getDatatypePrefixedName());
    Assert.assertEquals(34, literal.getShort());
  }

  @Test public void TestSQWRLCoreQualifiedIntResult()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"34\"^^xsd:int)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isInt());
    Assert.assertEquals("xsd:int", literal.getDatatypePrefixedName());
    Assert.assertEquals(34, literal.getInt());
  }

  @Test public void TestSQWRLCoreQualifiedIntegerResult()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"34\"^^xsd:integer)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isInteger());
    Assert.assertEquals("xsd:integer", literal.getDatatypePrefixedName());
    Assert.assertEquals(BigInteger.valueOf(34), literal.getInteger());
  }

  @Test public void TestSQWRLCoreRawIntegerResult()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(34)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isInteger());
    Assert.assertEquals("xsd:integer", literal.getDatatypePrefixedName());
    Assert.assertEquals(BigInteger.valueOf(34), literal.getInteger());
  }

  @Test public void TestSQWRLCoreLongResult() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"34\"^^xsd:long)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isLong());
    Assert.assertEquals("xsd:long", literal.getDatatypePrefixedName());
    Assert.assertEquals(34L, literal.getLong());
  }

  @Test public void TestSQWRLCoreRawDecimalResult()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(34.0)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isDecimal());
    Assert.assertEquals("xsd:decimal", literal.getDatatypePrefixedName());
    Assert.assertEquals(BigDecimal.valueOf(34.0), literal.getDecimal());
  }

  @Test public void TestSQWRLCoreQualifiedFloatResult()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"34.0\"^^xsd:float)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isFloat());
    Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:float");
    Assert.assertEquals(34.0f, literal.getFloat(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLCoreDoubleResult() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"34.0\"^^xsd:double)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isDouble());
    Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:double");
    Assert.assertEquals(34.0d, literal.getDouble(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLCoreQualifiedDecimalResult()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"34.0\"^^xsd:decimal)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isDecimal());
    Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:decimal");
    Assert.assertEquals(BigDecimal.valueOf(34.0), literal.getDecimal());
  }

  @Test public void TestSQWRLCoreQualifiedBooleanResult()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"true\"^^xsd:boolean)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isBoolean());
    Assert.assertEquals("xsd:boolean", literal.getDatatypePrefixedName());
    Assert.assertEquals(true, literal.getBoolean());
  }

  @Test public void TestSQWRLCoreRawBooleanResult()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(true)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isBoolean());
    Assert.assertEquals("xsd:boolean", literal.getDatatypePrefixedName());
    Assert.assertEquals(true, literal.getBoolean());
  }

  @Test public void TestSQWRLCoreQualifiedStringResult()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"Cat\"^^xsd:string)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isString());
    Assert.assertEquals("xsd:string", literal.getDatatypePrefixedName());
    Assert.assertEquals("Cat", literal.getString());
  }

  @Test public void TestSQWRLCoreRawStringResult()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"Cat\")");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isString());
    Assert.assertEquals("xsd:string", literal.getDatatypePrefixedName());
    Assert.assertEquals("Cat", literal.getString());
  }

  @Test public void TestSQWRLCoreURIResult()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException, URISyntaxException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String homepage = "http://stanford.edu/~fred";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"" + homepage + "\"^^xsd:anyURI)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isAnyURI());
    Assert.assertEquals("xsd:anyURI", literal.getDatatypePrefixedName());
    Assert.assertEquals(new URI(homepage), literal.getAnyURI());
  }

  @Test public void TestSQWRLCoreTimeResult() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"10:10:11\"^^xsd:time)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isTime());
    Assert.assertEquals("xsd:time", literal.getDatatypePrefixedName());
    Assert.assertEquals(new XSDTime("10:10:11"), literal.getTime());
  }

  @Test public void TestSQWRLCoreDateResult() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"1999-01-01\"^^xsd:date\")");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isDate());
    Assert.assertEquals("xsd:date", literal.getDatatypePrefixedName());
    Assert.assertEquals(new XSDDate("1999-01-01"), literal.getDate());
  }

  @Test public void TestSQWRLCoreDateTimeResult()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"1999-01-01T10:10:11\"^^xsd:dateTime)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isDateTime());
    Assert.assertEquals("xsd:dateTime", literal.getDatatypePrefixedName());
    Assert.assertEquals(new XSDDateTime("1999-01-01T10:10:11"), literal.getDateTime());
  }

  @Test public void TestSQWRLCoreDurationResult()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"P24Y\"^^xsd:duration)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isDuration());
    Assert.assertEquals("xsd:duration", literal.getDatatypePrefixedName());
    Assert.assertEquals(new XSDDuration("P24Y"), literal.getDuration());
  }

  @Test public void TestSQWRLCoreCount() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(MALE, P1), ClassAssertion(MALE, P2));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "Male(?m) -> sqwrl:count(?m)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isInt());
    Assert.assertEquals(2, literal.getInt());
  }

  @Test public void TestSQWRLCoreSumShortWidest()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_BYTE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P4, Literal("20", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P5, Literal("10", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P6, Literal("10", XSD_SHORT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:sum(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isShort());
    Assert.assertEquals(100, literal.getInt());
  }

  @Test public void TestSQWRLCoreSumIntWidest() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_BYTE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P4, Literal("20", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P5, Literal("10", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P6, Literal("10", XSD_INT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:sum(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isInt());
    Assert.assertEquals(100, literal.getInt());
  }

  @Test public void TestSQWRLCoreSumLongWidest() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_BYTE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P4, Literal("20", XSD_LONG)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P5, Literal("10", XSD_LONG)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P6, Literal("10", XSD_LONG)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:sum(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isLong());
    Assert.assertEquals(100L, literal.getLong());
  }

  @Test public void TestSQWRLCoreSumFloatWidest()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_BYTE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P4, Literal("20", XSD_LONG)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P5, Literal("10.0", XSD_FLOAT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P6, Literal("10.0", XSD_FLOAT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:sum(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isFloat());
    Assert.assertEquals(100.0f, literal.getFloat(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLCoreSumDoubleWidest()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_BYTE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P4, Literal("20", XSD_LONG)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P5, Literal("10.0", XSD_FLOAT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P6, Literal("10.0", XSD_DOUBLE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:sum(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isDouble());
    Assert.assertEquals(100.0d, literal.getDouble(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLCoreByteSum() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_BYTE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_BYTE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_BYTE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:sum(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isByte());
    Assert.assertEquals(60, literal.getByte());
  }

  @Test public void TestSQWRLCoreShortSum() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_SHORT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:sum(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isShort());
    Assert.assertEquals(60, literal.getShort());
  }

  @Test public void TestSQWRLCoreIntSum() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_INT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:sum(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isInt());
    Assert.assertEquals(60, literal.getInt());
  }

  @Test public void TestSQWRLCoreLongSum() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_LONG)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_LONG)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_LONG)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:sum(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isLong());
    Assert.assertEquals(60L, literal.getLong());
  }

  @Test public void TestSQWRLCoreFloatSum() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("100.1", XSD_FLOAT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P2, Literal("200.2", XSD_FLOAT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P3, Literal("300.2", XSD_FLOAT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:sum(?height)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isFloat());
    Assert.assertEquals(600.5f, literal.getFloat(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLCoreDoubleSum() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("100.1", XSD_DOUBLE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P2, Literal("200.2", XSD_DOUBLE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P3, Literal("300.2", XSD_DOUBLE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:sum(?height)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isDouble());
    Assert.assertEquals(600.5d, literal.getDouble(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLCoreAvgShortBroadest()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_BYTE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_SHORT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:avg(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isShort());
    Assert.assertEquals(20, literal.getShort());
  }

  @Test public void TestSQWRLCoreAvgIntBroadest()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_BYTE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_INT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:avg(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isInt());
    Assert.assertEquals(20, literal.getInt());
  }

  @Test public void TestSQWRLCoreAvgLongBroadest()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_LONG)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:avg(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isLong());
    Assert.assertEquals(20L, literal.getLong());
  }

  @Test public void TestSQWRLCoreAvgFloatBroadest()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_LONG)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30.0", XSD_FLOAT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:avg(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isFloat());
    Assert.assertEquals(20.0f, literal.getFloat(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLCoreAvgDoubleBroadest()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_LONG)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20.0", XSD_FLOAT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30.0", XSD_DOUBLE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:avg(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isDouble());
    Assert.assertEquals(20.0d, literal.getDouble(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLCoreByteAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_BYTE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_BYTE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_BYTE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:avg(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isByte());
    Assert.assertEquals(20, literal.getByte());
  }

  @Test public void TestSQWRLCoreShortAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_SHORT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:avg(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isShort());
    Assert.assertEquals(20, literal.getShort());
  }

  @Test public void TestSQWRLCoreIntAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_INT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:avg(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isInt());
    Assert.assertEquals(20, literal.getInt());
  }

  @Test public void TestSQWRLCoreLongAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_LONG)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_LONG)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_LONG)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:avg(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isLong());
    Assert.assertEquals(20L, literal.getLong());
  }

  @Test public void TestSQWRLCoreFloatAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("10.3", XSD_FLOAT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P2, Literal("20.4", XSD_FLOAT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P3, Literal("30.5", XSD_FLOAT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:avg(?height)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isFloat());
    Assert.assertEquals(20.4f, literal.getFloat(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLCoreDoubleAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("10.3", XSD_DOUBLE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P2, Literal("20.4", XSD_DOUBLE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P3, Literal("30.5", XSD_DOUBLE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:avg(?height)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isDouble());
    Assert.assertEquals(20.4d, literal.getDouble(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLCoreMedianShortWidest()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("15", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("20", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P4, Literal("30", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P5, Literal("35", XSD_SHORT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:median(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isShort());
    Assert.assertEquals(20, literal.getShort());
  }

  @Test public void TestSQWRLCoreMedianIntWidest()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("15", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("20", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P4, Literal("30", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P5, Literal("35", XSD_INT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:median(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isInt());
    Assert.assertEquals(20, literal.getInt());
  }

  @Test public void TestSQWRLCoreMedianLongWidest()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("15", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("20", XSD_LONG)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P4, Literal("30", XSD_LONG)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P5, Literal("35", XSD_LONG)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:median(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isLong());
    Assert.assertEquals(20L, literal.getLong());
  }

  @Test public void TestSQWRLCoreMedianFloatWidest()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("15", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("20", XSD_LONG)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P4, Literal("30.0", XSD_FLOAT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P5, Literal("35.0", XSD_FLOAT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:median(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isFloat());
    Assert.assertEquals(20.0f, literal.getFloat(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLCoreMedianDoubleWidest()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("15", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("20", XSD_LONG)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P4, Literal("30.0", XSD_FLOAT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P5, Literal("35.0", XSD_DOUBLE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:median(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isDouble());
    Assert.assertEquals(20.0f, literal.getDouble(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLCoreByteMedian() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_BYTE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("15", XSD_BYTE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("20", XSD_BYTE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P4, Literal("30", XSD_BYTE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P5, Literal("35", XSD_BYTE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:median(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isByte());
    Assert.assertEquals(20, literal.getByte());
  }

  @Test public void TestSQWRLCoreShortMedian() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("15", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("20", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P4, Literal("30", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P5, Literal("35", XSD_SHORT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:median(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isShort());
    Assert.assertEquals(20, literal.getShort());
  }

  @Test public void TestSQWRLCoreIntMedian() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("15", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("20", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P4, Literal("30", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P5, Literal("35", XSD_INT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:median(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isInt());
    Assert.assertEquals(20, literal.getInt());
  }

  @Test public void TestSQWRLCoreLongMedian() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_LONG)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("15", XSD_LONG)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("20", XSD_LONG)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P4, Literal("30", XSD_LONG)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P5, Literal("35", XSD_LONG)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:median(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isLong());
    Assert.assertEquals(20L, literal.getLong());
  }

  @Test public void TestSQWRLCoreFloatMedian() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("10.0", XSD_FLOAT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P2, Literal("15.3", XSD_FLOAT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P3, Literal("20.55", XSD_FLOAT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P4, Literal("30.32", XSD_FLOAT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P5, Literal("35.12", XSD_FLOAT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:median(?height)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isFloat());
    Assert.assertEquals(20.55f, literal.getFloat(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLCoreDoubleMedian() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("10.0", XSD_DOUBLE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P2, Literal("15.3", XSD_DOUBLE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P3, Literal("20.55", XSD_DOUBLE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P4, Literal("30.32", XSD_DOUBLE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P5, Literal("35.12", XSD_DOUBLE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:median(?height)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isDouble());
    Assert.assertEquals(20.55d, literal.getDouble(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLCoreByteMin() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_BYTE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_BYTE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_BYTE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:min(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isByte());
    Assert.assertEquals(10, literal.getByte());
  }

  @Test public void TestSQWRLCoreShortMin() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_SHORT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:min(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isShort());
    Assert.assertEquals(10, literal.getShort());
  }

  @Test public void TestSQWRLCoreIntMin() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_INT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:min(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isInt());
    Assert.assertEquals(10, literal.getInt());
  }

  @Test public void TestSQWRLCoreLongMin() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_LONG)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_LONG)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_LONG)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:min(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isLong());
    Assert.assertEquals(10L, literal.getLong());
  }

  @Test public void TestSQWRLCoreFloatMin() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("101.0", XSD_FLOAT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P2, Literal("102.3", XSD_FLOAT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P3, Literal("104.2", XSD_FLOAT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:min(?height)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isFloat());
    Assert.assertEquals(101.0f, literal.getFloat(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLCoreDoubleMin() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("101.0", XSD_DOUBLE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P2, Literal("102.3", XSD_DOUBLE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P3, Literal("104.2", XSD_DOUBLE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:min(?height)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isDouble());
    Assert.assertEquals(101.0d, literal.getDouble(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLCoreMixedTypesMin() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("23", XSD_BYTE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("44", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("55", XSD_INT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:min(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isByte());
    Assert.assertEquals(23, literal.getByte());
  }

  @Test(expected = SQWRLException.class) public void TestSQWRLCoreInvalidMin()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P1, Literal("Bob", XSD_STRING)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P2, Literal("Fred", XSD_STRING)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P3, Literal("Joe", XSD_STRING)));

    queryEngine.runSQWRLQuery("q1", "hasName(?p, ?name)-> sqwrl:min(?name)");
  }

  @Test public void TestSQWRLCoreByteMax() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_BYTE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_BYTE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_BYTE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:max(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isByte());
    Assert.assertEquals(30, literal.getByte());
  }

  @Test public void TestSQWRLCoreShortMax() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_SHORT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:max(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isShort());
    Assert.assertEquals(30, literal.getShort());
  }

  @Test public void TestSQWRLCoreIntMax() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_INT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:max(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isInt());
    Assert.assertEquals(30, literal.getInt());
  }

  @Test public void TestSQWRLCoreFloatMax() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("101.0", XSD_FLOAT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P2, Literal("102.3", XSD_FLOAT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P3, Literal("104.1", XSD_FLOAT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:max(?height)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isFloat());
    Assert.assertEquals(104.1f, literal.getFloat(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLCoreDoubleMax() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("101.0", XSD_DOUBLE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P2, Literal("102.3", XSD_DOUBLE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P3, Literal("104.1", XSD_DOUBLE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:max(?height)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isDouble());
    Assert.assertEquals(104.1d, literal.getDouble(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLCoreMixedTypesMax() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("23", XSD_BYTE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("44", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("55", XSD_INT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:max(?age)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral(0);
    Assert.assertTrue(literal.isInt());
    Assert.assertEquals(55, literal.getInt());
  }

  @Test public void TestSQWRLCoreOrderByByte() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("20", XSD_BYTE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("10", XSD_BYTE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_BYTE)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderBy(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p2", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isByte());
    Assert.assertEquals(10, result.getLiteral("age").getByte());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isByte());
    Assert.assertEquals(20, result.getLiteral("age").getByte());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p3", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isByte());
    Assert.assertEquals(30, result.getLiteral("age").getByte());
  }

  @Test public void TestSQWRLCoreOrderByShort() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("20", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("10", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_SHORT)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderBy(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p2", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isShort());
    Assert.assertEquals(10, result.getLiteral("age").getShort());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isShort());
    Assert.assertEquals(20, result.getLiteral("age").getShort());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p3", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isShort());
    Assert.assertEquals(30, result.getLiteral("age").getShort());
  }

  @Test public void TestSQWRLOrderByInt() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("20", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("10", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_INT)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderBy(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p2", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isInt());
    Assert.assertEquals(10, result.getLiteral("age").getInt());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isInt());
    Assert.assertEquals(20, result.getLiteral("age").getInt());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p3", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isInt());
    Assert.assertEquals(30, result.getLiteral("age").getInt());
  }

  @Test public void TestSQWRLOrderByLong() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("20", XSD_LONG)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("10", XSD_LONG)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_LONG)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderBy(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p2", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isLong());
    Assert.assertEquals(10, result.getLiteral("age").getLong());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isLong());
    Assert.assertEquals(20, result.getLiteral("age").getLong());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p3", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isLong());
    Assert.assertEquals(30, result.getLiteral("age").getLong());
  }

  @Test public void TestSQWRLOrderByFloat() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("200.0", XSD_FLOAT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P2, Literal("100.0", XSD_FLOAT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P3, Literal("300.0", XSD_FLOAT)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:select(?p, ?height) ^ sqwrl:orderBy(?height)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p2", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("height").isFloat());
    Assert.assertEquals(100.0f, result.getLiteral("height").getFloat(), IntegrationTestBase.DELTA);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("height").isFloat());
    Assert.assertEquals(200.0f, result.getLiteral("height").getFloat(), IntegrationTestBase.DELTA);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p3", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("height").isFloat());
    Assert.assertEquals(300.0f, result.getLiteral("height").getFloat(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLOrderByDouble() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("200.0", XSD_DOUBLE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P2, Literal("100.0", XSD_DOUBLE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P3, Literal("300.0", XSD_DOUBLE)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:select(?p, ?height) ^ sqwrl:orderBy(?height)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p2", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("height").isDouble());
    Assert.assertEquals(100.0d, result.getLiteral("height").getDouble(), IntegrationTestBase.DELTA);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("height").isDouble());
    Assert.assertEquals(200.0d, result.getLiteral("height").getDouble(), IntegrationTestBase.DELTA);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p3", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("height").isDouble());
    Assert.assertEquals(300.0d, result.getLiteral("height").getDouble(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSQWRLCoreOrderByString() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P1, Literal("Fred", XSD_STRING)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P2, Literal("Bob", XSD_STRING)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P3, Literal("Ann", XSD_STRING)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasName(?p, ?name)-> sqwrl:select(?p, ?name) ^ sqwrl:orderBy(?name)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p3");
    Assert.assertTrue(result.getLiteral("name").isString());
    Assert.assertEquals("Ann", result.getLiteral("name").getString());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p2", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("name").isString());
    Assert.assertEquals("Bob", result.getLiteral("name").getString());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("name").isString());
    Assert.assertEquals("Fred", result.getLiteral("name").getString());
  }

  @Test public void TestSQWRLCoreOrderByAnyURI()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException, URISyntaxException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String p1HomePage = "http://stanford.edu/~joe";
    String p2HomePage = "http://stanford.edu/~fred";
    String p3HomePage = "http://stanford.edu/~bob";

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HOMEPAGE, P1, Literal(p1HomePage, XSD_ANY_URI)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HOMEPAGE, P2, Literal(p2HomePage, XSD_ANY_URI)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HOMEPAGE, P3, Literal(p3HomePage, XSD_ANY_URI)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasHomePage(?p, ?homepage)-> sqwrl:select(?p, ?homepage) ^ sqwrl:orderBy(?homepage)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p3", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("homepage").isAnyURI());
    Assert.assertEquals(new URI(p3HomePage), result.getLiteral("homepage").getAnyURI());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p2", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("homepage").isAnyURI());
    Assert.assertEquals(new URI(p2HomePage), result.getLiteral("homepage").getAnyURI());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("homepage").isAnyURI());
    Assert.assertEquals(new URI(p1HomePage), result.getLiteral("homepage").getAnyURI());
  }

  @Test public void TestSQWRLOrderByDate() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_DOB, P1, Literal("1990-01-02", XSD_DATE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_DOB, P2, Literal("1989-10-10", XSD_DATE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_DOB, P3, Literal("1991-01-10", XSD_DATE)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasDOB(?p, ?dob)-> sqwrl:select(?p, ?dob) ^ sqwrl:orderBy(?dob)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p2", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("dob").isDate());
    Assert.assertEquals(new XSDDate("1989-10-10"), result.getLiteral("dob").getDate());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("dob").isDate());
    Assert.assertEquals(new XSDDate("1990-01-02"), result.getLiteral("dob").getDate());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p3", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("dob").isDate());
    Assert.assertEquals(new XSDDate("1991-01-10"), result.getLiteral("dob").getDate());
  }

  @Test public void TestSQWRLOrderByDateTime() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_TOB, P1, Literal("1990-01-02T10:10:09.2", XSD_DATETIME)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_TOB, P2, Literal("1989-10-10T09:08:08.3", XSD_DATETIME)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_TOB, P3, Literal("1991-01-10T11:11:11.11", XSD_DATETIME)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasTOB(?p, ?tob)-> sqwrl:select(?p, ?tob) ^ sqwrl:orderBy(?tob)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p2", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("tob").isDateTime());
    Assert.assertEquals(new XSDDateTime("1989-10-10T09:08:08.3"), result.getLiteral("tob").getDateTime());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("tob").isDateTime());
    Assert.assertEquals(new XSDDateTime("1990-01-02T10:10:09.2"), result.getLiteral("tob").getDateTime());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p3", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("tob").isDateTime());
    Assert.assertEquals(new XSDDateTime("1991-01-10T11:11:11.11"), result.getLiteral("tob").getDateTime());
  }

  @Test public void TestSQWRLOrderByTime() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_TOB, P1, Literal("10:10:09.2", XSD_TIME)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_TOB, P2, Literal("09:08:08.3", XSD_TIME)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_TOB, P3, Literal("11:11:11.11", XSD_TIME)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasTOB(?p, ?tob)-> sqwrl:select(?p, ?tob) ^ sqwrl:orderBy(?tob)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p2", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("tob").isTime());
    Assert.assertEquals(new XSDTime("09:08:08.3"), result.getLiteral("tob").getTime());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("tob").isTime());
    Assert.assertEquals(new XSDTime("10:10:09.2"), result.getLiteral("tob").getTime());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p3", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("tob").isTime());
    Assert.assertEquals(new XSDTime("11:11:11.11"), result.getLiteral("tob").getTime());
  }

  @Test public void TestSQWRLOrderByDuration() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("P23Y", XSD_DURATION)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("P21Y", XSD_DURATION)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("P30Y", XSD_DURATION)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderBy(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p2", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isDuration());
    Assert.assertEquals(new XSDDuration("P21Y"), result.getLiteral("age").getDuration());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isDuration());
    Assert.assertEquals(new XSDDuration("P23Y"), result.getLiteral("age").getDuration());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p3", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isDuration());
    Assert.assertEquals(new XSDDuration("P30Y"), result.getLiteral("age").getDuration());
  }

  @Test public void TestSQWRLOrderByDescendingByte()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("20", XSD_BYTE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("10", XSD_BYTE)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_BYTE)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderByDescending(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p3", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isByte());
    Assert.assertEquals(30, result.getLiteral("age").getByte());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isByte());
    Assert.assertEquals(20, result.getLiteral("age").getByte());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p2", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isByte());
    Assert.assertEquals(10, result.getLiteral("age").getByte());
  }

  @Test public void TestSQWRLOrderByDescendingShort()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("20", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("10", XSD_SHORT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_SHORT)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderByDescending(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p3", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isShort());
    Assert.assertEquals(30, result.getLiteral("age").getShort());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isShort());
    Assert.assertEquals(20, result.getLiteral("age").getShort());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p2", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isShort());
    Assert.assertEquals(10, result.getLiteral("age").getShort());
  }

  @Test public void TestSQWRLOrderByDescendingInt()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("20", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("10", XSD_INT)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_INT)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderByDescending(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p3", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isInt());
    Assert.assertEquals(30, result.getLiteral("age").getInt());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isInt());
    Assert.assertEquals(20, result.getLiteral("age").getInt());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p2", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isInt());
    Assert.assertEquals(10, result.getLiteral("age").getInt());
  }

  @Test public void TestSQWRLOrderByDescendingLong()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("20", XSD_LONG)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("10", XSD_LONG)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_LONG)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderByDescending(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p3", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isLong());
    Assert.assertEquals(30L, result.getLiteral("age").getLong());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isLong());
    Assert.assertEquals(20L, result.getLiteral("age").getLong());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p2", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("age").isLong());
    Assert.assertEquals(10L, result.getLiteral("age").getLong());
  }

  @Test public void TestSQWRLOrderByDescendingString()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P1, Literal("Fred", XSD_STRING)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P2, Literal("Bob", XSD_STRING)));
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P3, Literal("Ann", XSD_STRING)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasName(?p, ?name)-> sqwrl:select(?p, ?name) ^ sqwrl:orderByDescending(?name)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("name").isString());
    Assert.assertEquals("Fred", result.getLiteral("name").getString());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p2", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("name").isString());
    Assert.assertEquals("Bob", result.getLiteral("name").getString());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
    Assert.assertEquals("p3", result.getNamedIndividual("p").getShortName());
    Assert.assertTrue(result.getLiteral("name").isString());
    Assert.assertEquals("Ann", result.getLiteral("name").getString());
  }

  @Test public void TestSQWRLInvalidColumnName() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("only string literals allowed as column names");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", " -> sqwrl:select(0) ^ sqwrl:columnNames(23)");
  }

  @Test public void TestSQWRLInvalidOrderByArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("only variables allowed for ordered columns");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", " -> sqwrl:select(0) ^ sqwrl:orderBy(23)");
  }

  @Test public void TestSQWRLInvalidOrderByDescendingArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("only variables allowed for ordered columns");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", " -> sqwrl:select(0) ^ sqwrl:orderByDescending(23)");
  }

  @Test public void TestSQWRLInvalidOrderByClauseWithoutSelectClause()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("ordered clause cannot be used without a select clause");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", "owl:Thing(?x) -> sqwrl:orderBy(?x)");
  }

  @Test public void TestSQWRLInvalidOrderByDescendingClauseWithoutSelectClause()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("ordered clause cannot be used without a select clause");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", "owl:Thing(?x) -> sqwrl:orderByDescending(?x)");
  }

  @Test public void TestSQWRLInvalidFirstNClauseUsedWithoutASelectClause()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("slicing operator used without a select clause");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", " -> sqwrl:firstN(1)");
  }

  @Test public void TestSQWRLInvalidNthClauseUsedWithoutASelectClause()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("slicing operator used without a select clause");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", " -> sqwrl:nth(1)");
  }

  @Test public void TestSQWRLInvalidNotNthClauseUsedWithoutASelectClause()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("slicing operator used without a select clause");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", " -> sqwrl:notNth(1)");
  }

  @Test public void TestSQWRLInvalidNthGreatestClauseUsedWithoutASelectClause()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("slicing operator used without a select clause");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", " -> sqwrl:nthGreatest(1)");
  }

  @Test public void TestSQWRLInvalidNthLastClauseUsedWithoutASelectClause()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("slicing operator used without a select clause");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", " -> sqwrl:nthLast(1)");
  }

  @Test public void TestSQWRLInvalidNotNthLastClauseUsedWithoutASelectClause()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("slicing operator used without a select clause");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", " -> sqwrl:notNthLast(1)");
  }

  @Test public void TestSQWRLInvalidNotNthGreatestClauseUsedWithoutASelectClause()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("slicing operator used without a select clause");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", " -> sqwrl:notNthGreatest(1)");
  }

  @Test public void TestSQWRLInvalidNotFirstNClauseUsedWithoutASelectClause()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("slicing operator used without a select clause");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", " -> sqwrl:notFirstN(1)");
  }

  @Test public void TestSQWRLInvalidNotLastNClauseUsedWithoutASelectClause()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("slicing operator used without a select clause");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", " -> sqwrl:notLastN(1)");
  }

  @Test public void TestSQWRLInvalidNotGreatestNClauseUsedWithoutASelectClause()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("slicing operator used without a select clause");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", " -> sqwrl:notGreatestN(1)");
  }

  @Test public void TestSQWRLInvalidNotLeastNClauseUsedWithoutASelectClause()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("slicing operator used without a select clause");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", " -> sqwrl:notLeastN(1)");
  }

  @Test public void TestSQWRLInvalidLastNClauseUsedWithoutASelectClause()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("slicing operator used without a select clause");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", " -> sqwrl:lastN(1)");
  }

  @Test public void TestSQWRLInvalidLeastNClauseUsedWithoutASelectClause()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("slicing operator used without a select clause");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", " -> sqwrl:leastN(1)");
  }

  @Test public void TestSQWRLInvalidGreatestNClauseUsedWithoutASelectClause()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("slicing operator used without a select clause");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", " -> sqwrl:greatestN(1)");
  }

  @Test public void TestSQWRLInvalidNthSliceClauseUsedWithoutASelectClause()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("slicing operator used without a select clause");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", " -> sqwrl:nthSlice(1, 1)");
  }

  @Test public void TestSQWRLInvalidNthLastSliceClauseUsedWithoutASelectClause()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("slicing operator used without a select clause");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", " -> sqwrl:nthLastSlice(1, 1)");
  }

  @Test public void TestSQWRLInvalidNthGreatestSliceClauseUsedWithoutASelectClause()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("slicing operator used without a select clause");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", " -> sqwrl:nthGreatestSlice(1, 1)");
  }

  @Test public void TestSQWRLInvalidNotNthGreatestSliceClauseUsedWithoutASelectClause()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("slicing operator used without a select clause");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", " -> sqwrl:notNthGreatestSlice(1, 1)");
  }

  @Test public void TestSQWRLInvalidNotNthLastSliceClauseUsedWithoutASelectClause()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("slicing operator used without a select clause");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", " -> sqwrl:notNthLastSlice(1, 1)");
  }

  @Test public void TestSQWRLInvalidNotNthSliceClauseUsedWithoutASelectClause()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("slicing operator used without a select clause");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", " -> sqwrl:notNthSlice(1, 1)");
  }

  @Test public void TestSQWRLInvalidSliceArgumentWithoutOrderClause()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("slicing operator used without an order clause");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", "owl:Thing(?x) -> sqwrl:select(?x) ^ sqwrl:nth(1)");
  }

  @Test public void TestSQWRLInvalidSliceArgumentType()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("expecting xsd:int or xsd:integer argument for slicing operator sqwrl:nth");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", "owl:Thing(?x) -> sqwrl:select(?x) ^ sqwrl:orderBy(?x) ^ sqwrl:nth(33.3)");
  }

  @Test public void TestSQWRLInvalidNonPositiveSliceArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("nth argument for slicing operator sqwrl:nth must be a positive xsd:int or xsd:integer");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", "owl:Thing(?x) -> sqwrl:select(?x) ^ sqwrl:orderBy(?x) ^ sqwrl:nth(-3)");
  }

  @Test public void TestSQWRLInvalidSliceSizeArgumentType()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("expecting xsd:int or xsd:integer argument for slicing operator sqwrl:nth");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", "owl:Thing(?x) -> sqwrl:select(?x) ^ sqwrl:orderBy(?x) ^ sqwrl:nth(1, 22.3)");
  }

  @Test public void TestSQWRLInvalidNonPositiveSliceSizeArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown
      .expectMessage("slice size argument to slicing operator sqwrl:nth must be a positive xsd:int or xsd:integer");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", "owl:Thing(?x) -> sqwrl:select(?x) ^ sqwrl:orderBy(?x) ^ sqwrl:nth(1, -4)");
  }

  @Test public void TestSQWRLInvalidSliceArgumentNumber()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("slicing operator sqwrl:nth expecting a maximum of 2 arguments");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", "owl:Thing(?x) -> sqwrl:select(?x) ^ sqwrl:orderBy(?x) ^ sqwrl:nth(1, 1, 1)");
  }

  @Test public void TestSQWRLInvalidOrderByArgumentType()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    this.thrown.expect(SQWRLException.class);
    this.thrown.expectMessage("only variables allowed for ordered columns");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.createSQWRLQuery("q1", "owl:Thing(?x) -> sqwrl:select(?x) ^ sqwrl:orderBy(3)");
  }
}
