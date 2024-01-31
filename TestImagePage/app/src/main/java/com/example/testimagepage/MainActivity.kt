package com.example.testimagepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.testimagepage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val selectedImagesList: MutableList<KakaoImage> = mutableListOf()
    private var selectedFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            setFragment(ImageSearchFragment())
        } else {
            selectedFragment =
                supportFragmentManager.findFragmentById(R.id.frameLayout)
        }

        binding.apply {
            fragment1Btn.setOnClickListener {
                selectedFragment = ImageSearchFragment()
                setFragment(selectedFragment!!)
            }
            fragment2Btn.setOnClickListener {
                selectedFragment = MyLockerFragment()
                setFragment(selectedFragment!!)
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
        supportFragmentManager.commit {
            replace(R.id.frameLayout, fragment, fragment::class.java.simpleName)
            setReorderingAllowed(true)
            addToBackStack(null)
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