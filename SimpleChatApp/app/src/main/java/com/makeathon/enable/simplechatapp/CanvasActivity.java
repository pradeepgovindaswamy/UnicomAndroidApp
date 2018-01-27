package com.makeathon.enable.simplechatapp;

/**
 * Created by SAHAJA on 20-12-2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;


public class CanvasActivity extends Activity {

    private CanvasView customCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);

        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);
    }

    public void clearCanvas(View v) {
        customCanvas.clearCanvas();
    }

}