package com.zerone.medtrail.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

abstract  class BaseActivity<VM : BaseViewModel>(clazz : KClass<VM>) : AppCompatActivity() {

    val viewModel : VM by viewModel(clazz)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(provideLayoutId())
        setupView(savedInstanceState)
        setupObservers()
        viewModel.onCreate()

    }

    @LayoutRes
    protected abstract fun provideLayoutId(): Int

    protected open fun setupObservers() {

        viewModel.messageStringId.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })


    }
    protected abstract fun setupView(savedInstanceState: Bundle?)

    fun showMessage(message: String) =
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()

    fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))

}


