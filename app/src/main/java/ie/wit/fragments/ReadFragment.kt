package ie.wit.fragments


import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.ValueEventListener

import ie.wit.R
import ie.wit.main.ReadApp
import ie.wit.models.ReadModel
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import ie.wit.utils.showLoader
import kotlinx.android.synthetic.main.fragment_read.*
import kotlinx.android.synthetic.main.fragment_read.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class ReadFragment : Fragment(), AnkoLogger {

    lateinit var app: ReadApp
    lateinit var loader : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as ReadApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_read, container, false)
        loader = createLoader(activity!!)
        activity?.title = getString(R.string.action_read)

        setButtonListener(root)
        return root;
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ReadFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    fun setButtonListener( layout: View) {
        layout.post_button.setOnClickListener {
            val bookName = book_name.text.toString()
            val author = author.text.toString()
            val genre = Genre.text.toString()
            val yearReleased = year_released.text.toString()
            val summary = summary.text.toString()

            if (bookName.isEmpty()){
                activity?.toast("Please enter a book title.")
            } else if (author.isEmpty()){
                activity?.toast("please enter an authors name.")
            } else if (genre.isEmpty()) {
                activity?.toast("Please enter a genre.")
            } else if (yearReleased.isEmpty()){
                activity?.toast("Please the year the book was released.")
            } else if (summary.isEmpty()){
                activity?.toast("Please enter why you enjoyed the book.")
            } else {
                writeNewRead (
                    ReadModel (
                        booktitle = bookName,
                        author = author,
                        genre = genre,
                        year_released = yearReleased,
                        summary = summary,
                        profilepic = app.userImage.toString(),
                        email = app.currentUser?.email
                    )
                )
                activity?.toast("You have made a post.")
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if(app.currentUser.uid != null)
            app.database.child("user-reads")
                .child(app.currentUser!!.uid)
    }


    fun writeNewRead(read: ReadModel) {
        // Create new read at /reads & /reads/$uid
        showLoader(loader, "Adding Lets Read to Firebase")
        info("Firebase DB Reference : $app.database")
        val uid = app.currentUser!!.uid
        val key = app.database.child("reads").push().key
        if (key == null) {
            info("Firebase Error : Key Empty")
            return
        }
        read.uid = key
        val readValues = read.toMap()

        val childUpdates = HashMap<String, Any>()
        childUpdates["/reads/$key"] = readValues
        childUpdates["/user-reads/$uid/$key"] = readValues

        app.database.updateChildren(childUpdates)
        hideLoader(loader)
    }

}

