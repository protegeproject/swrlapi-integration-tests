package org.swrlapi;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;
import org.swrlapi.drools.core.DroolsFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.test.SWRLAPITestBase;

public class SWRLCoreTestCase extends SWRLAPITestBase
{
	final String Namespace = "http://swrlapi.org/ontologies/SWRLCoreTestCase.owl#";

	private SQWRLQueryEngine sqwrlQueryEngine;

	@Before
	public void setUp() throws OWLOntologyCreationException
	{
		SWRLAPIOWLOntology swrlapiOWLOntology = createEmptySWRLAPIOWLOntology(Namespace);

		sqwrlQueryEngine = swrlapiOWLOntology.createSQWRLQueryEngine(DroolsFactory.getSWRLRuleEngineCreator());
	}

	@Test
	public void TestSWRLCoreClassAtomInAntecedentWithNamedIndividual() throws SWRLParseException, SQWRLException
	{
		declareOWLClassAssertion("Male", "p1");

		SQWRLResult result = executeSQWRLQuery("q1", "Male(p1) -> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLCoreClassAtomInAntecedentWithVariable() throws SWRLParseException, SQWRLException
	{
		declareOWLClassAssertion("Male", "p1");

		SQWRLResult result = executeSQWRLQuery("q1", "Male(?m) -> sqwrl:select(?m)");

		Assert.assertTrue(result.next());

		Assert.assertEquals(result.getIndividual("m").getShortName(), "p1");
	}

	@Test
	public void TestSWRLCoreSameAs() throws SWRLParseException, SQWRLException
	{
		declareOWLSameAsAssertion("p1", "p2");

		SQWRLResult result = executeSQWRLQuery("q1", "sameAs(p1, p2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreDifferentFrom() throws SWRLParseException, SQWRLException
	{
		declareOWLDifferentFromAssertion("p1", "p2");

		SQWRLResult result = executeSQWRLQuery("q1", "differentFrom(p1, p2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreIndividualShortNameMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLObjectPropertyAssertion("p1", "hasUncle", "p2");

		SQWRLResult result = executeSQWRLQuery("q1", "hasUncle(p1, p2) -> sqwrl:select(p1, p2)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
		Assert.assertTrue(result.getIndividual(1).isIndividual());
		Assert.assertEquals(result.getIndividual(1).getShortName(), "p2");
	}

	@Test
	public void TestSWRLCoreIndividualPrefixedNameMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLObjectPropertyAssertion("p1", "hasUncle", "p2");

		SQWRLResult result = executeSQWRLQuery("q1", "hasUncle(:p1, :p2) -> sqwrl:select(:p1, :p2)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
		Assert.assertTrue(result.getIndividual(1).isIndividual());
		Assert.assertEquals(result.getIndividual(1).getShortName(), "p2");
	}

	@Test
	public void TestSWRLCoreIndividualShortNameBind() throws SWRLParseException, SQWRLException
	{
		declareOWLObjectPropertyAssertion("p1", "hasUncle", "p2");

		SQWRLResult result = executeSQWRLQuery("q1", "hasUncle(p1, ?uncle) -> sqwrl:select(?uncle)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("uncle").isIndividual());
		Assert.assertEquals(result.getIndividual("uncle").getShortName(), "p2");
	}

	@Test
	public void TestSWRLCoreByteLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:byte");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, \"42\"^^xsd:byte) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreNegativeByteLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "yearOffsetToBirth", "-42", "xsd:byte");

		SQWRLResult result = executeSQWRLQuery("q1", "yearOffsetToBirth(p1, \"-42\"^^xsd:byte) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreByteLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:byte");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, ?age) -> sqwrl:select(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("age").isByte());
		Assert.assertEquals(result.getLiteral("age").getByte(), 42);
	}

	@Test
	public void TestSWRLCoreShortLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:short");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, \"42\"^^xsd:short) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreNegativeShortLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "yearOffsetToBirth", "-42", "xsd:short");

		SQWRLResult result = executeSQWRLQuery("q1", "yearOffsetToBirth(p1, \"-42\"^^xsd:short) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreShortLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:short");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, ?age) -> sqwrl:select(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("age").isShort());
		Assert.assertEquals(result.getLiteral("age").getShort(), 42);
	}

	@Test
	public void TestSWRLCoreNegativeShortLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "yearOffsetToBirth", "-42", "xsd:short");

		SQWRLResult result = executeSQWRLQuery("q1", "yearOffsetToBirth(p1, ?offset) -> sqwrl:select(?offset)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("offset").isShort());
		Assert.assertEquals(result.getLiteral("offset").getShort(), -42);
	}

	@Test
	public void TestSWRLCoreRawIntLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:int");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, 42) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreRawNegativeIntLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "yearOffsetToBirth", "-42", "xsd:int");

		SQWRLResult result = executeSQWRLQuery("q1", "yearOffsetToBirth(p1, -42) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreQualifiedIntLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:int");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, \"42\"^^xsd:int) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreQualifiedNegativeIntLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "yearOffsetToBirth", "-42", "xsd:int");

		SQWRLResult result = executeSQWRLQuery("q1", "yearOffsetToBirth(p1, \"-42\"^^xsd:int) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreIntLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:int");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, ?age) -> sqwrl:select(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("age").isInt());
		Assert.assertEquals(result.getLiteral("age").getInt(), 42);
	}

