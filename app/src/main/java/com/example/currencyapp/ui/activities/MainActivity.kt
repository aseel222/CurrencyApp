package com.example.currencyapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.currencyapp.databinding.ActivityMainBinding
import com.example.currencyapp.model.CountryCurrency
import com.example.currencyapp.model.sympols.Symbols
import com.example.currencyapp.ui.adapter.CurrencyDropDownAdapter
import com.example.currencyapp.viewmodel.ConvertCurrencyViewModel
import com.example.currencyapp.viewmodel.SympolsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.full.memberProperties

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val sympolsviewmodel:SympolsViewModel by viewModels()
    private val convertcurrencyviewmodel:ConvertCurrencyViewModel by viewModels()
    private lateinit var currencykeys:ArrayList<CountryCurrency>
    private lateinit var currencyadapter:CurrencyDropDownAdapter
    var fromvalue=""
    var tovalue=""
    var amount=""
    var selection1=""
    var selection2=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currencykeys= ArrayList()

        sympolsviewmodel.getsympols("Tcci5REFyKKzYJXIxRuaKuhKr3FfmpP7")
        observesympolse()


        binding.fromDropdown.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position!=0){
                    val from: CountryCurrency = parent!!.getItemAtPosition(position) as CountryCurrency
                    fromvalue = from.name
                    selection1=from.value
                    amount=binding.fromEtxt.text.toString()
                    if(amount.isEmpty()){
                        amount=="1"
                        return
                    }
                    if(fromvalue==tovalue){
                        Toast.makeText(this@MainActivity,"please enter valid currency",Toast.LENGTH_SHORT).show()
                        return

                    }
                    convertcurrencyviewmodel.getconvertedvalue("sfJL0bweJcWqlZlYLDpADkiaruvw4SIK",amount,fromvalue,tovalue)

                }



            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }

        }
        binding.toDropdown.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position!=0) {
                    val from: CountryCurrency =
                        parent!!.getItemAtPosition(position) as CountryCurrency
                    tovalue = from.name
                    selection2=from.value
                    amount=binding.fromEtxt.text.toString()
                    if(amount.isEmpty()){
                        amount=="1"
                        return
                    }
                    if(fromvalue==tovalue){
                        Toast.makeText(this@MainActivity,"please enter valid currency",Toast.LENGTH_SHORT).show()
                        return

                    }
                    convertcurrencyviewmodel.getconvertedvalue("Tcci5REFyKKzYJXIxRuaKuhKr3FfmpP7",amount,fromvalue,tovalue)

                }



            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }

        }


       binding.fromEtxt.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if(s.toString().isEmpty()){
                        s.toString()=="1"
                        return
                    }
                    if(fromvalue==tovalue){
                        Toast.makeText(this@MainActivity,"please enter valid currency",Toast.LENGTH_SHORT).show()
                        return

                    }
                    convertcurrencyviewmodel.getconvertedvalue("sfJL0bweJcWqlZlYLDpADkiaruvw4SIK",it.toString(),fromvalue,tovalue)

                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        binding.swapBtn.setOnClickListener {
            var value2=currencykeys.singleOrNull {
                it.value==selection2
            }
            var value=currencykeys.singleOrNull {
                it.value==selection1
            }
            var index=currencykeys.indexOf(value)
            var index2=currencykeys.indexOf(value2)
            binding.toDropdown.setSelection(index)
            binding.fromDropdown.setSelection(index2)
            amount=binding.fromEtxt.text.toString()
            if(amount.isEmpty()){
                amount=="1"
                return@setOnClickListener
            }

            convertcurrencyviewmodel.getconvertedvalue("Tcci5REFyKKzYJXIxRuaKuhKr3FfmpP7",amount,fromvalue,tovalue)


        }



        observeconvertcurrency()

    }
    fun observesympolse(){
        sympolsviewmodel.sympolslivedata.observe(this, Observer {
            val firstitem=CountryCurrency("empty","Please select currency")
            currencykeys.add(0,firstitem)

            for (prop in Symbols::class.memberProperties){
                val countryCurrency = CountryCurrency(prop.name , prop.get(it.symbols!!).toString())

                currencykeys.add(countryCurrency)
            }
            currencyadapter= CurrencyDropDownAdapter(this, currencykeys)
            binding.fromDropdown.adapter=currencyadapter
            binding.toDropdown.adapter=currencyadapter

        })
        sympolsviewmodel.msg.observe(this, Observer {
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        })

    }
    fun observeconvertcurrency(){
       convertcurrencyviewmodel.convertcurrencylivedata.observe(this, Observer {
           binding.toEtxt.setText(it.result.toString())
       })
        convertcurrencyviewmodel.msg.observe(this, Observer {
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        })
    }


}