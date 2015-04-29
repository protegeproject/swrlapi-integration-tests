package org.swrlapi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.core.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.test.SWRLAPIIntegrationTestBase;

public class SWRLMathematicalBuiltInsIT extends SWRLAPIIntegrationTestBase
{
	private SQWRLQueryEngine sqwrlQueryEngine;

	@Before
	public void setUp() throws OWLOntologyCreationException
	{
		OWLOntology ontology = createEmptyOWLOntology();

		sqwrlQueryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
	}

	@Test
	public void TestSWRLCoreLessThanBuiltInWithShort() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:int");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasAge(p1, ?age) ^ swrlm:eval(?r, \"age + 1\", ?age) -> sqwrl:select(?r)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("r").isDouble());
		Assert.assertEquals(result.getLiteral("r").getDouble(), 43.0d, DELTA);
	}

	private SQWRLResult executeSQWRLQuery(String queryName) throws SQWRLException
	{
		return sqwrlQueryEngine.runSQWRLQuery(queryName);
	}

	protected SQWRLResult executeSQWRLQuery(String queryName, String query) throws SQWRLException, SWRLParseException
	{
		createSQWRLQuery(queryName, query);

		return executeSQWRLQuery(queryName);
	}
}
