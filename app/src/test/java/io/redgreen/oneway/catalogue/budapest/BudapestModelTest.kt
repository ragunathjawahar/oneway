package io.redgreen.oneway.catalogue.budapest

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.redgreen.oneway.SourceLifecycleEvent
import io.redgreen.oneway.catalogue.budapest.usecases.BudapestUseCases
import io.redgreen.oneway.test.MviTestHelper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BudapestModelTest {
  private val intentions = PublishSubject.create<BudapestIntention>()

  private val testHelper = MviTestHelper<BudapestState>()

  @BeforeEach fun setup() {
    testHelper.setSource {
      sourceLifecycleEvents: Observable<SourceLifecycleEvent>, sourceCopy: Observable<BudapestState> ->

      BudapestModel.createSource(
          intentions,
          sourceLifecycleEvents,
          BudapestUseCases(sourceCopy)
      )
    }
  }

  @Test fun `creating a screen starts with a stranger state`() {
    // when
    testHelper.sourceIsCreated()

    // then
    testHelper.assertStates(StrangerState)
  }

  @Test fun `restoring the screen restores the last known state`() {
    // when
    val spiderManState = GreeterState("Spider-Man")
    testHelper.setState(spiderManState) {
      testHelper.sourceIsRestored()
    }

    // then
    testHelper.assertStates(spiderManState)
  }

  @Test fun `entering a name emits a greeter state`() {
    // given
    val name = "Goundamani"

    // when
    testHelper.setState(StrangerState) {
      intentions.onNext(EnterNameIntention(name))
    }

    // then
    testHelper.assertStates(GreeterState(name))
  }

  @Test fun `entering a blank name emits a stranger state`() {
    // when
    testHelper.setState(StrangerState) {
      intentions.onNext(NoNameIntention)
    }

    // then
    testHelper.assertStates(StrangerState)
  }
}
