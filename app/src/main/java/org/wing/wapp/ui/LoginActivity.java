package org.wing.wapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;
import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SaveCallback;
import org.wing.wapp.MainActivity;
import org.wing.wapp.R;
import org.wing.wapp.ui.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/*import com.avos.avoscloud.PushDemo.R;*/

public class LoginActivity extends BaseActivity {
  @InjectView(R.id.usernameEditText)
  EditText usernameEditText;
  @InjectView(R.id.passwordEditText)
  EditText passwordEditText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login_activity);
    ButterKnife.inject(this);
    AVAnalytics.trackAppOpened(getIntent());

    usernameEditText.setText("");
    passwordEditText.setText("");

    if (AVUser.getCurrentUser() != null) {
      onSucceed();
    }
  }

  void onSucceed() {
    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
    startActivity(intent);
    finish();
  }

  @OnClick(R.id.login)
  void login() {
    String username = usernameEditText.getText().toString();
    String password = passwordEditText.getText().toString();
    if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
      AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
        @Override
        public void done(AVUser avUser, AVException e) {
          if (filterException(e)) {
            onSucceed();
          }
        }
      });
    }
  }

  @OnClick(R.id.register)
  void register() {
    String username = usernameEditText.getText().toString();
    String password = passwordEditText.getText().toString();
    if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
      AVUser user = new AVUser();
      user.setUsername(username);
      user.setPassword(password);
      user.saveInBackground(new SaveCallback() {
        @Override
        public void done(AVException e) {
          if (filterException(e)) {
            onSucceed();
          }
        }
      });
    }
  }

  protected void toast(String s) {
    Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
  }
  protected boolean filterException(Exception e) {
    if (e != null) {
      toast(e.getMessage());
      return false;
    } else {
      return true;
    }
  }
}