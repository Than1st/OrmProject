package com.than.ormproject

import android.app.AlertDialog
import android.content.Intent
import android.provider.Settings
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.than.ormproject.databinding.MyStudentItemBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class StudentAdapter(private val listStudent: List<Student>) :
    RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    class ViewHolder (val binding: MyStudentItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAdapter.ViewHolder {
        return ViewHolder(MyStudentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: StudentAdapter.ViewHolder, position: Int) {
        holder.binding.tvIdStudent.text = listStudent[position].id.toString()
        holder.binding.tvNamaStudent.text = listStudent[position].nama
        holder.binding.tvEmailStudent.text = listStudent[position].email

        holder.binding.btnEditStudent.setOnClickListener{
            val intentKeEditActivity = Intent(it.context, EditStudentActivity::class.java)
            intentKeEditActivity.putExtra("student", listStudent[position])
            it.context.startActivity(intentKeEditActivity)
        }

        holder.binding.btnDeleteStudent.setOnClickListener {
            AlertDialog.Builder(it.context).setPositiveButton("Ya"){p0, p1 ->
                val mDb = StudentDatabase.getInstance(holder.itemView.context)
                GlobalScope.async {
                    val result = mDb?.studentDao()?.deleteStudent(listStudent[position])
                    (holder.itemView.context as MainActivity).runOnUiThread{
                        if (result != 0){
                            Toast.makeText(it.context, "${listStudent[position].nama} berhasil dihapus!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(it.context, "${listStudent[position].nama} gagal dihapus!", Toast.LENGTH_SHORT).show()
                        }
                    }
                    (holder.itemView.context as MainActivity).fetchData()
                }
            }.setNegativeButton("Tidak"){ p0, p1 ->
                p0.dismiss()
            }
                .setMessage("Apakah Anda Yakin ingin menghapus data ${listStudent[position].nama}")
                .setTitle("Konfirmasi Hapus").create().show()
        }
    }

    override fun getItemCount(): Int {
        return listStudent.size
    }

}