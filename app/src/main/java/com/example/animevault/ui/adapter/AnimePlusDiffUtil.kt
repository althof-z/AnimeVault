import androidx.recyclerview.widget.DiffUtil
import com.example.domain.model.Anime
import com.example.domain.model.AnimeHome

class AnimePlusDiffUtil : DiffUtil.ItemCallback<Anime>() {

    override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean {
        return oldItem.title == newItem.title
    }
}