/*
 * Copyright 2010 Henry Coles
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package org.pitest.mutationtest.engine.gregor.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import org.pitest.functional.FCollection;
import org.pitest.help.Help;
import org.pitest.help.PitHelpError;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.mutators.ArgumentPropagationMutator;
import org.pitest.mutationtest.engine.gregor.mutators.EmptyReturnsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.FalseReturnsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.InlineConstsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.TrueReturnsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.ConditionalsBoundaryMutator;
import org.pitest.mutationtest.engine.gregor.mutators.ConstructorCallsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.IncrementsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.InvertNegsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.MathMutator;
import org.pitest.mutationtest.engine.gregor.mutators.NegateConditionalsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.NonVoidMethodCallsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.NullReturnsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.PrimitiveReturnsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.RemoveConditionalsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.RemoveConditionalsMutator.Choice;
import org.pitest.mutationtest.engine.gregor.mutators.ReturnValsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.VoidMethodCallsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.experimental.BigIntegerMutator;
import org.pitest.mutationtest.engine.gregor.mutators.experimental.MemberVariableMutator;
import org.pitest.mutationtest.engine.gregor.mutators.experimental.NakedReceiverMutator;
import org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveIncrementsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator;
import org.pitest.mutationtest.engine.gregor.mutators.experimental.SwitchMutator;
import org.pitest.mutationtest.engine.gregor.mutators.rv.ABSMutator;
import org.pitest.mutationtest.engine.gregor.mutators.rv.AOD1Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.rv.AOD2Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.rv.AOR1Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.rv.AOR2Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.rv.AOR3Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.rv.AOR4Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.rv.CRCR1Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.rv.CRCR2Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.rv.CRCR3Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.rv.CRCR4Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.rv.CRCR5Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.rv.CRCR6Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.rv.OBBN1Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.rv.OBBN2Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.rv.OBBN3Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.rv.ROR1Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.rv.ROR2Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.rv.ROR3Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.rv.ROR4Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.rv.ROR5Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.rv.UOI1Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.rv.UOI2Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.rv.UOI3Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.rv.UOI4Mutator;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public final class Mutator {

  private static final Map<String, Iterable<MethodMutatorFactory>> MUTATORS = new LinkedHashMap<>();

  static {

    /*
     * Default set of groupMutators - designed to provide balance between strength and
     * performance
     */
    buildGroup("DEFAULTS")
        /*
         * Default mutator that inverts the negation of integer and floating point
         * numbers.
         */
        .withMutator("INVERT_NEGS",
            InvertNegsMutator.INVERT_NEGS_MUTATOR)

        /*
         * Default mutator that mutates the return values of methods.
         */
        .withMutator("RETURN_VALS",
            ReturnValsMutator.RETURN_VALS_MUTATOR)

        /*
         * Default mutator that mutates binary arithmetic operations.
         */
        .withMutator("MATH", MathMutator.MATH_MUTATOR)

        /*
         * Default mutator that removes method calls to void methods.
         */
        .withMutator("VOID_METHOD_CALLS",
            VoidMethodCallsMutator.VOID_METHOD_CALLS_MUTATOR)

        /*
         * Default mutator that negates conditionals.
         */
        .withMutator("NEGATE_CONDITIONALS",
            NegateConditionalsMutator.NEGATE_CONDITIONALS_MUTATOR)

        /*
         * Default mutator that replaces the relational operators with their
         * boundary counterpart.
         */
        .withMutator("CONDITIONALS_BOUNDARY",
            ConditionalsBoundaryMutator.CONDITIONALS_BOUNDARY_MUTATOR)

        /*
         * Default mutator that mutates increments, decrements and assignment
         * increments and decrements of local variables.
         */
        .withMutator("INCREMENTS",
            IncrementsMutator.INCREMENTS_MUTATOR);

    /**
     * Optional mutator that mutates integer and floating point inline
     * constants.
     */
    add("INLINE_CONSTS", new InlineConstsMutator());

    /**
     * Optional mutator that removes local variable increments.
     */

    add("REMOVE_INCREMENTS", RemoveIncrementsMutator.REMOVE_INCREMENTS_MUTATOR);

    /**
     * Optional mutator that removes method calls to non void methods.
     */
    add("NON_VOID_METHOD_CALLS",
        NonVoidMethodCallsMutator.NON_VOID_METHOD_CALLS_MUTATOR);

    /**
     * Optional mutator that replaces constructor calls with null values.
     */
    add("CONSTRUCTOR_CALLS", ConstructorCallsMutator.CONSTRUCTOR_CALLS_MUTATOR);

    /**
     * Removes conditional statements so that guarded statements always execute
     * The EQUAL version ignores LT,LE,GT,GE, which is the default behaviour,
     * ORDER version mutates only those.
     */

    buildGroup("REMOVE_CONDITIONALS")
        .withMutator("REMOVE_CONDITIONALS_EQ_IF",
            new RemoveConditionalsMutator(Choice.EQUAL, true))
        .withMutator("REMOVE_CONDITIONALS_EQ_ELSE",
            new RemoveConditionalsMutator(Choice.EQUAL, false))
        .withMutator("REMOVE_CONDITIONALS_ORD_IF",
            new RemoveConditionalsMutator(Choice.ORDER, true))
        .withMutator("REMOVE_CONDITIONALS_ORD_ELSE",
            new RemoveConditionalsMutator(Choice.ORDER, false));

    buildGroup("RETURNS")
        .withMutator("TRUE_RETURNS",
            TrueReturnsMutator.TRUE_RETURNS_MUTATOR)
        .withMutator("FALSE_RETURNS",
            FalseReturnsMutator.FALSE_RETURNS_MUTATOR)
        .withMutator("PRIMITIVE_RETURNS",
            PrimitiveReturnsMutator.PRIMITIVE_RETURNS_MUTATOR)
        .withMutator("EMPTY_RETURNS",
            EmptyReturnsMutator.EMPTY_RETURNS_MUTATOR)
        .withMutator("NULL_RETURNS",
            NullReturnsMutator.NULL_RETURNS_MUTATOR);

    experimentalMutators();
    
    researchMutators();

    buildGroup("REMOVE_SWITCH")
        .withMutators(RemoveSwitchMutator.makeMutators());

    addGroup("STRONGER", stronger());
    addGroup("NEW_DEFAULTS", newDefaults());

    addGroup("UOI", uoi());

    addGroup("ALL", fromStrings(MUTATORS.keySet()));
  }
  
  /**
   * Mutators that have not yet been battle tested
   */
  private static void experimentalMutators() {
    /**
     * Experimental mutator that removed assignments to member variables.
     */
    add("EXPERIMENTAL_MEMBER_VARIABLE", new MemberVariableMutator());

    /**
     * Experimental mutator that swaps labels in switch statements
     */
    add("EXPERIMENTAL_SWITCH", new SwitchMutator());

    /**
     * Experimental mutator that replaces method call with one of its parameters
     * of matching type
     */
    add("EXPERIMENTAL_ARGUMENT_PROPAGATION",
        ArgumentPropagationMutator.ARGUMENT_PROPAGATION_MUTATOR);

    /**
     * Experimental mutator that replaces method call with this
     */
    add("EXPERIMENTAL_NAKED_RECEIVER", NakedReceiverMutator.NAKED_RECEIVER);
    
    /**
     * Experimental mutator that swaps big integer methods
     */
    add("EXPERIMENTAL_BIG_INTEGER", BigIntegerMutator.INSTANCE);
  }
  
  /**
   * "Research" mutators that makeup "PITrv" as described in
   * https://researchrepository.ucd.ie/bitstream/10197/7748/4/ISSTA_2016_Demo_Camera_ready.pdf
   */
  private static void researchMutators() {
    /*
     * mutators that mutate binary arithmetic operations.
     */
    buildGroup("AOR")
            .withMutator("AOR_1", AOR1Mutator.AOR_1_MUTATOR)
            .withMutator("AOR_2", AOR2Mutator.AOR_2_MUTATOR)
            .withMutator("AOR_3", AOR3Mutator.AOR_3_MUTATOR)
            .withMutator("AOR_4", AOR4Mutator.AOR_4_MUTATOR);

    /**
     * mutator that replaces a variable with its negation.
     */
    add("ABS", ABSMutator.ABS_MUTATOR);

    /*
     * mutators that replace a binary arithmetic operations with one of its members.
     */
    buildGroup("AOD")
            .withMutator("AOD1", AOD1Mutator.AOD_1_MUTATOR)
            .withMutator("AOD2", AOD2Mutator.AOD_2_MUTATOR);

    /*
     * mutators that replace an inline constant a with 0, 1, -1, a+1 or a-1 .
     */
    buildGroup("CRCR")
            .withMutator("CRCR1", CRCR1Mutator.CRCR_1_MUTATOR)
            .withMutator("CRCR2", CRCR2Mutator.CRCR_2_MUTATOR)
            .withMutator("CRCR3", CRCR3Mutator.CRCR_3_MUTATOR)
            .withMutator("CRCR4", CRCR4Mutator.CRCR_4_MUTATOR)
            .withMutator("CRCR5", CRCR5Mutator.CRCR_5_MUTATOR)
            .withMutator("CRCR6", CRCR6Mutator.CRCR_6_MUTATOR);

    /*
     * mutators that replace an bitwise ands and ors.
     */
    buildGroup("OBBN")
            .withMutator("OBBN1", OBBN1Mutator.OBBN_1_MUTATOR)
            .withMutator("OBBN2", OBBN2Mutator.OBBN_2_MUTATOR)
            .withMutator("OBBN3", OBBN3Mutator.OBBN_3_MUTATOR);

    /*
     * mutators that replace conditional operators.
     */
    buildGroup("ROR")
            .withMutator("ROR1", ROR1Mutator.ROR_1_MUTATOR)
            .withMutator("ROR2", ROR2Mutator.ROR_2_MUTATOR)
            .withMutator("ROR3", ROR3Mutator.ROR_3_MUTATOR)
            .withMutator("ROR4", ROR4Mutator.ROR_4_MUTATOR)
            .withMutator("ROR5", ROR5Mutator.ROR_5_MUTATOR);

    /**
     * mutators that insert increments.
     */
    add("UOI1", UOI1Mutator.UOI_1_MUTATOR);
    add("UOI2", UOI2Mutator.UOI_2_MUTATOR);
    add("UOI3", UOI3Mutator.UOI_3_MUTATOR);
    add("UOI4", UOI4Mutator.UOI_4_MUTATOR);
  }

  private static Collection<MethodMutatorFactory> stronger() {
    return combine(fromStrings("DEFAULTS"),
        group(new RemoveConditionalsMutator(Choice.EQUAL, false),
            new SwitchMutator()));
  }

  private static Collection<MethodMutatorFactory> combine(
      Collection<MethodMutatorFactory> a, Collection<MethodMutatorFactory> b) {
    final List<MethodMutatorFactory> l = new ArrayList<>(a);
    l.addAll(b);
    return l;
  }

  /**
   * Proposed new defaults - replaced the RETURN_VALS mutator with the new more stable set
   */
  private static Collection<MethodMutatorFactory> newDefaults() {
    return combine(group(InvertNegsMutator.INVERT_NEGS_MUTATOR,
        MathMutator.MATH_MUTATOR,
        VoidMethodCallsMutator.VOID_METHOD_CALLS_MUTATOR,
        NegateConditionalsMutator.NEGATE_CONDITIONALS_MUTATOR,
        ConditionalsBoundaryMutator.CONDITIONALS_BOUNDARY_MUTATOR,
        IncrementsMutator.INCREMENTS_MUTATOR),
        fromStrings("RETURNS"));
  }

  private static Collection<MethodMutatorFactory> uoi() {
    return group(UOI1Mutator.UOI_1_MUTATOR,
            UOI2Mutator.UOI_2_MUTATOR,
            UOI3Mutator.UOI_3_MUTATOR,
            UOI4Mutator.UOI_4_MUTATOR);
  }

  private static Collection<MethodMutatorFactory> group(
      final MethodMutatorFactory... ms) {
    return asList(ms);
  }

  private static void add(final String key, final MethodMutatorFactory value) {
    MUTATORS.put(key, Collections.singleton(value));
  }

  private static void addGroup(final String key,
      final Iterable<MethodMutatorFactory> value) {
    MUTATORS.put(key, value);
  }

  public static Collection<MethodMutatorFactory> fromStrings(String ...names) {
    return fromStrings(asList(names));
  }

  public static Collection<MethodMutatorFactory> fromStrings(
      final Collection<String> names) {
    final Set<MethodMutatorFactory> unique = new TreeSet<>(
        compareId());

    FCollection.flatMapTo(names, fromString(), unique);
    return unique;
  }

  private static Comparator<? super MethodMutatorFactory> compareId() {
    return (o1, o2) -> o1.getGloballyUniqueId().compareTo(o2.getGloballyUniqueId());
  }

  private static Function<String, Iterable<MethodMutatorFactory>> fromString() {
    return a -> {
      final Iterable<MethodMutatorFactory> i = MUTATORS.get(a);
      if (i == null) {
        throw new PitHelpError(Help.UNKNOWN_MUTATOR, a);
      }
      return i;
    };
  }

  static Iterable<String> allKeys() {
    return MUTATORS.keySet();
  }

  private static GroupBuilder buildGroup(String groupKey) {
    return new GroupBuilder(groupKey);
  }

  private static final class GroupBuilder {
    private final List<MethodMutatorFactory> groupMutators;

    private GroupBuilder(String groupKey) {
      this.groupMutators = new ArrayList<>();
      MUTATORS.put(groupKey, groupMutators);
    }

    private GroupBuilder withMutator(
        String mutatorKey, MethodMutatorFactory mutator) {

      groupMutators.add(mutator);
      MUTATORS.put(mutatorKey, singletonList(mutator));

      return this;
    }

    private GroupBuilder withMutators(Iterable<MethodMutatorFactory> mutators) {
      mutators.forEach(groupMutators::add);
      return this;
    }
  }
}
