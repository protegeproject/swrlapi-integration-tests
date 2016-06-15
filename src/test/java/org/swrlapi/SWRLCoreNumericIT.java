package org.swrlapi;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.test.IntegrationTestBase;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;

/**
 * NOTE: All tests are designed for parallel execution.
 */
public class SWRLCoreNumericIT extends IntegrationTestBase
{
  private static final OWLNamedIndividual P1 = NamedIndividual(iri("p1"));
  private static final OWLNamedIndividual P2 = NamedIndividual(iri("p2"));
  private static final OWLDataProperty YEAR_OFFSET_TO_BIRTH = DataProperty(iri("yearOffsetToBirth"));
  private static final OWLDataProperty HAS_AGE = DataProperty(iri("hasAge"));
  private static final OWLDataProperty HAS_HEIGHT_IN_CM = DataProperty(iri("hasHeightInCM"));
  private static final OWLDataProperty HAS_HEIGHT = DataProperty(iri("hasHeight"));
  private static final OWLDataProperty HEIGHT_OFFET_IN_CM = DataProperty(iri("heightOffsetInCM"));

  @Rule public final ExpectedException thrown = ExpectedException.none();

  @Test public void TestSWRLCoreNumericByteLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_BYTE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, \"42\"^^xsd:byte) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericMinusByteLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(YEAR_OFFSET_TO_BIRTH, P1, Literal("-42", XSD_BYTE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "yearOffsetToBirth(p1, \"-42\"^^xsd:byte) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericByteLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_BYTE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) -> sqwrl:select(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("age").isByte());
    Assert.assertEquals(42, result.getLiteral("age").getByte());
  }

