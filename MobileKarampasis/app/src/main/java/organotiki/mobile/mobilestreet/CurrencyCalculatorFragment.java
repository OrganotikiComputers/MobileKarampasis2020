package organotiki.mobile.mobilestreet;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import io.realm.Realm;
import organotiki.mobile.mobilestreet.objects.FInvoice;
import organotiki.mobile.mobilestreet.objects.GlobalVar;

/**
 * Created by Thanasis on 29/9/2016.
 */

public class CurrencyCalculatorFragment extends DialogFragment {

    ImageView imvCheck;
    Button close;
    TextView txvExpensesTotalTitle, txvExpensesTotal, txvCashTotalTitle, txvCashTotal, txvDifferenceTitle, txvDifference, txvFromChecks, txvDeposit, txvCheckNum;
    EditText edt500, edt200, edt100, edt50, edt20, edt10, edt5, edt2, edt1, edt050, edt020, edt010, edt005, edtCChecksValue , edtDeposit, edtCChecksCount;
    Realm realm;
    GlobalVar gVar;
    DecimalFormat decim = new DecimalFormat("0.00");
    Double currencyTotal, cashTotal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {
            View view = inflater.inflate(R.layout.fragment_currency_calculator, null);

            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

            realm = Realm.getDefaultInstance();
            gVar = realm.where(GlobalVar.class).findFirst();

            txvExpensesTotalTitle = view.findViewById(R.id.textView_currency_total_title);
            txvExpensesTotalTitle.setText(getString(R.string.currencyTotal_, ""));
            txvExpensesTotal = view.findViewById(R.id.textView_currency_total);
            currencyTotal = Calculate();
            txvCashTotalTitle = view.findViewById(R.id.textView_cash_total_title);
            txvCashTotalTitle.setText(getString(R.string.cashTotal_, ""));
            txvCashTotal = view.findViewById(R.id.textView_deposit);
            ArrayList<FInvoice> invoices = new ArrayList<>();
            invoices.addAll(realm.where(FInvoice.class).equalTo("myUser.ID", gVar.getMyUser().getID()).findAll());
            cashTotal =0.0;
            int l = invoices.size();
            for (int i =0; i<l;i++){
                if (invoices.get(i).isFinal()) {
                    cashTotal += invoices.get(i).getCash1()+invoices.get(i).getCash2()+invoices.get(i).getCash3()+invoices.get(i).getCash4();
                    Log.d("asdfg", "cash total: "+cashTotal);
                }
            }
            cashTotal-=gVar.getMyUser().getTotalExpenses();
            txvCashTotal.setText(decim.format(cashTotal).replace(",", "."));
            txvDifferenceTitle = view.findViewById(R.id.textView_difference_title);
            txvDifferenceTitle.setText(getString(R.string.difference_, ""));
            txvDifference = view.findViewById(R.id.textView_difference);
            txvFromChecks = view.findViewById(R.id.textView_from_checks);
            txvFromChecks.setText(getResources().getString(R.string.fromChecks_, ""));
            txvDeposit = view.findViewById(R.id.textView_deposit);
            txvDeposit.setText(getResources().getString(R.string.deposit_, ""));
            txvCheckNum = view.findViewById(R.id.textView_check_num);
            txvCheckNum.setText(getResources().getString(R.string.checkNum_, ""));
            imvCheck = view.findViewById(R.id.imageView_check);

            CheckDifference();

            edt500 = view.findViewById(R.id.editText_500);
            edt500.setText(String.valueOf(gVar.getMyUser().getCurrency500()));
            edt500.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(final Editable s) {
                    try {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                gVar.getMyUser().setCurrency500(Integer.parseInt(String.valueOf(s).equals("")?"0":String.valueOf(s)));
                            }
                        });
                        CheckDifference();
                    } catch (Exception e) {
                        Log.e("asdfg", e.getMessage(), e);
                    }
                }
            });
            edt200 = view.findViewById(R.id.editText_200);
            edt200.setText(String.valueOf(gVar.getMyUser().getCurrency200()));
            edt200.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(final Editable s) {
                    try {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                gVar.getMyUser().setCurrency200(Integer.parseInt(String.valueOf(s).equals("")?"0":String.valueOf(s)));
                            }
                        });
                        CheckDifference();
                    } catch (Exception e) {
                        Log.e("asdfg", e.getMessage(), e);
                    }
                }
            });
            edt100 = view.findViewById(R.id.editText_100);
            edt100.setText(String.valueOf(gVar.getMyUser().getCurrency100()));
            edt100.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(final Editable s) {
                    try {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                gVar.getMyUser().setCurrency100(Integer.parseInt(String.valueOf(s).equals("")?"0":String.valueOf(s)));
                            }
                        });
                        CheckDifference();
                    } catch (Exception e) {
                        Log.e("asdfg", e.getMessage(), e);
                    }
                }
            });
            edt50 = view.findViewById(R.id.editText_50);
            edt50.setText(String.valueOf(gVar.getMyUser().getCurrency50()));
            edt50.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(final Editable s) {
                    try {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                gVar.getMyUser().setCurrency50(Integer.parseInt(String.valueOf(s).equals("")?"0":String.valueOf(s)));
                            }
                        });
                        CheckDifference();
                    } catch (Exception e) {
                        Log.e("asdfg", e.getMessage(), e);
                    }
                }
            });
            edt20 = view.findViewById(R.id.editText_20);
            edt20.setText(String.valueOf(gVar.getMyUser().getCurrency20()));
            edt20.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(final Editable s) {
                    try {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                gVar.getMyUser().setCurrency20(Integer.parseInt(String.valueOf(s).equals("")?"0":String.valueOf(s)));
                            }
                        });
                        CheckDifference();
                    } catch (Exception e) {
                        Log.e("asdfg", e.getMessage(), e);
                    }
                }
            });
            edt10 = view.findViewById(R.id.editText_10);
            edt10.setText(String.valueOf(gVar.getMyUser().getCurrency10()));
            edt10.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(final Editable s) {
                    try {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                gVar.getMyUser().setCurrency10(Integer.parseInt(String.valueOf(s).equals("")?"0":String.valueOf(s)));
                            }
                        });
                        CheckDifference();
                    } catch (Exception e) {
                        Log.e("asdfg", e.getMessage(), e);
                    }
                }
            });
            edt5 = view.findViewById(R.id.editText_5);
            edt5.setText(String.valueOf(gVar.getMyUser().getCurrency5()));
            edt5.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(final Editable s) {
                    try {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                gVar.getMyUser().setCurrency5(Integer.parseInt(String.valueOf(s).equals("")?"0":String.valueOf(s)));
                            }
                        });
                        CheckDifference();
                    } catch (Exception e) {
                        Log.e("asdfg", e.getMessage(), e);
                    }
                }
            });
            edt2 = view.findViewById(R.id.editText_2);
            edt2.setText(String.valueOf(gVar.getMyUser().getCurrency2()));
            edt2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(final Editable s) {
                    try {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                gVar.getMyUser().setCurrency2(Integer.parseInt(String.valueOf(s).equals("")?"0":String.valueOf(s)));
                            }
                        });
                        CheckDifference();
                    } catch (Exception e) {
                        Log.e("asdfg", e.getMessage(), e);
                    }
                }
            });
            edt1 = view.findViewById(R.id.editText_1);
            edt1.setText(String.valueOf(gVar.getMyUser().getCurrency1()));
            edt1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(final Editable s) {
                    try {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                gVar.getMyUser().setCurrency1(Integer.parseInt(String.valueOf(s).equals("")?"0":String.valueOf(s)));
                            }
                        });
                        CheckDifference();
                    } catch (Exception e) {
                        Log.e("asdfg", e.getMessage(), e);
                    }
                }
            });
            edt050 = view.findViewById(R.id.editText_050);
            edt050.setText(String.valueOf(gVar.getMyUser().getCurrency050()));
            edt050.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(final Editable s) {
                    try {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                gVar.getMyUser().setCurrency050(Integer.parseInt(String.valueOf(s).equals("")?"0":String.valueOf(s)));
                            }
                        });
                        CheckDifference();
                    } catch (NumberFormatException e) {
                        Log.e("asdfg", e.getMessage(), e);
                    }
                }
            });
            edt020 = view.findViewById(R.id.editText_020);
            edt020.setText(String.valueOf(gVar.getMyUser().getCurrency020()));
            edt020.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(final Editable s) {
                    try {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                gVar.getMyUser().setCurrency020(Integer.parseInt(String.valueOf(s).equals("")?"0":String.valueOf(s)));
                            }
                        });
                        CheckDifference();
                    } catch (NumberFormatException e) {
                        Log.e("asdfg", e.getMessage(), e);
                    }
                }
            });
            edt010 = view.findViewById(R.id.editText_010);
            edt010.setText(String.valueOf(gVar.getMyUser().getCurrency010()));
            edt010.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(final Editable s) {
                    try {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                gVar.getMyUser().setCurrency010(Integer.parseInt(String.valueOf(s).equals("")?"0":String.valueOf(s)));
                            }
                        });
                        CheckDifference();
                    } catch (NumberFormatException e) {
                        Log.e("asdfg", e.getMessage(), e);
                    }
                }
            });
            edt005 = view.findViewById(R.id.editText_005);
            edt005.setText(String.valueOf(gVar.getMyUser().getCurrency005()));
            edt005.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(final Editable s) {
                    try {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                gVar.getMyUser().setCurrency005(Integer.parseInt(String.valueOf(s).equals("")?"0":String.valueOf(s)));
                            }
                        });
                        CheckDifference();
                    } catch (NumberFormatException e) {
                        Log.e("asdfg", e.getMessage(), e);
                    }
                }
            });

            edtCChecksValue = view.findViewById(R.id.editText_from_checks);
            edtCChecksValue.setText(decim.format(gVar.getMyUser().getCCheckValue()).replace(",", "."));
            edtCChecksValue.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(final Editable s) {
                    try {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                gVar.getMyUser().setCCheckValue(Double.parseDouble(String.valueOf(s).equals("")?"0":String.valueOf(s)));
                            }
                        });
                        CheckDifference();
                    } catch (NumberFormatException e) {
                        Log.e("asdfg", e.getMessage(), e);
                    }
                }
            });

            edtDeposit = view.findViewById(R.id.editText_deposit);
            edtDeposit.setText(decim.format(gVar.getMyUser().getDeposit()).replace(",", "."));
            edtDeposit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(final Editable s) {
                    try {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                gVar.getMyUser().setDeposit(Double.parseDouble(String.valueOf(s).equals("")?"0":String.valueOf(s)));
                            }
                        });
                        CheckDifference();
                    } catch (NumberFormatException e) {
                        Log.e("asdfg", e.getMessage(), e);
                    }
                }
            });

            edtCChecksCount = view.findViewById(R.id.editText_check_num);
            edtCChecksCount.setText(String.valueOf(gVar.getMyUser().getCCheckCount()));
            edtCChecksCount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(final Editable s) {
                    try {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                gVar.getMyUser().setCCheckCount(Integer.parseInt(String.valueOf(s).equals("")?"0":String.valueOf(s)));
                            }
                        });
                    } catch (NumberFormatException e) {
                        Log.e("asdfg", e.getMessage(), e);
                    }
                }
            });

            close = view.findViewById(R.id.button_close);
            close.setTransformationMethod(null);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            return view;
        } catch (Exception e) {
            Log.e("asdfg", e.getMessage(), e);
            return null;
        }
    }

    private Double Calculate(){
        Double sub500 = (double) 500 * gVar.getMyUser().getCurrency500();
        Double sub200 = (double) 200 * gVar.getMyUser().getCurrency200();
        Double sub100 = (double) 100 * gVar.getMyUser().getCurrency100();
        Double sub50 = (double) 50 * gVar.getMyUser().getCurrency50();
        Double sub20 = (double) 20 * gVar.getMyUser().getCurrency20();
        Double sub10 = (double) 10 * gVar.getMyUser().getCurrency10();
        Double sub5 = (double) 5 * gVar.getMyUser().getCurrency5();
        Double sub2 = (double) 2 * gVar.getMyUser().getCurrency2();
        Double sub1 = (double) (gVar.getMyUser().getCurrency1());
        Double sub050 = 0.50 * gVar.getMyUser().getCurrency050();
        Double sub020 = 0.20 * gVar.getMyUser().getCurrency020();
        Double sub010 = 0.10 * gVar.getMyUser().getCurrency010();
        Double sub005 = 0.05 * gVar.getMyUser().getCurrency005();
        Double checkValue = gVar.getMyUser().getCCheckValue();
        Double deposit = gVar.getMyUser().getDeposit();
        Double total = sub500+sub200+sub100+sub50+sub20+sub10+sub5+sub2+sub1+sub050+sub020+sub010+sub005+checkValue+deposit;
        return total;
    }

    public void CheckDifference(){
        currencyTotal = Calculate();
        txvExpensesTotal.setText(decim.format(currencyTotal).replace(",", "."));
        if (Double.valueOf(decim.format(currencyTotal).replace(",", "."))>Double.valueOf(decim.format(cashTotal).replace(",", "."))){
            Log.d("asdfg", decim.format(currencyTotal)+">"+decim.format(cashTotal));
            txvDifference.setText("+"+decim.format(currencyTotal-cashTotal).replace(",", "."));
        }else if (Double.valueOf(decim.format(currencyTotal).replace(",", "."))<Double.valueOf(decim.format(cashTotal).replace(",", "."))){
            Log.d("asdfg", decim.format(currencyTotal)+"<"+decim.format(cashTotal));
            txvDifference.setText(decim.format(currencyTotal-cashTotal).replace(",", "."));
        }else {
            Log.d("asdfg", decim.format(currencyTotal)+"="+decim.format(cashTotal));
            txvDifference.setText(decim.format(0).replace(",", "."));
        }
        if (Double.parseDouble(decim.format(currencyTotal).replace(",", "."))>=Double.parseDouble(decim.format(cashTotal).replace(",", "."))-5 && Double.parseDouble(decim.format(currencyTotal).replace(",", "."))<=Double.parseDouble(decim.format(cashTotal).replace(",", "."))+5){
            imvCheck.setVisibility(View.VISIBLE);
        }else{
            imvCheck.setVisibility(View.GONE);
        }
    }
}