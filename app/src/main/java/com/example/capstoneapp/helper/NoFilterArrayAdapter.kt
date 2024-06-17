import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter

class NoFilterArrayAdapter(context: Context, resource: Int, private val items: Array<String>) :
    ArrayAdapter<String>(context, resource, items) {

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                filterResults.values = items
                filterResults.count = items.size
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                notifyDataSetChanged()
            }
        }
    }
}
