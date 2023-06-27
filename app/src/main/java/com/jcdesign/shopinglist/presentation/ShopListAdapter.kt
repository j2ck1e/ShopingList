package com.jcdesign.shopinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.jcdesign.shopinglist.R
import com.jcdesign.shopinglist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

//    var shopList = listOf<ShopItem>()
//        set(value) {
//            val callback = ShopListDiffCallback(shopList, value)
//            val diffResult = DiffUtil.calculateDiff(callback)
//            diffResult.dispatchUpdatesTo(this)
//            field = value
//        }
    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null
    var onTouchListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_ENABLE -> R.layout.item_shop_enabled
            VIEW_TYPE_DISABLE -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        viewHolder.view.setOnLongClickListener { true }
        viewHolder.tvName.text = shopItem.name
        viewHolder.tvCount.text = shopItem.count.toString()
        viewHolder.view.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        viewHolder.view.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
            true
        }

    }

//    override fun getItemCount(): Int {
//        return shopList.size
//    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enable) VIEW_TYPE_ENABLE else VIEW_TYPE_DISABLE
    }



    companion object {
        const val VIEW_TYPE_ENABLE = 0
        const val VIEW_TYPE_DISABLE = 1

        const val MAX_POOL_SIZE = 30

    }
}