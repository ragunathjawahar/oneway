package io.mobsgeeks.oneway.catalogue.budapest

import android.os.Bundle
import com.jakewharton.rxbinding2.widget.textChanges
import io.mobsgeeks.oneway.Binding
import io.mobsgeeks.oneway.catalogue.R
import io.mobsgeeks.oneway.catalogue.budapest.drivers.BudapestViewDriver
import io.mobsgeeks.oneway.catalogue.budapest.usecases.BudapestUseCases
import io.mobsgeeks.oneway.catalogue.budapest.usecases.NameChangeUseCase
import io.mobsgeeks.oneway.catalogue.mvi.MviActivity
import io.mobsgeeks.oneway.usecases.DefaultBindingCreatedUseCase
import io.mobsgeeks.oneway.usecases.DefaultBindingRestoredUseCase
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.budapest_fragment.*

class BudapestActivity : MviActivity<BudapestState>(), BudapestView {
  private val intentions: Observable<BudapestIntention>
    get() = nameEditText.textChanges().skipInitialValue()
        .map { it.toString() }
        .map { NameChangeIntention(it) }

  private val useCases: BudapestUseCases
    get() = BudapestUseCases(
        DefaultBindingCreatedUseCase(BudapestState.STRANGER),
        DefaultBindingRestoredUseCase(timeline),
        NameChangeUseCase()
    )

  private val viewDriver: BudapestViewDriver
    get() = BudapestViewDriver(this)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.budapest_fragment)
  }

  override fun source(
      bindings: Observable<Binding>,
      timeline: Observable<BudapestState>
  ): Observable<BudapestState> =
      BudapestModel.bind(intentions, bindings, useCases)

  override fun sink(source: Observable<BudapestState>): Disposable =
      viewDriver.render(source)

  override fun greetStranger() {
    greetingTextView.text = getString(R.string.hello_stranger)
  }

  override fun greet(name: String) {
    greetingTextView.text = getString(R.string.template_greeting, name)
  }
}