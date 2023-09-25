import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem

open class BaseActivity : AppCompatActivity() {
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed() // Navigate back when the home (back) button is pressed
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}