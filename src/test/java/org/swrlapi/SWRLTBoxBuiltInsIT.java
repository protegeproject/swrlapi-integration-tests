package org.swrlapi;

import org.junit.Assert;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
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
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyDomain;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyRange;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointClasses;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentClasses;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.FunctionalDataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.FunctionalObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.InverseFunctionalObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyDomain;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyRange;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubClassOf;

/**
 * NOTE: All tests are designed for parallel execution.
 *
 * @see org.swrlapi.builtins.tbox.SWRLBuiltInLibraryImpl
 */
public class SWRLTBoxBuiltInsIT extends IntegrationTestBase
{
  private static final OWLClass C1 = Class(iri("C1"));
  private static final OWLClass C2 = Class(iri("C2"));
  private static final OWLClass C3 = Class(iri("C3"));
  private static final OWLObjectProperty OP1 = ObjectProperty(iri("op1"));
  private static final OWLObjectProperty OP2 = ObjectProperty(iri("op2"));
  private static final OWLObjectProperty OP3 = ObjectProperty(iri("op3"));
  private static final OWLDataProperty DP1 = DataProperty(iri("dp1"));
  private static final OWLDataProperty DP2 = DataProperty(iri("dp2"));
  private static final OWLDatatype D1 = XSD_INT;
  private static final OWLDatatype D2 = XSD_FLOAT;

  // TODO rbox:opd, dpd, apd, dd

