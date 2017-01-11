package org.swrlapi;

import org.junit.Assert;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
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

import java.math.BigInteger;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ClassAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DifferentIndividuals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NegativeDataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NegativeObjectPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SameIndividual;

/**
 * NOTE: All tests are designed for parallel execution.
 *
 * @see org.swrlapi.builtins.abox.SWRLBuiltInLibraryImpl
 */
public class SWRLABoxBuiltInsIT extends IntegrationTestBase
{
  private static final OWLClass C1 = Class(iri("C1"));
  private static final OWLClass C2 = Class(iri("C2"));
  private static final OWLClass C3 = Class(iri("C3"));
  private static final OWLNamedIndividual NI1 = NamedIndividual(iri("ni1"));
  private static final OWLNamedIndividual NI2 = NamedIndividual(iri("ni2"));
  private static final OWLNamedIndividual NI3 = NamedIndividual(iri("ni3"));
  private static final OWLNamedIndividual NI4 = NamedIndividual(iri("ni4"));
  private static final OWLObjectProperty OP1 = ObjectProperty(iri("op1"));
  private static final OWLObjectProperty OP2 = ObjectProperty(iri("op2"));
  private static final OWLObjectProperty OP3 = ObjectProperty(iri("op3"));
  private static final OWLDataProperty DP1 = DataProperty(iri("dp1"));
  private static final OWLDataProperty DP2 = DataProperty(iri("dp2"));
  private static final OWLDataProperty DP3 = DataProperty(iri("dp3"));
  private static final OWLLiteral L1INTEGER = Literal("1", XSD_INTEGER);
  private static final OWLLiteral L18INTEGER = Literal("18", XSD_INTEGER);
  private static final OWLLiteral L123INTEGER = Literal("123", XSD_INTEGER);

