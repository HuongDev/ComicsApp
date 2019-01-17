package vn.com.huong.comicskotlin.category

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.google.gson.Gson
import vn.com.huong.comicskotlin.R
import vn.com.huong.comicskotlin.model.Category

/**
 * Created by HuongPN on 01/17/2019.
 */
class MultipleChooseAdapter(internal var context: Context,
                            internal var categoriesList: List<Category>) : RecyclerView.Adapter<MultipleChooseAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_checkbox, p0, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, p1: Int) {
        holder.ckbOptions.text = categoriesList[p1].name
        holder.ckbOptions.isChecked = itemStateArray.get(p1)
    }

    private val itemStateArray = SparseBooleanArray()

    internal var selectedCategory : MutableList<Category> = ArrayList()

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        internal var ckbOptions: CheckBox

        init{
            ckbOptions = itemView.findViewById(R.id.ckbText) as CheckBox
            ckbOptions.setOnCheckedChangeListener{ _, b ->
                itemStateArray.put(adapterPosition, b)

                // b is boolean isChecked
                if (b)
                    selectedCategory.add(categoriesList[adapterPosition])
                else
                    selectedCategory.remove(categoriesList[adapterPosition])

            }
        }
    }

    val filterArray: String
    get(){
        val idSelected = ArrayList<Int>()
        for (category in selectedCategory)
            idSelected.add(category.id)
        return Gson().toJson(idSelected)
    }
}