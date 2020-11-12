package org.swrlapi;

import org.junit.Assert;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.test.IntegrationTestBase;

import java.math.BigDecimal;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;

/**
 * NOTE: All tests are designed for parallel execution.
 *
 * @see org.swrlapi.builtins.swrlm.SWRLBuiltInLibraryImpl
 */
public class SWRLMathematicalBuiltInsIT extends IntegrationTestBase
{
  private static final OWLNamedIndividual P1 = NamedIndividual(iri("p1"));
  private static final OWLDataProperty HAS_HEIGHT = DataProperty(iri("hasHeight"));

  @Test public void TestSWRLMathematicalLogBuiltIn()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("100", OWL2Datatype.XSD_DECIMAL)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasHeight(p1, ?height) ^ swrlm:log(?r, ?height) -> sqwrl:select(?r)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("r").isDecimal());
    Assert.assertEquals(BigDecimal.valueOf(2), result.getLiteral("r").getDecimal());
  }

  @Test public void TestSWRLMathematicalSqrtBuiltIn()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("100", OWL2Datatype.XSD_DECIMAL)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasHeight(p1, ?height) ^ swrlm:sqrt(?r, ?height) -> sqwrl:select(?r)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("r").isDecimal());
    Assert.assertEquals(BigDecimal.valueOf(10.0), result.getLiteral("r").getDecimal());
  }

  @Test public void TestSWRLMathematicalEvalBuiltIn()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(HAS_HEIGHT, P1, Literal("100", OWL2Datatype.XSD_DOUBLE)));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "hasHeight(p1, ?height) ^ swrlm:eval(?r, \"height + 1\", ?height) -> sqwrl:select(?r)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("r").isDouble());
    Assert.assertEquals(Double.parseDouble("101"), result.getLiteral("r").getDouble(), IntegrationTestBase.DELTA);
  }

}

//  SQWRLResult result = queryEngine
//    .runSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlm:eval(?r, \"age + 1\", ?age) -> sqwrl:select(?r)");
