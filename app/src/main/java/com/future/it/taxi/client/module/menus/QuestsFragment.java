package com.future.it.taxi.client.module.menus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.future.it.taxi.client.App;
import com.future.it.taxi.client.R;
import com.future.it.taxi.client.module.registration.BankDetailFragment;
import com.future.it.taxi.client.net.APIServices;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by monir.sobuj on 11/9/17.
 */

public class QuestsFragment extends Fragment {

    @Inject
    APIServices apiServices;
    @Inject
    Realm r;


    public QuestsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //initiate dependencies
        App.getComponent().inject(this);
        View rootView = inflater.inflate(R.layout.fragment_menu_quests, container, false);
        setHasOptionsMenu(false);
        getActivity().setTitle("Awards");
        ButterKnife.bind(this, rootView);
        return rootView;
    }

   /* @OnClick(R.id.btnNext)
    public void onClick() {
        Fragment fragment = new BankDetailFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack("bank_detai").commit();
    }*/
}
