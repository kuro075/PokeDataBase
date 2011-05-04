package kuro075.poke.pokedatabase.poke_book.poke_page;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class AnimationHelper {
	private static final float SPEED=2.0f;
	private static final int DURATION=350;
	/**
     * 右から描画領域に入ってくる動作を表現するアニメーションを生成します
     * @return Animation 生成したアニメーションが戻ります
     */
    public static Animation inFromRightAnimation() {

        Animation inFromRight = 
            new TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT
                    , +SPEED
                    , Animation.RELATIVE_TO_PARENT
                    , 0.0f
                    , Animation.RELATIVE_TO_PARENT
                    , 0.0f
                    , Animation.RELATIVE_TO_PARENT
                    , 0.0f);
        inFromRight.setDuration(DURATION);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }
    /**
     * 描画領域の左へ出ていく動作を表現するアニメーションを生成します
     * @return Animation 生成したアニメーションが戻ります
     */
    public static Animation outToLeftAnimation() {
        Animation outtoLeft = 
            new TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT
                    , 0.0f
                    , Animation.RELATIVE_TO_PARENT
                    , -SPEED
                    , Animation.RELATIVE_TO_PARENT
                    , 0.0f
                    , Animation.RELATIVE_TO_PARENT
                    , 0.0f);
        outtoLeft.setDuration(DURATION);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }    
    /**
     * 左から描画領域に入ってくる動作を表現するアニメーションを生成します
     * @return Animation 生成したアニメーションが戻ります
     */
    public static Animation inFromLeftAnimation() {
        Animation inFromLeft = 
            new TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT
                    , -SPEED
                    , Animation.RELATIVE_TO_PARENT
                    , 0.0f
                    , Animation.RELATIVE_TO_PARENT
                    , 0.0f
                    , Animation.RELATIVE_TO_PARENT
                    , 0.0f);
        inFromLeft.setDuration(DURATION);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
    }
    /**
     * 描画領域の右へ出ていく動作を表現するアニメーションを生成します
     * @return Animation 生成したアニメーションが戻ります
     */
    public static Animation outToRightAnimation() {
        Animation outtoRight = 
            new TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT
                    , 0.0f
                    , Animation.RELATIVE_TO_PARENT
                    , +SPEED
                    , Animation.RELATIVE_TO_PARENT
                    , 0.0f
                    , Animation.RELATIVE_TO_PARENT
                    , 0.0f );
        outtoRight.setDuration(DURATION);
        outtoRight.setInterpolator(new AccelerateInterpolator());
        return outtoRight;
    }
}
