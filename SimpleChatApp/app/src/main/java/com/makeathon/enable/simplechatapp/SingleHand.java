package com.makeathon.enable.simplechatapp;



        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.text.InputType;
        import android.view.inputmethod.EditorInfo;
        import android.view.inputmethod.InputConnection;
        import android.widget.EditText;

/**
 * Created by SAHAJA on 13-12-2017.
 */

public class SingleHand extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_hand);

        EditText editText1 = (EditText) findViewById(R.id.editText1);
        Mykeyboard keyboard = (Mykeyboard) findViewById(R.id.keyboard);


        // prevent system keyboard from appearing when EditText is tapped
        editText1.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText1.setTextIsSelectable(true);

        // pass the InputConnection from the EditText to the keyboard
        InputConnection ic = editText1.onCreateInputConnection(new EditorInfo());
        keyboard.setInputConnection(ic);
    }
}

