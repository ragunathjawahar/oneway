package io.redgreen.oneway.catalogue.signup.form

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Field<T>(
    val touched: Boolean = false,
    val unmetConditions: Set<T> = emptySet(),
    val displayError: Boolean = false
) : Parcelable where T : Enum<T>, T : Condition {
  fun unmetConditions(unmetConditions: Set<T>): Field<T> =
      copy(touched = true, unmetConditions = unmetConditions)

  fun displayError(display: Boolean): Field<T> =
      copy(displayError = display)

  fun hasErrors(): Boolean =
      unmetConditions.isNotEmpty()
}
