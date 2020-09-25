package fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.education.R;
import com.education.ResultListActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapter.ResultsAdapter;
import util.CommonClass;
import util.JSONReader;

/**
 * Created by ACER on 10-4-17.
 */

public class ResultFragment extends Fragment {
    Activity act;
    CommonClass common;
    JSONReader j_reader;
    JSONArray objResultArray;
    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_result, container, false);
        act =getActivity();
        // Inflate the layout for this fragment




        common = new CommonClass(act);
        j_reader = new JSONReader(act);

        ListView listView = (ListView)rootView.findViewById(R.id.listView2);
        final ResultsAdapter adapter = new ResultsAdapter(act, new JSONArray());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JSONObject obj = adapter.getItem(position);
                try {
                    //new getResultTask().execute(obj.getString("exam_id"));
                    Intent intent = new Intent(act, ResultListActivity.class);
                    intent.putExtra("exam_id",obj.getString("exam_id"));
                    intent.putExtra("exam",obj.toString());
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });




        return  rootView;
    }
}
