package ie.wit.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.R
import ie.wit.main.ReadApp
import kotlinx.android.synthetic.main.activity_post.*

class Post : AppCompatActivity() {

    lateinit var app: ReadApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        app = this.application as ReadApp

        recyclerView.setLayoutManager(LinearLayoutManager(this))
        recyclerView.adapter = ReadAdapter(app.donationsStore.findAll())
        //recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_post, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_read -> { startActivity(Intent(this, Read::class.java))
                true }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
