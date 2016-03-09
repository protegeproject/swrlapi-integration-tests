package org.swrlapi;

import org.junit.Assert;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.literal.XSDDate;
import org.swrlapi.literal.XSDDateTime;
import org.swrlapi.literal.XSDDuration;
import org.swrlapi.literal.XSDTime;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.test.IntegrationTestBase;

import java.net.URI;
import java.net.URISyntaxException;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ClassAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DifferentIndividuals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.OWLNothing;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.OWLThing;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SameIndividual;

/**
 * NOTE: All tests are designed for parallel execution.
 */
public class SWRLCoreIT extends IntegrationTestBase
{
  private static final OWLClass MALE = Class(iri("Male"));
  private static final OWLNamedIndividual P1 = NamedIndividual(iri("p1"));
  private static final OWLNamedIndividual P2 = NamedIndividual(iri("p2"));
  private static final OWLObjectProperty HAS_UNCLE = ObjectProperty(iri("hasUncle"));
  private static final OWLDataProperty YEAR_OFFSET_TO_BIRTH = DataProperty(iri("yearOffsetToBirth"));
  private static final OWLDataProperty HAS_AGE = DataProperty(iri("hasAge"));
  private static final OWLDataProperty HAS_TOB = DataProperty(iri("hasTOB"));
  private static final OWLDataProperty HAS_DOB = DataProperty(iri("hasDOB"));
  private static final OWLDataProperty HAS_HOMEPAGE = DataProperty(iri("hasHomePage"));
  private static final OWLDataProperty HAS_NAME = DataProperty(iri("hasName"));
  private static final OWLDataProperty IS_FRENCH = DataProperty(iri("isFrench"));
  private static final OWLDataProperty HAS_HEIGHT_IN_CM = DataProperty(iri("hasHeightInCM"));
  private static final OWLDataProperty HAS_HEIGHT = DataProperty(iri("hasHeight"));
  private static final OWLDataProperty HEIGHT_OFFET_IN_CM = DataProperty(iri("heightOffsetInCM"));

