package com.example.composedemo

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FetchViewModel : ViewModel() {
    val data = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    @SuppressLint("CheckResult")
    fun fetchData() {
        Observable.just(1)
            .subscribeOn(Schedulers.newThread())
            .map {
                Thread.sleep(2000)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .compose(loading())
            .subscribe {
                data.value = "done!"
            }
    }

    private fun <T> loading(): ObservableTransformer<T, T> {
        return ObservableTransformer { single ->
            single
                .doOnSubscribe { loading.value = true }
                .doFinally { loading.value = false }
        }
    }
}