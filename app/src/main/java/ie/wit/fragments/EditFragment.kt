package ie.wit.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

import ie.wit.R
import ie.wit.main.ReadApp
import ie.wit.models.ReadModel
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import ie.wit.utils.showLoader
import kotlinx.android.synthetic.main.fragment_edit.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class EditFragment : Fragment(), AnkoLogger {

    lateinit var app: ReadApp
    lateinit var loader : AlertDialog
    lateinit var root: View
    var editRead: ReadModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as ReadApp

        arguments?.let {
            editRead = it.getParcelable("editRead")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_edit, container, false)
        activity?.title = getString(R.string.action_edit)
        loader = createLoader(activity!!)

        root.editBook_name.setText(editRead!!.booktitle)
        root.editAuthor.setText(editRead!!.author)
        root.editGenre.setText(editRead!!.genre)
        root.editYear_released.setText(editRead!!.year_released)
        root.editSummary.setText(editRead!!.summary)

        root.editUpdateButton.setOnClickListener {
            showLoader(loader, "Updating Lets Read on Server...")
            updateReadData()
            updateRead(editRead!!.uid, editRead!!)
            updateUserRead(app.currentUser!!.uid,
                            editRead!!.uid, editRead!!)
        }
        return root
    }


    companion object {
        @JvmStatic
        fun newInstance(read: ReadModel) =
            EditFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("editRead",read)
                }
            }
    }

    fun updateReadData(){
        editRead!!.booktitle= root.editBook_name.text.toString()
        editRead!!.author = root.editAuthor.text.toString()
        editRead!!.genre = root.editGenre.text.toString()
        editRead!!.year_released = root.editYear_released.text.toString()
        editRead!!.summary = root.editSummary.text.toString()
    }

    fun updateUserRead(userId: String, uid: String?, read: ReadModel) {
        app.database.child("user-reads").child(userId).child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.setValue(read)
                        activity!!.supportFragmentManager.beginTransaction()
                            .replace(R.id.homeFrame, PostFragment.newInstance())
                            .addToBackStack(null)
                            .commit()
                        hideLoader(loader)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Lets Read error : ${error.message}")
                    }
                })
    }

    fun updateRead(uid: String?, read: ReadModel) {
        app.database.child("reads").child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.setValue(read)
                        hideLoader(loader)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Lets Read error : ${error.message}")
                    }
                })
    }
}