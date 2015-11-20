package br.com.lincoln.imagemaparealib;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * @author Lincoln Amorim - Android developer.
 * 
 * Created at 29/08/2014
 */
public class ImageMapAreaView extends RelativeLayout {

	private LayoutInflater mInflater;
	private ImageView imageView;
	private LinearLayout mask;
	private ImageMapArea imageMapArea;
	private OnAreaTouchListener onAreaTouchListener = null;
	private Context context;

	public ImageMapAreaView(Context context) {
		super(context);
		this.mInflater = LayoutInflater.from(context);
		this.context = context;
		init(); 
	}

	public ImageMapAreaView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		this.mInflater = LayoutInflater.from(context);
		this.context = context;
		init(); 
	}

	public ImageMapAreaView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mInflater = LayoutInflater.from(context);
		this.context = context;
		init(); 
	}

	public void init(){
		mInflater.inflate(R.layout.image_map_area, this, true);

		imageView = (ImageView) findViewById(R.id.img_root);
		mask = (LinearLayout) findViewById(R.id.mask_area);

		this.imageMapArea = new ImageMapArea(context, mask);
	}

	/**
	 * Add image for map.
	 * 
	 * @param bitmap Bitmap.
	 */
	public void addImage(Bitmap bitmap){
		if (imageView != null && bitmap != null) {
			imageView.setImageBitmap(bitmap);
			setSizeArea(bitmap);
			addListener();
		}
	}

	/**
	 * Set the size of the area that the component will occupy.
	 * The size of the area is the size of the bitmap.
	 * 
	 * @param bitmap Bitmap parameter.
	 */
	private void setSizeArea(Bitmap bitmap) {
		getLayoutParams().width = AndroidUtils.getDependentPixel(bitmap.getWidth(), context);
		getLayoutParams().height = AndroidUtils.getDependentPixel(bitmap.getHeight(), context);
	}

	/**
	 * Set listener for touch.
	 */
	private void addListener(){
		imageView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					if (onAreaTouchListener != null) {
						onAreaTouchListener.onTouch(event.getX(), event.getY());
						return true;
					}
				}
				return false;
			}
		});
	}

	/**
	 * Add new area.
	 * 
	 * @param id Identifier.
	 * @param coordinates Coordinates.
	 * @return
	 */
	public ImageMapAreaView addArea(Long id, String coordinates){
		imageMapArea.add(id, coordinates);
		return this;
	}

	/**
	 * Add touch listener.
	 * 
	 * @param onAreaTouchListener
	 */
	public void setTouchListener(OnAreaTouchListener onAreaTouchListener){
		this.onAreaTouchListener = onAreaTouchListener;
	}

	public interface OnAreaTouchListener{
		public void onTouch(float x, float y);
	}

	/**
	 * Show area selected.
	 * 
	 * @param x X position coordinate selected.
	 * @param y Y position coordinate selected.
	 * @return Id of area selected.
	 */
	public Long showArea(float x, float y){
		return imageMapArea.show(x, y);
	}
}