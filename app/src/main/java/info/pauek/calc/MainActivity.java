package info.pauek.calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Problemes coneguts que no intentem arreglar:
    // 1. Que el número ocupi més que el tamany de la pantalla (perquè és molt gran o bé té molts decimals).
    // 2. Errors de precisió que fan que certs càlculs donin resultats amb molts decimals quan no tocaria.

    // Model
    private String snum = "";     // Número actual (el que es veu a la pantalla)
    private double fnumant = 0.0; // Número anterior (ja en float)
    private char op = ' ';        // Operació pendent (un sol char)

    // Referències a objectes de la pantalla
    private TextView numview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numview = findViewById(R.id.numview);
        numview.setText(snum);
    }

    public void onDigitPress(View view) {
        Button btn = (Button)view;
        snum += btn.getText().toString();
        numview.setText(snum);
    }

    public void onDot(View view) {
        if (!snum.contains(".")) {
            snum += ".";
            numview.setText(snum);
        }
    }

    public void onOperation(View view) {
        Button btn = (Button)view;
        fnumant = computeResult();
        snum = "";
        numview.setText(snum);
        op = btn.getText().toString().charAt(0); // Guardem la nova operació pendent
    }

    public void onResult(View view) {
        fnumant = computeResult();
        op = ' ';
        snum = Double.toString(fnumant);
        fixCurrNum();
        numview.setText(snum);
    }

    public void onClear(View view) {
        fnumant = 0.0;
        op = ' ';
        snum = "";
        numview.setText(snum);
    }

    private double computeResult() {
        double value = Double.valueOf(snum);
        double result;
        switch (op) {
            case '+': result = fnumant + value; break;
            case '-': result = fnumant - value; break;
            case '*': result = fnumant * value; break;
            case '/': result = fnumant / value; break;
            default: result = value;
        }
        return result;
    }

    void fixCurrNum() {
        // Traiem zeros del final (només si n'hi ha 2, per no esborrar el número "0")
        while (snum.endsWith("0")) {
            snum = snum.substring(0, snum.length()-1);
        }
        // Traiem el punt si té un zero després (per no treure el
        if (snum.endsWith(".")) {
            snum = snum.substring(0, snum.length()-1);
        }
    }
}

