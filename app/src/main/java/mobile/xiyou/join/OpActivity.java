package mobile.xiyou.join;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Window;

/**
 * Created by Administrator on 2017/4/2 0002.
 */

public class OpActivity extends AppCompatActivity {

    private RAdapter ra;
    private MRV rv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.op_layout);

        rv=(MRV)findViewById(R.id.list);
        ra=new RAdapter(this,rv);

        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(ra);
    }
}
