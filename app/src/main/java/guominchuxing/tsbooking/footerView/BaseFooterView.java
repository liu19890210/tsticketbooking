package guominchuxing.tsbooking.footerView;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by admin on 2017/8/31.
 */

public abstract class BaseFooterView extends FrameLayout implements FooterViewListener{

    public BaseFooterView(@NonNull Context context) {
        super(context);
    }

    public BaseFooterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseFooterView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
