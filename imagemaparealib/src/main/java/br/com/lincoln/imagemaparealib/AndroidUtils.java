package br.com.lincoln.imagemaparealib;

import android.content.Context;

/**
 * @author Lincoln Amorim - Android developer.
 *
 * Created at 29/08/2014
 */
public class AndroidUtils {

	/**
	 * Get dependent pixel. 
	 * 
	 * @param dp dependent pixel for convert.
	 * @return number of dependent pixel.
	 */
	public static int getDependentPixel(int dp, Context context){
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}
}