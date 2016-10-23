package zsbpj.lccpj.view.recyclerview;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;


public abstract class SuperFragment extends Fragment {

    private int mTaskId;
    protected int getTaskId (){
        return mTaskId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskId=getActivity().getTaskId();
    }
}

