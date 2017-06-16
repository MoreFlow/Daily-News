package pl.rudnicki.dailynews.ui.sources;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.rudnicki.dailynews.MainActivity;
import pl.rudnicki.dailynews.R;
import pl.rudnicki.dailynews.data.model.Source;
import pl.rudnicki.dailynews.ui.articles.ArticlesFragment;

/**
 * Created by Szymon on 14.05.2017.
 */

public class SourcesAdapter extends RecyclerView.Adapter<SourcesAdapter.ViewHolder> {

    private List<Source> sourcesList;

    public SourcesAdapter(List<Source> sourcesList) {
        this.sourcesList = sourcesList;
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
        holder.country.setBackground(
                ResourcesCompat.getDrawable(
                        holder.itemView.getResources(),
                        chooseBackground(source.country),
                        null));
        holder.itemView.setOnClickListener(v -> loadArticlesFragment(source, holder));
        holder.itemView.setOnLongClickListener(v -> {
            ((MainActivity) v.getContext()).showInfoDialog(v.getContext(), source.name, source.category, source.description);
            return true;
        });
    }


    private void loadArticlesFragment(Source source, ViewHolder holder) {
        ArticlesFragment fragment = new ArticlesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", source.id);
        bundle.putString("name", source.name);
        bundle.putString("category", source.category);
        bundle.putString("description", source.description);
        fragment.setArguments(bundle);
        ((MainActivity) holder.itemView.getContext()).loadArticlesFragment(fragment);
    }

    @Override
    public int getItemCount() {
        return sourcesList.size();
    }

    private int chooseBackground(String country) {
        switch (country) {
            case "us":
                return R.drawable.us;
            case "au":
                return R.drawable.au;
            case "gb":
                return R.drawable.gb;
            case "it":
                return R.drawable.it;
            case "de":
            default:
                return R.drawable.de;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.category)
        TextView category;

        @BindView(R.id.country)
        ImageView country;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
