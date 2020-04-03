package com.example.grocerylistapp.ui.AddItem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import com.example.grocerylistapp.R;
import com.example.grocerylistapp.ui.Database.GroceryDatabase;
import com.example.grocerylistapp.ui.Database.GroceryItem;
import com.example.grocerylistapp.ui.Database.GroceryItemRepository;

public class AddItemFragment extends Fragment {

    String defaultInputName;
    String defaultInputCategory;
    String noInputNameMsg;
    String noInputCategoryMsg;
    String addedMsg1;
    String addedMsg2;
    int toastDuration = Toast.LENGTH_SHORT;

    private EditText inputItemName;
    private Button buttonAddItem;
    private CheckBox checkBoxItemAtHome;
    private EditText inputItemCategory;

    GroceryDatabase gDB;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_additem, container, false);

        defaultInputName = getText(R.string.input_field_defaultName).toString();
        defaultInputCategory = getText(R.string.input_field_defaultCategory).toString();
        noInputNameMsg = getText(R.string.input_toast_noinput_name).toString();
        noInputCategoryMsg = getText(R.string.input_toast_noinput_category).toString();
        addedMsg1 = getText(R.string.input_toast_added1).toString();
        addedMsg2 = getText(R.string.input_toast_added2).toString();

        inputItemName = (EditText) root.findViewById(R.id.input_additem_name);
        buttonAddItem = (Button) root.findViewById(R.id.button_additem);
        inputItemCategory = (EditText) root.findViewById(R.id.input_additem_category);
        checkBoxItemAtHome = (CheckBox) root.findViewById(R.id.checkbox_additem);

        // (NAME) Remove default text when input box is clicked.
        inputItemName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b && inputItemName.getText().toString().contentEquals(getText(R.string.input_field_defaultName))) {
                    resetField(inputItemName);
                }

            }
        });

        // (CATEGORY) Remove default text when input box is clicked.
        inputItemCategory.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b && inputItemCategory.getText().toString().contentEquals(getText(R.string.input_field_defaultCategory))) {
                    resetField(inputItemCategory);
                }

            }
        });

        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addItem();
            }
        });

        // Create or Get Database (depending on if its been created yet)
        gDB = GroceryDatabase.getInstance(getContext());

        return root;
    }

    private void resetField(EditText editText) {
        editText.getText().clear();
        editText.setTextColor(getResources().getColor(R.color.color_black));
    }

    private void addItem() {

        String inputText = inputItemName.getText().toString().trim();
        String inputCategory = inputItemCategory.getText().toString().trim();
        boolean atHomeBool = checkBoxItemAtHome.isChecked();

        Toast inputToast;

        // If input text not valid, display toast warning and clear invalid fields.
        boolean inputNameInvalid = (inputText.isEmpty() || inputText.contentEquals(defaultInputName));
        boolean inputCategoryInvalid = (inputCategory.isEmpty() || inputCategory.contentEquals(defaultInputCategory));

        if (inputNameInvalid || inputCategoryInvalid) {

            String toastMsg = "";

            if (inputNameInvalid) {

                toastMsg = toastMsg + noInputNameMsg + "\n";
                resetField(inputItemName);
            }
            if (inputCategoryInvalid) {

                toastMsg = toastMsg + noInputCategoryMsg + "";
                resetField(inputItemCategory);
            }

            inputToast = Toast.makeText(getActivity(), toastMsg, toastDuration);
            inputToast.show();

        } else {

            // Add to database, toast and clear fields.
            gDB.groceryItemDAO().insertAll(new GroceryItem(inputText, inputCategory, atHomeBool));
            String toastText = addedMsg1 + inputText + addedMsg2;
            inputToast = Toast.makeText(getActivity(), toastText, toastDuration);
            inputToast.show();
            resetField(inputItemName);
            resetField(inputItemCategory);
        }

    }

}
