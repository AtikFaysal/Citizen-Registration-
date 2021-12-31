package com.citizen.registration

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.citizen.registration.core.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("NonConstantResourceId")
class MainActivity : BaseActivity() {

    @BindView(R.id.tv_toolbar_title) lateinit var tvTitle: TextView
    @BindView(R.id.toolbar) lateinit var rlToolbar: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
    }

    override fun init() {}

    override fun setToolbarTitle(title: String) {
        setToolbarTitle(title, tvTitle)
    }

    /**
     * ...hide toolbar
     */
    override fun hideToolbar() {
        rlToolbar.visibility = View.GONE
    }

    /**
     * ...show toolbar
     */
    override fun showToolbar() {
        rlToolbar.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }
}