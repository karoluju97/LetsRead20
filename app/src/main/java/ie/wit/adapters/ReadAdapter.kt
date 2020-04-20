package ie.wit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.R
import ie.wit.models.ReadModel
import kotlinx.android.synthetic.main.card_read.view.*
import kotlinx.android.synthetic.main.content_read.view.*

class DonationAdapter constructor(private var reads: List<ReadModel>)
    : RecyclerView.Adapter<DonationAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.card_read,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val read01 = reads[holder.adapterPosition]
        holder.bind(read01)
    }

    override fun getItemCount(): Int = reads.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(Read: ReadModel) {
            itemView.bookTitle.text = Read.booktitle
            itemView.bookAuthor.text = Read.author
            itemView.genre.text = Read.genre
            itemView.yearReleased.text = Read.year_released.toString()
            itemView.summary01.text = Read.summary
            itemView.imageView.setImageResource(R.mipmap.ic_launcher_round)
        }
    }
}