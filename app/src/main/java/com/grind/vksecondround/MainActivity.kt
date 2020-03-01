package com.grind.vksecondround

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.grind.vksecondround.fragments.DocsListFragment
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.utils.VKUtils


class MainActivity : AppCompatActivity() {
    var accessToken: VKAccessToken? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (savedInstanceState == null) {
            VK.login(this, listOf(VKScope.DOCS))
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VK.onActivityResult(requestCode, resultCode, data, object : VKAuthCallback {

                override fun onLogin(token: VKAccessToken) {
                    accessToken = token
                    App.userId = token.userId

                    supportFragmentManager.beginTransaction()
                        .add(R.id.main_container, DocsListFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commitAllowingStateLoss()
                }

                override fun onLoginFailed(errorCode: Int) {
                    Toast.makeText(this@MainActivity, "Auth error", Toast.LENGTH_SHORT).show()
                }

            })) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}