  @Test public void TestSWRLCoreNumericCascadingByteVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("22", XSD_BYTE)),
      DataPropertyAssertion(HAS_AGE, P2, Literal("22", XSD_BYTE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) ^ hasAge(p2, ?age) -> sqwrl:select(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("age").isByte());
    Assert.assertEquals(22, result.getLiteral("age").getByte());
  }

  @Test public void TestSWRLCoreNumericInvalidByteLiteral()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    thrown.expect(SWRLParseException.class);
    thrown.expectMessage("literal value 'x42' is not a valid http://www.w3.org/2001/XMLSchema#byte");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"x42\"^^xsd:byte)");
  }

  @Test public void TestSWRLCoreNumericShortLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_SHORT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, \"42\"^^xsd:short) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericMinusShortLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(YEAR_OFFSET_TO_BIRTH, P1, Literal("-42", XSD_SHORT)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "yearOffsetToBirth(p1, \"-42\"^^xsd:short) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericShortLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_SHORT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) -> sqwrl:select(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("age").isShort());
    Assert.assertEquals(42, result.getLiteral("age").getShort());
  }

  @Test public void TestSWRLCoreNumericMinusShortLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(YEAR_OFFSET_TO_BIRTH, P1, Literal("-42", XSD_SHORT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "yearOffsetToBirth(p1, ?offset) -> sqwrl:select(?offset)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("offset").isShort());
    Assert.assertEquals(-42, result.getLiteral("offset").getShort());
  }

  @Test public void TestSWRLCoreNumericCascadingShortVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("22", XSD_SHORT)),
      DataPropertyAssertion(HAS_AGE, P2, Literal("22", XSD_SHORT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) ^ hasAge(p2, ?age) -> sqwrl:select(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("age").isShort());
    Assert.assertEquals(22, result.getLiteral("age").getShort());
  }

  @Test public void TestSWRLCoreNumericInvalidShortLiteral()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    thrown.expect(SWRLParseException.class);
    thrown.expectMessage("literal value 'x42' is not a valid http://www.w3.org/2001/XMLSchema#short");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"x42\"^^xsd:short)");
  }

  @Test public void TestSWRLCoreNumericRawIntLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_INT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, 42) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericRawMinusIntLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(YEAR_OFFSET_TO_BIRTH, P1, Literal("-42", XSD_INT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "yearOffsetToBirth(p1, -42) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericQualifiedIntLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_INT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, \"42\"^^xsd:int) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericQualifiedMinusIntLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(YEAR_OFFSET_TO_BIRTH, P1, Literal("-42", XSD_INT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "yearOffsetToBirth(p1, \"-42\"^^xsd:int) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericIntLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_INT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) -> sqwrl:select(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("age").isInt());
    Assert.assertEquals(42, result.getLiteral("age").getInt());
  }

  @Test public void TestSWRLCoreNumericMinusIntLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(YEAR_OFFSET_TO_BIRTH, P1, Literal("-42", XSD_INT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "yearOffsetToBirth(p1, ?offset) -> sqwrl:select(?offset)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("offset").isInt());
    Assert.assertEquals(-42, result.getLiteral("offset").getInt());
  }

  @Test public void TestSWRLCoreNumericCascadingIntVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("22", XSD_INT)),
      DataPropertyAssertion(HAS_AGE, P2, Literal("22", XSD_INT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) ^ hasAge(p2, ?age) -> sqwrl:select(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("age").isInt());
    Assert.assertEquals(22, result.getLiteral("age").getInt());
  }

  @Test public void TestSWRLCoreNumericInvalidIntLiteral()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    thrown.expect(SWRLParseException.class);
    thrown.expectMessage("literal value 'x42' is not a valid http://www.w3.org/2001/XMLSchema#int");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"x42\"^^xsd:int)");
  }

  @Test public void TestSWRLCoreNumericLongLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_LONG)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, \"42\"^^xsd:long) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericMinusLongLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(YEAR_OFFSET_TO_BIRTH, P1, Literal("-42", XSD_LONG)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "yearOffsetToBirth(p1, \"-42\"^^xsd:long) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericLongLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_LONG)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) -> sqwrl:select(p1, ?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual(0).getShortName());
    Assert.assertTrue(result.getLiteral("age").isLong());
    Assert.assertEquals(42L, result.getLiteral("age").getLong());
  }

  @Test public void TestSWRLCoreNumericMinusLongLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(YEAR_OFFSET_TO_BIRTH, P1, Literal("-42", XSD_LONG)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "yearOffsetToBirth(p1, ?offset) -> sqwrl:select(?offset)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("offset").isLong());
    Assert.assertEquals(-42L, result.getLiteral("offset").getLong());
  }

  @Test public void TestSWRLCoreNumericCascadingLongVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("22", XSD_LONG)),
      DataPropertyAssertion(HAS_AGE, P2, Literal("22", XSD_LONG)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) ^ hasAge(p2, ?age) -> sqwrl:select(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("age").isLong());
    Assert.assertEquals(22L, result.getLiteral("age").getLong());
  }

  @Test public void TestSWRLCoreNumericInvalidLongLiteral()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    thrown.expect(SWRLParseException.class);
    thrown.expectMessage("literal value 'x42' is not a valid http://www.w3.org/2001/XMLSchema#long");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"x42\"^^xsd:long)");
  }

  @Test public void TestSWRLCoreNumericRawFloatLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT_IN_CM, P1, Literal("180.0", XSD_FLOAT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHeightInCM(p1, 180.0) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericRawMinusFloatLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HEIGHT_OFFET_IN_CM, P1, Literal("-180.0", XSD_FLOAT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "heightOffsetInCM(p1, -180.0) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericQualifiedFloatLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT_IN_CM, P1, Literal("177.0", XSD_FLOAT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHeightInCM(p1, \"177.0\"^^xsd:float) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericQualifiedMinusFloatLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HEIGHT_OFFET_IN_CM, P1, Literal("-177.1", XSD_FLOAT)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "heightOffsetInCM(p1, \"-177.1\"^^xsd:float) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericFloatLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT_IN_CM, P1, Literal("177.0", XSD_FLOAT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHeightInCM(p1, ?height) -> sqwrl:select(?height)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("height").isFloat());
    Assert.assertEquals(177.0f, result.getLiteral("height").getFloat(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSWRLCoreNumericMinusFloatLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HEIGHT_OFFET_IN_CM, P1, Literal("-177.0", XSD_FLOAT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "heightOffsetInCM(p1, ?offset) -> sqwrl:select(?offset)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("offset").isFloat());
    Assert.assertEquals(-177.0f, result.getLiteral("offset").getFloat(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSWRLCoreNumericCascadingFloatVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("122.0", XSD_FLOAT)),
      DataPropertyAssertion(HAS_HEIGHT, P2, Literal("122.0", XSD_FLOAT)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasHeight(p1, ?height) ^ hasHeight(p2, ?height) -> sqwrl:select(?height)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("height").isFloat());
    Assert.assertEquals(122.0f, result.getLiteral("height").getFloat(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSWRLCoreNumericInvalidFloatLiteral()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    thrown.expect(SWRLParseException.class);
    thrown.expectMessage("literal value 'x42.0' is not a valid http://www.w3.org/2001/XMLSchema#float");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"x42.0\"^^xsd:float)");
  }

  @Test public void TestSWRLCoreNumericDoubleLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT_IN_CM, P1, Literal("177.0", XSD_DOUBLE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHeightInCM(p1, \"177.0\"^^xsd:double) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericMinusDoubleLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HEIGHT_OFFET_IN_CM, P1, Literal("-177.1", XSD_DOUBLE)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "heightOffsetInCM(p1, \"-177.1\"^^xsd:double) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericDoubleLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT_IN_CM, P1, Literal("177.0", XSD_DOUBLE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHeightInCM(p1, ?height) -> sqwrl:select(?height)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("height").isDouble());
    Assert.assertEquals(177.0d, result.getLiteral("height").getDouble(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSWRLCoreNumericMinusDoubleLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HEIGHT_OFFET_IN_CM, P1, Literal("-177.0", XSD_DOUBLE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "heightOffsetInCM(p1, ?offset) -> sqwrl:select(?offset)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("offset").isDouble());
    Assert.assertEquals(-177.0d, result.getLiteral("offset").getDouble(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSWRLCoreNumericCascadingDoubleVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("122.0", XSD_DOUBLE)),
      DataPropertyAssertion(HAS_HEIGHT, P2, Literal("122.0", XSD_DOUBLE)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasHeight(p1, ?age) ^ hasHeight(p2, ?age) -> sqwrl:select(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("age").isDouble());
    Assert.assertEquals(122.0d, result.getLiteral("age").getDouble(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSWRLCoreNumericInvalidDoubleLiteral()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    thrown.expect(SWRLParseException.class);
    thrown.expectMessage("literal value 'x42.0' is not a valid http://www.w3.org/2001/XMLSchema#double");

    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"x42.0\"^^xsd:double)");
  }

  @Test public void TestSWRLCoreNumericIntegerLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_INTEGER)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, \"42\"^^xsd:integer) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericMinusIntegerLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(YEAR_OFFSET_TO_BIRTH, P1, Literal("-42", XSD_INTEGER)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "yearOffsetToBirth(p1, \"-42\"^^xsd:integer) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericIntegerLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_INTEGER)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) -> sqwrl:select(p1, ?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual(0).getShortName());
    Assert.assertTrue(result.getLiteral("age").isInteger());
    Assert.assertEquals(new BigInteger("42"), result.getLiteral("age").getInteger());
  }

  @Test public void TestSWRLCoreNumericMinusIntegerLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(YEAR_OFFSET_TO_BIRTH, P1, Literal("-42", XSD_INTEGER)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "yearOffsetToBirth(p1, ?offset) -> sqwrl:select(?offset)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("offset").isInteger());
    Assert.assertEquals(new BigInteger("-42"), result.getLiteral("offset").getInteger());
  }

  @Test public void TestSWRLCoreNumericCascadingIntegerVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("22", XSD_INTEGER)),
      DataPropertyAssertion(HAS_AGE, P2, Literal("22", XSD_INTEGER)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) ^ hasAge(p2, ?age) -> sqwrl:select(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("age").isInteger());
    Assert.assertEquals(new BigInteger("22"), result.getLiteral("age").getInteger());
  }

  @Test public void TestSWRLCoreNumericDecimalLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42.0", XSD_DECIMAL)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, \"42.0\"^^xsd:decimal) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericMinusDecimalLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(YEAR_OFFSET_TO_BIRTH, P1, Literal("-42.0", XSD_DECIMAL)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "yearOffsetToBirth(p1, \"-42.0\"^^xsd:decimal) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericDecimalLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42.0", XSD_DECIMAL)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) -> sqwrl:select(p1, ?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual(0).getShortName());
    Assert.assertTrue(result.getLiteral("age").isDecimal());
    Assert.assertEquals(new BigDecimal("42.0"), result.getLiteral("age").getDecimal());
  }

  @Test public void TestSWRLCoreNumericMinusDecimalLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(YEAR_OFFSET_TO_BIRTH, P1, Literal("-42.0", XSD_DECIMAL)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "yearOffsetToBirth(p1, ?offset) -> sqwrl:select(?offset)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("offset").isDecimal());
    Assert.assertEquals(new BigDecimal("-42.0"), result.getLiteral("offset").getDecimal());
  }

  @Test public void TestSWRLCoreNumericCascadingDecimalVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("22.0", XSD_DECIMAL)),
      DataPropertyAssertion(HAS_AGE, P2, Literal("22.0", XSD_DECIMAL)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) ^ hasAge(p2, ?age) -> sqwrl:select(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("age").isDecimal());
    Assert.assertEquals(new BigDecimal("22.0"), result.getLiteral("age").getDecimal());
  }






  @Test public void TestSWRLCoreNumericNonNegativeIntegerLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_NON_NEGATIVE_INTEGER)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(p1, \"42\"^^xsd:nonNegativeInteger) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericNonNegativeIntegerLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_NON_NEGATIVE_INTEGER)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) -> sqwrl:select(p1, ?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual(0).getShortName());
    Assert.assertTrue(result.getLiteral("age").isNonNegativeInteger());
    Assert.assertEquals(new BigInteger("42"), result.getLiteral("age").getNonNegativeInteger());
  }









  @Test public void TestSWRLCoreNumericNonPositiveIntegerLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("-42", XSD_NON_POSITIVE_INTEGER)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasAge(p1, \"-42\"^^xsd:nonPositiveInteger) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericNonPositiveIntegerLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("-42", XSD_NON_POSITIVE_INTEGER)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) -> sqwrl:select(p1, ?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual(0).getShortName());
    Assert.assertTrue(result.getLiteral("age").isNonPositiveInteger());
    Assert.assertEquals(new BigInteger("-42"), result.getLiteral("age").getNonPositiveInteger());
  }

  @Test public void TestSWRLCoreNumericNegativeIntegerLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("-42", XSD_NEGATIVE_INTEGER)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, \"-42\"^^xsd:negativeInteger) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericNegativeIntegerLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("-42", XSD_NEGATIVE_INTEGER)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) -> sqwrl:select(p1, ?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual(0).getShortName());
    Assert.assertTrue(result.getLiteral("age").isNegativeInteger());
    Assert.assertEquals(new BigInteger("-42"), result.getLiteral("age").getNegativeInteger());
  }

  @Test public void TestSWRLCoreNumericPositiveIntegerLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_POSITIVE_INTEGER)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, \"42\"^^xsd:positiveInteger) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericPositiveIntegerLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_POSITIVE_INTEGER)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) -> sqwrl:select(p1, ?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual(0).getShortName());
    Assert.assertTrue(result.getLiteral("age").isPositiveInteger());
    Assert.assertEquals(new BigInteger("42"), result.getLiteral("age").getPositiveInteger());
  }

  @Test public void TestSWRLCoreNumericCascadingPositiveIntegerVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("22", XSD_POSITIVE_INTEGER)),
      DataPropertyAssertion(HAS_AGE, P2, Literal("22", XSD_POSITIVE_INTEGER)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) ^ hasAge(p2, ?age) -> sqwrl:select(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("age").isPositiveInteger());
    Assert.assertEquals(new BigInteger("22"), result.getLiteral("age").getPositiveInteger());
  }

  @Test public void TestSWRLCoreNumericUnsignedLongLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_UNSIGNED_LONG)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, \"42\"^^xsd:unsignedLong) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericUnsignedLongLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_UNSIGNED_LONG)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) -> sqwrl:select(p1, ?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual(0).getShortName());
    Assert.assertTrue(result.getLiteral("age").isUnsignedLong());
    Assert.assertEquals(42L, result.getLiteral("age").getUnsignedLong());
  }

  @Test public void TestSWRLCoreNumericCascadingUnsignedLongVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("22", XSD_UNSIGNED_LONG)),
      DataPropertyAssertion(HAS_AGE, P2, Literal("22", XSD_UNSIGNED_LONG)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) ^ hasAge(p2, ?age) -> sqwrl:select(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("age").isUnsignedLong());
    Assert.assertEquals(22L, result.getLiteral("age").getUnsignedLong());
  }

  @Test public void TestSWRLCoreNumericUnsignedIntLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_UNSIGNED_INT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, \"42\"^^xsd:unsignedInt) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericUnsignedIntLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_UNSIGNED_INT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) -> sqwrl:select(p1, ?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual(0).getShortName());
    Assert.assertTrue(result.getLiteral("age").isUnsignedInt());
    Assert.assertEquals(42L, result.getLiteral("age").getUnsignedInt());
  }

  @Test public void TestSWRLCoreNumericCascadingUnsignedIntVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("22", XSD_UNSIGNED_INT)),
      DataPropertyAssertion(HAS_AGE, P2, Literal("22", XSD_UNSIGNED_INT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) ^ hasAge(p2, ?age) -> sqwrl:select(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("age").isUnsignedInt());
    Assert.assertEquals(22L, result.getLiteral("age").getUnsignedInt());
  }

  @Test public void TestSWRLCoreNumericUnsignedShortLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_UNSIGNED_SHORT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, \"42\"^^xsd:unsignedShort) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericUnsignedShortLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_UNSIGNED_SHORT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) -> sqwrl:select(p1, ?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual(0).getShortName());
    Assert.assertTrue(result.getLiteral("age").isUnsignedShort());
    Assert.assertEquals(42, result.getLiteral("age").getUnsignedShort());
  }

  @Test public void TestSWRLCoreNumericCascadingUnsignedShortVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("22", XSD_UNSIGNED_SHORT)),
      DataPropertyAssertion(HAS_AGE, P2, Literal("22", XSD_UNSIGNED_SHORT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) ^ hasAge(p2, ?age) -> sqwrl:select(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("age").isUnsignedShort());
    Assert.assertEquals(22, result.getLiteral("age").getUnsignedShort());
  }

  @Test public void TestSWRLCoreNumericUnsignedByteLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_UNSIGNED_BYTE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, \"42\"^^xsd:unsignedByte) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreNumericUnsignedByteLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_UNSIGNED_BYTE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) -> sqwrl:select(p1, ?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual(0).getShortName());
    Assert.assertTrue(result.getLiteral("age").isUnsignedByte());
    Assert.assertEquals(42, result.getLiteral("age").getUnsignedByte());
  }

  @Test public void TestSWRLCoreNumericCascadingUnsignedByteVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("22", XSD_UNSIGNED_BYTE)),
      DataPropertyAssertion(HAS_AGE, P2, Literal("22", XSD_UNSIGNED_BYTE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) ^ hasAge(p2, ?age) -> sqwrl:select(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("age").isUnsignedByte());
    Assert.assertEquals(22, result.getLiteral("age").getUnsignedByte());
  }

}
