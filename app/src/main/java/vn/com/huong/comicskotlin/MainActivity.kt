package vn.com.huong.comicskotlin

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import dmax.dialog.SpotsDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import ss.com.bannerslider.Slider
import vn.com.huong.comicskotlin.Retrofit.IComicAPI
import vn.com.huong.comicskotlin.common.Common
import vn.com.huong.comicskotlin.common.GridItemDecoration
import vn.com.huong.comicskotlin.service.PicassoImageLoadingService

class MainActivity : AppCompatActivity() {

    private val PERMISSIONS_REQUEST_ACCESS_NETWORK_STATE = 123
    internal var compositeDisposable = CompositeDisposable()
    internal lateinit var iComicAPI: IComicAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Init API
        iComicAPI = Common.api

        Slider.init(PicassoImageLoadingService(this))

        //init RecyclerView
        rcvComic.setHasFixedSize(true)
        rcvComic.layoutManager = GridLayoutManager(this, 2)
        rcvComic.addItemDecoration(GridItemDecoration(50))

        swipeRefresh.setColorSchemeResources( R.color.colorPrimary, android.R.color.holo_orange_dark, android.R.color.background_dark)
        swipeRefresh.setOnRefreshListener {
            reloadBannerAndComic()
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
        } else {
            swipeRefresh.post { reloadBannerAndComic() }
        }
    }

    private fun fetchBanner() {
        compositeDisposable.add(iComicAPI.getBannerList
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    banners -> banner_slider.setAdapter(MainSliderAdapter(banners))
                },
                        {
                            error -> Toast.makeText(baseContext, "Error: " + error, Toast.LENGTH_SHORT).show()
                        }))
    }

    private fun fetchComic() {
        val dialog = SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Please wait ...")
                .build()

        if (!swipeRefresh.isRefreshing){
            dialog.show()

        }
        compositeDisposable.add(iComicAPI.getComicList
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    comicList -> tvNewComic.text = StringBuilder("NEW COMIC (")
                            .append(comicList.size)
                        .append(")")
                    rcvComic.adapter = MyComicAdapter(baseContext, comicList)
                    dismissDialog(dialog)
                },
                        {
                            error -> Toast.makeText(baseContext, "Error: " + error.message, Toast.LENGTH_SHORT).show()
                            dismissDialog(dialog)
                        }))
    }

    private fun reloadBannerAndComic() {
        if (Common.isConnectedToInternet(baseContext)) {
            fetchBanner()
            fetchComic()
        }else{
            Toast.makeText(baseContext, "Please check your connection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun dismissDialog(dialog: Dialog) {
        if (!swipeRefresh.isRefreshing)
            dialog.dismiss()
        swipeRefresh.isRefreshing = false
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_NETWORK_STATE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    reloadBannerAndComic()
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(baseContext, "Please grant permission to app work", Toast.LENGTH_SHORT).show()
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }


}
