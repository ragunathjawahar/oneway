package io.mobsgeeks.oneway.catalogue.budapest.drivers

import io.mobsgeeks.oneway.catalogue.budapest.BudapestState
import io.mobsgeeks.oneway.catalogue.budapest.BudapestView
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

class BudapestViewDriver(private val view: BudapestView) {
  fun render(states: Observable<BudapestState>): Disposable {
    return states.subscribe {
      if (it == BudapestState.STRANGER) {
        view.greetStranger()
      } else {
        view.greet(it.name)
      }
    }
  }
}