  @Test public void TestSWRLABoxCAABuiltInWithAllUnboundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(C1, NI1));
    addOWLAxioms(ontology, ClassAssertion(C2, NI2));
    addOWLAxioms(ontology, ClassAssertion(C3, NI3));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "abox:caa(?c, ?i) -> sqwrl:select(?c, ?i) ^ sqwrl:orderBy(?c, ?i)");

    Assert.assertEquals(3, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertEquals("C1", result.getClass(0).getShortName());
    Assert.assertTrue(result.hasNamedIndividualValue(1));
    Assert.assertEquals("ni1", result.getNamedIndividual(1).getShortName());
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertEquals("C2", result.getClass(0).getShortName());
    Assert.assertTrue(result.hasNamedIndividualValue(1));
    Assert.assertEquals("ni2", result.getNamedIndividual(1).getShortName());
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertEquals("C3", result.getClass(0).getShortName());
    Assert.assertTrue(result.hasNamedIndividualValue(1));
    Assert.assertEquals("ni3", result.getNamedIndividual(1).getShortName());
  }

  @Test public void TestSWRLABoxCAABuiltInWithAllBoundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(C1, NI1));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:caa(C1, ni1) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLABoxCAABuiltInWithBoundClassAndUnboundNamedIndividual()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(C1, NI1));
    addOWLAxioms(ontology, ClassAssertion(C1, NI2));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:caa(C1, ?i) -> sqwrl:select(?i) ^ sqwrl:orderBy(?i)");

    Assert.assertEquals(2, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertEquals("ni1", result.getNamedIndividual(0).getShortName());
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertEquals("ni2", result.getNamedIndividual(0).getShortName());
  }

  @Test public void TestSWRLABoxCAABuiltInWithUnboundClassAndBoundNamedIndividual()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ClassAssertion(C1, NI1));
    addOWLAxioms(ontology, ClassAssertion(C2, NI1));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:caa(?c, ni1) -> sqwrl:select(?c) ^ sqwrl:orderBy(?c)");

    Assert.assertEquals(2, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertEquals("C1", result.getClass(0).getShortName());
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertEquals("C2", result.getClass(0).getShortName());
  }

  @Test public void TestSWRLABoxSIABuiltInWithAllUnboundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, SameIndividual(NI1, NI2));
    addOWLAxioms(ontology, SameIndividual(NI2, NI3));
    addOWLAxioms(ontology, SameIndividual(NI3, NI4));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "abox:sia(?i1, ?i2) -> sqwrl:select(?i1, ?i2) ^ sqwrl:orderBy(?i1, ?i2)");

    Assert.assertEquals(3, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasNamedIndividualValue(1));
    Assert.assertEquals("ni1", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("ni2", result.getNamedIndividual(1).getShortName());
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasNamedIndividualValue(1));
    Assert.assertEquals("ni2", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("ni3", result.getNamedIndividual(1).getShortName());
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasNamedIndividualValue(1));
    Assert.assertEquals("ni3", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("ni4", result.getNamedIndividual(1).getShortName());
  }

  @Test public void TestSWRLABoxSIABuiltInWithAllBoundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, SameIndividual(NI1, NI2));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:sia(ni1, ni2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLABoxSIABuiltInWithUnbound1stArgumentAndBound2ndArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, SameIndividual(NI1, NI2));
    addOWLAxioms(ontology, SameIndividual(NI2, NI3));
    addOWLAxioms(ontology, SameIndividual(NI3, NI4));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:sia(?i, ni3) -> sqwrl:select(?i, ni3)");

    Assert.assertEquals(1, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasNamedIndividualValue(1));
    Assert.assertEquals("ni2", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("ni3", result.getNamedIndividual(1).getShortName());
  }

  @Test public void TestSWRLABoxSIABuiltInWithBound1stArgumentAndUnbound2ndArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, SameIndividual(NI1, NI2));
    addOWLAxioms(ontology, SameIndividual(NI2, NI3));
    addOWLAxioms(ontology, SameIndividual(NI3, NI4));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:sia(ni3, ?i) -> sqwrl:select(ni3, ?i)");

    Assert.assertEquals(1, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasNamedIndividualValue(1));
    Assert.assertEquals("ni3", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("ni4", result.getNamedIndividual(1).getShortName());
  }

  @Test public void TestSWRLABoxDIABuiltInWithAllUnboundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DifferentIndividuals(NI1, NI2));
    addOWLAxioms(ontology, DifferentIndividuals(NI2, NI3));
    addOWLAxioms(ontology, DifferentIndividuals(NI3, NI4));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "abox:dia(?i1, ?i2) -> sqwrl:select(?i1, ?i2) ^ sqwrl:orderBy(?i1, ?i2)");

    Assert.assertEquals(3, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasNamedIndividualValue(1));
    Assert.assertEquals("ni1", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("ni2", result.getNamedIndividual(1).getShortName());
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasNamedIndividualValue(1));
    Assert.assertEquals("ni2", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("ni3", result.getNamedIndividual(1).getShortName());
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasNamedIndividualValue(1));
    Assert.assertEquals("ni3", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("ni4", result.getNamedIndividual(1).getShortName());
  }

  @Test public void TestSWRLABoxDIABuiltInWithAllBoundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DifferentIndividuals(NI1, NI2));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:dia(ni1, ni2) -> sqwrl:select(ni1, ni2)");

    Assert.assertEquals(1, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasNamedIndividualValue(1));
    Assert.assertEquals("ni1", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("ni2", result.getNamedIndividual(1).getShortName());
  }

  @Test public void TestSWRLABoxDIABuiltInWithUnbound1stArgumentAndBound2ndArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DifferentIndividuals(NI1, NI2));
    addOWLAxioms(ontology, DifferentIndividuals(NI2, NI3));
    addOWLAxioms(ontology, DifferentIndividuals(NI3, NI4));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:dia(?i, ni3) -> sqwrl:select(?i, ni3)");

    Assert.assertEquals(1, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasNamedIndividualValue(1));
    Assert.assertEquals("ni2", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("ni3", result.getNamedIndividual(1).getShortName());
  }

  @Test public void TestSWRLABoxDIABuiltInWithBound1stArgumentAndUnbound2ndArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DifferentIndividuals(NI1, NI2));
    addOWLAxioms(ontology, DifferentIndividuals(NI2, NI3));
    addOWLAxioms(ontology, DifferentIndividuals(NI3, NI4));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:dia(ni3, ?i) -> sqwrl:select(ni3, ?i)");

    Assert.assertEquals(1, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasNamedIndividualValue(1));
    Assert.assertEquals("ni3", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("ni4", result.getNamedIndividual(1).getShortName());
  }

  @Test public void TestSWRLABoxOPAABuiltInWithAllUnboundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ObjectPropertyAssertion(OP1, NI1, NI2));
    addOWLAxioms(ontology, ObjectPropertyAssertion(OP2, NI2, NI3));
    addOWLAxioms(ontology, ObjectPropertyAssertion(OP3, NI3, NI4));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "abox:opaa(?s, ?p, ?o) -> sqwrl:select(?s, ?p, ?o) ^ sqwrl:orderBy(?s, ?p, ?o)");

    Assert.assertEquals(3, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertTrue(result.hasNamedIndividualValue(2));
    Assert.assertEquals("ni1", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("op1", result.getObjectProperty(1).getShortName());
    Assert.assertEquals("ni2", result.getNamedIndividual(2).getShortName());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertTrue(result.hasNamedIndividualValue(2));
    Assert.assertEquals("ni2", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("op2", result.getObjectProperty(1).getShortName());
    Assert.assertEquals("ni3", result.getNamedIndividual(2).getShortName());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertTrue(result.hasNamedIndividualValue(2));
    Assert.assertEquals("ni3", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("op3", result.getObjectProperty(1).getShortName());
    Assert.assertEquals("ni4", result.getNamedIndividual(2).getShortName());
  }

  @Test public void TestSWRLABoxOPAABuiltInWithAllBoundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ObjectPropertyAssertion(OP1, NI1, NI2));
    addOWLAxioms(ontology, ObjectPropertyAssertion(OP2, NI2, NI3));
    addOWLAxioms(ontology, ObjectPropertyAssertion(OP3, NI3, NI4));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:opaa(ni1, op1, ni2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLABoxOPAABuiltInWithUnbound1stUnbound2ndBound3dArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ObjectPropertyAssertion(OP1, NI1, NI2));
    addOWLAxioms(ontology, ObjectPropertyAssertion(OP2, NI2, NI3));
    addOWLAxioms(ontology, ObjectPropertyAssertion(OP3, NI3, NI4));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:opaa(?s, ?p, ni2) -> sqwrl:select(?s, ?p, ni2)");

    Assert.assertEquals(1, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertTrue(result.hasNamedIndividualValue(2));
    Assert.assertEquals("ni1", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("op1", result.getObjectProperty(1).getShortName());
    Assert.assertEquals("ni2", result.getNamedIndividual(2).getShortName());
  }

  @Test public void TestSWRLABoxOPAABuiltInWithUnbound1stBound2ndUnbound3dArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ObjectPropertyAssertion(OP1, NI1, NI2));
    addOWLAxioms(ontology, ObjectPropertyAssertion(OP2, NI2, NI3));
    addOWLAxioms(ontology, ObjectPropertyAssertion(OP3, NI3, NI4));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:opaa(?s, op1, ?o) -> sqwrl:select(?s, op1, ?o)");

    Assert.assertEquals(1, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertTrue(result.hasNamedIndividualValue(2));
    Assert.assertEquals("ni1", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("op1", result.getObjectProperty(1).getShortName());
    Assert.assertEquals("ni2", result.getNamedIndividual(2).getShortName());
  }

  @Test public void TestSWRLABoxOPAABuiltInWithUnbound1stBound2ndBound3dArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ObjectPropertyAssertion(OP1, NI1, NI2));
    addOWLAxioms(ontology, ObjectPropertyAssertion(OP2, NI2, NI3));
    addOWLAxioms(ontology, ObjectPropertyAssertion(OP3, NI3, NI4));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:opaa(?s, op1, ni2) -> sqwrl:select(?s, op1, ni2)");

    Assert.assertEquals(1, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertTrue(result.hasNamedIndividualValue(2));
    Assert.assertEquals("ni1", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("op1", result.getObjectProperty(1).getShortName());
    Assert.assertEquals("ni2", result.getNamedIndividual(2).getShortName());
  }

  @Test public void TestSWRLABoxOPAABuiltInWithBound1stUnbound2ndUnbound3dArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ObjectPropertyAssertion(OP1, NI1, NI2));
    addOWLAxioms(ontology, ObjectPropertyAssertion(OP2, NI2, NI3));
    addOWLAxioms(ontology, ObjectPropertyAssertion(OP3, NI3, NI4));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:opaa(ni1, ?p, ?o) -> sqwrl:select(ni1, ?p, ?o)");

    Assert.assertEquals(1, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertTrue(result.hasNamedIndividualValue(2));
    Assert.assertEquals("ni1", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("op1", result.getObjectProperty(1).getShortName());
    Assert.assertEquals("ni2", result.getNamedIndividual(2).getShortName());
  }

  @Test public void TestSWRLABoxOPAABuiltInWithBound1stUnbound2ndBound3dArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ObjectPropertyAssertion(OP1, NI1, NI2));
    addOWLAxioms(ontology, ObjectPropertyAssertion(OP2, NI2, NI3));
    addOWLAxioms(ontology, ObjectPropertyAssertion(OP3, NI3, NI4));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:opaa(ni1, ?p, ni2) -> sqwrl:select(ni1, ?p, ni2)");

    Assert.assertEquals(1, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertTrue(result.hasNamedIndividualValue(2));
    Assert.assertEquals("ni1", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("op1", result.getObjectProperty(1).getShortName());
    Assert.assertEquals("ni2", result.getNamedIndividual(2).getShortName());
  }

  @Test public void TestSWRLABoxOPAABuiltInWithBound1stBound2ndUnbound3dArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ObjectPropertyAssertion(OP1, NI1, NI2));
    addOWLAxioms(ontology, ObjectPropertyAssertion(OP2, NI2, NI3));
    addOWLAxioms(ontology, ObjectPropertyAssertion(OP3, NI3, NI4));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:opaa(ni1, op1, ?o) -> sqwrl:select(ni1, op1, ?o)");

    Assert.assertEquals(1, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertTrue(result.hasNamedIndividualValue(2));
    Assert.assertEquals("ni1", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("op1", result.getObjectProperty(1).getShortName());
    Assert.assertEquals("ni2", result.getNamedIndividual(2).getShortName());
  }

  @Test public void TestSWRLABoxNOPAABuiltInWithAllUnboundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, NegativeObjectPropertyAssertion(OP1, NI1, NI2));
    addOWLAxioms(ontology, NegativeObjectPropertyAssertion(OP2, NI2, NI3));
    addOWLAxioms(ontology, NegativeObjectPropertyAssertion(OP3, NI3, NI4));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "abox:nopaa(?s, ?p, ?o) -> sqwrl:select(?s, ?p, ?o) ^ sqwrl:orderBy(?s, ?p, ?o)");

    Assert.assertEquals(3, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertTrue(result.hasNamedIndividualValue(2));
    Assert.assertEquals("ni1", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("op1", result.getObjectProperty(1).getShortName());
    Assert.assertEquals("ni2", result.getNamedIndividual(2).getShortName());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertTrue(result.hasNamedIndividualValue(2));
    Assert.assertEquals("ni2", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("op2", result.getObjectProperty(1).getShortName());
    Assert.assertEquals("ni3", result.getNamedIndividual(2).getShortName());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertTrue(result.hasNamedIndividualValue(2));
    Assert.assertEquals("ni3", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("op3", result.getObjectProperty(1).getShortName());
    Assert.assertEquals("ni4", result.getNamedIndividual(2).getShortName());
  }

  @Test public void TestSWRLABoxNOPAABuiltInWithAllBoundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, NegativeObjectPropertyAssertion(OP1, NI1, NI2));
    addOWLAxioms(ontology, NegativeObjectPropertyAssertion(OP2, NI2, NI3));
    addOWLAxioms(ontology, NegativeObjectPropertyAssertion(OP3, NI3, NI4));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:nopaa(ni1, op1, ni2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLABoxNOPAABuiltInWithUnbound1stUnbound2ndBound3dArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, NegativeObjectPropertyAssertion(OP1, NI1, NI2));
    addOWLAxioms(ontology, NegativeObjectPropertyAssertion(OP2, NI2, NI3));
    addOWLAxioms(ontology, NegativeObjectPropertyAssertion(OP3, NI3, NI4));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:nopaa(?s, ?p, ni2) -> sqwrl:select(?s, ?p, ni2)");

    Assert.assertEquals(1, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertTrue(result.hasNamedIndividualValue(2));
    Assert.assertEquals("ni1", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("op1", result.getObjectProperty(1).getShortName());
    Assert.assertEquals("ni2", result.getNamedIndividual(2).getShortName());
  }

  @Test public void TestSWRLABoxNOPAABuiltInWithUnbound1stBound2ndUnbound3dArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, NegativeObjectPropertyAssertion(OP1, NI1, NI2));
    addOWLAxioms(ontology, NegativeObjectPropertyAssertion(OP2, NI2, NI3));
    addOWLAxioms(ontology, NegativeObjectPropertyAssertion(OP3, NI3, NI4));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:nopaa(?s, op1, ?o) -> sqwrl:select(?s, op1, ?o)");

    Assert.assertEquals(1, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertTrue(result.hasNamedIndividualValue(2));
    Assert.assertEquals("ni1", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("op1", result.getObjectProperty(1).getShortName());
    Assert.assertEquals("ni2", result.getNamedIndividual(2).getShortName());
  }

  @Test public void TestSWRLABoxNOPAABuiltInWithUnbound1stBound2ndBound3dArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, NegativeObjectPropertyAssertion(OP1, NI1, NI2));
    addOWLAxioms(ontology, NegativeObjectPropertyAssertion(OP2, NI2, NI3));
    addOWLAxioms(ontology, NegativeObjectPropertyAssertion(OP3, NI3, NI4));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:nopaa(?s, op1, ni2) -> sqwrl:select(?s, op1, ni2)");

    Assert.assertEquals(1, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertTrue(result.hasNamedIndividualValue(2));
    Assert.assertEquals("ni1", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("op1", result.getObjectProperty(1).getShortName());
    Assert.assertEquals("ni2", result.getNamedIndividual(2).getShortName());
  }

  @Test public void TestSWRLABoxNOPAABuiltInWithBound1stUnbound2ndUnbound3dArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, NegativeObjectPropertyAssertion(OP1, NI1, NI2));
    addOWLAxioms(ontology, NegativeObjectPropertyAssertion(OP2, NI2, NI3));
    addOWLAxioms(ontology, NegativeObjectPropertyAssertion(OP3, NI3, NI4));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:nopaa(ni1, ?p, ?o) -> sqwrl:select(ni1, ?p, ?o)");

    Assert.assertEquals(1, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertTrue(result.hasNamedIndividualValue(2));
    Assert.assertEquals("ni1", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("op1", result.getObjectProperty(1).getShortName());
    Assert.assertEquals("ni2", result.getNamedIndividual(2).getShortName());
  }

  @Test public void TestSWRLABoxNOPAABuiltInWithBound1stUnbound2ndBound3dArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, NegativeObjectPropertyAssertion(OP1, NI1, NI2));
    addOWLAxioms(ontology, NegativeObjectPropertyAssertion(OP2, NI2, NI3));
    addOWLAxioms(ontology, NegativeObjectPropertyAssertion(OP3, NI3, NI4));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:nopaa(ni1, ?p, ni2) -> sqwrl:select(ni1, ?p, ni2)");

    Assert.assertEquals(1, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertTrue(result.hasNamedIndividualValue(2));
    Assert.assertEquals("ni1", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("op1", result.getObjectProperty(1).getShortName());
    Assert.assertEquals("ni2", result.getNamedIndividual(2).getShortName());
  }

  @Test public void TestSWRLABoxNOPAABuiltInWithBound1stBound2ndUnbound3dArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, NegativeObjectPropertyAssertion(OP1, NI1, NI2));
    addOWLAxioms(ontology, NegativeObjectPropertyAssertion(OP2, NI2, NI3));
    addOWLAxioms(ontology, NegativeObjectPropertyAssertion(OP3, NI3, NI4));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:nopaa(ni1, op1, ?o) -> sqwrl:select(ni1, op1, ?o)");

    Assert.assertEquals(1, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertTrue(result.hasNamedIndividualValue(2));
    Assert.assertEquals("ni1", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("op1", result.getObjectProperty(1).getShortName());
    Assert.assertEquals("ni2", result.getNamedIndividual(2).getShortName());
  }

  @Test public void TestSWRLABoxDPAABuiltInWithAllUnboundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(DP1, NI1, L1INTEGER));
    addOWLAxioms(ontology, DataPropertyAssertion(DP2, NI2, L18INTEGER));
    addOWLAxioms(ontology, DataPropertyAssertion(DP3, NI3, L123INTEGER));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "abox:dpaa(?s, ?p, ?l) -> sqwrl:select(?s, ?p, ?l) ^ sqwrl:orderBy(?s, ?p, ?l)");

    Assert.assertEquals(3, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasDataPropertyValue(1));
    Assert.assertTrue(result.hasLiteralValue(2));
    Assert.assertEquals("ni1", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("dp1", result.getDataProperty(1).getShortName());
    Assert.assertEquals(BigInteger.valueOf(1), result.getLiteral(2).getInteger());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasDataPropertyValue(1));
    Assert.assertTrue(result.hasLiteralValue(2));
    Assert.assertEquals("ni2", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("dp2", result.getDataProperty(1).getShortName());
    Assert.assertEquals(BigInteger.valueOf(18), result.getLiteral(2).getInteger());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasDataPropertyValue(1));
    Assert.assertTrue(result.hasLiteralValue(2));
    Assert.assertEquals("ni3", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("dp3", result.getDataProperty(1).getShortName());
    Assert.assertEquals(BigInteger.valueOf(123), result.getLiteral(2).getInteger());
  }

  @Test public void TestSWRLABoxDPAABuiltInWithAllBoundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyAssertion(DP1, NI1, L1INTEGER));
    addOWLAxioms(ontology, DataPropertyAssertion(DP2, NI2, L18INTEGER));
    addOWLAxioms(ontology, DataPropertyAssertion(DP3, NI3, L123INTEGER));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:dpaa(ni1, dp1, 1) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  // TODO TestSWRLABoxDPAABuiltInWithUnbound1stUnbound2ndBound3dArgument
  // TODO TestSWRLABoxDPAABuiltInWithUnbound1stBound2ndUnbound3dArgument
  // TODO TestSWRLABoxDPAABuiltInWithUnbound1stBound2ndBound3dArgument
  // TODO TestSWRLABoxDPAABuiltInWithBound1stUnbound2ndUnbound3dArgument
  // TODO TestSWRLABoxDPAABuiltInWithBound1stUnbound2ndBound3dArgument
  // TODO TestSWRLABoxDPAABuiltInWithBound1stBound2ndUnbound3dArgument

  @Test public void TestSWRLABoxNDPAABuiltInWithAllUnboundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, NegativeDataPropertyAssertion(DP1, NI1, L1INTEGER));
    addOWLAxioms(ontology, NegativeDataPropertyAssertion(DP2, NI2, L18INTEGER));
    addOWLAxioms(ontology, NegativeDataPropertyAssertion(DP3, NI3, L123INTEGER));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "abox:ndpaa(?s, ?p, ?l) -> sqwrl:select(?s, ?p, ?l) ^ sqwrl:orderBy(?s, ?p, ?l)");

    Assert.assertEquals(3, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasDataPropertyValue(1));
    Assert.assertTrue(result.hasLiteralValue(2));
    Assert.assertEquals("ni1", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("dp1", result.getDataProperty(1).getShortName());
    Assert.assertEquals(BigInteger.valueOf(1), result.getLiteral(2).getInteger());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasDataPropertyValue(1));
    Assert.assertTrue(result.hasLiteralValue(2));
    Assert.assertEquals("ni2", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("dp2", result.getDataProperty(1).getShortName());
    Assert.assertEquals(BigInteger.valueOf(18), result.getLiteral(2).getInteger());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasNamedIndividualValue(0));
    Assert.assertTrue(result.hasDataPropertyValue(1));
    Assert.assertTrue(result.hasLiteralValue(2));
    Assert.assertEquals("ni3", result.getNamedIndividual(0).getShortName());
    Assert.assertEquals("dp3", result.getDataProperty(1).getShortName());
    Assert.assertEquals(BigInteger.valueOf(123), result.getLiteral(2).getInteger());
  }

  @Test public void TestSWRLABoxNDPAABuiltInWithAllBoundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, NegativeDataPropertyAssertion(DP1, NI1, L1INTEGER));
    addOWLAxioms(ontology, NegativeDataPropertyAssertion(DP2, NI2, L18INTEGER));
    addOWLAxioms(ontology, NegativeDataPropertyAssertion(DP3, NI3, L123INTEGER));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "abox:ndpaa(ni1, dp1, 1) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  // TODO TestSWRLABoxNDPAABuiltInWithUnbound1stUnbound2ndBound3dArgument
  // TODO TestSWRLABoxNDPAABuiltInWithUnbound1stBound2ndUnbound3dArgument
  // TODO TestSWRLABoxNDPAABuiltInWithUnbound1stBound2ndBound3dArgument
  // TODO TestSWRLABoxNDPAABuiltInWithBound1stUnbound2ndUnbound3dArgument
  // TODO TestSWRLABoxNDPAABuiltInWithBound1stUnbound2ndBound3dArgument
  // TODO TestSWRLABoxNDPAABuiltInWithBound1stBound2ndUnbound3dArgument
}