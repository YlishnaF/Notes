package com.example.notes.ui.common

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.notes.R
import com.example.notes.extensions.MyLog
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity<T, VS : BaseViewState<T>> : AppCompatActivity() {
    abstract val viewModel: BaseViewModel<T, VS>
    abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        viewModel.getViewSate().observe(this, object : Observer<VS> {
            override fun onChanged(t: VS?) {
                if (t == null) return
                if (t.data != null) renderData(t.data)
                if (t.error != null) renderError(t.error)
            }
        })
    }

    abstract fun renderData(data: T)

    protected fun renderError(error: Throwable) {
        if (error.message != null) showError(error.message!!)
    }

    protected fun showError(error: String) {
        Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
    }
}

