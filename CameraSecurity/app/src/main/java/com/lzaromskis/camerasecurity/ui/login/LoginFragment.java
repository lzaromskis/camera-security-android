package com.lzaromskis.camerasecurity.ui.login;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lzaromskis.camerasecurity.R;
import com.lzaromskis.camerasecurity.communication.requests.IRequest;
import com.lzaromskis.camerasecurity.communication.requests.LoginRequest;
import com.lzaromskis.camerasecurity.communication.requests.asynctasks.LoginRequestAsyncTask;
import com.lzaromskis.camerasecurity.utility.SharedPrefs;

import org.w3c.dom.Text;

public class LoginFragment extends Fragment {

    private EditText _hostname;
    private EditText _password;
    private Button _loginButton;
    private ProgressBar _progressBar;
    private TextView _errorMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        _hostname = root.findViewById(R.id.server_hostname);
        _password = root.findViewById(R.id.password);
        _loginButton = root.findViewById(R.id.login);
        _progressBar = root.findViewById(R.id.loading);
        _errorMessage = root.findViewById(R.id.login_error_text);

        _hostname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setLoginButtonEnabled();
            }
        });

        _password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 4) {
                    _password.setError(getString(R.string.invalid_password));
                } else {
                    _password.setError(null);
                }

                setLoginButtonEnabled();
            }
        });

        _loginButton.setOnClickListener(v -> {
            _progressBar.setVisibility(View.VISIBLE);
            _errorMessage.setText("");
            _errorMessage.invalidate();
            String hostname = _hostname.getText().toString();
            String password = _password.getText().toString();

            SharedPrefs.writeString(SharedPrefs.HOSTNAME, hostname);
            IRequest request = new LoginRequest(password);
            new LoginRequestAsyncTask().execute(this, root, request, _errorMessage, _password, _progressBar);
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            String reason = getArguments().getString("redirect_reason");
            String hostname = getArguments().getString("hostname");
            _hostname.setText(hostname);
            _errorMessage.setTextColor(Color.RED);
            _errorMessage.setText(reason);
            _errorMessage.invalidate();
        }

        return root;
    }

    private void setLoginButtonEnabled() {
        _loginButton.setEnabled(_hostname.getText().toString().trim().length() != 0 && _password.getText().length() >= 4);
    }
}