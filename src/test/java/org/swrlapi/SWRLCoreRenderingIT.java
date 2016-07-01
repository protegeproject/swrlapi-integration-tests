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
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.SWRLRuleRenderer;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.test.IntegrationTestBase;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ClassAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.OWLNothing;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.OWLThing;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyAssertion;

/**
 * NOTE: All tests are designed for parallel execution.
 */
public class SWRLCoreRenderingIT extends IntegrationTestBase
{
  private static final OWLClass MALE = Class(iri("Male"));
  private static final OWLNamedIndividual P1 = NamedIndividual(iri("p1"));
  private static final OWLNamedIndividual P2 = NamedIndividual(iri("p2"));
  private static final OWLObjectProperty HAS_UNCLE = ObjectProperty(iri("hasUncle"));
  private static final OWLDataProperty HAS_AGE = DataProperty(iri("hasAge"));
  private static final OWLDataProperty HAS_HEIGHT = DataProperty(iri("hasHeight"));
  private static final OWLDataProperty HAS_NAME = DataProperty(iri("hasName"));
  private static final OWLDataProperty IS_FRENCH = DataProperty(iri("isFrench"));

  @Test public void TestSWRLCoreRenderOWLThingClassAtom()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    addOWLAxioms(ontology, ClassAssertion(OWLThing(), P1));

