package pl.rudnicki.dailynews.ui.sources;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.rudnicki.dailynews.R;
import pl.rudnicki.dailynews.data.model.Source;

/**
 * Created by Szymon on 14.05.2017.
 */

public class SourcesAdapter extends RecyclerView.Adapter<SourcesAdapter.ViewHolder> {

    private List<Source> sourcesList;
    private int[] colorsArray;

    public SourcesAdapter(Context context, List<Source> sourcesList) {
        this.sourcesList = sourcesList;
        colorsArray = context.getResources().getIntArray(R.array.rainbow);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_source, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Source source = sourcesList.get(position);
        holder.name.setText(source.name);
        holder.category.setText(source.category);
        holder.description.setText(source.description);
        holder.url.setText(source.url);
        holder.country.setText(source.country.toUpperCase());
        holder.itemView.setBackgroundColor(colorsArray[position % colorsArray.length]);
        holder.itemView.setOnClickListener(v -> holder.additional.toggle());
    }

    @Override
    public int getItemCount() {
        return sourcesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.category)
        TextView category;

        @BindView(R.id.description)
        TextView description;

        @BindView(R.id.url)
        TextView url;

        @BindView(R.id.country)
        TextView country;

        @BindView(R.id.additional)
        ExpandableLayout additional;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
