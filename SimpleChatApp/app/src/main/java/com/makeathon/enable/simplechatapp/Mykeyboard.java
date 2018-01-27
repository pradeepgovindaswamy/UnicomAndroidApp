package com.makeathon.enable.simplechatapp;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * Created by SAHAJA on 06-12-2017.
 */

public class Mykeyboard extends LinearLayout implements View.OnClickListener {

    // constructors
    public Mykeyboard(Context context) {
        this(context, null, 0);
    }

    public Mykeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Mykeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    // keyboard keys (buttons)
    private ImageButton a;
    private ImageButton b;
    private ImageButton c;
    private ImageButton d;
    private ImageButton e;
    private ImageButton f;
    private ImageButton g;
    private ImageButton h;
    private ImageButton i;
    private ImageButton j;
    private ImageButton k;
    private ImageButton l;
    private ImageButton m;
    private ImageButton n;
    private ImageButton o;
    private ImageButton p;
    private ImageButton q;
    private ImageButton r;
    private ImageButton s;
    private ImageButton t;
    private ImageButton u;
    private ImageButton v;
    private ImageButton w;
    private ImageButton x;
    private ImageButton y;
    private ImageButton z;
    private Button space;
    private Button enter;
    private Button delete;


    // This will map the button resource id to the String value that we want to
    // input when that button is clicked.
    SparseArray<String> keyValues = new SparseArray<>();

    // Our communication link to the EditText
    InputConnection inputConnection;

    private void init(Context context, AttributeSet attrs) {

        // initialize buttons
        LayoutInflater.from(context).inflate(R.layout.keyboard1, this, true);
        a=(ImageButton) findViewById(R.id.a);
        b=(ImageButton) findViewById(R.id.b);
        c=(ImageButton) findViewById(R.id.c);
        d=(ImageButton) findViewById(R.id.d);
        e=(ImageButton) findViewById(R.id.e);
        f=(ImageButton) findViewById(R.id.f);
        g=(ImageButton) findViewById(R.id.g);
        h=(ImageButton) findViewById(R.id.h);
        i=(ImageButton) findViewById(R.id.i);
        j=(ImageButton) findViewById(R.id.j);
        k=(ImageButton) findViewById(R.id.k);
        l=(ImageButton) findViewById(R.id.l);
        m=(ImageButton) findViewById(R.id.m);
        n=(ImageButton) findViewById(R.id.n);
        o=(ImageButton) findViewById(R.id.o);
        p=(ImageButton) findViewById(R.id.p);
        q=(ImageButton) findViewById(R.id.q);
        r=(ImageButton) findViewById(R.id.r);
        s=(ImageButton) findViewById(R.id.s);
        t=(ImageButton) findViewById(R.id.t);
        u=(ImageButton) findViewById(R.id.u);
        v=(ImageButton) findViewById(R.id.v);
        w=(ImageButton) findViewById(R.id.w);
        x=(ImageButton) findViewById(R.id.x);
        y=(ImageButton) findViewById(R.id.y);
        z=(ImageButton) findViewById(R.id.z);
        space = (Button) findViewById(R.id.space);
        delete = (Button) findViewById(R.id.del);
        enter = (Button) findViewById(R.id.enter);

        // set button click listeners
        a.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
        d.setOnClickListener(this);
        e.setOnClickListener(this);
        f.setOnClickListener(this);
        g.setOnClickListener(this);
        h.setOnClickListener(this);
        i.setOnClickListener(this);
        j.setOnClickListener(this);
        k.setOnClickListener(this);
        l.setOnClickListener(this);
        m.setOnClickListener(this);
        n.setOnClickListener(this);
        o.setOnClickListener(this);
        p.setOnClickListener(this);
        q.setOnClickListener(this);
        r.setOnClickListener(this);
        s.setOnClickListener(this);
        t.setOnClickListener(this);
        u.setOnClickListener(this);
        v.setOnClickListener(this);
        w.setOnClickListener(this);
        x.setOnClickListener(this);
        y.setOnClickListener(this);
        z.setOnClickListener(this);
        space.setOnClickListener(this);
        delete.setOnClickListener(this);
        enter.setOnClickListener(this);

        // map buttons IDs to input strings
        keyValues.put(R.id.a, "a");
        keyValues.put(R.id.b, "b");
        keyValues.put(R.id.c, "c");
        keyValues.put(R.id.d, "d");
        keyValues.put(R.id.e, "e");
        keyValues.put(R.id.f, "f");
        keyValues.put(R.id.g, "g");
        keyValues.put(R.id.h, "h");
        keyValues.put(R.id.i, "i");
        keyValues.put(R.id.j, "j");
        keyValues.put(R.id.k, "k");
        keyValues.put(R.id.l, "l");
        keyValues.put(R.id.m, "m");
        keyValues.put(R.id.n, "n");
        keyValues.put(R.id.o, "o");
        keyValues.put(R.id.p, "p");
        keyValues.put(R.id.q, "q");
        keyValues.put(R.id.r, "r");
        keyValues.put(R.id.s, "s");
        keyValues.put(R.id.t, "t");
        keyValues.put(R.id.u, "u");
        keyValues.put(R.id.v, "v");
        keyValues.put(R.id.w, "w");
        keyValues.put(R.id.x, "x");
        keyValues.put(R.id.y, "y");
        keyValues.put(R.id.z, "z");
        keyValues.put(R.id.space, " ");
        keyValues.put(R.id.enter, "\n");
    }



    @Override
    public void onClick(View v) {

        // do nothing if the InputConnection has not been set yet
        if (inputConnection == null) return;

        // Delete text or input key value
        // All communication goes through the InputConnection
        if (v.getId() == R.id.del) {
            CharSequence selectedText = inputConnection.getSelectedText(0);
            if (TextUtils.isEmpty(selectedText)) {
                // no selection, so delete previous character
                inputConnection.deleteSurroundingText(1, 0);
            } else {
                // delete the selection
                inputConnection.commitText("", 1);
            }
        } else {
            String value = keyValues.get(v.getId());
            inputConnection.commitText(value,1);

        }
    }
    // The activity (or some parent or controller) must give us
    // a reference to the current EditText's InputConnection
    public void setInputConnection(InputConnection ic) {
        this.inputConnection = ic;
    }}