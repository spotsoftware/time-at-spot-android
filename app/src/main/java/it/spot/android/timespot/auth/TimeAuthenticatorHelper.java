package it.spot.android.timespot.auth;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import java.io.IOException;

import it.spot.android.timespot.R;
import it.spot.android.timespot.domain.User;

/**
 * TODO - review persisted name and id... maybe it would be better to store as user data the entire model?
 *
 * @author a.rinaldi
 */
public class TimeAuthenticatorHelper {

    private static final String AUTH_TOKEN_TYPE = "auth_time_token";

    public static Account addAccount(Context context, User user) {

        AccountManager accountManager = AccountManager.get(context);
        Account account = new Account(user.getEmail(), context.getString(R.string.account_type));

        Bundle accountData = new Bundle();
        accountData.putParcelable(Constants.USER_DATA_ID, user);

        if (accountManager.addAccountExplicitly(account, "password", accountData)) {
            accountManager.setUserData(account, Constants.USER_DATA_ID, user.get_id());
            getAuthPreferences(context).edit().putString(Constants.PREFERENCES_ACCOUNT_NAME, account.name).commit();
            return account;

        } else {
            return null;
        }
    }

    public static String getToken(Context context, Account account) {
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        try {
            return accountManager.blockingGetAuthToken(account, AUTH_TOKEN_TYPE, true);

        } catch (OperationCanceledException e) {
            return null;

        } catch (IOException e) {
            return null;

        } catch (AuthenticatorException e) {
            return null;
        }
    }

    public static void updateToken(Context context, Account account, String token) {
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        accountManager.setAuthToken(account, AUTH_TOKEN_TYPE, token);
    }

    public static Account getAccount(Context context) {
        String accountName = getAuthPreferences(context).getString(Constants.PREFERENCES_ACCOUNT_NAME, null);

        if (accountName == null) {
            return null;
        }

        return getAccount(context, accountName);
    }

    public static Account getAccount(Context context, String name) {
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
            Account[] accounts = accountManager.getAccountsByType(context.getString(R.string.account_type));

            for (Account account : accounts) {
                if (account.name.equals(name)) {
                    return account;
                }
            }
        }

        return null;
    }

    public static String getUserId(Context context, Account account) {
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        return accountManager.getUserData(account, Constants.USER_DATA_ID);
    }

    public static boolean removeAccount(Context context, Account account) {
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                getAuthPreferences(context).edit().remove(Constants.PREFERENCES_ACCOUNT_NAME).commit();
                return accountManager.removeAccount(account, null, null, null).getResult().getBoolean(AccountManager.KEY_BOOLEAN_RESULT);

            } else {
                getAuthPreferences(context).edit().remove(Constants.PREFERENCES_ACCOUNT_NAME).commit();
                return accountManager.removeAccount(account, null, null).getResult().booleanValue();
            }

        } catch (OperationCanceledException e) {
            return false;

        } catch (IOException e) {
            return false;

        } catch (AuthenticatorException e) {
            return false;
        }
    }

    private static SharedPreferences getAuthPreferences(Context context) {
        return context.getSharedPreferences(Constants.PREFERENCES_ACCOUNT, Context.MODE_PRIVATE);
    }

    private static class Constants {
        public static final String PREFERENCES_ACCOUNT = "it.spot.android.timespot.auth";
        public static final String PREFERENCES_ACCOUNT_NAME = "preferences_account_name";

        public static final String USER_DATA_ID = "user_data_id";
    }
}
