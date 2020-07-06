package com.team.runnershi.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.textChangeListener(textChanged : (CharSequence?) -> Unit){
    this.addTextChangedListener(object :TextWatcher{
        override fun afterTextChanged(p0: Editable?){
            textChanged(p0)
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
    })
}