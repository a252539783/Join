package mobile.xiyou.join;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.v4.animation.ValueAnimatorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2017/4/3 0003.
 */

public class RAdapter extends RecyclerView.Adapter implements View.OnClickListener,View.OnTouchListener{

    private LayoutInflater lf;
    private MRV rv;

    private ArrayList<Info> infos_default=new ArrayList<>();
    private ArrayList<Info> infos_name=new ArrayList<>();
    private ArrayList<Info> infos_num=new ArrayList<>();
    private ArrayList<Info> infos_m_name=new ArrayList<>();
    private ArrayList<Info> infos_w_num=new ArrayList<>();
    private ArrayList<Info> infos_m_num=new ArrayList<>();
    private ArrayList<Info> infos_w_name=new ArrayList<>();
    private ArrayList<Info> infos;

    private ArrayList<Holder> holders=new ArrayList<>();

    private Comparator defaultComp,pinyinComp;
    private ArrayList<View> selectedView=new ArrayList<>();

    private int height_default;
    public RAdapter(Activity c,MRV rv)
    {
        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                for (int i=0;i<selectedView.size();i++)
                    selectedView.get(i).callOnClick();
            }
        });
        this.rv=rv;
        height_default=150;
        lf=LayoutInflater.from(c);
        defaultComp=new CaseInsensitiveComparator();
        pinyinComp=new CaseInsensitiveComparator();
        infos=infos_num;
        new Thread()
        {
            @Override
            public void run() {
                super.run();
                for (int i=0;i<100;i++)
                {
                    add(new Info("aaa",""+i,false,"android"));
                }
            }
        }.start();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=lf.inflate(R.layout.item_layout,null,false);
        v.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height_default));
        v.findViewById(R.id.container_top).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height_default));
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder x=(Holder) holder;
        if(position>=holders.size()) {
            holders.add(x);
            Log.e("xx", "" + position+":"+holders.size());
        }
        x.itemView.setId(position);
        x.itemView.setOnClickListener(this);
        x.itemView.setOnTouchListener(this);
        Info i=infos.get(position);
        x.name.setText(i.name);
        x.num.setText(i.num);
        if (i.sex)
            x.sex.setText("男");
        else
            x.sex.setText("女");
    }

    @Override
    public void onClick(final View v) {
        final View img=v.findViewById(R.id.img_icon);
        final ValueAnimator ani,img_ani;
        AniState as;
        if (v.getTag()!=null)
            as=(AniState)v.getTag();
        else
        {
            selectedView.add(v);
            ani = ValueAnimator.ofInt(v.getLayoutParams().height, 2 * height_default);
            ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    v.getLayoutParams().height=(Integer)animation.getAnimatedValue();
                    v.setLayoutParams(v.getLayoutParams());
                }
            });
            ani.setDuration((height_default*2-v.getLayoutParams().height)*2);

            img_ani=ValueAnimator.ofInt(img.getLayoutParams().height,2*height_default);
            img_ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    img.getLayoutParams().height=(Integer)animation.getAnimatedValue();
                    img.setLayoutParams(img.getLayoutParams());
                    img.setAlpha((float)img.getLayoutParams().height/(height_default*2));
                }
            });
            img_ani.setDuration((height_default*2-v.getLayoutParams().height)*2);
            as=new AniState(ani);
            as.c=img_ani;
            v.setTag(as);
            ani.start();
            img_ani.start();
            return ;
        }

        if (as.select) {
                selectedView.remove(v);
                as.a.cancel();
            as.c.cancel();
                ani = ValueAnimator.ofInt(v.getLayoutParams().height, height_default);
                ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        v.getLayoutParams().height=(Integer)animation.getAnimatedValue();
                        v.setLayoutParams(v.getLayoutParams());
                    }
                });
            ani.setDuration((v.getLayoutParams().height-height_default)*2);
            img_ani=ValueAnimator.ofInt(img.getLayoutParams().height,0);
            img_ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    img.getLayoutParams().height=(Integer)animation.getAnimatedValue();
                    img.setLayoutParams(img.getLayoutParams());
                    img.setAlpha((float)img.getLayoutParams().height/(height_default*2));
                }
            });
            img_ani.setDuration((v.getLayoutParams().height-height_default)*2);
            ani.start();
            img_ani.start();
            as.b=ani;
            as.d=img_ani;
            as.select=false;
            return;
        }

            as.b.cancel();
            as.d.cancel();
            ani = ValueAnimator.ofInt(v.getLayoutParams().height, 2 * height_default);
            ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    v.getLayoutParams().height=(Integer)animation.getAnimatedValue();
                    v.setLayoutParams(v.getLayoutParams());
                }
            });
        ani.setDuration((height_default*2-v.getLayoutParams().height)*2);

        img_ani=ValueAnimator.ofInt(img.getLayoutParams().height,2*height_default);
        img_ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                img.getLayoutParams().height=(Integer)animation.getAnimatedValue();
                img.setLayoutParams(img.getLayoutParams());
                img.setAlpha((float)img.getLayoutParams().height/(height_default*2));
            }
        });
        img_ani.setDuration((height_default*2-v.getLayoutParams().height)*2);
        ani.start();
        img_ani.start();
        as.a=ani;
        as.c=img_ani;
        as.select=true;
        selectedView.add(v);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN)
        {
            for (int i=0;i<selectedView.size();i++)
                if (v!=selectedView.get(i))
            selectedView.get(i).callOnClick();
            return false;
        }
    return false;
    }

    public void add(Info x)
    {
        infos_default.add(x);
        sortAdd(infos_name,x,true);
        sortAdd(infos_num,x,false);
        if (x.sex)
        {
            sortAdd(infos_m_name,x,true);
            sortAdd(infos_m_num,x,false);
        }else
        {sortAdd(infos_w_name,x,true);
            sortAdd(infos_w_num,x,false);

        }
    }

    public void sortAdd(ArrayList<Info> xx,Info x,boolean byname)
    {

        if (xx.size()==0)
        {
            xx.add(x);
            return;
        }

        for (int i=xx.size()-1;i>=0;i--)
        {
            int res=(byname?pinyinComp.compare(x.name,xx.get(i).name):defaultComp.compare(x.num,xx.get(i).num));

            if (res>=0)
            {
                xx.add(i+1,x);
                return ;
            }else if (i==0&&res<0) {
                xx.add(0, x);
                return ;
            }
        }
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    public class Holder extends RecyclerView.ViewHolder
    {

        public TextView name,num,sex;
        public ImageView icon;

        public Holder(View v) {
            super(v);

            name=(TextView) v.findViewById(R.id.t_name);
            sex=(TextView) v.findViewById(R.id.t_sex);
            num=(TextView) v.findViewById(R.id.t_num);
            icon=(ImageView) v.findViewById(R.id.img_icon);
        }
    }

    private static final class CaseInsensitiveComparator implements
            Comparator<String>, Serializable {
        private static final long serialVersionUID = 8575799808933029326L;

        /**
         * See {@link java.lang.String#compareToIgnoreCase}.
         *
         * @exception ClassCastException
         *                if objects are not the correct type
         */
        public int compare(String o1, String o2) {
            return o1.compareToIgnoreCase(o2);
        }
    }

    private class AniState
    {
        public boolean select=false;
        public ValueAnimator a=null,b=null,c=null,d=null;

        public AniState(ValueAnimator a)
        {
            this.a=a;
            select=true;
        }
    }


}
