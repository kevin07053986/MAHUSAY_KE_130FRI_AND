package com.mab.menu_application

import CustomDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.mab.menu_application.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)  // Set the toolbar as the ActionBar
        supportActionBar?.title = ""
        supportActionBar?.subtitle = ""
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        // Set title color to black for all menu items
        for (i in 0 until menu.size()) {
            val menuItem = menu.getItem(i)
            val spanString = SpannableString(menuItem.title)
            spanString.setSpan(ForegroundColorSpan(Color.BLACK), 0, spanString.length, 0)
            menuItem.title = spanString

            // Apply the same to the submenu items (if any)
            val subMenu = menuItem.subMenu
            if (subMenu != null) {  // Check for null
                for (j in 0 until subMenu.size()) {
                    val subMenuItem = subMenu.getItem(j)
                    val subSpanString = SpannableString(subMenuItem.title)
                    subSpanString.setSpan(ForegroundColorSpan(Color.BLACK), 0, subSpanString.length, 0)
                    subMenuItem.title = subSpanString
                }
            }
        }
        // Enable icons in the overflow menu
        if (menu.javaClass.simpleName.equals("MenuBuilder", ignoreCase = true)) {
            try {
                val method = menu.javaClass.getDeclaredMethod("setOptionalIconsVisible", Boolean::class.javaPrimitiveType)
                method.isAccessible = true
                method.invoke(menu, true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_navigate -> {
                // Navigate to another fragment
                replaceFragment(SampleFragment())
                true
            }
            R.id.menu_dialog -> {
                // Show a DialogFragment
                val dialog = CustomDialog()
                dialog.show(supportFragmentManager, "CustomDialogFragment")
                true
            }
            R.id.menu_exit -> {
                // Exit the app
                finishAffinity()  // Finish the app
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Function to replace fragments
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
