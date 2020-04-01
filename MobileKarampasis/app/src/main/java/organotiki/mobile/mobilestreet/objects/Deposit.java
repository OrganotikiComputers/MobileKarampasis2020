package organotiki.mobile.mobilestreet.objects;

import java.text.DecimalFormat;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Thanasis on 1/7/2016.
 */
public class Deposit extends RealmObject {

    @PrimaryKey
    private String ID;
    private double Value;
	private String Date;

    public Deposit() {
    }

    public Deposit(String ID,double Value,String Date) {
        this.ID=ID;
        this.Value=Value;
        this.Date=Date;
    }
	
	public String getID() {
        return ID;
    }
	
	public void setID(String ID) {
        this.ID = ID;
    }

    public double getValue() {
        return Value;
    }

    public void setValue(Double value) {
        Value = value;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getValueText() {

        DecimalFormat decim = new DecimalFormat("0.00");
        return decim.format(Value).replace(",", ".");
    }

    public void setValueText(String value) {
        Value = Double.parseDouble(value.replace(",", "."));
    }
}
