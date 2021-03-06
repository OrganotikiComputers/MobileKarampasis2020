package organotiki.mobile.mobilestreet.objects;

import android.content.Intent;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Thanasis on 25/5/2016.
 */
public class InvoiceType extends RealmObject {

    @PrimaryKey
    private String ID;
    private String Code;
    private String Description;
    private Boolean isNegativeSell;
    private Boolean isSalesPolicy;
    private String Type;
    private boolean isEY;
    private boolean isGuarantee;
    private boolean isScript;

    // Standard getters & setters generated by your IDE…

    public InvoiceType() {
    }

    public InvoiceType(String ID, String code, String description, Boolean isNegativeSell, Boolean isSalesPolicy, String type, boolean isEY, boolean isGuarantee, boolean isScript) {
        this.ID = ID;
        Code = code;
        Description = description;
        this.isNegativeSell = isNegativeSell;
        this.isSalesPolicy = isSalesPolicy;
        Type = type;
        this.isEY = isEY;
        this.isGuarantee = isGuarantee;
        this.isScript = isScript;
    }

    public InvoiceType(String ID, String description) {
        this.ID = ID;
        Description = description;

    }

    public String getID() { return ID; }
    public void   setID(String id) { this.ID = id; }
    public String getDescription() { return Description; }
    public void   setDescription(String description) { Description = description; }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public Boolean getNegativeSell() {
        return isNegativeSell;
    }

    public void setNegativeSell(Boolean negativeSell) {
        isNegativeSell = negativeSell;
    }

    public Boolean getSalesPolicy() {
        return isSalesPolicy;
    }

    public void setSalesPolicy(Boolean salesPolicy) {
        isSalesPolicy = salesPolicy;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public boolean isEY() {
        return isEY;
    }

    public void setEY(boolean EY) {
        isEY = EY;
    }

    public boolean isGuarantee() {
        return isGuarantee;
    }

    public void setGuarantee(boolean guarantee) {
        isGuarantee = guarantee;
    }

    public boolean isScript() {
        return isScript;
    }

    public void setScript(boolean script) {
        isScript = script;
    }
}
