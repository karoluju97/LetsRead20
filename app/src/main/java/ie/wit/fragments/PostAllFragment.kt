package ie.wit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import ie.wit.R
import ie.wit.adapters.ReadAdapter
import ie.wit.adapters.ReadListener
import ie.wit.models.ReadModel

import ie.wit.utils.*
import kotlinx.android.synthetic.main.fragment_post.view.*
import org.jetbrains.anko.info

class PostAllFragment : PostFragment(),
    ReadListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_post, container, false)
        activity?.title = getString(R.string.menu_post_all)

        root.recyclerView.setLayoutManager(LinearLayoutManager(activity))

        var query = FirebaseDatabase.getInstance()
            .reference.child("reads")

        var options = FirebaseRecyclerOptions.Builder<ReadModel>()
            .setQuery(query, ReadModel::class.java)
            .setLifecycleOwner(this)
            .build()

        root.recyclerView.adapter = ReadAdapter(options, this)

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PostAllFragment().apply {
                arguments = Bundle().apply { }
            }
    }
}