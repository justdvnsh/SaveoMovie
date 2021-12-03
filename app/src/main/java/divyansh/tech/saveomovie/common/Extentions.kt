package divyansh.tech.saveomovie.common

import android.view.View

fun View.animateView() = run {
    animate().translationX(0f).alpha(1.0f).duration = 300L
    visibility = View.VISIBLE
}