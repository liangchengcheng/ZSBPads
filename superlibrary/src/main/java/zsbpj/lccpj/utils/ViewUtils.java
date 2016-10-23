package zsbpj.lccpj.utils;

import android.app.Activity;
import android.view.View;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class ViewUtils {

    public static <V extends View> V setGone(final V view, final boolean gone) {
        if (view != null)
            if (gone) {
                if (GONE != view.getVisibility())
                    view.setVisibility(GONE);
            } else {
                if (VISIBLE != view.getVisibility())
                    view.setVisibility(VISIBLE);
            }
        return view;
    }

    public static <V extends View> V setInvisible(final V view,final boolean invisible) {
        if (view != null)
            if (invisible) {
                if (INVISIBLE != view.getVisibility())
                    view.setVisibility(INVISIBLE);
            } else {
                if (VISIBLE != view.getVisibility())
                    view.setVisibility(VISIBLE);
            }
        return view;
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T getViewOrNull(Activity parent,int viewId) {
        return (T) parent.findViewById(viewId);
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T getViewOrNull(View parent,
                                                   int viewId) {
        return (T) parent.findViewById(viewId);
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T getView(Activity parent, int viewId) {
        return (T) checkView(parent.findViewById(viewId));
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T getView(View parent, int viewId) {
        return (T) checkView(parent.findViewById(viewId));
    }

    private static View checkView(View v) {
        if (v == null) {
            throw new IllegalArgumentException("View doesn't exist");
        }
        return v;
    }

}
