package sg.edu.rp.c347.p04_practical;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText etBrand, etLitre;
    TextView tv;
    Button btnInsert, btnRetrieve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etBrand = findViewById(R.id.etBrand);
        etLitre = findViewById(R.id.etLitre);

        tv = findViewById(R.id.tvResult);

        btnInsert = findViewById(R.id.btnInsert);
        btnRetrieve = findViewById(R.id.btnRetrieve);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etBrand.getText().toString().equalsIgnoreCase("") || etLitre.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(MainActivity.this, "Please fill up the brand or litre", Toast.LENGTH_LONG).show();
                }else{
                    DBHelper db = new DBHelper(MainActivity.this);
                    db.insertCar(etBrand.getText().toString(), Double.parseDouble(etLitre.getText().toString()));
                    Toast.makeText(MainActivity.this, "Inserted", Toast.LENGTH_SHORT).show();
                    etBrand.setText("");
                    etLitre.setText("");
                }
            }
        });

        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper db = new DBHelper(MainActivity.this);

                ArrayList<Car> car = db.getAllCars();
                db.close();

                String txt = "";
                for (int i = 0; i<car.size(); i++){
                    txt += car.get(i).getBrand() + ". " + car.get(i).getLitre() + "L\n";
                }
                tv.setText(txt);
            }
        });
    }
}
