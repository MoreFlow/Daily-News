package pl.rudnicki.dailynews.ui.articles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import pl.rudnicki.dailynews.MainActivity;
import pl.rudnicki.dailynews.R;
import pl.rudnicki.dailynews.data.model.ArticlesResponse;
import pl.rudnicki.dailynews.data.remote.NewsService;

/**
 * Created by Szymon on 04.06.2017.
 */

public class ArticlesFragment extends Fragment {

    @BindView(R.id.recycler_view_articles)
    RecyclerView articlesRecyclerView;

    private String sourceId;
    private String sourceName;
    private String sourceCategory;
    private String sourceDescription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        sourceId = getArguments().getString("id");
        sourceName = getArguments().getString("name");
        sourceCategory = getArguments().getString("category");
        sourceDescription = getArguments().getString("description");

        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(sourceName);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        articlesRecyclerView.setLayoutManager(layoutManager);
        articlesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getArticlesData();
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.articles, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                ((MainActivity) getContext()).showInfoDialog(getContext(), sourceName, sourceCategory, sourceDescription);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void getArticlesData() {
        NewsService.Creator.newInstance().getArticlesFromSource(sourceId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError);
    }

    private void handleResponse(ArticlesResponse response) {
        ArticlesAdapter articlesAdapter = new ArticlesAdapter(response.articles);
        articlesRecyclerView.setAdapter(articlesAdapter);
    }

    private void handleError(Throwable error) {
        Toast.makeText(getActivity(), "Error: " + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

}
