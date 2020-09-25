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

import adapter.ExamAdapter;

/**
 * Created by ACER on 10-4-17.
 */

public class ExamFragment extends Fragment {
    Activity act;
    public ExamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_exam, container, false);
        act =getActivity();
        // Inflate the layout for this fragment



        ListView listView = (ListView)rootView.findViewById(R.id.listView);
        ExamAdapter adapter = new ExamAdapter(act, new JSONArray());
        listView.setAdapter(adapter);




        return  rootView;
    }
}
