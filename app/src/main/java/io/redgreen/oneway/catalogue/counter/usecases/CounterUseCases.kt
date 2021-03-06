package io.redgreen.oneway.catalogue.counter.usecases

import io.reactivex.Observable
import io.redgreen.oneway.catalogue.counter.CounterState
import io.redgreen.oneway.catalogue.counter.CounterState.Companion.ZERO
import io.redgreen.oneway.usecases.SourceCreatedUseCase
import io.redgreen.oneway.usecases.SourceRestoredUseCase

class CounterUseCases(
    sourceCopy: Observable<CounterState>
) {
  val sourceCreatedUseCase = SourceCreatedUseCase(ZERO)
  val sourceRestoredUseCase = SourceRestoredUseCase(sourceCopy)
  val incrementUseCase = AddDeltaUseCase(sourceCopy, +1)
  val decrementUseCase = AddDeltaUseCase(sourceCopy, -1)
}
