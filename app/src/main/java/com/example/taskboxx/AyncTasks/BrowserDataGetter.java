package com.example.taskboxx.AyncTasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.taskboxx.Dashboard;

import java.util.List;

import me.everything.providers.android.browser.BrowserProvider;

import static com.example.taskboxx.LoginActivity.TAG;

/**
 * Created by Shubham Maheshwari on 18-04-2017.
 */

public class BrowserDataGetter extends AsyncTask<String,Boolean,Boolean>{

    ProgressDialog pd = new ProgressDialog(Dashboard.getContext1());

    @Override
    protected void onPreExecute(){
        pd.setMessage("Loading");
        pd.show();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        BrowserProvider browserProvider = new BrowserProvider(Dashboard.getContext1());
        List list = browserProvider.getSearches().getList();
        Log.d(TAG, "Browser Searches: " + list);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result){
        pd.dismiss();
    }

}