  @Test public void TestSWRLRBoxCDBuiltInWithUnboundArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, Declaration(C1));
    addOWLAxioms(ontology, Declaration(C2));
    addOWLAxioms(ontology, Declaration(C3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:cd(?c) -> sqwrl:select(?c) ^ sqwrl:orderBy(?c)");

    Assert.assertEquals(3, result.getNumberOfRows());

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertEquals(result.getClass(0).getShortName(), "C1");
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertEquals(result.getClass(0).getShortName(), "C2");
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertEquals(result.getClass(0).getShortName(), "C3");
  }

  @Test public void TestSWRLRBoxCDBuiltInWithBoundArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, Declaration(C1));
    addOWLAxioms(ontology, Declaration(C2));
    addOWLAxioms(ontology, Declaration(C3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:cd(C1) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLTBoxSCABuiltInWithAllUnboundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, SubClassOf(C1, C2));
    addOWLAxioms(ontology, SubClassOf(C2, C3));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "tbox:sca(?csub, ?csup) -> sqwrl:select(?csub, ?csup) ^ sqwrl:orderBy(?csub, ?csup)");

    Assert.assertEquals(result.getNumberOfRows(), 2);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(0).getShortName(), "C1");
    Assert.assertEquals(result.getClass(1).getShortName(), "C2");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(0).getShortName(), "C2");
    Assert.assertEquals(result.getClass(1).getShortName(), "C3");
  }

  @Test public void TestSWRLTBoxSCABuiltInWithAllBoundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, SubClassOf(C1, C2));
    addOWLAxioms(ontology, SubClassOf(C2, C3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:sca(C1, C2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLTBoxSCABuiltInWithBound1stUnbound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, SubClassOf(C1, C2));
    addOWLAxioms(ontology, SubClassOf(C2, C3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:sca(C1, ?csup) -> sqwrl:select(C1, ?csup)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(0).getShortName(), "C1");
    Assert.assertEquals(result.getClass(1).getShortName(), "C2");
  }

  @Test public void TestSWRLTBoxSCABuiltInWithUnbound1stBound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, SubClassOf(C1, C2));
    addOWLAxioms(ontology, SubClassOf(C2, C3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:sca(?csub, C2) -> sqwrl:select(?csub, C2)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(0).getShortName(), "C1");
    Assert.assertEquals(result.getClass(1).getShortName(), "C2");

  }

  @Test public void TestSWRLTBoxECABuiltInWithAllUnboundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, EquivalentClasses(C1, C2));
    addOWLAxioms(ontology, EquivalentClasses(C2, C3));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "tbox:eca(?c1, ?c2) -> sqwrl:select(?c1, ?c2) ^ sqwrl:orderBy(?c1, ?c2)");

    Assert.assertEquals(result.getNumberOfRows(), 2);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(0).getShortName(), "C1");
    Assert.assertEquals(result.getClass(1).getShortName(), "C2");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(0).getShortName(), "C2");
    Assert.assertEquals(result.getClass(1).getShortName(), "C3");
  }

  @Test public void TestSWRLTBoxECABuiltInWithAllBoundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, EquivalentClasses(C1, C2));
    addOWLAxioms(ontology, EquivalentClasses(C2, C3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:eca(C1, C2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLTBoxECABuiltInWithBound1stUnbound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, EquivalentClasses(C1, C2));
    addOWLAxioms(ontology, EquivalentClasses(C2, C3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:eca(C1, ?c2) -> sqwrl:select(C1, ?c2)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(0).getShortName(), "C1");
    Assert.assertEquals(result.getClass(1).getShortName(), "C2");

  }

  @Test public void TestSWRLTBoxECABuiltInWithUnbound1stBound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, EquivalentClasses(C1, C2));
    addOWLAxioms(ontology, EquivalentClasses(C2, C3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:eca(?c1, C2) -> sqwrl:select(?c1, C2)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(0).getShortName(), "C1");
    Assert.assertEquals(result.getClass(1).getShortName(), "C2");
  }

  @Test public void TestSWRLTBoxDCABuiltInWithAllUnboundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DisjointClasses(C1, C2));
    addOWLAxioms(ontology, DisjointClasses(C2, C3));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "tbox:dca(?c1, ?c2) -> sqwrl:select(?c1, ?c2) ^ sqwrl:orderBy(?c1, ?c2)");

    Assert.assertEquals(result.getNumberOfRows(), 2);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(0).getShortName(), "C1");
    Assert.assertEquals(result.getClass(1).getShortName(), "C2");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(0).getShortName(), "C2");
    Assert.assertEquals(result.getClass(1).getShortName(), "C3");
  }

  @Test public void TestSWRLTBoxDCABuiltInWithAllBoundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DisjointClasses(C1, C2));
    addOWLAxioms(ontology, DisjointClasses(C2, C3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:dca(C1, C2) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLTBoxDCABuiltInWithBound1stUnbound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DisjointClasses(C1, C2));
    addOWLAxioms(ontology, DisjointClasses(C2, C3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:dca(C1, ?c2) -> sqwrl:select(C1, ?c2)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(0).getShortName(), "C1");
    Assert.assertEquals(result.getClass(1).getShortName(), "C2");
  }

  @Test public void TestSWRLTBoxDCABuiltInWithUnbound1stBound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DisjointClasses(C1, C2));
    addOWLAxioms(ontology, DisjointClasses(C2, C3));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:dca(?c1, C2) -> sqwrl:select(?c1, C2)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasClassValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(0).getShortName(), "C1");
    Assert.assertEquals(result.getClass(1).getShortName(), "C2");
  }

  @Test public void TestSWRLTBoxFOPABuiltInWithUnboundArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, FunctionalObjectProperty(OP1));
    addOWLAxioms(ontology, FunctionalObjectProperty(OP2));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:fopa(?p) -> sqwrl:select(?p) ^ sqwrl:orderBy(?p)");

    Assert.assertEquals(result.getNumberOfRows(), 2);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op2");
  }

  @Test public void TestSWRLTBoxFOPABuiltInWithBoundArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, FunctionalObjectProperty(OP1));
    addOWLAxioms(ontology, FunctionalObjectProperty(OP2));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:fopa(op1) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLTBoxIFOPABuiltInWithUnboundArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, InverseFunctionalObjectProperty(OP1));
    addOWLAxioms(ontology, InverseFunctionalObjectProperty(OP2));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:ifopa(?p) -> sqwrl:select(?p) ^ sqwrl:orderBy(?p)");

    Assert.assertEquals(result.getNumberOfRows(), 2);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op2");
  }

  @Test public void TestSWRLTBoxIFOPABuiltInWithBoundArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, InverseFunctionalObjectProperty(OP1));
    addOWLAxioms(ontology, InverseFunctionalObjectProperty(OP2));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:ifopa(op1) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLTBoxFDPABuiltInWithUnboundArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, FunctionalDataProperty(DP1));
    addOWLAxioms(ontology, FunctionalDataProperty(DP2));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:fdpa(?p) -> sqwrl:select(?p) ^ sqwrl:orderBy(?p)");

    Assert.assertEquals(result.getNumberOfRows(), 2);

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasDataPropertyValue(0));
    Assert.assertEquals(result.getDataProperty(0).getShortName(), "dp1");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasDataPropertyValue(0));
    Assert.assertEquals(result.getDataProperty(0).getShortName(), "dp2");
  }

  @Test public void TestSWRLTBoxFDPABuiltInWithBoundArgument()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, FunctionalDataProperty(DP1));
    addOWLAxioms(ontology, FunctionalDataProperty(DP2));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:fdpa(dp1) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLTBoxOPDABuiltInWithAllUnboundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ObjectPropertyDomain(OP1, C1));
    addOWLAxioms(ontology, ObjectPropertyDomain(OP2, C2));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "tbox:opda(?p, ?d) -> sqwrl:select(?p, ?d) ^ sqwrl:orderBy(?p, ?d)");

    Assert.assertEquals(result.getNumberOfRows(), 2);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(1).getShortName(), "C1");
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(1).getShortName(), "C2");
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op2");
  }

  @Test public void TestSWRLTBoxOPDABuiltInWithAllBoundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ObjectPropertyDomain(OP1, C1));
    addOWLAxioms(ontology, ObjectPropertyDomain(OP2, C2));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:opda(op1, C1) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLTBoxOPDABuiltInWithBound1stUnbound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ObjectPropertyDomain(OP1, C1));
    addOWLAxioms(ontology, ObjectPropertyDomain(OP2, C2));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:opda(op1, ?d) -> sqwrl:select(op1, ?d)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(1).getShortName(), "C1");
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
  }

  @Test public void TestSWRLTBoxOPDABuiltInWithUnbound1stBound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ObjectPropertyDomain(OP1, C1));
    addOWLAxioms(ontology, ObjectPropertyDomain(OP2, C2));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:opda(?p, C1) -> sqwrl:select(?p, C1)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(1).getShortName(), "C1");
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
  }

  @Test public void TestSWRLTBoxOPRABuiltInWithAllUnboundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ObjectPropertyRange(OP1, C1));
    addOWLAxioms(ontology, ObjectPropertyRange(OP2, C2));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "tbox:opra(?p, ?d) -> sqwrl:select(?p, ?d) ^ sqwrl:orderBy(?p, ?d)");

    Assert.assertEquals(result.getNumberOfRows(), 2);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(1).getShortName(), "C1");
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(1).getShortName(), "C2");
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op2");
  }

  @Test public void TestSWRLTBoxOPRABuiltInWithAllBoundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ObjectPropertyRange(OP1, C1));
    addOWLAxioms(ontology, ObjectPropertyRange(OP2, C2));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:opra(op1, C1) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLTBoxOPRABuiltInWithBound1stUnbound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ObjectPropertyRange(OP1, C1));
    addOWLAxioms(ontology, ObjectPropertyRange(OP2, C2));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:opra(op1, ?d) -> sqwrl:select(op1, ?d)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(1).getShortName(), "C1");
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
  }

  @Test public void TestSWRLTBoxOPRABuiltInWithUnbound1stBound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, ObjectPropertyRange(OP1, C1));
    addOWLAxioms(ontology, ObjectPropertyRange(OP2, C2));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:opra(?p, C1) -> sqwrl:select(?p, C1)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasObjectPropertyValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(1).getShortName(), "C1");
    Assert.assertEquals(result.getObjectProperty(0).getShortName(), "op1");
  }

  @Test public void TestSWRLTBoxDPDABuiltInWithAllUnboundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyDomain(DP1, C1));
    addOWLAxioms(ontology, DataPropertyDomain(DP2, C2));

    SQWRLResult result = queryEngine
      .runSQWRLQuery("q1", "tbox:dpda(?p, ?d) -> sqwrl:select(?p, ?d) ^ sqwrl:orderBy(?p, ?d)");

    Assert.assertEquals(result.getNumberOfRows(), 2);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasDataPropertyValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(1).getShortName(), "C1");
    Assert.assertEquals(result.getDataProperty(0).getShortName(), "dp1");

    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasDataPropertyValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(1).getShortName(), "C2");
    Assert.assertEquals(result.getDataProperty(0).getShortName(), "dp2");
  }

  @Test public void TestSWRLTBoxDPDABuiltInWithAllBoundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyDomain(DP1, C1));
    addOWLAxioms(ontology, DataPropertyDomain(DP2, C2));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:dpda(dp1, C1) -> sqwrl:select(0)");

    Assert.assertTrue(result.next());
  }

  @Test public void TestSWRLTBoxDPDABuiltInWithBound1stUnbound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyDomain(DP1, C1));
    addOWLAxioms(ontology, DataPropertyDomain(DP2, C2));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:dpda(dp1, ?d) -> sqwrl:select(dp1, ?d)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasDataPropertyValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(1).getShortName(), "C1");
    Assert.assertEquals(result.getDataProperty(0).getShortName(), "dp1");
  }

  @Test public void TestSWRLTBoxDPDABuiltInWithUnbound1stBound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyDomain(DP1, C1));
    addOWLAxioms(ontology, DataPropertyDomain(DP2, C2));

    SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:dpda(?d, C1) -> sqwrl:select(?d, C1)");

    Assert.assertEquals(result.getNumberOfRows(), 1);
    Assert.assertTrue(result.next());
    Assert.assertTrue(result.hasDataPropertyValue(0));
    Assert.assertTrue(result.hasClassValue(1));
    Assert.assertEquals(result.getClass(1).getShortName(), "C1");
    Assert.assertEquals(result.getDataProperty(0).getShortName(), "dp1");
  }

  @Test public void TestSWRLTBoxDPRABuiltInWithAllUnboundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyRange(DP1, D1));
    addOWLAxioms(ontology, DataPropertyRange(DP2, D2));

    // SQWRLResult result = queryEngine
    //  .runSQWRLQuery("q1", "tbox:dpra(?p, ?r) -> sqwrl:select(?p, ?r) ^ sqwrl:orderBy(?p, ?r)");

    // TODO tbox:dpra not yet implemented
  }

  @Test public void TestSWRLTBoxDPRABuiltInWithAllBoundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyRange(DP1, D1));
    addOWLAxioms(ontology, DataPropertyRange(DP2, D2));

    // SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:dpra(dp1, C1) -> sqwrl:select(0)");

    // TODO tbox:dpra not yet implemented
  }

  @Test public void TestSWRLTBoxDPRABuiltInWithBound1stUnbound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyRange(DP1, D1));
    addOWLAxioms(ontology, DataPropertyRange(DP2, D2));

    //SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:dpra(dp1, ?r) -> sqwrl:select(dp1, ?r)");

    // TODO tbox:dpra not yet implemented
  }

  @Test public void TestSWRLTBoxDPRABuiltInWithUnbound1stBound2ndArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    addOWLAxioms(ontology, DataPropertyRange(DP1, D1));
    addOWLAxioms(ontology, DataPropertyRange(DP2, D2));

    // SQWRLResult result = queryEngine.runSQWRLQuery("q1", "tbox:dpra(dp1, ?r) -> sqwrl:select(?d, D1)");

    // TODO tbox:dpra not yet implemented
  }
}