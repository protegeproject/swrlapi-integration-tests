package org.swrlapi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.literal.XSDDuration;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.test.IntegrationTestBase;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;

public class SWRLCoreBuiltInsIT extends IntegrationTestBase
{
  private static final OWLNamedIndividual P1 = NamedIndividual(IRI(":p1"));
  private static final OWLDataProperty HAS_AGE = DataProperty(IRI(":hasAge"));
  private static final OWLDataProperty HAS_NAME = DataProperty(IRI(":hasName"));
  private static final OWLDataProperty IS_FRENCH = DataProperty(IRI(":isFrench"));
  private static final OWLDataProperty HAS_HEIGHT_IN_CM = DataProperty(IRI(":hasHeightInCM"));
  private static final OWLDataProperty HAS_DOB = DataProperty(IRI(":hasDOB"));
  private static final OWLDataProperty HAS_TOB = DataProperty(IRI(":hasTOB"));

  private OWLOntology ontology;
  private SQWRLQueryEngine queryEngine;

  @Before public void setUp() throws OWLOntologyCreationException
  {
    ontology = OWLManager.createOWLOntologyManager().createOntology();
    queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
  }

  @Test
  public void TestSWRLCoreLessThanBuiltInWithByte() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_BYTE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasAge(p1, ?age) ^ swrlb:lessThan(?age, \"43\"^^xsd:byte) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreGreaterThanBuiltInWithByte() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_BYTE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasAge(p1, ?age) ^ swrlb:greaterThan(?age, \"41\"^^xsd:byte) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreEqualBuiltInWithByte() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_BYTE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasAge(p1, ?age) ^ swrlb:equal(?age, \"42\"^^xsd:byte) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreLessThanBuiltInWithShort() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_SHORT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasAge(p1, ?age) ^ swrlb:lessThan(?age, \"43\"^^xsd:short) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreGreaterThanBuiltInWithShort() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_SHORT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasAge(p1, ?age) ^ swrlb:greaterThan(?age, \"41\"^^xsd:short) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreEqualBuiltInWithShort() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_SHORT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasAge(p1, ?age) ^ swrlb:equal(?age, \"42\"^^xsd:short) -> sqwrl:select(p1)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals(result.getNamedIndividual(0).getShortName(), "p1");
  }

