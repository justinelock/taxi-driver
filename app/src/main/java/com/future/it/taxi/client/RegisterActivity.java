package com.future.it.taxi.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.future.it.taxi.client.module.registration.RegisterAsFragment;
import com.future.it.taxi.client.net.APIServices;
import com.future.it.taxi.client.utils.ConnectionUtils;
import com.future.it.taxi.client.utils.LoadingDialog;
import com.future.it.taxi.client.utils.SharedPrefsUtils;
import com.future.it.taxi.client.utils.StringConstants;
import com.future.it.taxi.client.utils.ToastUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import io.realm.Realm;

public class RegisterActivity extends AppCompatActivity {

    private String TAG = "LoginActivity";

    private RegisterActivity activity = this;


    @Inject
    Realm r;

    private CompositeDisposable mCompositeDisposable;
    public LoadingDialog loadingDialog;// = LoadingDialog.newInstance(this, "Please Wait...");

    @Inject
    APIServices apiServices;


    public static void start(Activity context){
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        App.getComponent().inject(this);
        initialize();
    }


    public void initialize(){
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCompositeDisposable = new CompositeDisposable();
        Fragment fragment = new RegisterAsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack("register_as").commit();
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
