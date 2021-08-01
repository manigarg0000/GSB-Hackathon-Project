package com.mani.wordwizard
//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
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val requestCodeForAddWordActivity = 1
    private val viewModel : WordViewModel by viewModels{
        WordViewModelFactory((application as WordApplication).repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fab = findViewById<FloatingActionButton>(R.id.fab_addNew)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = WordListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.getAllWords.observe(this){
            words->
                words.let{
                    adapter.submitList(it)

                 }
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val word = adapter.currentList[viewHolder.adapterPosition]
                viewModel.onSwipe(word)
            }
        }).attachToRecyclerView(recyclerView)



        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddWordActivity::class.java)
            startActivityForResult(intent, requestCodeForAddWordActivity)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == requestCodeForAddWordActivity && resultCode == RESULT_OK){
           data?.getStringExtra(AddWordActivity.EXTRA_REPLY)?.let{
               val word = Word(it)
               viewModel.insert(word)
           }
        }else{
            Toast.makeText(this, "Empty field cannot be added", Toast.LENGTH_LONG).show()
        }
    }


    companion object {
        val PREFIX_FORSEARCH = "https://www.google.com/search?q="
    }

}