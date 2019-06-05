package com.security_delete;
import org.joda.time.DateTime;
/**
 * Created by simon on 5/15/2019.
 */
public class UserTokenStatus {

    private String username;
    private boolean notified = false;
    private DateTime notifyTime;

    public UserTokenStatus(String username) {
        this.username = username;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the notified
     */
    public boolean isNotified() {
        return notified;
    }

    /**
     * @param notified the notified to set
     */
    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    /**
     * @return the notifyTime
     */
    public DateTime getNotifyTime() {
        return notifyTime;
    }

    /**
     * @param notifyTime the notifyTime to set
     */
    public void setNotifyTime(DateTime notifyTime) {
        this.notifyTime = notifyTime;
    }


}
