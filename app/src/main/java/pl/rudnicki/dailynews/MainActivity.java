package pl.rudnicki.dailynews;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pl.rudnicki.dailynews.ui.sources.SourcesFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadSourcesFragment();
    }

    private void loadSourcesFragment() {
        Fragment sourcesFragment = new SourcesFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout_main_container, sourcesFragment);
        fragmentTransaction.commit();
    }
}
