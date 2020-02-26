package com.grind.vksecondround

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.grind.vksecondround.fragments.DocsListFragment
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import com.vk.sdk.util.VKUtil


class MainActivity : AppCompatActivity() {
    var accessToken: VKAccessToken? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val certificateFingerprint = VKUtil.getCertificateFingerprint(this, packageName)
        Log.e("FPrint", certificateFingerprint!![0])

        if(savedInstanceState == null){
            VKSdk.login(this, VKScope.DOCS)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken>{

                override fun onResult(token: VKAccessToken) {
                    accessToken = token

                    supportFragmentManager.beginTransaction()
                        .add(R.id.main_container, DocsListFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit()

                }

                override fun onError(error: VKError) {
                    Toast.makeText(this@MainActivity, "Auth error", Toast.LENGTH_SHORT).show()
                }

            })) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
