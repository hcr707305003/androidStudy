package replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_aas;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.R;

import java.util.List;

public class replaceflag_Bhhrtg extends RecyclerView.Adapter<replaceflag_Bhhrtg.AppViewHolder> {

    private List<replaceflag_Begryt> mDatas;
    private Context mContext;

    public replaceflag_Bhhrtg(Context context, List<replaceflag_Begryt> datas) {
        mDatas = datas;
        mContext = context;
    }


    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.replaceflag_itemapp_jnj, viewGroup, false);
        return new AppViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder viewHolder, int position) {
        viewHolder.icon.setBackground(mDatas.get(position).icon);
        viewHolder.name.setText(mDatas.get(position).name);
        viewHolder.info.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, replaceflag_Bhhrrg.class);
            intent.putExtra("pkg", mDatas.get(position).pkgName);
            mContext.startActivity(intent);
        });
        viewHolder.remove.setOnClickListener(v -> {
            showInstalledAppDetails(mContext,  mDatas.get(position).pkgName);
        });
    }

    private void showInstalledAppDetails(Context context, String packageName) {
        Uri packageURI = Uri.parse("package:" + packageName);
        // 创建Intent意图
        Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
        // 执行卸载程序
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class AppViewHolder extends RecyclerView.ViewHolder {
        private final ImageView icon;
        private final TextView name;
        private final ImageView info;
        private final TextView remove;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.name);
            info = itemView.findViewById(R.id.info);
            remove = itemView.findViewById(R.id.remove);
        }
    }
}
