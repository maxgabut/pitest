/*
 * Copyright 2011 Henry Coles
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

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.mutators.ArgumentPropagationMutator;
import org.pitest.mutationtest.engine.gregor.mutators.InvertNegsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.MathMutator;

public class MutatorTest {

  @Test
  public void shouldReturnRequestedMutators() {
    assertThat(parseStrings("MATH", "INVERT_NEGS")).containsAll(
        asList(MathMutator.MATH_MUTATOR,
            InvertNegsMutator.INVERT_NEGS_MUTATOR));
  }

  @Test
  public void shouldNotCreateDuplicatesWhenRequestedDirectly() {
    assertThat(parseStrings("MATH", "MATH")).hasSize(1);
  }

  @Test
  public void shouldNotCreateDuplicatesWhenRequestedViaGroup() {
    assertThat(parseStrings("MATH", "DEFAULTS")).hasSameSizeAs(
        parseStrings("DEFAULTS"));
  }

  private Collection<MethodMutatorFactory> parseStrings(final String... s) {
    return Mutator.fromStrings(asList(s));
  }

  @Test
  public void allContainsReplaceMethodMutator() throws Exception {
    assertThat(Mutator.all()).contains(
        ArgumentPropagationMutator.ARGUMENT_PROPAGATION_MUTATOR);
  }

  @Test
  public void allShouldHaveTheSameContentNoMatterTheWayToGetIt() {
    assertThat(Mutator.all())
        .hasSameElementsAs(Mutator.byName("ALL"));

    assertThat(Mutator.all())
        .hasSameElementsAs(Mutator.fromStrings(singletonList("ALL")));
  }

  @Test
  public void defaultsShouldHaveTheSameContentNoMatterTheWayToGetIt() {
    assertThat(Mutator.defaults())
        .hasSameElementsAs(Mutator.byName("DEFAULTS"));

    assertThat(Mutator.defaults())
        .hasSameElementsAs(Mutator.fromStrings(singletonList("DEFAULTS")));
  }

  @Test
  public void allShouldContainTheRightMutators() {
    assertThat(Mutator.all())
        .extracting(MethodMutatorFactory::getGloballyUniqueId)
        .containsExactly(
            "org.pitest.mutationtest.engine.gregor.mutators.ArgumentPropagationMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.ConditionalsBoundaryMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.ConstructorCallsMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.EmptyReturnsMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.FalseReturnsMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.IncrementsMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.InlineConstsMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.InvertNegsMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.MathMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.NegateConditionalsMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.NonVoidMethodCallsMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.NullReturnsMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.PrimitiveReturnsMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.RemoveConditionalsMutator_EQUAL_ELSE",
            "org.pitest.mutationtest.engine.gregor.mutators.RemoveConditionalsMutator_EQUAL_IF",
            "org.pitest.mutationtest.engine.gregor.mutators.RemoveConditionalsMutator_ORDER_ELSE",
            "org.pitest.mutationtest.engine.gregor.mutators.RemoveConditionalsMutator_ORDER_IF",
            "org.pitest.mutationtest.engine.gregor.mutators.ReturnValsMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.TrueReturnsMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.VoidMethodCallsMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.BigIntegerMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.MemberVariableMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.NakedReceiverMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveIncrementsMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_0",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_1",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_10",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_11",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_12",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_13",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_14",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_15",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_16",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_17",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_18",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_19",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_2",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_20",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_21",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_22",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_23",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_24",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_25",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_26",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_27",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_28",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_29",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_3",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_30",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_31",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_32",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_33",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_34",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_35",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_36",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_37",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_38",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_39",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_4",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_40",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_41",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_42",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_43",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_44",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_45",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_46",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_47",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_48",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_49",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_5",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_50",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_51",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_52",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_53",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_54",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_55",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_56",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_57",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_58",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_59",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_6",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_60",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_61",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_62",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_63",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_64",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_65",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_66",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_67",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_68",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_69",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_7",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_70",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_71",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_72",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_73",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_74",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_75",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_76",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_77",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_78",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_79",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_8",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_80",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_81",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_82",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_83",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_84",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_85",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_86",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_87",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_88",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_89",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_9",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_90",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_91",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_92",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_93",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_94",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_95",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_96",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_97",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_98",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_99",
            "org.pitest.mutationtest.engine.gregor.mutators.experimental.SwitchMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.rv.ABSMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.rv.AOD1Mutator",
            "org.pitest.mutationtest.engine.gregor.mutators.rv.AOD2Mutator",
            "org.pitest.mutationtest.engine.gregor.mutators.rv.AOR1Mutator",
            "org.pitest.mutationtest.engine.gregor.mutators.rv.AOR2Mutator",
            "org.pitest.mutationtest.engine.gregor.mutators.rv.AOR3Mutator",
            "org.pitest.mutationtest.engine.gregor.mutators.rv.AOR4Mutator",
            "org.pitest.mutationtest.engine.gregor.mutators.rv.CRCR1Mutator",
            "org.pitest.mutationtest.engine.gregor.mutators.rv.CRCR2Mutator",
            "org.pitest.mutationtest.engine.gregor.mutators.rv.CRCR3Mutator",
            "org.pitest.mutationtest.engine.gregor.mutators.rv.CRCR4Mutator",
            "org.pitest.mutationtest.engine.gregor.mutators.rv.CRCR5Mutator",
            "org.pitest.mutationtest.engine.gregor.mutators.rv.CRCR6Mutator",
            "org.pitest.mutationtest.engine.gregor.mutators.rv.OBBN1Mutator",
            "org.pitest.mutationtest.engine.gregor.mutators.rv.OBBN2Mutator",
            "org.pitest.mutationtest.engine.gregor.mutators.rv.OBBN3Mutator",
            "org.pitest.mutationtest.engine.gregor.mutators.rv.ROR1Mutator",
            "org.pitest.mutationtest.engine.gregor.mutators.rv.ROR2Mutator",
            "org.pitest.mutationtest.engine.gregor.mutators.rv.ROR3Mutator",
            "org.pitest.mutationtest.engine.gregor.mutators.rv.ROR4Mutator",
            "org.pitest.mutationtest.engine.gregor.mutators.rv.ROR5Mutator",
            "org.pitest.mutationtest.engine.gregor.mutators.rv.UOI1Mutator",
            "org.pitest.mutationtest.engine.gregor.mutators.rv.UOI2Mutator",
            "org.pitest.mutationtest.engine.gregor.mutators.rv.UOI3Mutator",
            "org.pitest.mutationtest.engine.gregor.mutators.rv.UOI4Mutator"
        );
  }

  @Test
  public void defaultsShouldContainTheRightMutators() {
    assertThat(Mutator.defaults())
        .extracting(MethodMutatorFactory::getGloballyUniqueId)
        .containsExactly(
            "org.pitest.mutationtest.engine.gregor.mutators.InvertNegsMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.ReturnValsMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.MathMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.VoidMethodCallsMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.NegateConditionalsMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.ConditionalsBoundaryMutator",
            "org.pitest.mutationtest.engine.gregor.mutators.IncrementsMutator"
        );
  }
}
