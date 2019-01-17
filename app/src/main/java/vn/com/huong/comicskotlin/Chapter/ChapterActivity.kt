package vn.com.huong.comicskotlin.Chapter

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import dmax.dialog.SpotsDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_chapter.*
import vn.com.huong.comicskotlin.R
import vn.com.huong.comicskotlin.Retrofit.IComicAPI
import vn.com.huong.comicskotlin.common.Common

class ChapterActivity : AppCompatActivity() {

    internal var compositeDisposable = CompositeDisposable()
    internal lateinit var iComicAPI: IComicAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter)

        iComicAPI = Common.api


        toolbar.title = Common.selectedComic?.name
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_24dp)
        toolbar.setNavigationOnClickListener { finish() }

        rcvChapter.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        rcvChapter.layoutManager = layoutManager
        rcvChapter.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))

        // Set data
        fetchChapter(Common.selectedComic!!.id)
    }

    private fun fetchChapter(id: Int) {
        val dialog = SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Please wait ...")
                .build()

        dialog.show()

        compositeDisposable.add(iComicAPI.getChapterList(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    chapterList -> tvChapterComic.text = StringBuilder("NEW CHAPTER (")
                        .append(chapterList.size)
                        .append(")")

                    rcvChapter.adapter = ChapterAdapter(this@ChapterActivity, chapterList)

                    // Save for prev - next navigation
                    Common.chapterList = chapterList

                    dismissDialog(dialog)
                },
                        {
                            error -> Toast.makeText(this@ChapterActivity, "Error: " + error.message, Toast.LENGTH_SHORT).show()
                            dismissDialog(dialog)
                        }))
    }

    private fun dismissDialog(dialog: Dialog) {
        dialog.dismiss()
    }

    override fun onStop() {
        compositeDisposable.clear()

        super.onStop()
    }
}
