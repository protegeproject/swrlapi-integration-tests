package org.swrlapi.sqwrl;

import org.junit.Assert;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.test.IntegrationTestBase;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;

/**
 * NOTE: All tests are designed for parallel execution.
 */
public class SQWRLCollectionRenderingIT extends IntegrationTestBase
{
  private static final OWLClass DRUG = Class(iri("Drug"));
  private static final OWLNamedIndividual DDI = NamedIndividual(iri("DDI"));
  private static final OWLNamedIndividual AZT = NamedIndividual(iri("AZT"));

  @Test public void TestSQWRLCollectionRenderingWithEmptySWRLAndCollectionOperationClauses()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
    SQWRLQueryRenderer queryRenderer = queryEngine.createSQWRLQueryRenderer();

    addOWLAxioms(ontology, Declaration(DDI));

    String queryText = ". sqwrl:makeBag(?s1, DDI) . ->";
    String expectedRendering = " " + SWRLParser.RING_CHAR + " sqwrl:makeBag(?s1, DDI) -> ";
    SQWRLQuery query = queryEngine.createSQWRLQuery("q1", queryText);
    String queryRendering = queryRenderer.renderSQWRLQuery(query);
    Assert.assertEquals(expectedRendering, queryRendering);
  }

  @Test public void TestSQWRLCollectionRenderingWithEmptySWRLAndCollectionOperationClausesNoLeadingRing()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
    SQWRLQueryRenderer queryRenderer = queryEngine.createSQWRLQueryRenderer();

    addOWLAxioms(ontology, Declaration(DDI));

    String queryText = "sqwrl:makeBag(?s1, DDI) . ->";
    String expectedRendering = " " + SWRLParser.RING_CHAR + " sqwrl:makeBag(?s1, DDI) -> ";
    SQWRLQuery query = queryEngine.createSQWRLQuery("q1", queryText);
    String queryRendering = queryRenderer.renderSQWRLQuery(query);
    Assert.assertEquals(expectedRendering, queryRendering);
  }

  @Test public void TestSQWRLCollectionRenderingWithEmptySWRLAndCollectionOperationClausesNoTrailingRing()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
    SQWRLQueryRenderer queryRenderer = queryEngine.createSQWRLQueryRenderer();

    addOWLAxioms(ontology, Declaration(DDI));

    String queryText = ". sqwrl:makeBag(?s1, DDI) ->";
    String expectedRendering = " " + SWRLParser.RING_CHAR + " sqwrl:makeBag(?s1, DDI) -> ";
    SQWRLQuery query = queryEngine.createSQWRLQuery("q1", queryText);
    String queryRendering = queryRenderer.renderSQWRLQuery(query);
    Assert.assertEquals(expectedRendering, queryRendering);
  }

  @Test public void TestSQWRLCollectionRenderingWithEmptySWRLAndCollectionOperationClausesNoRings()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
    SQWRLQueryRenderer queryRenderer = queryEngine.createSQWRLQueryRenderer();

    addOWLAxioms(ontology, Declaration(DDI));

    String queryText = "sqwrl:makeBag(?s1, DDI) ->";
    String expectedRendering = " " + SWRLParser.RING_CHAR + " sqwrl:makeBag(?s1, DDI) -> ";
    SQWRLQuery query = queryEngine.createSQWRLQuery("q1", queryText);
    String queryRendering = queryRenderer.renderSQWRLQuery(query);
    Assert.assertEquals(expectedRendering, queryRendering);
  }

  @Test public void TestSQWRLCollectionRenderingWithEmptySWRLClause()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
    SQWRLQueryRenderer queryRenderer = queryEngine.createSQWRLQueryRenderer();

    addOWLAxioms(ontology, Declaration(DDI));

    String queryText = ". sqwrl:makeBag(?s1, DDI) . sqwrl:size(?size, ?s1) ->";
    String expectedRendering =
      " " + SWRLParser.RING_CHAR + " sqwrl:makeBag(?s1, DDI) " + SWRLParser.RING_CHAR + " sqwrl:size(?size, ?s1) -> ";
    SQWRLQuery query = queryEngine.createSQWRLQuery("q1", queryText);
    String queryRendering = queryRenderer.renderSQWRLQuery(query);
    Assert.assertEquals(expectedRendering, queryRendering);
  }

  @Test public void TestSQWRLCollectionRenderingWithEmptyOperationClause()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
    SQWRLQueryRenderer queryRenderer = queryEngine.createSQWRLQueryRenderer();

    addOWLAxioms(ontology, Declaration(DRUG), Declaration(DDI));

    String queryText = "Drug(DDI) . sqwrl:makeBag(?s1, DDI) . -> ";
    String expectedRendering = "Drug(DDI) " + SWRLParser.RING_CHAR + " sqwrl:makeBag(?s1, DDI) -> ";
    SQWRLQuery query = queryEngine.createSQWRLQuery("q1", queryText);
    String queryRendering = queryRenderer.renderSQWRLQuery(query);
    Assert.assertEquals(expectedRendering, queryRendering);
  }

  @Test public void TestSQWRLCollectionRenderingWithNonEmptySWRLCollectionAndOperationClauses()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
    SQWRLQueryRenderer queryRenderer = queryEngine.createSQWRLQueryRenderer();

    addOWLAxioms(ontology, Declaration(DRUG), Declaration(DDI));

    String queryText = "Drug(DDI) . sqwrl:makeBag(?s1, DDI) . sqwrl:size(?size, ?s1) -> ";
    String expectedRendering = "Drug(DDI) " + SWRLParser.RING_CHAR + " sqwrl:makeBag(?s1, DDI) " + SWRLParser.RING_CHAR
      + " sqwrl:size(?size, ?s1) -> ";
    SQWRLQuery query = queryEngine.createSQWRLQuery("q1", queryText);
    String queryRendering = queryRenderer.renderSQWRLQuery(query);
    Assert.assertEquals(expectedRendering, queryRendering);
  }

  @Test public void TestSQWRLCollectionRenderingWithMultiAtomSWRLCollectionAndOperationClauses()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
    SQWRLQueryRenderer queryRenderer = queryEngine.createSQWRLQueryRenderer();

    addOWLAxioms(ontology, Declaration(DRUG), Declaration(DDI), Declaration(AZT));

    String queryText = "Drug(DDI) ^ Drug(AZT) . sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeBag(?s2, AZT) "
      + " . sqwrl:size(?size, ?s1) ^ sqwrl:equal(?s1, ?s2) -> ";
    String expectedRendering =
      "Drug(DDI) ^ Drug(AZT) " + SWRLParser.RING_CHAR + " sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeBag(?s2, AZT) "
        + SWRLParser.RING_CHAR + " sqwrl:size(?size, ?s1) ^ sqwrl:equal(?s1, ?s2) -> ";
    SQWRLQuery query = queryEngine.createSQWRLQuery("q1", queryText);
    String queryRendering = queryRenderer.renderSQWRLQuery(query);
    Assert.assertEquals(expectedRendering, queryRendering);
  }
}
