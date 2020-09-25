package fragments;

import android.app.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.education.R;

import org.json.JSONArray;

import adapter.DailyAdapter;

/**
 * Created by ACER on 07-4-17.
 */

public class DailyfeedsFragment extends Fragment {

    Activity act;
    public DailyfeedsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_daily, container, false);
        act =getActivity();
        // Inflate the layout for this fragment



        ListView listview = (ListView)rootView.findViewById(R.id.listview);
        DailyAdapter adapter = new DailyAdapter(act, new JSONArray());
        listview.setAdapter(adapter);




        return  rootView;
    }
}
