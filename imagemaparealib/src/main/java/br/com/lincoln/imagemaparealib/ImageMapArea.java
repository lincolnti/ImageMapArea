package br.com.lincoln.imagemaparealib;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import br.com.lincoln.imagemaparealib.ImageMapAreaItem.PlotView.Point;

/**
 * @author Lincoln Amorim - Android developer.
 * 
 * Created at 29/08/2014
 */
public class ImageMapArea {

	private Context context;
	private Map<Long, ImageMapAreaItem> areas = new HashMap<Long, ImageMapAreaItem>();
	private LinearLayout maskArea;

	public ImageMapArea(Context context, LinearLayout maskArea) {
		this.context = context;
		this.maskArea = maskArea;
	}

	/**
	 * Add new area for map.
	 * 
	 * @param id Identify.
	 * @param coordinate Coordinates.
	 * @return MapImageArea current.
	 */
	public ImageMapArea add(Long id, String coordinate){
		ImageMapAreaItem item = new ImageMapAreaItem(context, coordinate, id);
		areas.put(id, item);
		return this;
	}

	/**
	 * Shows selected area.
	 * 
	 * @param x Coordinate X of point selected.
	 * @param y Coordinate Y of point selected.
	 * @return Id of area selected.
	 */
	public Long show(float x, float y){
		clearView();

		for (Entry<Long, ImageMapAreaItem> entry : areas.entrySet()) {
			ImageMapAreaItem item = entry.getValue();
			Point[] points = item.getPoints();

			if (contains(points, x, y)) {
				showArea(item);
				return item.getId();
			}
		}
		return null;
	}

	/**
	 * Mark area as selected.
	 * 
	 * @param item Area selected.
	 */
	private void showArea(ImageMapAreaItem item) {
		maskArea.addView(item.getView());
	}

	/**
	 * Clear view.
	 */
	private void clearView(){
		maskArea.removeAllViews();
	}

	/**
	 * Checks whether the area contains the coordinate
	 * 
	 * @param points Coordinate mapped.
	 * @param x Coordinate X selected.
	 * @param y Coordinate Y selected.
	 * @return true or false.
	 */
	public boolean contains(Point[] points, float x, float y){
		boolean result = false;

		for (int i = 0, j = points.length - 1; i < points.length; j = i++) {
			if ((points[i].y > y) != (points[j].y > y) &&
					(x < (points[j].x - points[i].x) * (y - points[i].y) / (points[j].y-points[i].y) + points[i].x)) {
				result = !result;
			}
		}
		return result;
	}
}