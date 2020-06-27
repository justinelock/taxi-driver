package com.future.it.taxi.client;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.future.it.taxi.client.listener.LogoutListener;
import com.future.it.taxi.client.utils.StringConstants;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class MenusActivity extends AppCompatActivity {

    private String TAG = "MenusActivity";

    private MenusActivity activity = this;

    static LogoutListener logoutListener;

    public static void start(Activity context, LogoutListener listener) {
        logoutListener = listener;
        Intent intent = new Intent(context, MenusActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus);
        ButterKnife.bind(this);
        App.getComponent().inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.cardJob, R.id.cardQuest, R.id.cardStat, R.id.cardProfile, R.id.cardInfo, R.id.cardSetting, R.id.cardLegal, R.id.cardPrivacy, R.id.cardLogout})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.cardJob:
                MenusHostActivity.start(this, "Jobs");
                break;
            case R.id.cardQuest:
                MenusHostActivity.start(this, "Quests");
                break;
            case R.id.cardStat:
                MenusHostActivity.start(this, "Statistics");
                break;
            case R.id.cardProfile:
                MenusHostActivity.start(this, "Profile");
                break;
            case R.id.cardInfo:
                MenusHostActivity.start(this, "Info");
                break;
            case R.id.cardSetting:
                MenusHostActivity.start(this, "Settings");
                break;
            case R.id.cardLegal:
                MenusHostActivity.start(this, "Legal");
                break;
            case R.id.cardPrivacy:
                MenusHostActivity.start(this, "Privacy");
                break;
            case R.id.cardLogout:
                displayLogoutConfirmationPopup();
                break;
        }
    }

    public void displayLogoutConfirmationPopup(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        // alert.setTitle(getString(R.string.logout));
        alert.setMessage(StringConstants.LOGOUT_CONF_MSG);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                finish();
                logoutListener.success();


            }
        });

        alert.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        //finish();
                    }
                });
        alert.show();
    }
}
