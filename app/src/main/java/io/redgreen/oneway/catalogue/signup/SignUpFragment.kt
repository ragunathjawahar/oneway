package io.redgreen.oneway.catalogue.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.redgreen.oneway.SourceEvent
import io.redgreen.oneway.android.OneWayFragment
import io.redgreen.oneway.catalogue.R
import io.redgreen.oneway.catalogue.base.DefaultSchedulersProvider
import io.redgreen.oneway.catalogue.base.SchedulersProvider
import io.redgreen.oneway.catalogue.signup.drivers.SignUpViewDriver
import io.redgreen.oneway.catalogue.signup.form.PhoneNumberCondition
import io.redgreen.oneway.catalogue.signup.form.UsernameCondition
import io.redgreen.oneway.catalogue.signup.form.Validator
import io.redgreen.oneway.catalogue.signup.usecases.SignUpUseCases
import kotlinx.android.synthetic.main.sign_up_fragment.*

class SignUpFragment : OneWayFragment<SignUpState>(), SignUpView {
  private val intentionsGroup: SignUpIntentionsGroup
    get() = SignUpIntentionsGroup(
        phoneNumberEditText.textChanges(),
        usernameEditText.textChanges()
    )

  private val validator: Validator
    get() = Validator()

  private val useCases: SignUpUseCases
    get() = SignUpUseCases(
        SignUpState.UNTOUCHED,
        timeline,
        validator
    )

  private val schedulersProvider: SchedulersProvider
    get() = DefaultSchedulersProvider()

  private val viewDriver: SignUpViewDriver
    get() = SignUpViewDriver(this, schedulersProvider)

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View {
    return inflater.inflate(R.layout.sign_up_fragment, container, false)
  }

  override fun source(
      sourceEvents: Observable<SourceEvent>,
      timeline: Observable<SignUpState>
  ): Observable<SignUpState> {
    return SignUpModel
        .createSource(
            intentionsGroup.intentions(),
            viewDriver.displayErrorEvents(),
            sourceEvents,
            useCases
        )
  }

  override fun sink(source: Observable<SignUpState>): Disposable =
      viewDriver.render(source)

  override fun showPhoneNumberErrors(unmetConditions: Set<PhoneNumberCondition>) {
    phoneNumberTextInputLayout.error = unmetConditions.toString()
  }

  override fun hidePhoneNumberError() {
    phoneNumberTextInputLayout.error = null
  }

  override fun showUsernameErrors(unmetConditions: Set<UsernameCondition>) {
    usernameTextInputLayout.error = unmetConditions.toString()
  }

  override fun hideUsernameError() {
    usernameTextInputLayout.error = null
  }
}
