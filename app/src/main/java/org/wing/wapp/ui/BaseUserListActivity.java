package org.wing.wapp.ui;

import android.app.Activity;
import android.os.Bundle;

import com.avos.avoscloud.AVUser;


import org.wing.wapp.Initialization;
import org.wing.wapp.R;
import org.wing.wapp.ui.base.BaseListView;
import org.wing.wapp.ui.base.UserAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lzw on 15/1/2.
 */
public abstract class BaseUserListActivity extends Activity {

  @InjectView(R.id.userList)
  protected BaseListView<AVUser> userList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.status_users_layout);
    ButterKnife.inject(this);
    initList();
  }

  protected void refresh() {
    userList.onRefresh();
  }

  private void initList() {
    userList.init(new BaseListView.DataInterface<AVUser>() {

      @Override
      public List<AVUser> getDatas(int skip, int limit, List<AVUser> currentDatas) throws Exception {
        List<AVUser> users = getUserList(skip, limit);
        Initialization.registerBatchUser(users);
        return users;
      }

      @Override
      public void onItemSelected(AVUser item) {
        PersonActivity.go(BaseUserListActivity.this, item);
      }
    }, new UserAdapter(this));
  }

  protected abstract List<AVUser> getUserList(int skip, int limit) throws Exception;
}
