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
    private var initialFragment: Fragment = ImageSearchFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            fragment1Btn.setOnClickListener {
                initialFragment = ImageSearchFragment()
                setFragment(initialFragment)
            }
            fragment2Btn.setOnClickListener {
                val myLockerFragment = MyLockerFragment()
                setFragment(myLockerFragment)

                myLockerFragment.onImagesReceived(selectedImagesList)
            }
        }

        if (savedInstanceState == null) {
            setFragment(initialFragment)
        }
    }
    private fun setFragment(frag: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.frameLayout, frag)
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

    fun onImageSelected(selectedImages: List<KakaoImage>) {
        selectedImagesList.clear()
        selectedImagesList.addAll(selectedImages)

        val myLockerFragment = supportFragmentManager.findFragmentByTag(MyLockerFragment::class.java.simpleName) as? MyLockerFragment
        myLockerFragment?.onImagesReceived(selectedImagesList)

        Log.d("MainActivity", "전송된 이미지 수: ${selectedImagesList.size}")
    }
}