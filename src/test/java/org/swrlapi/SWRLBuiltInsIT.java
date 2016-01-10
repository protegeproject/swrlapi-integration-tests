package org.swrlapi;

import org.junit.Assert;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.literal.XSDDateTime;
import org.swrlapi.literal.XSDDuration;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.test.IntegrationTestBase;

/**
 * NOTE: All tests are designed for parallel execution.
 */
public class SWRLBuiltInsIT extends IntegrationTestBase
{
  @Test public void TestSWRLBuiltInsBasicInvocation()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:add(4, 2, 2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLBuiltInsByteBoundResult()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String query = "swrlb:add(?r, \"2\"^^xsd:byte, \"2\"^^xsd:byte) -> sqwrl:select(?r)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral("r");
    Assert.assertTrue(literal.isByte());
    Assert.assertEquals(4, literal.getByte());
  }

  @Test public void TestSWRLBuiltInsShortBoundResult()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String query = "swrlb:add(?r, \"2\"^^xsd:short, \"2\"^^xsd:short) -> sqwrl:select(?r)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral("r");
    Assert.assertTrue(literal.isShort());
    Assert.assertEquals(4, literal.getShort());
  }

  @Test public void TestSWRLBuiltInsIntBoundResult()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:add(?r, 2, 2) -> sqwrl:select(?r)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral("r");
    Assert.assertTrue(literal.isInt());
    Assert.assertEquals(4, literal.getInt());
  }

  @Test public void TestSWRLBuiltInsFloatBoundResult()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:add(?r, 2.1, 2.0) -> sqwrl:select(?r)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral("r");
    Assert.assertTrue(literal.isFloat());
    Assert.assertEquals(4.1f, literal.getFloat(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSWRLBuiltInsLongBoundResult()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String query = "swrlb:add(?r, \"2\"^^xsd:long, \"2\"^^xsd:long) -> sqwrl:select(?r)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral("r");
    Assert.assertTrue(literal.isLong());
    Assert.assertEquals(4, literal.getLong());
  }

  @Test public void TestSWRLBuiltInsDoubleBoundResult()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String query = "swrlb:add(?r, \"2.0\"^^xsd:double, \"2.0\"^^xsd:double) -> sqwrl:select(?r)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral("r");
    Assert.assertTrue(literal.isDouble());
    Assert.assertEquals(4.0d, literal.getDouble(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSWRLBuiltInsBooleanBoundTrueResult()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:booleanNot(?r, false) -> sqwrl:select(?r)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral("r");
    Assert.assertTrue(literal.isBoolean());
    Assert.assertEquals(true, literal.getBoolean());
  }

  @Test public void TestSWRLBuiltInsBooleanBoundFalseResult()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:booleanNot(?r, true) -> sqwrl:select(?r)");

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral("r");
    Assert.assertTrue(literal.isBoolean());
    Assert.assertEquals(false, literal.getBoolean());
  }

  @Test public void TestSWRLBuiltInsDateTimeBoundResult()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "temporal:add(?r, \"1999-11-01T10:00:01.0\"^^xsd:dateTime, 0, \"Years\") -> sqwrl:select(?r)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("r").isDateTime());
    Assert.assertEquals(new XSDDateTime("1999-11-01T10:00:01.0"), result.getLiteral("r").getDateTime());
  }

  @Test public void TestSWRLBuiltInsDurationBoundResult()
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

  @Test public void TestSWRLBuiltInsCascadingShortVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String query = "swrlb:add(?x, \"2\"^^xsd:short, \"2\"^^xsd:short) ^ "
      + "swrlb:multiply(?y, ?x, \"2\"^^xsd:short) -> sqwrl:select(?y)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("y").isShort());
    Assert.assertEquals(8, result.getLiteral("y").getShort());
  }

  @Test public void TestSWRLBuiltInsCascadingIntVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String query = "swrlb:add(?x, 2, 2) ^ swrlb:multiply(?y, ?x, 2) -> sqwrl:select(?y)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("y").isInt());
    Assert.assertEquals(8, result.getLiteral("y").getInt());
  }

  @Test public void TestSWRLBuiltInsCascadingLongVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String query = "swrlb:add(?x, \"2\"^^xsd:long, \"2\"^^xsd:long) ^ "
      + "swrlb:multiply(?y, ?x, \"2\"^^xsd:long) -> sqwrl:select(?y)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("y").isLong());
    Assert.assertEquals(8L, result.getLiteral("y").getLong());
  }

  @Test public void TestSWRLBuiltInsCascadingFloatVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String query = "swrlb:add(?x, 2.0, 2.0) ^ swrlb:multiply(?y, ?x, 2.0) -> sqwrl:select(?y)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("y").isFloat());
    Assert.assertEquals(8.0f, result.getLiteral("y").getFloat(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSWRLBuiltInsCascadingDoubleVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String query = "swrlb:add(?x, \"2.0\"^^xsd:double, \"2.0\"^^xsd:double) ^ "
      + "swrlb:multiply(?y, ?x, \"2.0\"^^xsd:double) -> sqwrl:select(?y)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("y").isDouble());
    Assert.assertEquals(8.0d, result.getLiteral("y").getDouble(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSWRLBuiltInsCascadingBooleanVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String query = "swrlb:booleanNot(?x, true) ^ swrlb:booleanNot(?y, ?x) -> sqwrl:select(?y)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("y").isBoolean());
    Assert.assertEquals(true, result.getLiteral("y").getBoolean());
  }

  @Test public void TestSWRLBuiltInsCascadingStringVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String query = "swrlb:stringConcat(?x, \"The\", \"Cat\") ^ swrlb:stringConcat(?y, ?x, \"Sat\") -> sqwrl:select(?y)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("y").isString());
    Assert.assertEquals("TheCatSat", result.getLiteral("y").getString());
  }
}
