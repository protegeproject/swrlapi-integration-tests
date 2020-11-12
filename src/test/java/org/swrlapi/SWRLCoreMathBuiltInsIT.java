package org.swrlapi;

import org.junit.Assert;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
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

/**
 * NOTE: All tests are designed for parallel execution.
 *
 * @see org.swrlapi.builtins.swrlb.SWRLBuiltInLibraryImpl
 */
public class SWRLCoreMathBuiltInsIT extends IntegrationTestBase
{
  @Test public void TestSWRLCoreMathAddBuiltInWithByte()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String query = "swrlb:add(?r, \"2\"^^xsd:byte, \"2\"^^xsd:byte) -> sqwrl:select(?r)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("r").isByte());
    Assert.assertEquals(4, result.getLiteral("r").getByte());
  }

  @Test public void TestSWRLCoreMathAddBuiltInWithShort()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String query = "swrlb:add(?r, \"2\"^^xsd:short, \"2\"^^xsd:short) -> sqwrl:select(?r)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("r").isShort());
    Assert.assertEquals(4, result.getLiteral("r").getShort());
  }

  @Test public void TestSWRLCoreMathAddBuiltInWithInt()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "swrlb:add(?r, \"2\"^^xsd:int, \"2\"^^xsd:int) -> sqwrl:select(?r)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("r").isInt());
    Assert.assertEquals(4, result.getLiteral("r").getInt());
  }

  @Test public void TestSWRLCoreMathAddBuiltInWithInteger()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:add(?r, 2, 2) -> sqwrl:select(?r)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("r").isInteger());
    Assert.assertEquals(BigInteger.valueOf(4), result.getLiteral("r").getInteger());
  }

  @Test public void TestSWRLCoreMathAddBuiltInWithFloat()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "swrlb:add(?r, \"2.1\"^^xsd:float, \"2.0\"^^xsd:float) -> sqwrl:select(?r)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("r").isFloat());
    Assert.assertEquals(4.1f, result.getLiteral("r").getFloat(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSWRLCoreMathAddBuiltInWithLong()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String query = "swrlb:add(?r, \"2\"^^xsd:long, \"2\"^^xsd:long) -> sqwrl:select(?r)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("r").isLong());
    Assert.assertEquals(4L, result.getLiteral("r").getLong());
  }

  @Test public void TestSWRLCoreMathAddBuiltInWithDoubles()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String query = "swrlb:add(?r, \"2.0\"^^xsd:double, \"2.0\"^^xsd:double)" + " -> sqwrl:select(?r)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("r").isDouble());
    Assert.assertEquals(4.0d, result.getLiteral("r").getDouble(), IntegrationTestBase.DELTA);
  }

  @Test public void TestSWRLCoreMathAddBuiltInWithDecimals()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String query = "swrlb:add(?r, \"2.0\"^^xsd:decimal, \"2.0\"^^xsd:decimal)" + " -> sqwrl:select(?r)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("r").isDecimal());
    Assert.assertEquals(BigDecimal.valueOf(4.0), result.getLiteral("r").getDecimal());
  }

  @Test public void TestSWRLCoreMathSinBuiltInWithDecimals()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String query = "swrlb:sin(?r, \"0\"^^xsd:decimal)" + " -> sqwrl:select(?r)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("r").isDecimal());
    Assert.assertEquals(BigDecimal.ZERO, result.getLiteral("r").getDecimal());
  }

  @Test public void TestSWRLCoreMathTanBuiltInWithDecimals()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String query = "swrlb:cos(?r, \"0\"^^xsd:decimal)" + " -> sqwrl:select(?r)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("r").isDecimal());
    Assert.assertEquals(BigDecimal.ONE, result.getLiteral("r").getDecimal());
  }

}
