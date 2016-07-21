package org.swrlapi;

import org.junit.Assert;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.test.IntegrationTestBase;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ClassAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataAllValuesFrom;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataExactCardinality;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataHasValue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataMaxCardinality;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataMinCardinality;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataSomeValuesFrom;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectAllValuesFrom;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectComplementOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectExactCardinality;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectHasValue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectIntersectionOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectMaxCardinality;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectMinCardinality;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectSomeValuesFrom;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectUnionOf;

/**
 * NOTE: All tests are designed for parallel execution.
 */
public class SWRLClassExpressionBuiltInArgumentsIT extends IntegrationTestBase
{
  private static final OWLClass C1 = Class(iri("C1"));
  private static final OWLClass C2 = Class(iri("C2"));
  private static final OWLClass C3 = Class(iri("C3"));
  private static final OWLObjectProperty OP1 = ObjectProperty(iri("OP1"));
  private static final OWLDataProperty DP1 = DataProperty(iri("DP1"));
  private static final OWLNamedIndividual I1 = NamedIndividual(iri("i1"));
  private static final OWLDatatype D1 = OWLFunctionalSyntaxFactory.Datatype(iri("d1"));

  @Test public void TestObjectUnionOfSWRLClassExpressionBuiltInArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(ObjectUnionOf(C1, C2), I1));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:caa(?c, ?i) -> sqwrl:select(?c)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassExpressionValue(0));
    Assert.assertFalse(result.getClassExpression(0).getRendering().isEmpty());
  }

  @Test public void TestObjectComplementOfSWRLClassExpressionBuiltInArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(ObjectComplementOf(C1), I1));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:caa(?c, ?i) -> sqwrl:select(?c)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassExpressionValue(0));
    Assert.assertFalse(result.getClassExpression(0).getRendering().isEmpty());
  }

  @Test public void TestObjectSomeValuesFromSWRLClassExpressionBuiltInArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(ObjectSomeValuesFrom(OP1, C1), I1));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:caa(?c, ?i) -> sqwrl:select(?c)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassExpressionValue(0));
    Assert.assertFalse(result.getClassExpression(0).getRendering().isEmpty());
  }

  @Test public void TestObjectMinCardinalitySWRLClassExpressionBuiltInArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(ObjectMinCardinality(1, OP1, C1), I1));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:caa(?c, ?i) -> sqwrl:select(?c)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassExpressionValue(0));
    Assert.assertFalse(result.getClassExpression(0).getRendering().isEmpty());
  }

  @Test public void TestObjectMaxCardinalitySWRLClassExpressionBuiltInArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(ObjectMaxCardinality(1, OP1, C1), I1));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:caa(?c, ?i) -> sqwrl:select(?c)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassExpressionValue(0));
    Assert.assertFalse(result.getClassExpression(0).getRendering().isEmpty());
  }

  @Test public void TestObjectExactCardinalitySWRLClassExpressionBuiltInArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(ObjectExactCardinality(1, OP1, C1), I1));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:caa(?c, ?i) -> sqwrl:select(?c)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassExpressionValue(0));
    Assert.assertFalse(result.getClassExpression(0).getRendering().isEmpty());
  }

  @Test public void TestObjectAllValuesFromSWRLClassExpressionBuiltInArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(ObjectAllValuesFrom(OP1, C1), I1));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:caa(?c, ?i) -> sqwrl:select(?c)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassExpressionValue(0));
    Assert.assertFalse(result.getClassExpression(0).getRendering().isEmpty());
  }

  @Test public void TestObjectHasValueFromSWRLClassExpressionBuiltInArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(ObjectHasValue(OP1, I1), I1));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:caa(?c, ?i) -> sqwrl:select(?c)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassExpressionValue(0));
    Assert.assertFalse(result.getClassExpression(0).getRendering().isEmpty());
  }

  @Test public void TestObjectIntersectionOfSWRLClassExpressionBuiltInArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(ObjectIntersectionOf(C1, C2, C3), I1));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:caa(?c, ?i) -> sqwrl:select(?c)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassExpressionValue(0));
    Assert.assertFalse(result.getClassExpression(0).getRendering().isEmpty());
  }

  @Test public void TestDataMinCardinalitySWRLClassExpressionBuiltInArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(DataMinCardinality(1, DP1, D1), I1));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:caa(?c, ?i) -> sqwrl:select(?c)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassExpressionValue(0));
    Assert.assertFalse(result.getClassExpression(0).getRendering().isEmpty());
  }

  @Test public void TestDataMaxCardinalitySWRLClassExpressionBuiltInArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(DataMaxCardinality(1, DP1, D1), I1));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:caa(?c, ?i) -> sqwrl:select(?c)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassExpressionValue(0));
    Assert.assertFalse(result.getClassExpression(0).getRendering().isEmpty());
  }

  @Test public void TestDataExactCardinalitySWRLClassExpressionBuiltInArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(DataExactCardinality(1, DP1, D1), I1));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:caa(?c, ?i) -> sqwrl:select(?c)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassExpressionValue(0));
    Assert.assertFalse(result.getClassExpression(0).getRendering().isEmpty());
  }

  @Test public void TestDataAllValuesFromSWRLClassExpressionBuiltInArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(DataAllValuesFrom(DP1, D1), I1));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:caa(?c, ?i) -> sqwrl:select(?c)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassExpressionValue(0));
    Assert.assertFalse(result.getClassExpression(0).getRendering().isEmpty());
  }

  @Test public void TestDataHasValueSWRLClassExpressionBuiltInArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(DataHasValue(DP1, Literal("42", XSD_BYTE)), I1));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:caa(?c, ?i) -> sqwrl:select(?c)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassExpressionValue(0));
    Assert.assertFalse(result.getClassExpression(0).getRendering().isEmpty());
  }

  @Test public void TestDataSomeValuesFromSWRLClassExpressionBuiltInArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(DataSomeValuesFrom(DP1, D1), I1));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:caa(?c, ?i) -> sqwrl:select(?c)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassExpressionValue(0));
    Assert.assertFalse(result.getClassExpression(0).getRendering().isEmpty());
  }
}

