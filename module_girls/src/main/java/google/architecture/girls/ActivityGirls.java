package google.architecture.girls;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;

import google.architecture.common.base.ARouterPath;
import google.architecture.common.base.BaseActivity;
import google.architecture.common.util.Utils;
import google.architecture.coremodel.datamodel.http.entities.GirlsData;
import google.architecture.coremodel.viewmodel.GirlsViewModel;
import android.arch.lifecycle.ViewModelProviders;
import google.architecture.girls.databinding.ActivityGirlsBinding;

@Route(path = ARouterPath.GirlsListAty)
public class ActivityGirls extends BaseActivity {

    GirlsAdapter            girlsAdapter;//适配器
    ActivityGirlsBinding    activityGirlsBinding;//代替view 相当于windows，调用每个text，imageview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("danxx", "跳转ActivityGirls页面----");

        setTitle("Module_ActivityGirls");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activityGirlsBinding = DataBindingUtil.setContentView(ActivityGirls.this,R.layout.activity_girls);

//        GirlsViewModel girlsViewModel = new GirlsViewModel(ActivityGirls.this.getApplication());

        GirlsViewModel girlsViewModel =
        ViewModelProviders.of(ActivityGirls.this).get(GirlsViewModel.class);

        girlsAdapter = new GirlsAdapter(girlItemClickCallback);
        activityGirlsBinding.setRecyclerAdapter(girlsAdapter);

        subscribeToModel(girlsViewModel);

    }

    GirlItemClickCallback   girlItemClickCallback = new GirlItemClickCallback() {
        @Override
        public void onClick(GirlsData.ResultsBean fuliItem) {
            Toast.makeText(ActivityGirls.this, fuliItem.getDesc(), Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 订阅数据变化来刷新UI
     * @param model
     */
    private void subscribeToModel(final GirlsViewModel model){
        //观察数据变化来刷新UI
        model.getLiveObservableData().observe(this, new Observer<GirlsData>() {
            @Override
            public void onChanged(@Nullable GirlsData girlsData) {
                Log.i("danyy",girlsData.toString());
                Log.i("danxx", "subscribeToModel onChanged onChanged");
//                Log.i("datamodel",girlsData.getResults().toArray().toString());
                model.setUiObservableData(girlsData);
                girlsAdapter.setGirlsList(girlsData.getResults());//adapter传递数据
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
