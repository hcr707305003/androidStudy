package replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_aas;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.R;
import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Thfgfw;

public class replaceflag_Mjyyui extends Activity {
    private TextView mOpenTv1;
    private TextView mOpenTv2;
    private TextView mOpenTv3;
    private TextView mOpenTv4;
    private TextView mOpenTv5;
    private TextView mScan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replaceflag_activitysafe_jtu);

        mOpenTv1 = findViewById(R.id.open1);
        mOpenTv2 = findViewById(R.id.open2);
        mOpenTv3 = findViewById(R.id.open3);
        mOpenTv4 = findViewById(R.id.open4);
        mOpenTv5 = findViewById(R.id.open5);
        mScan = findViewById(R.id.scan);

        mOpenTv1.setOnClickListener(v -> {
            replaceflag_Thfgfw.show(getString(R.string.replaceflag_tip42));
        });
        mOpenTv2.setOnClickListener(v -> {
            replaceflag_Thfgfw.show(getString(R.string.replaceflag_tip42));
        });
        mOpenTv3.setOnClickListener(v -> {
            replaceflag_Thfgfw.show(getString(R.string.replaceflag_tip42));
        });
        mOpenTv4.setOnClickListener(v -> {
            replaceflag_Thfgfw.show(getString(R.string.replaceflag_tip42));
        });
        mOpenTv5.setOnClickListener(v -> {
            replaceflag_Thfgfw.show(getString(R.string.replaceflag_tip42));
        });
        mScan.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
