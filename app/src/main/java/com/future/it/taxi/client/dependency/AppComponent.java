package com.future.it.taxi.client.dependency;


import com.future.it.taxi.client.App;
import com.future.it.taxi.client.LoginActivity;
import com.future.it.taxi.client.MapsActivity;
import com.future.it.taxi.client.MenusActivity;
import com.future.it.taxi.client.MenusHostActivity;
import com.future.it.taxi.client.RegisterActivity;
import com.future.it.taxi.client.module.menus.DriverLicenseFragment;
import com.future.it.taxi.client.module.menus.HelpFragment;
import com.future.it.taxi.client.module.menus.InfoAndHelpFragment;
import com.future.it.taxi.client.module.menus.JobDetaiFragment;
import com.future.it.taxi.client.module.menus.JobsFragment;
import com.future.it.taxi.client.module.menus.LegalFragment;
import com.future.it.taxi.client.module.menus.PrivacyFragment;
import com.future.it.taxi.client.module.menus.ProfileFragment;
import com.future.it.taxi.client.module.menus.QuestsFragment;
import com.future.it.taxi.client.module.menus.SettingsFragment;
import com.future.it.taxi.client.module.menus.StatisticsFragment;
import com.future.it.taxi.client.module.menus.VehicleLicenseFragment;
import com.future.it.taxi.client.module.registration.AddressFragment;
import com.future.it.taxi.client.module.registration.BankDetailFragment;
import com.future.it.taxi.client.module.registration.ContactFragment;
import com.future.it.taxi.client.module.registration.LicenseFragment;
import com.future.it.taxi.client.module.registration.PersonalFragment;
import com.future.it.taxi.client.module.registration.RegisterAsFragment;
import com.future.it.taxi.client.module.registration.VehicleFragment;
import com.future.it.taxi.client.net.RequestServices;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by monir.sobuj on 5/17/17.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    final class Initializer {
        private Initializer() {
        }

        public static AppComponent init(App app) {
            return DaggerAppComponent.builder().appModule(new AppModule(app)).build();
        }
    }

    void inject(MapsActivity mapsActivity);
    void inject(LoginActivity loginActivity);


    void inject(RegisterActivity registerActivity);
    void inject(RegisterAsFragment registerAsFragment);
    void inject(ContactFragment contactFragment);
    void inject(PersonalFragment personalFragment);
    void inject(AddressFragment addressFragment);
    void inject(BankDetailFragment bankDetailFragment);
    void inject(LicenseFragment licenseFragment);
    void inject(VehicleFragment vehicleFragment);


    void inject(MenusActivity menusActivity);
    void inject(MenusHostActivity menusHostActivity);
    void inject(DriverLicenseFragment driverLicenseFragment);
    void inject(HelpFragment helpFragment);
    void inject(InfoAndHelpFragment infoAndHelpFragment);
    void inject(JobDetaiFragment jobDetaiFragment);
    void inject(JobsFragment jobsFragment);
    void inject(LegalFragment legalFragment);
    void inject(PrivacyFragment privacyFragment);
    void inject(ProfileFragment profileFragment);
    void inject(QuestsFragment questsFragment);
    void inject(SettingsFragment settingsFragment);
    void inject(StatisticsFragment statisticsFragment);
    void inject(VehicleLicenseFragment vehicleLicenseFragment);


}
