package com.example.koza

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OglasAdapter(
    private var lista: List<Oglas>,
    private val isMyAdsScreen: Boolean = false,
    private val onDeleteKlik: ((Oglas) -> Unit)? = null,
    private val onKlik: (Oglas) -> Unit
) : RecyclerView.Adapter<OglasAdapter.OglasViewHolder>() {

    inner class OglasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivThumbnail: ImageView = itemView.findViewById(R.id.iv_thumbnail)
        val tvNaziv: TextView = itemView.findViewById(R.id.tv_naziv)
        val tvVelicinaStanje: TextView = itemView.findViewById(R.id.tv_velicina_stanje)
        val tvCijena: TextView = itemView.findViewById(R.id.tv_cijena)
        val tvWishlistCount: TextView = itemView.findViewById(R.id.tv_wishlist_count)
        val ivHeart: ImageView = itemView.findViewById(R.id.iv_heart_grid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OglasViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_oglas_grid, parent, false)
        return OglasViewHolder(view)
    }

    override fun onBindViewHolder(holder: OglasViewHolder, position: Int) {
        val oglas = lista[position]

        holder.tvNaziv.text = oglas.naziv
        holder.tvCijena.text = "${oglas.cijena} €"
        
        if (isMyAdsScreen) {
            holder.tvWishlistCount.visibility = View.GONE
            holder.ivHeart.visibility = View.GONE
        } else {
            holder.ivHeart.visibility = View.VISIBLE
            holder.tvWishlistCount.visibility = View.VISIBLE
            holder.tvWishlistCount.text = oglas.wishlistCount.toString()
            holder.ivHeart.setImageResource(R.drawable.ic_heart_filled)
            if (oglas.isFavorite) {
                holder.ivHeart.setColorFilter(android.graphics.Color.parseColor("#E53935"))
            } else {
                holder.ivHeart.setColorFilter(android.graphics.Color.WHITE)
            }
        }

        val velicinaStanje = buildString {
            if (oglas.velicina.isNotEmpty()) append(oglas.velicina)
            if (oglas.velicina.isNotEmpty() && oglas.stanje.isNotEmpty()) append(" · ")
            if (oglas.stanje.isNotEmpty()) append(oglas.stanje)
        }

        if (velicinaStanje.isNotEmpty()) {
            holder.tvVelicinaStanje.text = velicinaStanje
            holder.tvVelicinaStanje.visibility = View.VISIBLE
        } else {
            holder.tvVelicinaStanje.visibility = View.GONE
        }

        if (oglas.slikaUrl.isNotEmpty()) {
            val context = holder.itemView.context
            val resourceId = context.resources.getIdentifier(oglas.slikaUrl, "drawable", context.packageName)
            if (resourceId != 0) {
                holder.ivThumbnail.setImageResource(resourceId)
            } else {
                holder.ivThumbnail.setImageResource(R.drawable.koza404)
            }
        } else {
            holder.ivThumbnail.setImageResource(R.drawable.koza404)
        }

        holder.itemView.setOnClickListener { onKlik(oglas) }
    }

    override fun getItemCount() = lista.size

    fun azurirajListu(novaLista: List<Oglas>) {
        lista = novaLista
        notifyDataSetChanged()
    }
}
