package vn.com.huong.comicskotlin.category

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_category_filter.*
import kotlinx.android.synthetic.main.dialog_search.*
import vn.com.huong.comicskotlin.MyComicAdapter
import vn.com.huong.comicskotlin.R
import vn.com.huong.comicskotlin.Retrofit.IComicAPI
import vn.com.huong.comicskotlin.common.Common

class CategoryFilterActivity : AppCompatActivity() {

    internal var compositeDisposable = CompositeDisposable()
    internal lateinit var iComicAPI: IComicAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_filter)

        iComicAPI = Common.api

        Handler().post{ fetchCategory() }

        rcvFilter.setHasFixedSize(true)
        rcvFilter.layoutManager = GridLayoutManager(this, 2)

        btnFilter.setOnClickListener {
//            if (Common.categoriesList.isNotEmpty()) showFilterDialog()
            if (Common.categoriesList.size > 0) showFilterDialog()
        }

        btnSearch.setOnClickListener { showSearchDialog() }
    }

    private fun showSearchDialog() {
        val alertDialog = AlertDialog.Builder(this@CategoryFilterActivity)
        alertDialog.setTitle("Search Comic")

        val filterSearchLayout = this.layoutInflater.inflate(R.layout.dialog_search, null)

        val textSearch = filterSearchLayout.findViewById(R.id.edtSearch) as EditText

        alertDialog.setView(filterSearchLayout)

        alertDialog.setNegativeButton("CANCEL") { dialogInterface, _ -> dialogInterface.dismiss()}
        alertDialog.setPositiveButton("SEARCH") { dialogInterface, _ -> fetchSearchCategory(textSearch.text.toString())}
        alertDialog.show()
    }

    private fun fetchSearchCategory(text: String) {

        if (text.isEmpty()) edtSearch.error = "Input error"

        compositeDisposable.add(iComicAPI.getSearchedComicList(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    comicList ->
                    rcvFilter.visibility = View.VISIBLE
                    rcvFilter.adapter = MyComicAdapter(this@CategoryFilterActivity, comicList)
                },
                        {
                            error -> Toast.makeText(this@CategoryFilterActivity, "Error: $error", Toast.LENGTH_SHORT).show()
                            rcvFilter.visibility = View.INVISIBLE
                        }))
    }

    private fun showFilterDialog() {
        val alertDialog = AlertDialog.Builder(this@CategoryFilterActivity)
        alertDialog.setTitle("Select Category")

        val filterOptionLayout = this.layoutInflater.inflate(R.layout.dialog_filter, null)

        val rcvOptions = filterOptionLayout.findViewById(R.id.rcvOptions) as RecyclerView
        rcvOptions.setHasFixedSize(true)
        rcvOptions.layoutManager = LinearLayoutManager(this)
//        rcvOptions.addItemDecoration(GridItemDecoration(30))
        val adapter = MultipleChooseAdapter(this@CategoryFilterActivity, Common.categoriesList)
        rcvOptions.adapter = adapter

        alertDialog.setView(filterOptionLayout)

        alertDialog.setNegativeButton("CANCEL") { dialogInterface, _ -> dialogInterface.dismiss()}
        alertDialog.setPositiveButton("FILTER") { dialogInterface, _ -> fetchFilterCategory(adapter.filterArray)}

        alertDialog.show()
    }

    private fun fetchFilterCategory(filterArray: String) {
        compositeDisposable.add(iComicAPI.getFilterComicList(filterArray)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    comicList ->
                    rcvFilter.visibility = View.VISIBLE
                    rcvFilter.adapter = MyComicAdapter(this@CategoryFilterActivity, comicList)
                },
                        {
                            error -> Toast.makeText(this@CategoryFilterActivity, "Error: $error", Toast.LENGTH_SHORT).show()
                            rcvFilter.visibility = View.INVISIBLE
                        }))
    }

    private fun fetchCategory() {
        compositeDisposable.add(iComicAPI.getCategoryList
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    categories -> Common.categoriesList = categories
                },
                        {
                            error -> Toast.makeText(this@CategoryFilterActivity, "Error: $error", Toast.LENGTH_SHORT).show()
                        }))
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }
}
