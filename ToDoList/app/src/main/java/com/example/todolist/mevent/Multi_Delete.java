package com.example.todolist.mevent;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.todolist.R;
import com.example.todolist.mactivity.MainActivity;
import com.example.todolist.madapter.ListViewTaskAdapter;

public class Multi_Delete implements AbsListView.MultiChoiceModeListener {

    private final ListView lvTask;
    private final ListViewTaskAdapter listViewTaskAdapter;
    private final MainActivity mainActivity;

    public Multi_Delete(MainActivity mainActivity, ListView lvTask, ListViewTaskAdapter listViewTaskAdapter) {
        this.lvTask = lvTask;
        this.listViewTaskAdapter = listViewTaskAdapter;
        this.mainActivity = mainActivity;
    }

    public void showMyDialog(final ActionMode mode, String setTitle, String setMessage, int setIcon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setIcon(setIcon);
        builder.setTitle(setTitle);
        builder.setMessage(setMessage);

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listViewTaskAdapter.actionItemClicked();
                mode.finish();
                Toast.makeText(mainActivity, "Success", Toast.LENGTH_LONG).show();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setCancelable(false);

        builder.create().show();
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        int checkCount = lvTask.getCheckedItemCount();
        mode.setTitle(checkCount + " selected");
        listViewTaskAdapter.checkedStateChanged(position);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.menu_multi_delete, menu);
        mainActivity.getSupportActionBar().hide();
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_multi_delete:
                String setTitle = "Do you want to delete???";
                String setMessage = "Delete!!!";
                int setIcon = R.drawable.garbage_24;
                showMyDialog(mode, setTitle, setMessage, setIcon);
                break;
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mainActivity.getSupportActionBar().show();
        listViewTaskAdapter.destroyActionMode();
    }
}