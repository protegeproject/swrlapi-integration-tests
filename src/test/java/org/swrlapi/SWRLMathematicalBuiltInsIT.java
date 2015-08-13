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
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.test.IntegrationTestBase;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;

public class SWRLMathematicalBuiltInsIT extends IntegrationTestBase
{
  private static final OWLNamedIndividual P1 = NamedIndividual(IRI(":p1"));
  private static final OWLDataProperty HAS_AGE = DataProperty(IRI(":hasAge"));

  private OWLOntology ontology;
  private SQWRLQueryEngine queryEngine;

  @Before public void setUp() throws OWLOntologyCreationException
  {
    ontology = OWLManager.createOWLOntologyManager().createOntology();
    queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
  }

  @Test
  public void TestSWRLCoreLessThanBuiltInWithShort() throws SWRLParseException, SQWRLException
  {
    addOWLAxioms(ontology, DataPropertyAssertion(HAS_AGE, P1, Literal("42", XSD_INT)));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1",
        "hasAge(p1, ?age) ^ swrlm:eval(?r, \"age + 1\", ?age) -> sqwrl:select(?r)");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.getLiteral("r").isDouble());
    Assert.assertEquals(result.getLiteral("r").getDouble(), 43.0d, this.DELTA);
  }
}
