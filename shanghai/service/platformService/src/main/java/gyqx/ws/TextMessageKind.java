package gyqx.ws;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TextMessageKind {
    TEXT(1,"普通文本消息"),
    ONLINE_USER(2,"用户在线消息"),
    CMD(3,"命令消息"),
    NOTICE(4,"通知");
    private int code;
    private String description;
    TextMessageKind(int code, String description){
        this.code = code;
        this.description = description;
    }

    @JsonCreator
    public static TextMessageKind getItem(int code){
        for(TextMessageKind item : values()){
            if(item.getCode() == code){
                return item;
            }
        }
        return null;
    }
    @JsonValue
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
