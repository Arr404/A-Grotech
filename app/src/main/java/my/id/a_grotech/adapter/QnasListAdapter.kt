package my.id.a_grotech.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import my.id.a_grotech.DetailActivity
import my.id.a_grotech.network.QnasResponse
import my.id.a_grotech.databinding.ItemQnasBinding
import com.bumptech.glide.Glide
import my.id.a_grotech.network.QnasResponseItem

class QnasListAdapter :
    PagingDataAdapter<QnasResponseItem, QnasListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemQnasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)

        if (data != null) {
            holder.bind(data)
        }
    }

    class MyViewHolder(private val binding: ItemQnasBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: QnasResponseItem) {
            //binding.tvItemUserName.text = data.createdBy?.name ?: "username"
            binding.tvItemQnas.text = data.tittle
            binding.tvItemAuthor.text = data.message
            //Glide.with(binding.root).load(data.createdBy?.picture).into(binding.ivProfileIcon)
            binding.bSearch.setOnClickListener {
                val intent = Intent(it.context, DetailActivity::class.java)
                intent.putExtra("username", data.id)
                it.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<QnasResponseItem>() {
            override fun areItemsTheSame(oldItem: QnasResponseItem, newItem: QnasResponseItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: QnasResponseItem, newItem: QnasResponseItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}