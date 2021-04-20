package mx.fenrir.moneymanager.ui.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.fenrir.moneymanager.R;
import mx.fenrir.moneymanager.db.MoneyManagerViewModel;
import mx.fenrir.moneymanager.db.entity.MovimientosEntity;
import mx.fenrir.moneymanager.utils.DatePickerFragment;

public class NuevoMovimientoDialogFragment extends DialogFragment implements View.OnClickListener {

    ImageView ivClose;
    Button btGuardar;
    Spinner spMov,spCat;
    EditText etImporte,etFecha;
    MovimientosEntity movimientosEntity;
    Date fechaSel=new Date();
    SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/YYYY");

    private MoneyManagerViewModel moneyManagerViewModel;


    public NuevoMovimientoDialogFragment() {

    }

    public NuevoMovimientoDialogFragment(MovimientosEntity movimientosEntity) {
        this.movimientosEntity=movimientosEntity;
    }

    private void setMovEntity() {

        btGuardar.setText(getText(R.string.buttonActualizarMov));

        etFecha.setText(formateador.format(fechaSel));

        if(movimientosEntity.getTipo()==1){

            etImporte.setText(String.valueOf(movimientosEntity.getIngreso()));
            spMov.setSelection(0);
        }else if(movimientosEntity.getTipo()==0){
            etImporte.setText(String.valueOf(movimientosEntity.getEgreso()));
            spMov.setSelection(1);
        }

        spCat.setSelection(obtenerPosicionItem(spCat,movimientosEntity.getNombreTipo()));

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL,R.style.FullScreenDialogStyle);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view=inflater.inflate(R.layout.nuevo_movimiento_dialog,container,false);

        //vinculamos el viewmodel
        moneyManagerViewModel= ViewModelProviders.of(getActivity()).get(MoneyManagerViewModel.class);

        //vinculamos el image view
        ivClose=view.findViewById(R.id.imageViewClose);
        ivClose.setOnClickListener(this);

        //vinculamos al boton de guardar mov

        btGuardar=view.findViewById(R.id.buttonGuardarMov);
        btGuardar.setOnClickListener(this);

        //Vinculamos el spinner

        spMov=view.findViewById(R.id.spinnerMov);
        spCat=view.findViewById(R.id.spinnerCat);


        String [] mov={getString(R.string.ingreso),getString(R.string.egreso)};
        ArrayAdapter <String> adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,mov);
        spMov.setAdapter(adapter);


        spMov.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selspMov=spMov.getSelectedItem().toString();

                List<String> listCategoria;

                if(selspMov.equals(getString(R.string.ingreso))){

                    listCategoria=moneyManagerViewModel.obtenerTipos(0);

                    String[] array=new String[listCategoria.size()];
                    array=listCategoria.toArray(array);
                    ArrayAdapter <String> adapterTip=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,array);
                    spCat.setAdapter(adapterTip);


                }else if(selspMov.equals(getString(R.string.egreso))){

                   listCategoria=moneyManagerViewModel.obtenerTipos(1);

                    String[] array=new String[listCategoria.size()];
                    array=listCategoria.toArray(array);
                    ArrayAdapter <String> adapterTip=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,array);
                    spCat.setAdapter(adapterTip);

                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        etImporte=view.findViewById(R.id.editTextImporte);
        etFecha=view.findViewById(R.id.etFecha);
        etFecha.setOnClickListener(this);
        etFecha.setText(formateador.format(fechaSel));

        if(movimientosEntity!=null){

            fechaSel=movimientosEntity.getFecha();
            setMovEntity();


        }

        return  view;

    }




    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.etFecha:

                showDatePickerDialog();

                break;

            case R.id.imageViewClose:


                getDialog().dismiss();

            break;

            case R.id.buttonGuardarMov:


                if(etImporte.getText().toString().isEmpty()){

                    Toast.makeText(getActivity(),getString(R.string.importeVacio),Toast.LENGTH_SHORT).show();

                }else{


                Float dinero=Float.parseFloat(etImporte.getText().toString().trim());
                String categoria=spCat.getSelectedItem().toString();
                String movimiento=spMov.getSelectedItem().toString();

                if(movimientosEntity==null){
                if(movimiento.equals(getString(R.string.ingreso))){
                    moneyManagerViewModel.insertMovimiento(new MovimientosEntity(fechaSel,0,dinero,1,categoria));
                }else if(movimiento.equals(getString(R.string.egreso))){
                    moneyManagerViewModel.insertMovimiento(new MovimientosEntity(fechaSel,dinero,0,0,categoria));
                }
                Toast.makeText(getActivity(),getString(R.string.guardarMov),Toast.LENGTH_SHORT).show();
                }else{


                    if(movimiento.equals(getString(R.string.ingreso))){

                        moneyManagerViewModel.actualizarMovimiento(movimientosEntity.getId(),categoria,0,dinero,1,fechaSel);

                    }else if(movimiento.equals(getString(R.string.egreso))){

                        moneyManagerViewModel.actualizarMovimiento(movimientosEntity.getId(),categoria,dinero,0,0,fechaSel);

                    }

                    Toast.makeText(getActivity(),getString(R.string.actualizarMov),Toast.LENGTH_SHORT).show();

                }
                // cerramos el dialogo
                getDialog().dismiss();

                }
            break;


        }

    }

    private void showDatePickerDialog() {

        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero

                Calendar calTemp=Calendar.getInstance();
                calTemp.set(year,month,day);

                fechaSel=calTemp.getTime();

                etFecha.setText(formateador.format(fechaSel));
            }
        },fechaSel);

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }


    public static int obtenerPosicionItem(Spinner spinner, String cadena) {
        int posicion = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(cadena)) {
                posicion = i;
            }
        }
        return posicion;
    }

}
