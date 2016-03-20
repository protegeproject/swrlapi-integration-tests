package org.swrlapi.sqwrl;

import org.junit.Assert;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.test.IntegrationTestBase;

/**
 * NOTE: All tests are designed for parallel execution.
 */
public class SQWRLCoreRenderIT extends IntegrationTestBase
{
  @Test public void TestSQWRLCoreRenderColumnName()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
    SQWRLQueryRenderer queryRenderer = queryEngine.createSQWRLQueryRenderer();
    String queryText = " -> sqwrl:select(3, 4) ^ sqwrl:columnNames(\"col1\", \"col2\")";
    SQWRLQuery query = queryEngine.createSQWRLQuery("q1", queryText);

    String queryRendering = queryRenderer.renderSQWRLQuery(query);

    Assert.assertEquals(queryText, queryRendering);
  }
}
