package br.com.lincoln.imagemaparealib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.view.View;

/**
 * @author Lincoln Amorim - Android developer.
 * 
 * Created at 29/08/2014
 */

public class ImageMapAreaItem {

	private String coordinates = null;
	private PlotView plotView;
	private Long id = null;

	public ImageMapAreaItem(Context context, String coordinates, Long id) {
		this.coordinates = coordinates;
		this.id = id;
		this.plotView = new PlotView(context);
	}

	public class PlotView extends View {
		float[][] coord = scaleATwo(getCoordinates(), 2);

		Point[] points = new Point[coord.length];

		public PlotView(Context context){
			super(context);

			for (int i = 0; i < coord.length; i++) {
				float[] result = coord[i];

				Float x = null;
				Float y = null;

				for (float j : result) {
					if (x == null) {
						x = j;
					} else {
						y = j;
					}
				}

				points[i] = new Point();
				points[i].x = x;
				points[i].y = y;
			}
		}

		/**
		 * Get coordinates. Convert of String to float[].
		 * 
		 * @return float[].
		 */
		private float[] getCoordinates(){
			coordinates = coordinates.replace(" ", "");
			String[] coords = coordinates.split(","); 
			float[] array = new float[coords.length];

			for (int i = 0; i < coords.length; i++) {
				array[i] = Float.valueOf(coords[i]);
			}

			return array;
		}

		@Override
		protected void onDraw(Canvas canvas){
			canvas.drawColor(Color.TRANSPARENT);
			drawLine(canvas);
			drawPath(canvas);
			super.onDraw(canvas);
		}

		/**
		 * Draw border.
		 * 
		 * @param canvas Canvas.
		 */
		private void drawLine(Canvas canvas) {
			Paint border = getBorderPaint();

			for (int i = 0; i < points.length; i++){
				if (i < points.length - 1){
					canvas.drawLine(points[i].x, points[i].y, points[i + 1].x, points[i + 1].y, border);
				} else if (i == points.length - 1) {
					canvas.drawLine(points[i].x, points[i].y, points[0].x, points[0].y, border);
				}
			}
		}

		/**
		 * Draw background.
		 * 
		 * @param canvas Canvas.
		 */
		private void drawPath(Canvas canvas) {
			Paint pathPaint = getPathPaint();
			Path path = new Path();
			path.reset();

			path.moveTo(points[0].x, points[0].y);

			for (int i = 0; i < points.length; i++){
				if (i < points.length - 1){
					path.lineTo(points[i].x, points[i].y);
				}
			}
			path.lineTo(points[(points.length - 1)].x, points[points.length - 1].y);

			canvas.drawPath(path, pathPaint);
		}

		public class Point implements Comparable<Point>{
			float x;
			float y;

			@Override
			public int compareTo(Point other){
				if (x < other.x) return -1;
				if (x > other.x) return 1;
				return 0;
			}
		}

		/**
		 * Transform array dimensional to array two-dimensional.
		 * 
		 * @param matrix Matrix.
		 * @param width Width.
		 * @return float[][] (array two-dimensional).
		 */
		float[][] scaleATwo(float[] matrix, int width){
			int altura = matrix.length / width;
			float[][] ret = new float[altura][width];

			for(int i=0; i<matrix.length; i++) {
				ret[i/width][i%width] = matrix[i];
			}

			return ret;
		}

		/**
		 * Get style border.
		 * 
		 * @return Paint.
		 */
		private Paint getBorderPaint(){
			Paint border = new Paint();
			border.setColor(Color.parseColor("#003F87"));
			border.setStrokeWidth(3);
			border.setFlags(Paint.ANTI_ALIAS_FLAG);
			border.setStyle(Style.STROKE);
			return border;
		}

		/**
		 * Get background style.
		 * 
		 * @return
		 */
		private Paint getPathPaint(){
			Paint paint = new Paint();
			paint.setColor(Color.parseColor("#731D7CF2"));
			paint.setFlags(Paint.ANTI_ALIAS_FLAG);
			paint.setStyle(Style.FILL);
			return paint;
		}

		public Point[] getPoints() {
			return points;
		}
	}

	public PlotView getView() {
		return plotView;
	}

	public PlotView.Point[] getPoints(){
		PlotView.Point[] points = plotView.getPoints();
		return points != null ? plotView.getPoints() : null;
	}

	public Long getId() {
		return id;
	}

	public String getCoordinates() {
		return coordinates;
	}

}