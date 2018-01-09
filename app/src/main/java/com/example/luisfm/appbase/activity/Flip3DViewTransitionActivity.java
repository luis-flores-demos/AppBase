package com.example.luisfm.appbase.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.example.luisfm.appbase.R;
import com.example.luisfm.appbase.helpers.animation.AnimationFactory;

/**
 * Created by LUIS  FM on 08/01/2018.
 * Customización Animation
 */

public class Flip3DViewTransitionActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip);
        
        final ViewAnimator viewAnimator = (ViewAnimator)this.findViewById(R.id.viewFlipper);

        viewAnimator.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View v) { 
				AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);
			}
        });

        this.findViewById(R.id.imageView1).setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View v) { 
				AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);
			}
        });
        
        this.findViewById(R.id.imageView2).setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View v) { 
				AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);
			}
        });
        
        
    }
}