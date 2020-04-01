package organotiki.mobile.mobilestreet;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import com.github.mikephil.charting.utils.Utils;
import io.realm.Realm;
import io.realm.RealmList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import organotiki.mobile.mobilestreet.objects.Company;
import organotiki.mobile.mobilestreet.objects.Deposit;
import organotiki.mobile.mobilestreet.objects.GlobalVar;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class DepositsFragment extends DialogFragment {
    Button btnAdd;
    Button btnOK;
    Company company;
    GlobalVar gVar;
    ArrayList<Deposit> lines;
    MyListAdapter mAdapter;
    AlertDialog mAlertDialog;
    ListView mListView;
    Realm realm;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            View view = inflater.inflate(R.layout.fragment_deposits, (ViewGroup) null);
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            this.realm = Realm.getDefaultInstance();
            this.gVar = (GlobalVar) this.realm.where(GlobalVar.class).findFirst();
            RealmList<Deposit> deposits = this.gVar.getMyUser().getMyDebosits();
            this.lines = new ArrayList<>();
            this.lines.addAll(deposits);
            this.btnAdd = (Button) view.findViewById(R.id.button_add);
            this.btnAdd.setTransformationMethod((TransformationMethod) null);
            this.btnAdd.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        final Deposit line = new Deposit(UUID.randomUUID().toString(), Double.valueOf(0.0), (String) null);
                        DepositsFragment.this.realm.executeTransaction(new Realm.Transaction() {
                            public void execute(Realm realm) {
                                Deposit l = (Deposit) realm.copyToRealmOrUpdate(line);
                                DepositsFragment.this.gVar.getMyUser().getMyDebosits().add(l);
                                DepositsFragment.this.lines.add(l);
                            }
                        });
                        DepositsFragment.this.mAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        Log.e("asdfg", e.getMessage(), e);
                    }
                }
            });
            this.btnOK = (Button) view.findViewById(R.id.button_ok);
            this.btnOK.setTransformationMethod((TransformationMethod) null);
            this.btnOK.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        DepositsFragment.this.doExit();
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
                        DepositsFragment.this.doExit();
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
            Iterator<Deposit> it = this.lines.iterator();
            while (it.hasNext()) {
                Deposit line = it.next();
                if (line.getDate() == null || line.getValue() == 0.0) {
                    isNotCompleted = true;
                }
            }
            if (isNotCompleted) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setPositiveButton(getString(R.string.ok), (DialogInterface.OnClickListener) null);
                alertDialog.setMessage("Δεν έχουν συμπληρωθεί όλα τα απαραίτητα πεδία των καταθέσεων σας.\nΣυμπληρώστε τα ή διαγράψτε τις ελλιπής καταθέσεις για να συνεχίσετε.");
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
            ((InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.btnOK.getWindowToken(), 0);
            dismiss();
        } catch (Exception e) {
            Log.e("asdfg", e.getMessage(), e);
        }
    }

    public void onDismiss(DialogInterface dialog) {
        getActivity().getWindow().setSoftInputMode(2);
        Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
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
                    DepositsFragment.this.lines.get(num.intValue()).setDate(i + "/" + i2 + "/" + i3);
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
            if (DepositsFragment.this.lines == null || DepositsFragment.this.lines.size() == 0) {
                return 0;
            }
            return DepositsFragment.this.lines.size();
        }

        public Object getItem(int position) {
            return DepositsFragment.this.lines.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = DepositsFragment.this.getActivity().getLayoutInflater().inflate(R.layout.listview_deposits, parent, false);
                    holder.Date = (EditText) convertView.findViewById(R.id.editText_expiration_date);
                    holder.Date.setKeyListener((KeyListener) null);
                    holder.Value = (EditText) convertView.findViewById(R.id.editText_value);
                    holder.Delete = (ImageButton) convertView.findViewById(R.id.imageButton_delete);
                    convertView.setTag(holder);
                    holder.Date.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            FragmentManager manager = DepositsFragment.this.getFragmentManager();
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
                                DepositsFragment.this.realm.executeTransaction(new Realm.Transaction() {
                                    public void execute(Realm realm) {
                                        DepositsFragment.this.lines.get(holder.ref).setValueText(String.valueOf(holder.Value.getText()));
                                    }
                                });
                            } catch (Exception e) {
                                Log.e("asdfg", e.getMessage(), e);
                            }
                        }
                    });
                    holder.Delete.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            DepositsFragment.this.realm.executeTransaction(new Realm.Transaction() {
                                public void execute(Realm realm) {
                                    try {
                                        DepositsFragment.this.lines.get(holder.ref).deleteFromRealm();
                                        DepositsFragment.this.lines.remove(holder.ref);
                                    } catch (Exception e) {
                                        Log.e("asdfg", e.getMessage(), e);
                                    }
                                }
                            });
                            DepositsFragment.this.mAdapter.notifyDataSetChanged();
                        }
                    });

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.ref = position;
            holder.Date.setText(DepositsFragment.this.lines.get(holder.ref).getDate());
            holder.Value.setText(DepositsFragment.this.lines.get(holder.ref).getValueText());
            return convertView;
        }

        class ViewHolder {
            EditText Date;
            ImageButton Delete;
            EditText Value;
            int ref;

            ViewHolder() {
            }
        }
    }
}
