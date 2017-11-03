package com.quenDel.multiUtils.anim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.transition.Explode;
import android.transition.Fade;
import android.view.View;

import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.kogitune.activity_transition.ExitActivityTransition;
import com.kogitune.activity_transition.fragment.FragmentTransition;
import com.quenDel.multiUtils.R;

/**
 * Created by Natraj on 7/25/2017.
 */

public class QuendelTransition {

    /**
     * Open or Send Intent with animation in activity corrosponding to view
     */
    public static void sendIntentWithAnimation(Intent intent, Activity from, View view) {
        ActivityTransitionLauncher.with(from).from(view).launch(intent);
    }

    /**
     * Receive Intent with animation in activity corrosponding to view
     */
    public static ExitActivityTransition receiveIntentWithAnimation(Activity activity, Bundle savedInstanceState, View view) {
        return ActivityTransition.with(activity.getIntent()).to(view).duration(400).start(savedInstanceState);
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    public static void overridePendingTransitionEnter(Activity activity) {
        activity.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    public static void overridePendingTransitionExit(Activity activity) {
        activity.overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    /**
     * Overrides the Fade transaction on Activity.
     */
    public static void setFadeTransitionToActivity(Activity activity) {
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    /**
     * Overrides the transaction on Fragments.
     */
    public static void setTransitionToFragment(FragmentTransaction ft) {
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.exit_to_right, R.anim.exit_to_right);
    }


}
