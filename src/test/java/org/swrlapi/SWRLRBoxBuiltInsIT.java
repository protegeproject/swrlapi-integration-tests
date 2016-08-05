package org.swrlapi;

import org.junit.Assert;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.test.IntegrationTestBase;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AsymmetricObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IrreflexiveObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ReflexiveObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SymmetricObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.TransitiveObjectProperty;

/**
 * NOTE: All tests are designed for parallel execution.
 *
 * @see org.swrlapi.builtins.rbox.SWRLBuiltInLibraryImpl
 */
public class SWRLRBoxBuiltInsIT extends IntegrationTestBase
{
  private static final OWLObjectProperty OP1 = ObjectProperty(iri("op1"));
  private static final OWLObjectProperty OP2 = ObjectProperty(iri("op2"));
  private static final OWLObjectProperty OP3 = ObjectProperty(iri("op3"));
  private static final OWLDataProperty DP1 = DataProperty(iri("dp1"));
  private static final OWLDataProperty DP2 = DataProperty(iri("dp2"));
  private static final OWLDataProperty DP3 = DataProperty(iri("dp3"));

  @Test public void TestSWRLRBoxTOPABuiltInWithUnboundArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, TransitiveObjectProperty(OP1));
    addOWLAxioms(ontology, TransitiveObjectProperty(OP2));
    addOWLAxioms(ontology, TransitiveObjectProperty(OP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:topa(?p) -> sqwrl:select(?p) ^ sqwrl:orderBy(?p)");

    Assert.assertEquals(3, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op2");
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op3");
  }

  @Test public void TestSWRLRBoxTOPABuiltInWithBoundArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, TransitiveObjectProperty(OP1));
    addOWLAxioms(ontology, TransitiveObjectProperty(OP2));
    addOWLAxioms(ontology, TransitiveObjectProperty(OP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:topa(op1) -> sqwrl:select(op1)");

    Assert.assertEquals(1, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
  }

  @Test public void TestSWRLRBoxSPABuiltInWithUnboundArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, SymmetricObjectProperty(OP1));
    addOWLAxioms(ontology, SymmetricObjectProperty(OP2));
    addOWLAxioms(ontology, SymmetricObjectProperty(OP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:spa(?p) -> sqwrl:select(?p) ^ sqwrl:orderBy(?p)");

    Assert.assertEquals(3, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op2");
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op3");
  }

  @Test public void TestSWRLRBoxSPABuiltInWithBoundArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, SymmetricObjectProperty(OP1));
    addOWLAxioms(ontology, SymmetricObjectProperty(OP2));
    addOWLAxioms(ontology, SymmetricObjectProperty(OP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:spa(op1) -> sqwrl:select(op1)");

    Assert.assertEquals(1, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
  }

  @Test public void TestSWRLRBoxAOPABuiltInWithUnboundArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, AsymmetricObjectProperty(OP1));
    addOWLAxioms(ontology, AsymmetricObjectProperty(OP2));
    addOWLAxioms(ontology, AsymmetricObjectProperty(OP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:aopa(?p) -> sqwrl:select(?p) ^ sqwrl:orderBy(?p)");

    Assert.assertEquals(3, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op2");
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op3");
  }

  @Test public void TestSWRLRBoxAOPABuiltInWithBoundArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, AsymmetricObjectProperty(OP1));
    addOWLAxioms(ontology, AsymmetricObjectProperty(OP2));
    addOWLAxioms(ontology, AsymmetricObjectProperty(OP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:aopa(op1) -> sqwrl:select(op1)");

    Assert.assertEquals(1, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
  }

  @Test public void TestSWRLRBoxROPABuiltInWithUnboundArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ReflexiveObjectProperty(OP1));
    addOWLAxioms(ontology, ReflexiveObjectProperty(OP2));
    addOWLAxioms(ontology, ReflexiveObjectProperty(OP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:ropa(?p) -> sqwrl:select(?p) ^ sqwrl:orderBy(?p)");

    Assert.assertEquals(3, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op2");
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op3");
  }

  @Test public void TestSWRLRBoxROPABuiltInWithBoundArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ReflexiveObjectProperty(OP1));
    addOWLAxioms(ontology, ReflexiveObjectProperty(OP2));
    addOWLAxioms(ontology, ReflexiveObjectProperty(OP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:ropa(op1) -> sqwrl:select(op1)");

    Assert.assertEquals(1, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
  }

  @Test public void TestSWRLRBoxIROPABuiltInWithUnboundArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, IrreflexiveObjectProperty(OP1));
    addOWLAxioms(ontology, IrreflexiveObjectProperty(OP2));
    addOWLAxioms(ontology, IrreflexiveObjectProperty(OP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:iropa(?p) -> sqwrl:select(?p) ^ sqwrl:orderBy(?p)");

    Assert.assertEquals(3, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op2");
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op3");
  }

  @Test public void TestSWRLRBoxIROPABuiltInWithBoundArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, IrreflexiveObjectProperty(OP1));
    addOWLAxioms(ontology, IrreflexiveObjectProperty(OP2));
    addOWLAxioms(ontology, IrreflexiveObjectProperty(OP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:iropa(op1) -> sqwrl:select(op1)");

    Assert.assertEquals(1, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
  }

  // INVERSE_OBJECT_PROPERTIES,
  // DISJOINT_OBJECT_PROPERTIES,
  // SUB_OBJECT_PROPERTY,
  // EQUIVALENT_OBJECT_PROPERTIES,
  // DISJOINT_DATA_PROPERTIES,
  // SUB_DATA_PROPERTY,
  // EQUIVALENT_DATA_PROPERTIES,
  // TODO: SUB_PROPERTY_CHAIN_OF - rbox:spcoa
}