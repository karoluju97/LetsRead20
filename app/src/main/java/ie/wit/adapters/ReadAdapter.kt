package ie.wit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.squareup.picasso.Picasso
import ie.wit.R
import ie.wit.fragments.PostAllFragment
import ie.wit.models.ReadModel
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.card_read.view.*
import kotlinx.android.synthetic.main.fragment_read.view.*


interface ReadListener {
    fun onReadClick(read: ReadModel)
}

class ReadAdapter(options: FirebaseRecyclerOptions<ReadModel>,
                  private val listener: ReadListener?)
    : FirebaseRecyclerAdapter<ReadModel,
        ReadAdapter.DonationViewHolder>(options) {

    class DonationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(read: ReadModel, listener: ReadListener) {
            with(read) {
                itemView.tag = read
                itemView.bookTitle.text = read.booktitle
                itemView.bookAuthor.text = read.author
                itemView.genre.text = read.genre
                itemView.yearReleased.text = read.year_released
                itemView.summary01.text = read.summary

                if(listener is PostAllFragment)
                    ; // Do Nothing, Don't Allow 'Clickable' Rows
                else
                    itemView.setOnClickListener { listener.onReadClick(read) }

                if(!read.profilepic.isEmpty()) {
                    Picasso.get().load(read.profilepic.toUri())
                        //.resize(180, 180)
                        .transform(CropCircleTransformation())
                        .into(itemView.imageView)
                }
                else
                    itemView.imageView.setImageResource(R.mipmap.ic_launcher_read_round)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationViewHolder {

        return DonationViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_read, parent, false))
    }

    override fun onBindViewHolder(holder: DonationViewHolder, position: Int, model: ReadModel) {
        holder.bind(model,listener!!)
    }

    override fun onDataChanged() {
        // Called each time there is a new data snapshot. You may want to use this method
        // to hide a loading spinner or check for the "no documents" state and update your UI.
        // ...
    }
}