  @Test
  public void TestSWRLCoreLessThanBuiltInWithInt() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_INT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasAge(p1, ?age) ^ swrlb:lessThan(?age, 43) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreGreaterThanBuiltInWithInt() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_INT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasAge(p1, ?age) ^ swrlb:greaterThan(?age, 41) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreEqualBuiltInWithInt() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_INT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:equal(?age, 42) -> sqwrl:select(p1)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals(result.getNamedIndividual(0).getShortName(), "p1");
  }

  @Test
  public void TestSWRLCoreLessThanBuiltInWithLong() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_LONG)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasAge(p1, ?age) ^ swrlb:lessThan(?age, \"43\"^^xsd:long) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreGreaterThanBuiltInWithLong() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_LONG)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasAge(p1, ?age) ^ swrlb:greaterThan(?age, \"41\"^^xsd:long) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreEqualBuiltInWithLong() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_LONG)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasAge(p1, ?age) ^ swrlb:equal(?age, \"42\"^^xsd:long) -> sqwrl:select(p1)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals(result.getNamedIndividual(0).getShortName(), "p1");
  }

  @Test
  public void TestSWRLCoreGreaterThanBuiltInWithFloat() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42.0", XSD_FLOAT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasAge(p1, ?age) ^ swrlb:greaterThan(?age, 41.0) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreEqualBuiltInWithFloat() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT_IN_CM, P1, Literal("177.0", XSD_FLOAT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasHeightInCM(p1, ?height) ^ swrlb:equal(?height, 177.0) -> sqwrl:select(p1)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals(result.getNamedIndividual(0).getShortName(), "p1");
  }

  @Test
  public void TestSWRLCoreLessThanBuiltInWithFloat() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42.0", XSD_FLOAT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasAge(p1, ?age) ^ swrlb:lessThan(?age, 43.0) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreLessThanBuiltInWithDouble() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42.0", XSD_DOUBLE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasAge(p1, ?age) ^ swrlb:lessThan(?age, \"43\"^^xsd:double) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreGreaterThanBuiltInWithDouble() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42.0", XSD_DOUBLE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasAge(p1, ?age) ^ swrlb:greaterThan(?age, \"41\"^^xsd:double) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreEqualBuiltInWithDouble() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT_IN_CM, P1, Literal("177.0", XSD_DOUBLE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasHeightInCM(p1, ?height) ^ swrlb:equal(?height, \"177.0\"^^xsd:double) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreEqualBuiltInWithBoolean() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(IS_FRENCH, P1, Literal("true", XSD_BOOLEAN)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "isFrench(p1, ?french) ^ swrlb:equal(?french, true)-> sqwrl:select(p1)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals(result.getNamedIndividual(0).getShortName(), "p1");
  }

  @Test
  public void TestSWRLCoreLessThanBuiltInWithString() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P1, Literal("Bob", XSD_STRING)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasName(p1, ?name) ^ swrlb:lessThan(?name, \"Fred\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreGreaterThanBuiltInWithString() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P1, Literal("Bob", XSD_STRING)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasName(p1, ?name) ^ swrlb:greaterThan(?name, \"Adam\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreEqualBuiltInWithString() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P1, Literal("Bob", XSD_STRING)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasName(p1, ?name) ^ swrlb:equal(?name, \"Bob\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreEqualBuiltInWithXSDDate() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_DOB, P1, Literal("2001-01-05", XSD_DATE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasDOB(p1, ?dob) ^ swrlb:equal(?dob, \"2001-01-05\"^^xsd:date) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreEqualBuiltInWithXSDDateTime() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_TOB, P1, Literal("2001-01-05T10:10:10", XSD_DATETIME)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasTOB(p1, ?tob) ^ swrlb:equal(?tob, \"2001-01-05T10:10:10\"^^xsd:dateTime)-> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreEqualBuiltInWithXSDDuration() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("P42Y", XSD_DURATION)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasAge(p1, ?age) ^ swrlb:equal(?age, \"P42Y\"^^xsd:duration) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreEqualBuiltInWithXSDTime() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_TOB, P1, Literal("10:10:10.33", XSD_TIME)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "hasTOB(p1, ?bt) ^ swrlb:equal(?bt, \"10:10:10.33\"^^xsd:time) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreAddBuiltInWithShort() throws SWRLParseException, SQWRLException
  {
    String query = "swrlb:add(\"4\"^^xsd:short, \"2\"^^xsd:short, \"2\"^^xsd:short) -> sqwrl:select(0)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreAddBuiltInWithInt() throws SWRLParseException, SQWRLException
  {
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:add(4, 2, 2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreAddBuiltInWithFloat() throws SWRLParseException, SQWRLException
  {
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:add(4.1, 2.1, 2.0) -> sqwrl:select(\"Yes\")");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreAddBuiltInWithLong() throws SWRLParseException, SQWRLException
  {
    String query = "swrlb:add(\"4\"^^xsd:long, \"2\"^^xsd:long, \"2\"^^xsd:long) -> sqwrl:select(\"Yes\")";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreAddBuiltInWithDoubles() throws SWRLParseException, SQWRLException
  {
    String query = "swrlb:add(\"4.0\"^^xsd:double, \"2.0\"^^xsd:double, \"2.0\"^^xsd:double)" + " -> sqwrl:select(0)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreBooleanNotBuiltIn() throws SWRLParseException, SQWRLException
  {
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:booleanNot(true, false) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreStringConcatBuiltIn() throws SWRLParseException, SQWRLException
  {
    String query = "swrlb:stringConcat(?r, \"A\", \"B\") -> sqwrl:select(?r)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
    SQWRLLiteralResultValue literal = result.getLiteral("r");
    Assert.assertTrue(literal.isString());
    Assert.assertEquals(literal.getString(), "AB");
  }

  @Test
  public void TestSWRLCoreStringEqualIgnoreCaseBuiltIn() throws SWRLParseException, SQWRLException
  {
    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "swrlb:stringEqualIgnoreCase(\"AAA\", \"aaa\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreSubStringBuiltIn() throws SWRLParseException, SQWRLException
  {
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:substring(\"123\", \"12345\", 0, 3) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreStringLengthBuiltIn() throws SWRLParseException, SQWRLException
  {
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:stringLength(3, \"ABC\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreUpperCaseBuiltIn() throws SWRLParseException, SQWRLException
  {
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:upperCase(\"ABC\", \"abc\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreLowerCaseBuiltIn() throws SWRLParseException, SQWRLException
  {
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:lowerCase(\"abc\", \"ABC\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreContainsBuiltIn() throws SWRLParseException, SQWRLException
  {
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:contains(\"abcd\", \"bc\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreContainsIgnoreCaseBuiltIn() throws SWRLParseException, SQWRLException
  {
    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "swrlb:containsIgnoreCase(\"abcd\", \"BC\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreStartsWithBuiltIn() throws SWRLParseException, SQWRLException
  {
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:startsWith(\"abcd\", \"ab\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreEndsWithBuiltIn() throws SWRLParseException, SQWRLException
  {
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:endsWith(\"abcd\", \"cd\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  // TODO Built-in implementation not correct
  // @Test
  // public void TestSWRLCoreTranslateBuiltIn() throws SWRLParseException, SQWRLException
  // {
  // SQWRLResult result = queryEngine.runSQWRLQuery("q1",
  // "swrlb:translate(\"AAA\", \"--aaa--\", \"abc-\", \"ABC\") -> sqwrl:select(0)");
  //
  // Assert.assertTrue(result.next());
  // }

  @Test
  public void TestSWRLCoreSubstringAfterBuiltIn() throws SWRLParseException, SQWRLException
  {
    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "swrlb:substringAfter(\"ddd\", \"aaaddd\", \"aaa\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreSubstringBeforeBuiltIn() throws SWRLParseException, SQWRLException
  {
    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "swrlb:substringBefore(\"aaa\", \"aaaddd\", \"ddd\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreMatchesBuiltIn() throws SWRLParseException, SQWRLException
  {
    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "swrlb:matches(\"abracadabra\", \"^a.*a$\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreReplaceBuiltIn() throws SWRLParseException, SQWRLException
  {
    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "swrlb:replace(\"a*cada*\", \"abracadabra\", \"bra\", \"*\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreNormalizeSpaceBuiltIn() throws SWRLParseException, SQWRLException
  {
    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "swrlb:normalizeSpace(\"fred\", \"  fred  \") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test
  public void TestSWRLCoreYearMonthDurationBuiltIn() throws SWRLParseException, SQWRLException
  {
    String query = "swrlb:yearMonthDuration(?x, 3, 4) -> sqwrl:select(?x)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("x").isDuration());
    Assert.assertEquals(result.getLiteral("x").getDuration(), new XSDDuration("P3Y4M"));
  }

  @Test
  public void TestSWRLCoreDayTimeDurationBuiltIn() throws SWRLParseException, SQWRLException
  {
    String query = "swrlb:dayTimeDuration(?x, 2, 3, 4, 5) -> sqwrl:select(?x)";
    SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("x").isDuration());
    Assert.assertEquals(result.getLiteral("x").getDuration(), new XSDDuration("P2DT3H4M5S"));
  }

  @Test
  public void TestSWRLCoreTimeBuiltIn() throws SWRLParseException, SQWRLException
  {
    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
      "swrlb:time(\"10:11:12\"^^xsd:time, 10, 11, 12, \"\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }
}
