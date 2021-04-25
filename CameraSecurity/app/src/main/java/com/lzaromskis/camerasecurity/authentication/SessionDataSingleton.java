package com.lzaromskis.camerasecurity.authentication;

import com.lzaromskis.camerasecurity.utility.SharedPrefs;

public class SessionDataSingleton {

    private static class LazyHolder {
        static final SessionDataSingleton INSTANCE = new SessionDataSingleton();
    }

    private SessionDataSingleton _instance;

    private String _hostname;
    private String _secret;
    private boolean _isValid;

    private SessionDataSingleton() {
        _hostname = SharedPrefs.readString(SharedPrefs.HOSTNAME);
        _secret = SharedPrefs.readString(SharedPrefs.SECRET);
        _isValid = SharedPrefs.readBoolean(SharedPrefs.IS_SECRET_VALID);
    }

    public static SessionDataSingleton getInstance() {
        return LazyHolder.INSTANCE;
    }

    public String getHostname() {
        return _hostname;
    }

    public void setHostname(String hostname) {
        _hostname = hostname;
        SharedPrefs.writeString(SharedPrefs.HOSTNAME, hostname);
    }

    public String getSecret() {
        return _secret;
    }

    public void setSecret(String secret) {
        _secret = secret;
        SharedPrefs.writeString(SharedPrefs.SECRET, secret);
    }

    public boolean isValid() {
        return _isValid;
    }

    public void setValidity(boolean valid) {
        _isValid = valid;
        SharedPrefs.writeBoolean(SharedPrefs.IS_SECRET_VALID, valid);
    }

}
