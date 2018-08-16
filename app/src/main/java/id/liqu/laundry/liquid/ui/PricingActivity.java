package id.liqu.laundry.liquid.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import id.liqu.laundry.liquid.R;

/**
 * Created by fathy on 23/03/2018.
 */

public class PricingActivity extends AppCompatActivity {

    Button button, submitharga;
    TextInputEditText berat;
    TextView cost;
    int i, harga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricing);
        harga= 0;
        i =0;
        button =  (Button) findViewById(R.id.plus_btn);
        cost = (TextView) findViewById(R.id.harga);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                berat = (TextInputEditText) findViewById(R.id.berat);
                i = i +1;
                berat.setText(""+i);

                cost.setText(""+hitungHarga(i));


            }
        });

        button =  (Button) findViewById(R.id.minus_btn);
        cost = (TextView) findViewById(R.id.harga);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                berat = (TextInputEditText) findViewById(R.id.berat);
                i = i-1;
                berat.setText(""+i);
                cost.setText(""+hitungHarga(i));

            }
        });

        submitharga = (Button) findViewById(R.id.submitharga);
        submitharga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( PricingActivity.this, Comfirmation.class));
            }
        });


    }


    public int hitungHarga(int i){
        int total = 5000 * i;
        return total;
    }

}
