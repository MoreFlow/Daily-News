package pl.rudnicki.dailynews.ui.articles;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.rudnicki.dailynews.R;
import pl.rudnicki.dailynews.data.model.Article;

/**
 * Created by Szymon on 04.06.2017.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    private List<Article> articlesList;

    public ArticlesAdapter(List<Article> articlesList) {
        this.articlesList = articlesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = articlesList.get(position);
        holder.title.setText(article.title);

        if (article.author == null) {
            holder.author.setVisibility(View.GONE);
        } else {
            holder.author.setText(article.author.replaceAll("\n\n", "\n"));
        }
        holder.description.setText(article.description);

        Picasso.with(holder.itemView.getContext())
                .load(article.urlToImage)
                .into(holder.image);

        holder.readButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.url));
            holder.itemView.getContext().startActivity(intent);
        });

        holder.shareButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, article.url);
            holder.itemView.getContext().startActivity(
                    Intent.createChooser(
                            intent,
                            holder.itemView.getContext().getString(R.string.share)));
        });
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.author)
        TextView author;

        @BindView(R.id.image)
        ImageView image;

        @BindView(R.id.description)
        TextView description;

        @BindView(R.id.read)
        Button readButton;

        @BindView(R.id.share)
        Button shareButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
