package foodeliver.utility;

/**
 * Data search interface.
 * @author Jiaqi Zhang
 */
public interface DataSearch {

    public void dbResultReady(String result);
    public void updateReady(String result);
    public void dbDetailReady(String result);
    public void deleteReady(String result);

}
