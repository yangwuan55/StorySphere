package com.ruselabs.storysphere.activities;

import rajawali.vr.RajawaliVRActivity;
import com.ruselabs.storysphere.renderer.StorySphereRenderer;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

/**
 * This class is the entry point for the StorySphere experience.
 * All we're doing is extending {@link rajawali.RajawaliActivity}
 * to connect our custom renderer, which will handle all things graphics.
 *
 *  @author David Brodsky (dbro@dbro.pro)
 */
public class StorySphereActivity extends RajawaliVRActivity {
	private StorySphereRenderer mRenderer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide all Android window chrome
        enterStickyImmersiveMode();

        // Lock the orientation to landscape
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Connect our Storysphere renderer
		mRenderer = new StorySphereRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		setRenderer(mRenderer);
	}


    /**
     * Enter Sticky Immersion Mode, hiding the status and navigation bars.
     * This allows the user to swipe from screen edge to temporarily
     * reveal the status and navigation interface.
     *
     * @see <a href="https://developer.android.com/training/system-ui/immersive.html#sticky">Sticky Immersion</a>
     */
    private void enterStickyImmersiveMode() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            // Prevent user touches from permanently resuming the Navigation Bar
            enterStickyImmersiveMode();
        }
    }
}
