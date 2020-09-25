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

import adapter.NoticeAdapter;

/**
 * Created by ACER on 05-4-17.
 */

public class NoticeFragment extends Fragment {
    Activity act;
    public NoticeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.content_notice, container, false);
        act =getActivity();
        // Inflate the layout for this fragment


        ListView listview = (ListView)rootView.findViewById(R.id.listview);
        NoticeAdapter adapter = new NoticeAdapter(act, new JSONArray());
        listview.setAdapter(adapter);




        return  rootView;
    }
}
