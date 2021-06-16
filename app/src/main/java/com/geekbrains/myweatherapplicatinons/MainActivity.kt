package com.geekbrains.myweatherapplicatinons

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.geekbrains.myweatherapplicatinons.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Тип байдинга будет исходить из названия layout
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // layoutInflater - системный сервис
        setContentView(binding.root) // root - корневой элемент
        binding.textView.setOnClickListener {
            Toast.makeText(applicationContext, "On text view clicked", Toast.LENGTH_SHORT).show()
        }

        val w = Weather()

        // анонимные классы начинаются со слова object
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //TODO("Not yet implemented")
            }

            override fun afterTextChanged(s: Editable?) {
                //TODO("Not yet implemented")
            }
        }
        binding.editText.addTextChangedListener(textWatcher)

        val text =binding.textView.text
        when(text){
            "hello" -> {
                Toast.makeText(applicationContext, "hello", Toast.LENGTH_SHORT).show()
            }
            "goodbye" -> {
                Toast.makeText(applicationContext, "goodbye", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(applicationContext, "else", Toast.LENGTH_SHORT).show()
            }
        }
    }
}