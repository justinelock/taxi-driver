package com.future.it.taxi.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.future.it.taxi.client.net.APIServices;
import com.future.it.taxi.client.utils.ConnectionUtils;
import com.future.it.taxi.client.utils.LoadingDialog;
import com.future.it.taxi.client.utils.SharedPrefsUtils;
import com.future.it.taxi.client.utils.StringConstants;
import com.future.it.taxi.client.utils.StringUtils;
import com.future.it.taxi.client.utils.ToastUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class LoginActivity extends AppCompatActivity {

    private String TAG = "LoginActivity";

    private LoginActivity activity = this;

    @BindView(R.id.vInfoTV)
    TextView vInfoTV;

    @BindView(R.id.connectionInfoTV)
    TextView connectionInfoTV;

    @BindView(R.id.buttonLogin)
    Button buttonLogin;

    @BindView(R.id.editTextUserId)
    TextInputEditText editTextUserId;

    @BindView(R.id.editTextPassword)
    TextInputEditText editTextPassword;

    @BindView(R.id.checkBoxRememberMe)
    CheckBox checkBoxRememberMe;

    public String REMMEMBER_USER_ID ="rem_userid";
    public String REMMEMBER_PASSWORD ="rem_password";

    @Inject
    Realm r;

    private CompositeDisposable mCompositeDisposable;
    public LoadingDialog loadingDialog;// = LoadingDialog.newInstance(this, "Please Wait...");

    @Inject
    APIServices apiServices;


    public static void start(Activity context){
        Intent intent = new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        App.getComponent().inject(this);

        //`exportRealmDatabase();

        initialize();
    }

    public void exportRealmDatabase(){
        try {
            final File file = new File(Environment.getExternalStorageDirectory().getPath().concat("/sample.realm"));
            if (file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }

            r.writeCopyTo(file);
            Toast.makeText(LoginActivity.this, "Success export realm file", Toast.LENGTH_SHORT).show();
        } catch (io.realm.internal.IOException e) {
            r.close();
            e.printStackTrace();
        }
    }

    public void initialize(){
     ButterKnife.bind(this);

        hideKeyboard(activity);

        mCompositeDisposable = new CompositeDisposable();


        //Remember me
       String rememberedUserId = SharedPrefsUtils.getStringPreference(activity,REMMEMBER_USER_ID);
       String rememberedPassword = SharedPrefsUtils.getStringPreference(activity,REMMEMBER_PASSWORD);

       if (rememberedUserId!=null && rememberedPassword!=null){
           checkBoxRememberMe.setChecked(true);
           editTextUserId.setText(rememberedUserId);
           editTextPassword.setText(rememberedPassword);
       }else{
           checkBoxRememberMe.setChecked(false);
           editTextUserId.setText("");
           editTextPassword.setText("");
       }

        if (ConnectionUtils.isNetworkConnected(activity)){
            connectionInfoTV.setVisibility(View.GONE);
        }else{
            connectionInfoTV.setVisibility(View.VISIBLE);
        }

        String vInfo = getString(R.string.app_name)+" V:"+ StringUtils.getVersionName(activity)+" "+getString(R.string.sil);
        vInfoTV.setText(vInfo);


    }

    @OnClick(R.id.buttonLogin)
    void loginButtonClicked(){

        String userId = editTextUserId.getText().toString();
        String password = editTextPassword.getText().toString();


        /*if (TextUtils.isEmpty(userId)){
            editTextUserId.setError(StringConstants.USER_ID_REQUIRED_MSG);
            return;
        }

        if (TextUtils.isEmpty(password)){
            editTextPassword.setError(StringConstants.PASSWORD_REQUIERD_MSG);
            return;
        }


        if (ConnectionUtils.isNetworkConnected(activity)){
            handleLogin(userId,password);
        }else{
            ToastUtils.shortToast(getString(R.string.check_internet));
        }*/


        handleLogin(userId,password);
    }

    @OnClick(R.id.buttonRegister)
    void registerButtonClicked(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        //finish();

    }

    public void handleLogin(final String userId, final String password){
        buttonLogin.setText(getString(R.string.please_wait));
        buttonLogin.setEnabled(false);

        String deviceToken = SharedPrefsUtils.getStringPreference(activity,SharedPrefsUtils.FCM_TOKEN);

        /*MyLog.show(TAG,"HandleLogin- device token:"+deviceToken);

        loadingDialog.show();

        mCompositeDisposable.add(apiServices.login(userId,password,deviceToken)
                .subscribeOn(Schedulers.io())  // Run on a background thread
                .observeOn(AndroidSchedulers.mainThread()) // Be notified on the main thread
                .subscribeWith(new DisposableObserver<LoginResponse>() {
                    @Override
                    public void onComplete() {
                       Log.e(TAG, "OnComplete login");
                        buttonLogin.setText(getString(R.string.login));
                        buttonLogin.setEnabled(true);
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError login: "+e.toString());
                        ToastUtils.shortToast(StringConstants.INTERNET_CONNECTION_ERROR);
                        loadingDialog.dismiss();
                        buttonLogin.setText(getString(R.string.login));
                        buttonLogin.setEnabled(true);
                    }

                    @Override
                    public void onNext(LoginResponse value) {
                        if (value.getStatus()!=null && value.getStatus().equalsIgnoreCase(StringConstants.SERVICE_RESPONSE_STATUS)) {
                            ToastUtils.shortToast(StringConstants.LOGIN_SUCCESS_MSG);
                            goToDashboard(userId,password,value);
                        }else{
                            ToastUtils.shortToast(StringConstants.LOGIN_FAIL_MSG);
                        }
                    }
                }));*/

        goToDashboard(userId,password);
    }

    public void goToDashboard(final String userId, final String password){

        r.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm r) {
                /*final UserModel userModel= new UserModel();

                userModel.setUserId(value.getMPGroup());//need to change later
                userModel.setPassword(password);
                userModel.setMPGroup(value.getMPGroup());
                userModel.setLoginID(userId);
                userModel.setMarket(value.getMarket());
                userModel.setName(value.getName());
                userModel.setIsTourPartialAllow(value.getIsTourPartialAllow());


                r.insertOrUpdate(userModel);*/

               if (checkBoxRememberMe.isChecked()){
                   SharedPrefsUtils.setStringPreference(activity,REMMEMBER_USER_ID,userId);
                   SharedPrefsUtils.setStringPreference(activity,REMMEMBER_PASSWORD,password);
               }else{
                   SharedPrefsUtils.setStringPreference(activity,REMMEMBER_USER_ID,null);
                   SharedPrefsUtils.setStringPreference(activity,REMMEMBER_PASSWORD,null);
               }
                SharedPrefsUtils.setBooleanPreference(activity, "isSyncNeed",true);

                //Go to Main activity
                //HomeActivityOld.start(activity, true);
                //MapsActivity.start(activity, true);
            }
        });

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
        finish();

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadingDialog = LoadingDialog.newInstance(this, "Please wait...");
        if (ConnectionUtils.isNetworkConnected(activity)){
            connectionInfoTV.setVisibility(View.GONE);
        }else{
            connectionInfoTV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable!=null){
            mCompositeDisposable.clear();
        }
    }
}
