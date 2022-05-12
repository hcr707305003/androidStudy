package replaceflag_fysn.replaceflag_rydk.replaceflag_capud;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class replaceflag_rryyuActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replaceflag_activitypertip_bvf);
        this.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);


        findViewById(R.id.ok).setOnClickListener(v -> {
            setResult(0);
            finish();
        });
    }

    @Override
    public void onBackPressed() {

    }

}
