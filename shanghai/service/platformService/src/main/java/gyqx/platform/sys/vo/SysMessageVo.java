package gyqx.platform.sys.vo;

import gyqx.platform.po.SysMessage;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by moonb on 2017/11/1.
 */
public class SysMessageVo extends SysMessage implements Serializable {
    public String userId;
    public int hasRead = 0;
    public java.util.Date readDatetimeStart;
    public java.util.Date readDatetimeEnd;
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }

    public Date getReadDatetimeStart() {
        return readDatetimeStart;
    }

    public void setReadDatetimeStart(Date readDatetimeStart) {
        this.readDatetimeStart = readDatetimeStart;
    }

    public Date getReadDatetimeEnd() {
        return readDatetimeEnd;
    }

    public void setReadDatetimeEnd(Date readDatetimeEnd) {
        this.readDatetimeEnd = readDatetimeEnd;
    }
}