    String ruleText = "owl:Thing(p1) -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(ruleText, ruleRendering);
  }

  @Test public void TestSWRLCoreRenderOWLNothingClassAtom()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    addOWLAxioms(ontology, ClassAssertion(OWLNothing(), P1));

    String ruleText = "owl:Nothing(p1) -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(ruleText, ruleRendering);
  }

  @Test public void TestSWRLCoreRenderClassAtomWithVariable()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    addOWLAxioms(ontology, Declaration(MALE));

    String ruleText = "Male(?m) -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(ruleText, ruleRendering);
  }

  @Test public void TestSWRLCoreRenderClassAtomWithNamedIndividual()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    addOWLAxioms(ontology, ClassAssertion(MALE, P1));

    String ruleText = "Male(p1) -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(ruleText, ruleRendering);
  }

  @Test public void TestSWRLCoreRenderObjectPropertyAtomWithNamedIndividualArguments()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    addOWLAxioms(ontology, ObjectPropertyAssertion(HAS_UNCLE, P1, P2));

    String ruleText = "hasUncle(p1, p2) -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(ruleText, ruleRendering);
  }

  @Test public void TestSWRLCoreRenderObjectPropertyAtomWithNamedIndividualFirstArgument()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    addOWLAxioms(ontology, Declaration(HAS_UNCLE));
    addOWLAxioms(ontology, Declaration(P1));

    String ruleText = "hasUncle(p1, ?uncle) -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(ruleText, ruleRendering);
  }

  @Test public void TestSWRLCoreRenderObjectPropertyAtomWithNamedIndividualSecondArgument()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    addOWLAxioms(ontology, Declaration(HAS_UNCLE));
    addOWLAxioms(ontology, Declaration(P2));

    String ruleText = "hasUncle(?person, p2) -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(ruleText, ruleRendering);
  }

  @Test public void TestSWRLCoreRenderObjectPropertyAtomWithVariableArguments()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    addOWLAxioms(ontology, Declaration(HAS_UNCLE));

    String ruleText = "hasUncle(?person, ?uncle) -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(ruleText, ruleRendering);
  }

  @Test public void TestSWRLCoreRenderDataPropertyAtomWithStringLiteral()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    addOWLAxioms(ontology, Declaration(HAS_NAME));
    addOWLAxioms(ontology, Declaration(P1));

    String ruleText = "hasName(p1, \"Bob\") -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(ruleText, ruleRendering);
  }

  @Test public void TestSWRLCoreRenderDataPropertyAtomWithQualifiedStringLiteral()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    addOWLAxioms(ontology, Declaration(HAS_NAME));
    addOWLAxioms(ontology, Declaration(P1));

    String ruleText = "hasName(p1, \"Bob\"^^xsd:string) -> ";
    String expectedRendering = "hasName(p1, \"Bob\") -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(expectedRendering, ruleRendering);
  }

  @Test public void TestSWRLCoreRenderDataPropertyAtomWithBooleanTrueLiteral()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    addOWLAxioms(ontology, Declaration(IS_FRENCH));
    addOWLAxioms(ontology, Declaration(P1));

    String ruleText = "isFrench(p1, true) -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(ruleText, ruleRendering);
  }

  @Test public void TestSWRLCoreRenderDataPropertyAtomWithQualifiedBooleanTrueLiteral()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    addOWLAxioms(ontology, Declaration(IS_FRENCH));
    addOWLAxioms(ontology, Declaration(P1));

    String ruleText = "isFrench(p1, \"true\"^^xsd:boolean) -> ";
    String expectedRendering = "isFrench(p1, true) -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(expectedRendering, ruleRendering);
  }

  @Test public void TestSWRLCoreRenderDataPropertyAtomWithBooleanFalseLiteral()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    addOWLAxioms(ontology, Declaration(IS_FRENCH));
    addOWLAxioms(ontology, Declaration(P1));

    String ruleText = "isFrench(p1, false) -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(ruleText, ruleRendering);
  }

  @Test public void TestSWRLCoreRenderDataPropertyAtomWithQualifiedBooleanFalseLiteral()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    addOWLAxioms(ontology, Declaration(IS_FRENCH));
    addOWLAxioms(ontology, Declaration(P1));

    String ruleText = "isFrench(p1, \"false\"^^xsd:boolean) -> ";
    String expectedRendering = "isFrench(p1, false) -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(expectedRendering, ruleRendering);
  }

  @Test public void TestSWRLCoreRenderDataPropertyAtomWithIntLiteral()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    addOWLAxioms(ontology, Declaration(HAS_AGE));
    addOWLAxioms(ontology, Declaration(P1));

    String ruleText = "hasAge(p1, 23) -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(ruleText, ruleRendering);
  }

  @Test public void TestSWRLCoreRenderDataPropertyAtomWithQualifiedIntLiteral()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    addOWLAxioms(ontology, Declaration(HAS_AGE));
    addOWLAxioms(ontology, Declaration(P1));

    String ruleText = "hasAge(p1, \"23\"^^xsd:int) -> ";
    String expectedRendering = "hasAge(p1, 23) -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(expectedRendering, ruleRendering);
  }


  @Test public void TestSWRLCoreRenderDataPropertyAtomWithFloatLiteral()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    addOWLAxioms(ontology, Declaration(HAS_HEIGHT));
    addOWLAxioms(ontology, Declaration(P1));

    String ruleText = "hasHeight(p1, 130.3) -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(ruleText, ruleRendering);
  }

  @Test public void TestSWRLCoreRenderDataPropertyAtomWithQualifiedFloatLiteral()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    addOWLAxioms(ontology, Declaration(HAS_HEIGHT));
    addOWLAxioms(ontology, Declaration(P1));

    String ruleText = "hasHeight(p1, \"130.3\"^^xsd:float) -> ";
    String expectedRendering = "hasHeight(p1, 130.3) -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(expectedRendering, ruleRendering);
  }

  @Test public void TestSWRLCoreRenderSameAsAtomWithVariables()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    String ruleText = "sameAs(?s1, ?s2) -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(ruleText, ruleRendering);
  }

  @Test public void TestSWRLCoreRenderSameAsAtomWithNamedIndividualFirstArgument()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    addOWLAxioms(ontology, Declaration(P1));

    String ruleText = "sameAs(p1, ?s2) -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(ruleText, ruleRendering);
  }

  @Test public void TestSWRLCoreRenderSameAsAtomWithNamedIndividualSecondArgument()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    addOWLAxioms(ontology, Declaration(P2));

    String ruleText = "sameAs(?s1, p2) -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(ruleText, ruleRendering);
  }

  @Test public void TestSWRLCoreRenderSameAsAtomWithNamedIndividualArguments()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    addOWLAxioms(ontology, Declaration(P1));
    addOWLAxioms(ontology, Declaration(P2));

    String ruleText = "sameAs(p1, p2) -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(ruleText, ruleRendering);
  }

  @Test public void TestSWRLCoreRenderDifferentFromAtomWithVariables()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    String ruleText = "differentFrom(?s1, ?s2) -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(ruleText, ruleRendering);
  }

  @Test public void TestSWRLCoreRenderDifferentFromAtomWithNamedIndividualFirstArgument()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    addOWLAxioms(ontology, Declaration(P1));

    String ruleText = "differentFrom(p1, ?s2) -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(ruleText, ruleRendering);
  }

  @Test public void TestSWRLCoreRenderDifferentFromAtomWithNamedIndividualSecondArgument()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    addOWLAxioms(ontology, Declaration(P2));

    String ruleText = "differentFrom(?s1, p2) -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(ruleText, ruleRendering);
  }

  @Test public void TestSWRLCoreRenderDifferentFromAtomWithNamedIndividualArguments()
    throws SWRLParseException, SWRLBuiltInException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
    SWRLRuleRenderer ruleRenderer = ruleEngine.createSWRLRuleRenderer();

    addOWLAxioms(ontology, Declaration(P1));
    addOWLAxioms(ontology, Declaration(P2));

    String ruleText = "differentFrom(p1, p2) -> ";
    SWRLAPIRule rule = ruleEngine.createSWRLRule("R1", ruleText);
    String ruleRendering = ruleRenderer.renderSWRLRule(rule);
    Assert.assertEquals(ruleText, ruleRendering);
  }
}
