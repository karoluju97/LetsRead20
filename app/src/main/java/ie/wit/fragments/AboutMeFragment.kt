package ie.wit.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ie.wit.R


class AboutMeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        activity?.title = getString(R.string.aboutme_title)
        return inflater.inflate(R.layout.fragment_about_me, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AboutMeFragment().apply {
                arguments = Bundle().apply { }
            }
    }
}