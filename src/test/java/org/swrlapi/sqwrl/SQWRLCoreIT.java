package org.swrlapi.sqwrl;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ClassAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.literal.XSDDate;
import org.swrlapi.literal.XSDDateTime;
import org.swrlapi.literal.XSDDuration;
import org.swrlapi.literal.XSDTime;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.test.IntegrationTestBase;

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

	@Test
	public void TestSQWRLCoreColumnName() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		SQWRLResult result = queryEngine.runSQWRLQuery("q1",
				"-> sqwrl:select(3, 4) ^ sqwrl:columnNames(\"col1\", \"col2\")");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal1 = result.getLiteral("col1");
		Assert.assertTrue(literal1.isInt());
		Assert.assertEquals(literal1.getInt(), 3);
		SQWRLLiteralResultValue literal2 = result.getLiteral("col2");
		Assert.assertTrue(literal2.isInt());
		Assert.assertEquals(literal2.getInt(), 4);
	}

	@Test
	public void TestSQWRLCoreByteResult() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"34\"^^xsd:byte)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isByte());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:byte");
		Assert.assertEquals(literal.getByte(), 34);
	}

	@Test
	public void TestSQWRLCoreShortResult() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"34\"^^xsd:short)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isShort());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:short");
		Assert.assertEquals(literal.getShort(), 34);
	}

	@Test
	public void TestSQWRLCoreQualifiedIntResult() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"34\"^^xsd:int)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isInt());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:int");
		Assert.assertEquals(literal.getInt(), 34);
	}

	@Test
	public void TestSQWRLCoreRawIntResult() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(34)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isInt());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:int");
		Assert.assertEquals(literal.getInt(), 34);
	}

	@Test
	public void TestSQWRLCoreLongResult() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"34\"^^xsd:long)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isLong());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:long");
		Assert.assertEquals(literal.getLong(), 34L);
	}

	@Test
	public void TestSQWRLCoreRawFloatResult() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(34.0)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isFloat());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:float");
		Assert.assertEquals(literal.getFloat(), 34.0f, IntegrationTestBase.DELTA);
	}

	@Test
	public void TestSQWRLCoreQualifiedFloatResult()
			throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"34.0\"^^xsd:float)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isFloat());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:float");
		Assert.assertEquals(literal.getFloat(), 34.0f, IntegrationTestBase.DELTA);
	}

	@Test
	public void TestSQWRLCoreDoubleResult() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"34.0\"^^xsd:double)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isDouble());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:double");
		Assert.assertEquals(literal.getDouble(), 34.0d, IntegrationTestBase.DELTA);
	}

	@Test
	public void TestSQWRLCoreQualifiedBooleanResult()
			throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"true\"^^xsd:boolean)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isBoolean());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:boolean");
		Assert.assertEquals(literal.getBoolean(), true);
	}

	@Test
	public void TestSQWRLCoreRawBooleanResult() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(true)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isBoolean());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:boolean");
		Assert.assertEquals(literal.getBoolean(), true);
	}

	@Test
	public void TestSQWRLCoreQualifiedStringResult()
			throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"Cat\"^^xsd:string)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isString());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:string");
		Assert.assertEquals(literal.getString(), "Cat");
	}

	@Test
	public void TestSQWRLCoreRawStringResult() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"Cat\")");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isString());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:string");
		Assert.assertEquals(literal.getString(), "Cat");
	}

	@Test
	public void TestSQWRLCoreURIResult()
			throws SWRLParseException, SQWRLException, OWLOntologyCreationException, URISyntaxException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		String homepage = "http://stanford.edu/~fred";
		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"" + homepage + "\"^^xsd:anyURI)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isAnyURI());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:anyURI");
		Assert.assertEquals(literal.getAnyURI(), new URI(homepage));
	}

	@Test
	public void TestSQWRLCoreTimeResult() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"10:10:11\"^^xsd:time)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isTime());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:time");
		Assert.assertEquals(literal.getTime(), new XSDTime("10:10:11"));
	}

	@Test
	public void TestSQWRLCoreDateResult() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"1999-01-01\"^^xsd:date\")");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isDate());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:date");
		Assert.assertEquals(literal.getDate(), new XSDDate("1999-01-01"));
	}

	@Test
	public void TestSQWRLCoreDateTimeResult() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"1999-01-01T10:10:11\"^^xsd:dateTime)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isDateTime());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:dateTime");
		Assert.assertEquals(literal.getDateTime(), new XSDDateTime("1999-01-01T10:10:11"));
	}

	@Test
	public void TestSQWRLCoreDurationResult() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "-> sqwrl:select(\"P24Y\"^^xsd:duration)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isDuration());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:duration");
		Assert.assertEquals(literal.getDuration(), new XSDDuration("P24Y"));
	}

	@Test
	public void TestSQWRLCoreCount() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, ClassAssertion(MALE, P1), ClassAssertion(MALE, P2));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "Male(?m) -> sqwrl:count(?m)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isInt());
		Assert.assertEquals(literal.getInt(), 2);
	}

	@Test
	public void TestSQWRLCoreSumShortWidest() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

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
		Assert.assertEquals(literal.getInt(), 100);
	}

	@Test
	public void TestSQWRLCoreSumIntWidest() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

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
		Assert.assertEquals(literal.getInt(), 100);
	}

	@Test
	public void TestSQWRLCoreSumLongWidest() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

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
		Assert.assertEquals(literal.getLong(), 100L);
	}

	@Test
	public void TestSQWRLCoreSumFloatWidest() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

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
		Assert.assertEquals(literal.getFloat(), 100.0f, IntegrationTestBase.DELTA);
	}

	@Test
	public void TestSQWRLCoreSumDoubleWidest() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

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
		Assert.assertEquals(literal.getDouble(), 100.0d, IntegrationTestBase.DELTA);
	}

	@Test
	public void TestSQWRLCoreByteSum() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_BYTE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_BYTE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_BYTE)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:sum(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isByte());
		Assert.assertEquals(literal.getByte(), 60);
	}

	@Test
	public void TestSQWRLCoreShortSum() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_SHORT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:sum(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isShort());
		Assert.assertEquals(literal.getShort(), 60);
	}

	@Test
	public void TestSQWRLCoreIntSum() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_INT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_INT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_INT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:sum(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isInt());
		Assert.assertEquals(literal.getInt(), 60);
	}

	@Test
	public void TestSQWRLCoreLongSum() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_LONG)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_LONG)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_LONG)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:sum(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isLong());
		Assert.assertEquals(literal.getLong(), 60L);
	}

	@Test
	public void TestSQWRLCoreFloatSum() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("100.1", XSD_FLOAT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P2, Literal("200.2", XSD_FLOAT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P3, Literal("300.2", XSD_FLOAT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:sum(?height)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isFloat());
		Assert.assertEquals(literal.getFloat(), 600.5f, IntegrationTestBase.DELTA);
	}

	@Test
	public void TestSQWRLCoreDoubleSum() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("100.1", XSD_DOUBLE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P2, Literal("200.2", XSD_DOUBLE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P3, Literal("300.2", XSD_DOUBLE)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:sum(?height)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isDouble());
		Assert.assertEquals(literal.getDouble(), 600.5d, IntegrationTestBase.DELTA);
	}

	@Test
	public void TestSQWRLCoreAvgShortBroadest() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_BYTE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_SHORT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:avg(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isShort());
		Assert.assertEquals(literal.getShort(), 20);
	}

	@Test
	public void TestSQWRLCoreAvgIntBroadest() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_BYTE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_INT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:avg(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isInt());
		Assert.assertEquals(literal.getInt(), 20);
	}

	@Test
	public void TestSQWRLCoreAvgLongBroadest() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_INT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_LONG)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:avg(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isLong());
		Assert.assertEquals(literal.getLong(), 20L);
	}

	@Test
	public void TestSQWRLCoreAvgFloatBroadest() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_INT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_LONG)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30.0", XSD_FLOAT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:avg(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isFloat());
		Assert.assertEquals(literal.getFloat(), 20.0f, IntegrationTestBase.DELTA);
	}

	@Test
	public void TestSQWRLCoreAvgDoubleBroadest() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_LONG)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20.0", XSD_FLOAT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30.0", XSD_DOUBLE)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:avg(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isDouble());
		Assert.assertEquals(literal.getDouble(), 20.0d, IntegrationTestBase.DELTA);
	}

	@Test
	public void TestSQWRLCoreByteAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_BYTE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_BYTE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_BYTE)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:avg(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isByte());
		Assert.assertEquals(literal.getByte(), 20);
	}

	@Test
	public void TestSQWRLCoreShortAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_SHORT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:avg(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isShort());
		Assert.assertEquals(literal.getShort(), 20);
	}

	@Test
	public void TestSQWRLCoreIntAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_INT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_INT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_INT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:avg(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isInt());
		Assert.assertEquals(literal.getInt(), 20);
	}

	@Test
	public void TestSQWRLCoreLongAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_LONG)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_LONG)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_LONG)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:avg(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isLong());
		Assert.assertEquals(literal.getLong(), 20L);
	}

	@Test
	public void TestSQWRLCoreFloatAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("10.3", XSD_FLOAT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P2, Literal("20.4", XSD_FLOAT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P3, Literal("30.5", XSD_FLOAT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:avg(?height)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isFloat());
		Assert.assertEquals(literal.getFloat(), 20.4f, IntegrationTestBase.DELTA);
	}

	@Test
	public void TestSQWRLCoreDoubleAvg() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("10.3", XSD_DOUBLE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P2, Literal("20.4", XSD_DOUBLE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P3, Literal("30.5", XSD_DOUBLE)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:avg(?height)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isDouble());
		Assert.assertEquals(literal.getDouble(), 20.4d, IntegrationTestBase.DELTA);
	}

	@Test
	public void TestSQWRLCoreMedianShortWidest() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("15", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("20", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P4, Literal("30", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P5, Literal("35", XSD_SHORT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:median(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isShort());
		Assert.assertEquals(literal.getShort(), 20);
	}

	@Test
	public void TestSQWRLCoreMedianIntWidest() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("15", XSD_INT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("20", XSD_INT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P4, Literal("30", XSD_INT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P5, Literal("35", XSD_INT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:median(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isInt());
		Assert.assertEquals(literal.getInt(), 20);
	}

	@Test
	public void TestSQWRLCoreMedianLongWidest() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("15", XSD_INT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("20", XSD_LONG)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P4, Literal("30", XSD_LONG)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P5, Literal("35", XSD_LONG)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:median(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isLong());
		Assert.assertEquals(literal.getLong(), 20L);
	}

	@Test
	public void TestSQWRLCoreMedianFloatWidest() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("15", XSD_INT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("20", XSD_LONG)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P4, Literal("30.0", XSD_FLOAT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P5, Literal("35.0", XSD_FLOAT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:median(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isFloat());
		Assert.assertEquals(literal.getFloat(), 20.0f, IntegrationTestBase.DELTA);
	}

	@Test
	public void TestSQWRLCoreMedianDoubleWidest() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("15", XSD_INT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("20", XSD_LONG)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P4, Literal("30.0", XSD_FLOAT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P5, Literal("35.0", XSD_DOUBLE)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:median(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isDouble());
		Assert.assertEquals(literal.getDouble(), 20.0f, IntegrationTestBase.DELTA);
	}

	@Test
	public void TestSQWRLCoreByteMedian() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_BYTE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("15", XSD_BYTE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("20", XSD_BYTE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P4, Literal("30", XSD_BYTE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P5, Literal("35", XSD_BYTE)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:median(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isByte());
		Assert.assertEquals(literal.getByte(), 20);
	}

	@Test
	public void TestSQWRLCoreShortMedian() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("15", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("20", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P4, Literal("30", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P5, Literal("35", XSD_SHORT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:median(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isShort());
		Assert.assertEquals(literal.getShort(), 20);
	}

	@Test
	public void TestSQWRLCoreIntMedian() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_INT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("15", XSD_INT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("20", XSD_INT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P4, Literal("30", XSD_INT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P5, Literal("35", XSD_INT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:median(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isInt());
		Assert.assertEquals(literal.getInt(), 20);
	}

	@Test
	public void TestSQWRLCoreLongMedian() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_LONG)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("15", XSD_LONG)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("20", XSD_LONG)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P4, Literal("30", XSD_LONG)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P5, Literal("35", XSD_LONG)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:median(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isLong());
		Assert.assertEquals(literal.getLong(), 20L);
	}

	@Test
	public void TestSQWRLCoreFloatMedian() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("10.0", XSD_FLOAT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P2, Literal("15.3", XSD_FLOAT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P3, Literal("20.55", XSD_FLOAT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P4, Literal("30.32", XSD_FLOAT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P5, Literal("35.12", XSD_FLOAT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:median(?height)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isFloat());
		Assert.assertEquals(literal.getFloat(), 20.55f, IntegrationTestBase.DELTA);
	}

	@Test
	public void TestSQWRLCoreDoubleMedian() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("10.0", XSD_DOUBLE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P2, Literal("15.3", XSD_DOUBLE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P3, Literal("20.55", XSD_DOUBLE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P4, Literal("30.32", XSD_DOUBLE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P5, Literal("35.12", XSD_DOUBLE)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:median(?height)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isDouble());
		Assert.assertEquals(literal.getDouble(), 20.55d, IntegrationTestBase.DELTA);
	}

	@Test
	public void TestSQWRLCoreByteMin() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_BYTE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_BYTE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_BYTE)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:min(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isByte());
		Assert.assertEquals(literal.getByte(), 10);
	}

	@Test
	public void TestSQWRLCoreShortMin() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_SHORT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:min(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isShort());
		Assert.assertEquals(literal.getShort(), 10);
	}

	@Test
	public void TestSQWRLCoreIntMin() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_INT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_INT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_INT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:min(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isInt());
		Assert.assertEquals(literal.getInt(), 10);
	}

	@Test
	public void TestSQWRLCoreLongMin() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_LONG)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_LONG)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_LONG)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:min(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isLong());
		Assert.assertEquals(literal.getLong(), 10L);
	}

	@Test
	public void TestSQWRLCoreFloatMin() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("101.0", XSD_FLOAT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P2, Literal("102.3", XSD_FLOAT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P3, Literal("104.2", XSD_FLOAT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:min(?height)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isFloat());
		Assert.assertEquals(literal.getFloat(), 101.0f, IntegrationTestBase.DELTA);
	}

	@Test
	public void TestSQWRLCoreDoubleMin() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("101.0", XSD_DOUBLE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P2, Literal("102.3", XSD_DOUBLE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P3, Literal("104.2", XSD_DOUBLE)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:min(?height)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isDouble());
		Assert.assertEquals(literal.getDouble(), 101.0d, IntegrationTestBase.DELTA);
	}

	@Test
	public void TestSQWRLCoreMixedTypesMin() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("23", XSD_BYTE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("44", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("55", XSD_INT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:min(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isByte());
		Assert.assertEquals(literal.getByte(), 23);
	}

	@Test(expected = SQWRLException.class)
	public void TestSQWRLCoreInvalidMin() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P1, Literal("Bob", XSD_STRING)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P2, Literal("Fred", XSD_STRING)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P3, Literal("Joe", XSD_STRING)));

		queryEngine.runSQWRLQuery("q1", "hasName(?p, ?name)-> sqwrl:min(?name)");
	}

	@Test
	public void TestSQWRLCoreByteMax() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_BYTE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_BYTE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_BYTE)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:max(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isByte());
		Assert.assertEquals(literal.getByte(), 30);
	}

	@Test
	public void TestSQWRLCoreShortMax() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_SHORT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:max(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isShort());
		Assert.assertEquals(literal.getShort(), 30);
	}

	@Test
	public void TestSQWRLCoreIntMax() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("10", XSD_INT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("20", XSD_INT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_INT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:max(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isInt());
		Assert.assertEquals(literal.getInt(), 30);
	}

	@Test
	public void TestSQWRLCoreFloatMax() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("101.0", XSD_FLOAT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P2, Literal("102.3", XSD_FLOAT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P3, Literal("104.1", XSD_FLOAT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:max(?height)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isFloat());
		Assert.assertEquals(literal.getFloat(), 104.1f, IntegrationTestBase.DELTA);
	}

	@Test
	public void TestSQWRLCoreDoubleMax() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("101.0", XSD_DOUBLE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P2, Literal("102.3", XSD_DOUBLE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P3, Literal("104.1", XSD_DOUBLE)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:max(?height)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isDouble());
		Assert.assertEquals(literal.getDouble(), 104.1d, IntegrationTestBase.DELTA);
	}

	@Test
	public void TestSQWRLCoreMixedTypesMax() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("23", XSD_BYTE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("44", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("55", XSD_INT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:max(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isInt());
		Assert.assertEquals(literal.getInt(), 55);
	}

	@Test
	public void TestSQWRLCoreOrderByByte() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("20", XSD_BYTE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("10", XSD_BYTE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_BYTE)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1",
				"hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderBy(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("age").isByte());
		Assert.assertEquals(result.getLiteral("age").getByte(), 10);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("age").isByte());
		Assert.assertEquals(result.getLiteral("age").getByte(), 20);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("age").isByte());
		Assert.assertEquals(result.getLiteral("age").getByte(), 30);
	}

	@Test
	public void TestSQWRLCoreOrderByShort() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("20", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("10", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_SHORT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1",
				"hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderBy(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("age").isShort());
		Assert.assertEquals(result.getLiteral("age").getShort(), 10);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("age").isShort());
		Assert.assertEquals(result.getLiteral("age").getShort(), 20);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("age").isShort());
		Assert.assertEquals(result.getLiteral("age").getShort(), 30);
	}

	@Test
	public void TestSQWRLOrderByInt() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("20", XSD_INT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("10", XSD_INT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_INT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1",
				"hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderBy(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("age").isInt());
		Assert.assertEquals(result.getLiteral("age").getInt(), 10);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("age").isInt());
		Assert.assertEquals(result.getLiteral("age").getInt(), 20);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("age").isInt());
		Assert.assertEquals(result.getLiteral("age").getInt(), 30);
	}

	@Test
	public void TestSQWRLOrderByLong() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("20", XSD_LONG)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("10", XSD_LONG)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_LONG)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1",
				"hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderBy(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("age").isLong());
		Assert.assertEquals(result.getLiteral("age").getLong(), 10);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("age").isLong());
		Assert.assertEquals(result.getLiteral("age").getLong(), 20);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("age").isLong());
		Assert.assertEquals(result.getLiteral("age").getLong(), 30);
	}

	@Test
	public void TestSQWRLOrderByFloat() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("200.0", XSD_FLOAT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P2, Literal("100.0", XSD_FLOAT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P3, Literal("300.0", XSD_FLOAT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1",
				"hasHeight(?p, ?height)-> sqwrl:select(?p, ?height) ^ sqwrl:orderBy(?height)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("height").isFloat());
		Assert.assertEquals(result.getLiteral("height").getFloat(), 100.0f, IntegrationTestBase.DELTA);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("height").isFloat());
		Assert.assertEquals(result.getLiteral("height").getFloat(), 200.0f, IntegrationTestBase.DELTA);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("height").isFloat());
		Assert.assertEquals(result.getLiteral("height").getFloat(), 300.0f, IntegrationTestBase.DELTA);
	}

	@Test
	public void TestSQWRLOrderByDouble() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("200.0", XSD_DOUBLE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P2, Literal("100.0", XSD_DOUBLE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P3, Literal("300.0", XSD_DOUBLE)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1",
				"hasHeight(?p, ?height)-> sqwrl:select(?p, ?height) ^ sqwrl:orderBy(?height)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("height").isDouble());
		Assert.assertEquals(result.getLiteral("height").getDouble(), 100.0d, IntegrationTestBase.DELTA);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("height").isDouble());
		Assert.assertEquals(result.getLiteral("height").getDouble(), 200.0d, IntegrationTestBase.DELTA);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("height").isDouble());
		Assert.assertEquals(result.getLiteral("height").getDouble(), 300.0d, IntegrationTestBase.DELTA);
	}

	@Test
	public void TestSQWRLCoreOrderByString() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P1, Literal("Fred", XSD_STRING)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P2, Literal("Bob", XSD_STRING)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P3, Literal("Ann", XSD_STRING)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1",
				"hasName(?p, ?name)-> sqwrl:select(?p, ?name) ^ sqwrl:orderBy(?name)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("name").isString());
		Assert.assertEquals(result.getLiteral("name").getString(), "Ann");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("name").isString());
		Assert.assertEquals(result.getLiteral("name").getString(), "Bob");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("name").isString());
		Assert.assertEquals(result.getLiteral("name").getString(), "Fred");
	}

	@Test
	public void TestSQWRLCoreOrderByAnyURI()
			throws SWRLParseException, SQWRLException, OWLOntologyCreationException, URISyntaxException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		String p1HomePage = "http://stanford.edu/~joe";
		String p2HomePage = "http://stanford.edu/~fred";
		String p3HomePage = "http://stanford.edu/~bob";

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HOMEPAGE, P1, Literal(p1HomePage, XSD_ANY_URI)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HOMEPAGE, P2, Literal(p2HomePage, XSD_ANY_URI)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_HOMEPAGE, P3, Literal(p3HomePage, XSD_ANY_URI)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1",
				"hasHomePage(?p, ?homepage)-> sqwrl:select(?p, ?homepage) ^ sqwrl:orderBy(?homepage)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("homepage").isAnyURI());
		Assert.assertEquals(result.getLiteral("homepage").getAnyURI(), new URI(p3HomePage));

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("homepage").isAnyURI());
		Assert.assertEquals(result.getLiteral("homepage").getAnyURI(), new URI(p2HomePage));

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("homepage").isAnyURI());
		Assert.assertEquals(result.getLiteral("homepage").getAnyURI(), new URI(p1HomePage));
	}

	@Test
	public void TestSQWRLOrderByDate() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_DOB, P1, Literal("1990-01-02", XSD_DATE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_DOB, P2, Literal("1989-10-10", XSD_DATE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_DOB, P3, Literal("1991-01-10", XSD_DATE)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1",
				"hasDOB(?p, ?dob)-> sqwrl:select(?p, ?dob) ^ sqwrl:orderBy(?dob)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("dob").isDate());
		Assert.assertEquals(result.getLiteral("dob").getDate(), new XSDDate("1989-10-10"));

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("dob").isDate());
		Assert.assertEquals(result.getLiteral("dob").getDate(), new XSDDate("1990-01-02"));

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("dob").isDate());
		Assert.assertEquals(result.getLiteral("dob").getDate(), new XSDDate("1991-01-10"));
	}

	@Test
	public void TestSQWRLOrderByDateTime() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_TOB, P1, Literal("1990-01-02T10:10:09.2", XSD_DATETIME)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_TOB, P2, Literal("1989-10-10T09:08:08.3", XSD_DATETIME)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_TOB, P3, Literal("1991-01-10T11:11:11.11", XSD_DATETIME)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1",
				"hasTOB(?p, ?tob)-> sqwrl:select(?p, ?tob) ^ sqwrl:orderBy(?tob)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("tob").isDateTime());
		Assert.assertEquals(result.getLiteral("tob").getDateTime(), new XSDDateTime("1989-10-10T09:08:08.3"));

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("tob").isDateTime());
		Assert.assertEquals(result.getLiteral("tob").getDateTime(), new XSDDateTime("1990-01-02T10:10:09.2"));

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("tob").isDateTime());
		Assert.assertEquals(result.getLiteral("tob").getDateTime(), new XSDDateTime("1991-01-10T11:11:11.11"));
	}

	@Test
	public void TestSQWRLOrderByTime() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_TOB, P1, Literal("10:10:09.2", XSD_TIME)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_TOB, P2, Literal("09:08:08.3", XSD_TIME)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_TOB, P3, Literal("11:11:11.11", XSD_TIME)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1",
				"hasTOB(?p, ?tob)-> sqwrl:select(?p, ?tob) ^ sqwrl:orderBy(?tob)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("tob").isTime());
		Assert.assertEquals(result.getLiteral("tob").getTime(), new XSDTime("09:08:08.3"));

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("tob").isTime());
		Assert.assertEquals(result.getLiteral("tob").getTime(), new XSDTime("10:10:09.2"));

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("tob").isTime());
		Assert.assertEquals(result.getLiteral("tob").getTime(), new XSDTime("11:11:11.11"));
	}

	@Test
	public void TestSQWRLOrderByDuration() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("P23Y", XSD_DURATION)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("P21Y", XSD_DURATION)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("P30Y", XSD_DURATION)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1",
				"hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderBy(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("age").isDuration());
		Assert.assertEquals(result.getLiteral("age").getDuration(), new XSDDuration("P21Y"));

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("age").isDuration());
		Assert.assertEquals(result.getLiteral("age").getDuration(), new XSDDuration("P23Y"));

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("age").isDuration());
		Assert.assertEquals(result.getLiteral("age").getDuration(), new XSDDuration("P30Y"));
	}

	@Test
	public void TestSQWRLOrderByDescendingByte() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("20", XSD_BYTE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("10", XSD_BYTE)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_BYTE)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1",
				"hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderByDescending(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("age").isByte());
		Assert.assertEquals(result.getLiteral("age").getByte(), 30);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("age").isByte());
		Assert.assertEquals(result.getLiteral("age").getByte(), 20);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("age").isByte());
		Assert.assertEquals(result.getLiteral("age").getByte(), 10);
	}

	@Test
	public void TestSQWRLOrderByDescendingShort() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("20", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("10", XSD_SHORT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_SHORT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1",
				"hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderByDescending(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("age").isShort());
		Assert.assertEquals(result.getLiteral("age").getShort(), 30);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("age").isShort());
		Assert.assertEquals(result.getLiteral("age").getShort(), 20);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("age").isShort());
		Assert.assertEquals(result.getLiteral("age").getShort(), 10);
	}

	@Test
	public void TestSQWRLOrderByDescendingInt() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("20", XSD_INT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("10", XSD_INT)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_INT)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1",
				"hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderByDescending(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("age").isInt());
		Assert.assertEquals(result.getLiteral("age").getInt(), 30);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("age").isInt());
		Assert.assertEquals(result.getLiteral("age").getInt(), 20);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("age").isInt());
		Assert.assertEquals(result.getLiteral("age").getInt(), 10);
	}

	@Test
	public void TestSQWRLOrderByDescendingLong() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("20", XSD_LONG)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P2, Literal("10", XSD_LONG)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P3, Literal("30", XSD_LONG)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1",
				"hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderByDescending(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("age").isLong());
		Assert.assertEquals(result.getLiteral("age").getLong(), 30L);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("age").isLong());
		Assert.assertEquals(result.getLiteral("age").getLong(), 20L);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("age").isLong());
		Assert.assertEquals(result.getLiteral("age").getLong(), 10L);
	}

	@Test
	public void TestSQWRLOrderByDescendingString() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

		addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P1, Literal("Fred", XSD_STRING)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P2, Literal("Bob", XSD_STRING)));
		addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P3, Literal("Ann", XSD_STRING)));

		SQWRLResult result = queryEngine.runSQWRLQuery("q1",
				"hasName(?p, ?name)-> sqwrl:select(?p, ?name) ^ sqwrl:orderByDescending(?name)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("name").isString());
		Assert.assertEquals(result.getLiteral("name").getString(), "Fred");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("name").isString());
		Assert.assertEquals(result.getLiteral("name").getString(), "Bob");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getNamedIndividual("p").isNamedIndividual());
		Assert.assertEquals(result.getNamedIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("name").isString());
		Assert.assertEquals(result.getLiteral("name").getString(), "Ann");
	}
}
