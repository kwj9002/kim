package com.example.testimagepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.testimagepage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var selectedFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            setFragment(ImageSearchFragment())
        } else {
            selectedFragment = supportFragmentManager.findFragmentById(R.id.frameLayout)
        }

        binding.apply {
            fragment1Btn.setOnClickListener {
                selectedFragment = ImageSearchFragment()
                setFragment(selectedFragment as ImageSearchFragment)
            }
            fragment2Btn.setOnClickListener {
                selectedFragment = MyLockerFragment()
                setFragment(selectedFragment as MyLockerFragment)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        selectedFragment?.let {
            outState.putString("SELECTED_FRAGMENT_TAG", it::class.java.simpleName)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val selectedFragmentTag = savedInstanceState.getString("SELECTED_FRAGMENT_TAG")
        selectedFragment =
            supportFragmentManager.findFragmentByTag(selectedFragmentTag) ?: selectedFragment
    }

    private fun setFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment, fragment::class.java.simpleName)
        transaction.setReorderingAllowed(true)

        if (!fragment.isStateSaved) {
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        } else {
            super.onBackPressed()
        }
    }
}