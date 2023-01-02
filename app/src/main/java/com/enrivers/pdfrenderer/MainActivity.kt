package com.enrivers.pdfrenderer

import android.app.NotificationManager
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog


class MainActivity : AppCompatActivity() {

    private var linkTo = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //showPopUpDialog()

        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(230)

        val webView = findViewById<WebView>(R.id.webView)
        webView.loadUrl("https://docs.google.com/gview?embedded=true&url=$linkTo")
        webView.settings.javaScriptEnabled = true

        val progressDialog = ProgressDialog(this)
        webView.settings.useWideViewPort = true
        webView.settings.loadWithOverviewMode = true
        webView.setInitialScale(100)
        webView.settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
        webView.settings.setNeedInitialFocus(true)
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        webView.settings.pluginState = WebSettings.PluginState.ON

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress == 100) {
                    progressDialog.setMessage("Please wait")
                    progressDialog.setCancelable(true)
                    progressDialog.setCanceledOnTouchOutside(false)
                    progressDialog.cancel()
                } else {
                    progressDialog.show()
                }
            }
        }


    }


    private fun showPopUpDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.success_bottom_sheet_layout, null)
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }


}