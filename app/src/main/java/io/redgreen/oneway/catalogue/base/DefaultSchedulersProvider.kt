package io.redgreen.oneway.catalogue.base

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DefaultSchedulersProvider : SchedulersProvider {
  override fun ui(): Scheduler =
      AndroidSchedulers.mainThread()

  override fun computation(): Scheduler =
      Schedulers.computation()
}
