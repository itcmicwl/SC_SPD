package gyqx.ws;

import java.util.Date;

public class GYTextMessage<T> {
    private T data;
    private TextMessageKind msgKind;
    private String toUser;
    private String fromUser;
    private String appCode;
    private Date msgDate;
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public TextMessageKind getMsgKind() {
        return msgKind;
    }

    public void setMsgKind(TextMessageKind msgKind) {
        this.msgKind = msgKind;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public Date getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(Date msgDate) {
        this.msgDate = msgDate;
    }
}