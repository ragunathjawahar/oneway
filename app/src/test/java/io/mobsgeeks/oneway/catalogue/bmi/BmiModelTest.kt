package io.mobsgeeks.oneway.catalogue.bmi

import io.mobsgeeks.oneway.SourceEvent
import io.mobsgeeks.oneway.catalogue.bmi.MeasurementSystem.IMPERIAL
import io.mobsgeeks.oneway.catalogue.bmi.MeasurementSystem.SI
import io.mobsgeeks.oneway.catalogue.bmi.usecases.BmiUseCases
import io.mobsgeeks.oneway.catalogue.bmi.usecases.ChangeHeightUseCase
import io.mobsgeeks.oneway.catalogue.bmi.usecases.ChangeMeasurementSystemUseCase
import io.mobsgeeks.oneway.catalogue.bmi.usecases.ChangeWeightUseCase
import io.mobsgeeks.oneway.test.MviTestRule
import io.mobsgeeks.oneway.usecases.DefaultSourceCreatedUseCase
import io.mobsgeeks.oneway.usecases.DefaultSourceRestoredUseCase
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.junit.Test

class BmiModelTest {
  private val initialState = BmiState(48.0, 160.0, SI)
  private val intentions = PublishSubject.create<BmiIntention>()
  private val testRule = MviTestRule { sourceEvents: Observable<SourceEvent>, timeline: Observable<BmiState> ->
    val useCases = BmiUseCases(
        DefaultSourceCreatedUseCase(initialState),
        DefaultSourceRestoredUseCase(timeline),
        ChangeWeightUseCase(timeline),
        ChangeHeightUseCase(timeline),
        ChangeMeasurementSystemUseCase(timeline)
    )
    BmiModel.createSource(intentions, sourceEvents, useCases)
  }

  @Test fun `when screen is created, then emit default state`() {
    // when
    testRule.screenIsCreated()

    // then
    testRule.assertStates(initialState)
  }

  @Test fun `when screen is restored, then emit last known state`() {
    // given
    val lastKnownState = BmiState(70.0, 170.0, SI)

    // when
    testRule.startWith(lastKnownState) {
      testRule.screenIsDestroyed()
      testRule.screenIsRestored()
    }

    // then
    testRule.assertStates(lastKnownState)
  }

  @Test fun `when user changes weight, then update weight`() {
    // given
    val startState = BmiState(70.0, 170.0, SI)
    val updatedWeightInKg = 72.0

    // when
    testRule.startWith(startState) {
      intentions.onNext(ChangeWeightIntention(updatedWeightInKg))
    }

    // then
    val updatedWeightState = startState.updateWeight(updatedWeightInKg)
    testRule.assertStates(updatedWeightState)
  }

  @Test fun `when user changes height, then update height`() {
    // given
    val startState = BmiState(70.0, 170.0, SI)
    val updatedHeightInCm = 180.0

    // when
    testRule.startWith(startState) {
      intentions.onNext(ChangeHeightIntention(updatedHeightInCm))
    }

    // then
    val updatedHeightState = startState.updateHeight(updatedHeightInCm)
    testRule.assertStates(updatedHeightState)
  }

  @Test fun `when user updates measurement system, then update measurement system`() {
    // given
    val startState = BmiState(70.0, 170.0, SI)
    val updatedMeasurementSystem = IMPERIAL

    // when
    testRule.startWith(startState) {
      intentions.onNext(ChangeMeasurementSystemIntention(updatedMeasurementSystem))
    }

    // then
    val updatedMeasurementSystemState = startState.updateMeasurementSystem(updatedMeasurementSystem)
    testRule.assertStates(updatedMeasurementSystemState)
  }
}
