package org.wing.wapp.navigation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.avos.avoscloud.AVStatus;
import com.avos.avoscloud.AVUser;
import org.wing.wapp.R;
import org.wing.wapp.StatusNetAsyncTask;
import org.wing.wapp.StatusService;
import org.wing.wapp.StatusUtils;
import org.wing.wapp.ui.StatusListAdapter;
import org.wing.wapp.ui.base.BaseListView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liao on 2016/9/22.
 * 动弹页面
 */


public class StatusFragment extends Fragment {
    BaseListView<org.wing.wapp.Status> statusList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.status_list,null);
        statusList = (BaseListView<org.wing.wapp.Status>)view.findViewById(R.id.status_List);
        initList();
        statusList.setToastIfEmpty(false);
        //statusList.onRefresh();
        return view;
    }


    private void initList() {
        statusList.init(new BaseListView.DataInterface<org.wing.wapp.Status>() {
            @Override
            public List<org.wing.wapp.Status> getDatas(int skip, int limit, List<org.wing.wapp.Status> currentDatas) throws Exception {
                long maxId;
                maxId = getMaxId(skip, currentDatas);
                if (maxId == 0) {
                    return new ArrayList<>();
                } else {
                    return StatusService.getStatusDatas(maxId, limit);
                }
            }

            @Override
            public void onItemLongPressed(final org.wing.wapp.Status item) {
                AVStatus innerStatus = item.getInnerStatus();
                AVUser source = innerStatus.getSource();
                if (source.getObjectId().equals(AVUser.getCurrentUser().getObjectId())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage(R.string.status_deleteStatusTips).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new StatusNetAsyncTask(getContext()) {
                                @Override
                                protected void doInBack() throws Exception {
                                    StatusService.deleteStatus(item);
                                }

                                @Override
                                protected void onPost(Exception e) {
                                    if (e != null) {
                                        StatusUtils.toast(getContext(), e.getMessage());
                                    } else {
                                        statusList.onRefresh();
                                    }
                                }
                            }.execute();
                        }
                    }).setNegativeButton(R.string.cancel, null);
                    builder.show();
                }
            }
        }, new StatusListAdapter(getContext()));
    }


    public static long getMaxId(int skip, List<org.wing.wapp.Status> currentDatas) {
        long maxId;
        if (skip == 0) {
            maxId = Long.MAX_VALUE;
        } else {
            AVStatus lastStatus = currentDatas.get(currentDatas.size() - 1).getInnerStatus();
            maxId = lastStatus.getMessageId() - 1;
        }
        return maxId;
    }


}
