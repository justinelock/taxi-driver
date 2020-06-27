package com.future.it.taxi.client.module.menus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.future.it.taxi.client.App;
import com.future.it.taxi.client.R;
import com.future.it.taxi.client.module.registration.BankDetailFragment;
import com.future.it.taxi.client.net.APIServices;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by monir.sobuj on 11/9/17.
 */

public class JobsFragment extends Fragment {

    @Inject
    APIServices apiServices;
    @Inject
    Realm r;
    @BindView(R.id.rvJob)
    RecyclerView rvJob;

    final FastItemAdapter<IJobModel> fastAdapter = new FastItemAdapter<>();
    List<IJobModel> jobList = new ArrayList<>();


    public JobsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //initiate dependencies
        App.getComponent().inject(this);
        View rootView = inflater.inflate(R.layout.fragment_menu_monthly_jobs, container, false);
        setHasOptionsMenu(false);
        getActivity().setTitle("Jobs");
        ButterKnife.bind(this, rootView);
        fastAdapter.add(getJobs());
        fastAdapter.withSelectable(true);
        fastAdapter.setHasStableIds(false);
        fastAdapter.withOnClickListener(new FastAdapter.OnClickListener<IJobModel>() {
            @Override
            public boolean onClick(View v, IAdapter<IJobModel> adapter, final IJobModel item, int position) {
                Fragment fragment = new JobDetaiFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack("job_detai").commit();
                return false;
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvJob.setLayoutManager(layoutManager);
        rvJob.setAdapter(fastAdapter);
        return rootView;
    }

    /*@OnClick(R.id.btnNext)
    public void onClick() {
        Fragment fragment = new BankDetailFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack("bank_detai").commit();
    }*/

    public List<IJobModel> getJobs(){
        List<IJobModel> jobModels = new ArrayList<>();
        IJobModel iJobModel1 = new IJobModel();
        iJobModel1.setTime("1:147 PM - 1:58 PM");
        iJobModel1.setPick("Woodhaven 5");
        iJobModel1.setDrop("Eyre Square 18");
        iJobModel1.setId(String.valueOf(5));

        IJobModel iJobModel2 = new IJobModel();
        iJobModel2.setTime("2:10 PM - 2:20 PM");
        iJobModel2.setPick("Eyre Square 18");
        iJobModel2.setDrop("Woodhaven 5");
        iJobModel2.setId(String.valueOf(6));
        jobModels.add(iJobModel1);
        jobModels.add(iJobModel2);
        return jobModels;
    }
}
