package mx.fenrir.moneymanager.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import mx.fenrir.moneymanager.R;
import mx.fenrir.moneymanager.ui.fragments.DashboardCategoriaDialogFragment;

public class OpcionesActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvEditCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);

        this.setTitle(R.string.opciones);

        setElements();

        tvEditCat.setOnClickListener(this);

    }

    private void setElements() {

        tvEditCat=findViewById(R.id.textViewEditCat);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.textViewEditCat:

                DashboardCategoriaDialogFragment dialogFragment=new DashboardCategoriaDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "DashboardCategoriaDialogFragment");

                break;

            default:

                Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();

                break;


        }


    }
}
