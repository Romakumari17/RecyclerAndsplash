package com.romakumari.recyclerandsplash
import android.app.AsyncNotedAppOp
import android.app.Dialog
import android.icu.text.Transliterator.Position
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.renderscript.ScriptGroup.Binding
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.romakumari.recyclerandsplash.databinding.ActivityMainBinding
import com.romakumari.recyclerandsplash.databinding.ActivitySplashScreenBinding
import com.romakumari.recyclerandsplash.databinding.CustomlayoutBinding
import com.romakumari.recyclerandsplash.databinding.ItemlayoutBinding

class MainActivity : AppCompatActivity(), listInterface {
    var list = arrayListOf<NotesData>()
    lateinit var adapter: RecycleViewAdapter
    lateinit var binding: ActivityMainBinding
    lateinit var notesDb: NotesDb
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = RecycleViewAdapter(list, this)
        binding.recylcer.layoutManager = GridLayoutManager(this, 4)
        binding.recylcer.adapter = adapter
        notesDb= NotesDb.getDatabase(this)

        binding.fab.setOnClickListener {it: View? ->
            var dialog = Dialog(this)
            var dialogBinding = CustomlayoutBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
            dialog.getWindow()?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialogBinding.BtnUpdate.setOnClickListener {
                if (dialogBinding.title.text.toString().isNullOrEmpty()) {
                    dialogBinding.title.error = ("Enter Your Title")
                } else if (dialogBinding.description.text.toString().isNullOrEmpty()) {
                    dialogBinding.description.error = "enter Your description "
                } else {
                  /*  list.add(
                        NotesData(
                         title=   dialogBinding.etName.text.toString(),
                            description =  dialogBinding.description.text.toString()
                        )
                    )*/

                    class insert:AsyncTask<Void,Void,Void>(){
                     override fun doInBackground(vararg p0: Void?): Void? {
                         notesDb.notesdao()
                             .insertNotes(NotesData(
                                 title=   dialogBinding.title.text.toString(),
                                 description =  dialogBinding.description.text.toString()
                             ))

                         return null
                     }

                        override fun onPostExecute(result: Void?) {
                            super.onPostExecute(result)
                            getNotes()
                        }
                 }
                    insert().execute()

                    dialog.dismiss()
                    adapter.notifyDataSetChanged()
                }

            }
            dialog.show()
        }
        getNotes()
    }


    fun getNotes(){
        list.clear()
        class getnotes:AsyncTask<Void,Void,Void,>(){
            override fun doInBackground(vararg p0: Void?): Void? {
                list.addAll(notesDb.notesdao().getNotes())
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                adapter.notifyDataSetChanged()
            }
        }
        getnotes().execute()
    }
    override fun onDeleteClick(student: NotesData, position: Int) {
           //list.removeAt(position)
        class delete:AsyncTask<Void,Void,Void>(){
               override fun doInBackground(vararg p0: Void?): Void? {
                notesDb.notesdao().deleteNotes(list[position])
                return null
               }

               override fun onPostExecute(result: Void?) {
                   super.onPostExecute(result)
                   getNotes()
               }
           }
        delete().execute()
        adapter.notifyDataSetChanged()
    }

    override fun onUpdateClick(student: NotesData, position: Int) {
        var dialog = Dialog(this)

        var dialogBinding = CustomlayoutBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
       // dialogBinding.title.findViewById<EditText>(androidx.recyclerview.R.id.title)
        dialog.getWindow()?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
         dialogBinding.BtnUpdate.setText("Update")
        dialogBinding.BtnUpdate.setOnClickListener {
            if (dialogBinding.title.text.toString().isNullOrEmpty()) {
                dialogBinding.title.error = ("Enter Your Title")
            } else if
                           (dialogBinding.description.text.toString().isNullOrEmpty()) {
                dialogBinding.description.error = "enter Your description "
            } else {

                list.set(position,NotesData(
                    title=dialogBinding.title.text.toString(),
                    description=  dialogBinding.description.text.toString()

                    )
                )
                class update:AsyncTask<Void,Void,Void>(){
                    override fun doInBackground(vararg p0: Void?): Void? {
                        notesDb.notesdao().updateNotes(NotesData(id=list[position].id,title=dialogBinding.title.text.toString(),
                            description=  dialogBinding.description.text.toString()))

                        return null
                    }

                    override fun onPostExecute(result: Void?) {
                        super.onPostExecute(result)
                        getNotes()
                    }

                }
                update().execute()
               dialog.dismiss()
                adapter.notifyDataSetChanged()
            }
        }
        dialog.show()
    }
}
