package vn.com.huong.comicskotlin.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer
import dmax.dialog.SpotsDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_view_detail.*
import vn.com.huong.comicskotlin.R
import vn.com.huong.comicskotlin.Retrofit.IComicAPI
import vn.com.huong.comicskotlin.common.Common

class ViewDetailActivity : AppCompatActivity() {

    internal var compositeDisposable = CompositeDisposable()
    internal lateinit var iComicAPI: IComicAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_detail)

        iComicAPI = Common.api

        vBack.setOnClickListener {
            if (Common.chapterIndex == 0) {
                Toast.makeText(this@ViewDetailActivity, "You are reading first chapter", Toast.LENGTH_SHORT).show()
            } else {
                Common.chapterIndex!!.dec()
                fetchImageLink(Common.chapterList[Common.chapterIndex!!].id)
            }
        }

        vNext.setOnClickListener {
            if (Common.chapterIndex == Common.chapterList.size - 1) {
                Toast.makeText(this@ViewDetailActivity, "You are reading last chapter", Toast.LENGTH_SHORT).show()
            } else {
                Common.chapterIndex!!.inc()
                fetchImageLink(Common.chapterList[Common.chapterIndex!!].id)
            }
        }

        // Load default
        fetchImageLink(Common.selectedChapter!!.id)
    }

    private fun fetchImageLink(id: Int) {
        val dialog = SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Please wait ...")
                .build()

        dialog.show()

        compositeDisposable.add(iComicAPI.getLinkList(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { linkList ->
                            val adapter = MySliderAdapter(this@ViewDetailActivity, linkList)
                            viewpager.visibility = View.VISIBLE
                            viewpager.adapter = adapter

                            tvDetail.text = Common.formatString(Common.selectedChapter!!.name)

                            // Animation
                            val bookFlipPageTransformer = BookFlipPageTransformer()
                            bookFlipPageTransformer.scaleAmountPercent = 10f

                            viewpager.setPageTransformer(true, bookFlipPageTransformer)

                            dialog.dismiss()
                        },
                        { error ->
                            Toast.makeText(this@ViewDetailActivity, "Error: " + error.message, Toast.LENGTH_SHORT).show()

                            dialog.dismiss()
                        }))
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }
}
