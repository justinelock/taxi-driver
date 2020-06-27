package com.future.it.taxi.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.future.it.taxi.client.module.menus.InfoAndHelpFragment;
import com.future.it.taxi.client.module.menus.JobsFragment;
import com.future.it.taxi.client.module.menus.LegalFragment;
import com.future.it.taxi.client.module.menus.PrivacyFragment;
import com.future.it.taxi.client.module.menus.ProfileFragment;
import com.future.it.taxi.client.module.menus.QuestsFragment;
import com.future.it.taxi.client.module.menus.SettingsFragment;
import com.future.it.taxi.client.module.menus.StatisticsFragment;
import com.future.it.taxi.client.module.registration.RegisterAsFragment;
import com.future.it.taxi.client.net.APIServices;
import com.future.it.taxi.client.utils.LoadingDialog;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.realm.Realm;

public class MenusHostActivity extends AppCompatActivity {

    private String TAG = "LoginActivity";

    private MenusHostActivity activity = this;
    static String fragmentName;


    @Inject
    Realm r;

    private CompositeDisposable mCompositeDisposable;
    public LoadingDialog loadingDialog;// = LoadingDialog.newInstance(this, "Please Wait...");

    @Inject
    APIServices apiServices;


    public static void start(Activity context, String tittle){
        fragmentName = tittle;
        Intent intent = new Intent(context, MenusHostActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus_host);
        App.getComponent().inject(this);
        initialize();
    }


    public void initialize(){
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCompositeDisposable = new CompositeDisposable();
        Fragment fragment;
        switch (fragmentName){
            case "Jobs":
                fragment = new JobsFragment();
                break;
            case "Quests":
                fragment = new QuestsFragment();
                break;
            case "Statistics":
                fragment = new StatisticsFragment();
                break;
            case "Profile":
                fragment = new ProfileFragment();
                break;
            case "Info":
                fragment = new InfoAndHelpFragment();
                break;
            case "Settings":
                fragment = new SettingsFragment();
                break;
            case "Legal":
                fragment = new LegalFragment();
                break;
            case "Privacy":
                fragment = new PrivacyFragment();
                break;
                default:
                    fragment = new ProfileFragment();
                    break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack("menus").commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }
}
