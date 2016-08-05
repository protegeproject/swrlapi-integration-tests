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
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointDataProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointObjectProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentDataProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentObjectProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.InverseObjectProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IrreflexiveObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ReflexiveObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubDataPropertyOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubObjectPropertyOf;
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

  @Test public void TestSWRLRBoxIOPABuiltInWithAllUnboundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, InverseObjectProperties(OP1, OP2));
    addOWLAxioms(ontology, InverseObjectProperties(OP2, OP3));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "rbox:iopa(?p1, ?p2) -> sqwrl:select(?p1, ?p2) ^ sqwrl:orderBy(?p1, ?p2)");

    Assert.assertEquals(result.getNumberOfRows(), 2);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
    Assert.assertEquals(result.getObjectProperty(1).getShortName(), "op2");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op2");
    Assert.assertEquals(result.getObjectProperty(1).getShortName(), "op3");
  }

  @Test public void TestSWRLRBoxSCABuiltInWithAllBoundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, InverseObjectProperties(OP1, OP2));
    addOWLAxioms(ontology, InverseObjectProperties(OP2, OP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:iopa(op1, op2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLRBoxSCABuiltInWithBound1stUnbound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, InverseObjectProperties(OP1, OP2));
    addOWLAxioms(ontology, InverseObjectProperties(OP2, OP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:iopa(op1, ?p) -> sqwrl:select(op1, ?p)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
    Assert.assertEquals(result.getObjectProperty(1).getShortName(), "op2");
  }

  @Test public void TestSWRLRBoxSCABuiltInWithUnbound1stBound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, InverseObjectProperties(OP1, OP2));
    addOWLAxioms(ontology, InverseObjectProperties(OP2, OP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:iopa(?p, op2) -> sqwrl:select(?p, op2)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
    Assert.assertEquals(result.getObjectProperty(1).getShortName(), "op2");
  }

  @Test public void TestSWRLRBoxDJOPABuiltInWithAllUnboundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DisjointObjectProperties(OP1, OP2));
    addOWLAxioms(ontology, DisjointObjectProperties(OP2, OP3));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "rbox:djopa(?p1, ?p2) -> sqwrl:select(?p1, ?p2) ^ sqwrl:orderBy(?p1, ?p2)");

    Assert.assertEquals(result.getNumberOfRows(), 2);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
    Assert.assertEquals(result.getObjectProperty(1).getShortName(), "op2");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op2");
    Assert.assertEquals(result.getObjectProperty(1).getShortName(), "op3");
  }

  @Test public void TestSWRLRBoxDJOPABuiltInWithAllBoundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DisjointObjectProperties(OP1, OP2));
    addOWLAxioms(ontology, DisjointObjectProperties(OP2, OP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:djopa(op1, op2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLRBoxDJOPABuiltInWithBound1stUnbound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DisjointObjectProperties(OP1, OP2));
    addOWLAxioms(ontology, DisjointObjectProperties(OP2, OP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:djopa(op1, ?p) -> sqwrl:select(op1, ?p)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
    Assert.assertEquals(result.getObjectProperty(1).getShortName(), "op2");
  }

  @Test public void TestSWRLRBoxDJOPABuiltInWithUnbound1stBound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DisjointObjectProperties(OP1, OP2));
    addOWLAxioms(ontology, DisjointObjectProperties(OP2, OP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:djopa(?p, op2) -> sqwrl:select(?p, op2)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
    Assert.assertEquals(result.getObjectProperty(1).getShortName(), "op2");
  }

  @Test public void TestSWRLRBoxSOPABuiltInWithAllUnboundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, SubObjectPropertyOf(OP1, OP2));
    addOWLAxioms(ontology, SubObjectPropertyOf(OP2, OP3));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "rbox:sopa(?p1, ?p2) -> sqwrl:select(?p1, ?p2) ^ sqwrl:orderBy(?p1, ?p2)");

    Assert.assertEquals(result.getNumberOfRows(), 2);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
    Assert.assertEquals(result.getObjectProperty(1).getShortName(), "op2");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op2");
    Assert.assertEquals(result.getObjectProperty(1).getShortName(), "op3");
  }

  @Test public void TestSWRLRBoxSOPABuiltInWithAllBoundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, SubObjectPropertyOf(OP1, OP2));
    addOWLAxioms(ontology, SubObjectPropertyOf(OP2, OP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:sopa(op1, op2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLRBoxSOPABuiltInWithBound1stUnbound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, SubObjectPropertyOf(OP1, OP2));
    addOWLAxioms(ontology, SubObjectPropertyOf(OP2, OP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:sopa(op1, ?p) -> sqwrl:select(op1, ?p)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
    Assert.assertEquals(result.getObjectProperty(1).getShortName(), "op2");
  }

  @Test public void TestSWRLRBoxSOPABuiltInWithUnbound1stBound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, SubObjectPropertyOf(OP1, OP2));
    addOWLAxioms(ontology, SubObjectPropertyOf(OP2, OP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:sopa(?p, op2) -> sqwrl:select(?p, op2)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
    Assert.assertEquals(result.getObjectProperty(1).getShortName(), "op2");
  }

  @Test public void TestSWRLRBoxEOPABuiltInWithAllUnboundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, EquivalentObjectProperties(OP1, OP2));
    addOWLAxioms(ontology, EquivalentObjectProperties(OP2, OP3));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "rbox:eopa(?p1, ?p2) -> sqwrl:select(?p1, ?p2) ^ sqwrl:orderBy(?p1, ?p2)");

    Assert.assertEquals(result.getNumberOfRows(), 2);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
    Assert.assertEquals(result.getObjectProperty(1).getShortName(), "op2");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op2");
    Assert.assertEquals(result.getObjectProperty(1).getShortName(), "op3");
  }

  @Test public void TestSWRLRBoxEOPABuiltInWithAllBoundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, EquivalentObjectProperties(OP1, OP2));
    addOWLAxioms(ontology, EquivalentObjectProperties(OP2, OP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:eopa(op1, op2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLRBoxEOPABuiltInWithBound1stUnbound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, EquivalentObjectProperties(OP1, OP2));
    addOWLAxioms(ontology, EquivalentObjectProperties(OP2, OP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:eopa(op1, ?p) -> sqwrl:select(op1, ?p)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
    Assert.assertEquals(result.getObjectProperty(1).getShortName(), "op2");
  }

  @Test public void TestSWRLRBoxEOPABuiltInWithUnbound1stBound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, EquivalentObjectProperties(OP1, OP2));
    addOWLAxioms(ontology, EquivalentObjectProperties(OP2, OP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:eopa(?p, op2) -> sqwrl:select(?p, op2)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertTrue(result.hasObjectPropertyValue(1));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
    Assert.assertEquals(result.getObjectProperty(1).getShortName(), "op2");
  }

  @Test public void TestSWRLRBoxDJDPABuiltInWithAllUnboundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DisjointDataProperties(DP1, DP2));
    addOWLAxioms(ontology, DisjointDataProperties(DP2, DP3));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "rbox:djdpa(?p1, ?p2) -> sqwrl:select(?p1, ?p2) ^ sqwrl:orderBy(?p1, ?p2)");

    Assert.assertEquals(result.getNumberOfRows(), 2);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasDataPropertyValue(0));
    Assert.assertTrue(result.hasDataPropertyValue(1));
    Assert.assertEquals(result.getDataProperty(0).getShortName(), "dp1");
    Assert.assertEquals(result.getDataProperty(1).getShortName(), "dp2");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasDataPropertyValue(0));
    Assert.assertTrue(result.hasDataPropertyValue(1));
    Assert.assertEquals(result.getDataProperty(0).getShortName(), "dp2");
    Assert.assertEquals(result.getDataProperty(1).getShortName(), "dp3");
  }

  @Test public void TestSWRLRBoxDJDPABuiltInWithAllBoundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DisjointDataProperties(DP1, DP2));
    addOWLAxioms(ontology, DisjointDataProperties(DP2, DP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:djdpa(dp1, dp2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLRBoxDJDPABuiltInWithBound1stUnbound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DisjointDataProperties(DP1, DP2));
    addOWLAxioms(ontology, DisjointDataProperties(DP2, DP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:djdpa(dp1, ?p) -> sqwrl:select(dp1, ?p)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasDataPropertyValue(0));
    Assert.assertTrue(result.hasDataPropertyValue(1));
    Assert.assertEquals(result.getDataProperty(0).getShortName(), "dp1");
    Assert.assertEquals(result.getDataProperty(1).getShortName(), "dp2");
  }

  @Test public void TestSWRLRBoxDJDPABuiltInWithUnbound1stBound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DisjointDataProperties(DP1, DP2));
    addOWLAxioms(ontology, DisjointDataProperties(DP2, DP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:djdpa(?p, dp2) -> sqwrl:select(?p, dp2)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasDataPropertyValue(0));
    Assert.assertTrue(result.hasDataPropertyValue(1));
    Assert.assertEquals(result.getDataProperty(0).getShortName(), "dp1");
    Assert.assertEquals(result.getDataProperty(1).getShortName(), "dp2");
  }

  @Test public void TestSWRLRBoxSDPABuiltInWithAllUnboundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, SubDataPropertyOf(DP1, DP2));
    addOWLAxioms(ontology, SubDataPropertyOf(DP2, DP3));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "rbox:sdpa(?p1, ?p2) -> sqwrl:select(?p1, ?p2) ^ sqwrl:orderBy(?p1, ?p2)");

    Assert.assertEquals(result.getNumberOfRows(), 2);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasDataPropertyValue(0));
    Assert.assertTrue(result.hasDataPropertyValue(1));
    Assert.assertEquals(result.getDataProperty(0).getShortName(), "dp1");
    Assert.assertEquals(result.getDataProperty(1).getShortName(), "dp2");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasDataPropertyValue(0));
    Assert.assertTrue(result.hasDataPropertyValue(1));
    Assert.assertEquals(result.getDataProperty(0).getShortName(), "dp2");
    Assert.assertEquals(result.getDataProperty(1).getShortName(), "dp3");
  }

  @Test public void TestSWRLRBoxSDPABuiltInWithAllBoundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, SubDataPropertyOf(DP1, DP2));
    addOWLAxioms(ontology, SubDataPropertyOf(DP2, DP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:sdpa(dp1, dp2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLRBoxSDPABuiltInWithBound1stUnbound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, SubDataPropertyOf(DP1, DP2));
    addOWLAxioms(ontology, SubDataPropertyOf(DP2, DP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:sdpa(dp1, ?p) -> sqwrl:select(dp1, ?p)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasDataPropertyValue(0));
    Assert.assertTrue(result.hasDataPropertyValue(1));
    Assert.assertEquals(result.getDataProperty(0).getShortName(), "dp1");
    Assert.assertEquals(result.getDataProperty(1).getShortName(), "dp2");
  }

  @Test public void TestSWRLRBoxSDPABuiltInWithUnbound1stBound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, SubDataPropertyOf(DP1, DP2));
    addOWLAxioms(ontology, SubDataPropertyOf(DP2, DP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:sdpa(?p, dp2) -> sqwrl:select(?p, dp2)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasDataPropertyValue(0));
    Assert.assertTrue(result.hasDataPropertyValue(1));
    Assert.assertEquals(result.getDataProperty(0).getShortName(), "dp1");
    Assert.assertEquals(result.getDataProperty(1).getShortName(), "dp2");
  }

  @Test public void TestSWRLRBoxEDPABuiltInWithAllUnboundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, EquivalentDataProperties(DP1, DP2));
    addOWLAxioms(ontology, EquivalentDataProperties(DP2, DP3));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "rbox:edpa(?p1, ?p2) -> sqwrl:select(?p1, ?p2) ^ sqwrl:orderBy(?p1, ?p2)");

    Assert.assertEquals(result.getNumberOfRows(), 2);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasDataPropertyValue(0));
    Assert.assertTrue(result.hasDataPropertyValue(1));
    Assert.assertEquals(result.getDataProperty(0).getShortName(), "dp1");
    Assert.assertEquals(result.getDataProperty(1).getShortName(), "dp2");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasDataPropertyValue(0));
    Assert.assertTrue(result.hasDataPropertyValue(1));
    Assert.assertEquals(result.getDataProperty(0).getShortName(), "dp2");
    Assert.assertEquals(result.getDataProperty(1).getShortName(), "dp3");
  }

  @Test public void TestSWRLRBoxEDPABuiltInWithAllBoundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, EquivalentDataProperties(DP1, DP2));
    addOWLAxioms(ontology, EquivalentDataProperties(DP2, DP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:edpa(dp1, dp2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLRBoxEDPABuiltInWithBound1stUnbound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, EquivalentDataProperties(DP1, DP2));
    addOWLAxioms(ontology, EquivalentDataProperties(DP2, DP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:edpa(dp1, ?p) -> sqwrl:select(dp1, ?p)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasDataPropertyValue(0));
    Assert.assertTrue(result.hasDataPropertyValue(1));
    Assert.assertEquals(result.getDataProperty(0).getShortName(), "dp1");
    Assert.assertEquals(result.getDataProperty(1).getShortName(), "dp2");
  }

  @Test public void TestSWRLRBoxEDPABuiltInWithUnbound1stBound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, EquivalentDataProperties(DP1, DP2));
    addOWLAxioms(ontology, EquivalentDataProperties(DP2, DP3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "rbox:edpa(?p, dp2) -> sqwrl:select(?p, dp2)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasDataPropertyValue(0));
    Assert.assertTrue(result.hasDataPropertyValue(1));
    Assert.assertEquals(result.getDataProperty(0).getShortName(), "dp1");
    Assert.assertEquals(result.getDataProperty(1).getShortName(), "dp2");
  }

  // EQUIVALENT_DATA_PROPERTIES,
  // TODO: SUB_PROPERTY_CHAIN_OF - rbox:spcoa
}