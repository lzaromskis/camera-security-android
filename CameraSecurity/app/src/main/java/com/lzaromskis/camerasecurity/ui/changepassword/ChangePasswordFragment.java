package com.lzaromskis.camerasecurity.ui.changepassword;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lzaromskis.camerasecurity.R;
import com.lzaromskis.camerasecurity.communication.requests.ChangePasswordRequest;
import com.lzaromskis.camerasecurity.communication.requests.IRequest;
import com.lzaromskis.camerasecurity.communication.requests.asynctasks.ChangePasswordRequestAsyncTask;
import com.lzaromskis.camerasecurity.utility.SharedPrefs;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePasswordFragment extends Fragment {

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    public static ChangePasswordFragment newInstance(String param1, String param2) {
        return new ChangePasswordFragment();
    }

    private EditText _currentPassword;
    private EditText _newPassword;
    private EditText _newPasswordRepeat;
    private Button _changePasswordButton;
    private TextView _errorText;
    private boolean _passwordsAreEqual;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_change_password, container, false);

        _errorText = root.findViewById(R.id.chpw_text);

        if (!SharedPrefs.readBoolean(SharedPrefs.IS_SECRET_VALID)) {
            _errorText.setTextColor(Color.RED);
            _errorText.setText(R.string.not_authenticated_chpw);
            _errorText.invalidate();
        }

        _currentPassword = root.findViewById(R.id.chpw_current_password);
        _newPassword = root.findViewById(R.id.chpw_new_password);
        _newPasswordRepeat = root.findViewById(R.id.chpw_new_password_repeat);
        _changePasswordButton = root.findViewById(R.id.chpw_change_password);


        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateViewStates();
            }
        };

        _changePasswordButton.setOnClickListener((v) -> {
            IRequest request = new ChangePasswordRequest(_currentPassword.getText().toString(), _newPassword.getText().toString());
            new ChangePasswordRequestAsyncTask().execute(this, root, request, _errorText, _currentPassword, _newPassword, _newPasswordRepeat, _changePasswordButton);
        });

        _currentPassword.addTextChangedListener(watcher);
        _newPassword.addTextChangedListener(watcher);
        _newPasswordRepeat.addTextChangedListener(watcher);

        return root;
    }

    private void setEditTextError(EditText editText) {
        int len = editText.getText().length();
        if (len < 4) {
            editText.setError(getString(R.string.invalid_password));
        } else {
            editText.setError(null);
        }
    }

    private void updateViewStates() {
        passwordsAreEqual();
        setChangePasswordButtonEnabled();
        setEditTextError(_currentPassword);
        setEditTextError(_newPassword);
        setEditTextError(_newPasswordRepeat);
    }

    private void passwordsAreEqual() {
        _passwordsAreEqual = _newPassword.getText().toString().equals(_newPasswordRepeat.getText().toString());
        if (_passwordsAreEqual) {
            _errorText.setText("");
        } else {
            _errorText.setText(R.string.passwords_do_not_match);
            _errorText.setTextColor(Color.RED);
        }
        _errorText.invalidate();
    }

    private void setChangePasswordButtonEnabled() {
        if (_changePasswordButton != null)
            _changePasswordButton.setEnabled(_passwordsAreEqual
                && _currentPassword.getText().toString().length() >= 4
                && _newPassword.getText().toString().length() >= 4
                && _newPasswordRepeat.getText().toString().length() >= 4);
    }
}