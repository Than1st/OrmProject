package com.than.ormproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.than.ormproject.databinding.ActivityAddStudentBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class AddStudentActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddStudentBinding
    var mDb : StudentDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mDb = StudentDatabase.getInstance(this)

        binding.btnInput.setOnClickListener{
            val objectStudent = Student(null, binding.etNama.text.toString(), binding.etEmail.text.toString())
            GlobalScope.async {
                val result = mDb?.studentDao()?.insertStudent(objectStudent)
                runOnUiThread {
                    if(result != 0.toLong()){
                        Toast.makeText(
                            this@AddStudentActivity,
                            "Sukses menambahkan ${objectStudent.nama}",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@AddStudentActivity,
                            "Gagal Menambahkan ${objectStudent.nama}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    finish()
                }
            }
        }
    }
}