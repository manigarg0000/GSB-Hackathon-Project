package com.mani.wordwizard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class WordListAdapter : ListAdapter<Word, WordListAdapter.WordViewHolder>(WordComparator()) {

    class WordViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val wordTV : TextView = itemView.findViewById(R.id.word_textView)
        fun bind(text : String?){
            wordTV.text = text
        }

        companion object{
         fun create(parent: ViewGroup) : WordViewHolder{
             val view : View = LayoutInflater.from(parent.context)
                 .inflate(R.layout.item_view, parent, false)

             return WordViewHolder(view)
         }
    }

}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
       return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
       val current = getItem(position)
        holder.bind(current.word)
    }

}

class WordComparator : DiffUtil.ItemCallback<Word>(){
    override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem===newItem
    }

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem.word == newItem.word
    }

}