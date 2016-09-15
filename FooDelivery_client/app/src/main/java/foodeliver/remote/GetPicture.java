package foodeliver.remote;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import foodeliver.utility.PictureSearch;


public class GetPicture {
    PictureSearch ps = null;

    public void search(ArrayList<String> searchUrls, PictureSearch ps) {
        this.ps = ps;
        new AsyncPicSearch().execute(searchUrls);
    }

    private class AsyncPicSearch extends AsyncTask<ArrayList<String>, Void, ArrayList<Bitmap>> {
        protected ArrayList<Bitmap> doInBackground(ArrayList<String>... urls) {
            System.out.println(urls[0]);
            return search(urls[0]);
        }

        protected void onPostExecute(ArrayList<Bitmap> pictures) {
            ps.pictureReady(pictures);
        }

        private ArrayList<Bitmap> search(ArrayList<String> searchUrls) {
            try {
                ArrayList<Bitmap> results = new ArrayList<Bitmap>();
                for (String s : searchUrls) {
                    URL u = new URL(s);
                    results.add(getImage(u));
                }
                return results;
            } catch (Exception e) {
                e.printStackTrace();
                return null; // so compiler does not complain
            }
        }

        private Bitmap getImage(final URL url) {
            try {
                URLConnection con = url.openConnection();
                con.connect();
                BufferedInputStream b = new BufferedInputStream(con.getInputStream());
                Bitmap bm = BitmapFactory.decodeStream(b);
                b.close();
                return bm;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
