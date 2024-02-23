package com.akp.winninggator.Helper;
/**
 * Created by Anoop Pandey on 9696381023.
 */
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class AnimationHelper {
    public static void animatate(Context context, View view,int animId){
        Animation animation= AnimationUtils.loadAnimation(context,animId);
        view.setAnimation(animation);
    }
}