	@Test
	public void TestSWRLCoreNegativeIntLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "yearOffsetToBirth", "-42", "xsd:int");

		SQWRLResult result = executeSQWRLQuery("q1", "yearOffsetToBirth(p1, ?offset) -> sqwrl:select(?offset)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("offset").isInt());
		Assert.assertEquals(result.getLiteral("offset").getInt(), -42);
	}

	@Test
	public void TestSWRLCoreLongLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:long");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, \"42\"^^xsd:long) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreNegativeLongLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "yearOffsetToBirth", "-42", "xsd:long");

		SQWRLResult result = executeSQWRLQuery("q1", "yearOffsetToBirth(p1, \"-42\"^^xsd:long) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreLongLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:long");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, ?age) -> sqwrl:select(p1, ?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("age").isLong());
		Assert.assertEquals(result.getLiteral("age").getLong(), 42L);
	}

	@Test
	public void TestSWRLCoreNegativeLongLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "yearOffsetToBirth", "-42", "xsd:long");

		SQWRLResult result = executeSQWRLQuery("q1", "yearOffsetToBirth(p1, ?offset) -> sqwrl:select(?offset)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("offset").isLong());
		Assert.assertEquals(result.getLiteral("offset").getLong(), -42L);
	}

	@Test
	public void TestSWRLCoreRawFloatLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasHeightInCM", "180.0", "xsd:float");

		SQWRLResult result = executeSQWRLQuery("q1", "hasHeightInCM(p1, 180.0) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreRawNegativeFloatLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "heightOffsetInCM", "-180.0", "xsd:float");

		SQWRLResult result = executeSQWRLQuery("q1", "heightOffsetInCM(p1, -180.0) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreQualifiedFloatLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasHeightInCM", "177.0", "xsd:float");

		SQWRLResult result = executeSQWRLQuery("q1", "hasHeightInCM(p1, \"177.0\"^^xsd:float) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreQualifiedNegativeFloatLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "heightOffsetInCM", "-177.1", "xsd:float");

		SQWRLResult result = executeSQWRLQuery("q1", "heightOffsetInCM(p1, \"-177.1\"^^xsd:float) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreFloatLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasHeightInCM", "177.0", "xsd:float");

		SQWRLResult result = executeSQWRLQuery("q1", "hasHeightInCM(p1, ?height) -> sqwrl:select(?height)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("height").isFloat());
		Assert.assertEquals(result.getLiteral("height").getFloat(), 177.0f, DELTA);
	}

	@Test
	public void TestSWRLCoreNegativeFloatLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "heightOffsetInCM", "-177.0", "xsd:float");

		SQWRLResult result = executeSQWRLQuery("q1", "heightOffsetInCM(p1, ?offset) -> sqwrl:select(?offset)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("offset").isFloat());
		Assert.assertEquals(result.getLiteral("offset").getFloat(), -177.0f, DELTA);
	}

	@Test
	public void TestSWRLCoreDoubleLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasHeightInCM", "177.0", "xsd:double");

		SQWRLResult result = executeSQWRLQuery("q1", "hasHeightInCM(p1, \"177.0\"^^xsd:double) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreNegativeDoubleLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "heightOffsetInCM", "-177.1", "xsd:double");

		SQWRLResult result = executeSQWRLQuery("q1", "heightOffsetInCM(p1, \"-177.1\"^^xsd:double) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreDoubleLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasHeightInCM", "177.0", "xsd:double");

		SQWRLResult result = executeSQWRLQuery("q1", "hasHeightInCM(p1, ?height) -> sqwrl:select(?height)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("height").isDouble());
		Assert.assertEquals(result.getLiteral("height").getDouble(), 177.0d, DELTA);
	}

	@Test
	public void TestSWRLCoreNegativeDoubleLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "heightOffsetInCM", "-177.0", "xsd:double");

		SQWRLResult result = executeSQWRLQuery("q1", "heightOffsetInCM(p1, ?offset) -> sqwrl:select(?offset)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("offset").isDouble());
		Assert.assertEquals(result.getLiteral("offset").getDouble(), -177.0d, DELTA);
	}

	@Test
	public void TestSWRLCoreRawBooleanLiteralTrueMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "isFrench", "true", "xsd:boolean");

		SQWRLResult result = executeSQWRLQuery("q1", "isFrench(p1, true) -> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLCoreQualifiedBooleanLiteralTrueMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "isFrench", "true", "xsd:boolean");

		SQWRLResult result = executeSQWRLQuery("q1", "isFrench(p1, \"true\"^^xsd:boolean) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreRawBooleanLiteralFalseMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "isFrench", "false", "xsd:boolean");

		SQWRLResult result = executeSQWRLQuery("q1", "isFrench(p1, false) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreQualifiedBooleanLiteralFalseMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "isFrench", "false", "xsd:boolean");

		SQWRLResult result = executeSQWRLQuery("q1", "isFrench(p1, \"false\"^^xsd:boolean) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreBooleanLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "isFrench", "true", "xsd:boolean");

		SQWRLResult result = executeSQWRLQuery("q1", "isFrench(p1, ?french) -> sqwrl:select(?french)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("french").isBoolean());
		Assert.assertEquals(result.getLiteral("french").getBoolean(), true);
	}

	@Test
	public void TestSWRLCoreStringLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasName", "Bob", "xsd:string");

		SQWRLResult result = executeSQWRLQuery("q1", "hasName(p1, ?name) -> sqwrl:select(?name)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("name").isString());
		Assert.assertEquals(result.getLiteral("name").getString(), "Bob");
	}

	@Test
	public void TestSWRLCoreRawStringLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasName", "Bob", "xsd:string");

		SQWRLResult result = executeSQWRLQuery("q1", "hasName(p1, \"Bob\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreQualifiedStringLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasName", "Bob", "xsd:string");

		SQWRLResult result = executeSQWRLQuery("q1", "hasName(p1, \"Bob\"^^xsd:string) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreURILiteralMatch() throws SWRLParseException, SQWRLException
	{
		String homePage = "http://stanford.edu/~fred";

		declareOWLDataPropertyAssertion("p1", "hasHomePage", homePage, "xsd:anyURI");

		SQWRLResult result = executeSQWRLQuery("q1", "hasHomePage(p1, \"" + homePage + "\"^^xsd:anyURI) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreURILiteralBind() throws SWRLParseException, SQWRLException, URISyntaxException
	{
		String homePage = "http://stanford.edu/~fred";

		declareOWLDataPropertyAssertion("p1", "hasHomePage", homePage, "xsd:anyURI");

		SQWRLResult result = executeSQWRLQuery("q1", "hasHomePage(p1, ?home) -> sqwrl:select(?home)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("home").isAnyURI());
		Assert.assertEquals(result.getLiteral("home").getAnyURI(), new URI(homePage));
	}

	@Test
	public void TestSWRLCoreXSDDateLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasDOB", "2001-01-05", "xsd:date");

		SQWRLResult result = executeSQWRLQuery("q1", "hasDOB(p1, \"2001-01-05\"^^xsd:date) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreXSDDateLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasDOB", "2001-01-05", "xsd:date");

		SQWRLResult result = executeSQWRLQuery("q1", "hasDOB(p1, ?dob) -> sqwrl:select(?dob)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("dob").isDate());
		Assert.assertEquals(result.getLiteral("dob").getDate(), new XSDDate("2001-01-05"));
	}

	@Test
	public void TestSWRLCoreXSDDateTimeLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasTOB", "2001-01-05T10:10:10", "xsd:dateTime");

		SQWRLResult result = executeSQWRLQuery("q1", "hasTOB(p1, \"2001-01-05T10:10:10\"^^xsd:dateTime) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreXSDDateTimeLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasTOB", "2001-01-05T10:10:10", "xsd:dateTime");

		SQWRLResult result = executeSQWRLQuery("q1", "hasTOB(p1, ?tob) -> sqwrl:select(?tob)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("tob").isDateTime());
		Assert.assertEquals(result.getLiteral("tob").getDateTime(), new XSDDateTime("2001-01-05T10:10:10"));
	}

	@Test
	public void TestSWRLCoreXSDDurationLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "P42Y", "xsd:duration");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, \"P42Y\"^^xsd:duration) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreXSDDurationLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "P42Y", "xsd:duration");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, ?age) -> sqwrl:select(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("age").isDuration());
		Assert.assertEquals(result.getLiteral("age").getDuration(), new XSDDuration("P42Y"));
	}

	@Test
	public void TestSWRLCoreXSDTimeLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasBirthTime", "10:10:10.33", "xsd:time");

		SQWRLResult result = executeSQWRLQuery("q1", "hasBirthTime(p1, \"10:10:10.33\"^^xsd:time) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreXSDTimeLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasBirthTime", "10:10:10.33", "xsd:time");

		SQWRLResult result = executeSQWRLQuery("q1", "hasBirthTime(p1, ?bt) -> sqwrl:select(p1, ?bt)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("bt").isTime());
		Assert.assertEquals(result.getLiteral("bt").getTime(), new XSDTime("10:10:10.33"));
	}

	@Test
	public void TestSWRLCoreCascadingByteVariable() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "22", "xsd:byte");
		declareOWLDataPropertyAssertion("p2", "hasAge", "22", "xsd:byte");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, ?age) ^ hasAge(p2, ?age) -> sqwrl:select(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("age").isByte());
		Assert.assertEquals(result.getLiteral("age").getByte(), 22);
	}

	@Test
	public void TestSWRLCoreCascadingShortVariable() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "22", "xsd:short");
		declareOWLDataPropertyAssertion("p2", "hasAge", "22", "xsd:short");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, ?age) ^ hasAge(p2, ?age) -> sqwrl:select(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("age").isShort());
		Assert.assertEquals(result.getLiteral("age").getShort(), 22);
	}

	@Test
	public void TestSWRLCoreCascadingIntVariable() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "22", "xsd:int");
		declareOWLDataPropertyAssertion("p2", "hasAge", "22", "xsd:int");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, ?age) ^ hasAge(p2, ?age) -> sqwrl:select(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("age").isInt());
		Assert.assertEquals(result.getLiteral("age").getInt(), 22);
	}

	@Test
	public void TestSWRLCoreCascadingLongVariable() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "22", "xsd:long");
		declareOWLDataPropertyAssertion("p2", "hasAge", "22", "xsd:long");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, ?age) ^ hasAge(p2, ?age) -> sqwrl:select(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("age").isLong());
		Assert.assertEquals(result.getLiteral("age").getLong(), 22L);
	}

	@Test
	public void TestSWRLCoreCascadingFloatVariable() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasHeight", "122.0", "xsd:float");
		declareOWLDataPropertyAssertion("p2", "hasHeight", "122.0", "xsd:float");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasHeight(p1, ?height) ^ hasHeight(p2, ?height) -> sqwrl:select(?height)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("height").isFloat());
		Assert.assertEquals(result.getLiteral("height").getFloat(), 122.0f, DELTA);
	}

	@Test
	public void TestSWRLCoreCascadingDoubleVariable() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasHeight", "122.0", "xsd:double");
		declareOWLDataPropertyAssertion("p2", "hasHeight", "122.0", "xsd:double");

		SQWRLResult result = executeSQWRLQuery("q1", "hasHeight(p1, ?age) ^ hasHeight(p2, ?age) -> sqwrl:select(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("age").isDouble());
		Assert.assertEquals(result.getLiteral("age").getDouble(), 122.0d, DELTA);
	}

	@Test
	public void TestSWRLCoreCascadingBooleanVariable() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "isFrench", "true", "xsd:boolean");
		declareOWLDataPropertyAssertion("p2", "isFrench", "true", "xsd:boolean");

		SQWRLResult result = executeSQWRLQuery("q1", "isFrench(p1, ?f) ^ isFrench(p2, ?f) -> sqwrl:select(?f)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("f").isBoolean());
		Assert.assertEquals(result.getLiteral("f").getBoolean(), true);
	}

	@Test
	public void TestSWRLCoreCascadingStringVariable() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasName", "Bob", "xsd:string");
		declareOWLDataPropertyAssertion("p2", "hasName", "Bob", "xsd:string");

		SQWRLResult result = executeSQWRLQuery("q1", "hasName(p1, ?name) ^ hasName(p2, ?name) -> sqwrl:select(?name)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("name").isString());
		Assert.assertEquals(result.getLiteral("name").getString(), "Bob");
	}

	@Test
	public void TestSWRLCoreCascadingURIVariable() throws SWRLParseException, SQWRLException, URISyntaxException
	{
		String homePage = "http://stanford.edu/~fred";

		declareOWLDataPropertyAssertion("p1", "hasHomePage", homePage, "xsd:anyURI");
		declareOWLDataPropertyAssertion("p2", "hasHomePage", homePage, "xsd:anyURI");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasHomePage(p1, ?home) ^ hasHomePage(p2, ?home) -> sqwrl:select(?home)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("home").isAnyURI());
		Assert.assertEquals(result.getLiteral("home").getAnyURI(), new URI(homePage));
	}

	@Test
	public void TestSWRLCoreCascadingXSDDateVariable() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasDOB", "1999-01-02", "xsd:date");
		declareOWLDataPropertyAssertion("p2", "hasDOB", "1999-01-02", "xsd:date");

		SQWRLResult result = executeSQWRLQuery("q1", "hasDOB(p1, ?dob) ^ hasDOB(p2, ?dob) -> sqwrl:select(?dob)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("dob").isDate());
		Assert.assertEquals(result.getLiteral("dob").getDate(), new XSDDate("1999-01-02"));
	}

	@Test
	public void TestSWRLCoreCascadingXSDDateTimeVariable() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasTOB", "1999-01-02T10:10:10", "xsd:dateTime");
		declareOWLDataPropertyAssertion("p2", "hasTOB", "1999-01-02T10:10:10", "xsd:dateTime");

		SQWRLResult result = executeSQWRLQuery("q1", "hasTOB(p1, ?tob) ^ hasTOB(p2, ?tob) -> sqwrl:select(?tob)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("tob").isDateTime());
		Assert.assertEquals(result.getLiteral("tob").getDateTime(), new XSDDateTime("1999-01-02T10:10:10"));
	}

	@Test
	public void TestSWRLCoreCascadingXSDTimeVariable() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasTOB", "10:10:10", "xsd:time");
		declareOWLDataPropertyAssertion("p2", "hasTOB", "10:10:10", "xsd:time");

		SQWRLResult result = executeSQWRLQuery("q1", "hasTOB(p1, ?tob) ^ hasTOB(p2, ?tob) -> sqwrl:select(?tob)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("tob").isTime());
		Assert.assertEquals(result.getLiteral("tob").getTime(), new XSDTime("10:10:10"));
	}

	@Test
	public void TestSWRLCoreCascadingXSDDurationVariable() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "P42Y", "xsd:duration");
		declareOWLDataPropertyAssertion("p2", "hasAge", "P42Y", "xsd:duration");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, ?age) ^ hasAge(p2, ?age) -> sqwrl:select(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("age").isDuration());
		Assert.assertEquals(result.getLiteral("age").getDuration(), new XSDDuration("P42Y"));
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
