package pl.rudnicki.dailynews.ui.sources;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import pl.rudnicki.dailynews.MainActivity;
import pl.rudnicki.dailynews.R;
import pl.rudnicki.dailynews.data.model.SourcesResponse;
import pl.rudnicki.dailynews.data.remote.NewsService;

/**
 * Created by Szymon on 14.05.2017.
 */

public class SourcesFragment extends Fragment {

    @BindView(R.id.recycler_view_sources)
    RecyclerView sourcesRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sources, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.app_name);
        setupRecyclerView();
        getSourcesData();
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        sourcesRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(sourcesRecyclerView.getContext(), LinearLayout.VERTICAL);
        sourcesRecyclerView.addItemDecoration(dividerItemDecoration);
        sourcesRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void getSourcesData() {
        NewsService.Creator.newInstance().getAllSources()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError);
    }

    private void handleResponse(SourcesResponse response) {
        SourcesAdapter sourcesAdapter = new SourcesAdapter(response.sources);
        sourcesRecyclerView.setAdapter(sourcesAdapter);
    }

    private void handleError(Throwable error) {
        Toast.makeText(getActivity(), getString(R.string.error) + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }
}
