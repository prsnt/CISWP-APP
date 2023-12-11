package com.project.elt.ui.browser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.elt.R
import com.project.elt.databinding.ActivityBrowserBinding
import com.project.elt.ui.products.fragments.ProductsFragment
import com.project.elt.utils.TextUtils.Companion.CONTACT_US_URL
import com.project.elt.utils.TextUtils.Companion.PRIVACY_URL

class BrowserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBrowserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrowserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.activity = this
        getIntentData()
    }

    private fun getIntentData()
    {
        binding.webview.settings.javaScriptEnabled = true;
        if (intent.hasExtra("browserInt")) {
            if(intent.getIntExtra("browserInt",0) == 1)
            {
                binding.tvTitle.text = getString(R.string.contact_us)
                binding.webview.loadUrl(CONTACT_US_URL)
            }else if(intent.getIntExtra("browserInt",0) == 2)
            {
                binding.tvTitle.text = getString(R.string.privacy_policy)
                binding.webview.loadUrl(PRIVACY_URL)
            }
        }
    }
}