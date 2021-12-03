package divyansh.tech.saveomovie.common

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import divyansh.tech.saveomovie.utils.C.IMAGE_BASE_URL

/*
* Load Images into imageViews
* */
@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view.context).load(IMAGE_BASE_URL + url).into(view)
}
