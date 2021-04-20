package mx.fenrir.moneymanager.ui.adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import mx.fenrir.moneymanager.R;
import mx.fenrir.moneymanager.db.MoneyManagerViewModel;
import mx.fenrir.moneymanager.ui.activity.DashboardActivity;
import mx.fenrir.moneymanager.ui.fragments.NuevoMovimientoDialogFragment;


public class MyMovimientosRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context ctx;
    private MoneyManagerViewModel moneyManagerViewModel;
    SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/YYYY");

    //lista de items
    private List<Listitem> items;

    public MyMovimientosRecyclerViewAdapter(List<Listitem> items, Context context) {
        this.items = items;
        ctx=context;
        moneyManagerViewModel=ViewModelProviders.of((AppCompatActivity)ctx).get(MoneyManagerViewModel.class);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){

            case Listitem.TYPE_HEADER:{

                View itemView = inflater.inflate(R.layout.header_item, parent, false);
                return new HeaderViewHolder(itemView);


            }

            case Listitem.TYPE_EVENT:{

                View itemView = inflater.inflate(R.layout.movimiento_item, parent, false);
                return new MovimientosViewHolder(itemView);

            }

            default:
                throw new IllegalStateException("unsupported item type");

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        int viewType = getItemViewType(position);

        switch (viewType) {
            case Listitem.TYPE_HEADER: {
                HeaderItem header =(HeaderItem)  items.get(position);
                HeaderViewHolder Headerholder = (HeaderViewHolder)  viewHolder;
                Headerholder.tvFechaHeader.setText(formateador.format(header.getDate()));
                break;
            }
            case Listitem.TYPE_EVENT: {
                MovimientosItem event = (MovimientosItem) items.get(position);
                MovimientosViewHolder holder = (MovimientosViewHolder) viewHolder;

                holder.tvTipoItem.setText(event.getMovimientosEntity().getNombreTipo());
                DecimalFormat decimalFormat=new DecimalFormat("$###,###,###,###.00");
                if(event.getMovimientosEntity().getTipo()==1){
                    holder.tvDineroItem.setText(decimalFormat.format(event.getMovimientosEntity().getIngreso()));
                    holder.tvDineroItem.setTextColor(ctx.getColor(R.color.colorIngreso));
                }else{
                    holder.tvDineroItem.setText(decimalFormat.format(event.getMovimientosEntity().getEgreso()));
                    holder.tvDineroItem.setTextColor(ctx.getColor(R.color.colorEgreso));
                }

                break;
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void setNuevosMovimientosItems(List<Listitem> items) {

        this.items=items;
        notifyDataSetChanged();

    }


    private  class HeaderViewHolder extends RecyclerView.ViewHolder{

        public final TextView tvFechaHeader;
        public final View mView;

        public HeaderViewHolder(View view) {
            super(view);
            mView=view;
            tvFechaHeader=view.findViewById(R.id.tvFechaHeader);
        }

    }


    private  class MovimientosViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public final TextView tvTipoItem;
        public final TextView tvDineroItem;
        public final View mView;


        public MovimientosViewHolder(View view) {
            super(view);
            mView=view;
            tvTipoItem=view.findViewById(R.id.tvTipoItem);
            tvDineroItem=view.findViewById(R.id.tvDineroItem);

            view.setOnCreateContextMenuListener(this);
        }


        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            //obtenemos el movimientositem
            final MovimientosItem movimientosItem=(MovimientosItem) items.get(getAdapterPosition());

            switch (menuItem.getItemId()) {

                case R.id.action_editar:

                    DashboardActivity myActivity = (DashboardActivity)ctx;

                    NuevoMovimientoDialogFragment dialogFragment=new NuevoMovimientoDialogFragment(movimientosItem.getMovimientosEntity());
                    dialogFragment.show(myActivity.getSupportFragmentManager(), "NuevoMovimientoDialogFragment");

                    return true;

                case R.id.action_eliminar:

                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setTitle(R.string.mensaje_eliminar);
                    builder.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            moneyManagerViewModel.eliminarMovimiento(movimientosItem.getMovimientosEntity().getId());
                            Toast.makeText(ctx,ctx.getText(R.string.mov_eliminar_confirmacion),Toast.LENGTH_LONG).show();

                        }
                    });
                    builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    return true;

                default:
                    return false;


            }

        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

            MenuInflater inflater = new MenuInflater(ctx);
            inflater.inflate(R.menu.menu_movimientos,contextMenu);


            for (int i = 0; i < contextMenu.size(); i++)
            contextMenu.getItem(i).setOnMenuItemClickListener(this);

        }
    }


    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }


}