  @Test public void TestSWRLCoreOWLThingClassAtomInAntecedentWithNamedIndividual()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(OWLThing(), P1));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "owl:Thing(p1) -> sqwrl:select(p1)");

    Assert.assertTrue(result.next());
    Assert.assertEquals("p1", result.getNamedIndividual(0).getShortName());
  }

  @Test public void TestSWRLCoreOWLNothingClassAtomInAntecedentWithNamedIndividual()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(OWLNothing(), P1));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "owl:Nothing(p1) -> sqwrl:select(p1)");

    Assert.assertTrue(result.next());
    Assert.assertEquals("p1", result.getNamedIndividual(0).getShortName());
  }

  @Test public void TestSWRLCoreClassAtomInAntecedentWithNamedIndividual()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(MALE, P1));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "Male(p1) -> sqwrl:select(p1)");

    Assert.assertTrue(result.next());
    Assert.assertEquals("p1", result.getNamedIndividual(0).getShortName());
  }

  @Test public void TestSWRLCoreClassAtomInAntecedentWithVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(MALE, P1));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "Male(?m) -> sqwrl:select(?m)");

    Assert.assertTrue(result.next());
    Assert.assertEquals("p1", result.getNamedIndividual("m").getShortName());
  }

  @Test public void TestSWRLCoreSameAs() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, SameIndividual(P1, P2));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "sameAs(p1, p2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreDifferentFrom() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DifferentIndividuals(P1, P2));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "differentFrom(p1, p2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreIndividualShortNameMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ObjectPropertyAssertion(HAS_UNCLE, P1, P2));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasUncle(p1, p2) -> sqwrl:select(p1, p2)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual(0).getShortName());
    Assert.assertTrue(result.getNamedIndividual(1).isNamedIndividual());
    Assert.assertEquals("p2", result.getNamedIndividual(1).getShortName());
  }

  @Test public void TestSWRLCoreIndividualShortNameBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ObjectPropertyAssertion(HAS_UNCLE, P1, P2));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasUncle(p1, ?uncle) -> sqwrl:select(?uncle)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual("uncle").isNamedIndividual());
    Assert.assertEquals("p2", result.getNamedIndividual("uncle").getShortName());
  }

  @Test public void TestSWRLCoreRawBooleanLiteralTrueMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(IS_FRENCH, P1, Literal("true", XSD_BOOLEAN)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "isFrench(p1, true) -> sqwrl:select(p1)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual(0).getShortName());
  }

  @Test public void TestSWRLCoreQualifiedBooleanLiteralTrueMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(IS_FRENCH, P1, Literal("true", XSD_BOOLEAN)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "isFrench(p1, \"true\"^^xsd:boolean) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreRawBooleanLiteralFalseMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(IS_FRENCH, P1, Literal("false", XSD_BOOLEAN)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "isFrench(p1, false) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreQualifiedBooleanLiteralFalseMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(IS_FRENCH, P1, Literal("false", XSD_BOOLEAN)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "isFrench(p1, \"false\"^^xsd:boolean) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreBooleanLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(IS_FRENCH, P1, Literal("true", XSD_BOOLEAN)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "isFrench(p1, ?french) -> sqwrl:select(?french)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("french").isBoolean());
    Assert.assertEquals(true, result.getLiteral("french").getBoolean());
  }

  @Test public void TestSWRLCoreStringLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P1, Literal("Bob", XSD_STRING)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasName(p1, ?name) -> sqwrl:select(?name)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("name").isString());
    Assert.assertEquals("Bob", result.getLiteral("name").getString());
  }

  @Test public void TestSWRLCoreRawStringLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P1, Literal("Bob", XSD_STRING)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasName(p1, \"Bob\") -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreQualifiedStringLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P1, Literal("Bob", XSD_STRING)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasName(p1, \"Bob\"^^xsd:string) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreURILiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String homePage = "http://stanford.edu/~fred";

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HOMEPAGE, P1, Literal(homePage, XSD_ANY_URI)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasHomePage(p1, \"" + homePage + "\"^^xsd:anyURI) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreURILiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException, URISyntaxException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String homePage = "http://stanford.edu/~fred";

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HOMEPAGE, P1, Literal(homePage, XSD_ANY_URI)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasHomePage(p1, ?home) -> sqwrl:select(?home)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("home").isAnyURI());
    Assert.assertEquals(new URI(homePage), result.getLiteral("home").getAnyURI());
  }

  @Test public void TestSWRLCoreXSDDateLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_DOB, P1, Literal("2001-01-05", XSD_DATE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasDOB(p1, \"2001-01-05\"^^xsd:date) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreXSDDateLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_DOB, P1, Literal("2001-01-05", XSD_DATE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasDOB(p1, ?dob) -> sqwrl:select(?dob)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("dob").isDate());
    Assert.assertEquals(new XSDDate("2001-01-05"), result.getLiteral("dob").getDate());
  }

  @Test public void TestSWRLCoreXSDDateTimeLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_TOB, P1, Literal("2001-01-05T10:10:10", XSD_DATETIME)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasTOB(p1, \"2001-01-05T10:10:10\"^^xsd:dateTime) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreXSDDateTimeLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_TOB, P1, Literal("2001-01-05T10:10:10", XSD_DATETIME)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasTOB(p1, ?tob) -> sqwrl:select(?tob)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("tob").isDateTime());
    Assert.assertEquals(new XSDDateTime("2001-01-05T10:10:10"), result.getLiteral("tob").getDateTime());
  }

  @Test public void TestSWRLCoreXSDDurationLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("P42Y", XSD_DURATION)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, \"P42Y\"^^xsd:duration) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreXSDDurationLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("P42Y", XSD_DURATION)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) -> sqwrl:select(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("age").isDuration());
    Assert.assertEquals(new XSDDuration("P42Y"), result.getLiteral("age").getDuration());
  }

  @Test public void TestSWRLCoreXSDTimeLiteralMatch()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_TOB, P1, Literal("10:10:10.33", XSD_TIME)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasTOB(p1, \"10:10:10.33\"^^xsd:time) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLCoreXSDTimeLiteralBind()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_TOB, P1, Literal("10:10:10.33", XSD_TIME)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasTOB(p1, ?bt) -> sqwrl:select(p1, ?bt)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getNamedIndividual(0).isNamedIndividual());
    Assert.assertEquals("p1", result.getNamedIndividual(0).getShortName());
    Assert.assertTrue(result.getLiteral("bt").isTime());
    Assert.assertEquals(new XSDTime("10:10:10.33"), result.getLiteral("bt").getTime());
  }

  @Test public void TestSWRLCoreCascadingBooleanVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(IS_FRENCH, P1, Literal("true", XSD_BOOLEAN)),
      DataPropertyAssertion(IS_FRENCH, P2, Literal("true", XSD_BOOLEAN)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "isFrench(p1, ?f) ^ isFrench(p2, ?f) -> sqwrl:select(?f)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("f").isBoolean());
    Assert.assertEquals(true, result.getLiteral("f").getBoolean());
  }

  @Test public void TestSWRLCoreCascadingStringVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_NAME, P1, Literal("Bob", XSD_STRING)),
      DataPropertyAssertion(HAS_NAME, P2, Literal("Bob", XSD_STRING)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasName(p1, ?name) ^ hasName(p2, ?name) -> sqwrl:select(?name)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("name").isString());
    Assert.assertEquals("Bob", result.getLiteral("name").getString());
  }

  @Test public void TestSWRLCoreCascadingURIVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException, URISyntaxException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    String homePage = "http://stanford.edu/~fred";

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HOMEPAGE, P1, Literal(homePage, XSD_ANY_URI)),
      DataPropertyAssertion(HAS_HOMEPAGE, P2, Literal(homePage, XSD_ANY_URI)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasHomePage(p1, ?home) ^ hasHomePage(p2, ?home) -> sqwrl:select(?home)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("home").isAnyURI());
    Assert.assertEquals(new URI(homePage), result.getLiteral("home").getAnyURI());
  }

  @Test public void TestSWRLCoreCascadingXSDDateVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_DOB, P1, Literal("1999-01-02", XSD_DATE)),
      DataPropertyAssertion(HAS_DOB, P2, Literal("1999-01-02", XSD_DATE)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasDOB(p1, ?dob) ^ hasDOB(p2, ?dob) -> sqwrl:select(?dob)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("dob").isDate());
    Assert.assertEquals(new XSDDate("1999-01-02"), result.getLiteral("dob").getDate());
  }

  @Test public void TestSWRLCoreCascadingXSDDateTimeVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_TOB, P1, Literal("1999-01-02T10:10:10", XSD_DATETIME)),
      DataPropertyAssertion(HAS_TOB, P2, Literal("1999-01-02T10:10:10", XSD_DATETIME)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasTOB(p1, ?tob) ^ hasTOB(p2, ?tob) -> sqwrl:select(?tob)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("tob").isDateTime());
    Assert.assertEquals(new XSDDateTime("1999-01-02T10:10:10"), result.getLiteral("tob").getDateTime());
  }

  @Test public void TestSWRLCoreCascadingXSDTimeVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_TOB, P1, Literal("10:10:10", XSD_TIME)),
      DataPropertyAssertion(HAS_TOB, P2, Literal("10:10:10", XSD_TIME)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasTOB(p1, ?tob) ^ hasTOB(p2, ?tob) -> sqwrl:select(?tob)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("tob").isTime());
    Assert.assertEquals(new XSDTime("10:10:10"), result.getLiteral("tob").getTime());
  }

  @Test public void TestSWRLCoreCascadingXSDDurationVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("P42Y", XSD_DURATION)),
      DataPropertyAssertion(HAS_AGE, P2, Literal("P42Y", XSD_DURATION)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "hasAge(p1, ?age) ^ hasAge(p2, ?age) -> sqwrl:select(?age)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("age").isDuration());
    Assert.assertEquals(new XSDDuration("P42Y"), result.getLiteral("age").getDuration());
  }
}
