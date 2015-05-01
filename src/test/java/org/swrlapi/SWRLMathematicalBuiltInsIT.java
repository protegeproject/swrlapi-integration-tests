package org.swrlapi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.test.SWRLAPIIntegrationTestBase;

public class SWRLMathematicalBuiltInsIT extends SWRLAPIIntegrationTestBase
{
  @Before
  public void setUp() throws OWLOntologyCreationException
  {
    createOWLOntologyAndSQWRLQueryEngine();
  }

  @Test
  public void TestSWRLCoreLessThanBuiltInWithShort() throws SWRLParseException, SQWRLException
  {
    declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:int");

    SQWRLResult result = executeSQWRLQuery("q1",
        "hasAge(p1, ?age) ^ swrlm:eval(?r, \"age + 1\", ?age) -> sqwrl:select(?r)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("r").isDouble());
    Assert.assertEquals(result.getLiteral("r").getDouble(), 43.0d, this.DELTA);
  }
}
