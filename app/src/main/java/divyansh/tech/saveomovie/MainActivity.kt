package divyansh.tech.saveomovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}