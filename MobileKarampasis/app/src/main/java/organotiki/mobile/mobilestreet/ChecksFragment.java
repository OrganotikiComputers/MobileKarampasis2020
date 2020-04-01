package organotiki.mobile.mobilestreet;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import com.github.mikephil.charting.utils.Utils;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import organotiki.mobile.mobilestreet.objects.Bank;
import organotiki.mobile.mobilestreet.objects.Company;
import organotiki.mobile.mobilestreet.objects.FInvoiceLine;
import organotiki.mobile.mobilestreet.objects.GlobalVar;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class ChecksFragment extends DialogFragment {
    Button addCheck;
    Button addPromissoryNote;
    Button back;
    ArrayList<String> banklist;
    Company company;
    GlobalVar gVar;
    ArrayList<FInvoiceLine> lines;
    MyListAdapter mAdapter;
    AlertDialog mAlertDialog;
    ListView mListView;
    Realm realm;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            View view = inflater.inflate(R.layout.fragment_checks, (ViewGroup) null);
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            this.realm = Realm.getDefaultInstance();
            this.gVar = (GlobalVar) this.realm.where(GlobalVar.class).findFirst();
            RealmResults<Bank> findAll = this.realm.where(Bank.class).findAll();
            Integer bankCount = Integer.valueOf(findAll.size());
            this.banklist = new ArrayList<>();
            for (int i = 0; i < bankCount.intValue(); i++) {
                this.banklist.add(((Bank) findAll.get(i)).getDescription());
            }
            this.lines = new ArrayList<>();
            RealmResults<FInvoiceLine> findAll2 = this.realm.where(FInvoiceLine.class).equalTo("myFInvoice.ID", this.gVar.getMyFInvoice().getID()).equalTo("myCompany.InAppID", this.company.getInAppID()).findAll();
            Integer rrCount = Integer.valueOf(findAll2.size());
            for (int i2 = 0; i2 < rrCount.intValue(); i2++) {
                this.lines.add(findAll2.get(i2));
            }
            this.addCheck = (Button) view.findViewById(R.id.button_add_check);
            if (this.gVar.getMyFInvoice().isFinal()) {
                this.addCheck.setVisibility(View.GONE);
            } else {
                this.addCheck.setTransformationMethod((TransformationMethod) null);
                this.addCheck.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        try {
                            final FInvoiceLine line = new FInvoiceLine(UUID.randomUUID().toString(), ChecksFragment.this.gVar.getMyFInvoice(), ChecksFragment.this.company, 1);
                            ChecksFragment.this.realm.executeTransaction(new Realm.Transaction() {
                                public void execute(Realm realm) {
                                    ChecksFragment.this.lines.add((FInvoiceLine) realm.copyToRealmOrUpdate(line));
                                }
                            });
                            ChecksFragment.this.mAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            Log.e("asdfg", e.getMessage(), e);
                        }
                    }
                });
            }
            this.addPromissoryNote = (Button) view.findViewById(R.id.button_add_promissory_note);
            if (this.gVar.getMyFInvoice().isFinal()) {
                this.addPromissoryNote.setVisibility(View.GONE);
            } else {
                this.addPromissoryNote.setTransformationMethod((TransformationMethod) null);
                this.addPromissoryNote.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        try {
                            final FInvoiceLine line = new FInvoiceLine(UUID.randomUUID().toString(), ChecksFragment.this.gVar.getMyFInvoice(), ChecksFragment.this.company, 2);
                            ChecksFragment.this.realm.executeTransaction(new Realm.Transaction() {
                                public void execute(Realm realm) {
                                    ChecksFragment.this.lines.add((FInvoiceLine) realm.copyToRealmOrUpdate(line));
                                }
                            });
                            ChecksFragment.this.mAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            Log.e("asdfg", e.getMessage(), e);
                        }
                    }
                });
            }
            this.back = (Button) view.findViewById(R.id.button_back);
            this.back.setTransformationMethod((TransformationMethod) null);
            if (this.gVar.getMyFInvoice().isFinal()) {
                this.back.setText(R.string.back);
            }
            this.back.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        ChecksFragment.this.doExit();
                    } catch (Exception e) {
                        try {
                            Log.e("asdfg", e.getMessage(), e);
                        } catch (Exception e2) {
                            Log.e("asdfg", e2.getMessage(), e2);
                        }
                    }
                }
            });
            this.mAdapter = new MyListAdapter();
            this.mListView = (ListView) view.findViewById(R.id.listView_checks);
            this.mListView.setAdapter(this.mAdapter);
            setCancelable(false);
            getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode != 4) {
                        return false;
                    }
                    try {
                        ChecksFragment.this.doExit();
                        return true;
                    } catch (Exception e) {
                        Log.e("asdfg", e.getMessage(), e);
                        return true;
                    }
                }
            });
            return view;
        } catch (Exception e) {
            Log.e("asdfg", e.getMessage(), e);
            return null;
        }
    }

    /* access modifiers changed from: private */
    public void doExit() {
        boolean isNotCompleted = false;
        try {
            Iterator<FInvoiceLine> it = this.lines.iterator();
            while (it.hasNext()) {
                FInvoiceLine line = it.next();
                if (line.getExDate() != null) {
                    if (line.getType().intValue() != 1 || line.getNumber() != null) {
                        if (line.getValue().doubleValue() == 0.0 || line.getExDate().equals("") || (line.getType().intValue() == 1 && line.getNumber().equals(""))) {
                            isNotCompleted = true;
                        }
                    }
                }
                isNotCompleted = true;
            }
            if (isNotCompleted) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setPositiveButton(getString(R.string.ok), (DialogInterface.OnClickListener) null);
                alertDialog.setMessage("Δεν έχουν συμπληρωθεί όλα τα απαραίτητα πεδία των αξιογράφων σας.\nΣυμπληρώστε τα ή διαγράψτε τα ελλιπή αξιόγραφα για να συνεχίσετε.");
                alertDialog.setTitle(R.string.app_name);
                this.mAlertDialog = alertDialog.create();
                this.mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    public void onShow(DialogInterface dialog) {
                        ((AlertDialog) dialog).getButton(-1).setTransformationMethod((TransformationMethod) null);
                    }
                });
                this.mAlertDialog.show();
                return;
            }
            ((InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.back.getWindowToken(), 0);
            dismiss();
        } catch (Exception e) {
            Log.e("asdfg", e.getMessage(), e);
        }
    }

    public void onDismiss(DialogInterface dialog) {
        getActivity().getWindow().setSoftInputMode(2);
        super.onDismiss(dialog);
    }

    public void onDestroyView() {
        try {
            ((InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getView().getWindowToken(), 0);
        } catch (Exception e) {
            Log.e("asdfg", e.getMessage(), e);
        }
        super.onDestroyView();
    }

    public void respondDate(Integer position, int year, int month, int day) {
        try {
            final Integer num = position;
            final int i = day;
            final int i2 = month;
            final int i3 = year;
            this.realm.executeTransaction(new Realm.Transaction() {
                public void execute(Realm realm) {
                    ChecksFragment.this.lines.get(num.intValue()).setExDate(i + "/" + i2 + "/" + i3);
                }
            });
            this.mAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e("asdfg", e.getMessage(), e);
        }
    }

    public void setCompany(Company company2) {
        this.company = company2;
    }

    private class MyListAdapter extends BaseAdapter {
        private MyListAdapter() {
        }

        public int getCount() {
            if (ChecksFragment.this.lines == null || ChecksFragment.this.lines.size() == 0) {
                return 0;
            }
            return ChecksFragment.this.lines.size();
        }

        public Object getItem(int position) {
            return ChecksFragment.this.lines.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {

                    holder = new ViewHolder();
                    convertView = ChecksFragment.this.getActivity().getLayoutInflater().inflate(R.layout.listview_collections, parent, false);
                    holder.Bank = (Spinner) convertView.findViewById(R.id.spinner_bank);
                    holder.ExpirationDate = (EditText) convertView.findViewById(R.id.editText_expiration_date);
                    holder.ExpirationDate.setKeyListener((KeyListener) null);
                    holder.Number = (EditText) convertView.findViewById(R.id.editText_number);
                    holder.Value = (EditText) convertView.findViewById(R.id.editText_value);
                    holder.Delete = (ImageButton) convertView.findViewById(R.id.imageButton_delete);
                    convertView.setTag(holder);
                    if (ChecksFragment.this.gVar.getMyFInvoice().isFinal()) {
                        holder.Bank.setClickable(false);
                        holder.Bank.setEnabled(false);
                        holder.Number.setEnabled(false);
                        holder.Number.setFocusable(false);
                        holder.Number.setLongClickable(false);
                        holder.ExpirationDate.setEnabled(false);
                        holder.Value.setFocusable(false);
                        holder.Value.setEnabled(false);
                        holder.Delete.setImageAlpha(130);
                        holder.Delete.setEnabled(false);
                    } else {
                        holder.ExpirationDate.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                FragmentManager manager = ChecksFragment.this.getFragmentManager();
                                DatePickerFragment fragment = new DatePickerFragment();
                                fragment.setLimit(false);
                                fragment.setPosition(Integer.valueOf(holder.ref));
                                fragment.show(manager, "datePicker");
                            }
                        });
                        holder.Value.addTextChangedListener(new TextWatcher() {
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            }

                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            }

                            public void afterTextChanged(Editable editable) {
                                try {
                                    ChecksFragment.this.realm.executeTransaction(new Realm.Transaction() {
                                        public void execute(Realm realm) {
                                            ChecksFragment.this.lines.get(holder.ref).setValueText(String.valueOf(holder.Value.getText()));
                                            ChecksFragment.this.gVar.getMyFInvoice().setTotal();
                                        }
                                    });
                                    ((Collections) ChecksFragment.this.getActivity()).setTotal();
                                } catch (Exception e) {
                                    Log.e("asdfg", e.getMessage(), e);
                                }
                            }
                        });
                        holder.Delete.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                ChecksFragment.this.realm.executeTransaction(new Realm.Transaction() {
                                    public void execute(Realm realm) {
                                        try {
                                            ChecksFragment.this.lines.get(holder.ref).deleteFromRealm();
                                            ChecksFragment.this.lines.remove(holder.ref);
                                            ChecksFragment.this.gVar.getMyFInvoice().setTotal();
                                        } catch (Exception e) {
                                            Log.e("asdfg", e.getMessage(), e);
                                        }
                                    }
                                });
                                ChecksFragment.this.mAdapter.notifyDataSetChanged();
                                ((Collections) ChecksFragment.this.getActivity()).setTotal();
                            }
                        });
                    }

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (ChecksFragment.this.gVar.getMyFInvoice().isFinal()) {
                if (ChecksFragment.this.lines.get(position).getType().intValue() == 1) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ChecksFragment.this.getActivity(), R.layout.spinner_collections_item, ChecksFragment.this.banklist);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    holder.Bank.setAdapter(adapter);
                } else {
                    holder.Bank.setAdapter((SpinnerAdapter) null);
                }
            } else if (ChecksFragment.this.lines.get(position).getType().intValue() == 1) {
                Log.d("asdfg", "epitagi " + position + " " + holder.ref);
                ArrayAdapter<String> adapter2 = new ArrayAdapter<>(ChecksFragment.this.getActivity(), R.layout.spinner_collections_item, ChecksFragment.this.banklist);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                holder.Bank.setAdapter(adapter2);
                holder.Bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(final AdapterView<?> adapterView, View view, final int i, long l) {
                        try {
                            ChecksFragment.this.realm.executeTransaction(new Realm.Transaction() {
                                public void execute(Realm realm) {
                                    try {
                                        ChecksFragment.this.lines.get(holder.ref).setMyBank((Bank) realm.where(Bank.class).equalTo("Description", String.valueOf(adapterView.getItemAtPosition(i))).findFirst());
                                        Log.d("asdfg", "description3:" + ChecksFragment.this.lines.get(holder.ref).getMyBank());
                                    } catch (Exception e) {
                                        Log.e("asdfg", e.getMessage(), e);
                                    }
                                }
                            });
                        } catch (Exception e) {
                            Log.e("asdfg", e.getMessage(), e);
                        }
                    }

                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
                holder.Number.addTextChangedListener(new TextWatcher() {
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    public void afterTextChanged(Editable editable) {
                        try {
                            ChecksFragment.this.realm.executeTransaction(new Realm.Transaction() {
                                public void execute(Realm realm) {
                                    ChecksFragment.this.lines.get(holder.ref).setNumber(String.valueOf(holder.Number.getText()));
                                }
                            });
                        } catch (Exception e) {
                            Log.e("asdfg", e.getMessage(), e);
                        }
                    }
                });
                holder.Bank.setClickable(true);
                holder.Bank.setEnabled(true);
                holder.Number.setEnabled(true);
                holder.Number.setLongClickable(true);
                holder.Number.setFocusableInTouchMode(true);
                holder.Number.setFocusable(true);
                holder.Number.setHint(R.string.number);
            } else {
                Log.d("asdfg", "grammatio " + position + " " + holder.ref);
                holder.Bank.setClickable(false);
                holder.Bank.setEnabled(false);
                holder.Bank.setAdapter((SpinnerAdapter) null);
                holder.Number.setEnabled(false);
                holder.Number.setFocusable(false);
                holder.Number.setLongClickable(false);
                holder.Number.setHint("");
            }
            holder.ref = position;
            if (ChecksFragment.this.lines.get(holder.ref).getMyBank() == null) {
                holder.Bank.setSelection(0);
            } else {
                holder.Bank.setSelection(ChecksFragment.this.banklist.indexOf(ChecksFragment.this.lines.get(holder.ref).getMyBank().getDescription()));
            }
            holder.ExpirationDate.setText(ChecksFragment.this.lines.get(holder.ref).getExDate());
            holder.Number.setText(ChecksFragment.this.lines.get(holder.ref).getNumber());
            holder.Value.setText(ChecksFragment.this.lines.get(holder.ref).getValueText());
            return convertView;
        }

        class ViewHolder {
            Spinner Bank;
            ImageButton Delete;
            EditText ExpirationDate;
            String ID;
            EditText Number;
            EditText Value;
            int ref;

            ViewHolder() {
            }
        }
    }
}
