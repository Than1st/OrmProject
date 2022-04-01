package com.than.ormproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.than.ormproject.databinding.ActivityEditStudentBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class EditStudentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditStudentBinding
    private var mDb : StudentDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mDb = StudentDatabase.getInstance(this)
        val objectStudent = intent.getParcelableExtra<Student>("student")
        binding.etNama.setText(objectStudent!!.nama)
        binding.etEmail.setText(objectStudent.email)
        binding.btnSubmit.setOnClickListener{
            objectStudent.nama = binding.etNama.text.toString()
            objectStudent.email = binding.etEmail.text.toString()
            GlobalScope.async{
                val result = mDb?.studentDao()?.updateStudent(objectStudent)
                runOnUiThread {
                    if (result != 0){
                        Toast.makeText(this@EditStudentActivity, "Sukses Menambahkan ${objectStudent.nama}", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@EditStudentActivity, "Gagal Menambahkan ${objectStudent.nama}", Toast.LENGTH_SHORT).show()
                    }
                    finish()
                }
            }
        }
    }
}