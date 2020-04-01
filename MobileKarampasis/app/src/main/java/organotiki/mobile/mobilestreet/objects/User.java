package organotiki.mobile.mobilestreet.objects;

import java.text.DecimalFormat;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Thanasis on 25/5/2016.
 */
public class User extends RealmObject {

    @PrimaryKey
    private String ID;
    private String Username;
    private String Password;
    private String FullName;
    private Double HotelExpenses = 0.0;
    private Double MealsExpenses = 0.0;
    private Double FuelExpenses = 0.0;
    private Double MiscExpenses = 0.0;
    private int Currency500 = 0;
    private int Currency200 = 0;
    private int Currency100 = 0;
    private int Currency50 = 0;
    private int Currency20 = 0;
    private int Currency10 = 0;
    private int Currency5 = 0;
    private int Currency2 = 0;
    private int Currency1 = 0;
    private int Currency050 = 0;
    private int Currency020 = 0;
    private int Currency010 = 0;
    private int Currency005 = 0;
    private Double CCheckValue = 0.0;
    private Double Deposit = 0.0;
    private int CCheckCount = 0;
    private RealmList<Customer> myCustomers;
    private RealmList<Deposit> myDeposits;

    // Standard getters & setters generated by your IDE…


    public User() {
    }

    public User(String ID, String username, String password, String fullName) {
        this.ID = ID;
        Username = username;
        Password = password;
        FullName = fullName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public Double getHotelExpenses() {
        return HotelExpenses;
    }

    public void setHotelExpenses(Double hotelExpenses) {
        this.HotelExpenses = hotelExpenses;
    }

    public Double getMealsExpenses() {
        return MealsExpenses;
    }

    public void setMealsExpenses(Double mealsExpenses) {
        this.MealsExpenses = mealsExpenses;
    }

    public Double getFuelExpenses() {
        return FuelExpenses;
    }

    public void setFuelExpenses(Double fuelExpenses) {
        this.FuelExpenses = fuelExpenses;
    }

    public Double getMiscExpenses() {
        return MiscExpenses;
    }

    public void setMiscExpenses(Double miscExpenses) {
        this.MiscExpenses = miscExpenses;
    }

    public String getHotelExpensesText() {

        DecimalFormat decim = new DecimalFormat("0.00");
        return decim.format(HotelExpenses).replace(",", ".");
    }

    public void setHotelExpensesText(String hotelExpenses) {
        HotelExpenses = Double.parseDouble(hotelExpenses.equals("")?"0":hotelExpenses.replace(",", "."));
    }

    public String getMealsExpensesText() {

        DecimalFormat decim = new DecimalFormat("0.00");
        return decim.format(MealsExpenses).replace(",", ".");
    }

    public void setMealsExpensesText(String mealsExpenses) {
        MealsExpenses = Double.parseDouble(mealsExpenses.equals("")?"0":mealsExpenses.replace(",", "."));
    }

    public String getFuelExpensesText() {

        DecimalFormat decim = new DecimalFormat("0.00");
        return decim.format(FuelExpenses).replace(",", ".");
    }

    public void setFuelExpensesText(String fuelExpenses) {
        FuelExpenses = Double.parseDouble(fuelExpenses.equals("")?"0":fuelExpenses.replace(",", "."));
    }

    public String getMiscExpensesText() {

        DecimalFormat decim = new DecimalFormat("0.00");
        return decim.format(MiscExpenses).replace(",", ".");
    }

    public void setMiscExpensesText(String miscExpenses) {
        MiscExpenses = Double.parseDouble(miscExpenses.equals("")?"0":miscExpenses.replace(",", "."));
    }

    public Double getTotalExpenses(){
        return HotelExpenses+MealsExpenses+FuelExpenses+MiscExpenses;
    }

    public String getTotalExpensesText() {

        DecimalFormat decim = new DecimalFormat("0.00");
        return decim.format(HotelExpenses+MealsExpenses+FuelExpenses+MiscExpenses).replace(",", ".");
    }

    public int getCurrency500() {
        return Currency500;
    }

    public void setCurrency500(int currency500) {
        Currency500 = currency500;
    }

    public int getCurrency200() {
        return Currency200;
    }

    public void setCurrency200(int currency200) {
        Currency200 = currency200;
    }

    public int getCurrency100() {
        return Currency100;
    }

    public void setCurrency100(int currency100) {
        Currency100 = currency100;
    }

    public int getCurrency50() {
        return Currency50;
    }

    public void setCurrency50(int currency50) {
        Currency50 = currency50;
    }

    public int getCurrency20() {
        return Currency20;
    }

    public void setCurrency20(int currency20) {
        Currency20 = currency20;
    }

    public int getCurrency10() {
        return Currency10;
    }

    public void setCurrency10(int currency10) {
        Currency10 = currency10;
    }

    public int getCurrency5() {
        return Currency5;
    }

    public void setCurrency5(int currency5) {
        Currency5 = currency5;
    }

    public int getCurrency2() {
        return Currency2;
    }

    public void setCurrency2(int currency2) {
        Currency2 = currency2;
    }

    public int getCurrency1() {
        return Currency1;
    }

    public void setCurrency1(int currency1) {
        Currency1 = currency1;
    }

    public int getCurrency050() {
        return Currency050;
    }

    public void setCurrency050(int currency050) {
        Currency050 = currency050;
    }

    public int getCurrency020() {
        return Currency020;
    }

    public void setCurrency020(int currency020) {
        Currency020 = currency020;
    }

    public int getCurrency010() {
        return Currency010;
    }

    public void setCurrency010(int currency010) {
        Currency010 = currency010;
    }

    public int getCurrency005() {
        return Currency005;
    }

    public void setCurrency005(int currency005) {
        Currency005 = currency005;
    }

    public Double getCCheckValue() {
        return CCheckValue;
    }

    public void setCCheckValue(Double CCheckValue) {
        this.CCheckValue = CCheckValue;
    }

    public int getCCheckCount() {
        return CCheckCount;
    }

    public void setCCheckCount(int CCheckCount) {
        this.CCheckCount = CCheckCount;
    }

    public RealmList<Customer> getMyCustomers() {
        return myCustomers;
    }

    public void setMyCustomers(RealmList<Customer> myCustomers) {
        this.myCustomers = myCustomers;
    }
	public RealmList<Deposit> getMyDebosits() {
        return myDeposits;
    }

    public void setMyDebosits(RealmList<Deposit> myDeposits) {
        this.myDeposits= myDeposits;
    }
    public Double getDeposit() {
        return Deposit;
    }

    public void setDeposit(Double deposit) {
        Deposit = deposit;
    }
}