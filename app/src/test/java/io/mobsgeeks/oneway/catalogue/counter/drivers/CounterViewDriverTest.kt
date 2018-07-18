package io.mobsgeeks.oneway.catalogue.counter.drivers

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import io.mobsgeeks.oneway.catalogue.counter.CounterState
import io.mobsgeeks.oneway.catalogue.counter.CounterView
import io.reactivex.subjects.PublishSubject
import org.junit.Test

class CounterViewDriverTest {
  private val states = PublishSubject.create<CounterState>()

  @Test fun `it renders counter value`() {
    // given
    val view = mock<CounterView>()
    val viewDriver = CounterViewDriver(view)
    viewDriver.render(states)

    // when
    val seven = CounterState(7)
    states.onNext(seven)

    // then
    verify(view).showCounter(seven.counter)
    verifyNoMoreInteractions(view)
  }
}