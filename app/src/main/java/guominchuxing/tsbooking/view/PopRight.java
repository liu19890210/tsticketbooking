package guominchuxing.tsbooking.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import guominchuxing.tsbooking.R;

/**
 * Created by admin on 2017/5/4.
 */

public class PopRight extends PopupWindow{
    private View conentView;
    public PopRight(final Activity context){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.pop_right, null);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w / 2 + 40);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);

        conentView.findViewById(R.id.rl_1).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //do something you need here
                setAlertDialog(context);

            }
        });
        conentView.findViewById(R.id.rl_2).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // do something before signing out
//                context.finish();
                setAlertDialog(context);
//                PopRight.this.dismiss();
            }
        });

    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        // 以下拉方式显示popupwindow
        if (!this.isShowing()) this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 5);
        else {
            this.dismiss();
        }
    }

    /**
     *
     * @param mContext
     */
   private void setAlertDialog(Activity mContext){
       AlertDialog adb = new AlertDialog.Builder(mContext,R.style.DialogAlert)
               .setTitle("确定拨打")
               .setMessage("0755-526585475")
               .setPositiveButton("拨打", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
//                                intent(LoginActivity.class);
//                                App.finishAllActivity();
                   }
               })
               .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {

                   }
               })
               .create();
       adb.show();
       setLayoutParams(adb);
   }
    /**
     * dialog
     * @param adb
     */
   private void setLayoutParams(AlertDialog adb){
       WindowManager.LayoutParams params  = adb.getWindow().getAttributes();
       int width = adb.getWindow().getWindowManager().getDefaultDisplay().getWidth();
       params.width = width*3/4;
       adb.getWindow().setAttributes(params);
       PopRight.this.dismiss();
    }
}



