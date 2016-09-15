package foodeliver.remote;

import android.os.AsyncTask;

import java.util.ArrayList;

import foodeliver.exception.ConnectionException;
import foodeliver.utility.DataSearch;


public class DBTask {
    DataSearch ds = null;
    String type = "result";

    public void getResult(ArrayList<String> sql, DataSearch ds, String type) {
        this.ds = ds;
        this.type = type;
        new GetResTask().execute(sql);
    }

    private class GetResTask extends AsyncTask<ArrayList<String>, Void, String> {
        protected String doInBackground(ArrayList<String>... sql) {
            return search(sql[0]);
        }
        protected void onPostExecute(String sqlResult) {
            if (type.equals("result")) {
                ds.dbResultReady(sqlResult);
            } else if (type.equals("update")) {
                ds.updateReady(sqlResult);
            } else if (type.equals("delete")){
                ds.deleteReady(sqlResult);
            } else {
                ds.dbDetailReady(sqlResult);
            }
        }
        private String search(ArrayList<String> sql) {
            String results = "";
            try {
                if (type.equals("update") || type.equals("delete")) {
                    results = "true";
                    for (String s : sql) {
                        if(Client.createData(s).equals("False")) {
                            results = "false";
                        }
                    }
                } else {
                    results = Client.readData(sql.get(0));
                }
            } catch (ConnectionException e) {
                e.printStackTrace();
            }
            System.out.println("***"+results);
            return results;
        }
    }
}
