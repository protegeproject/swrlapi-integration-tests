package org.swrlapi;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.test.IntegrationTestBase;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;

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

  @Test public void TestSWRLRBoxTOPABuiltInWithAllUnboundArguments()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
  }

  // TRANSITIVE_OBJECT_PROPERTY,
  // DISJOINT_OBJECT_PROPERTIES,
  // SUB_OBJECT_PROPERTY,
  // EQUIVALENT_OBJECT_PROPERTIES,
  // INVERSE_OBJECT_PROPERTIES,
  // SYMMETRIC_OBJECT_PROPERTY,
  // ASYMMETRIC_OBJECT_PROPERTY,
  // REFLEXIVE_OBJECT_PROPERTY,
  // IRREFLEXIVE_OBJECT_PROPERTY,
  // DISJOINT_DATA_PROPERTIES, SUB_DATA_PROPERTY, EQUIVALENT_DATA_PROPERTIES,
  // TODO: SUB_PROPERTY_CHAIN_OF - rbox:spcoa
}